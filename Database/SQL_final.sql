--0 creat database
use master
drop database if exists QuanLyBanHang
create database QuanLyBanHang
go

--1 create schema
use QuanLyBanHang
go
create schema person
go
create schema production
go
create schema sales
go
create schema virtual
go

--2 create table admin
create table person.Administrator(
	idAdmin varchar(5) not null,
	nameAdmin nvarchar(max) not null,
	username varchar(50) not null,
	password varchar(50) not null,
	phone varchar(10) not null,
	birthday date,
	sex int,
	address nvarchar(255),
)
go
	
--3 create table  user
create table person.Users (
	idUser varchar(5) not null,
	username varchar(50) not null,
	password varchar(50) not null,
	permission bit,
)
go

--4 create table customer
create table person.Customer(
	idCustomer varchar(5) not null,	
	nameCustomer nvarchar(50) not null,
	phone varchar(10) not null,
	birthday date,
	sex int,
	address nvarchar(255),
	dayRegister date	
)
go

--5 create table type product
create table production.TypeProduct(
	idType varchar(2) not null,
	nameType nvarchar(50) not null,
	description nvarchar(max)	
)
go

--6 create table product
create table production.Product(
	idProduct varchar(5) not null,
	nameProduct nvarchar(max) not null,
	type varchar(2) not null,
	unit nvarchar(50) not null,
	cost money not null,
	price money not null,
	quantityOnHand int not null,
	description nvarchar(max)
)
go

--7 create table import	
create table production.Import(
	idImport varchar(5) not null,
	idAdmin varchar(5) not null,
	totalCost money not null,
	dayImported date not null,
	note nvarchar(max),
)
go

--8 create table import details
create table production.ImportDetails(
	idImportDetails varchar(5) not null,	
	idImport varchar(5) not null,
	idProduct varchar(5) not null,
	quantity int not null,
	cost money not null,
	price money not null,
	note nvarchar(max)
)
go
	
--9 create table bill
create table sales.Bill(
	idBill varchar(5) not null,	
	idCustomer varchar(5) not null,
	totalPrice money not null,
	dayBilled date not null,
	stt bit not null,
	note nvarchar(max)
)
go
					
--10 create table Bill details
create table sales.BillDetails(
	idBillDetails varchar(5) not null,
	idBill varchar(5) not null,
	idProduct varchar(5) not null,
	quantity int not null,
	current_price money not null,
	price money not null,
	note nvarchar(max),
)
go

--11 create table cart
create table sales.Cart(
	idCustomer varchar(5) not null,
	idProduct varchar(5) not null,
	quantity int not null,
)
go

--edit admin 2
alter table person.Administrator add 
	constraint pk_Administrator primary key(idAdmin)
go

--edit user 3
alter table person.Users add		
	constraint pk_users primary key(idUser)
go

--edit customer 4
alter table person.Customer add	
	constraint pk_Customer primary key(idCustomer)
go
alter table person.Customer add
	constraint df_Customer_dayRegister default (getdate()) for dayRegister
go
alter table person.Customer add	
	constraint fk_Customer_idCustomer foreign key(idCustomer)
		references person.Users(idUser)
		on delete cascade on update cascade
go

--edit type product 5
alter table production.TypeProduct add
	constraint pk_TypeProduct primary key(idType)
go
alter table production.TypeProduct add
	constraint df_TypeProduct_description default('') for description
go

--edit product 6
alter table production.Product add
	constraint pk_Product primary key(idProduct)
go
alter table production.Product add
	constraint fk_Product_type foreign key (type)
		references production.TypeProduct(idType)
		on delete cascade on update cascade
go
alter table production.Product add
	constraint df_Product_description default('') for description
go

--edit import 7
alter table production.Import add
	constraint pk_Import primary key(idImport)
go
alter table production.Import add
	constraint fk_Import_idAdmin foreign key (idAdmin) 
		references person.Administrator(idAdmin)
		on delete cascade on update cascade
go
alter table production.Import add
	constraint df_Import_dayImported default (getDate()) for dayImported
go
alter table production.Import add
	constraint df_Import_note default('') for note
go

--edit import details 8
alter table production.ImportDetails add
	constraint pk_ImportDetails primary key(idImportDetails)
go
alter table production.ImportDetails add
	constraint df_ImportDetails_note default('') for note
go
alter table production.ImportDetails add
	constraint fk_ImportDetails_idProduct foreign key (idProduct) 
		references production.Product(idProduct)
		on delete cascade on update cascade
go
alter table production.ImportDetails add
	constraint fk_ImportDetails_idImport foreign key (idImport) 
		references production.Import(idImport)
		on delete cascade on update cascade
go

--edit bill 9
alter table sales.Bill add
	constraint pk_Bill primary key(idBill)
go
alter table sales.Bill add
	constraint df_Bill_dayBil default(getDate()) for dayBilled
go
alter table sales.Bill add
	constraint df_Bill_note default ('') for note
go
alter table sales.Bill add
	constraint fk_Bill_idCustomer foreign key (idCustomer) 
		references person.Customer(idCustomer)
		on delete cascade on update cascade
go

--edit bill details 10 
alter table sales.BillDetails add
	constraint pk_BillDetails primary key(idBillDetails)
go
alter table sales.BillDetails add
	constraint df_BillDetails_note default ('') for note
go
alter table sales.BillDetails add
	constraint fk_BillDetails_idProduct foreign key (idProduct) 
		references production.Product(idProduct)
		on delete cascade on update cascade
go
alter table sales.BillDetails add
	constraint fk_BillDetails_idBill foreign key (idBill) 
		references sales.Bill(idBill) 
		on delete cascade on update cascade
go

--edit cart 11
alter table sales.Cart add
	constraint pk_Cart primary key(idProduct, idCustomer)
go
alter table sales.Cart add
	constraint fk_Cart_idProduct foreign key (idProduct) 
		references production.Product(idProduct)
		on delete cascade on update cascade
go
alter table sales.Cart add
	constraint fk_Cart_idCustomer foreign key (idCustomer) 
		references person.Customer(idCustomer)
		on delete cascade on update cascade
go