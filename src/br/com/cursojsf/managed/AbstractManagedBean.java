package br.com.cursojsf.managed;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
			getApplicationMap().put(userBean.getKey(), new HashMap<String, HttpSession>());
		}
		
		Map<String, HttpSession> cacheSessions = (Map<String, HttpSession>)getApplicationMap().get(userBean.getKey());
		if(onlyOneSessionPerUser) {
			for(HttpSession sessionToInvalidate : cacheSessions.values()) {
				try { sessionToInvalidate.invalidate(); } catch(Exception e) {}
			}
			cacheSessions.clear();
		}
		
		cacheSessions.put(session.getId(), session);
	}
	
	public String redirect(String url) {
		return url + "?faces-redirect=true";
	}
	
	protected String logout(UserBean userBean, HttpSession session, boolean allSessions) throws IOException {		
		Map<String, HttpSession> cached = (Map<String, HttpSession>)getApplicationMap().get(userBean.getKey());
		Set<Entry<String, HttpSession>> entryKeySet = cached.entrySet();
		for(Entry<String, HttpSession> entry : entryKeySet) {
			if(allSessions) {
				try {entry.getValue().invalidate();} catch(Exception e) {}
			} else if(entry.getKey().equals(session.getId())) {
				entry.getValue().invalidate();
				cached.remove(entry.getKey());
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
