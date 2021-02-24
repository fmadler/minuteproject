package net.sf.minuteProject.configuration.bean.model.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.sf.minuteProject.configuration.bean.AbstractConfiguration;
import net.sf.minuteProject.configuration.bean.Package;
import net.sf.minuteProject.configuration.bean.Template;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.configuration.bean.model.data.constant.Direction;

public class Composite extends AbstractConfiguration {

	// TODO change Composite to embrace composite pattern
	private Composites composites;
	private Composite inputComposite, outputComposite;
	private Direction direction;
	private List<Table> entities;
	private String executionType;
	private Package pack;
	private String packageName;

	private List<CompositeQueryElement> queries;

	public void setComposites(Composites composites) {
		this.composites = composites;
	}

	public String getExecutionType() {
		return executionType;
	}

	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}

	public List<CompositeQueryElement> getQueries() {
		if (queries == null)
			queries = new ArrayList<CompositeQueryElement>();
		return queries;
	}

	public void setQueries(List<CompositeQueryElement> queries) {
		this.queries = queries;
	}

	public Composites getComposites() {
		return composites;
	}

	public void addQuery(CompositeQueryElement query) {
		query.setComposite(this);
		getQueries().add(query);
	}

	public Composite getComposite(Direction direction) {
		if (Direction.IN.equals(direction))
			return getInputComposite();
		else
			return getOutputComposite();
	}

	public Composite getInputComposite() {
		if (inputComposite != null)
			return inputComposite;
		return retrieveComposite(Direction.IN);
	}

	public Composite getOutputComposite() {
		if (outputComposite != null)
			return outputComposite;
		return retrieveComposite(Direction.OUT);
	}

	private Composite retrieveComposite(Direction dir) {
		Composite composite = new Composite();
		composite.direction = dir;
		composite.setName(this.name);
		composite.setExecutionType(this.executionType);
		composite.setPackage(this.getPackage());
		composite.setQueries(getQueries());
		for (CompositeQueryElement query : getQueries()) {
			Query q = query.getQuery();
			Table table = q.getEntity(dir);
			// table.setName(q.getName());
			table.setPackage(q.getPackage());
			composite.getEntities().add(table);
		}
		return composite;
	}

	public List<Table> getEntities() {
		if (entities == null)
			entities = new ArrayList<Table>();
		return entities;
	}

	public void setEntities(List<Table> entities) {
		this.entities = entities;
	}

	public List<Column> getDistinctInputColumn() {
		//https://stackoverflow.com/questions/23699371/java-8-distinct-by-property
/*		
		String [] arr = {"ABQ","ALB","CHI","CUN","PHX","PUJ","BWI"};
        //final ImmutableList<String> arpts = ImmutableList.of("ABQ","ALB","CHI","CUN","PHX","PUJ","BWI");

		List<String> arpts = Arrays.asList(arr);
        arpts.stream().filter(distinctByKey(f -> f.substring(0,1))).forEach(s -> System.out.println(s));
*/
		return getInputComposite().getEntities()
			.stream()
			.flatMap(u -> Arrays.asList(u.getColumns())
			.stream())
			.collect(Collectors.toList())
			.stream()
			.filter(distinctByKey(p -> p.getName()))
			.collect(Collectors.toList());

	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    Map<Object,Boolean> seen = new ConcurrentHashMap<>();
	    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	/*
	public static <Column> Predicate<Column> distinctColumn(Function<Column, ?> keyExtractor) {
		return distinctByKey(keyExtractor);
	}
	*/

	public String getTechnicalPackage(Template template) {
		net.sf.minuteProject.configuration.bean.Package p = getPackage();
		if (p == null)
			return "ERROR_COMPOSITE_PACKAGE_IS_NULL";
		return p.getTechnicalPackage(template);
	}

	public Package getPackage() {
		return pack;
	}

	public void setPackage(Package pack) {
		this.pack = pack;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isDirection(Direction direction) {
		return (this.direction == direction);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
