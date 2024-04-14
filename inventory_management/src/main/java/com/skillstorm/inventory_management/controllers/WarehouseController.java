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

import com.skillstorm.inventory_management.models.Warehouse;
import com.skillstorm.inventory_management.services.WarehouseService;


@RestController
@RequestMapping("/warehouses")
@CrossOrigin("http://127.0.0.1:5501/")
public class WarehouseController {

    @Autowired
    WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<Warehouse>> findAllWarehouses() {
        List<Warehouse> warehouses = warehouseService.findAllWarehouses();

        return new ResponseEntity<List<Warehouse>>(warehouses, HttpStatus.OK);
    }

    @GetMapping("/warehouse/{id}")
    public ResponseEntity<Warehouse> findWarehouseById(@PathVariable int id) {
        Warehouse warehouse = warehouseService.findWarehouseById(id);

        return new ResponseEntity<Warehouse>(warehouse, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<Warehouse>> findWarehousesByCompanyId(@PathVariable int id) {
        List<Warehouse> warehouses = warehouseService.findWarehousesByCompanyId(id);

        return new ResponseEntity<List<Warehouse>>(warehouses, HttpStatus.OK);
    }

    @PostMapping("/warehouse")
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse newWarehouse = warehouseService.saveWarehouse(warehouse);

        return new ResponseEntity<Warehouse>(newWarehouse, HttpStatus.OK);
    }

    @PutMapping("/warehouse")
    public ResponseEntity<Warehouse> updateWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse newWarehouse = warehouseService.saveWarehouse(warehouse);
        return new ResponseEntity<Warehouse>(newWarehouse, HttpStatus.OK);
    }

    @DeleteMapping("/warehouse")
    public ResponseEntity<Warehouse> deleteWarehouse(@RequestBody Warehouse warehouse) {
        warehouseService.deleteWarehouse(warehouse);
        return ResponseEntity.noContent().build();
    }
}
