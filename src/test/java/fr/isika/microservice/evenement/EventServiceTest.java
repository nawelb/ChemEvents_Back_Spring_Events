package fr.isika.microservice.evenement;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import fr.isika.microservice.evenement.model.Event;
import fr.isika.microservice.evenement.service.EventService;

@AutoConfigureWebTestClient(timeout = "36000")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventServiceTest {
	
	@Autowired
    private WebTestClient webTestClient;
	
	public String baseURI = System.getenv("HOST_NAME");
	
	@Test
    public void testGetAllEvents() {
        webTestClient.get().uri(baseURI+"/event-api/public/events")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Event.class);
    }
	
	@Test
    public void testGetEventById() {
        webTestClient.get().uri(baseURI+"/event-api/public/event", "10thWorldConferenceon")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }
	
	
}
