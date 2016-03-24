package br.com.cursojsf.managed;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.com.cursojsf.ejb.login.LoginBean;

@ManagedBean
@RequestScoped
public class LoginAction implements Serializable {

	private static final long serialVersionUID = -5263706623967677173L;	
	
	@Inject
	private LoginBean loginBean;
	
	private String login;
	private String senha;
	
	public LoginAction(){
		System.out.println("Creating LoginBean..." + new java.util.Date());
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void logar(){
		try {
			if(loginBean.autenticar(getLogin(), getSenha())){
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				message.setSummary("Logado com sucesso.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			} else {
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				message.setSummary("Email ou senha incorretos.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			message.setSummary(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
//		return "home";
	}
}
