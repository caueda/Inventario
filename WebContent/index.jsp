<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Sistema de Inventário</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<base href="http://<%= request.getLocalName() + ":" + request.getLocalPort() + request.getContextPath() + "/" %>">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">


	<link rel="stylesheet" href="resources/jQuery/jquery-ui-1.11.4/jquery-ui.min.css">
	<script type="text/javascript" src="resources/jQuery/jquery-1.12.1.min.js"></script>
	<script type="text/javascript" src="resources/jQuery/jquery-ui-1.11.4/jquery-ui.min.js"></script>

    <!-- Bootstrap -->
    <link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="resources/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
    <script src="resources/bootstrap/js/bootstrap.min.js"></script>
	
	<script>
		$(function(){
			window.location.href = "http://<%= request.getLocalName() + ":" + request.getLocalPort() + request.getContextPath() + "/login.curso" %>";
		});
	</script>
</head>
<body>
	<div class="container">
		<h1>Sistema de Inventário</h1>
		<p><font style="color:green;">Redirecionando para a página de login...</font></p>
	</div>
</body>
</html>