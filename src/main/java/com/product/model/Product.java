package com.product.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class Product {

	public Product(String string, String string2, double d, int i) {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false ,length = 50)
	@NotBlank(message = "Product Name is Mandatory")
	private String name;
	
	@Column(nullable = false)
	@NotBlank(message = "Product description is Mandatory")
	private String description;
	
	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	private int quantity;


}
