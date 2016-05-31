package br.com.cursojsf.managed;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
public class IdleMonitorView extends AbstractManagedBean implements Serializable {
	public static final String MESSAGE_MONITOR = "messageMonitor";
    /**
	 * 
	 */
	private static final long serialVersionUID = 1610661144086364381L;

	public void onIdle() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,  "No activity.", "What are you doing over there?");		
        FacesContext.getCurrentInstance().addMessage(MESSAGE_MONITOR, message);
    }
 
    public void onActive() {
    	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Welcome Back", "Well, that's a long coffee break!");
        FacesContext.getCurrentInstance().addMessage(MESSAGE_MONITOR, message);
    }
}