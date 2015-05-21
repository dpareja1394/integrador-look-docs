package co.edu.usbcali.lookdocs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.Usuarios;
import co.edu.usbcali.lookdocs.model.dto.ColeccionesDTO;
import co.edu.usbcali.lookdocs.presentation.businessDelegate.IBusinessDelegatorView;

@Controller
@RequestMapping("/lookDocs")
public class ServiciosRESTLookDocs {

	@Autowired
	private IBusinessDelegatorView businessDelegatorView;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(String name, String pass) {
		String retorno = "";
		try {
			retorno = businessDelegatorView.iniciarSesionLector(name, pass);
			return retorno;
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/registrar", method = RequestMethod.POST)
	@ResponseBody
	public String registrar(String nombre, String correo, String pass,
			String passConfirma) {
		Usuarios usuario = new Usuarios();
		try {
			if (pass.trim().equals(passConfirma.trim())) {
				usuario.setNombre(nombre);
				usuario.setEmail(correo);
				usuario.setClave(passConfirma);
				businessDelegatorView.registrarUsuarioLector(usuario);
				return "exito";
			} else {
				return "Las contraseñas no coinciden";
			}
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/recuperarclave", method = RequestMethod.POST)
	@ResponseBody
	public String recuperarClave(String mail) {
		try {
			businessDelegatorView.recuperarClave(mail);
			return "exito";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/obtenercolecciones", method = RequestMethod.GET)
	@ResponseBody
	public List<ColeccionesDTO> obtenerColeccionesDadoMail(String mail) {
		try {
			List<ColeccionesDTO> lasColeccionesDelUsuario = businessDelegatorView
					.obtenerColeccionesDadoMailDeUsuario(mail);
			return lasColeccionesDelUsuario;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@RequestMapping(value="/crearColeccion",method=RequestMethod.POST)
	@ResponseBody
	public String crearColeccion(String correo, String nombreColeccion){
		
		Colecciones colecciones = new Colecciones();
		try {
			Usuarios usuarios = new Usuarios();
			usuarios = businessDelegatorView.obtenerPorMail(correo);
			colecciones.setNombre(nombreColeccion);
			colecciones.setUsuarios(usuarios);
			businessDelegatorView.saveColecciones(colecciones);
			return "exito";
		} catch (Exception e) {
			return e.getMessage();
		}		
		
	}
}
