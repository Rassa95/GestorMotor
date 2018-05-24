package vk.otakon.proyecto.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class addModeloTest {

	@Test
	void test() {
		JunitTesting test = new JunitTesting();
		boolean output = test.addModelo();
		assertEquals(true, output);
	}

}
