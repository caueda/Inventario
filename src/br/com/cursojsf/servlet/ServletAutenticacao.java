package br.com.cursojsf.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.cursojsf.ejb.login.LoginBean;
import br.com.cursojsf.entities.UserBean;
import br.com.cursojsf.entities.Usuario;
import br.com.cursojsf.util.WebUtil;

@WebServlet(urlPatterns="/autenticar")
public class ServletAutenticacao extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1997026148543236463L;

	@Inject
	private LoginBean loginBean;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = (String)req.getParameter("login");
		String senha = (String)req.getParameter("senha");
		try {
			Usuario usuario = loginBean.autenticar(login, senha);
			if(usuario != null){				
				 HttpSession session = req.getSession(true);
				 UserBean userBean = new UserBean(usuario);				 
				 WebUtil.cacheSession(userBean, session, true);
				 resp.sendRedirect(req.getContextPath() + "/home.curso");
			} else {				
				resp.sendRedirect(req.getContextPath() + "/index.jsp?mensagem=" + URLEncoder.encode("E-mail ou senha inválida!", "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
}
