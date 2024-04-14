const warehouseURL = 'http://localhost:8080/warehouses';
const userURL = 'http://localhost:8080/users';
const productURL = 'http://localhost:8080/products';

let allWarehouses = [];

let allProducts = [];

var currentUsername = "kwickens0";

var currentUser;

var currentWarehouse;

document.addEventListener('DOMContentLoaded', () => {
    setCurrentUser();
})

function setCurrentUser(){
    document.getElementById('outer-product-table-div').style.display = 'none';
    document.getElementById('warehouse-table-div').style.display = 'flex';
    currentUsername = document.getElementById('user').value;
    console.log(currentUsername);
    getCurrentUser();
    clearWarehouses();
    clearProducts();
    getUsersWarehouses();
}

function clearWarehouses() {
    document.getElementById('warehouse-table-body').innerHTML = '';
    allWarehouses.length = 0;
}

function clearProducts() {
    document.getElementById('product-table-body').innerHTML = '';
    document.getElementById('product-table-foot').innerHTML = '';
    allProducts.length = 0;
}

function getCurrentUser() {
    let xhrUser = new XMLHttpRequest();

    xhrUser.onreadystatechange = () => {
        if(xhrUser.readyState === 4) {
            let user = JSON.parse(xhrUser.responseText);
            currentUser = user[0];
            console.log(currentUser);
            console.log(currentUser.company.companyId);
        }
    }

    xhrUser.open('GET', userURL+'/name/'+currentUsername, false);

    xhrUser.send();
}

function getUsersWarehouses() {
    let xhrWarehouse = new XMLHttpRequest();

    xhrWarehouse.onreadystatechange = () => {
        if(xhrWarehouse.readyState === 4) {
            let warehouses = JSON.parse(xhrWarehouse.responseText);
            
            //Sort Warehouses in list by ID
            warehouses.sort(function(a,b) {
                return a.id-b.id;
            })

            warehouses.forEach(newWarehouse => {
                addWarehouseToTable(newWarehouse);
            });

            document.getElementById('warehouse-table-title').innerText = `Warehouses (${currentUser.company.companyName})`;
        }
    }

    xhrWarehouse.open('GET', warehouseURL+'/company/'+currentUser.company.companyId);

    xhrWarehouse.send();
}

function getWarehousesProducts(warehouseId) {
    let xhr = new XMLHttpRequest();
    allWarehouses.forEach(warehouse => {
        if (warehouse.warehouseId === warehouseId) {
            currentWarehouse = warehouse;
        }
    })

    xhr.onreadystatechange = () => {
        if(xhr.readyState === 4) {
            let products = JSON.parse(xhr.responseText);

            products.sort(function(a,b) {
                return a.id-b.id;
            })

            products.forEach(newProduct => {
                addProductToTable(newProduct);
            });

            addFooterToProductsTable();
        }
    }

    xhr.open('GET', productURL+'/warehouse/'+currentWarehouse.warehouseId);

    xhr.send();
}

function addWarehouseToTable(warehouse) {
    console.log(warehouse);
    let tr = document.createElement('tr');
    let id = document.createElement('td');
    let name = document.createElement('td');
    let location = document.createElement('td');
    let capacity = document.createElement('td');
    let editBtn = document.createElement('td');
    let deleteBtn = document.createElement('td');

    editBtn.id = 'edit-row-button';

    id.innerHTML = `<span id="warehouse-id-span" onclick='displayProducts(${warehouse.warehouseId})'>${warehouse.warehouseId}</span>`;
    name.innerText = warehouse.name;
    location.innerText = warehouse.location;
    capacity.innerText = warehouse.capacity;
    editBtn.innerHTML = `<button id="edit-button" onclick="activateEditWarehouseForm(${warehouse.warehouseId})">Edit</button>`;
    deleteBtn.innerHTML = `<button id="delete-button" onclick="activateDeleteWarehouseForm(${warehouse.warehouseId})">Delete</button>`;

    tr.appendChild(id);
    tr.appendChild(name);
    tr.appendChild(location);
    tr.appendChild(capacity);
    tr.appendChild(editBtn);
    tr.appendChild(deleteBtn);

    tr.id = 'TR-warehouse' + warehouse.warehouseId;

    document.getElementById('warehouse-table-body').appendChild(tr);

    allWarehouses.push(warehouse);
}

function activateEditWarehouseForm(id) {
    resetAllForms();
    for (let w of allWarehouses) {
        if (w.warehouseId === id) {
            document.getElementById('edit-warehouse-id').value = w.warehouseId;
            document.getElementById('edit-warehouse-name').value = w.name;
            document.getElementById('edit-warehouse-location').value = w.location;
            document.getElementById('edit-warehouse-capacity').value = w.capacity;
        }
    }

    document.getElementById('edit-warehouse-popup-form-div').style.display = 'flex';
}

