<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.JavaBeans"%>

<%
JavaBeans novoContato = (JavaBeans) request.getAttribute("contatoSelecionado");
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Editar Contato</title>
<link rel="stylesheet" href="style.css">
<link type="icon" href="imagens/favicon.png">
</head>
<body>
	<h1>Editar Contato</h1>

	<!--Setando os valores dinamicamente com o java -->
	<form action="update" name="frmContato" class="container"
		onsubmit="event.preventDefault()">
		<input title="campo não pode ser alterado" type="text" name="idcon"
			class="container_flex" id="estilo_campo_inalteravel" readonly
			value="<%out.println(novoContato.getId());%>"> <input
			type="text" name="nome" class="container_flex input_estilo"
			value="<%out.println(novoContato.getNome());%>"> <input
			type="text" name="fone"
			class="container_flex input_estilo input_estilo_curto"
			value=<%out.println(novoContato.getTelefone());%>> <input
			type="text" name="email" class="container_flex input_estilo"
			value="<%out.println(novoContato.getEmail());%>">

		<button type="submit" class="botao botao_salvar" onclick="validar()">Salvar</button>

	</form>

	<script src="scripts/validador.js"></script>
</body>
</html>