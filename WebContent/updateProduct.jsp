<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Update Product</title>
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
			<h2>Update Product</h2>

		</header>
		<main>
		<c:if test="${!empty errors}">
			<div class="alert-danger">
				<ul>
					<c:forEach var="error" items="${errors}">
						<li>${error}</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<form method="post" action="" novalidate="novalidate">
			<!-- novalidate in order to be able to run tests correctly -->
			<p>
				<label for="productId">ID</label><input type="text"
					id="productId" name="productId" value="${fn:escapeXml(product.productId)}" required disabled>
			</p>			<p>
				<label for="name">Name</label><input type="text"
					id="name" name="name"  value="${fn:escapeXml(product.name)}" required>
			</p>
			<p>
				<label for="description">Description</label><input type="text"
					id="description" name="description" value="${fn:escapeXml(product.description)}" required>
			</p>
			<p>
				<label for="price">Price</label><input type="text" id="price"
					name="price" value="${fn:escapeXml(product.price)}" required>
			</p>
			<p>
				<input type="submit" id="updateProduct" value="Update Product">
			</p>

		</form>
		</main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>