function activateDeleteWarehouseForm(id) {
    resetAllForms();
    for (let w of allWarehouses) {
        if (w.warehouseId === id) {
            document.getElementById('delete-warehouse-id').value = w.warehouseId;
            document.getElementById('delete-warehouse-name').value = w.name;
            document.getElementById('delete-warehouse-location').value = w.location;
            document.getElementById('delete-warehouse-capacity').value = w.capacity;
        }
    }

    document.getElementById('delete-warehouse-popup-form-div').style.display = 'flex';
}

document.getElementById('edit-warehouse-popup-form-div').addEventListener('submit', (event) => {
    event.preventDefault();

    let inputData = new FormData(document.getElementById('edit-warehouse-popup-form'));

    let warehouse = {
        warehouseId : document.getElementById('edit-warehouse-id').value,
        name : inputData.get('edit-warehouse-name'),
        location : inputData.get('edit-warehouse-location'),
        capacity : inputData.get('edit-warehouse-capacity'),
        company : currentUser.company
    }

    fetch(warehouseURL + '/warehouse', {
        method : 'PUT',
        headers : {
            'Content-Type' : 'application/json'
        },
        body : JSON.stringify(warehouse)
    })
    .then((data) => {
        return data.json();
    })
    .then((warehouseJson) => {
        updateWarehouseInTable(warehouseJson);
        resetAllForms();
    })
    .catch((error) => {
        console.error(error);
    })
})

document.getElementById('delete-warehouse-popup-form-div').addEventListener('submit', (event) => {
    event.preventDefault();

    let warehouse = {
        warehouseId : document.getElementById('delete-warehouse-id').value,
        name : document.getElementById('delete-warehouse-name').value,
        location : document.getElementById('delete-warehouse-location').value,
        capacity : document.getElementById('delete-warehouse-capacity').value,
        company : currentUser.company
    }



    fetch(warehouseURL + '/warehouse', {
        method : 'DELETE',
        headers : {
            'Content-Type' : 'application/json'
        },
        body : JSON.stringify(warehouse)
    })
    .then((data) => {
        if (data.status === 204) {
            removeWarehouseFromTable(warehouse);
            resetAllForms();
        }
    })
    .catch((error) => {
        console.error(error);
        console.log("If there are products in this warehouse they must be deleted first");
    })
})

function removeWarehouseFromTable(warehouse) {
    const element = document.getElementById('TR-warehouse' + warehouse.warehouseId);
    element.remove();
}

function updateWarehouseInTable(warehouse) {
    document.getElementById('TR-warehouse' + warehouse.warehouseId).innerHTML = `
    <td><span id="warehouse-id-span" onclick='displayProducts(${warehouse.warehouseId})'>${warehouse.warehouseId}</span></td>
    <td>${warehouse.name}</td>
    <td>${warehouse.location}</td>
    <td>${warehouse.capacity}</td>
    <td><button id="edit-button" onclick="activateEditWarehouseForm(${warehouse.warehouseId})">Edit</button></td>
    <td><button id="delete-button" onclick="activateDeleteWarehouseForm(${warehouse.warehouseId})">Delete</button></td>
    `;
}

function activateEditProductForm(id) {
    resetAllForms();
    for (let p of allProducts) {
        if (p.id === id) {
            document.getElementById('edit-product-id').value = p.id;
            document.getElementById('edit-product-name').value = p.productName;
            document.getElementById('edit-product-quantity').value = p.quantity;
            document.getElementById('edit-product-size').value = p.size.toFixed(2);
            document.getElementById('edit-product-price').value = p.price.toFixed(2);
        }
    }

    document.getElementById('edit-product-popup-form-div').style.display = 'flex';
}

function activateDeleteProductForm(id) {
    resetAllForms();
    for (let w of allProducts) {
        if (w.id === id) {
            document.getElementById('delete-product-id').value = w.id;
            document.getElementById('delete-product-name').value = w.productName;
            document.getElementById('delete-product-quantity').value = w.quantity;
            document.getElementById('delete-product-size').value = w.size.toFixed(2);
            document.getElementById('delete-product-price').value = w.price.toFixed(2);
        }
    }

    document.getElementById('delete-product-popup-form-div').style.display = 'flex';
}

