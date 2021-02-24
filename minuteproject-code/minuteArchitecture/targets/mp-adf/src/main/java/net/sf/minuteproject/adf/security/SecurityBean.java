package net.sf.minuteproject.adf.security;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import java.security.Principal;

public class SecurityBean {
    public SecurityBean() {
        super();
    }
    
    public SecurityUser getSecurityUser() {
        SecurityUser securityUser = new SecurityUser();
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal!=null) {
		    System.out.println(">> userPrincipal "+userPrincipal.getClass().getName());
            securityUser.setUserId(userPrincipal.getName());
            System.out.println("user : "+securityUser);
        }
        return securityUser;
    }
}
