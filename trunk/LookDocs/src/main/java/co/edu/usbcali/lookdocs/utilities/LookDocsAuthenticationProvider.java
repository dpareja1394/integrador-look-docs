package co.edu.usbcali.lookdocs.utilities;

import java.util.ArrayList;
import java.util.List;
 











import java.util.concurrent.ConcurrentHashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import co.edu.usbcali.lookdocs.model.Usuarios;
import co.edu.usbcali.lookdocs.presentation.businessDelegate.BusinessDelegatorView;
import co.edu.usbcali.lookdocs.presentation.businessDelegate.IBusinessDelegatorView;
 
 
@Scope("singleton")
@Component("LookDocsAuthenticationProvider")
public class LookDocsAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private IBusinessDelegatorView businessDelegatorView;
	private Usuarios usu;
	final ThreadLocal<FacesContext> instance = new ThreadLocal<FacesContext>() {
        protected FacesContext initialValue() { return (null); }
    };
    private static ConcurrentHashMap threadInitContext = new ConcurrentHashMap(2);
    /**
     * Implementacion de la seguridad propia
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
         
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        String adminVerifi;
        String lectorVerifi;
        
        
        
		try {
			adminVerifi = businessDelegatorView.iniciarSesionAdministrador(email, password);
			usu = businessDelegatorView.obtenerPorMail(email);
		} catch (Exception e) {			
			adminVerifi="";
		}
		try {			
			lectorVerifi = businessDelegatorView.iniciarSesionLector(email, password);
			usu = businessDelegatorView.obtenerPorMail(email);
		} catch (Exception e) {
			lectorVerifi="";
		}
         
        if (lectorVerifi.equals("exito") ) {
            final List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority("LECTOR"));
            final UserDetails principal = new User(email, password, grantedAuths);
            final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
            
            ServletRequestAttributes servletRequestAttributes=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = servletRequestAttributes.getRequest().getSession();
            session.setAttribute("usuarioLector", usu);
            return auth;
        } else {
        	if (adminVerifi.equals("exito")) {
                final List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
                grantedAuths.add(new SimpleGrantedAuthority("ADMIN"));
                final UserDetails principal = new User(email, password, grantedAuths);
                final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
                
                ServletRequestAttributes servletRequestAttributes=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = servletRequestAttributes.getRequest().getSession();
                session.setAttribute("usuarioAdministrador", usu);
                return auth;
            } else {
                return null;
            }
        }
        
        
        
        
    }
 
    /**
     * Este metodo se llama primero cuando se autentica un usuario
     */
    @Override
    public boolean supports(Class<?> authentication) {
         return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
     
}
