package net.sf.minuteProject.plugin.maven;

import org.junit.Assert;
import org.junit.Test;

public class MavenDependencyTest {

	@Test
	public void testDependency() {
		String s = "group: 'junit', name: 'junit2', version: '4.12', scope:'test'";
		MavenDependency md = new MavenDependency(s);
		checkDependency(md, "junit2", "junit", "4.12", "test");
	}
	
	@Test
	public void testDependency2() {
		String s = "group: 'junit', name: 'junit2', version: '4.12', scope='test'";
		MavenDependency md = new MavenDependency(s);
		checkDependency(md, "junit2", "junit", "4.12", "");
	}

	private void checkDependency(MavenDependency md, String artifactId, String groupId, String version, String scope) {
		Assert.assertTrue("md.getArtifactId() = '"+md.getArtifactId()+"'", md.getArtifactId().equals(artifactId));
		Assert.assertTrue("md.getGroupId() = '"+md.getGroupId()+"'", md.getGroupId().equals(groupId));
		Assert.assertTrue("md.getVersion() = '"+md.getVersion()+"'", md.getVersion().equals(version));
		Assert.assertTrue("md.getScope() = '"+md.getScope()+"'", md.getScope().equals(scope));
	}
}
