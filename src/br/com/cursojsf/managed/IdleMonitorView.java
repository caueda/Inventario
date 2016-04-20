package br.com.cursojsf.managed;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class IdleMonitorView extends AbstractManagedBean{
    public boolean loggedOut;
    
    public void onIdle() {
        HttpSession session = (HttpSession)getHttpSession(false);
        if(session == null) {
        	setLoggedOut(true);
        }
    }

	public boolean isLoggedOut() {
		return loggedOut;
	}

	public void setLoggedOut(boolean loggedOut) {
		this.loggedOut = loggedOut;
	}
	
	public String goLogin() {
		return redirect("login");
	}

//    public void onActive() {
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
//                                        "Bem vindo", "Esta foi uma longa parada para o café!"));
//    }
}