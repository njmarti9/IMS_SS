package com.skillstorm.inventory_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.inventory_management.models.Product;
import com.skillstorm.inventory_management.services.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://127.0.0.1:5501/")
public class ProductController {
    
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAllProducts() {
        List<Product> products = productService.findAllProducts();

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable int id) {
        Product product = productService.findProductById(id);

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Product>> findProductsByName(@PathVariable String name) {
        List<Product> products = productService.findProductsByName(name);

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/size/{size}")
    public ResponseEntity<List<Product>> findProductsBySize(@PathVariable Double size) {
        List<Product> products = productService.findProductsBySize(size);

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/warehouse/{id}")
    public ResponseEntity<List<Product>> findProductsByWarehouseWarehouseId(@PathVariable int id) {
        List<Product> products = productService.findProductsByWarehouseId(id);

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.saveProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    @PutMapping("/product")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        Product newProduct = productService.saveProduct(product);
        return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
    }

    @DeleteMapping("/product")
    public ResponseEntity<Product> deleteProduct(@RequestBody Product product) {
        productService.deleteProduct(product);
        return ResponseEntity.noContent().build();
    }
}
