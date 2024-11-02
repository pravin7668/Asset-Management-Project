INSERT INTO `employeeasset`.`user` (`first_name`, `last_name`, `phoneno`, `address`, `email`, `password`, `status`) VALUES
('John', 'Doe', '1234567890', '123 Main St, Springfield, IL', 'john.doe@example.com', 'password123', 'Active'),
('Jane', 'Smith', '2345678901', '456 Elm St, Springfield, IL', 'jane.smith@example.com', 'password123', 'Active'),
('Michael', 'Johnson', '3456789012', '789 Oak St, Springfield, IL', 'michael.johnson@example.com', 'password123', 'Inactive'),
('Emily', 'Davis', '4567890123', '101 Pine St, Springfield, IL', 'emily.davis@example.com', 'password123', 'Active'),
('Daniel', 'Brown', '5678901234', '202 Maple St, Springfield, IL', 'daniel.brown@example.com', 'password123', 'Inactive'),
('Olivia', 'Wilson', '6789012345', '303 Birch St, Springfield, IL', 'olivia.wilson@example.com', 'password123', 'Active'),
('James', 'Martinez', '7890123456', '404 Cedar St, Springfield, IL', 'james.martinez@example.com', 'password123', 'Inactive'),
('Sophia', 'Garcia', '8901234567', '505 Redwood St, Springfield, IL', 'sophia.garcia@example.com', 'password123', 'Active'),
('William', 'Miller', '9012345678', '606 Ash St, Springfield, IL', 'william.miller@example.com', 'password123', 'Active'),
('Isabella', 'Lopez', '0123456789', '707 Palm St, Springfield, IL', 'isabella.lopez@example.com', 'password123', 'Inactive');

INSERT INTO `employeeasset`.`asset` (`asset_name`, `asset_category`, `asset_model`, `asset_description`, `asset_value`, `manufacturing_date`, `expiry_date`, `image_url`, `status`) VALUES
('Dell XPS 15', 'Laptop', 'XPS 9570', 'High performance laptop', 1500.00, '2022-01-15', '2025-01-15', 'https://example.com/xps15.jpg', 'Available'),
('Herman Miller Chair', 'Furniture', 'Aeron', 'Ergonomic office chair', 1200.00, '2021-06-01', '2026-06-01', 'https://example.com/aeron.jpg', 'Available'),
('Apple MacBook Pro', 'Laptop', 'MBP 2021', 'Latest MacBook Pro with M1 chip', 2000.00, '2021-11-10', '2025-11-10', 'https://example.com/mbp2021.jpg', 'Available'),
('Samsung Galaxy Tab', 'Gadgets', 'S7', 'High-end Android tablet', 700.00, '2021-03-22', '2024-03-22', 'https://example.com/galaxytab.jpg', 'Available'),
('Tesla Model S', 'Car', 'Model S 2021', 'Electric car with autopilot', 80000.00, '2021-05-10', '2031-05-10', 'https://example.com/modelS.jpg', 'Available'),
('HP Spectre x360', 'Laptop', 'Spectre 13', 'Convertible laptop', 1400.00, '2022-02-15', '2025-02-15', 'https://example.com/spectre.jpg', 'Available'),
('IKEA Desk', 'Furniture', 'Bekant', 'Adjustable desk', 300.00, '2020-07-01', '2025-07-01', 'https://example.com/bekant.jpg', 'Available'),
('GoPro Hero 9', 'Gadgets', 'Hero 9', 'Action camera', 400.00, '2021-08-01', '2024-08-01', 'https://example.com/hero9.jpg', 'Available'),
('Microsoft Surface Pro', 'Laptop', 'Surface Pro 7', '2-in-1 laptop', 1000.00, '2021-09-15', '2024-09-15', 'https://example.com/surfacepro7.jpg', 'Available'),
('Audi A4', 'Car', 'A4 2021', 'Luxury sedan', 40000.00, '2021-04-10', '2031-04-10', 'https://example.com/audiA4.jpg', 'Available'),
('Lenovo ThinkPad X1', 'Laptop', 'X1 Carbon', 'Business laptop', 1600.00, '2021-12-05', '2024-12-05', 'https://example.com/x1carbon.jpg', 'Available'),
('Steelcase Leap', 'Furniture', 'Leap V2', 'Ergonomic chair', 1000.00, '2021-01-20', '2026-01-20', 'https://example.com/leapv2.jpg', 'Available'),
('Sony Alpha Camera', 'Gadgets', 'A7 III', 'Mirrorless camera', 2000.00, '2021-06-15', '2024-06-15', 'https://example.com/a7iii.jpg', 'Available'),
('BMW 3 Series', 'Car', '330i', 'Luxury sedan', 45000.00, '2021-07-25', '2031-07-25', 'https://example.com/bmw330i.jpg', 'Available'),
('Asus ROG Zephyrus', 'Laptop', 'Zephyrus G14', 'Gaming laptop', 1800.00, '2022-03-05', '2025-03-05', 'https://example.com/zephyrus.jpg', 'Available'),
('La-Z-Boy Recliner', 'Furniture', 'Anderson', 'Comfortable recliner', 800.00, '2020-12-15', '2025-12-15', 'https://example.com/recliner.jpg', 'Available'),
('DJI Mavic Air 2', 'Gadgets', 'Mavic Air 2', 'Compact drone', 900.00, '2021-10-01', '2024-10-01', 'https://example.com/mavicair2.jpg', 'Available'),
('Mercedes-Benz C-Class', 'Car', 'C300', 'Luxury sedan', 50000.00, '2021-11-05', '2031-11-05', 'https://example.com/mbc300.jpg', 'Available'),
('Acer Predator Helios', 'Laptop', 'Helios 300', 'Gaming laptop', 1300.00, '2021-09-01', '2024-09-01', 'https://example.com/helios300.jpg', 'Available'),
('Herman Miller Embody', 'Furniture', 'Embody', 'Ergonomic office chair', 1500.00, '2021-02-01', '2026-02-01', 'https://example.com/embody.jpg', 'Available');

