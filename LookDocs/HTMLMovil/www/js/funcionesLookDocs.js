// JavaScript Document

var ip = '127.0.0.1';
var path = 'http://'+ip+':8080/LookDocs/controller/lookDocs'
var usuario = window.localStorage.getItem("nombreUsuario");
var coleccion = window.localStorage.getItem("idColeccion");


function toRegistarLector(){
	window.location="RegistrarLector.html";
}

function toRecuperarClave(){
	window.location="RecuperarClave.html";
}

function toIndex(){
	window.location="Index.html";
}

function toFeedPrincipalLector(){
	window.location="FeedPrincipalLector.html";
}

function toIndexFromFeedPrincipal(){
 	window.localStorage.removeItem("nombreUsuario");
	window.location="Index.html";
}

function toGestionColecciones(){
	window.location="GestionarColecciones.html";
}

function toListarColecciones(){
	window.location="ListarColecciones.html";
}

function toListarCategorias(){
	window.location="ListarCategorias.html";
}

function toAgregarRSS(){
	window.location="AgregarRSS.html";
}

function toColecciones(){
	window.localStorage.removeItem("idColeccion");
	window.location="ListarColecciones.html";
}

function toListarRss(){
	window.localStorage.removeItem("codigoRss");
	window.location="ListarRSS.html";
}

function ingresar(){
	var username = $('#correo').val();
	var password = $('#passwordinput').val();

	$.post(path+'/login',{name:username,pass:password},function(retorno){
		if (retorno=="exito") {
			window.localStorage.setItem("nombreUsuario",username);
			toFeedPrincipalLector();
		}else{
			window.alert(retorno);
		};

	});
}

function registrar(){
	var nombreLector = $('#nombreLector').val();
	var correoLector = $('#correoLector').val();
	var passwordLector = $('#passwordLector').val();
	var passwordConfirma = $('#passwordConfirma').val();

	$.post(path+'/registrar',{nombre:nombreLector,correo:correoLector,pass:passwordLector,passConfirma:passwordConfirma},function(retorno){
		if (retorno=="exito") {
			alert("Se ha registrado exitosamente");
			toIndex();
		}else{
			alert(retorno);
		};

	});
}

function traerColecciones(){
	$.get(path+'/obtenercolecciones',{mail:usuario},function(data){
		if (data==null) {
			window.alert('El usuario no tiene colecciones');
		}else{
			$('#listaColecciones').empty().append('<option onclick="modificarColeccion()" value="0">Seleccione una Colección para guardar</option>').find('option:first').attr("selected","selected");
				if(data!=null){
					//Inserta los valores recuperados en la lista desplegable
					$.each(data, function(i, value) {
						$('#listaColecciones').append('<option value='+value.codigoCole+'>'+value.nombre+'</option>');
					});
				}
				//Actualiza la lista desplegable
				$('#listaColecciones').selectmenu("refresh", true);
		};
	});
}

function buscarRSS(){
	var laUrl = $('#txtUrlRss').val();

	$.get(path+'/buscarRss',{url:laUrl},function(data){
		if (data==null) {
			window.alert('No se ha encontrado el rss');
		}else{
			$('#listaNoticias').empty()
			if(data!=null){
				//Inserta los valores recuperados en la lista desplegable
				$.each(data, function(i, value) {
				$('#listaNoticias').append('<li><a data-icon="none">'+value.titulo+'</a></li>');
				});
			}
			//Actualiza la lista desplegable
			$('#listaNoticias').listview("refresh", true);
			traerColecciones();
		};
	});
}

function guardarRSS(){
	var laUrl = $('#txtUrlRss').val();
	var idCol = document.getElementById("listaColecciones").value;
	if(idCol==0){
		window.alert('Debe seleccionar una colección');
	}else{
		$.post(path+'/guardarRss',{url:laUrl,idColeccion:idCol},function(retorno){
			if (retorno=="rssGuardado") {
				window.alert('Se ha guardado el RSS ');
				$('#txtUrlRss').val("");
				$('#listaNoticias').empty();
				$('#listaColecciones').empty();
				toFeedPrincipalLector();
			}else{
				window.alert(retorno);
			};
		});
	}

}

function traerCategorias(){
	$.get(path+'/obtenercategorias',{},function(data){
		if (data==null) {
			window.alert('No hay categorías');
		}else{
			$('#listaCategorias').empty()
			if(data!=null){
				//Inserta los valores recuperados en la lista desplegable
				$.each(data, function(i, value) {
				$('#listaCategorias').append('<li><a onclick="abrirCategoria('+value.codigoCate+')" value='+value.codigoCate+'>'+value.nombre+'</a></li>');
				});
			}
			//Actualiza la lista desplegable
			$('#listaCategorias').listview("refresh", true);
		};
	});
}

function abrirCategoria(codigo){
	window.alert('Ha seleccionado la categoria '+codigo);
}

function traerColecciones(){
			$.get(path+'/obtenercolecciones',{mail:usuario},function(data){
			if (data==null) {
				window.alert('El usuario no tiene colecciones');
			}else{
				$('#listaColecciones').empty()
				if(data!=null){
				//Inserta los valores recuperados en la lista desplegable
				$.each(data, function(i, value) {
				$('#listaColecciones').append('<li><a onclick="abrirColeccion('+value.codigoCole+')" value='+value.codigoCole+'>'+value.nombre+'</a></li>');
				});}
				//Actualiza la lista desplegable
				$('#listaColecciones').listview("refresh", true);
			};
			});
}

function abrirColeccion(codigo){
			window.localStorage.setItem("idColeccion",codigo);
			//window.alert('Ha seleccionado la colección '+codigo);
			window.location="ListarRSS.html";
}


