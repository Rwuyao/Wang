package org.datacenter.config.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.datacenter.utils.Result;
import org.datacenter.utils.ResultEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

@Component
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {


	 @Override
	    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
	        httpServletResponse
	        .getWriter()
	        .write(JSON.toJSONString(Result.of(ResultEnum.USER_NO_ACCESS)));
	    }
}