INSERT INTO `employeeasset`.`admin` (`first_name`, `last_name`, `phoneno`, `address`, `email`, `password`, `status`) VALUES
('Alexander', 'Hamilton', '1234567890', '123 Elm Street', 'alexander.hamilton@example.com', 'password123', 'Active'),
('Sophia', 'Martinez', '0987654321', '456 Oak Avenue', 'sophia.martinez@example.com', 'password123', 'Active'),
('William', 'Garcia', '5555555555', '789 Pine Road', 'william.garcia@example.com', 'password123', 'Active'),
('Isabella', 'Rodriguez', '4444444444', '101 Maple Lane', 'isabella.rodriguez@example.com', 'password123', 'Active'),
('Henry', 'Martinez', '3333333333', '202 Cedar Street', 'henry.martinez@example.com', 'password123', 'Active'),
('Ava', 'Lopez', '2222222222', '303 Birch Avenue', 'ava.lopez@example.com', 'password123', 'Active'),
('Noah', 'Gonzalez', '1111111111', '404 Spruce Road', 'noah.gonzalez@example.com', 'password123', 'Active'),
('Mia', 'Wilson', '6666666666', '505 Willow Lane', 'mia.wilson@example.com', 'password123', 'Active'),
('Oliver', 'Anderson', '7777777777', '606 Cherry Street', 'oliver.anderson@example.com', 'password123', 'Active'),
('Charlotte', 'Thomas', '8888888888', '707 Aspen Avenue', 'charlotte.thomas@example.com', 'password123', 'Active');

