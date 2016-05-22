package br.com.cursojsf.managed;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@javax.enterprise.context.RequestScoped
public class IdleMonitorView extends AbstractManagedBean{
	
    public void onIdle() {
        FacesContext.getCurrentInstance().addMessage("messageMonitor", new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                        "No activity.", "What are you doing over there?"));
    }
 
    public void onActive() {
        FacesContext.getCurrentInstance().addMessage("messageMonitor", new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        "Welcome Back", "Well, that's a long coffee break!"));
    }
}