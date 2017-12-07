<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Delete User Confirmation</title>
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
					<li><a href="Controller?action=overview">Users</a></li>
					<li><a href="Controller?action=products">Products</a></li>
					<li><a href="Controller?action=addProduct">Add Product</a></li>
					<li><a href="Controller?action=signUp">Sign up</a></li>
				</ul>
			</nav>
			<h2>Delete User Confirmation</h2>

		</header>
		<main>
		<h2>Are you sure you want to delete the user with id ${fn:escapeXml(user.userid)}</h2>
		<form method="post" action="Controller?action=deletePersonConfirm" novalidate="novalidate">
			<!-- novalidate in order to be able to run tests correctly -->
			<p>
				<input type="hidden" name="userid" value="${fn:escapeXml(user.userid)}">
				<input name="delete" type="submit" id="delete" value="Yes">
				<input name="delete" type="submit" id="delete" value="No">
			</p>

		</form>
		</main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>