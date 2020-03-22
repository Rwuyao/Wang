package org.datacenter.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.datacenter.utils.Result;
import org.datacenter.utils.ResultEnum;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

	 @Override
	    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
		 Result  res;
		 if (e instanceof BadCredentialsException ||e instanceof UsernameNotFoundException) {
			 res = Result.of(ResultEnum.ACCOUNT_PASSWORD_WORNG);
	        } else if (e instanceof LockedException) {
	        	res =Result.of(ResultEnum.ACCOUNT_LOCK);
	        } else if (e instanceof CredentialsExpiredException) {
	        	res = Result.of(ResultEnum.PASSWORD_EXPIRE);
	        } else if (e instanceof AccountExpiredException) {
	        	res =Result.of(ResultEnum.ACCOUNT_EXPIRE);
	        } else if (e instanceof DisabledException) {
	        	res =Result.of(ResultEnum.ACCOUNT_FORBID);
	        } else {
	        	res =Result.of(ResultEnum.USER_LOGIN_FAILED);
	        }		 
		  httpServletResponse
		  .setCharacterEncoding("UTF-8");
		  httpServletResponse
		  .setContentType("text/html;charset=utf-8");
		  
		 
		 httpServletResponse
	     .getWriter()
	     .write(JSON.toJSONString(res));
	    }

}
