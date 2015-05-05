//Aplica los estilos a los mensajes de error que aparecen en las etiquetas h:message con la clase .has-error.
//Mostrando el texto del error en rojo y recuadrando la caja de texto a la que pertenece el h:message
function estilarMessages(){
	//Estilo cada <h:message.. de error
	$.each($('.has-error'),function (id,obj){
		
		//Creo un icono con el aspa y un espacio
		var $icon=$("<i/>").addClass('fa fa-times-circle-o').css('cursor','pointer');
		$icon.append("&nbsp;");
		
		
		//Añado el icon delante del texto de error
		$(obj).prepend($icon);
		
		//Añado el estilo al de error al div que contiene el input-text para remarcarlo en rojo
		$(obj).parent().addClass('has-error');
		
		//Si el padre de la etiqueta de error tiene la clase creada por mi:form-group-inline debo aplicar estilos a la label de la caja de texto 
		
		if ($(obj).parent().hasClass('form-group-inline')){
			//El elemento superior anterior al mensaje de error es la label 
			$abuelo=$(obj).parent().prev();
			//	La pongo en rojo el texto de la label
			$($abuelo).css('color','#dd4b39');
		}
		
	});		
}

//Oculta mensaje y quita el estilo al hacer click
function ocultarMessage(){
	$(this).parent().hide();
  	$(this).parent().parent().removeClass('has-error');
 	//Si se muestran los mensajes encima de la caja de texto, lo pongo en negro
  	if ($(this).parent().parent().hasClass('form-group-inline'))
  		$(this).parent().parent().prev().css('color','#333');
  	
}

//Mostrar ventana modal
function mostrarModal(){
	$('.modal').show();
}

//Ocultar ventana modal
function ocultarModal(){
	$('.modal').hide();
}

