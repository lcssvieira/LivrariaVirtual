function carregaTabelaObras() {
	$.post("ServletMuseu.do", {
		metodo : "listarObras",
		classe : "ObrasControl"
	}, function(data) {
		document.open();
		document.write(data);
		document.close();
	});
}

function deletar(id, classe) {
	$.post("ServletMuseu.do", {
		metodo : "deletar",
		classe : classe,
		id : id,
	}, function(data) {
		document.open();
		document.write(data);
		document.close();
	});
}

function editar(id, classe) {
	$.post("ServletMuseu.do", {
		metodo : "editar",
		classe : classe,
		id : id,
	}, function(data) {
		document.open();
		document.write(data);
		document.close();
	});
}