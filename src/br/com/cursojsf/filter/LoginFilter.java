package br.com.cursojsf.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.cursojsf.entities.UserBean;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD
		}
					, urlPatterns = { "/*" })
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	public String getURL(final HttpServletRequest req) {
		return "http://" + req.getLocalAddr() + ":" + req.getLocalPort() + req.getContextPath() + "/";
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {		
	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;
	    HttpSession session = req.getSession(false);
	    UserBean login = null;
	    if(session != null){
	    	login = (UserBean) session.getAttribute(UserBean.USER_LOGGED);
	    }
	    String path = req.getRequestURI().substring(req.getContextPath().length());	  
	    
	    @SuppressWarnings("unused")
		String context = request.getServletContext().getContextPath();
	    
	    res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        res.setDateHeader("Expires", 0); // Proxies.
        
	    System.out.println("url:" + path);
	    
	    if(Pattern.matches(".*javax\\.faces\\.resource.*|.*\\.js[;jsessionid=]*.*|.*\\.css[;jsessionid=]*.*|.*\\.css\\.curso[;jsessionid=]*.*|.*\\.js\\.curso[;jsessionid=]*.*", path)){
	    	chain.doFilter(request, response);
	    } else if(login == null && !path.contains("login.curso")){
	    	res.sendRedirect(req.getContextPath() + "/login.curso");
	    } else if(path.contains("login.curso") || path.contains("index.jsp")){
	    	if(path.contains("index.jsp")){
	    		System.out.println("shit!!!");
	    	}
	    	chain.doFilter(request, response);
	    } else {
	    	chain.doFilter(request, response);
	    }
	    /*
	    if (!path.contains("login.curso") && !path.contains("index.curso") && !Pattern.matches(".*\\.js[;jsessionid=]*.*|.*\\.css[;jsessionid=]*.*|.*\\.css\\.curso[;jsessionid=]*.*|.*\\.js\\.curso[;jsessionid=]*.*", path)) {
	        if (login != null) {	
				System.out.println("Usuário logado");	    	
	        	chain.doFilter(request, response);
	        } else {
				System.out.println("Usuário não está logado");	    
	        }
	    } else {
	        chain.doFilter(request, response);
	    }
	    */
	}

	
	public static void main(String[] args) {
		String url = "/javax.faces.resource/jquery-1.12.1.min.js.curso;jsessionid=e5mOtJ8gBrv-tJtlygs5fkB5Q_w6Xz41xnZBhHP8.matrix";
		System.out.println(Pattern.matches(".*javax\\.faces\\.resource.*|.*\\.js[;jsessionid=]*.*", url));
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