function addProductToTable(product) {
    console.log(product);
    let tr = document.createElement('tr');
    let id = document.createElement('td');
    let name = document.createElement('td');
    let quantity = document.createElement('td');
    let size = document.createElement('td');
    let price = document.createElement('td');
    let editBtn = document.createElement('td');
    let deleteBtn = document.createElement('td');

    editBtn.id = 'edit-row-button';

    id.innerText = product.id;
    name.innerText = product.productName;
    quantity.innerText = product.quantity;
    size.innerText = product.size.toFixed(2);
    price.innerText = product.price.toFixed(2);
    editBtn.innerHTML = `<button id="edit-button" onclick="activateEditProductForm(${product.id})">Edit</button>`;
    deleteBtn.innerHTML = `<button id="delete-button" onclick="activateDeleteProductForm(${product.id})">Delete</button>`;

    tr.appendChild(id);
    tr.appendChild(name);
    tr.appendChild(quantity);
    tr.appendChild(size);
    tr.appendChild(price);
    tr.appendChild(editBtn);
    tr.appendChild(deleteBtn);

    tr.id = 'TR' + product.id;

    document.getElementById('product-table-body').appendChild(tr);

    allProducts.push(product);
}

function addFooterToProductsTable() {
    let tr = document.createElement('tr');
    let stats = document.createElement('td');
    let capLabel = document.createElement('td');
    let capacity = document.createElement('td');
    let areaLabel = document.createElement('td');
    let areaTaken = document.createElement('td');
    let percentLabel = document.createElement('td');
    let percentCapacity = document.createElement('td');

    stats.innerText = 'Warehouse:\n' + currentWarehouse.warehouseId;
    capLabel.innerText = "Capacity: ";
    capacity.innerText = currentWarehouse.capacity;
    areaLabel.innerText = "Area of Products: ";
    areaTaken.innerText = getAreaOfProducts().toFixed(2);
    percentLabel.innerText = "% Capacity";
    percentCapacity.innerText = (getAreaOfProducts()/currentWarehouse.capacity*100).toFixed(2);

    stats.id = 'footer-right-border';
    capacity.id = 'footer-right-border';
    areaTaken.id = 'footer-right-border';


    tr.appendChild(stats);
    tr.appendChild(capLabel);
    tr.appendChild(capacity);
    tr.appendChild(areaLabel);
    tr.appendChild(areaTaken);
    tr.appendChild(percentLabel);
    tr.appendChild(percentCapacity);

    document.getElementById('product-table-foot').appendChild(tr);

}

document.getElementById('edit-product-popup-form-div').addEventListener('submit', (event) => {
    event.preventDefault();

    let inputData = new FormData(document.getElementById('edit-product-popup-form'));

    let product = {
        id : document.getElementById('edit-product-id').value,
        productName : inputData.get('edit-product-name'),
        quantity : inputData.get('edit-product-quantity'),
        size : inputData.get('edit-product-size'),
        price : inputData.get('edit-product-price'),
        warehouse : currentWarehouse
    }

    fetch(productURL + '/product', {
        method : 'PUT',
        headers : {
            'Content-Type' : 'application/json'
        },
        body : JSON.stringify(product)
    })
    .then((data) => {
        return data.json();
    })
    .then(() => {
        clearProducts();
        getWarehousesProducts(currentWarehouse.warehouseId);
        resetAllForms();
    })
    .catch((error) => {
        console.error(error);
    })
})

document.getElementById('delete-product-popup-form-div').addEventListener('submit', (event) => {
    event.preventDefault();

    let product = {
        id : document.getElementById('delete-product-id').value,
        productName : document.getElementById('delete-product-name').value,
        quantity : document.getElementById('delete-product-quantity').value,
        size : document.getElementById('delete-product-size').value,
        price : document.getElementById('delete-product-price').value,
        warehouse : currentWarehouse
    }

    fetch(productURL + '/product', {
        method : 'DELETE',
        headers : {
            'Content-Type' : 'application/json'
        },
        body : JSON.stringify(product)
    })
    .then((data) => {
        if (data.status === 204) {
            clearProducts();
            getWarehousesProducts(currentWarehouse.warehouseId);
            resetAllForms();
        }
    })
    .catch((error) => {
        console.error(error);
    })
})

function updateProductInTable(product) {
    document.getElementById('TR' + product.id).innerHTML = `
    <td>${product.id})</td>
    <td>${product.productName}</td>
    <td>${product.quantity}</td>
    <td>${product.size.toFixed(2)}</td>
    <td>${product.price.toFixed(2)}</td>
    <td><button id="edit-button" onclick="activateEditProductForm(${product.id})">Edit</button></td>
    <td><button id="delete-button" onclick="activateDeleteProductForm(${product.id})">Delete</button></td>
    `;
}

function showCreateWarehouseForm() {
    resetAllForms();
    document.getElementById('create-warehouse-popup-form-div').style.display = 'flex';
}

function showCreateProductForm() {
    resetAllForms();
    document.getElementById('create-product-popup-form-div').style.display = 'flex';
}

