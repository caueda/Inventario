package br.com.cursojsf.managed;


import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.cursojsf.ejb.login.LoginBean;
import br.com.cursojsf.entities.UserBean;
import br.com.cursojsf.entities.Usuario;

@Named
@javax.enterprise.context.RequestScoped
public class LoginAction extends AbstractManagedBean implements Serializable {

	private static final long serialVersionUID = -5263706623967677173L;	
	
	@EJB
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
	
    public void onIdle() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                                        "Nenhuma atividade.", "O que você está fazendo por aí ?"));
    }
 
    public void onActive() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        "Bem vindo", "Esta foi uma longa parada para o café!"));
    }
	
	public String logar(){
		try {
			Usuario usuario = loginBean.autenticar(getLogin(), getSenha());
			if(usuario != null){				
				 HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
				 UserBean userBean = new UserBean(usuario);				 
				 cacheSession(userBean, session, true);
				return redirect("home");
			} else {				
				incluirWarning("E-mail ou senha incorretos.");
				return "";
			}
		} catch (Exception e) {
			incluirError(e.getMessage());
			return "";
		}		
	}
	
	public String logout() throws Exception {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		UserBean userBean = (UserBean)session.getAttribute(UserBean.USER_LOGGED);
		if(userBean != null) {
			return super.logout(userBean, session, true);
		}
		return redirect("login");
	}
}
