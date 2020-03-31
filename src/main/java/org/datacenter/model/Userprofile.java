package org.datacenter.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userprofile {
    private Integer id;

    private String username;

    private String headsculpture;

    private String telphone;

    private String email;

    private Integer age;

    private List<Role> roles;
    
 
}