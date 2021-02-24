package net.sf.minuteProject.architecture.bsla.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DefaultFactory {
    private static BeanFactory beanFactory;
    private static boolean isLoaded = false;
    private static String config;

    public static String getConfig() {
    	if (config==null)
    		return "classpath:spring-myproject-config.xml";
		return "classpath:"+config;
	}

	public static void setConfig(String config) {
		DefaultFactory.config = config;
	}
    /**
     * Returns a bean factory
     * @return BeanFactory
     */
    private static BeanFactory getFactory() {
        if (!isLoaded) {
            loadFactory();
        }

        return beanFactory;
    }

    /**
     * Instantiates a bean
     *
     * @param name String
     * @return Object
     */
    public static Object getBean(String name) {
        return getFactory().getBean(name);

    }

    /**
     * Loads an configures a Bean Factory
     */

    private static void loadFactory() {
        try {
            String[] configLocations = new String[] {getConfig()};
            // Instantiate the object but do not process yet;
            beanFactory = new ClassPathXmlApplicationContext(configLocations, false);
            isLoaded = true;
            // Process the configuration now so that no infinite loop happens
            ((ClassPathXmlApplicationContext) beanFactory).refresh();
            System.out.println("load of the spring factory done");
        } catch (Exception ex) {
            System.out.println("Could not load Spring myproject Bean Factory");
            System.out.println(ex.getMessage());
        }
    }


}
