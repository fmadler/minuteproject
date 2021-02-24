package net.sf.minuteProject.architecture.web;

import java.io.*;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.sql.DataSource;

import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.architecture.bsla.factory.DefaultFactory;
import net.sf.minuteProject.architecture.bsla.factory.MyProjectFactory;
import net.sf.minuteProject.architecture.web.controller.DefaultController;
	
   /**
    * Handles a single guestbook request from the client.
    */
public class DefaultServlet extends HttpServlet {
	
	private static String SERVICE_PARAMETER = "_service";
	private static String INPUT_OBJECT_PARAMETER = "_inputObject";
	private static String METHOD_PARAMETER = "_method";
	private static String FORM_PARAM_ROOT_PARAMETER = "_root";
	private static String ACTION_PARAMETER = "_action";
	private DefaultController defaultController;
	
	public void doGet (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
    	doPost (req, res);
    }
    
	public void doPost (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        HttpSession session = req.getSession();
        String service = req.getParameter(SERVICE_PARAMETER);
        String inputObject = req.getParameter(INPUT_OBJECT_PARAMETER);
        String method = req.getParameter(METHOD_PARAMETER);
        String action = req.getParameter(ACTION_PARAMETER);
        Map parameters = getParametersMap(req);
        
        Object result = performRequest (service, method, inputObject, parameters);
        String forward = findExit (inputObject, method);
        session.setAttribute(getAttributeName(inputObject, method),result);
        forward(forward,req,res);
   }

	private String findExit (String inputObject, String method) {
		return defaultController.getForward(inputObject, method);
	}
	private String getAttributeName (String inputObject, String method) {
		return defaultController.getAttributeName(inputObject, method);
	}
	
	public Object performRequest(String service, String method, String inputObject, Map parameters) {
		Object result=null;
		try {
        	Class clazz = this.getClass().getClassLoader().loadClass(inputObject);
			Object object = clazz.newInstance();
			populate(object,parameters);
			Object serviceObject = DefaultFactory.getBean(service);
			Class paramClass [] = {clazz};
			Object paramObjects [] = {object};
			Method serviceMethod = serviceObject.getClass().getMethod(method,paramClass);
			result = serviceMethod.invoke(serviceObject, paramObjects);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return result;
	}
	
    private static Map getParametersMapForInputObject(HttpServletRequest request) {
        Map parameters = new HashMap();
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
        	String [] params = request.getParameterValues(key);
        	if (params.length>0 && !params[0].equals("")) {
            	parameters.put(key, params[0]);
            	System.out.println("param = "+key+", "+params[0]);            		
        	}
        }
        return parameters;
    }
    
    private static Map getParametersMap(HttpServletRequest request) {
        Map parameters = new HashMap();
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
        	String [] params = request.getParameterValues(key);
        	if (params.length>0 && !params[0].equals("")) {
        		String param = params[0];
        		if (StringUtils.indexOf(param, FORM_PARAM_ROOT_PARAMETER)!=-1) {
        			param=StringUtils.stripStart(param, FORM_PARAM_ROOT_PARAMETER);
        			parameters.put(key, param);
        			System.out.println("param = "+key+", "+params[0]);            	
        		}
        	}
        }
        return parameters;
    }   
    
    public static void populate(Object bean, Map parameters)
        throws IllegalAccessException, InvocationTargetException {
        //Map parameters = getParametersMap(request);
        BeanUtilsBean.getInstance().populate(bean, parameters);
    }    

    private void forward(
    	    String aDestination, 
    	    HttpServletRequest aRequest, 
    	    HttpServletResponse aResponse
    	  ) throws ServletException, IOException {
    	    
    	    RequestDispatcher dispatcher = aRequest.getRequestDispatcher(aDestination);
    	    dispatcher.forward(aRequest, aResponse);
    }      
}