<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Overview</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div id="container">
		<header>
			<h1>
				<span>Web shop</span>
			</h1>
			<nav>
				<ul>
					<li><a href="Controller">Home</a></li>
					<li id="actual"><a href="Controller?action=overview">Users</a></li>
					<li><a href="Controller?action=products">Products</a></li>
					<li><a href="Controller?action=addProduct">Add Product</a></li>
					<li><a href="Controller?action=signUp">Sign up</a></li>
				</ul>
			</nav>
			<h2>User Overview</h2>

		</header>
		<main>
		<table>
			<tr>
				<th>E-mail</th>
				<th>First Name</th>
				<th>Last Name</th>
			</tr>
			<c:forEach var="user" items="${users}">
				<tr>
					<td>${fn:escapeXml(user.email)}</td>
					<td>${fn:escapeXml(user.firstName)}</td>
					<td>${fn:escapeXml(user.lastName)}</td>
					<td><a href="Controller?action=deletePerson&userid=${fn:escapeXml(user.userid)}" title="Delete User">Delete</a></td>
					<td><a href="Controller?action=checkPassword&userid=${fn:escapeXml(user.userid)}" title="Check Password">Check Password</a></td>
				</tr>
			</c:forEach>

			<caption>Users Overview</caption>
		</table>
		</main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>