package org.datacenter.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	
	 @Override
	 public void configure(WebSecurity web)throws Exception{
	        web
	        .ignoring()
	        .antMatchers("/js/**","/css/**","/images/**","/bootstrap/**","/fonts/**");
	 }
	
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		//session配置
		  http
		  .sessionManagement()         
          .maximumSessions(1)//指定最大登录数      
          .maxSessionsPreventsLogin(false); //当达到最大值时，是否保留已经登录的用户
	       
		//登录配置
          http
          .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
	      .and()
	      .exceptionHandling()
	      .accessDeniedHandler(accessDeniedHandler)
	      .and()
       	  .formLogin()
          .loginPage("/login")
          .loginProcessingUrl("/login")//发送Ajax请求的路径
          .usernameParameter("username")//请求验证参数
          .passwordParameter("password")//请求验证参数
          .successHandler(authenticationSuccessHandler) // 登录成功
          .failureHandler(authenticationFailureHandler) // 登录失败
          .and()
          .logout()
          .logoutSuccessHandler(logoutSuccessHandler);
           
         //权限配置
           http.authorizeRequests() //允许匿名访问
          //.antMatchers("/login", "/logout").permitAll()
          .anyRequest().access("@rbacService.hasPermission(request,authentication)");// RABC 表达式管控
          //.authenticate
			
         //rememberMe 配置
           http
           .rememberMe()
           .rememberMeParameter("remember-me")
           .tokenRepository(persistentTokenRepository())
           .tokenValiditySeconds(300);

    }
	
	 @Autowired
     @Qualifier("dataSource")
     DataSource dataSource;	
	 
	 @Bean
	 public PersistentTokenRepository persistentTokenRepository(){
	     JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
	     tokenRepository.setDataSource(dataSource);
	     // 如果token表不存在，使用下面语句可以初始化该表；若存在，会报错。
	     // tokenRepository.setCreateTableOnStartup(true);
	     return tokenRepository;
	 }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth 
		// 设置UserDetailsService
        .userDetailsService(customUserDetailsService)
        // 使用BCrypt进行密码的hash
        .passwordEncoder(passwordEncoder());
    }
	
	// 装载BCrypt密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
