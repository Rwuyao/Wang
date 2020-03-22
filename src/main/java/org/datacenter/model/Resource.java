package org.datacenter.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
	
	private int id;
	private String resource;
	private String description;
	private String url;
	private Date createtime;
}
