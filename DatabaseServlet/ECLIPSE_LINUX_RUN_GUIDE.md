# Run DatabaseServlet on a Linux lab computer using Eclipse

This project uses **Java Servlets (`javax.servlet`) + JDBC + MySQL + Apache Tomcat 9**. It is **not** a Spring Boot project.

## 1. Extract the ZIP
Extract `DatabaseServlet_JDBC_Tomcat9_Eclipse_Guide.zip`. The project folder is `DatabaseServlet`, containing `pom.xml`.

## 2. Check prerequisites
- Eclipse IDE with Enterprise Java / Web Tools support
- JDK (check with `java -version`)
- Apache Tomcat **9** (Tomcat is separate from Eclipse; it must be installed/configured)
- MySQL server and a MySQL account
- Maven support in Eclipse (m2e)

## 3. Create the database
Start MySQL. Open MySQL Workbench or a terminal and execute `sql/setup.sql`.

From a terminal inside the project folder:

```bash
mysql -u root -p < sql/setup.sql
```

Enter the MySQL password when prompted. This creates database `test`, table `Employees`, and sample rows. If your lab uses a different MySQL username, use it instead of `root`.

## 4. Set the database credentials
Open `src/main/java/com/example/servlet/EmployeeServlet.java` and locate:

```java
private static final String URL = "jdbc:mysql://localhost:3306/test";
private static final String USER = "root";
private static final String PASSWORD = "YOUR_PASSWORD";
```

Replace `YOUR_PASSWORD` and, if needed, the username, host, port, or database name. Do this **before** running the application.

## 5. Import into Eclipse
1. Launch Eclipse.
2. Choose **File > Import...**.
3. Select **Maven > Existing Maven Projects** and click **Next**.
4. Select the extracted `DatabaseServlet` folder (the one containing `pom.xml`).
5. Confirm the detected `pom.xml`, then click **Finish**.
6. Wait for dependencies to download. If needed, right-click the project > **Maven > Update Project...**.

## 6. Set up Tomcat 9 in Eclipse
1. Go to **Window > Show View > Other... > Server > Servers** (or **Window > Show View > Servers**, if available).
2. In the Servers view, click the link to create a server, or right-click > **New > Server**.
3. Select **Apache > Tomcat v9.0 Server** and click **Next**.
4. Browse to the installed Tomcat 9 folder (the folder containing `bin`, `conf`, and `webapps`).
5. Select a compatible JRE/JDK if requested, then click **Finish**.

If Tomcat is already listed in the Servers view, use it instead. If the **Tomcat v9.0** option is missing, the lab may lack Eclipse Web Tools. If Tomcat itself is missing, ask the lab administrator for its installation path. **Tomcat 10+ is not directly compatible with this project's `javax.servlet` code.**

## 7. Run on Server
1. Right-click the **DatabaseServlet** project in Eclipse's Project Explorer.
2. Select **Run As > Run on Server**.
3. Choose **Tomcat v9.0 Server** and click **Finish**.
4. If prompted, add `DatabaseServlet` to the configured server.
5. Wait for the server to start and deploy the project.

No manual WAR copy to Tomcat's `webapps` folder is necessary when Eclipse deploys it for you.

## 8. Open the app
Visit:

- Form: **http://localhost:8080/DatabaseServlet/**
- Employee list: **http://localhost:8080/DatabaseServlet/employee**

The HTML form submits a POST to add an employee. The list link sends GET to display the records.

## 9. Test CRUD with Postman
Use the following requests (base URL: `http://localhost:8080`):

| Action | Method | Path | Inputs |
|---|---|---|---|
| Read employees | GET | `/DatabaseServlet/employee` | None |
| Add employee | POST | `/DatabaseServlet/employee` | Body > x-www-form-urlencoded: `id=103`, `name=Ananya`, `age=23` |
| Update employee | PUT | `/DatabaseServlet/employee?id=103&name=Ananya&age=24` | URL query parameters |
| Delete employee | DELETE | `/DatabaseServlet/employee?id=103` | URL query parameter |

For this lab's simple servlet implementation, **PUT and DELETE read parameters from the URL query string**, not a JSON body. Ordinary HTML forms support GET and POST, not PUT or DELETE.

## 10. Common problems
- **Run on Server missing:** Eclipse Web Tools / Enterprise Java support may not be installed. Check that the project is recognized as a web project.
- **Tomcat not found:** Eclipse does not automatically install Tomcat. Find the Tomcat 9 directory or ask the lab administrator.
- **Port 8080 already in use:** Stop the other application/server using 8080, or adjust Tomcat's port and use the new URL.
- **Database connection error:** Check MySQL is running, database/table exist, and credentials in `EmployeeServlet.java` are correct.
- **404 at `/DatabaseServlet/`:** Check the server has deployed the project and that its context path is `DatabaseServlet`.
- **Tomcat 10+:** This code uses `javax.servlet`; use Tomcat 9 or migrate code and dependencies to `jakarta.servlet`.

## What to remember for the lab
`index.html` -> Tomcat -> `web.xml` -> `EmployeeServlet` (`doGet`, `doPost`, `doPut`, `doDelete`) -> JDBC -> MySQL.

See `README.md` for more project details.
