package com.javapai.dataflow.collector.controller.dto;

public class QueryEventDTO extends ExportEventDTO {
	private int page;
	private int size;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
