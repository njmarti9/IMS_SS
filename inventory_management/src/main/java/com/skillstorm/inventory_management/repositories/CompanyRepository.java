package com.skillstorm.inventory_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.inventory_management.models.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>{
    
}
