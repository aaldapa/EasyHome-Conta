//Aplica los estilos a los mensajes de error que aparecen en las etiquetas h:message con la clase .has-error.
//Mostrando el texto del error en rojo y recuadrando la caja de texto a la que pertenece el h:message
function estilarMessages(){
	$.each($('.has-error'),function (id,obj){
		var $icon=$("<i/>").addClass('fa fa-times-circle-o').css('cursor','pointer');
		$icon.append("&nbsp;");
		$(obj).addClass('control-label');
		$(obj).prepend($icon);
		$(obj).parent().addClass('has-error');
		
		//Si se muestran los mensajes encima de la caja de texto
		if ($(obj).parent().parent().hasClass('form-group')){
			$(obj).parent().parent().addClass('has-error');
		};
	});		
}

//Oculta mensaje y quita el estilo al hacer click
function ocultarMessage(){
	$(this).parent().hide();
  	//$(this).parent().parent().removeClass('has-error');
  	//Si se muestran los mensajes encima de la caja de texto
  	//$(this).parent().parent().parent().removeClass('has-error');
  	
	
}