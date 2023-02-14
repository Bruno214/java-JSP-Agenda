/**
 * Confirmar a exclusao de um dado
 * @author Bruno da silva
 * @param idCont
 */

function confirmar(idCont) {
	let resposta = confirm("Tem certeza que deseja excluir? ");
	if (resposta === true) {
		window.location.href = "delete?idCont=" + idCont;
	}

}