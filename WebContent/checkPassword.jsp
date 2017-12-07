<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Check User Password</title>
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
			<h2>Check Password</h2>

		</header>
		<main>
		<c:if test="${!empty message}">
			<p>${message}</p>
		</c:if>
		
		<form method="post" action="" novalidate="novalidate">
			<!-- novalidate in order to be able to run tests correctly -->
			<p>Fill out your password:</p>
			<p>
				<input type="hidden" name="userid" value="${fn:escapeXml(user.userid)}">
				<label for="password">Password</label><input type="password"
					id="password" name="password" required>
			</p>
			<p>
				<input type="submit" id="check" value="Check">
			</p>

		</form>
		</main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>
