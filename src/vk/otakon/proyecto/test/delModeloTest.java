package vk.otakon.proyecto.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class delModeloTest {

	@Test
	void test() {
		JunitTesting test = new JunitTesting();
		boolean output = test.delModelo();
		assertEquals(true, output);
	}

}
