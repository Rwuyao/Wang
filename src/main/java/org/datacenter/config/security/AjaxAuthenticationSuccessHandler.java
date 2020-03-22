package org.datacenter.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.datacenter.utils.Result;
import org.datacenter.utils.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest  httpServletRequest , HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		
		  httpServletResponse
		  .setCharacterEncoding("UTF-8");
		  httpServletResponse
		  .setContentType("text/html;charset=utf-8");
		  
		  httpServletResponse 
		  .getWriter()
		  .write(JSON.toJSONString(Result.of(ResultEnum.SUCCESS)));
		 
		

	}

}
