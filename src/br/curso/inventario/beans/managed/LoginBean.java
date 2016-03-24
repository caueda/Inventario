package br.curso.inventario.beans.managed;

import javax.annotation.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class LoginBean {
	private String login;
	private String senha;
	
	public LoginBean(){
		
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

	public String login(){
		System.out.println("Login: " + login + "    senha: " + senha + "     Data: " + new java.util.Date());
		return "login";
	}
}
