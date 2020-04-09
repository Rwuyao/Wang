package org.datacenter.model;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource {
    private Integer id;

    @JSONField(name="text")
    private String resource;

    private String description;

    private String url;

    private Date createtime;

    private Integer parentid;

    private Boolean enable;

    @JSONField(name="nodes") 
    private List<Resource> children;
      
}