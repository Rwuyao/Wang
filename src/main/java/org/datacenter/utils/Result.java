package org.datacenter.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class  Result  {
	
	public String jwtToken;
	public int code;
	public String msg;
	public Object data;
	
	public static  Result of(String jwtToken,ResultEnum en) {
        return Result.builder()
        		.jwtToken(jwtToken)
                .code(en.getCode())
                .msg(en.getMessage())
                .build();
    }
	
	public static  Result of(ResultEnum en) {
        return Result.builder()
                .code(en.getCode())
                .msg(en.getMessage())
                .build();
    }	
	
	public static  Result of(ResultEnum en,Object data) {
        return Result.builder()
                .code(en.getCode())
                .msg(en.getMessage())
                .data(data)
                .build();
    }
}
