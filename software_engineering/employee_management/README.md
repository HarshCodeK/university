# Employee Management System (HR Portal)

A simple Django-based HR Portal for managing employees, attendance, and salary.

## Features

- **Two Roles**: HR Manager (full access) and Employee (own attendance/profile)
- **Employee CRUD**: Add, edit, delete employees (6 Indian dummy employees included)
- **Attendance**: Daily Sign In / Sign Out with timestamp recording
- **Salary Calculation**: Based on working hours + hourly rate
- **Dashboard**: Total working days, salary, attendance report

## Demo Credentials

| Role     | Username    | Password |
|----------|-------------|----------|
| HR       | hr_manager  | hr123    |
| Employee | emp_ravi    | emp123   |
| Employee | emp_priya   | emp123   |
| Employee | emp_amit    | emp123   |
| Employee | emp_sneha   | emp123   |
| Employee | emp_vikram  | emp123   |
| Employee | emp_ananya  | emp123   |

## How to Run

```bash
# 1. Install Django
pip install -r requirements.txt

# 2. Run migrations
python manage.py makemigrations
python manage.py migrate

# 3. Seed sample data
python manage.py seed_data

# 4. Run server
python manage.py runserver

# 5. Open browser at http://127.0.0.1:8000
```

## Project Structure

```
employee_management/
├── manage.py
├── requirements.txt
├── hr_portal/         # Django project settings
├── employee_app/      # Main app (models, views, urls)
│   ├── models.py      # Employee, Attendance, Holiday
│   ├── views.py       # All view logic
│   └── management/commands/seed_data.py
├── templates/         # Bootstrap HTML templates
├── static/            # CSS files
└── README.md
```
