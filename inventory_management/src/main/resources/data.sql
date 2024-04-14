insert into COMPANIES (company_name) 
values ('Apple'),
('HP'),
('Dell'),
('Framework'),
('Lenovo');

insert into WAREHOUSES (warehouse_name, location, capacity, company_id)
values ('AW1', 'California', 17500, 1),
('AW2', 'ILLINOIS', 18000, 1),
('HP1', 'ARIZONA', 20000, 2),
('HP2', 'FLORIDA', 16000, 2),
('DW1', 'CALIFORNIA', 17000, 3),
('DW2', 'VIRGINIA', 17850, 3),
('FW1', 'MAINE', 15000, 4),
('LW1', 'CALFORNIA', 16400, 5),
('LW2', 'FLORIDA', 14000, 5);

insert into PRODUCTS (product_name, quantity, size, price, warehouse_id)
values ('IMac', 50, 2.4, 1500, 1),
('MacBook Air', 200, 1.2, 1200, 2),
('HP Spectre x360', 100, 1.4, 1149.99, 3),
('HP Envy Desktop TE01-4000', 25, 3.2, 500, 4),
('Precision 5490 Workstation', 30, 1.6, 2489, 5),
('S2725DS', 75, 2.0, 229.99, 6),
('Framework Laptop 16', 500, 1.3, 1399.0, 7),
('Lenovo 500w Gen 3', 40, 0.9, 175.00, 8),
('ThinkPad X1 Carbon Gen 11 Intel 14Inch', 35, 1.5, 1658.99, 9);

insert into USERS (username, company_id)
values ('adamb1', 1),
('samz1', 2),
('dalet1', 3),
('noahm1', 4),
('rickl1', 5)