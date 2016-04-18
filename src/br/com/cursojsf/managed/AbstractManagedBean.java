package br.com.cursojsf.managed;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AbstractManagedBean {
	protected HttpSession getSession(boolean value) {
		return (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(value);
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
}
