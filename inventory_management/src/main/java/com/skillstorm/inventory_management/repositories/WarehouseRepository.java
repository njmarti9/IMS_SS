package com.skillstorm.inventory_management.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.inventory_management.models.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer>{

    Optional<List<Warehouse>> findWarehousesByCompanyCompanyId(int companyId);
    
}
