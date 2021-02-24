package net.sf.minuteProject.configuration.bean.enrichment.convention;

import java.util.ArrayList;
import java.util.List;

import net.sf.minuteProject.configuration.bean.BusinessModel;
import net.sf.minuteProject.configuration.bean.enrichment.BusinessModelEnrichment;
import net.sf.minuteProject.configuration.bean.enrichment.Entity;
import net.sf.minuteProject.configuration.bean.enrichment.Field;
import net.sf.minuteProject.configuration.bean.model.data.Column;
import net.sf.minuteProject.configuration.bean.model.data.Reference;
import net.sf.minuteProject.configuration.bean.model.data.Table;
import net.sf.minuteProject.plugin.format.I18nUtils;
import net.sf.minuteProject.utils.ReferenceUtils;
import net.sf.minuteProject.utils.enrichment.EnrichmentUtils;

public class ReferenceNamingConvention extends ModelConvention {

	public final String APPLY_REFERENCED_ALIAS_WHEN_NO_AMBIGUITY="apply-referenced-alias-when-no-ambiguity";
	public final String APPLY_REFERENCED_CHILD_ALIAS_WHEN_NO_AMBIGUITY="apply-referenced-child-alias-when-no-ambiguity";
	//TODO
	public final String APPLY_REFERENCED_ALIAS_WHEN_NO_AMBIGUITY_AND_NOT_PRESENT="apply-referenced-alias-when-no-ambiguity-and-not-present";
	public final String APPLY_MANY_TO_MANY_ALIASING = "apply-many-to-many-aliasing";
	
	private boolean isToPlurialize;
	
