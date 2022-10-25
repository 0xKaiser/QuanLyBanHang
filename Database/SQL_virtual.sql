use QuanLyBanHang
go

--hien thong tin tat ca khach hang => dung trong manager
create or alter view virtual.v_showAllCus as
	select * 
	from person.Customer
go

--hien thong tin tat ca khach hang va permission => dung trong manager
create or alter view virtual.v_showFullCus as
	select *
	from person.Users a, person.Customer b
	where a.idUser = b.idCustomer
go

--hien thong tin tat ca admin => dung trong manager
create or alter view virtual.v_showAllAdmin as
	select *
	from person.Administrator
go 

--hien thong tin san pham => dung trong homepage, manager
create or alter view virtual.v_showAllProduct as
	select *
	from production.Product
go

--hien thong tin user => dung trong sign in, sign up, homepage(profile)
create or alter proc virtual.proc_showUserbyUsername ( @username varchar(max) ) as
begin
	select *
	from person.Users
	where username = @username
end
go

-- hien so dien thoai bi trung => sign up, homepage(profile)
create or alter proc virtual.proc_checkPhoneExisted ( @phone varchar(10) ) as
begin
	select phone
	from person.Customer
	where phone = @phone
end
go

-- check san pham trong gio hang cua user => homepage
create or alter proc virtual.proc_checkPrdInCart ( @idUser varchar(5) ) as
begin
	select *
	from sales.Cart
	where idCustomer = @idUser
end
go

--show thong tin chi tiet san pham trong gio hang => homepage
create or alter view virtual.v_showCart as
	select a.idProduct, a.idCustomer, b.nameProduct, a.quantity, b.price, b.unit
	from sales.Cart a, production.Product b
	where a.idProduct = b.idProduct
go

--show tong tien khach hang mua => manager
create or alter view virtual.v_showReckon as
	select a.idProduct, a.nameProduct, sum(b.quantity) as tong_mua
	from production.Product a, sales.BillDetails b
	where a.idProduct = b.idProduct
	group by a.idProduct, a.nameProduct	
go

--show top mua hang => manager =>top tieu dung
create or alter view virtual.v_showTopBuy as
	select a.idCustomer, a.nameCustomer, a.dayRegister, sum(b.totalPrice) as tong_mua
	from person.Customer a, sales.Bill b
	where a.idCustomer = b.idCustomer
	group by a.idCustomer, a.nameCustomer, a.dayRegister
go

--search sản phẩm => manager
create or alter proc virtual.proc_searchByName ( @name nvarchar(max) ) as
begin 
	select *
	from production.Product
	where nameProduct like N'%'+ @name + '%'
end
go

--show bill chua xac nhan=> manager
create or alter view virtual.v_showBillNotConfirm as
	select * 
	from sales.Bill
	where stt = '0'
go

--tìm khách hàng theo tên
create or alter proc virtual.proc_SearchCustomer( @name varchar(max) ) as
begin 
	select *
	from person.Customer
	where nameCustomer like N'%' + @name + '%'
end
go