package org.datacenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.datacenter.model.Role;

@Mapper
public interface  RoleMapper {

	List<Role> findRoleByRoleName(List<String> list);  
}
