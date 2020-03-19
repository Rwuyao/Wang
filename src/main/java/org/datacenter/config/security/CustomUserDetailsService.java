package org.datacenter.config.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.datacenter.model.Resource;
import org.datacenter.model.Role;
import org.datacenter.model.User;
import org.datacenter.service.RoleService;
import org.datacenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userService.findByUserName(username);
		if(null==user){
				throw new UsernameNotFoundException("UserName "+username+" not found");
		}			 	
		
		List<String> nameList = user.getRoles().stream().map(Role::getRolename).collect(Collectors.toList());			
		List<Role> rs=roleService.findRoleByRoleName(nameList);
		user.setRoles(rs);	
		return user;
	}

}

