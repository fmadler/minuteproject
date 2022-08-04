package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j;
import net.sf.minuteProject.configuration.bean.StatementModel;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.utils.parser.ParserUtils;

@Log4j
public abstract class SddConvention extends Convention<StatementModel>{
	
	@Override
	public void apply(StatementModel model) {
		if (isValid()) {
			model.getSddPackage().getQueryPackages()
			.stream()
			.flatMap(t -> t.getListOfQueries().stream())
			.collect(Collectors.toList())
			.stream()
			.forEach (t -> apply(t));
		}else {
			log.error("Convention '"+this.getClass().getName()+"' is not valid and thus not applied!");
			log.error("Convention '"+this.getClass().getName()+"' value \n"+toString());
		}
	}
	
	protected abstract boolean isValid();
	protected abstract void apply(Query<?> query);
	
	protected boolean isBeginningMatch(Query<?> query, String pattern) {
		String tableNameLc = query.getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (tableNameLc.startsWith(s))
				return true;
		}
		return false;
	}
	
	protected boolean isEndingMatch(Query<?> query, String pattern) {
		String tableNameLc = query.getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (tableNameLc.endsWith(s))
				return true;
		}
		return false;
	}
	
	protected boolean containsMatch(Query<?> query, String pattern) {
		String tableNameLc = query.getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (tableNameLc.contains(s))
				return true;
		}
		return false;
	}
	
	protected boolean isBelongsToPackage(Query<?> query, String pattern) {
		String packageNameLowerCase = query.getPackage().getName().toLowerCase();
		for (String s : ParserUtils.getList(pattern.toLowerCase())) {
			if (packageNameLowerCase.endsWith(s))
				return true;
		}
		return false;
	}
	
	protected boolean doesNotBelongToPackage(Query<?> query, String pattern) {
		return !isBelongsToPackage(query, pattern);
	}

}