INSERT INTO `customer` (`name`, `purchase_date`, `return_date`, `doj`) VALUES 
('John Doe', '2024-09-01 10:00:00', '2024-09-05 15:00:00', '2024-01-15'),
('Jane Smith', '2024-09-02 11:00:00', '2024-09-06 16:00:00', '2024-02-16'),
('Michael Johnson', '2024-09-03 12:00:00', '2024-09-07 17:00:00', '2024-03-17'),
('Emily Davis', '2024-09-04 13:00:00', '2024-09-08 18:00:00', '2024-04-18'),
('William Brown', '2024-09-05 14:00:00', '2024-09-09 19:00:00', '2024-05-19'),
('Olivia Jones', '2024-09-06 15:00:00', '2024-09-10 20:00:00', '2024-06-20'),
('James Garcia', '2024-09-07 16:00:00', '2024-09-11 21:00:00', '2024-07-21'),
('Sophia Martinez', '2024-09-08 17:00:00', '2024-09-12 22:00:00', '2024-08-22'),
('Benjamin Hernandez', '2024-09-09 18:00:00', '2024-09-13 23:00:00', '2024-09-23'),
('Isabella Wilson', '2024-09-10 19:00:00', '2024-09-14 10:00:00', '2024-10-24'),
('Jacob Lee', '2024-09-11 20:00:00', '2024-09-15 11:00:00', '2024-11-25'),
('Mia Perez', '2024-09-12 21:00:00', '2024-09-16 12:00:00', '2024-12-26'),
('Ethan Clark', '2024-09-13 22:00:00', '2024-09-17 13:00:00', '2024-01-27'),
('Ava Lewis', '2024-09-14 23:00:00', '2024-09-18 14:00:00', '2024-02-28'),
('Alexander Robinson', '2024-09-15 10:00:00', '2024-09-19 15:00:00', '2024-03-29'),
('Charlotte Walker', '2024-09-16 11:00:00', '2024-09-20 16:00:00', '2024-04-30'),
('Daniel Hall', '2024-09-17 12:00:00', '2024-09-21 17:00:00', '2024-05-31'),
('Amelia Young', '2024-09-18 13:00:00', '2024-09-22 18:00:00', '2024-06-01'),
('Matthew Allen', '2024-09-19 14:00:00', '2024-09-23 19:00:00', '2024-07-02'),
('Harper King', '2024-09-20 15:00:00', '2024-09-24 20:00:00', '2024-08-03');


use employeeasset;
describe user;
select * from user;
select * from asset_request;
select * from asset_allocation;
select * from asset;
select * from asset_service;
describe asset_request;
describe asset_allocation;
describe asset_service;

select count(*) from asset_allocation aa 
join asset_request ar on aa.request_id=ar.request_id
where ar.user_id=1;

select count(*) cnt from asset_request ar where ar.user_id=1 and ar.request_type="AssetReturn" and status="Approved";

update asset set status=? where asset_id=(select asset_id from asset_request where request_id=?);
update asset_request set request_type="AssetReturn",status="Pending" where request_id=1 
and  request_type="AssetRequest" and status="Approved";
truncate table asset_service;

select asr.service_id,u.user_id,u.first_name,a.asset_id,a.asset_name,asr.service_type,asr.status from asset_service asr
join asset_request ar on asr.request_id=ar.request_id
join user u on ar.user_id=u.user_id
join asset a on ar.asset_id=a.asset_id
where asr.status=?;

drop database datetime;

