package br.com.cursojsf.managed;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.cursojsf.entities.UserBean;

@SuppressWarnings("unchecked")
public class AbstractManagedBean {	
	
	public void cacheSession(UserBean userBean, HttpSession session, boolean onlyOneSessionPerUser) {
		
		session.setAttribute(UserBean.USER_LOGGED, userBean);
		if(!getApplicationMap().containsKey(userBean.getKey())) {
			getApplicationMap().put(userBean.getKey(), new HashSet<HttpSession>());
		}
		
		Set<HttpSession> cacheSessions = (Set<HttpSession>)getApplicationMap().get(userBean.getKey());
		if(onlyOneSessionPerUser) {
			for(HttpSession sessionToInvalidate : cacheSessions) {
				try { sessionToInvalidate.invalidate(); } catch(Exception e) {}
			}
			cacheSessions.clear();
		}
		
		cacheSessions.add(session);
	}
	
	public String redirect(String url) {
		return url + "?faces-redirect=true";
	}
	
	protected String logout(UserBean userBean, HttpSession session, boolean allSessions) throws IOException {		
		Set<HttpSession> cached = (Set<HttpSession>)getApplicationMap().get(userBean.getKey());
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
		Map<String, Object> cookieMap = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
		Set<String> keys = cookieMap.keySet();
		for(String key : keys) {
			Cookie cookie = (Cookie)cookieMap.get(key);
			if(cookie.getName().equals("JSESSIONID")) {
				cookie.setMaxAge(0);
				cookie.setValue("");
				HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
				response.addCookie(cookie);
			}
		}
		FacesContext.getCurrentInstance().getExternalContext().redirect(getURL(getRequest()) + "login.curso");
		return "";
	}
	
	public String getURL(final HttpServletRequest req) {
		return "http://" + req.getLocalAddr() + ":" + req.getLocalPort() + req.getContextPath() + "/";
	}
	
	public String isUsuarioLogado(HttpSession session) {
		String loginPage = "login";
		if(session == null) {
			return loginPage;
		} else if(session != null) {
			if(session.getAttribute(UserBean.USER_LOGGED) == null) {
				return loginPage;
			} else {
				UserBean userBean = (UserBean)session.getAttribute(UserBean.USER_LOGGED);
				Map<String, HttpSession> cachedSession = (Map<String, HttpSession>)getApplicationMap().get(userBean.getKey());
				if(!cachedSession.containsKey(session.getId())) {
					return loginPage;
				} else {
					return "";
				}
			}
		}  
		return "";
	}
	
	protected HttpSession getHttpSession(boolean value) {
		return (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(value);
	}
	
	protected Map<String, Object> getApplicationMap() {
		return (Map<String, Object>)FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	protected void incluirInfo(String summary) {
		incluirInfo(summary, "");
	}
	
	protected void incluirInfo(String summary, String detail) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		message.setSummary(summary);
		message.setDetail(detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	protected void incluirError(String summary) {
		incluirError(summary, "");
	}
	
	protected void incluirError(String summary, String detail) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		message.setSummary(summary);
		message.setDetail(detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	protected void incluirWarning(String summary) {
		incluirWarning(summary, "");
	}
	
	protected void incluirWarning(String summary, String detail) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_WARN);
		message.setSummary(summary);
		message.setDetail(detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
