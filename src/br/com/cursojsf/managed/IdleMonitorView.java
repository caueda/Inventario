package br.com.cursojsf.managed;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
public class IdleMonitorView extends AbstractManagedBean implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1610661144086364381L;

	public void onIdle() {
        FacesContext.getCurrentInstance().addMessage("messageMonitor", new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                        "No activity.", "What are you doing over there?"));
    }
 
    public void onActive() {
        FacesContext.getCurrentInstance().addMessage("messageMonitor", new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        "Welcome Back", "Well, that's a long coffee break!"));
    }
}