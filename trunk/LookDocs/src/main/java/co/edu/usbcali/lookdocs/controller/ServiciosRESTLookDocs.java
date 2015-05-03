package co.edu.usbcali.lookdocs.controller;

import javax.faces.bean.ManagedProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.edu.usbcali.lookdocs.presentation.businessDelegate.IBusinessDelegatorView;

@Controller
@RequestMapping("/lookDocs")
public class ServiciosRESTLookDocs {
	
	@Autowired
	private IBusinessDelegatorView businessDelegatorView;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public String login(String name , String pass){
		String retorno="";
		try {
			retorno=businessDelegatorView.iniciarSesionLector(name, pass);
			return retorno;
		} catch (Exception e) {
			return "falla";
		}
		
	}
				
}
