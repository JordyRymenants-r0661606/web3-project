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
					<li><a href="Controller?action=overview">Users</a></li>
					<li id="actual"><a href="Controller?action=products">Products</a></li>
					<li><a href="Controller?action=addProduct">Add Product</a></li>
					<li><a href="Controller?action=signUp">Sign up</a></li>
				</ul>
			</nav>
			<h2>Product Overview</h2>

		</header>
		<main>
		<table>
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th>Price</th>
			</tr>
			<c:forEach var="product" items="${products}">
				<tr>
					<td><a href="Controller?action=updateProduct&productId=${fn:escapeXml(product.productId)}" title="Update Product">${fn:escapeXml(product.name)}</a></td>
					<td>${fn:escapeXml(product.description)}</td>
					<td>&euro; ${fn:escapeXml(product.price)}</td>
					<td><a href="Controller?action=deleteProduct&productId=${fn:escapeXml(product.productId)}" title="Delete Product">Delete</a></td>
				</tr>
			</c:forEach>

			<caption>Products Overview</caption>
		</table>
		</main>
		<footer> &copy; Webontwikkeling 3, UC Leuven-Limburg </footer>
	</div>
</body>
</html>