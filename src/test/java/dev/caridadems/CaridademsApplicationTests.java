package dev.caridadems;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CaridademsApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertThat(Boolean.TRUE).isTrue();
	}
}
