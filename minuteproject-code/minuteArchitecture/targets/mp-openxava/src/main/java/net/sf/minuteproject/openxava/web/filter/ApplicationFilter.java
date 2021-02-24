package net.sf.minuteproject.openxava.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author adlerfl
 * inspired by http://stackoverflow.com/questions/2725102/how-to-use-a-servlet-filter-in-java-to-change-an-incoming-servlet-request-url
 */
public class ApplicationFilter implements Filter {

	private String application;
	
    @Override
    public void init(FilterConfig config) throws ServletException {
    	application = config.getInitParameter("application");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURI();

        if (!requestURI.startsWith("/"+application+"/")) {
        	String newURI = "/"+application+requestURI;
            req.getRequestDispatcher(newURI).forward(req, res);
        } else {
            chain.doFilter(req, res);
        }
        
    }

    @Override
    public void destroy() {
        //
    }
}
