Drop database Catering

create Database Catering

use Catering
--saber

CREATE TABLE users
(
Name varchar(255) NOT NULL,
Email varchar(255) NOT NULL PRIMARY KEY,
Passwords varchar(30),
Roles varchar(10) 
)

insert into users
values( 'saber', 'saber@gmail.com','1234','ADMIN' )

Insert into users 
Values ('Sean Rahman','sean@gmail.com','4321','USER' )

select * from users;

Drop table users;



CREATE TABLE Customer (
    CustomerID int IDENTITY(1,1),
    Customer_Name  varchar(255) NOT NULL,
    Phone varchar (11) NOT NULL,
    Addresses varchar (255) NOT NULL,
    CONSTRAINT PK_Customer PRIMARY KEY (CustomerID),
    CONSTRAINT UK_Customer UNIQUE (Phone),
);

Insert into Customer (Customer_name, Phone, Addresses)
Values ('Sean Rahman','01712345678','38,Chamelibagh,Dhaka-1217' ),
       ('Rifat Bin Karim','01612345678','2/A.Monipuripara,Dhaka')

select * from Customer;
truncate table Customer;

--Rifat
CREATE TABLE CUS_SERVICES(
ITEM_ID INT NOT NULL,
PACKAGE_NAME VARCHAR(50) NOT NULL,
PRICE INT NOT NULL,
CONSTRAINT PK_itemId PRIMARY KEY (ITEM_ID)
);

INSERT INTO CUS_SERVICES(ITEM_ID,PACKAGE_NAME,PRICE)
VALUES(1,'MUTTON KACCHI',150),
(2,'CHICKEN BIRIYANI',120);

SELECT *FROM CUS_SERVICES;
DROP TABLE CUS_SERVICES;

--Sean

CREATE TABLE ORDERS
(
 Order_ID int not null IDENTITY(1,1),
 ITEM_ID INT,
 Quantity int,
 Total_costPerItem int,
 OrderDate date default GetDate(),
 Customer_ID int,
 Constraint fk_Itemid Foreign key (ITEM_ID) references CUS_SERVICES(ITEM_ID),
 Constraint fk_CusOrd_ID Foreign key(Customer_ID) references Customer(CustomerID),
 Constraint pk_OrderID Primary Key (Order_ID)
)

Alter table ORDERS
drop Constraint fk_Customer_ID 

Alter table ORDERS
Add Constraint fk_CusOrd_ID Foreign key(Customer_ID) references Customer(CustomerID)


SELECT *FROM ORDERS;

SELECT SUM(Total_costPerItem) as TotalPerItemPrice FROM ORDERS;

drop table ORDERS;

CREATE TABLE Payment
(

Payment_Id int not null IDENTITY(1,1),
Customer_Id int ,
Order_Id int,
Advance int,
Due int,
FullPayment varchar(255),
Constraint pk_Payment_Id Primary key (Payment_Id),
Constraint fk_CusPayid Foreign key (Customer_Id) references Customer(CustomerID),
Constraint fk_PayOrid Foreign key (Order_Id) references ORDERS(Order_ID)
);


Alter table Payment
drop Constraint fk_id

Alter table Payment
Add Constraint fk_PayOrid Foreign key (Order_Id) references ORDERS(Order_ID)



Alter table Payment
drop Constraint fk_Customerid

Alter table ORDERS
Add Constraint fk_CusPayid Foreign key(Customer_ID) references Customer(CustomerID)



Select * from Payment;

--Rifat
CREATE TABLE DELIVERY(
DELIVERY_ADDRESS VARCHAR(100)NOT NULL,
DELIVERY_ID INT NOT NULL IDENTITY(1,1),
CUSTOMER_ID INT NOT NULL,
PAYMENT_ID INT NOT NULL,
DELIVERY_DATE DATE,
RECORD VARCHAR(50),

CONSTRAINT PK_deliveryId PRIMARY KEY (DELIVERY_ID),
Constraint fk_DelCus_ID Foreign key(CUSTOMER_ID) references Customer(CustomerID),
Constraint fk_DelPay_ID Foreign key(PAYMENT_ID) references Payment(Payment_Id)
);

Select Payment_Id from Payment where Customer_Id=33;


Select * from DELIVERY;
drop table DELIVERY;

INSERT INTO DELIVERY(DELIVERY_ADDRESS,CUSTOMER_ID,PAYMENT_ID,DELIVERY_DATE,RECORD) VALUES('dhaka',40,26,'2022-03-18','Active')