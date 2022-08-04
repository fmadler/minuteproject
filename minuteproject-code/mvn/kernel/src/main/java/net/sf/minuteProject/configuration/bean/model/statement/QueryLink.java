package net.sf.minuteProject.configuration.bean.model.statement;

import lombok.Data;
import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueryLink extends AbstractConfiguration {
    public String type; //TODO add enum
    public String queryName;
    public List<QueryLinkParam> queryLinkParams = new ArrayList<>();

    public void addQueryLinkParam(QueryLinkParam queryLinkParam) {
        queryLinkParams.add(queryLinkParam);
    }

}
