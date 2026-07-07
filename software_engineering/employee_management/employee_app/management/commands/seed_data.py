from django.core.management.base import BaseCommand
from django.contrib.auth.models import User
from employee_app.models import Employee, Attendance, Holiday
from django.utils import timezone
from datetime import date, time, timedelta
from decimal import Decimal

class Command(BaseCommand):
    help = 'Seeds database with dummy Indian employees and sample data'

    def handle(self, *args, **options):
        # Create HR
        hr_user, _ = User.objects.get_or_create(username='hr_manager', defaults={
            'first_name': 'Rahul', 'last_name': 'Sharma', 'is_staff': True
        })
        hr_user.set_password('hr123')
        hr_user.save()
        Employee.objects.get_or_create(
            user=hr_user,
            defaults={
                'employee_id': 'HR001',
                'phone': '9876543210',
                'department': 'Human Resources',
                'role': 'HR',
                'hourly_rate': 1000.00
            }
        )

        employees_data = [
            ('emp_ravi', 'Ravi', 'Kumar', 'EMP001', 'Food & Beverages', 500.00),
            ('emp_priya', 'Priya', 'Singh', 'EMP002', 'Housekeeping', 450.00),
            ('emp_amit', 'Amit', 'Patel', 'EMP003', 'Front Office', 550.00),
            ('emp_sneha', 'Sneha', 'Reddy', 'EMP004', 'Sales & Marketing', 600.00),
            ('emp_vikram', 'Vikram', 'Joshi', 'EMP005', 'IT Support', 650.00),
            ('emp_ananya', 'Ananya', 'Gupta', 'EMP006', 'Accounts', 550.00),
        ]

        for username, first, last, emp_id, dept, rate in employees_data:
            user, _ = User.objects.get_or_create(username=username, defaults={
                'first_name': first, 'last_name': last
            })
            user.set_password('emp123')
            user.save()
            Employee.objects.get_or_create(
                user=user,
                defaults={
                    'employee_id': emp_id,
                    'phone': f'98{emp_id[3:]}',
                    'department': dept,
                    'role': 'EMP',
                    'hourly_rate': rate
                }
            )

        # Sample holidays
        holidays = [
            ('Republic Day', date(2026, 1, 26)),
            ('Holi', date(2026, 3, 6)),
            ('Independence Day', date(2026, 8, 15)),
            ('Diwali', date(2026, 10, 31)),
            ('Christmas', date(2026, 12, 25)),
        ]
        for name, dt in holidays:
            Holiday.objects.get_or_create(name=name, date=dt)

        # Sample attendance for last 5 days
        employees = Employee.objects.all()
        for emp in employees:
            for i in range(5):
                d = date.today() - timedelta(days=i + 1)
                if not Attendance.objects.filter(employee=emp, date=d).exists():
                    Attendance.objects.create(
                        employee=emp,
                        date=d,
                        sign_in=time(9, 0, 0),
                        sign_out=time(17, 30, 0),
                        total_hours=Decimal('8.50')
                    )

        self.stdout.write(self.style.SUCCESS('Sample data created successfully!'))
        self.stdout.write(f'HR Login: username=hr_manager, password=hr123')
        self.stdout.write(f'Employee Login: username=emp_ravi (etc.), password=emp123')
