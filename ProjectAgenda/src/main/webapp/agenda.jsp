<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.JavaBeans"%>
<%@ page import="java.util.ArrayList"%>

<%
@SuppressWarnings("unchecked")
ArrayList<JavaBeans> listaContatos = (ArrayList<JavaBeans>) request.getAttribute("contatos");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Agenda</title>
<link rel="stylesheet" href="style.css">
<link rel="icon" href="imagens/favicon.png">
</head>
<body>
	<h1>Agenda de Contatos</h1>
	<a href="novo.html" class="botao">Novo Contato</a>
	<a href="report" class="botao corRed">Relatório</a>

	<table id="estilo_tabela">
		<thead>
			<tr>
				<td>Id</td>
				<td>Nome</td>
				<td>Fone</td>
				<td>Email</td>
				<td colspan="2">Opções</td>
			</tr>
		</thead>

		<tbody>
			<%
			for (int i = 0; i < listaContatos.size(); i++) {
			%>
			<tr>
				<td><%=listaContatos.get(i).getId()%></td>
				<td><%=listaContatos.get(i).getNome()%></td>
				<td><%=listaContatos.get(i).getTelefone()%></td>
				<td><%=listaContatos.get(i).getEmail()%></td>
				<td><a href="editar?idcon=<%=listaContatos.get(i).getId()%>"
					class="formatar_acao corBlue">Editar</a></td>
				<td><a
					href="javaScript:confirmar(<%=listaContatos.get(i).getId()%>)"
					class="formatar_acao corRed">Excluir</a></td>
			</tr>

			<%
			}
			%>

		</tbody>



	</table>
	<script src="scripts/confirmador.js"></script>
</body>