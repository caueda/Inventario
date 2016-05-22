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
	    UserBean login = (UserBean) req.getSession().getAttribute(UserBean.USER_LOGGED);
	    String path = req.getRequestURI().substring(req.getContextPath().length());	  
	    
	    @SuppressWarnings("unused")
		String context = request.getServletContext().getContextPath();
	    
	    res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        res.setDateHeader("Expires", 0); // Proxies.
        
	    System.out.println(path);
	    
	    if (!path.contains("login.curso") && !path.contains("home.curso") && !path.contains("index.jsp") && !Pattern.matches(".*\\.js[;jsessionid=]*.*|.*\\.css[;jsessionid=]*.*|.*\\.css\\.curso[;jsessionid=]*.*|.*\\.js\\.curso[;jsessionid=]*.*", path)) {
	        if (login != null) {	
	        	chain.doFilter(request, response);
	        } else {
	        	System.out.println("Redirecionando para index.jsp");
	        	res.sendRedirect(getURL(req) + "index.jsp");
	        }
	    } else {
	        chain.doFilter(request, response);
	    }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
