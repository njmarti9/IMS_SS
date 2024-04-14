package com.skillstorm.inventory_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.inventory_management.models.Company;
import com.skillstorm.inventory_management.models.Warehouse;
import com.skillstorm.inventory_management.repositories.WarehouseRepository;

@Service
public class WarehouseService {

@Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    CompanyService companyService;

    public List<Warehouse> findAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse findWarehouseById(int id) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);

        if(warehouse.isPresent()) {
            return warehouse.get();
        }
        return null;
    }

    public List<Warehouse> findWarehousesByCompanyId(int id) {
        Optional<List<Warehouse>> warehouses = warehouseRepository.findWarehousesByCompanyCompanyId(id);

        if(warehouses.isPresent()) {
            return warehouses.get();
        }
        return null;
    }

    public Warehouse saveWarehouse(Warehouse warehouse) {

        Company companyWithId = companyService.saveCompany(warehouse.getCompany());
        warehouse.setCompany(companyWithId);
        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Warehouse warehouse) {

        warehouseRepository.delete(warehouse);
    }
    
}
