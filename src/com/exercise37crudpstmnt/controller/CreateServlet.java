package com.exercise37crudpstmnt.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.exercise37crudpstmnt.model.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
/**
 * Servlet that allows me to create an Employee into mysql database
 * 
 * @author oscar 
 * @version 1.0
 * 
 */
@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Post method that allows me to create the employee
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html charset='utf-8'");
		PrintWriter output = response.getWriter();
		
		Employee myEmployee = new Employee();
		
		//myEmployee.setIdEmployee(Integer.parseInt(request.getParameter("txtIdEmployee")));
		myEmployee.setNameEmployee(request.getParameter("txtNameEmployee"));
		myEmployee.setAgeEmployee(Integer.parseInt(request.getParameter("txtAgeEmployee")));
		myEmployee.setAddressEmployee(request.getParameter("txtAddressEmployee"));
		myEmployee.setSalaryEmployee(Double.parseDouble(request.getParameter("txtSalaryEmployee")));
		myEmployee.setDepartmentEmployee(request.getParameter("txtDepartmentEmployee"));
		
		//1. Declarar variables
		String urlServer="jdbc:mysql://localhost:3306/tiendita?useSSL=false&serverTimezone=UTC";
		String username = "root";
		String password = "root"; 
		int rowsAffected=0;
		String sentenciaSQLStatement = "INSERT INTO Empleados (nombreEmpleado, edadEmpleado, domicilioEmpleado, salarioEmpleado, departamentoEmpleado) VALUES ('"+myEmployee.getNameEmployee()+"', "+myEmployee.getAgeEmployee()+", '"+myEmployee.getAddressEmployee()+"',"+myEmployee.getSalaryEmployee()+", '"+myEmployee.getDepartmentEmployee()+"')";
		String sentenciaSQLPreparedStatements = 
				"INSERT INTO Empleados (nombreEmpleado, edadEmpleado, domicilioEmpleado, salarioEmpleado, departamentoEmpleado) VALUES (?,?,?,?,?)";
		
		
		//2. Declarar objetos
		Connection conn=null;
		PreparedStatement pstmnt = null;
		
		try
		{
			//3. Instanciamos el driver
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			
			//4. Abrimos la conexión
			conn = DriverManager.getConnection(urlServer, username, password);
			
			//5. Configuramos el prepared statement
			pstmnt = conn.prepareStatement(sentenciaSQLPreparedStatements);
			pstmnt.setString(1, myEmployee.getNameEmployee());
			pstmnt.setInt(2, myEmployee.getAgeEmployee());
			pstmnt.setString(3, myEmployee.getAddressEmployee());
			pstmnt.setDouble(4, myEmployee.getSalaryEmployee());
			pstmnt.setString(5, myEmployee.getDepartmentEmployee());
			
			//6. Ejecutamos la query
			rowsAffected = pstmnt.executeUpdate();
			if(rowsAffected>0)
			{
				output.append("Registro Añadido con éxito!!");
			}
			else
			{
				output.append("Registro NO fue añadido con éxito!!!");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				pstmnt.close();
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		output.close();
	}
}
