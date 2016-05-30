package br.com.cursojsf.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.cursojsf.entities.UserBean;

public class WebUtil {
	public static void cacheSession(UserBean userBean, HttpSession session, boolean onlyOneSessionPerUser) {
		ServletContext application = session.getServletContext();
		session.setAttribute(UserBean.USER_LOGGED, userBean);
		if(application.getAttribute(userBean.getKey()) == null) {
			application.setAttribute(userBean.getKey(), new HashSet<HttpSession>());
		}
		
		Set<HttpSession> cacheSessions = (Set<HttpSession>)application.getAttribute(userBean.getKey());
		if(onlyOneSessionPerUser) {
			for(HttpSession sessionToInvalidate : cacheSessions) {
				try { sessionToInvalidate.invalidate(); } catch(Exception e) {}
			}
			cacheSessions.clear();
		}
		
		cacheSessions.add(session);
	}
	
	public static void logout(UserBean userBean, HttpSession session, boolean allSessions) throws IOException {	
		ServletContext application = session.getServletContext();
		Set<HttpSession> cached = (Set<HttpSession>)application.getAttribute(userBean.getKey());
		Iterator<HttpSession> ite = cached.iterator();
		while(ite.hasNext()) {
			HttpSession sessionToInvalidate = ite.next();
			if(allSessions) {
				try {sessionToInvalidate.invalidate();} catch(Exception e) {}
			} else if(sessionToInvalidate.equals(session.getId())) {
				sessionToInvalidate.invalidate();
				ite.remove();
			}			
		}	
		if(allSessions) {
			cached.clear();
		}
	}
}
