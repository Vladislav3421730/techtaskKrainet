# techtaskKrainet
Примеры для входа приведены в классе AuthController.java<br>
Пример входа в систему по адресу ```http://localhost:8080/login```<br>
```bash
{
    "username" : "your_username",
    "password" : "your_password"
}
```
Пример добавления пользователя по адресу 
```bash http://localhost:8080/registration```
<br>

```bash
{
    "username" : "your_username",
    "password" : "your_password",
    "name" : "your_name",
    "surname" : "your_surname"
}
```
Пример добавления записи по адресу ```bash http://localhost:8080/record/add``` <br>
```bash
{
    "startTime": "2024-10-24 09:07:47",
    "endTime": "2024-10-25 09:07:47",
    "projectId" : 3 
}
```
Пример добавления проекта по адресу ```bash http://localhost:8080/project/add``` <br>
```bash
{
     "name": "project_name",
     "description": "project_description",
     "cost": 1500.34,
     "createdAt": "2024-10-24",
}
```
Найти пользвоателей<br>
```bash
http://localhost:8080/user/get
http://localhost:8080/user/get/1
```
Найти проекты<br>
```bash
http://localhost:8080/project/get
http://localhost:8080/project/get/1
```
Найти записи <br>
```bash
http://localhost:8080/record/get
http://localhost:8080/record/get/1
```
Пример удаления пользователя <br>
```bash
http://localhost:8080/user/delete/1
```
Пример удаления проекта <br>
```bash
http://localhost:8080/project/delete/1
```
Пример удаления записи <br>
```bash
http://localhost:8080/record/delete/1
```
Пример обновлений (Необходимо вставить полное тело записи, которую вы хотите изменить) <br>
```
http://localhost:8080/user/update
http://localhost:8080/record/update
http://localhost:8080/project/update
```
