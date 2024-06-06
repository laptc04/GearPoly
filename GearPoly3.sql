-- Database creation
CREATE DATABASE GearPoly;

-- Use the created database
USE GearPoly;

-- Create the Accounts table
CREATE TABLE Accounts (
    id INT PRIMARY KEY identity,
    fullname NVARCHAR(255) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) NOT NULL,
    phone NVARCHAR(20),
    address NVARCHAR(255),
    image NVARCHAR(255),
    role bit
);

-- Create the Bills table
CREATE TABLE Bills (
    id INT PRIMARY KEY identity,
    account_id INT,
    bill_date DATE,
    total INT,
    FOREIGN KEY (account_id) REFERENCES Accounts(id)
);

-- Create the Details_Bill table
CREATE TABLE Details_Bill (
    id INT PRIMARY KEY identity,
    bill_id INT,
    product_id INT,
    price DECIMAL(10, 2),
    quantity INT,
    FOREIGN KEY (bill_id) REFERENCES Bills(id),
    FOREIGN KEY (product_id) REFERENCES Products(id)
);

-- Create the Carts table
CREATE TABLE Carts (
    id INT PRIMARY KEY identity,
    account_id INT,
    product_id INT,
    quantity INT,
    price INT,
    status NVARCHAR(50),
    image NVARCHAR(255),
    FOREIGN KEY (account_id) REFERENCES Accounts(id),
    FOREIGN KEY (product_id) REFERENCES Products(id)
);

-- Create the Products table
CREATE TABLE Products (
    id INT PRIMARY KEY identity,
    product_name NVARCHAR(255) NOT NULL,
    price INT,
    in_stock INT,
    description NVARCHAR(255),
    manufacturer_id INT,
    supplier_id INT,
    FOREIGN KEY (manufacturer_id) REFERENCES Manufacturers(id),
    FOREIGN KEY (supplier_id) REFERENCES Suppliers(id)
);

-- Create the Categories table
CREATE TABLE Categories (
    id INT PRIMARY KEY identity,
    categories_name NVARCHAR(255) NOT NULL
);

-- Create the Set_Categories table
CREATE TABLE Set_Categories (
    id INT PRIMARY KEY identity,
    product_id INT,
    category_id INT,
    FOREIGN KEY (product_id) REFERENCES Products(id),
    FOREIGN KEY (category_id) REFERENCES Categories(id)
);

-- Create the Discounts table
CREATE TABLE Discounts (
    id INT PRIMARY KEY identity,
    discount_code NVARCHAR(50) NOT NULL,
    discount_percent INT,
    quantity INT,
    start_date DATE,
    end_date DATE
);

-- Create the Set_Discounts table
CREATE TABLE Set_Discounts (
    id INT PRIMARY KEY identity,
    product_id INT,
    discount_id INT,
    quantity_used INT,
    FOREIGN KEY (product_id) REFERENCES Products(id),
    FOREIGN KEY (discount_id) REFERENCES Discounts(id)
);

-- Create the Manufacturers table
CREATE TABLE Manufacturers (
    id INT PRIMARY KEY identity,
    manufacturer_name NVARCHAR(255) NOT NULL,
    contact_info NVARCHAR(255),
	images NVARCHAR(255)
);

-- Create the Suppliers table
CREATE TABLE Suppliers (
    id INT PRIMARY KEY identity,
    supplier_name NVARCHAR(255) NOT NULL,
    contact_info NVARCHAR,
	images NVARCHAR(255)
);

create table images(
	id int primary key identity,
	prod_id int not null foreign key references products(id),
	name nvarchar(255) not null
);

-- Add foreign keys to the Products table for Manufacturers and Suppliers
ALTER TABLE Products
ADD CONSTRAINT FK_Products_Manufacturers FOREIGN KEY (manufacturer_id) REFERENCES Manufacturers(id);

ALTER TABLE Products
ADD CONSTRAINT FK_Products_Suppliers FOREIGN KEY (supplier_id) REFERENCES Suppliers(id);




