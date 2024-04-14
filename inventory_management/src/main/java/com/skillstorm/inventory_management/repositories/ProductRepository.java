package com.skillstorm.inventory_management.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.inventory_management.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

    Optional<List<Product>> findAllByProductName(String name);

    Optional<List<Product>> findAllBySize(Double size);

    Optional<List<Product>> findProductsByWarehouseWarehouseId(int warehouseId);
    
}
