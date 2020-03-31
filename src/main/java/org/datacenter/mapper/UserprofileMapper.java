package org.datacenter.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.datacenter.model.Userprofile;
import org.datacenter.model.UserprofileExample;

public interface UserprofileMapper {
	
	Userprofile findByUserName(String username); 
	
	long countByExample(UserprofileExample example);

    int deleteByExample(UserprofileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Userprofile record);

    int insertSelective(Userprofile record);

    List<Userprofile> selectByExample(UserprofileExample example);

    Userprofile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Userprofile record, @Param("example") UserprofileExample example);

    int updateByExample(@Param("record") Userprofile record, @Param("example") UserprofileExample example);

    int updateByPrimaryKeySelective(Userprofile record);

    int updateByPrimaryKey(Userprofile record);
}