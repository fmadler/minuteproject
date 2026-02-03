package net.sf.minuteProject.plugin.maven;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
		Assertions.assertThat(md.getArtifactId()).isEqualTo(artifactId);
		Assertions.assertThat(md.getGroupId()).isEqualTo(groupId);
		Assertions.assertThat(md.getVersion()).isEqualTo(version);
		Assertions.assertThat(md.getScope()).isEqualTo(scope);
	}
}
