package net.sf.minuteProject.configuration.bean.model.statement;

import lombok.Data;
import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

@Data
public class QueryPostProcessor extends AbstractConfiguration {
    public Query query;
    public String type;
    public String templateName;
    public String renditionType;
}
