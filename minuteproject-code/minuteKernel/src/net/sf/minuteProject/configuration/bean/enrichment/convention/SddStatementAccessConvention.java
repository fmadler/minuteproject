package net.sf.minuteProject.configuration.bean.enrichment.convention;

import net.sf.minuteProject.configuration.bean.StatementModel;
import net.sf.minuteProject.configuration.bean.model.statement.Queries;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.utils.parser.ParserUtils;

class SddStatementAccessConvention extends SddConvention{

	private String securePackages;
	private String roles;
	
	public SddStatementAccessConvention () {
	}
	@Override
	public void apply(StatementModel t) {
		Queries queries = t.getQueries();
		if (queries!=null) {
			for (Query query : queries.getQueries()) {
				if (ParserUtils.isInListLowerCase (query.getPackageName(), securePackages)) {
					query.getSecurityColor().setRoles(roles);
					//TODO to improve 
					query.getPackage().getSecurityColor().setRoles(roles);
					
				}
			}
		}
	}
	public String getSecurePackages() {
		return securePackages;
	}
	public void setSecurePackages(String securePackages) {
		this.securePackages = securePackages;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	@Override
	protected boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void apply(Query<?> query) {
		// TODO Auto-generated method stub
		
	}

	
}
