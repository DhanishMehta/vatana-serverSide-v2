package com.capstone.grocery;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class GroceryApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
    public void mainMethodTest() {
        // test if the main method executes successfully
        GroceryApplication.main(new String[] {});
    }

}
