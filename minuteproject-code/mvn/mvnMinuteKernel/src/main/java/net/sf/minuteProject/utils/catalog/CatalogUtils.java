package net.sf.minuteProject.utils.catalog;

import net.sf.minuteProject.exception.MinuteProjectException;
import net.sf.minuteProject.loader.catalog.databasecatalog.Databasecatalog;
import net.sf.minuteProject.loader.catalog.databasecatalog.DatabasecatalogHolder;
import net.sf.minuteProject.loader.catalog.technologycatalog.Technologycatalog;
import net.sf.minuteProject.loader.catalog.technologycatalog.TechnologycatalogHolder;

public class CatalogUtils {

	private static final String NO_TECHNOLOGY_CATALOG_FOUND = "No technology catalog found";
	private static TechnologycatalogHolder technologycatalogHolder;
	private static DatabasecatalogHolder databasecatalogHolder;

	public static void resetTechnologycatalogHolder () {
		technologycatalogHolder = null;
	}
	
	public static void resetDatabasecatalogHolder () {
		databasecatalogHolder = null;
	}
	
	public static TechnologycatalogHolder getPublishedTechnologyCatalogHolder(String catalogDir) throws MinuteProjectException {
		if (catalogDir==null)
			catalogDir=getDefaultCatalogDir();		
		if (technologycatalogHolder==null)
			technologycatalogHolder = loadTechnologyCatalogHolder(catalogDir+"/technology-catalog.xml");
		return technologycatalogHolder;
	}
	
	public static TechnologycatalogHolder loadTechnologyCatalogHolder(String name) throws MinuteProjectException {
		try {
			return getTechnologyCatalogLoader(name).load();
		} catch (Exception e) {
			MinuteProjectException.throwException(CatalogUtils.class, NO_TECHNOLOGY_CATALOG_FOUND);
			return new TechnologycatalogHolder();
		}
	}

	private static Technologycatalog getTechnologyCatalogLoader(String name) {
		return new Technologycatalog(name);
	}
	
	public static DatabasecatalogHolder getPublishedDatabaseCatalogHolder(String catalogDir) {
		if (catalogDir==null)
			catalogDir=getDefaultCatalogDir();
		if (databasecatalogHolder==null)
			databasecatalogHolder = loadDatabaseCatalogHolder(catalogDir+"/database-catalog.xml");
		return databasecatalogHolder;
	}
	
	public static DatabasecatalogHolder loadDatabaseCatalogHolder(String name) {
		try {
			return getDatabaseCatalogLoader(name).load();
		} catch (Exception e) {
			e.printStackTrace();
			return new DatabasecatalogHolder();
		}
	}

	private static Databasecatalog getDatabaseCatalogLoader(String name) {
		return new Databasecatalog(name);
	}

	public static String getDefaultCatalogDir () {
		return "catalog";
	}

}
