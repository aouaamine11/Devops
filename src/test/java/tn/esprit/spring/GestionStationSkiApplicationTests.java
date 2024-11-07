package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.spring.services.InstructorServicesImpl;

@SpringBootTest(classes = InstructorServicesImpl.class)
class GestionStationSkiApplicationTests {
	@MockBean
	private InstructorServicesImpl instructorServices;

	@Test
	void contextLoads() {
	}

}
