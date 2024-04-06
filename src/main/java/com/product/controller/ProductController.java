package com.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.product.exception.ProductAlreadyExistsException;
import com.product.exception.ProductException;
import com.product.exception.ProductNotFoundException;
import com.product.model.Product;
import com.product.service.ProductService;

import jakarta.validation.Valid;

@RestController
@Validated
public class ProductController {

	@Autowired
	ProductService productService;
	
	//Adding A New Product
	
	@PostMapping(path = "/products")
	public String addProduct(@RequestBody @Valid Product product) throws ProductAlreadyExistsException{
		return productService.addProduct(product);
	}


	// Get Product by ID

	@GetMapping(path = "/{id}")
	public Optional<Product> getProductById(@PathVariable long id) throws ProductNotFoundException {

		return productService.getProductById(id);
	}

	// Get Product By Id list
	
	@GetMapping(path = "/getProductByIdList")
	public List<Product> getProductByIdList(@RequestBody List<Long> idList) throws ProductNotFoundException {
		return productService.getProductByIdList(idList);
	}

	// Get all Products
	@GetMapping(path = "/products")
	public List<Product> getAllProducts() throws ProductException {
		return productService.getAllProducts();
	}

	// update product by ID
	@PutMapping(path = "/{id}")
	public String updateProductById(@PathVariable long id, @RequestBody Product newproduct) throws ProductNotFoundException {
		return productService.updateProduct(id, newproduct);
	}

	// Delete product by ID
	@DeleteMapping("/{id}")
	public String deleteProductById(@PathVariable long id) throws ProductNotFoundException {
		return productService.deleteProductById(id);

	}

	// Delete All Products
	@DeleteMapping(path = "/products")
	public String deleteAllProducts() {
		return productService.deleteAllproducts();
	}
}
