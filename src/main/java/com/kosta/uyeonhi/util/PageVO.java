package com.kosta.uyeonhi.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import groovy.transform.ToString;
import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;

public class PageVO {
	private static final int default_size = 5;
	private static final int default_max_size = 50;
	
	private int page;
	private int size;
	
	private String type;
	private String keyword;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public PageVO() {
		this.page = 1;
		this.size = default_size;
	}

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page<0? 1: page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size<default_size|| size>default_max_size? default_size:size;
	}
	
	public Pageable makePageable(int direction,int page, String... props) {
		Sort.Direction dir = direction == 0? Sort.Direction.DESC : Sort.Direction.ASC;
		return PageRequest.of(page, this.size, dir, props);
	}
}
