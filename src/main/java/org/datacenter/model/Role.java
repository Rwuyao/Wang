package org.datacenter.model;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role  implements GrantedAuthority{
	
	private int id;
	private String rolename;
	private String description;
	private Date createtime;
	private List<Resource> Resources;
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return rolename;
	} 
		
}
