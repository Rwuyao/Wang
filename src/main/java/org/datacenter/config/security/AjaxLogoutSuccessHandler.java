package org.datacenter.config.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.datacenter.utils.Result;
import org.datacenter.utils.ResultEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

	 
	    @Override
	    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
	        	        
	        httpServletResponse
	        .getWriter()
	        .write(JSON.toJSONString(Result.of(ResultEnum.USER_LOGOUT_SUCCESS)));
	    }
}
