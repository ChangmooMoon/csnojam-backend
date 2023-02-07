package csnojam.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class CsnojamApplicationTests {

	@Autowired
	private WebApplicationContext context;

	@Test
	void contextLoads() {
	}

}
