package org.datacenter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.datacenter.model.User;
import org.datacenter.model.UserExample;

@Mapper
public interface  UserMapper {

	User findByUserName(String username); 
	 long countByExample(UserExample example);

	    int deleteByExample(UserExample example);

	    int deleteByPrimaryKey(Integer id);

	    int insert(User record);

	    int insertSelective(User record);

	    List<User> selectByExample(UserExample example);

	    User selectByPrimaryKey(Integer id);

	    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

	    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

	    int updateByPrimaryKeySelective(User record);

	    int updateByPrimaryKey(User record);
}
