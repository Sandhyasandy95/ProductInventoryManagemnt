package com.product.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.product.model.Product;
import com.product.service.ProductService;

public class ProductControllerTest extends AbstractTest {

	@MockBean
	private ProductService service;

	@Override
	@Before
	public void setUp() {
		super.setUp();

	}

	@Test
	public void testGetProductById() throws Exception {
		Product product = new Product();
		product.setId(1L);
		product.setName("Iphone13");
		product.setDescription("GoodPhone");
		product.setPrice(2000.2);
		product.setQuantity(1);

		Mockito.when(service.getProductById(Mockito.anyLong())).thenReturn(Optional.of(product));

		String URI = "/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(product);
		String outputInJson = result.getResponse().getContentAsString();
		assertEquals(expectedJson, outputInJson);
	}

	@Test
	public void testCreateProduct() throws Exception {

		Product product = new Product();
		product.setId(1L);
		product.setName("iphone12");
		product.setDescription("GoodPhone");
		product.setPrice(2000.2);
		product.setQuantity(0);

		String inputInJson = this.mapToJson(product);

		String URI = "/products";

		Mockito.when(service.addProduct(Mockito.any())).thenReturn("Product saved successfully");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertEquals("Product saved successfully", outputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void testGetAllSavedProducts() throws Exception {

		Product product1 = new Product();
		product1.setId(1L);
		product1.setName("iphone14");
		product1.setDescription("GoodPhone");
		product1.setPrice(2000.2);
		product1.setQuantity(0);

		Product product2 = new Product();
		product2.setId(1L);
		product2.setName("iPhone12");
		product2.setDescription("GoodPhone");
		product2.setPrice(2000.2);
		product2.setQuantity(0);

		List<Product> productList = new ArrayList<>();
		productList.add(product1);
		productList.add(product2);

		Mockito.when(service.getAllProducts()).thenReturn(productList);

		String URI = "/products";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(productList);
		String outputInJson = result.getResponse().getContentAsString();
		assertEquals(expectedJson, outputInJson);
	}

	@Test
	public void testDeleteProductById() throws Exception {
		Product product = new Product();
		product.setId(1L);
		product.setName("Iphone13");
		product.setDescription("GoodPhone");
		product.setPrice(2000.2);
		product.setQuantity(0);

		Mockito.when(service.deleteProductById(Mockito.anyLong())).thenReturn("Requested Id Deleted Successfully....");

		String URI = "/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		assertEquals("Requested Id Deleted Successfully....", outputInJson);
	}

	@Test
	public void testUpdateProductById() throws Exception {
		Product product = new Product();
		product.setId(1L);
		product.setName("iphone12");
		product.setDescription("GoodPhone");
		product.setPrice(2000.2);
		product.setQuantity(0);

		Mockito.when(service.updateProduct(Mockito.any(Long.class), Mockito.any(Product.class)))
				.thenReturn("Product updated successfully");

		String URI = "/1";
		String inputInJson = this.mapToJson(product);

		// String reqbody = new ObjectMapper().writeValueAsString(product);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).content(inputInJson)
				.characterEncoding(StandardCharsets.UTF_8).contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		// String expectedJson = this.mapToJson(product);
		String outputInJson = result.getResponse().getContentAsString();
		assertEquals("Product updated successfully", outputInJson);
	}

}
