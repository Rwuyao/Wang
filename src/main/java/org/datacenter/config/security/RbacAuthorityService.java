package org.datacenter.config.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.datacenter.model.Resource;
import org.datacenter.model.Role;
import org.datacenter.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;


@Component("rbacService")
public class RbacAuthorityService {

        private AntPathMatcher antPathMatcher = new AntPathMatcher();

        public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
            boolean hasPermission = false;
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                //ajax請求默認允許
                if(antPathMatcher.match("/ajax/**", request.getRequestURI())){
                    hasPermission = true;
                }else{
                    User user = (User) principal;
                    List<Role> roles = user.getRoles();
                    out:
                    for(Role role : roles){
                        //循環獲取角色對應的資源地址進行判斷
                    	if(role.getRolename().equals("ROLE_ADMIN")) {
                    		 hasPermission = true;
                             break ;
                    	}
                    	List<Resource> res=role.getResources()  ;                      
                        for(Resource r: res){
                            if (antPathMatcher.match(r.getUrl(), request.getRequestURI())) {
                                hasPermission = true;
                                break out;
                            }
                        }
                    }
                }
            }
            return hasPermission;
        }
}