	@Override
	public void apply(BusinessModel model) {
		if (APPLY_REFERENCED_ALIAS_WHEN_NO_AMBIGUITY.equals(type)) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					//apply (table); //TODO fix bug in liferay to enable m2m and child relationships
					applyForChildren (table);
				}
			}
		}
		else if (APPLY_REFERENCED_CHILD_ALIAS_WHEN_NO_AMBIGUITY.equals(type)) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					applyForChildren (table);
				}
			}
		}
		else if (APPLY_MANY_TO_MANY_ALIASING.equals(type)) {
			if (model.getBusinessPackage()!=null) {
				for (Table table : model.getBusinessPackage().getEntities()) {
					/*if (table.isManyToMany()) {
						applyM2MAlias (table);
					} else {
						applyMany2ManyReference(table);
					}*/
					applyMany2ManyReference(table);
				}
			}
		}
	}

	private void applyM2MAlias(Table m2m) {
		// get table other end 
		for (Reference parent : m2m.getParents()) {
			String retrievedAlias = retrieveAlias(m2m, parent);
			if (retrievedAlias!=null)
				parent.setAlias(retrievedAlias);
				//( ReferenceUtils.getReverseReference(parent)).setAlias(retrievedAlias);
		}
		// check that reference is enriched
	}

	private String retrieveAlias(Table m2m, Reference parent) {
		String retrievedAlias = getRetrievedAlias (m2m, parent);
		if (retrievedAlias!=null)
			if (isToPlurialize)
				retrievedAlias = I18nUtils.plurialize(retrievedAlias);
		return retrievedAlias;
	}

	private String getRetrievedAlias2 (Table m2m, Reference parent) {
		// take parent => get parent table and column
		Table table = parent.getLocalTable();
		Column column = parent.getLocalColumn();
		return parent.getLocalColumnName();
		// on parent column check if no ambiguity
		// if not add parent

	}
	
	private String getRetrievedAlias (Table table, Reference parent) {
		Field field = getRetrievedField(table, parent);
		if (field!=null)
			return field.getLinkReferenceAlias();
		return null;
	}

	private Field getRetrievedField(Table table, Reference parent) {
		BusinessModel businessModel = table.getDatabase().getDataModel().getModel().getBusinessModel();
		if (businessModel!=null){
			BusinessModelEnrichment enrichment = businessModel.getEnrichment();
			for (Entity entity : enrichment.getEntities()){
				if (net.sf.minuteProject.utils.StringUtils.compareName(entity.getName(), table.getName())) {
//					System.out.println(">>> table "+entity.getName()+"--"+ table.getName());
					for (Field field : entity.getFields()) {
						if (net.sf.minuteProject.utils.StringUtils.compareName(field.getLinkToTargetEntity(), parent.getForeignTable().getName())) {
//							System.out.println(">>> field "+field.getName()+"-link: "+field.getLinkToTargetEntity()+"- parent: "+parent.getForeignTable().getName());
							return field;
						}
					}
				}
			}
		}
		return null;
	}

	private void apply(Table table) {
		applyNotMany2ManyReference(table);
		applyMany2ManyReference(table);
	}
	
	private void applyForChildren(Table table) {
		applyNotMany2ManyReference(table);

	}

	private void applyMany2ManyReference (Table table) {
		List<Reference> list = getReferenceChildrenAndMany2Many(table);
		for (Reference reference : EnrichmentUtils.getLinkedTargetReferenceByMany2Many(table)) {
			if (!reference.getForeignTable().isManyToMany()) {
				if (isNoAmbiguityReference(reference, list)) {
					//TODO compare with all reference m2m+children
					reference.setAlias(getNameForUnambiguiousCaseAndMany2Many(table, reference));
					reference.getAlias();
				}
				else {
//					System.out.println(">>>>>>>>>> a "+reference.getAlias());
//					if (StringUtils.isEmpty(reference.getAlias()) 
//							&& !(ReferenceUtils.getDefaultAlias(reference)).equals(reference.getAlias())) {
//						System.out.println(">>>>>>>>>> alias");
						reference.setAlias(getNameForAmbiguiousCaseAndMany2Many(table, reference));
//					}
				} 				
			} 
		}	
	}

	private void applyNotMany2ManyReference(Table table) {
		List<Reference> list = getReferenceChildrenAndMany2Many(table);
		for (Reference reference : table.getChildren()) {
			if (!reference.getForeignTable().isManyToMany()) {
				if (isNoAmbiguityReference(reference, list))//TODO compare with all reference m2m+children
					reference.setAlias(getNameForUnambiguiousCaseAndNotMany2Many(reference.getForeignTable()));
				else 
					reference.setAlias(getNameForAmbiguiousCaseAndNotMany2Many(table, reference));
			} 
		}		
	}

	private List<Reference> getReferenceChildrenAndMany2Many (Table table) {
		List<Reference> list = new ArrayList<Reference>();
		for (Reference reference : table.getChildren()) {
			list.add(reference);
		}
		for (Reference reference : EnrichmentUtils.getLinkedTargetReferenceByMany2Many(table)) {
			list.add(reference);
		}
		return list;
	}
	
	private String getNameForUnambiguiousCaseAndNotMany2Many(Table table) {
		return getFinalName(table.getAlias());
	}

	public String getNameForAmbiguiousCaseAndNotMany2Many(Table table, Reference reference) {
		String name = reference.getForeignTable().getAlias()+"_"+reference.getForeignColumn().getAlias();
		return getFinalName(name);
	}

	private String getNameForUnambiguiousCaseAndMany2Many(Table table, Reference reference) {
		String name = reference.getForeignTable().getAlias();
		return getFinalName(name);
	}

	public String getNameForAmbiguiousCaseAndMany2Many(Table table, Reference reference) {
		//{targetTableVariableName}Via${linkTableName}By${localColumnName}s
		//String name = reference.getForeignTable().getAlias()+"_VIA_"+reference.getLocalTable().getAlias()+"_BY_"+reference.getForeignColumn().getAlias();
		String name = ReferenceUtils.getDefaultAliasForManyToMany(reference);//reference.getForeignTable().getAlias()+"_VIA_"+reference.getLocalTable().getAlias()+"_BY_"+reference.getLocalColumnName();
		
		return getFinalName(name);
	}
	
	private String getFinalName(String name) {
		return (!isToPlurialize)?name:I18nUtils.plurializeUppercase(name);
	}
	private boolean isNoAmbiguityReference (Reference reference, List<Reference> list) {
		int cpt = 0;
		for (Reference ref : list) {
			if (ref.getForeignTableName().equals(reference.getForeignTableName()))
				cpt++;
		}
		if (cpt>1)
			return false;
		return true;
	}

	public boolean isToPlurialize() {
		return isToPlurialize;
	}

	public void setIsToPlurialize(boolean isToPlurialize) {
		this.isToPlurialize = isToPlurialize;
	}

}
