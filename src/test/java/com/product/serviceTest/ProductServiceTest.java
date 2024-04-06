package com.product.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.product.exception.ProductAlreadyExistsException;
import com.product.exception.ProductException;
import com.product.exception.ProductNotFoundException;
import com.product.model.Product;
import com.product.repository.ProductRepository;
import com.product.service.ProductService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ProductServiceTest {

	@Autowired
	ProductService service;

	@InjectMocks
	ProductService serviceIM;

	@Mock
	ProductRepository repo;

	@Test
	public void testAddProductEmpty() throws ProductAlreadyExistsException {
		Product product = new Product("iPhone10Pro128", "GoodPhone", 1099.97, 1);
		serviceIM.addProduct(product);
		verify(repo, times(1)).save(product);

	}

	@Test
	public void testProductByID() throws ProductNotFoundException {

		Product product = new Product();
		product.setId(1L);
		when(repo.findById(product.getId())).thenReturn(Optional.of(product));
		Optional<Product> expected = serviceIM.getProductById(product.getId());
		assertEquals(expected.get(), product);
		verify(repo).findById(product.getId());
	}

	@Test
	public void testReturnAllProducts() throws ProductException {
		List<Product> products = new ArrayList();
		products.add(new Product());
		when(repo.findAll()).thenReturn(products);
		List<Product> expected = serviceIM.getAllProducts();
		assertEquals(expected, products);
		verify(repo).findAll();
	}

	@Test
	public void whenGivenId_shouldDeleteProduct_ifFound() throws ProductNotFoundException {
		Product product = new Product();
		product.setName("Iphone14");
		product.setId(1L);
		when(repo.findById(product.getId())).thenReturn(Optional.of(product));
		serviceIM.deleteProductById(product.getId());
		verify(repo).deleteById(product.getId());
	}

	@Test
	public void whenGivenId_shouldUpdateProduct_ifFound() throws ProductNotFoundException {
		Product oldProduct = new Product();
		oldProduct.setName("Iphone14");
		oldProduct.setId(1L);
		Product newProduct = new Product();
		newProduct.setName("Iphone14");
		newProduct.setId(1L);
		when(repo.findById(oldProduct.getId())).thenReturn(Optional.of(oldProduct));

		serviceIM.updateProduct(oldProduct.getId(), newProduct);
		verify(repo).save(newProduct);
		verify(repo).findById(oldProduct.getId());
	}
	
	

	@Test
	public void testDeleteAllProducts() {
		List<Product> products = new ArrayList();
		products.add(new Product());
		when(repo.findAll()).thenReturn(products);
		serviceIM.deleteAllproducts();
		verify(repo).deleteAll();
	}

}
