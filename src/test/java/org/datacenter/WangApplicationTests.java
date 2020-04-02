package org.datacenter;

import java.util.List;

import org.datacenter.model.User;
import org.datacenter.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class WangApplicationTests {

	@Autowired UserService userService;
	
	@Test
	void simpleTest() {
		System.out.println("xx");
	}	
	
	@Test
	void userService() {
		userService.getUserprofile("admin");
	//List<User> user=	userService.getuser(1, 100);
	}
	
	@Test
	void passwordcreate() {
		PasswordEncoder encoder=new BCryptPasswordEncoder();		
		System.out.println(encoder.encode("123456"));
	}

}