use employeeAsset;
CREATE TABLE IF NOT EXISTS `employeeasset`.`login` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` ENUM("Admin", "Employee") NOT NULL,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

drop table login;
delete from user where user_id=19;
truncate table login;

select a.asset_name,a.asset_category,aa.issued_date,aa.returned_date,ar.status from asset a 
join asset_request ar on a.asset_id=ar.asset_id
join user u on u.user_id=ar.user_id
join asset_allocation aa on aa.request_id=ar.request_id
where ar.status=? and ar.request_type=? and u.user_id=?;

select  u.user_id,u.first_name,a.asset_id,a.asset_name,aa.issued_date from asset_request ar 
join user u on ar.user_id=u.user_id
join asset a on ar.asset_id=a.asset_id 
join asset_allocation aa on aa.request_id=ar.request_id 
where ar.status=? and ar.request_type=? and u.user_id=?;

select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aal.issued_date,aau.audit_status from asset_request ar 
join user u on ar.user_id=u.user_id
join asset a on ar.asset_id=a.asset_id 
join asset_allocation aal on aal.request_id=ar.request_id 
join asset_audit aau on aau.request_id=ar.request_id
where aau.audit_status="Pending";

describe asset_audit;
delete from asset_audit where audit_id=2;

insert into asset_audit(`request_id`) values();

select ar.request_id, u.user_id,u.first_name,a.asset_id,a.asset_name,aa.issued_date from asset_request ar join user u on ar.user_id=u.user_id
join asset a on ar.asset_id=a.asset_id 
join asset_allocation aa on aa.request_id=ar.request_id 
where ar.status="Pending" and ar.request_type="AssetReturn";

select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aa.issued_date,ar.request_type,ar.status from asset_request ar 
join user u on ar.user_id=u.user_id  
join asset a on ar.asset_id=a.asset_id 
join asset_allocation aa on aa.request_id=ar.request_id 
where ar.request_type="AssetRequest" and u.user_id=20;

select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,ar.request_type,ar.status from asset_request ar 
join user u on ar.user_id=u.user_id  
join asset a on ar.asset_id=a.asset_id 
where ar.status="Pending";

select asr.service_id,u.user_id,u.first_name,a.asset_id,a.asset_name,asr.service_type,asr.description,asr.status from asset_service asr 
join asset_request ar on asr.request_id=ar.request_id 
join user u on ar.user_id=u.user_id 
join asset a on ar.asset_id=a.asset_id 
where u.user_id=20;

select * from asset where (asset_name like "%her%"  and asset_category="Furniture") and status="Available";

select * from asset_request ar
join user u on ar.user_id=u.user_id 
join asset a on ar.asset_id=a.asset_id  
where ar.status="Pending";

select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aa.issued_date from asset_request ar 
				join user u on ar.user_id=u.user_id 
				join asset a on ar.asset_id=a.asset_id 
				join asset_allocation aa on aa.request_id=ar.request_id 
				where ar.status="Approved" and ar.request_type="AssetRequest" and u.user_id=?;
                
select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name,aa.issued_date from asset_request ar 
				join user u on ar.user_id=u.user_id 
				join asset a on ar.asset_id=a.asset_id 
				join asset_allocation aa on aa.request_id=ar.request_id 
				where (ar.status="Approved" and ar.request_type="AssetRequest") and (u.first_name like "%%" or u.email like "%%");

select  ar.request_id,u.user_id,u.first_name,a.asset_id,a.asset_name from asset_request ar 
				join user u on ar.user_id=u.user_id 
				join asset a on ar.asset_id=a.asset_id 
				where ar.status="Pending" and ar.request_type="AssetRequest" and u.user_id=22;
use employeeasset;

select * from admin;
delete from admin where admin_id=17;
select * from user;
delete from user where user_id=32;
select * from login;
delete from login where id=20;

select * from asset_request;
use employeeasset;
SELECT COLUMN_TYPE AS enum_values 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_SCHEMA = 'employeeasset' 
  AND TABLE_NAME = 'asset' 
  AND COLUMN_NAME = 'asset_category';

USE datetime;




use datetime;


INSERT INTO `customer` (`name`, `purchase_date`, `return_date`, `doj`) VALUES 
('Pravin Doe', '2024-09-01 10:00:00', '2024-08-05 15:00:00', '2024-01-15');



SELECT * from customer where return_date = DATE_SUB(now(),INTERVAL 10 MONTH);

SELECT *
FROM customer
WHERE return_date >= DATE_FORMAT(CURDATE(), '%Y-%m-01 00:00:00')
  AND return_date < DATE_FORMAT(CURDATE() + INTERVAL 30 Day, '%Y-%m-01 00:00:00');

select DATE_FORMAT(curdate() + INTERVAL 30 day, '%Y-%m-01 00:00:00');

SELECT * 
FROM customer
WHERE return_date >= '2024-09-19 16:00:00'
  AND return_date < '2024-09-19 23:59:59';

select now() - interval 6 month;


select request_id from asset_request where asset_id=1;
delete from asset_allocation where request_id=0;
delete from asset_request where request_id=10;
delete from asset_audit where request_id=1;
select count(*) from asset_service where request_id=6;

delete from asset_service where request_id=6;
select count(*) cnt from asset where asset_id=3 and status="Available";

	
	
-- select count(*) from asset_ where request_id=1;














