package org.datacenter.config.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.datacenter.utils.Result;
import org.datacenter.utils.ResultEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        
		  httpServletResponse
		  .setCharacterEncoding("UTF-8");
		  httpServletResponse
		  .setContentType("text/html;charset=utf-8");
		  
		
		httpServletResponse
        .getWriter()
        .write(JSON.toJSONString(Result.of(ResultEnum.USER_NEED_AUTHORITIES)));
	}
}

