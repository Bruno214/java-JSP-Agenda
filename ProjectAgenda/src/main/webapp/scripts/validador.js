/**
 * Validador dos campos de um fomulario
 * @author Bruno da silva
 */

function validar() {
	let nome = frmContato.nome.value;
	let telefone = frmContato.fone.value;

	if (nome === "") {
		alert("Preencha o campo nome!");
		frmContato.nome.focus();
		return false;
	} else if (telefone === "") {
		alert("Preencha o campo telefone!")
		frmContato.fone.focus();
		return false;
	} else {
		document.forms["frmContato"].submit();
	}

}