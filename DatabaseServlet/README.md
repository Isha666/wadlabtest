# DatabaseServlet — Servlet + JDBC + MySQL (Tomcat 9)

## Requirements
- JDK 8 or newer (JDK 17 recommended)
- Maven (`mvn -version`)
- MySQL Server and optionally MySQL Workbench
- **Apache Tomcat 9** (not Tomcat 10/11: this project uses `javax.servlet`)

## Step 1: Set up the database
Start MySQL. In MySQL Workbench, open `sql/setup.sql` and execute it.
Or in Terminal: `mysql -u root -p < sql/setup.sql`.

## Step 2: Enter your MySQL password
Open `src/main/java/com/example/servlet/EmployeeServlet.java` and replace `YOUR_PASSWORD` with your MySQL root password. Change `USER` or `URL` if your setup differs.

## Step 3: Build the WAR
In the DatabaseServlet project folder run:

```sh
mvn clean package
```

The output is `target/DatabaseServlet.war`. Maven automatically includes the MySQL JDBC driver in `WEB-INF/lib`; you do not need to copy a JAR manually.

## Step 4: Deploy to Tomcat 9
1. Download/unzip Apache Tomcat 9 (if not installed).
2. Copy `target/DatabaseServlet.war` to Tomcat's `webapps/` folder.
3. Start Tomcat using `bin/startup.sh` on macOS/Linux (`chmod +x bin/*.sh` if needed) or `bin/startup.bat` on Windows.
4. Visit `http://localhost:8080/DatabaseServlet/`.
5. Use **Add Employee** or **Show All Employees**.

If port 8080 is occupied by Spring Boot, stop Spring Boot first or change Tomcat's port in `conf/server.xml`.

## Step 5: Test all four HTTP methods
Use Postman:

| Method | URL | Action |
|---|---|---|
| GET | `http://localhost:8080/DatabaseServlet/employee` | Show employee table |
| POST | `http://localhost:8080/DatabaseServlet/employee` | Body -> x-www-form-urlencoded: `id=103`, `name=Anu`, `age=22` |
| PUT | `http://localhost:8080/DatabaseServlet/employee?id=103&name=Ananya&age=23` | Update employee |
| DELETE | `http://localhost:8080/DatabaseServlet/employee?id=103` | Delete employee |

**Important:** PUT and DELETE take their parameters from the URL query string. Standard HTML forms only support GET and POST.

## Project structure
```
DatabaseServlet/
  pom.xml
  sql/setup.sql
  src/main/java/com/example/servlet/EmployeeServlet.java
  src/main/webapp/index.html
  src/main/webapp/WEB-INF/web.xml
```

## How it works
`index.html` -> Tomcat `/employee` mapping (`web.xml`) -> `EmployeeServlet` (`doGet` / `doPost` / `doPut` / `doDelete`) -> JDBC (`DriverManager`, `PreparedStatement`) -> MySQL `test.Employees`.

## Troubleshooting
- `Access denied for user 'root'`: check password and username in EmployeeServlet.java.
- `Communications link failure`: MySQL is not running or wrong port.
- `404`: verify WAR was deployed as `DatabaseServlet.war` and Tomcat is running.
- `ClassNotFoundException javax.servlet`: use Tomcat **9**, not Tomcat 10/11.
- `mvn: command not found`: install Maven or build/deploy as an Eclipse Dynamic Web Project with MySQL Connector/J in `WEB-INF/lib`.
