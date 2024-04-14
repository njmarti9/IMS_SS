package com.skillstorm.inventory_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.inventory_management.models.Product;
import com.skillstorm.inventory_management.models.Warehouse;
import com.skillstorm.inventory_management.repositories.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    WarehouseService warehouseService;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(int id) {
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()) {
            return product.get();
        }
        return null;
    }

    public List<Product> findProductsByName(String name) {
        Optional<List<Product>> products = productRepository.findAllByProductName(name);

        if (products.isPresent()) {
            return products.get();
        }
        return null;
    }

    public List<Product> findProductsBySize(Double size) {
        Optional<List<Product>> products = productRepository.findAllBySize(size);

        if (products.isPresent()) {
            return products.get();
        }
        return null;
    }

    public List<Product> findProductsByWarehouseId(int id) {
        Optional<List<Product>> products = productRepository.findProductsByWarehouseWarehouseId(id);

        if(products.isPresent()) {
            return products.get();
        }
        return null;
    }

    public Product saveProduct(Product product) {
        Warehouse warehouseWithId = warehouseService.saveWarehouse(product.getWarehouse());
        product.setWarehouse(warehouseWithId);
        return productRepository.save(product);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }
}
