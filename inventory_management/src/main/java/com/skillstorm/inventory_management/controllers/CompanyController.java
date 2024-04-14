package com.skillstorm.inventory_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.inventory_management.models.Company;
import com.skillstorm.inventory_management.services.CompanyService;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    
    @Autowired
    CompanyService companyService;

    @GetMapping
    public ResponseEntity <List<Company>> findAllCompanies() {
        List<Company> companies = companyService.findAllCompanies();

        return new ResponseEntity<List<Company>>(companies, HttpStatus.OK);
    }

}
