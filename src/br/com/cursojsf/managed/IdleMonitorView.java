package br.com.cursojsf.managed;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@javax.enterprise.context.SessionScoped
public class IdleMonitorView extends AbstractManagedBean implements Serializable {
	
    public void onIdle() {
        FacesContext.getCurrentInstance().addMessage("messageMonitor", new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                        "No activity.", "What are you doing over there?"));
    }
 
    public void onActive() {
        FacesContext.getCurrentInstance().addMessage("messageMonitor", new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        "Welcome Back", "Well, that's a long coffee break!"));
    }
}