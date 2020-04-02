package org.datacenter.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    private Integer id;

    private String username;

    private String rolename;

	public UserRole(String username, String rolename) {
		super();
		this.username = username;
		this.rolename = rolename;
	}

   
}