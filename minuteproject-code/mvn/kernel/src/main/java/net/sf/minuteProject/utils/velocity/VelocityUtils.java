package net.sf.minuteProject.utils.velocity;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import net.sf.minuteProject.configuration.bean.Target;
import net.sf.minuteProject.configuration.bean.TemplateTarget;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.Technology;
import net.sf.minuteProject.loader.catalog.technologycatalog.node.TechnologyCatalog;

public class VelocityUtils {

	public static String getCommentChunk() {
		return "##";
	}

	public static int getIndexFromCount(int count) {
		return count - 1; //for some obscure reason $foreach.index does not work
	}
}
