from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required, user_passes_test
from django.contrib.auth.models import User
from django.shortcuts import render, redirect, get_object_or_404
from django.utils import timezone
from .models import Employee, Attendance, Holiday
from decimal import Decimal
from datetime import date, datetime, timedelta

def is_hr(user):
    return hasattr(user, 'employee') and user.employee.role == 'HR'

def login_view(request):
    if request.method == 'POST':
        user = authenticate(username=request.POST['username'], password=request.POST['password'])
        if user:
            login(request, user)
            return redirect('dashboard')
        return render(request, 'registration/login.html', {'error': 'Invalid credentials'})
    return render(request, 'registration/login.html')

def logout_view(request):
    logout(request)
    return redirect('login')

@login_required
def dashboard(request):
    emp = request.user.employee
    today = date.today()
    month_start = today.replace(day=1)

    if emp.role == 'HR':
        employees = Employee.objects.all()
        # Calculate salary for all employees this month
        data = []
        for e in employees:
            atts = Attendance.objects.filter(employee=e, date__gte=month_start, date__lte=today, sign_out__isnull=False)
            total_hours = sum((a.total_hours or 0) for a in atts)
            salary = float(total_hours) * float(e.hourly_rate)
            data.append({'emp': e, 'hours': total_hours, 'salary': salary})
        return render(request, 'dashboard.html', {'data': data, 'is_hr': True, 'emp': emp})
    else:
        atts = Attendance.objects.filter(employee=emp, date__gte=month_start, date__lte=today).order_by('-date')
        total_hours = sum((a.total_hours or 0) for a in atts if a.sign_out)
        salary = float(total_hours) * float(emp.hourly_rate)
        return render(request, 'dashboard.html', {'atts': atts, 'hours': total_hours, 'salary': salary, 'is_hr': False, 'emp': emp})

@login_required
@user_passes_test(is_hr)
def employee_list(request):
    employees = Employee.objects.all()
    return render(request, 'employee_list.html', {'employees': employees})

@login_required
@user_passes_test(is_hr)
def employee_add(request):
    if request.method == 'POST':
        user = User.objects.create_user(
            username=request.POST['username'],
            password=request.POST['password'],
            first_name=request.POST['first_name'],
            last_name=request.POST['last_name']
        )
        Employee.objects.create(
            user=user,
            employee_id=request.POST['employee_id'],
            phone=request.POST['phone'],
            department=request.POST['department'],
            role=request.POST['role'],
            hourly_rate=request.POST['hourly_rate']
        )
        return redirect('employee_list')
    return render(request, 'employee_form.html', {'action': 'Add'})

@login_required
@user_passes_test(is_hr)
def employee_edit(request, pk):
    emp = get_object_or_404(Employee, pk=pk)
    if request.method == 'POST':
        emp.user.first_name = request.POST['first_name']
        emp.user.last_name = request.POST['last_name']
        emp.user.save()
        emp.phone = request.POST['phone']
        emp.department = request.POST['department']
        emp.role = request.POST['role']
        emp.hourly_rate = request.POST['hourly_rate']
        emp.save()
        return redirect('employee_list')
    return render(request, 'employee_form.html', {'emp': emp, 'action': 'Edit'})

@login_required
@user_passes_test(is_hr)
def employee_delete(request, pk):
    emp = get_object_or_404(Employee, pk=pk)
    emp.user.delete()
    emp.delete()
    return redirect('employee_list')

@login_required
def attendance_view(request):
    emp = request.user.employee
    if emp.role == 'HR':
        records = Attendance.objects.all().order_by('-date', '-sign_in')
    else:
        records = Attendance.objects.filter(employee=emp).order_by('-date', '-sign_in')
    today_att = Attendance.objects.filter(employee=emp, date=date.today()).first()
    return render(request, 'attendance_list.html', {'records': records, 'today_att': today_att, 'emp': emp})

@login_required
def sign_in(request):
    emp = request.user.employee
    now = timezone.localtime()
    att, created = Attendance.objects.get_or_create(employee=emp, date=now.date(), defaults={'sign_in': now.time()})
    if not created:
        att.sign_in = now.time()
        att.sign_out = None
        att.total_hours = None
        att.save()
    return redirect('attendance')

@login_required
def sign_out(request):
    emp = request.user.employee
    att = Attendance.objects.filter(employee=emp, date=date.today()).first()
    if att:
        now = timezone.localtime()
        att.sign_out = now.time()
        # Calculate hours
        fmt = "%H:%M:%S"
        delta = datetime.combine(date.today(), att.sign_out) - datetime.combine(date.today(), att.sign_in)
        att.total_hours = Decimal(str(round(delta.total_seconds() / 3600, 2)))
        att.save()
    return redirect('attendance')
