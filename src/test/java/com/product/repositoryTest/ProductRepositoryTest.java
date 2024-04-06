package com.product.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.product.model.Product;
import com.product.repository.ProductRepository;

@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository repo;

	@Test
	public void givenProductObject_whenSave_thenReturnSaveProduct() {

		Product product = Product.builder().name("Iphone13").description("Good Phone").price(2000.1).quantity(2)
				.build();

		Product addProduct = repo.save(product);
		assertEquals(addProduct, product);
	}

	@Test
	public void givenProductList_whenFindAll_thenProductList() {

		Product product1 = Product.builder().name("Iphone14").description("Good Phone").price(2000.1).quantity(2)
				.build();

		Product product2 = Product.builder().name("Iphone14").description("Good Phone").price(2000.1).quantity(2)
				.build();

		repo.save(product1);
		repo.save(product2);

		List<Product> expectedlist = new ArrayList<Product>();
		expectedlist.add(product1);
		expectedlist.add(product2);
		List<Product> actuallist = repo.findAll();

		assertEquals(expectedlist, actuallist);
	}

	@Test
	public void givenProductObject_whenFindById_thenReturnProductObject() {

		Product expectedProduct = Product.builder().name("Iphone13").description("Good Phone").price(2000.1).quantity(2)
				.build();
		repo.save(expectedProduct);

		Product getActualProduct = repo.findById(expectedProduct.getId()).get();

		assertEquals(expectedProduct, getActualProduct);

	}

	@Test
	public void givenProductObject_whenUpdate_thenProductObject() {

		Product product = Product.builder().name("Iphone13").description("Good Phone").price(2000.1).quantity(2)
				.build();

		repo.save(product);

		Product getActualProduct = repo.findById(product.getId()).get();
		getActualProduct.setId(1L);
		getActualProduct.setName("Iphone12");
		getActualProduct.setDescription("Good Phone");
		getActualProduct.setPrice(2000.2);
		getActualProduct.setQuantity(2);

		Product expectedProduct = Product.builder().id(1L).name("Iphone12").description("Good Phone").price(2000.2)
				.quantity(2).build();

		Product updatedActualProduct = repo.save(getActualProduct);
		assertEquals(expectedProduct, updatedActualProduct);

	}

	@Test
	public void givenProductObject_whenDelete_thenRemoveProduct() {

		Product product = Product.builder().name("Iphone13").description("Good Phone").price(2000.1).quantity(2)
				.build();

		repo.save(product);

		repo.deleteById(product.getId());

		Optional<Product> deleteProduct = repo.findById(product.getId());

		assertEquals(false, deleteProduct.isPresent());
	}

	@Test
	public void whendeleteAll_thenRemoveallProducts() {

		Product product = Product.builder().name("Iphone13").description("Good Phone").price(2000.1).quantity(2)
				.build();

		repo.save(product);

		repo.deleteAll();

		Optional<Product> deleteProduct = repo.findById(product.getId());

		assertEquals(false, deleteProduct.isPresent());
	}

}
