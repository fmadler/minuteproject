package net.sf.minuteProject.architecture.bsla.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyProjectFactory {
    private static BeanFactory beanFactory;
    private static boolean isLoaded = false;
    private static String config;

    public static String getConfig() {
    	if (config==null)
    		return "classpath:spring-myproject-config.xml";
		return getConfig(config);
	}

	public static void setConfig(String config) {
		MyProjectFactory.config = config;
	}

	protected static String getConfig (String config) {
		if (config.startsWith("classpath:"))
			return config;
		return "classpath:"+config;
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
            beanFactory = new ClassPathXmlApplicationContext(configLocations, false);
            isLoaded = true;
            ((ClassPathXmlApplicationContext) beanFactory).refresh();
            System.out.println("load of the spring factory done");
        } catch (Exception ex) {
            System.out.println("Could not load Spring myproject Bean Factory");
            System.out.println(ex.getMessage());
        }
    }
}
