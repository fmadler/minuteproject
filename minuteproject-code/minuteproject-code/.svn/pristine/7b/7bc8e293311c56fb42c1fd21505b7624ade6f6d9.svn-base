package net.sf.minuteProject.plugin.maven;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

public class MavenDependency {

	private final String groupId, artifactId, version, scope;

	public MavenDependency(String stringFmtDep) {
		// TODO Auto-generated constructor stub
		stringFmtDep = StringUtils.remove(stringFmtDep, " ");
		Map<String, String> map = Arrays.stream(stringFmtDep.split(","))
			.filter(u -> StringUtils.contains(u,":"))
			.collect(Collectors.toMap(
		            e -> e.split(":")[0],
		            e -> e.split(":")[1]
		        ))
			;
//			.map(s -> s.split(":"))
//			.reduce(v ->  {
//				
//			})
//			.forEach(u -> {
//
//				System.out.println(">>> "+u.toString());
//			});
//			.flatMap (u -> {
//				System.out.println(">>> "+u);
//			})
			;
		if (map.containsKey("group")) {
			this.groupId = getString(map.get("group"));
		} else {
			this.groupId = "";
		}
		if (map.containsKey("name")) {
			this.artifactId = getString(map.get("name"));
		} else {
			this.artifactId = "";
		}
		if (map.containsKey("version")) {
			this.version = getString(map.get("version"));
		} else {
			this.version = "";
		}
		if (map.containsKey("scope")) {
			this.scope = getString(map.get("scope"));
		} else {
			this.scope = "";
		}
	}

	private String getString (String s) {
		return StringUtils.remove(s,"'");
	}
	
	public String getArtifactId() {
		return artifactId;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getScope() {
		return scope;
	}

	public String getVersion() {
		return version;
	}
	
	public boolean hasScope () {
		return StringUtils.isNotBlank(scope);
	}
	
}
