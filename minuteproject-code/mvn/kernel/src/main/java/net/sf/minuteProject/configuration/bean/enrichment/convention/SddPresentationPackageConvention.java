package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.apache.commons.lang.StringUtils;

import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.StatementModel;
import net.sf.minuteProject.configuration.bean.model.statement.Query;
import net.sf.minuteProject.utils.parser.ParserUtils;

public class SddPresentationPackageConvention extends SddConvention{

	private String packageDisplayNameStripPrefixStartWith;
	private String packageOrderName;
	
	public SddPresentationPackageConvention () {
	}
	@Override
	public void apply(StatementModel t) {
		t.getSddPackage().getQueryPackages()
			.stream()
			.forEach (u -> {
				u.setDisplayName(getDisplayName(u));
				u.setDisplayOrder(getDisplayOrder(u));
			})
			;
	}
	private int getDisplayOrder(Package u) {
		List<String> list = ParserUtils.getListLowerCase(packageOrderName);
		OptionalInt ix = IntStream.range(0, list.size())
			.filter(i -> list.get(i).equals(u.getAlias()))
			.findFirst();
		return ix.isPresent()?ix.getAsInt():99;

	}
	private String getDisplayName(Package u) {
		String packName = u.getName();
		for (String s : ParserUtils.getListLowerCase(packageDisplayNameStripPrefixStartWith)) {
			if (packName.toLowerCase().startsWith(s.toLowerCase())) {
				return StringUtils.removeStartIgnoreCase(packName, s);
			}
		}
		return packName;
	}
	public String getPackageDisplayNameStripPrefixStartWith() {
		return packageDisplayNameStripPrefixStartWith;
	}
	public void setPackageDisplayNameStripPrefixStartWith(String packageDisplayNameStripPrefixStartWith) {
		this.packageDisplayNameStripPrefixStartWith = packageDisplayNameStripPrefixStartWith;
	}
	public String getPackageOrderName() {
		return packageOrderName;
	}
	public void setPackageOrderName(String packageOrderName) {
		this.packageOrderName = packageOrderName;
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
