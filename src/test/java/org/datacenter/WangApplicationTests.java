package org.datacenter;

import org.datacenter.model.User;
import org.datacenter.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

	User user=	userService.findByUserName("wangmin");
	System.out.println(user.getUsername());
	}

}