document.getElementById('create-warehouse-popup-form').addEventListener('submit', (event) => {
    resetAllForms();

    let inputData = new FormData(document.getElementById('create-warehouse-popup-form'));

    let newWarehouse = {
        name: inputData.get('create-warehouse-name').toUpperCase(),
        location: inputData.get('create-warehouse-location').toUpperCase(),
        capacity: inputData.get('create-warehouse-capacity'),
        company: {
            companyId: currentUser.company.companyId,
            companyName: currentUser.company.companyName
        }
    }

    doPostRequestWarehouse(newWarehouse);
});

document.getElementById('create-warehouse-popup-form').addEventListener('reset', (event) => {
    event.preventDefault;
    resetAllForms();
})

document.getElementById('create-product-popup-form').addEventListener('reset', (event) => {
    event.preventDefault;
    resetAllForms();
})

document.getElementById('edit-warehouse-popup-form').addEventListener('reset', (event) => {
    event.preventDefault;
    resetAllForms();
})

document.getElementById('edit-product-popup-form').addEventListener('reset', (event) => {
    event.preventDefault;
    resetAllForms();
})

document.getElementById('delete-warehouse-popup-form').addEventListener('reset', (event) => {
    event.preventDefault;
    resetAllForms();
})

document.getElementById('delete-product-popup-form').addEventListener('reset', (event) => {
    event.preventDefault;
    resetAllForms();
})

async function doPostRequestWarehouse(newWarehouse) {
    let returnedData = await fetch(warehouseURL + '/warehouse', {
        method : 'Post',
        headers : {
            'Content-Type' : 'application/json'
        },
        body : JSON.stringify(newWarehouse)
    });

    let warehouseJson = await returnedData.json();

    addWarehouseToTable(warehouseJson);

    resetAllForms();
}

document.getElementById('create-product-popup-form').addEventListener('submit', (event) => {
    resetAllForms();

    let inputData = new FormData(document.getElementById('create-product-popup-form'));

    let newProduct = {
        productName: inputData.get('create-product-name'),
        quantity: inputData.get('create-product-quantity'),
        size: inputData.get('create-product-size'),
        price: inputData.get('create-product-price'),
        warehouse: currentWarehouse
    }

    if (currentWarehouse.capacity < getAreaOfProducts() + (newProduct.quantity * newProduct.size)){
        console.log("This product will exceed the warehouse's capacity");
    }
    else {
        doPostRequestProduct(newProduct);

    }
});

document.getElementById('create-product-popup-form').addEventListener('reset', (event) => {
    event.preventDefault;
    resetAllForms();
})

async function doPostRequestProduct(newProduct) {
    let returnedData = await fetch(productURL + '/product', {
        method : 'Post',
        headers : {
            'Content-Type' : 'application/json'
        },
        body : JSON.stringify(newProduct)
    });

    let productJson = await returnedData.json();

    addProductToTable(productJson);

    document.getElementById('product-table-foot').innerHTML = ``;
    addFooterToProductsTable();

    resetAllForms();
}

function getAreaOfProducts() {
    let area = 0.0;
    allProducts.forEach(product => {
        area = area + product.size * product.quantity;
    })
    return area;
}

function displayProducts(warehouseId) {
    resetAllForms();
    document.getElementById("warehouse-table-div").style.display = 'none';
    document.getElementById("outer-product-table-div").style.display = 'flex';
    getWarehousesProducts(warehouseId);
}

function displayWarehouses() {
    document.getElementById("warehouse-table-div").style.display = 'flex';
    document.getElementById("outer-product-table-div").style.display = 'none';
}

document.getElementById('product-to-warehouse-btn').addEventListener('click', () => {
    currentWarehouse = null;
    resetAllForms();
    displayWarehouses();
    clearProducts();
});

function resetProductFooter() {
    document.getElementById('product-table-foot').innerHTML = ``;
}

function resetAllForms() {
    document.getElementById('create-warehouse-popup-form').reset();
    document.getElementById('edit-warehouse-popup-form').reset();
    document.getElementById('delete-warehouse-popup-form').reset();
    document.getElementById('create-product-popup-form').reset();
    document.getElementById('edit-product-popup-form').reset();
    document.getElementById('delete-product-popup-form').reset();

    document.getElementById('create-warehouse-popup-form-div').style.display = 'none';
    document.getElementById('edit-warehouse-popup-form-div').style.display = 'none';
    document.getElementById('delete-warehouse-popup-form-div').style.display = 'none';
    document.getElementById('create-product-popup-form-div').style.display = 'none';
    document.getElementById('edit-product-popup-form-div').style.display = 'none';
    document.getElementById('delete-product-popup-form-div').style.display = 'none';
}