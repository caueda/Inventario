<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <base href="http://<%= request.getLocalName() + ":" + request.getLocalPort() + request.getContextPath() + "/" %>">
  <script src="resources/jQuery/jquery-1.12.1.min.js"></script>
  <script src="resources/jQuery/jquery-ui-1.11.4/jquery-ui.min.js"></script>
  <script src="resources/jQuery/validate-1.1.2/jquery.validate.min.js"></script>
  
  <title>Login Inventário</title>
  <link rel="stylesheet" href="resources/login/css/style.css">
  <link rel="stylesheet" href="resources/jQuery/jquery-ui-1.11.4/jquery-ui.css">
  <link rel="stylesheet" href="resources/jQuery/jquery-ui-1.11.4/jquery-ui.theme.css">
  <link rel="stylesheet" href="resources/jQuery/jquery-ui-1.11.4/jquery-ui.structure.css">
  
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
  <script>
  	var queryString = function () {
	  // This function is anonymous, is executed immediately and 
	  // the return value is assigned to QueryString!
	  var query_string = {};
	  var query = window.location.search.substring(1);
	  var vars = query.split("&");
	  for (var i=0;i<vars.length;i++) {
	    var pair = vars[i].split("=");
	        // If first entry with this name
	    if (typeof query_string[pair[0]] === "undefined") {
	      query_string[pair[0]] = decodeURIComponent(pair[1]);
	        // If second entry with this name
	    } else if (typeof query_string[pair[0]] === "string") {
	      var arr = [ query_string[pair[0]],decodeURIComponent(pair[1]) ];
	      query_string[pair[0]] = arr;
	        // If third or later entry with this name
	    } else {
	      query_string[pair[0]].push(decodeURIComponent(pair[1]));
	    }
	  } 
	  return query_string;
	}();
	
	function validate(){
		var msg = $("#messageDialog");
		var message = "";
		if($("#login").val() == ""){
			message += "<li>O login é obrigatório.</li>";
		}
		if($("#senha").val() == ""){
			message += "<li>A senha é obrigatória.</li>";
		}
		
		if(message != ""){
			msg.html("<ul>" + message + "</ul>");
			msg.dialog();			
			return false;
		} else {			
			return true;
		}
	}
	
	$(document).ready(function(){
		var msg = $("#messageDialog");
		if(queryString.mensagem){
			msg.html(queryString.mensagem.replace(/\+/g,' '));			
			msg.dialog();
			return;
		}		
	});
  </script>
</head>
<body>
  <form method="post" action="<%= request.getContextPath() %>/autenticar" class="login">
    <p>
      <label for="login">Email:</label>
      <input type="text" name="login" id="login" value="">
    </p>

    <p>
      <label for="senha">Password:</label>
      <input type="password" name="senha" id="senha" value="">
    </p>

    <p class="login-submit">
      <button id="btnSubmit" type="submit" class="login-button" onclick="return validate()">Login</button>
    </p>
  </form>
  
  <div id="messageDialog" title="Basic dialog" style="display: none;">
  	<p><%= request.getAttribute("mensagem") %></p>
  </div>
</body>
</html>