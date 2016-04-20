package br.com.cursojsf.managed;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.cursojsf.entities.UserBean;

public class AbstractManagedBean {	
	
	@SuppressWarnings("unchecked")
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
	
	protected String lougout(UserBean userBean, HttpSession session, boolean allSessions) {		
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
		return "login";
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
		} else 
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
}
