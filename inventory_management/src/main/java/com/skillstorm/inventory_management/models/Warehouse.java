package com.skillstorm.inventory_management.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "WAREHOUSES")
public class Warehouse {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer warehouseId;

    @Column(name = "warehouse_name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "capacity")
    private double capacity;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(targetEntity = Product.class, mappedBy = "warehouse")
    private Set<Product> products;
    
    public Warehouse() {
    }

    public Warehouse(String name, String location, double capacity, Company company) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.company = company;
    }

    public Warehouse(int warehouseId, String name, String location, double capacity, Company company) {
        this.warehouseId = warehouseId;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.company = company;
    }

    public Warehouse(String name, String location, int warehouseId, double capacity, Company company,
            Set<Product> products) {
        this.name = name;
        this.location = location;
        this.warehouseId = warehouseId;
        this.capacity = capacity;
        this.company = company;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Product> getproducts() {
        return products;
    }

    public void setproducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + warehouseId;
        long temp;
        temp = Double.doubleToLongBits(capacity);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((products == null) ? 0 : products.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Warehouse other = (Warehouse) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (warehouseId != other.warehouseId)
            return false;
        if (Double.doubleToLongBits(capacity) != Double.doubleToLongBits(other.capacity))
            return false;
        if (company == null) {
            if (other.company != null)
                return false;
        } else if (!company.equals(other.company))
            return false;
        if (products == null) {
            if (other.products != null)
                return false;
        } else if (!products.equals(other.products))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Warehouse [name=" + name + ", location=" + location + ", warehouseId=" + warehouseId + ", capacity="
                + capacity + "]";
    }
}
