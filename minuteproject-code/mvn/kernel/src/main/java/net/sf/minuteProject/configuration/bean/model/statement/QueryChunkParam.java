package net.sf.minuteProject.configuration.bean.model.statement;

public class QueryChunkParam {
	
	private String name, id, type;
	private int size, scale;
	private QueryChunk queryChunk;

	public void setQueryChunk(QueryChunk queryChunk) {
		this.queryChunk = queryChunk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public QueryChunk getQueryChunk() {
		return queryChunk;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}


}
