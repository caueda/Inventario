package br.com.cursojsf.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.cursojsf.entities.UserBean;
import br.com.cursojsf.util.WebUtil;

@WebServlet(urlPatterns="/deslogar")
public class ServletDeslogar extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1997026148543236463L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = (HttpSession) req.getSession(false);
		UserBean userBean = null;
		if(session != null){
			userBean = (UserBean)session.getAttribute(UserBean.USER_LOGGED);
		}
		if(userBean != null) {
			WebUtil.logout(userBean, session, true);
		}
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
	}
	
}