function abrirRss(codigo){
			//window.alert('Ha seleccionado el Rss '+codigo);
			window.localStorage.setItem("codigoRss",codigo);
			window.location="MostrarNoticias.html";
}

function traerRss(){
			$.get(path+'/obtenerRssDadoColeccion',{idColeccion:coleccion},function(data){
			if (data==null) {
				window.alert('La colección no tiene Rss');
			}else{
				$('#listaRss').empty()
				if(data!=null){
				//Inserta los valores recuperados en la lista desplegable
				$.each(data, function(i, value) {
				$('#listaRss').append('<li><a onclick="abrirRss('+value.codigoRss+')" value='+value.codigoRss+'>'+value.url+'</a></li>');
				});}
				//Actualiza la lista desplegable
				$('#listaRss').listview("refresh", true);
			};
			});
}

function buscarRSSNoticias(){
			var laUrl;
			var codRss = window.localStorage.getItem("codigoRss");
			$.get(path+'/consultarUrlRss',{idRss:codRss},function(data){
				laUrl = data;
				cargarUrl(laUrl);
			});
		
}
function cargarUrl(elLink){
			
			$.get(path+'/buscarRss',{url:elLink},function(data){
			if (data==null) {
				window.alert('No se ha noticias');
			}else{
				$('#mostrarFeeds').empty()
				if(data!=null){
				//Inserta los valores recuperados en la lista desplegable
				$.each(data, function(i, value) {
				$('#mostrarFeeds').append('<div data-role="collapsible" data-theme="a"><h4>'+
				value.titulo+'</h4><p>'+value.descripcion+'</p></div>');
				});}
				//Actualiza la lista desplegable
				$('#mostrarFeeds').fieldcontain("refresh", true);
			};
			});
}

function guardarColeccion(){
			var coleccionName = $('#nombreColeccion').val();
			
			$.post(path+'/crearColeccion',{correo:usuario,nombreColeccion:coleccionName},function(retorno){
			if (retorno=="exito") {
				window.alert('Se ha guardado la colección ' +coleccionName);
				$('#nombreColeccion').val("");
			}else{
				window.alert(retorno);
			};
			});
		}
		
		function traerColeccionesModificar(){
			$.get(path+'/obtenercolecciones',{mail:usuario},function(data){
			if (data==null) {
				window.alert('El usuario no tiene colecciones');
			}else{
				$('#listaColeccionesModificar').empty().append('<option onclick="modificarColeccion()" value="0">Seleccione una Colección</option>').find('option:first').attr("selected","selected");
				if(data!=null){
				//Inserta los valores recuperados en la lista desplegable
				$.each(data, function(i, value) {
				$('#listaColeccionesModificar').append('<option value='+value.codigoCole+'>'+value.nombre+'</option>');
				});}
				//Actualiza la lista desplegable
				$('#listaColeccionesModificar').selectmenu("refresh", true);
			};
			});
		}
		
		function traerColeccionesEliminar(){
			$.get(path+'/obtenercolecciones',{mail:usuario},function(data){
			if (data==null) {
				window.alert('El usuario no tiene colecciones');
			}else{
				$('#listaColeccionesEliminar').empty().append('<option value="0">Seleccione una Colección</option>').find('option:first').attr("selected","selected");
				if(data!=null){
				//Inserta los valores recuperados en la lista desplegable
				$.each(data, function(i, value) {
				$('#listaColeccionesEliminar').append('<option value='+value.codigoCole+'>'+value.nombre+'</option>');
				});}
				//Actualiza la lista desplegable
				$('#listaColeccionesEliminar').selectmenu("refresh", true);
			};
			});
		}
		
		function traerColeccionDadoId(){
			var idCol = document.getElementById("listaColeccionesModificar").value;
			if(idCol ==0){
				window.alert('Debe seleccionar una colección');
			}else{
				$.get(path+'/obtenerColeccionDadoId',{idColeccion:idCol},function(data){
				$('#nuevoNombreColeccion').val(""+data);
				});
		
			}

		}
		
		function modificarColeccion(){
			var idCole = document.getElementById("listaColeccionesModificar").value;
			var nuevoNombreColeccion = $('#nuevoNombreColeccion').val();
			if(idCole ==0){
				window.alert('Debe seleccionar una colección');
			}else{
				$.post(path+'/modificarColeccion',{nuevoNombreCole:nuevoNombreColeccion,idColeccion:idCole,correo:usuario},function(retorno){
				if (retorno=="coleccionModificada") {
					window.alert('Se ha modificado la colección a '+nuevoNombreColeccion);
					$('#nuevoNombreColeccion').val("");
					traerColeccionesModificar();
					traerColeccionesEliminar();
				}else{
					window.alert(retorno);
				};
			});
			}	
		}
		
		function eliminarColeccion(){
			var idCole = document.getElementById("listaColeccionesEliminar").value;
			if(idCole ==0){
				window.alert('Debe seleccionar una colección');
			}else{
				$.post(path+'/eliminarColeccion',{idColeccion:idCole,correo:usuario},function(retorno){
				if (retorno=="coleccionEliminada") {
					window.alert('Se ha eliminado la colección');
					traerColeccionesModificar();
					traerColeccionesEliminar();
				}else{
					window.alert(retorno);
				};
			});
			}	
		}
		
function recuperar(){
    var username = $('#mailRecupera').val();
    

    $.post(path+'/recuperarclave',{mail:username},function(retorno){
      if (retorno=="exito") {
		alert("Se ha enviado un email al correo "+username+" con la nueva clave");
        window.location="Index.html";
      }else{
        alert(retorno);
      };

    });
  }
  

	