package com.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.exception.ProductAlreadyExistsException;
import com.product.exception.ProductException;
import com.product.exception.ProductNotFoundException;
import com.product.model.Product;
import com.product.repository.ProductRepository;

@Service
public class ProductService {

	Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductRepository productRepo;

	public String addProduct(Product product) throws ProductAlreadyExistsException {
		logger.info("New Product " + product);

		Optional<Product> saveProduct = productRepo.findById(product.getId());

		if (saveProduct.isPresent()) {
			throw new ProductAlreadyExistsException("Product already Exists with the given name" + product.getName());
		}

		productRepo.save(product);
		return "Product saved successfully";

	}

	public Optional<Product> getProductById(long id) throws ProductNotFoundException {

		logger.info("Requested Id ...... " + id);
		Optional<Product> product = productRepo.findById(id);

		if (product.isPresent()) {
			return product;
		} else
			throw new ProductNotFoundException("Product not found with the given id.." + id);

	}

	public List<Product> getProductByIdList(List<Long> idList) throws ProductNotFoundException {
		logger.info("Requested Id List ::: " + idList);
		List<Product> productList = new ArrayList<Product>();
		// List<Long> idNFList = new ArrayList<Long>();
		for (long i : idList) {

			Optional<Product> product = productRepo.findById(i);
			if (product.isPresent()) {
				logger.info("Product :::: " + i);
				productList.add(productRepo.getById(i));
			} else
				throw new ProductNotFoundException("Product not found with the given id's.." + idList);
		}
		return productList;
	}

	public List<Product> getAllProducts() throws ProductException {
		List<Product> productList;
			productList = productRepo.findAll();
			
			if(productList.isEmpty()) 
		
			throw new ProductException("Database is Empty please Add Products");

			else return productList;
	}

	public String updateProduct(long id, Product newproduct) throws ProductNotFoundException {
		logger.info("product updated " + newproduct);

		Optional<Product> oldproductData = productRepo.findById(id);

		if (oldproductData.isPresent()) {
			Product updatedProduct = oldproductData.get();
			updatedProduct.setName(newproduct.getName());
			updatedProduct.setDescription(newproduct.getDescription());
			updatedProduct.setPrice(newproduct.getPrice());
			updatedProduct.setQuantity(newproduct.getQuantity());
			productRepo.save(updatedProduct);

		} else

			throw new ProductNotFoundException("Product not found with the given id's..");

		return "Product updated successfully";
	}

	public String deleteProductById(long id) throws ProductNotFoundException {

		logger.info("Requested Id for delete::: " + id);

		Optional<Product> product = productRepo.findById(id);
		if (product.isPresent()) {
			logger.info("Product :::: " + id);
			productRepo.deleteById(id);
		} else
			throw new ProductNotFoundException("Product not found with the given id.." + id);

		return "Requested Id Deleted Successfully....";
	}

	public String deleteAllproducts() {
		
			productRepo.deleteAll();

		return "Products Deleted Successfully";

	}

}
