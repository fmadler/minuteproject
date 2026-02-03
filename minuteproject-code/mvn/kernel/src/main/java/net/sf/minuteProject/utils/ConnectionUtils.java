package net.sf.minuteProject.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.minuteProject.configuration.bean.DataModel;
import net.sf.minuteProject.configuration.bean.model.cmis.CmisModel;
import net.sf.minuteProject.configuration.bean.model.cmis.CmisSource;

import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.commons.dbcp.BasicDataSource;

public class ConnectionUtils {

	public static Connection getConnection (DataModel dataModel) {
		BasicDataSource basicDataSource = dataModel.getBasicDataSource();
	    String driver = basicDataSource.getDriverClassName();
	    String url = basicDataSource.getUrl();
	    String username = basicDataSource.getUsername();
	    String password = basicDataSource.getPassword();

	    try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // load Oracle driver
	    catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return null;		
	}

	public static Session getSession(CmisModel cmisModel) {
		return getSession(cmisModel.getCmisSource());
	}
	
    private static Session getSession (CmisSource cmisSource) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(SessionParameter.REPOSITORY_ID, cmisSource.getRepositoryId());

        String repositoryBinding = cmisSource.getRepositoryBinding();
		BindingType bindingType = BindingType.fromValue(repositoryBinding);
        parameters.put(SessionParameter.BINDING_TYPE, bindingType.value());

//        if (StringUtils.isNotBlank(httpInvokerClass)) {
//            parameters.put(SessionParameter.HTTP_INVOKER_CLASS, httpInvokerClass);
//        }
        org.apache.chemistry.opencmis.client.bindings.CmisBindingFactory c;
        //link the session to user
        parameters.put(SessionParameter.USER, "adlerfl");
        String repositoryUrl = cmisSource.getRepositoryUrl();
        
        switch (bindingType) {
            case ATOMPUB:
			parameters.put(SessionParameter.ATOMPUB_URL, repositoryUrl);
                break;
            case BROWSER:
                parameters.put(SessionParameter.BROWSER_URL, repositoryUrl);
                break;
            default:
                throw new IllegalArgumentException("Repository binding of type '" + repositoryBinding + "' is not supported!");
        }

        Session session = SessionFactoryImpl.newInstance().createSession(parameters);
        return session;
    }

}
