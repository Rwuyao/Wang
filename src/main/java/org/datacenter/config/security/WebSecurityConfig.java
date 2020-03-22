package org.datacenter.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private AjaxAuthenticationEntryPoint authenticationEntryPoint;  //  未登陆时返回 JSON 格式的数据给前端（否则为 html）

	 @Autowired
	 private AjaxAuthenticationSuccessHandler authenticationSuccessHandler;  // 登录成功返回的 JSON 格式数据给前端（否则为 html）

	 @Autowired
	 private AjaxAuthenticationFailureHandler authenticationFailureHandler;  //  登录失败返回的 JSON 格式数据给前端（否则为 html）

	 @Autowired
	 private AjaxLogoutSuccessHandler  logoutSuccessHandler;  // 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）

	 @Autowired
	 private AjaxAccessDeniedHandler accessDeniedHandler;    // 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）

	
	@Autowired 
	private CustomUserDetailsService customUserDetailsService;
	
	 @Autowired
	 private DataSource dataSource;	
	 
	 @Bean
	 public PersistentTokenRepository persistentTokenRepository(){
	     JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
	     tokenRepository.setDataSource(dataSource);
	     // 如果token表不存在，使用下面语句可以初始化该表；若存在，会报错。
	     //tokenRepository.setCreateTableOnStartup(true);
	     return tokenRepository;
	 }
	
	 
	// 装载BCrypt密码编码器
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
		
		@Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth 
			// 设置UserDetailsService
	        .userDetailsService(customUserDetailsService)
	        // 使用BCrypt进行密码的hash
	        .passwordEncoder(passwordEncoder());
	    }
	 
	 
	 @Override
	 public void configure(WebSecurity web)throws Exception{
	        web
	        .ignoring()
	        .antMatchers("/js/**","/css/**","/images/**","/vendor/**","/fonts/**");
	 }
	
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		//session配置
		  http
		  .csrf().disable()
		  .sessionManagement()      
		  .maximumSessions(5)//指定最大登录数      
          .maxSessionsPreventsLogin(false); //当达到最大值时，是否保留已经登录的用户
	       
		//登录配置
          http
          .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
	      .and()
	      .exceptionHandling()
	      .accessDeniedHandler(accessDeniedHandler)
	      .and()
       	  .formLogin()
          .loginPage("/signin")//登录页面
          .loginProcessingUrl("/login")//发送Ajax请求的路径
          .usernameParameter("username")//请求验证参数
          .passwordParameter("password")//请求验证参数
          .successHandler(authenticationSuccessHandler) // 登录成功
          .failureHandler(authenticationFailureHandler) // 登录失败
          .and()
          .logout()
          .logoutRequestMatcher(new AntPathRequestMatcher("/signout","POST"))
          .logoutSuccessHandler(logoutSuccessHandler);
           
         //权限配置
           http.authorizeRequests() //允许匿名访问 登录，注册，退出 "/signout","/signin"
          .antMatchers("/join").permitAll()
          .anyRequest().access("@rbacService.hasPermission(request,authentication)");// RABC 表达式管控
          //.authenticate
			
         //rememberMe 配置
           http
           .rememberMe()
           .rememberMeParameter("remember-me")
           .userDetailsService(customUserDetailsService)
           .tokenRepository(persistentTokenRepository())
           .tokenValiditySeconds(86400);

    }
	
	
	
	
}
