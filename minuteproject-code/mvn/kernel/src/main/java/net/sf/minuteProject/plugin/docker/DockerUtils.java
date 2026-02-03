package net.sf.minuteProject.plugin.docker;

import net.sf.minuteProject.configuration.bean.Template;

import java.util.Arrays;

public class DockerUtils {

    public static boolean hasDbScope(Template template) {
        return hasScope(template, "db");
    }

    public static boolean hasTomcatScope(Template template) {
        return hasScope(template, "tomcat");
    }

    public static boolean hasApacheScope(Template template) {
        return hasScope(template, "apache");
    }

    private static boolean hasScope(Template template, String scope) {
        return Arrays.stream(template.getPropertyValue("scopes").split(",")).anyMatch(u -> u.equalsIgnoreCase(scope));
    }
}
