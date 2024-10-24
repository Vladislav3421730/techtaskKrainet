# techtaskKrainet
Примеры для входа приведены в классе AuthController.java<br>
Пример добавления пользователя по адресу <br>
```bash http://localhost:8080/user/add```bash
<br>

```bash
{
    "username" : "your_username",
    "password" : "your_password",
    "name" : "your_name",
    "surname" : "your_surname"
}
```<br>
Пример добавления записи по адресу ```bash http://localhost:8080/record/add``` <br>
```bash
{
    "startTime": "2024-10-24 09:07:47",
    "endTime": "2024-10-25 09:07:47",
    "projectId" : 3 
}
```<br>
Пример добавления проекта по адресу ```bash http://localhost:8080/project/add``` <br>
```bash
{
     "name": "project_name",
     "description": "project_description",
     "cost": 1500.34,
     "createdAt": "2024-10-24",
}
```<br>
Найти пользвоателей<br>
```bash
http://localhost:8080/user/get
http://localhost:8080/user/get/1
```<br>
Найти проекты<br>
```bash
http://localhost:8080/project/get
http://localhost:8080/project/get/1
```<br>
Найти записи <br>
```bash
http://localhost:8080/record/get
http://localhost:8080/record/get/1
```<br>
Пример удаления пользователя <br>
```bash
http://localhost:8080/user/delete/1
```<br>
Пример удаления проекта <br>
```bash
http://localhost:8080/project/delete/1
```<br>
Пример удаления записи <br>
```bash
http://localhost:8080/record/delete/1
```<br>
Пример обновлений (Необходимо вставить полное тело записи, которую вы хотите изменить) <br>
```bash
http://localhost:8080/user/update
http://localhost:8080/record/update
http://localhost:8080/project/update
```<br>
