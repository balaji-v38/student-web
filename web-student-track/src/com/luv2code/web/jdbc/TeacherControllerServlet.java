package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/TeacherControllerServlet")
public class TeacherControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TeacherDbUtil teacherDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our student db util ... and pass in the conn pool / datasource
		try {
			teacherDbUtil = new TeacherDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			// route to the appropriate method
			switch (theCommand) {
			
			case "LIST":
				listTeachers(request, response);
				break;
				
			case "ADD":
				addTeacher(request, response);
				break;
				
			case "LOAD":
				loadTeacher(request, response);
				break;
				
			case "UPDATE":
				updateTeacher(request, response);
				break;
			
			case "DELETE":
				deleteTeacher(request, response);
				break;
				
			default:
				listTeachers(request, response);
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
	}

	private void deleteTeacher(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read student id from form data
		String theTeacherId = request.getParameter("teacherId");
		
		// delete student from database
		teacherDbUtil.deleteTeacher(theTeacherId);
		
		// send them back to "list students" page
		listTeachers(request, response);
	}

	private void updateTeacher(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		// read student info from form data
		int id = Integer.parseInt(request.getParameter("teacherId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// create a new student object
		Teacher theTeacher = new Teacher(id, firstName, lastName, email);
		
		// perform update on database
		teacherDbUtil.updateTeacher(theTeacher);
		
		// send them back to the "list students" page
		listTeachers(request, response);
		
	}

	private void loadTeacher(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// read student id from form data
		String theTeacherId = request.getParameter("teacherId");
		
		// get student from database (db util)
		Teacher theTeacher = teacherDbUtil.getTeacher(theTeacherId);
		
		// place student in the request attribute
		request.setAttribute("THE_TEACHER", theTeacher);
		
		// send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-teacher-form.jsp");
		dispatcher.forward(request, response);		
	}

	private void addTeacher(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// read student info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");		
		
		// create a new student object
		Teacher  theTeacher = new Teacher(firstName, lastName, email);
		
		// add the student to the database
		teacherDbUtil.addTeacher(theTeacher);
				
		// send back to main page (the student list)
		listTeachers(request, response);
	}

	private void listTeachers(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// get students from db util
		List<Teacher> teachers = teacherDbUtil.getTeachers();
		
		// add students to the request
		request.setAttribute("TEACHER_LIST", teachers);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-teacher.jsp");
		dispatcher.forward(request, response);
	}

}













