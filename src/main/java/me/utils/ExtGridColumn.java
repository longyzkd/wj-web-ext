package me.utils;

public class ExtGridColumn {

	public ExtGridColumn() {
		// TODO Auto-generated constructor stub
	}

	private String header;
	private String dataIndex;
	private boolean sortable = true;
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getDataIndex() {
		return dataIndex;
	}
	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}
	public boolean isSortable() {
		return sortable;
	}
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}
	
	
}
