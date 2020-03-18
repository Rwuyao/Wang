package org.datacenter.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private String account;
	private String password;
	private String role;
	private String username;
	private int sex;
	private Date createtime;	
}
