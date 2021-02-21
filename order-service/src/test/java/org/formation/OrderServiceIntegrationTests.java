package org.formation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.formation.model.Client;
import org.formation.model.Order;
import org.formation.model.OrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = OrderServiceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "org.formation:delivery-service:0.0.8-SNAPSHOT:stubs")
public class OrderServiceIntegrationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	JacksonTester<Order> jsonOrder;

	@Test
	public void given_WhenPassEvenNumberInQueryParam_ThenReturnEven() throws Exception {

		Order order = new Order();
		Client client = new Client();
		client.setId(1);
		OrderItem orderItem = new OrderItem();
		orderItem.setRefProduct("REF1");
		orderItem.setQuantity(10);
		order.getOrderItems().add(orderItem);
		order.setClient(client);
		

		mockMvc.perform(MockMvcRequestBuilders.post("/api/orders").contentType(MediaType.APPLICATION_JSON)
				.content("" + jsonOrder.write(order).getJson())).andExpect(status().is2xxSuccessful());
	}

}
