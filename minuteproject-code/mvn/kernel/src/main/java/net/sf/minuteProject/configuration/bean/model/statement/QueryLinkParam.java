package net.sf.minuteProject.configuration.bean.model.statement;

import lombok.Data;
import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

@Data
public class QueryLinkParam extends AbstractConfiguration {
    public boolean isMandatory;
    public String inputFieldName;
    public String inputQueryParamName;
}
