package org.datacenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("org.datacenter.mapper")
@SpringBootApplication
public class WangApplication {

	public static void main(String[] args) {
		SpringApplication.run(WangApplication.class, args);
	}

}
