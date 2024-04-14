package com.skillstorm.inventory_management.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.inventory_management.models.Company;
import com.skillstorm.inventory_management.repositories.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }
    
}
