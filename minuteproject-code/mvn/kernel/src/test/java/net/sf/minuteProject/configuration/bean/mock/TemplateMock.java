package net.sf.minuteProject.configuration.bean.mock;

import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.TemplateTarget;
import net.sf.minuteProject.configuration.bean.system.Property;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return getTemplateWithPropPresenceWithProperty(propPresence, propValue);
    }

    public static Template getTemplateWithPropPresenceWithProperty(String propPresence, String propValue, Property ...properties) {
        Template template = new Template();
        template.setTemplateTarget(getTemplateTarget());
        final List<Property> props = Arrays.asList(getPropertyPropertyPresence(propPresence), getPropertyPropertyPresenceValue(propValue));
        final List<Property> collect = Stream.concat(
                props.stream(),
                Stream.of(properties)
        ).collect(Collectors.toList());
        template.setProperties(collect);
        return template;
    }

    private static TemplateTarget getTemplateTarget() {
        TemplateTarget templateTarget = new TemplateTarget();
        return templateTarget;
    }

    public static Property getPropSecurityAuthenticationTypeCas() {
        return getProperty("securityAuthenticationType","cas");
    }

    public static Property getPropSecurityAuthenticationTypeBasic() {
        return getProperty("securityAuthenticationType","basic");
    }
}
