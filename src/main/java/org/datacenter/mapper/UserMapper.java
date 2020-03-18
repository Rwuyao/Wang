package org.datacenter.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.datacenter.model.User;

@Mapper
public interface  UserMapper {

	User getUser(String account);  
}
