package org.datacenter.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    private int id;

    private String username;

    private String role;

	public UserRole(String username, String role) {
		super();
		this.username = username;
		this.role = role;
	}

   
}