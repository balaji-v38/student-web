<!DOCTYPE html>
<html>

<head>
	<title>Update TEACHER</title>

	<link type="text/css" rel="stylesheet" href="css/nstyle.css">
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css">	
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>STANFORD UNIVERSITY</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Update TEACHER</h3>
		
		<form action="TeacherControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />

			<input type="hidden" name="teacherId" value="${THE_TEACHER.id}" />
			
			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" 
								   value="${THE_TEACHER.firstName}" /></td>
					</tr>

					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" 
								   value="${THE_TEACHER.lastName}" /></td>
					</tr>

					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" 
								   value="${THE_TEACHER.email}" /></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="TeacherControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>











