package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeServlet extends HttpServlet {

    // Change these to match your MySQL setup.
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "YOUR_PASSWORD";

    private Connection connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // GET /employee - display all employees as an HTML table.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter();
             Connection con = connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Employees")) {
            out.println("<html><body><h2>Employee Details</h2>");
            out.println("<table border='1' cellpadding='8'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Age</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id") + "</td><td>"
                    + escapeHtml(rs.getString("name")) + "</td><td>"
                    + rs.getInt("age") + "</td></tr>");
            }
            out.println("</table><p><a href='index.html'>Back to form</a></p></body></html>");
        } catch (Exception e) {
            throw new ServletException("Unable to read employees", e);
        }
    }

    // POST /employee - add an employee from the HTML form or Postman.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                 "INSERT INTO Employees (id, name, age) VALUES (?, ?, ?)")) {
            ps.setInt(1, Integer.parseInt(req.getParameter("id")));
            ps.setString(2, req.getParameter("name"));
            ps.setInt(3, Integer.parseInt(req.getParameter("age")));
            ps.executeUpdate();
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("Employee Added! <a href='employee'>View Employees</a>");
        } catch (Exception e) {
            throw new ServletException("Unable to add employee", e);
        }
    }

    // PUT /employee?id=101&name=NewName&age=22 - use Postman query parameters.
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE Employees SET name=?, age=? WHERE id=?")) {
            ps.setString(1, req.getParameter("name"));
            ps.setInt(2, Integer.parseInt(req.getParameter("age")));
            ps.setInt(3, Integer.parseInt(req.getParameter("id")));
            int rows = ps.executeUpdate();
            resp.getWriter().println(rows > 0 ? "Employee Updated" : "Employee Not Found");
        } catch (Exception e) {
            throw new ServletException("Unable to update employee", e);
        }
    }

    // DELETE /employee?id=101 - use Postman query parameters.
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                 "DELETE FROM Employees WHERE id=?")) {
            ps.setInt(1, Integer.parseInt(req.getParameter("id")));
            int rows = ps.executeUpdate();
            resp.getWriter().println(rows > 0 ? "Employee Deleted" : "Employee Not Found");
        } catch (Exception e) {
            throw new ServletException("Unable to delete employee", e);
        }
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
