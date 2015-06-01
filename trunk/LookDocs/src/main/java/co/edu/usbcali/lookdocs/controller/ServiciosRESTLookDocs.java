package co.edu.usbcali.lookdocs.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import co.edu.usbcali.lookdocs.model.Anexos;
import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.Entradas;
import co.edu.usbcali.lookdocs.model.Rss;
import co.edu.usbcali.lookdocs.model.Usuarios;
import co.edu.usbcali.lookdocs.model.dto.ArticulosDTO;
import co.edu.usbcali.lookdocs.model.dto.CategoriasDTO;
import co.edu.usbcali.lookdocs.model.dto.ColeccionesDTO;
import co.edu.usbcali.lookdocs.model.dto.NoticiasDTO;
import co.edu.usbcali.lookdocs.model.dto.RssDTO;
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

	@RequestMapping(value = "/obtenercategorias", method = RequestMethod.GET)
	@ResponseBody
	public List<CategoriasDTO> obtenerCategorias() {
		try {
			List<CategoriasDTO> lasCategorias = businessDelegatorView
					.getDataCategorias();
			return lasCategorias;
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/crearColeccion", method = RequestMethod.POST)
	@ResponseBody
	public String crearColeccion(String correo, String nombreColeccion) {

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

	@RequestMapping(value = "/modificarColeccion", method = RequestMethod.POST)
	@ResponseBody
	public String modificarColeccion(String nuevoNombreCole, Long idColeccion,
			String correo) {

		Colecciones coleccionSeleccionada = new Colecciones();
		Usuarios usuarios = new Usuarios();
		try {
			usuarios = businessDelegatorView.obtenerPorMail(correo);
			Long codigoCole = idColeccion;
			String coleccionAModificar = businessDelegatorView
					.findColeccionPorId(codigoCole);
			String newName = nuevoNombreCole;
			coleccionSeleccionada = businessDelegatorView
					.consultarColeccionPorNombreYUsuario(usuarios,
							coleccionAModificar);
			coleccionSeleccionada.setNombre(newName);
			businessDelegatorView.updateColecciones(coleccionSeleccionada);
			coleccionSeleccionada = null;
			return "coleccionModificada";
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/eliminarColeccion", method = RequestMethod.POST)
	@ResponseBody
	public String eliminarColeccion(Long idColeccion, String correo) {

		Colecciones coleccionSeleccionada = new Colecciones();
		Usuarios usuarios = new Usuarios();
		try {
			usuarios = businessDelegatorView.obtenerPorMail(correo);
			Long codigoCole = idColeccion;
			String coleccionAEliminar = businessDelegatorView
					.findColeccionPorId(codigoCole);
			coleccionSeleccionada = businessDelegatorView
					.consultarColeccionPorNombreYUsuario(usuarios,
							coleccionAEliminar);
			List<Rss> rssPorColeccion = new ArrayList<Rss>();
			rssPorColeccion = businessDelegatorView
					.getRssDadoIdColeccion(coleccionSeleccionada
							.getCodigoCole());
			if (rssPorColeccion.size() > 0) {
				return "No se puede eliminar esta Coleccion Porq Tiene 1 o mas RSS";
			} else {
				businessDelegatorView.deleteColecciones(coleccionSeleccionada);
				coleccionSeleccionada = null;

				return "coleccionEliminada";
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/guardarRss", method = RequestMethod.POST)
	@ResponseBody
	public String guardarRss(String url, Long idColeccion) {

		try {
			String urlRss = url;
			Long codigoCole = idColeccion;
			if (urlRss.trim().equals("") == true) {
				return "Debe ingresar una URL de Rss para agregar";
			} else {
				businessDelegatorView.guardarRSS(urlRss, codigoCole);

				return "rssGuardado";
			}

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/buscarRss", method = RequestMethod.GET)
	@ResponseBody
	public List<NoticiasDTO> buscarRss(String url) {

		try {
			List<NoticiasDTO> lasNoticias;
			String urlRss = url;
			if (urlRss.trim().equals("") == true) {
				return null;
			} else {
				URL feedUrl = new URL(urlRss);
				SyndFeedInput input = new SyndFeedInput();
				SyndFeed feed = input.build(new XmlReader(feedUrl));

				List<SyndEntry> losFeed = feed.getEntries();
				lasNoticias = new ArrayList<NoticiasDTO>();
				for (SyndEntry syndEntry : losFeed) {
					NoticiasDTO noticiasDto = new NoticiasDTO();
					noticiasDto.setTitulo((String) syndEntry.getTitle());
					noticiasDto.setDescripcion((String) syndEntry
							.getDescription().getValue());
					// noticiasDto.setLink((String) syndEntry.getLink());
					lasNoticias.add(noticiasDto);
				}
				return lasNoticias;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/mostrarFeed", method = RequestMethod.GET)
	@ResponseBody
	public List<NoticiasDTO> mostrarFeed(String url, Long idColeccion) {
		try {
			String urlRss = url;
			Rss elRss2 = new Rss();
			Entradas entrada = new Entradas();
			List<NoticiasDTO> lasNoticiasFeed;
			Colecciones nombreColeccionEntrada = new Colecciones();
			List<RssDTO> rssPorColeccionDto;
			Long codigoCole = idColeccion;

			URL feedUrl = new URL(urlRss);

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedUrl));

			List<SyndEntry> losFeed = feed.getEntries();
			lasNoticiasFeed = new ArrayList<NoticiasDTO>();
			for (SyndEntry syndEntry : losFeed) {
				NoticiasDTO noticiasFeedDTO = new NoticiasDTO();
				noticiasFeedDTO.setTitulo((String) syndEntry.getTitle());
				noticiasFeedDTO.setDescripcion((String) syndEntry
						.getDescription().getValue());
				noticiasFeedDTO.setLink((String) syndEntry.getLink());
				lasNoticiasFeed.add(noticiasFeedDTO);
			}

			List<Rss> losRss2 = businessDelegatorView
					.consultarRssPorURlList(urlRss);

			for (int i = 0; i < losRss2.size(); i++) {
				elRss2 = (Rss) losRss2.get(i);
				entrada = businessDelegatorView.consultarEntradaPorRss(elRss2);

				nombreColeccionEntrada = businessDelegatorView
						.getColecciones(codigoCole);

				if (entrada.getLeido().equals("N")) {
					entrada.setLeido("S");
					entrada.setFechaLeido(new Date());
					businessDelegatorView.updateEntradas(entrada);
					rssPorColeccionDto = businessDelegatorView
							.getDataRssPorColeccion(nombreColeccionEntrada);

				}

			}

			return lasNoticiasFeed;
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/obtenerColeccionDadoId", method = RequestMethod.GET)
	@ResponseBody
	public String obtenerColeccionDadoId(Long idColeccion) {

		try {
			Colecciones colecciones = businessDelegatorView
					.getColecciones(idColeccion);
			return colecciones.getNombre();

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/obtenerRssDadoColeccion", method = RequestMethod.GET)
	@ResponseBody
	public List<RssDTO> obtenerRssDadoColeccion(Long idColeccion) {
		try {
			List<RssDTO> losRss = businessDelegatorView
					.getRssDTODadoIdColeccion(idColeccion);
			return losRss;
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/consultarUrlRss", method = RequestMethod.GET)
	@ResponseBody
	public String consultarRssPorId(Long idRss) {
		try {
			Rss rss = new Rss();
			Long rssID = idRss;
			rss = businessDelegatorView.getRss(rssID);
			String urlRss;
			urlRss = rss.getUrl();

			return urlRss;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/obtenerArticuloDadoCategoria", method = RequestMethod.GET)
	@ResponseBody
	public List<ArticulosDTO> obtenerArticuloDadoCategoria(Long idCategoria) {
		try {
			List<ArticulosDTO> losArticulos = businessDelegatorView
					.getDataArticulosByCateg(idCategoria);
			return losArticulos;
		} catch (Exception e) {
			return null;
		}
	}

	@RequestMapping(value = "/descargarArticulo", method = RequestMethod.GET)
	@ResponseBody
	public String descargarArticulo(Long idArticulo) {

		try {
			Anexos download = businessDelegatorView
					.getAnexosbyArtiuclo(idArticulo);
			businessDelegatorView.downloadFileByFTP("integrador.comli.com",
					"a6132029", "andres20021994",
					download.getNombre() + ".pdf", download.getUrl());

			return "descargado";

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/feedLeido", method = RequestMethod.GET)
	@ResponseBody
	public String feedLeido(String url, Long idColeccion) {
		try {
			String urlRss = url;
			Rss elRss2 = new Rss();
			Entradas entrada = new Entradas();

			List<RssDTO> rssPorColeccionDto;
			Long codigoCole = idColeccion;

			Colecciones nombreColeccionEntrada = new Colecciones();
			String estaLeido = "";

			List<Rss> losRss2 = businessDelegatorView
					.consultarRssPorURlList(urlRss);

			for (int i = 0; i < losRss2.size(); i++) {
				elRss2 = (Rss) losRss2.get(i);
				entrada = businessDelegatorView.consultarEntradaPorRss(elRss2);

				nombreColeccionEntrada = businessDelegatorView
						.getColecciones(codigoCole);

				if (entrada.getLeido().equals("N")) {
					rssPorColeccionDto = businessDelegatorView
							.getDataRssPorColeccion(nombreColeccionEntrada);
					estaLeido = "No Leído";

				} else {
					estaLeido = "Leído";
				}

			}

			return estaLeido;
		} catch (Exception e) {
			return e.getMessage();
		}

	}
	
	@RequestMapping(value="/favorito",method=RequestMethod.GET)
	@ResponseBody
	public String favorito(String url){
		
		try {
			String urlRss = url;
			Rss elRss2 = new Rss();
			Entradas entrada = new Entradas();
			Date fechaMeGusta = new Date();
			Colecciones unicaColeccion = new Colecciones();
			
			List<Rss> losRss2 = businessDelegatorView.consultarRssPorURlList(urlRss);
			elRss2 = (Rss) losRss2.get(0);			
			
			for (Rss rss : losRss2) {
				elRss2 = rss;
				entrada = businessDelegatorView.consultarEntradaPorRss(elRss2);
				if(entrada.getFavorito().trim().equals("N")){
					entrada.setFavorito("S");
					entrada.setFechaFavorito(fechaMeGusta);
					businessDelegatorView.updateEntradas(entrada);
				}else
					if(entrada.getFavorito().trim().equals("S")){
						entrada.setFavorito("N");
						businessDelegatorView.updateEntradas(entrada);
					}
			}						
			
			List<Colecciones> listaUnicaColeccion;
			List<RssDTO> rssPorColeccionDto;
			listaUnicaColeccion = new ArrayList<Colecciones>();
			rssPorColeccionDto = new ArrayList<RssDTO>();
			
			listaUnicaColeccion = businessDelegatorView.consultarColePorURL(urlRss);
			unicaColeccion = (Colecciones) listaUnicaColeccion.get(0);
			rssPorColeccionDto = businessDelegatorView.getDataRssPorColeccion(unicaColeccion);
			
			return entrada.getFavorito();
		} catch (Exception e) {
			return e.getMessage();
		}
	}


}
