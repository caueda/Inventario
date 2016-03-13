<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Sistema de Inventário</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<base href="http://<%= request.getLocalName() + ":" + request.getLocalPort() + request.getContextPath() + "/" %>">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">


	<link rel="stylesheet" href="scripts/jquery-ui-1.11.4/jquery-ui.min.css">
	<script type="text/javascript" src="scripts/jquery-1.12.1.min.js"></script>
	<script type="text/javascript" src="scripts/jquery-ui-1.11.4/jquery-ui.min.js"></script>

    <!-- Bootstrap -->
    <link href="style/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="style/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
    <script src="style/bootstrap/js/bootstrap.min.js"></script>
	
	<script>
		$(function(){
			$("#mensagem").dialog({width:500, height:150});
		});
	</script>
</head>
<body>
	<div class="container">
		<h1>Sistema de Inventário</h1>
		<div id="mensagem" title="Deploy realizado com sucesso!">
			<p>Parabéns. O deploy no Glassfish foi realizado com sucesso!!!</p>
		</div>
		<div>
			<ul class="nav nav-tabs">
				<li role="presentation" ><a href="home.curso">Home</a></li>
			</ul>
		</div>
	</div>
</body>
</html>