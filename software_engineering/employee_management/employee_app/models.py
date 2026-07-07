from django.contrib.auth.models import User
from django.db import models
from django.utils import timezone

class Employee(models.Model):
    ROLE_CHOICES = [('HR', 'HR Manager'), ('EMP', 'Employee')]
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    employee_id = models.CharField(max_length=10, unique=True)
    phone = models.CharField(max_length=15)
    department = models.CharField(max_length=50)
    role = models.CharField(max_length=3, choices=ROLE_CHOICES, default='EMP')
    hourly_rate = models.DecimalField(max_digits=8, decimal_places=2, default=500.00)
    date_joined = models.DateField(default=timezone.now)

    def __str__(self):
        return f"{self.employee_id} - {self.user.get_full_name()}"

class Attendance(models.Model):
    employee = models.ForeignKey(Employee, on_delete=models.CASCADE)
    date = models.DateField(default=timezone.now)
    sign_in = models.TimeField(default=timezone.now)
    sign_out = models.TimeField(null=True, blank=True)
    total_hours = models.DecimalField(max_digits=5, decimal_places=2, null=True, blank=True)

    def __str__(self):
        return f"{self.employee.employee_id} - {self.date}"

class Holiday(models.Model):
    name = models.CharField(max_length=100)
    date = models.DateField(unique=True)

    def __str__(self):
        return f"{self.name} ({self.date})"
