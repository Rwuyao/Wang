package org.datacenter.config.security;

import java.util.Collection;
import java.util.List;

import org.datacenter.model.Role;
import org.datacenter.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail implements UserDetails {
	
	 private Integer id;
     private String username;
     private String password;
     private List<Role> authorities;

	
     public UserDetail(User user) {
         id = user.getId();
         username = user.getUsername();
         password = user.getPassword();
         authorities =user.getRoles();
     }

     
	
	@Override
	public List<Role> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
