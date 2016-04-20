package br.com.cursojsf.managed;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.cursojsf.ejb.login.LoginBean;
import br.com.cursojsf.entities.UserBean;
import br.com.cursojsf.entities.Usuario;

@ManagedBean
@RequestScoped
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
	
	public String logar(){
		try {
			Usuario usuario = loginBean.autenticar(getLogin(), getSenha());
			if(usuario != null){				
				 HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
				 UserBean userBean = new UserBean(usuario);				 
				 cacheSession(userBean, session, true);
				return redirect("home");
			} else {
				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_WARN);
				message.setSummary("Email ou senha incorretos.");
				message.setDetail("");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return "";
			}
		} catch (Exception e) {
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			message.setSummary(e.getMessage());
			message.setDetail("");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return "";
		}		
	}
	
	public String logout() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		UserBean userBean = (UserBean)session.getAttribute(UserBean.USER_LOGGED);
		if(userBean != null) {
			return super.lougout(userBean, session, true);
		}
		return redirect("login");
	}
}
