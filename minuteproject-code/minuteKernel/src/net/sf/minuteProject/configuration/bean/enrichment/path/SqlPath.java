package net.sf.minuteProject.configuration.bean.enrichment.path;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.sf.minuteProject.configuration.bean.AbstractConfiguration;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SqlPath extends AbstractConfiguration{

	private String path, root;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
	
}
