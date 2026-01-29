package net.sf.minuteProject.configuration.bean.mock;

import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;
import net.sf.minuteProject.configuration.bean.system.Property;

import java.util.Arrays;
import java.util.List;

public class TemplateMock {

    private TemplateMock(){}

    private static Property getProperty(String name, String value) {
        Property property = new Property();
        property.setName(name);
        property.setValue(value);
        return property;
    }

    private static Property getPropertyPropertyPresence(String value) {
        return getProperty("property-presence", value);
    }
    private static Property getPropertyPropertyPresenceValue(String value) {
        return getProperty("property-presence-value", value);
    }

    public static Template getTemplateWithPropPresence(String propPresence, String propValue) {
        Template template = new Template();
        template.setTemplateTarget(getTemplateTarget());
        List<Property> properties = Arrays.asList(getPropertyPropertyPresence(propPresence), getPropertyPropertyPresenceValue(propValue));
        template.setProperties(properties);
        return template;
    }

    public static Template getTemplateWithPropPresenceWithProperty(String propPresence, String propValue, Property property) {
        Template template = getTemplateWithPropPresence(propPresence, propValue);
        template.addProperty(property);
        return template;
    }

    private static TemplateTarget getTemplateTarget() {
        TemplateTarget templateTarget = new TemplateTarget();
        return templateTarget;
    }

    public static Property getPropSecurityAuthenticationTypeCas() {
        return getProperty("securityAuthenticationType","cas");
    }
}
