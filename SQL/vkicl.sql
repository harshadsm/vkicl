-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 23, 2016 at 07:25 AM
-- Server version: 5.6.25
-- PHP Version: 5.5.31

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vkicl`
--

DELIMITER $$
--
-- Procedures
--
DROP PROCEDURE IF EXISTS `sp_delete_dispatchorder`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_delete_dispatchorder`(IN `dispatch_no` INT, IN `usr` VARCHAR(100), OUT `message` VARCHAR(100))
BEGIN

DECLARE des VARCHAR(100);
set des = concat('Deleted Dispatch order no. ',dispatch_no);

delete from dispatch_details
where dispatch_order_id = dispatch_no;

delete from dispatch_order
where dispatch_order_id = dispatch_no;

insert into activity_log  (username, timestamp, description)
values (usr,NOW(),des);

set message = concat('Success: ',des);

END$$

DROP PROCEDURE IF EXISTS `sp_delete_stock`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_delete_stock`(IN `stock_id` INT, IN `usr` VARCHAR(100), OUT `message` VARCHAR(100))
BEGIN

DECLARE des VARCHAR(100);
set des = concat('Deleted stock ',stock_id);

update stock_balance s
set s.is_delete = 1,
    s.update_ts = NOW(),
    s.update_ui = usr
where s.stock_balance_id = stock_id;

insert into activity_log  (username, timestamp, description)
values (usr,NOW(),des);

set message = 'Success: Stock deleted successfully';


END$$

DROP PROCEDURE IF EXISTS `sp_insert_dispatch_order`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_dispatch_order`(IN `poNo` VARCHAR(1000), IN `dte` DATE, IN `vehicle` VARCHAR(1000), IN `handle` VARCHAR(1000), IN `transporter` VARCHAR(1000), IN `toUs` VARCHAR(1000), IN `toParty` VARCHAR(1000), IN `toPay` VARCHAR(1000), IN `prepaid` VARCHAR(1000), IN `perMTFix` VARCHAR(1000), IN `lumsum` VARCHAR(1000), IN `buyerName` VARCHAR(1000), IN `consigneeName` VARCHAR(1000), IN `brokerName` VARCHAR(1000), IN `brokerage` VARCHAR(1000), IN `brokerageUnit` VARCHAR(1000), IN `paymentTerms` VARCHAR(1000), IN `loadingCharges` VARCHAR(1000), IN `loadingChargesUnit` VARCHAR(1000), IN `cuttingCharges` VARCHAR(1000), IN `cuttingChargesUnit` VARCHAR(1000), IN `mtc` VARCHAR(1000), IN `inspection` VARCHAR(1000), IN `inspectionCharges` VARCHAR(1000), IN `utReport` VARCHAR(1000), IN `labReport` VARCHAR(1000), IN `toAcc` VARCHAR(1000), IN `comments` VARCHAR(1000), IN `total` DECIMAL(10,3), IN `material` VARCHAR(1000), IN `mill` VARCHAR(1000), IN `grade` VARCHAR(1000), IN `len` VARCHAR(1000), IN `width` VARCHAR(1000), IN `thick` VARCHAR(1000), IN `qty` VARCHAR(1000), IN `actual_wt` VARCHAR(1000), IN `actual_unt` VARCHAR(1000), IN `rate` VARCHAR(1000), IN `rateUnit` VARCHAR(1000), IN `taxes` VARCHAR(1000), IN `exise` VARCHAR(1000), IN `tot_ip` INT, IN `usr` VARCHAR(1000), OUT `id` INT, OUT `message` VARCHAR(100))
BEGIN

DECLARE lst_rec INT;
DECLARE rec_exists INT;

DECLARE pos_mat INT;
DECLARE item_mat VARCHAR(1000);
DECLARE inputstring_mat VARCHAR(1000);

DECLARE pos_typ INT;
DECLARE item_typ VARCHAR(1000);
DECLARE inputstring_typ VARCHAR(1000);

DECLARE pos_grade INT;
DECLARE item_grade VARCHAR(1000);
DECLARE inputstring_grade VARCHAR(1000);

DECLARE pos_len INT;
DECLARE item_len VARCHAR(1000);
DECLARE inputstring_len VARCHAR(1000);

DECLARE pos_width INT;
DECLARE item_width VARCHAR(1000);
DECLARE inputstring_width VARCHAR(1000);

DECLARE pos_thick INT;
DECLARE item_thick VARCHAR(1000);
DECLARE inputstring_thick VARCHAR(1000);

DECLARE pos_qty INT;
DECLARE item_qty VARCHAR(1000);
DECLARE inputstring_qty VARCHAR(1000);

DECLARE pos_wt INT;
DECLARE item_wt VARCHAR(1000);
DECLARE inputstring_wt VARCHAR(1000);

DECLARE pos_unt INT;
DECLARE item_unt VARCHAR(1000);
DECLARE inputstring_unt VARCHAR(1000);

DECLARE pos_rate INT;
DECLARE item_rate VARCHAR(1000);
DECLARE inputstring_rate VARCHAR(1000);

DECLARE pos_rateUnit INT;
DECLARE item_rateUnit VARCHAR(1000);
DECLARE inputstring_rateUnit VARCHAR(1000);

DECLARE pos_tax INT;
DECLARE item_tax VARCHAR(1000);
DECLARE inputstring_tax VARCHAR(1000);

DECLARE pos_exise INT;
DECLARE item_exise VARCHAR(1000);
DECLARE inputstring_exise VARCHAR(1000);

SET rec_exists = 0;
SET inputstring_mat = material;
SET inputstring_typ = mill;
SET inputstring_grade = grade;
SET inputstring_len = len;
SET inputstring_width = width;
SET inputstring_thick = thick;
SET inputstring_qty = qty;
SET inputstring_wt = actual_wt;
SET inputstring_unt = actual_unt;
SET inputstring_rate = rate;
SET inputstring_rateUnit = rateUnit;
SET inputstring_tax = taxes;
SET inputstring_exise = exise;

if right(inputstring_mat, 1) <> ',' then
SET inputstring_mat = concat(material, ',');
end if;

if right(inputstring_typ, 1) <> ',' then
SET inputstring_typ = concat(mill, ',');
end if;

if right(inputstring_grade, 1) <> ',' then
SET inputstring_grade = concat(grade, ',');
end if;

if (inputstring_len IS NOT NULL AND right(inputstring_len, 1) <> ',') then
SET inputstring_len = concat(len, ',');
end if;

if (inputstring_width IS NOT NULL AND right(inputstring_width, 1) <> ',') then
SET inputstring_width = concat(width, ',');
end if;

if (inputstring_thick IS NOT NULL AND right(inputstring_thick, 1) <> ',') then
SET inputstring_thick = concat(thick, ',');
end if;

if (inputstring_qty IS NOT NULL AND right(inputstring_qty, 1) <> ',') then
SET inputstring_qty = concat(qty, ',');
end if;

if (inputstring_wt IS NOT NULL AND right(inputstring_wt, 1) <> ',') then
SET inputstring_wt = concat(actual_wt, ',');
end if;

if (inputstring_unt IS NOT NULL AND right(inputstring_unt, 1) <> ',') then
SET inputstring_unt = concat(actual_unt, ',');
end if;

if (inputstring_rate IS NOT NULL AND right(inputstring_rate, 1) <> ',') then
SET inputstring_rate = concat(rate, ',');
end if;

if (inputstring_rateUnit IS NOT NULL AND right(inputstring_rateUnit, 1) <> ',') then
SET inputstring_rateUnit = concat(rateUnit, ',');
end if;

if (inputstring_tax IS NOT NULL AND right(inputstring_tax, 1) <> ',') then
SET inputstring_tax = concat(taxes, ',');
end if;

if (inputstring_exise IS NOT NULL AND right(inputstring_exise, 1) <> ',') then
SET inputstring_exise = concat(exise, ',');
end if;

SELECT COUNT(*) INTO rec_exists FROM dispatch_order do where do.poNo = poNo AND do.date = dte AND do.vehicle = vehicle;

IF ( rec_exists > 0) THEN
SET message = 'Record already exists for same PoNo,date and Vechicle combination';
ELSE
INSERT INTO dispatch_order  (poNo,date,vehicle,handleby,transporter_name,toUs,toParty,toPay,prepaid,perMTFix
										 ,lumsum,buyerName,consigneeName,brokerName,brokerage,brokerageUnit,paymentTerms,
										 loadingCharges,loadingChargesUnit,cuttingCharges,cuttingChargesUnit,mtc,inspection,
										 inspectionCharges,utReport,labReport,toAcc,comments,Total,create_ui,update_ui,
										 create_ts,update_ts)
VALUES 	(poNo,dte,vehicle,handle,transporter,toUs,toParty,toPay,prepaid,perMTFix,lumsum,buyerName,consigneeName,brokerName,
			 brokerage,brokerageUnit,paymentTerms,loadingCharges,loadingChargesUnit,cuttingCharges,cuttingChargesUnit,mtc,
			 inspection,inspectionCharges,utReport,labReport,toAcc,comments,total,usr,usr,NOW(),NOW());

SELECT LAST_INSERT_ID() INTO lst_rec;
SET id = lst_rec;

WHILE (tot_ip > 0) do

set pos_mat = INSTR(inputstring_mat,',');
set item_mat = LEFT(inputstring_mat, pos_mat-1);
set inputstring_mat = substring(inputstring_mat, pos_mat+1);

set pos_typ = INSTR(inputstring_typ,',');
set item_typ = LEFT(inputstring_typ, pos_typ-1);
set inputstring_typ = substring(inputstring_typ, pos_typ+1);

set pos_grade = INSTR(inputstring_grade,',');
set item_grade = LEFT(inputstring_grade, pos_grade-1);
set inputstring_grade = substring(inputstring_grade, pos_grade+1);

set pos_len = INSTR(inputstring_len,',');
set item_len = LEFT(inputstring_len, pos_len-1);
set inputstring_len = substring(inputstring_len, pos_len+1);

set pos_width = INSTR(inputstring_width,',');
set item_width = LEFT(inputstring_width, pos_width-1);
set inputstring_width = substring(inputstring_width, pos_width+1);

set pos_thick = INSTR(inputstring_thick,',');
set item_thick = LEFT(inputstring_thick, pos_thick-1);
set inputstring_thick = substring(inputstring_thick, pos_thick+1);

set pos_qty = INSTR(inputstring_qty,',');
set item_qty = LEFT(inputstring_qty, pos_qty-1);
set inputstring_qty = substring(inputstring_qty, pos_qty+1);

set pos_wt = INSTR(inputstring_wt,',');
set item_wt = LEFT(inputstring_wt, pos_wt-1);
set inputstring_wt = substring(inputstring_wt, pos_wt+1);

set pos_unt = INSTR(inputstring_unt,',');
set item_unt = LEFT(inputstring_unt, pos_unt-1);
set inputstring_unt = substring(inputstring_unt, pos_unt+1);

set pos_rate = INSTR(inputstring_rate,',');
set item_rate = LEFT(inputstring_rate, pos_rate-1);
set inputstring_rate = substring(inputstring_rate, pos_rate+1);

set pos_rateUnit = INSTR(inputstring_rateUnit,',');
set item_rateUnit= LEFT(inputstring_rateUnit, pos_rateUnit-1);
set inputstring_rateUnit = substring(inputstring_rateUnit, pos_rateUnit+1);

set pos_tax = INSTR(inputstring_tax,',');
set item_tax = LEFT(inputstring_tax, pos_tax-1);
set inputstring_tax = substring(inputstring_tax, pos_tax+1);

set pos_exise = INSTR(inputstring_exise,',');
set item_exise = LEFT(inputstring_exise, pos_exise-1);
set inputstring_exise = substring(inputstring_exise, pos_exise+1);

INSERT INTO dispatch_details  (dispatch_order_id,make,millName,grade,length,width,thickness,
											qty,actWt,actWtUnit,rate,rateUnit,taxes,excise,create_ui,update_ui,create_ts,update_ts)
VALUES (lst_rec,item_mat,item_typ,item_grade,item_len,item_width,item_thick,item_qty,item_wt,item_unt,item_rate,item_rateUnit,
		  item_tax,item_exise,usr,usr,NOW(),NOW());

set tot_ip = tot_ip-1;
end while;
					
SET message = concat('Dispatch Order created Successfully, Dispatch Order Id is:- ',lst_rec);

END IF;

END$$

DROP PROCEDURE IF EXISTS `sp_insert_material`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_material`(IN `warehouse_inward_detail_id` INT, IN `doc` LONGBLOB, IN `file_name` VARCHAR(100), IN `file_size` DOUBLE, IN `usr` VARCHAR(100), OUT `message` VARCHAR(100))
BEGIN

DECLARE lst_rec INT;
DECLARE rec_exist INT;

SET rec_exist = 0;

SELECT count(*) INTO rec_exist
FROM warehouse_inward_details wd
WHERE wd.warehouse_in_detail_id = warehouse_inward_detail_id
and (IFNULL (wd.material_id,'') = '' or wd.material_id = ' ' or wd.material_id = '');

IF ( rec_exist > 0) THEN

insert into material_doc (file_name,file_size,file,create_ui,update_ui,create_ts,update_ts)
values (file_name,file_size,doc,usr,usr,NOW(),NOW());

SELECT LAST_INSERT_ID() INTO lst_rec;

update
warehouse_inward_details wd
set wd.material_id = lst_rec
where wd.warehouse_in_detail_id = warehouse_inward_detail_id;

/*update
warehouse_inward wi
inner join warehouse_inward_details wd on wd.warehouse_inward_id = wi.warehouse_inward_id
inner join stock_balance s on (s.mill_name = wi.mill_name and s.length = wi.length and s.width = wi.width and
										 s.thickness = wi.thickness and s.material_make = wi.material_make and s.grade = wi.grade
										 and s.heat_no = wd.heat_no and s.plate_no = wd.plate_no)
set s.material_id = lst_rec; */

set message = 'Material document inserted Successfully';

ELSE IF (rec_exist = 0) THEN

update
warehouse_inward_details wd
inner join material_doc md on wd.material_id = md.material_id
set md.file_name = file_name,
    md.file_size = file_size,
    md.file = doc
where wd.warehouse_in_detail_id = warehouse_inward_detail_id;

set message = 'Material document updated Successfully';
END IF;
END IF;
END$$

DROP PROCEDURE IF EXISTS `sp_insert_port_inward`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_port_inward`(IN `vendor` VARCHAR(1000), IN `vessel` VARCHAR(1000), IN `vessel_dte` VARCHAR(1000), IN `be_no` VARCHAR(1000), IN `material` VARCHAR(1000), IN `mill_nm` VARCHAR(1000), IN `make` VARCHAR(1000), IN `grade` VARCHAR(1000), IN `mdesc` VARCHAR(1000), IN `bewght` VARCHAR(1000), IN `beunit` VARCHAR(1000), IN `tot_ip` INT, IN `usr` VARCHAR(1000), OUT `message` VARCHAR(100))
BEGIN

DECLARE rec_exist INT;
DECLARE lst_rec INT;

DECLARE pos_be INT;
DECLARE item_be VARCHAR(1000);
DECLARE inputstring_be VARCHAR(1000);

DECLARE pos_mat INT;
DECLARE item_mat VARCHAR(1000);
DECLARE inputstring_mat VARCHAR(1000);

DECLARE pos_mill INT;
DECLARE item_mill VARCHAR(1000);
DECLARE inputstring_mill VARCHAR(1000);

DECLARE pos_make INT;
DECLARE item_make VARCHAR(1000);
DECLARE inputstring_make VARCHAR(1000);

DECLARE pos_grade INT;
DECLARE item_grade VARCHAR(1000);
DECLARE inputstring_grade VARCHAR(1000);

DECLARE pos_desc INT;
DECLARE item_desc VARCHAR(1000);
DECLARE inputstring_desc VARCHAR(1000);

DECLARE pos_wt INT;
DECLARE item_wt VARCHAR(1000);
DECLARE inputstring_wt VARCHAR(1000);

DECLARE pos_unit INT;
DECLARE item_unit VARCHAR(1000);
DECLARE inputstring_unit VARCHAR(1000);

SET rec_exist = 0;
SET pos_be = 0;
SET pos_mat = 0;
SET inputstring_be = be_no;
SET inputstring_mat = material;
SET inputstring_mill = mill_nm;
SET inputstring_make = make;
SET inputstring_grade = grade;
SET inputstring_desc = mdesc;
SET inputstring_wt = bewght;
SET inputstring_unit = beunit;

if (inputstring_be IS NOT NULL AND right(inputstring_be, 1) <> ',') then
SET inputstring_be = concat(be_no, ',');
end if;

if right(inputstring_mat, 1) <> ',' then
SET inputstring_mat = concat(material, ',');
end if;

if right(inputstring_mill, 1) <> ',' then
SET inputstring_mill = concat(mill_nm, ',');
end if;

if right(inputstring_make, 1) <> ',' then
SET inputstring_make = concat(make, ',');
end if;

if right(inputstring_grade, 1) <> ',' then
SET inputstring_grade = concat(grade, ',');
end if;

if (inputstring_desc IS NOT NULL AND right(inputstring_desc, 1) <> ',') then
SET inputstring_desc = concat(mdesc, ',');
end if;

if right(inputstring_wt, 1) <> ',' then
SET inputstring_wt = concat(inputstring_wt, ',');
end if;

if right(inputstring_unit, 1) <> ',' then
SET inputstring_unit = concat(beunit, ',');
end if;

SELECT COUNT(*) INTO rec_exist FROM port_inward_shipment ps WHERE ps.vendor_name = vendor AND ps.vessel_name = vessel AND ps.vessel_date = vessel_dte;
IF ( rec_exist > 0) THEN
SET message = 'This combination already exist';

ELSE
INSERT INTO port_inward_shipment (vendor_name,vessel_name,vessel_date,create_ui,update_ui,create_ts,update_ts) 
VALUES (vendor,vessel,vessel_dte,usr,usr,NOW(),NOW());
SELECT LAST_INSERT_ID() INTO lst_rec;
/*UPDATE port_inward_shipment ps SET ps.create_ts = NOW() 
WHERE ps.port_inwd_shipment_id = lst_rec; */


WHILE (tot_ip > 0) do
set pos_be = INSTR(inputstring_be,',');
set item_be = LEFT(inputstring_be, pos_be-1);
set inputstring_be = substring(inputstring_be, pos_be+1);

set pos_mat = INSTR(inputstring_mat,',');
set item_mat = LEFT(inputstring_mat, pos_mat-1);
set inputstring_mat = substring(inputstring_mat, pos_mat+1);

set pos_mill = INSTR(inputstring_mill,',');
set item_mill = LEFT(inputstring_mill, pos_mill-1);
set inputstring_mill = substring(inputstring_mill, pos_mill+1);

set pos_make = INSTR(inputstring_make,',');
set item_make = LEFT(inputstring_make, pos_make-1);
set inputstring_make = substring(inputstring_make, pos_make+1);

set pos_grade = INSTR(inputstring_grade,',');
set item_grade = LEFT(inputstring_grade, pos_grade-1);
set inputstring_grade = substring(inputstring_grade, pos_grade+1);

set pos_desc = INSTR(inputstring_desc,',');
set item_desc = LEFT(inputstring_desc, pos_desc-1);
set inputstring_desc = substring(inputstring_desc, pos_desc+1);

set pos_wt = INSTR(inputstring_wt,',');
set item_wt = LEFT(inputstring_wt, pos_wt-1);
set inputstring_wt = substring(inputstring_wt, pos_wt+1);

set pos_unit = INSTR(inputstring_unit,',');
set item_unit = LEFT(inputstring_unit, pos_unit-1);
set inputstring_unit = substring(inputstring_unit, pos_unit+1);

insert into port_inward (port_inwd_shipment_id,be_no,material_type,mill_name,material_make,material_grade,description,be_weight,be_wt_unit,create_ui,update_ui,create_ts,update_ts) 
values (lst_rec,item_be,item_mat,item_mill,item_make,item_grade,item_desc,item_wt,item_unit,usr,usr,NOW(),NOW());
set tot_ip = tot_ip-1;
end while;
SET message = 'Records added successfully';
END IF;

END$$

DROP PROCEDURE IF EXISTS `sp_insert_port_inward_details`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_port_inward_details`(IN `array_str` VARCHAR(1000), IN `len` VARCHAR(1000), IN `width` VARCHAR(1000), IN `thick` VARCHAR(1000), IN `weight` VARCHAR(1000), IN `unit` VARCHAR(1000), IN `qty` VARCHAR(1000), IN `usr` VARCHAR(1000), OUT `message` VARCHAR(100))
BEGIN

DECLARE in_str VARCHAR(1000);
DECLARE pos_in_str INT;
DECLARE item_in_str VARCHAR(1000);
DECLARE tot_cnt INT;

DECLARE pos_len INT;
DECLARE item_len VARCHAR(1000);
DECLARE inputstring_len VARCHAR(1000);
DECLARE pos_width INT;
DECLARE item_width VARCHAR(1000);
DECLARE inputstring_width VARCHAR(1000);
DECLARE pos_thick INT;
DECLARE item_thick VARCHAR(1000);
DECLARE inputstring_thick VARCHAR(1000);
DECLARE pos_wt INT;
DECLARE item_wt VARCHAR(1000);
DECLARE inputstring_wt VARCHAR(1000);
DECLARE pos_unt INT;
DECLARE item_unt VARCHAR(1000);
DECLARE inputstring_unt VARCHAR(1000);
DECLARE pos_qty INT;
DECLARE item_qty VARCHAR(1000);
DECLARE inputstring_qty VARCHAR(1000);

SET in_str = array_str;
SET inputstring_len = len;
SET inputstring_width = width;
SET inputstring_thick = thick;
SET inputstring_wt = weight;
SET inputstring_unt = unit;
SET inputstring_qty = qty;

if (in_str IS NOT NULL AND right(in_str, 1) <> ',') then
SET in_str = concat(in_str, ',');
end if;

SET tot_cnt = (LENGTH(in_str) - LENGTH(REPLACE(in_str, ',', '')));


if (inputstring_len IS NOT NULL AND right(inputstring_len, 1) <> ',') then
SET inputstring_len = concat(len, ',');
end if;
if (inputstring_width IS NOT NULL AND right(inputstring_width, 1) <> ',') then
SET inputstring_width = concat(width, ',');
end if;
if (inputstring_thick IS NOT NULL AND right(inputstring_thick, 1) <> ',') then
SET inputstring_thick = concat(thick, ',');
end if;
if (inputstring_wt IS NOT NULL AND right(inputstring_wt, 1) <> ',') then
SET inputstring_wt = concat(weight, ',');
end if;
if (inputstring_unt IS NOT NULL AND right(inputstring_unt, 1) <> ',') then
SET inputstring_unt = concat(unit, ',');
end if;
if (inputstring_qty IS NOT NULL AND right(inputstring_qty, 1) <> ',') then
SET inputstring_qty = concat(qty, ',');
end if;

IF (in_str IS NOT NULL) THEN
WHILE (tot_cnt > 0) do
set pos_in_str = INSTR(in_str,',');
set item_in_str = LEFT(in_str, pos_in_str-1);
set in_str = substring(in_str, pos_in_str+1);

set pos_len = INSTR(inputstring_len,',');
set item_len = LEFT(inputstring_len, pos_len-1);
set inputstring_len = substring(inputstring_len, pos_len+1);

set pos_width = INSTR(inputstring_width,',');
set item_width = LEFT(inputstring_width, pos_width-1);
set inputstring_width = substring(inputstring_width, pos_width+1);

set pos_thick = INSTR(inputstring_thick,',');
set item_thick = LEFT(inputstring_thick, pos_thick-1);
set inputstring_thick = substring(inputstring_thick, pos_thick+1);

set pos_wt = INSTR(inputstring_wt,',');
set item_wt = LEFT(inputstring_wt, pos_wt-1);
set inputstring_wt = substring(inputstring_wt, pos_wt+1);

set pos_unt = INSTR(inputstring_unt,',');
set item_unt = LEFT(inputstring_unt, pos_unt-1);
set inputstring_unt = substring(inputstring_unt, pos_unt+1);

set pos_qty = INSTR(inputstring_qty,',');
set item_qty = LEFT(inputstring_qty, pos_qty-1);
set inputstring_qty = substring(inputstring_qty, pos_qty+1);

DELETE FROM port_inward_details WHERE port_inward_id = item_in_str;

INSERT INTO port_inward_details (port_inward_id,length,width,thickness,be_weight,be_wt_unit,quantity,create_ui,update_ui,create_ts,update_ts) 
VALUES (item_in_str,item_len,item_width,item_thick,item_wt,item_unt,item_qty,usr,usr,NOW(),NOW());
set tot_cnt = tot_cnt - 1;
end while;

SET message = 'Records added successfully';
END IF;
END$$

DROP PROCEDURE IF EXISTS `sp_insert_port_outward`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_port_outward`(IN `warehouse` VARCHAR(1000), IN `customer` VARCHAR(1000), IN `vehicle_no` VARCHAR(1000), IN `vehicle_dt` DATE, IN `vessel_nm` VARCHAR(1000), IN `vessel_dt` VARCHAR(1000), IN `be_no` VARCHAR(1000), IN `material` VARCHAR(1000), IN `grade` VARCHAR(1000), IN `mdesc` VARCHAR(1000), IN `len` VARCHAR(1000), IN `width` VARCHAR(1000), IN `thick` VARCHAR(1000), IN `actual_wt` VARCHAR(1000), IN `actual_unt` VARCHAR(1000), IN `section_wt` VARCHAR(1000), IN `section_unt` VARCHAR(1000), IN `qty` VARCHAR(1000), IN `tot_ip` INT, IN `usr` VARCHAR(1000), OUT `message` VARCHAR(100))
BEGIN

DECLARE rec_exist INT;
DECLARE lst_rec INT;

DECLARE pos_vslnm INT;
DECLARE item_vslnm VARCHAR(1000);
DECLARE inputstring_vslnm VARCHAR(1000);

DECLARE pos_vsldt INT;
DECLARE item_vsldt VARCHAR(1000);
DECLARE inputstring_vsldt VARCHAR(1000);

DECLARE pos_be INT;
DECLARE item_be VARCHAR(1000);
DECLARE inputstring_be VARCHAR(1000);

DECLARE pos_mat INT;
DECLARE item_mat VARCHAR(1000);
DECLARE inputstring_mat VARCHAR(1000);

DECLARE pos_grade INT;
DECLARE item_grade VARCHAR(1000);
DECLARE inputstring_grade VARCHAR(1000);

DECLARE pos_desc INT;
DECLARE item_desc VARCHAR(1000);
DECLARE inputstring_desc VARCHAR(1000);

DECLARE pos_len INT;
DECLARE item_len VARCHAR(1000);
DECLARE inputstring_len VARCHAR(1000);

DECLARE pos_width INT;
DECLARE item_width VARCHAR(1000);
DECLARE inputstring_width VARCHAR(1000);

DECLARE pos_thick INT;
DECLARE item_thick VARCHAR(1000);
DECLARE inputstring_thick VARCHAR(1000);

DECLARE pos_wt INT;
DECLARE item_wt VARCHAR(1000);
DECLARE inputstring_wt VARCHAR(1000);

DECLARE pos_unt INT;
DECLARE item_unt VARCHAR(1000);
DECLARE inputstring_unt VARCHAR(1000);

DECLARE pos_secwt INT;
DECLARE item_secwt VARCHAR(1000);
DECLARE inputstring_secwt VARCHAR(1000);

DECLARE pos_secunt INT;
DECLARE item_secunt VARCHAR(1000);
DECLARE inputstring_secunt VARCHAR(1000);

DECLARE pos_qty INT;
DECLARE item_qty VARCHAR(1000);
DECLARE inputstring_qty VARCHAR(1000);


SET rec_exist = 0;
SET inputstring_vslnm = vessel_nm;
SET inputstring_vsldt = vessel_dt;
SET inputstring_be = be_no;
SET inputstring_mat = material;
SET inputstring_grade = grade;
SET inputstring_desc = mdesc;
SET inputstring_len = len;
SET inputstring_width = width;
SET inputstring_thick = thick;
SET inputstring_wt = actual_wt;
SET inputstring_unt = actual_unt;
SET inputstring_secwt = section_wt;
SET inputstring_secunt = section_unt;
SET inputstring_qty = qty;

if (inputstring_vslnm IS NOT NULL AND right(inputstring_vslnm, 1) <> ',') then
SET inputstring_vslnm = concat(vessel_nm, ',');
end if;

if (inputstring_vsldt IS NOT NULL AND right(inputstring_vsldt, 1) <> ',') then
SET inputstring_vsldt = concat(vessel_dt, ',');
end if;

if (inputstring_be IS NOT NULL AND right(inputstring_be, 1) <> ',') then
SET inputstring_be = concat(be_no, ',');
end if;

if right(inputstring_mat, 1) <> ',' then
SET inputstring_mat = concat(material, ',');
end if;

if right(inputstring_grade, 1) <> ',' then
SET inputstring_grade = concat(grade, ',');
end if;

if (inputstring_desc IS NOT NULL AND right(inputstring_desc, 1) <> ',') then
SET inputstring_desc = concat(mdesc, ',');
end if;

if (inputstring_len IS NOT NULL AND right(inputstring_len, 1) <> ',') then
SET inputstring_len = concat(len, ',');
end if;

if (inputstring_width IS NOT NULL AND right(inputstring_width, 1) <> ',') then
SET inputstring_width = concat(width, ',');
end if;

if (inputstring_thick IS NOT NULL AND right(inputstring_thick, 1) <> ',') then
SET inputstring_thick = concat(thick, ',');
end if;

if (inputstring_wt IS NOT NULL AND right(inputstring_wt, 1) <> ',') then
SET inputstring_wt = concat(actual_wt, ',');
end if;

if (inputstring_unt IS NOT NULL AND right(inputstring_unt, 1) <> ',') then
SET inputstring_unt = concat(actual_unt, ',');
end if;

if (inputstring_secwt IS NOT NULL AND right(inputstring_secwt, 1) <> ',') then
SET inputstring_secwt = concat(section_wt, ',');
end if;

if (inputstring_secunt IS NOT NULL AND right(inputstring_secunt, 1) <> ',') then
SET inputstring_secunt = concat(section_unt, ',');
end if;

if (inputstring_qty IS NOT NULL AND right(inputstring_qty, 1) <> ',') then
SET inputstring_qty = concat(qty, ',');
end if;

SELECT COUNT(*) INTO rec_exist FROM port_outward_shipment ps WHERE ps.vehicle_number = vehicle_no AND ps.vehicle_date = vehicle_dt;
IF ( rec_exist > 0) THEN
SET message = 'Vehicle and Vehicle Date combination already exist';

ELSE
INSERT INTO port_outward_shipment (warehouse_name,customer_name,vehicle_number,vehicle_date,create_ui,update_ui,create_ts,update_ts) 
VALUES (warehouse,customer,vehicle_no,vehicle_dt,usr,usr,NOW(),NOW());
SELECT LAST_INSERT_ID() INTO lst_rec;


WHILE (tot_ip > 0) do
set pos_vslnm = INSTR(inputstring_vslnm,',');
set item_vslnm = LEFT(inputstring_vslnm, pos_vslnm-1);
set inputstring_vslnm = substring(inputstring_vslnm, pos_vslnm+1);

set pos_vsldt = INSTR(inputstring_vsldt,',');
set item_vsldt = LEFT(inputstring_vsldt, pos_vsldt-1);
set inputstring_vsldt = substring(inputstring_vsldt, pos_vsldt+1);

set pos_be = INSTR(inputstring_be,',');
set item_be = LEFT(inputstring_be, pos_be-1);
set inputstring_be = substring(inputstring_be, pos_be+1);

set pos_mat = INSTR(inputstring_mat,',');
set item_mat = LEFT(inputstring_mat, pos_mat-1);
set inputstring_mat = substring(inputstring_mat, pos_mat+1);

set pos_grade = INSTR(inputstring_grade,',');
set item_grade = LEFT(inputstring_grade, pos_grade-1);
set inputstring_grade = substring(inputstring_grade, pos_grade+1);

set pos_desc = INSTR(inputstring_desc,',');
set item_desc = LEFT(inputstring_desc, pos_desc-1);
set inputstring_desc = substring(inputstring_desc, pos_desc+1);

set pos_len = INSTR(inputstring_len,',');
set item_len = LEFT(inputstring_len, pos_len-1);
set inputstring_len = substring(inputstring_len, pos_len+1);

set pos_width = INSTR(inputstring_width,',');
set item_width = LEFT(inputstring_width, pos_width-1);
set inputstring_width = substring(inputstring_width, pos_width+1);

set pos_thick = INSTR(inputstring_thick,',');
set item_thick = LEFT(inputstring_thick, pos_thick-1);
set inputstring_thick = substring(inputstring_thick, pos_thick+1);

set pos_wt = INSTR(inputstring_wt,',');
set item_wt = LEFT(inputstring_wt, pos_wt-1);
set inputstring_wt = substring(inputstring_wt, pos_wt+1);

set pos_unt = INSTR(inputstring_unt,',');
set item_unt = LEFT(inputstring_unt, pos_unt-1);
set inputstring_unt = substring(inputstring_unt, pos_unt+1);

set pos_secwt = INSTR(inputstring_secwt,',');
set item_secwt = LEFT(inputstring_secwt, pos_secwt-1);
set inputstring_secwt = substring(inputstring_secwt, pos_secwt+1);

set pos_secunt = INSTR(inputstring_secunt,',');
set item_secunt = LEFT(inputstring_secunt, pos_secunt-1);
set inputstring_secunt = substring(inputstring_secunt, pos_secunt+1);

set pos_qty = INSTR(inputstring_qty,',');
set item_qty = LEFT(inputstring_qty, pos_qty-1);
set inputstring_qty = substring(inputstring_qty, pos_qty+1);

INSERT INTO port_outward  (port_out_shipment_id,vessel_name,vessel_Date,be_no,material_type,grade,description,length,width,
									  thickness,actual_wt,actual_wt_Unit,section_wt,section_wt_unit,quantity,
									  create_ui,update_ui,create_ts,update_ts)

VALUES (lst_rec,item_vslnm,item_vsldt,item_be,item_mat,item_grade,item_desc,item_len,item_width,item_thick,item_wt,item_unt,item_secwt,item_secunt,
		   item_qty,usr,usr,NOW(),NOW());

set tot_ip = tot_ip-1;
end while;
SET message = 'Records added successfully';
END IF;


END$$

DROP PROCEDURE IF EXISTS `sp_insert_warehouse_inward`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_warehouse_inward`(IN `vehicle` VARCHAR(1000), IN `vendor` VARCHAR(1000), IN `vehicle_dt` DATE, IN `be_no` VARCHAR(1000), IN `material` VARCHAR(1000), IN `mill_nm` VARCHAR(1000), IN `make` VARCHAR(1000), IN `grade` VARCHAR(1000), IN `len` VARCHAR(1000), IN `width` VARCHAR(1000), IN `thick` VARCHAR(1000), IN `actual_wt` VARCHAR(1000), IN `actual_unt` VARCHAR(1000), IN `section_wt` VARCHAR(1000), IN `section_unt` VARCHAR(1000), IN `qty` VARCHAR(1000), IN `heat` VARCHAR(1000), IN `plate` VARCHAR(1000), IN `detail_section_wt` VARCHAR(1000), IN `detail_section_unt` VARCHAR(1000), IN `detail_wt` VARCHAR(1000), IN `detail_unt` VARCHAR(1000), IN `detail_qty` VARCHAR(1000), IN `detail_loc` VARCHAR(1000), IN `detail_rmk` VARCHAR(65532), IN `array` VARCHAR(1000), IN `cnt` INT, IN `usr` VARCHAR(1000), OUT `message` VARCHAR(1000))
BEGIN

DECLARE ret INT;
DECLARE cnt_inner_rows INT;
DECLARE rec_exist INT;
DECLARE lst_rec_ship INT;
DECLARE lst_rec_inwrd INT;

DECLARE pos_arry INT;
DECLARE item_arry VARCHAR(1000);
DECLARE inputstring_arry VARCHAR(1000);

DECLARE pos_be INT;
DECLARE item_be VARCHAR(1000);
DECLARE inputstring_be VARCHAR(1000);

DECLARE pos_mat INT;
DECLARE item_mat VARCHAR(1000);
DECLARE inputstring_mat VARCHAR(1000);

DECLARE pos_mill INT;
DECLARE item_mill VARCHAR(1000);
DECLARE inputstring_mill VARCHAR(1000);

DECLARE pos_make INT;
DECLARE item_make VARCHAR(1000);
DECLARE inputstring_make VARCHAR(1000);

DECLARE pos_grade INT;
DECLARE item_grade VARCHAR(1000);
DECLARE inputstring_grade VARCHAR(1000);

DECLARE pos_len INT;
DECLARE item_len VARCHAR(1000);
DECLARE inputstring_len VARCHAR(1000);

DECLARE pos_width INT;
DECLARE item_width VARCHAR(1000);
DECLARE inputstring_width VARCHAR(1000);

DECLARE pos_thick INT;
DECLARE item_thick VARCHAR(1000);
DECLARE inputstring_thick VARCHAR(1000);

DECLARE pos_wt INT;
DECLARE item_wt VARCHAR(1000);
DECLARE inputstring_wt VARCHAR(1000);

DECLARE pos_unt INT;
DECLARE item_unt VARCHAR(1000);
DECLARE inputstring_unt VARCHAR(1000);

DECLARE pos_secwt INT;
DECLARE item_secwt VARCHAR(1000);
DECLARE inputstring_secwt VARCHAR(1000);

DECLARE pos_secunt INT;
DECLARE item_secunt VARCHAR(1000);
DECLARE inputstring_secunt VARCHAR(1000);

DECLARE pos_qty INT;
DECLARE item_qty VARCHAR(1000);
DECLARE inputstring_qty VARCHAR(1000);

/* Declaration of Inward Detail variables */
DECLARE pos_heat INT;
DECLARE item_heat VARCHAR(1000);
DECLARE prep_heat VARCHAR(1000);
DECLARE inputstring_heat VARCHAR(1000);

DECLARE pos_plate INT;
DECLARE item_plate VARCHAR(1000);
DECLARE prep_plate VARCHAR(1000);
DECLARE inputstring_plate VARCHAR(1000);

DECLARE pos_dtsecwt INT;
DECLARE item_dtsecwt VARCHAR(1000);
DECLARE prep_dtsecwt VARCHAR(1000);
DECLARE inputstring_dtsecwt VARCHAR(1000);

DECLARE pos_dtsecunt INT;
DECLARE item_dtsecunt VARCHAR(1000);
DECLARE prep_dtsecunt VARCHAR(1000);
DECLARE inputstring_dtsecunt VARCHAR(1000);

DECLARE pos_dtwt INT;
DECLARE item_dtwt VARCHAR(1000);
DECLARE prep_dtwt VARCHAR(1000);
DECLARE inputstring_dtwt VARCHAR(1000);

DECLARE pos_dtunt INT;

DECLARE item_dtunt VARCHAR(1000);
DECLARE prep_dtunt VARCHAR(1000);
DECLARE inputstring_dtunt VARCHAR(1000);

DECLARE pos_dtqty INT;
DECLARE item_dtqty VARCHAR(1000);
DECLARE prep_dtqty VARCHAR(1000);
DECLARE inputstring_dtqty VARCHAR(1000);

DECLARE pos_dtloc INT;
DECLARE item_dtloc VARCHAR(1000);
DECLARE prep_dtloc VARCHAR(1000);
DECLARE inputstring_dtloc VARCHAR(1000);

DECLARE pos_dtrmk INT;
DECLARE item_dtrmk VARCHAR(65532);
DECLARE prep_dtrmk VARCHAR(65532);
DECLARE inputstring_dtrmk VARCHAR(65532);

SET rec_exist = 0;
SET inputstring_be = be_no;
SET inputstring_mat = material;
SET inputstring_mill = mill_nm;
SET inputstring_make = make;
SET inputstring_grade = grade;
SET inputstring_len = len;
SET inputstring_width = width;
SET inputstring_thick = thick;
SET inputstring_wt = actual_wt;
SET inputstring_unt = actual_unt;
SET inputstring_secwt = section_wt;
SET inputstring_secunt = section_unt;
SET inputstring_qty = qty;


SET inputstring_arry = array;
SET inputstring_heat = heat;
SET inputstring_plate = plate;
SET inputstring_dtsecwt = detail_section_wt;
SET inputstring_dtsecunt = detail_section_unt;
SET inputstring_dtwt = detail_wt;
SET inputstring_dtunt = detail_unt;
SET inputstring_dtqty = detail_qty;
SET inputstring_dtloc = detail_loc;
SET inputstring_dtrmk = detail_rmk;

SET prep_heat ='';
SET prep_plate ='';
SET prep_dtsecwt = '';
SET prep_dtsecunt = '';
SET prep_dtwt = '';
SET prep_dtunt = '';
SET prep_dtqty = '';
SET prep_dtloc = '';
SET prep_dtrmk = '';

if (inputstring_arry IS NOT NULL AND right(inputstring_arry, 1) <> ',') then
SET inputstring_arry = concat(array, ',');
end if;

if (inputstring_be IS NOT NULL AND right(inputstring_be, 1) <> ',') then
SET inputstring_be = concat(be_no, ',');
end if;

if right(inputstring_mat, 1) <> ',' then
SET inputstring_mat = concat(material, ',');
end if;

if right(inputstring_mill, 1) <> ',' then
SET inputstring_mill = concat(mill_nm, ',');
end if;

if right(inputstring_make, 1) <> ',' then
SET inputstring_make = concat(make, ',');
end if;

if right(inputstring_grade, 1) <> ',' then
SET inputstring_grade = concat(grade, ',');
end if;

if (inputstring_len IS NOT NULL AND right(inputstring_len, 1) <> ',') then
SET inputstring_len = concat(len, ',');
end if;

if (inputstring_width IS NOT NULL AND right(inputstring_width, 1) <> ',') then
SET inputstring_width = concat(width, ',');
end if;

if (inputstring_thick IS NOT NULL AND right(inputstring_thick, 1) <> ',') then
SET inputstring_thick = concat(thick, ',');
end if;

if (inputstring_wt IS NOT NULL AND right(inputstring_wt, 1) <> ',') then
SET inputstring_wt = concat(actual_wt, ',');
end if;

if (inputstring_unt IS NOT NULL AND right(inputstring_unt, 1) <> ',') then
SET inputstring_unt = concat(actual_unt, ',');
end if;

if (inputstring_secwt IS NOT NULL AND right(inputstring_secwt, 1) <> ',') then
SET inputstring_secwt = concat(section_wt, ',');
end if;

if (inputstring_secunt IS NOT NULL AND right(inputstring_secunt, 1) <> ',') then
SET inputstring_secunt = concat(section_unt, ',');
end if;

if (inputstring_qty IS NOT NULL AND right(inputstring_qty, 1) <> ',') then
SET inputstring_qty = concat(qty, ',');
end if;

if (inputstring_heat IS NOT NULL AND right(inputstring_heat, 1) <> ',') then
SET inputstring_heat = concat(heat, ',');
end if;

if (inputstring_plate IS NOT NULL AND right(inputstring_plate, 1) <> ',') then
SET inputstring_plate = concat(plate, ',');
end if;

if (inputstring_dtsecwt IS NOT NULL AND right(inputstring_dtsecwt, 1) <> ',') then
SET inputstring_dtsecwt = concat(detail_section_wt, ',');
end if;

if (inputstring_dtsecunt IS NOT NULL AND right(inputstring_dtsecunt, 1) <> ',') then
SET inputstring_dtsecunt = concat(detail_section_unt, ',');
end if;

if (inputstring_dtwt IS NOT NULL AND right(inputstring_dtwt, 1) <> ',') then
SET inputstring_dtwt = concat(detail_wt, ',');
end if;

if (inputstring_dtunt IS NOT NULL AND right(inputstring_dtunt, 1) <> ',') then
SET inputstring_dtunt = concat(detail_unt, ',');
end if;

if (inputstring_dtqty IS NOT NULL AND right(inputstring_dtqty, 1) <> ',') then
SET inputstring_dtqty = concat(detail_qty, ',');
end if;

if (inputstring_dtloc IS NOT NULL AND right(inputstring_dtloc, 1) <> ',') then
SET inputstring_dtloc = concat(detail_loc, ',');
end if;

if (inputstring_dtrmk IS NOT NULL AND right(inputstring_dtrmk, 1) <> ',') then
SET inputstring_dtrmk = concat(detail_rmk, ',');
end if;

SELECT COUNT(*) INTO rec_exist FROM warehouse_inward_shipment ws 
where (vehicle_number = vehicle AND received_date = vehicle_dt) OR (vendor_name = vendor AND received_date = vehicle_dt);

/*IF (rec_exist > 0) THEN
SET message = 'Combination of (Vehicle number or Vendor Name) and Vehicle Date already exists';
ELSE */

INSERT INTO warehouse_inward_shipment (vehicle_number,received_date,vendor_name,create_ui,update_ui,create_ts,update_ts)
VALUES (vehicle,vehicle_dt,vendor,usr,usr,NOW(),NOW());
SELECT LAST_INSERT_ID() INTO lst_rec_ship;

WHILE (cnt > 0) do
set pos_be = INSTR(inputstring_be,',');
set item_be = LEFT(inputstring_be, pos_be-1);
set inputstring_be = substring(inputstring_be, pos_be+1);

set pos_mat = INSTR(inputstring_mat,',');
set item_mat = LEFT(inputstring_mat, pos_mat-1);
set inputstring_mat = substring(inputstring_mat, pos_mat+1);

set pos_mill = INSTR(inputstring_mill,',');
set item_mill = LEFT(inputstring_mill, pos_mill-1);
set inputstring_mill = substring(inputstring_mill, pos_mill+1);

set pos_make = INSTR(inputstring_make,',');
set item_make = LEFT(inputstring_make, pos_make-1);
set inputstring_make = substring(inputstring_make, pos_make+1);

set pos_grade = INSTR(inputstring_grade,',');
set item_grade = LEFT(inputstring_grade, pos_grade-1);
set inputstring_grade = substring(inputstring_grade, pos_grade+1);

set pos_len = INSTR(inputstring_len,',');
set item_len = LEFT(inputstring_len, pos_len-1);
set inputstring_len = substring(inputstring_len, pos_len+1);

set pos_width = INSTR(inputstring_width,',');
set item_width = LEFT(inputstring_width, pos_width-1);
set inputstring_width = substring(inputstring_width, pos_width+1);

set pos_thick = INSTR(inputstring_thick,',');
set item_thick = LEFT(inputstring_thick, pos_thick-1);
set inputstring_thick = substring(inputstring_thick, pos_thick+1);

set pos_wt = INSTR(inputstring_wt,',');
set item_wt = LEFT(inputstring_wt, pos_wt-1);
set inputstring_wt = substring(inputstring_wt, pos_wt+1);

set pos_unt = INSTR(inputstring_unt,',');
set item_unt = LEFT(inputstring_unt, pos_unt-1);
set inputstring_unt = substring(inputstring_unt, pos_unt+1);

set pos_secwt = INSTR(inputstring_secwt,',');
set item_secwt = LEFT(inputstring_secwt, pos_secwt-1);
set inputstring_secwt = substring(inputstring_secwt, pos_secwt+1);

set pos_secunt = INSTR(inputstring_secunt,',');
set item_secunt = LEFT(inputstring_secunt, pos_secunt-1);
set inputstring_secunt = substring(inputstring_secunt, pos_secunt+1);

set pos_qty = INSTR(inputstring_qty,',');
set item_qty = LEFT(inputstring_qty, pos_qty-1);
set inputstring_qty = substring(inputstring_qty, pos_qty+1);

INSERT INTO warehouse_inward   (warehouse_inship_id,be_no,material_type,mill_name,material_make,grade,
											length,width,thickness,section_wt,section_wt_unit,weight,
											weight_unit,quantity,create_ui,update_ui,create_ts,update_ts)
VALUES (lst_rec_ship,item_be,item_mat,item_mill,item_make,item_grade,item_len,item_width,item_thick,item_secwt,item_secunt,
		  item_wt,item_unt,item_qty,usr,usr,NOW(),NOW());
SELECT LAST_INSERT_ID() INTO lst_rec_inwrd;

/* Logic for inserting inward details */
set pos_arry = INSTR(inputstring_arry,',');
set item_arry = LEFT(inputstring_arry, pos_arry-1);
set inputstring_arry = substring(inputstring_arry, pos_arry+1);

set prep_heat = '';
set prep_plate = '';
SET prep_dtsecwt = '';
SET prep_dtsecunt = '';
SET prep_dtwt = '';
SET prep_dtunt = '';
SET prep_dtqty = '';
SET prep_dtloc = '';
SET prep_dtrmk = '';

IF (IFNULL (NULLIF (item_arry ,''),0)) <> 0 THEN
set cnt_inner_rows = item_arry;

while (item_arry > 0) do
set pos_heat = INSTR(inputstring_heat,',');
set item_heat = LEFT(inputstring_heat, pos_heat-1);
set prep_heat = CASE WHEN prep_heat = '' THEN item_heat ELSE CONCAT (prep_heat,',',item_heat) END;
set inputstring_heat = substring(inputstring_heat, pos_heat+1);

set pos_plate = INSTR(inputstring_plate,',');
set item_plate = LEFT(inputstring_plate, pos_plate-1);
set prep_plate = CASE WHEN prep_plate = '' THEN item_plate ELSE CONCAT (prep_plate,',',item_plate) END;
set inputstring_plate = substring(inputstring_plate, pos_plate+1);

set pos_dtsecwt = INSTR(inputstring_dtsecwt,',');
set item_dtsecwt = LEFT(inputstring_dtsecwt, pos_dtsecwt-1);
set prep_dtsecwt = CASE WHEN prep_dtsecwt = '' THEN item_dtsecwt ELSE CONCAT (prep_dtsecwt,',',item_dtsecwt) END;
set inputstring_dtsecwt = substring(inputstring_dtsecwt, pos_dtsecwt+1);

set pos_dtsecunt = INSTR(inputstring_dtsecunt,',');
set item_dtsecunt = LEFT(inputstring_dtsecunt, pos_dtsecunt-1);
set prep_dtsecunt = CASE WHEN prep_dtsecunt = '' THEN item_dtsecunt ELSE CONCAT (prep_dtsecunt,',',item_dtsecunt) END;
set inputstring_dtsecunt = substring(inputstring_dtsecunt, pos_dtsecunt+1);

set pos_dtwt = INSTR(inputstring_dtwt,',');
set item_dtwt = LEFT(inputstring_dtwt, pos_dtwt-1);
set prep_dtwt = CASE WHEN prep_dtwt = '' THEN item_dtwt ELSE CONCAT (prep_dtwt,',',item_dtwt) END;
set inputstring_dtwt = substring(inputstring_dtwt, pos_dtwt+1);

set pos_dtunt = INSTR(inputstring_dtunt,',');
set item_dtunt = LEFT(inputstring_dtunt, pos_dtunt-1);
set prep_dtunt = CASE WHEN prep_dtunt = '' THEN item_dtunt ELSE CONCAT (prep_dtunt,',',item_dtunt) END;
set inputstring_dtunt = substring(inputstring_dtunt, pos_dtunt+1);

set pos_dtqty = INSTR(inputstring_dtqty,',');
set item_dtqty = LEFT(inputstring_dtqty, pos_dtqty-1);
set prep_dtqty = CASE WHEN prep_dtqty = '' THEN item_dtqty ELSE CONCAT (prep_dtqty,',',item_dtqty) END;
set inputstring_dtqty = substring(inputstring_dtqty, pos_dtqty+1);

set pos_dtloc = INSTR(inputstring_dtloc,',');
set item_dtloc = LEFT(inputstring_dtloc, pos_dtloc-1);
set prep_dtloc = CASE WHEN prep_dtloc = '' THEN item_dtloc ELSE CONCAT (prep_dtloc,',',item_dtloc) END;
set inputstring_dtloc = substring(inputstring_dtloc, pos_dtloc+1);

set pos_dtrmk = INSTR(inputstring_dtrmk,',');
set item_dtrmk = LEFT(inputstring_dtrmk, pos_dtrmk-1);
set prep_dtrmk = CASE WHEN prep_dtrmk = '' THEN item_dtrmk ELSE CONCAT (prep_dtrmk,',',item_dtrmk) END;
set inputstring_dtrmk = substring(inputstring_dtrmk, pos_dtrmk+1);

set item_arry = item_arry-1;
end while;
CALL sp_insert_warehouse_inward_details (lst_rec_inwrd,prep_heat,prep_plate,prep_dtsecwt,prep_dtsecunt,prep_dtwt,prep_dtunt,prep_dtqty,
											 prep_dtloc,prep_dtrmk,cnt_inner_rows,usr,ret);
END IF;

set cnt = cnt-1;
end while;
SET message = 'Records Added Successfully';
/*END IF; */

END$$

DROP PROCEDURE IF EXISTS `sp_insert_warehouse_inward_details`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_warehouse_inward_details`(IN `id` INT, IN `heat` VARCHAR(1000), IN `plate` VARCHAR(1000), IN `section_wt` VARCHAR(1000), IN `section_unt` VARCHAR(1000), IN `actual_wt` VARCHAR(1000), IN `actual_unt` VARCHAR(1000), IN `qty` VARCHAR(1000), IN `loc` VARCHAR(1000), IN `rmk` VARCHAR(65532), IN `cnt` INT, IN `usr` VARCHAR(1000), OUT `ret` INT)
BEGIN

DECLARE loc_exists INT;
DECLARE stock_exists INT;

DECLARE pos_heat INT;
DECLARE item_heat VARCHAR(1000);
DECLARE inputstring_heat VARCHAR(1000);

DECLARE pos_plate INT;
DECLARE item_plate VARCHAR(1000);
DECLARE inputstring_plate VARCHAR(1000);

DECLARE pos_secwt INT;
DECLARE item_secwt VARCHAR(1000);
DECLARE inputstring_secwt VARCHAR(1000);

DECLARE pos_secunt INT;
DECLARE item_secunt VARCHAR(1000);
DECLARE inputstring_secunt VARCHAR(1000);

DECLARE pos_wt INT;
DECLARE item_wt VARCHAR(1000);
DECLARE inputstring_wt VARCHAR(1000);

DECLARE pos_unt INT;
DECLARE item_unt VARCHAR(1000);
DECLARE inputstring_unt VARCHAR(1000);

DECLARE pos_qty INT;
DECLARE item_qty VARCHAR(1000);
DECLARE inputstring_qty VARCHAR(1000);

DECLARE pos_loc INT;
DECLARE item_loc VARCHAR(1000);
DECLARE inputstring_loc VARCHAR(1000);

DECLARE pos_rmk INT;
DECLARE item_rmk VARCHAR(65532);
DECLARE inputstring_rmk VARCHAR(65532);

set loc_exists = 0;
set stock_exists = 0;
set inputstring_heat = heat;
set inputstring_plate = plate;
SET inputstring_secwt = section_wt;
SET inputstring_secunt = section_unt;
SET inputstring_wt = actual_wt;
SET inputstring_unt = actual_unt;
SET inputstring_qty = qty;
SET inputstring_loc = loc;
SET inputstring_rmk = rmk;
set ret = 0;

if (inputstring_heat IS NOT NULL AND right(inputstring_heat, 1) <> ',') then
SET inputstring_heat = concat(heat, ',');
end if;

if (inputstring_plate IS NOT NULL AND right(inputstring_plate, 1) <> ',') then
SET inputstring_plate = concat(plate, ',');
end if;

if (inputstring_secwt IS NOT NULL AND right(inputstring_secwt, 1) <> ',') then
SET inputstring_secwt = concat(section_wt, ',');
end if;

if (inputstring_secunt IS NOT NULL AND right(inputstring_secunt, 1) <> ',') then
SET inputstring_secunt = concat(section_unt, ',');
end if;

if (inputstring_wt IS NOT NULL AND right(inputstring_wt, 1) <> ',') then
SET inputstring_wt = concat(actual_wt, ',');
end if;

if (inputstring_unt IS NOT NULL AND right(inputstring_unt, 1) <> ',') then
SET inputstring_unt = concat(actual_unt, ',');
end if;

if (inputstring_qty IS NOT NULL AND right(inputstring_qty, 1) <> ',') then
SET inputstring_qty = concat(qty, ',');
end if;

if (inputstring_loc IS NOT NULL AND right(inputstring_loc, 1) <> ',') then
SET inputstring_loc = concat(loc, ',');
end if;

if (inputstring_rmk IS NOT NULL AND right(inputstring_rmk, 1) <> ',') then
SET inputstring_rmk = concat(rmk, ',');
end if;

IF (id is not null) THEN
while (cnt > 0) do

set pos_heat = INSTR(inputstring_heat,',');
set item_heat = LEFT(inputstring_heat, pos_heat-1);
set inputstring_heat = substring(inputstring_heat, pos_heat+1);

set pos_plate = INSTR(inputstring_plate,',');
set item_plate = LEFT(inputstring_plate, pos_plate-1);
set inputstring_plate = substring(inputstring_plate, pos_plate+1);

set pos_secwt = INSTR(inputstring_secwt,',');
set item_secwt = LEFT(inputstring_secwt, pos_secwt-1);
set inputstring_secwt = substring(inputstring_secwt, pos_secwt+1);

set pos_secunt = INSTR(inputstring_secunt,',');
set item_secunt = LEFT(inputstring_secunt, pos_secunt-1);
set inputstring_secunt = substring(inputstring_secunt, pos_secunt+1);

set pos_wt = INSTR(inputstring_wt,',');
set item_wt = LEFT(inputstring_wt, pos_wt-1);
set inputstring_wt = substring(inputstring_wt, pos_wt+1);

set pos_unt = INSTR(inputstring_unt,',');
set item_unt = LEFT(inputstring_unt, pos_unt-1);
set inputstring_unt = substring(inputstring_unt, pos_unt+1);

set pos_qty = INSTR(inputstring_qty,',');
set item_qty = LEFT(inputstring_qty, pos_qty-1);
set inputstring_qty = substring(inputstring_qty, pos_qty+1);

set pos_loc = INSTR(inputstring_loc,',');
set item_loc = LEFT(inputstring_loc, pos_loc-1);
set inputstring_loc = substring(inputstring_loc, pos_loc+1);

set pos_rmk = INSTR(inputstring_rmk,',');
set item_rmk = LEFT(inputstring_rmk, pos_rmk-1);
set inputstring_rmk = substring(inputstring_rmk, pos_rmk+1);

/* If location is new then insert into location table */
SELECT COUNT(*) INTO loc_exists FROM location loc where location = item_loc;
IF (loc_exists = 0) THEN
INSERT INTO location (location,create_ui,update_ui,create_ts,update_ts)
values (item_loc,usr,usr,NOW(),NOW());
END IF;

INSERT INTO warehouse_inward_details  (warehouse_inward_id,heat_no,plate_no,section_wt,section_wt_unit,
														weight,weight_unit,quantity,remark,location,create_ui,
														update_ui,create_ts,update_ts)
values (id,item_heat,item_plate,item_secwt,item_secunt,item_wt,item_unt,item_qty,item_rmk,item_loc,
		  usr,usr,NOW(),NOW());
		  
/*SELECT COUNT(sb.stock_balance_id) INTO stock_exists
FROM stock_balance sb
INNER JOIN warehouse_inward wi on (sb.mill_name = wi.mill_name AND sb.material_make = wi.material_make and sb.grade = wi.grade and
											  sb.length = wi.length and sb.width = wi.width and sb.thickness = wi.thickness)
INNER JOIN warehouse_inward_details wd on (wi.warehouse_inward_id = wd.warehouse_inward_id and wd.location = sb.location)
where wi.warehouse_inward_id = id and sb.location = item_loc;


IF (stock_exists > 0) THEN

UPDATE 
stock_balance sb
INNER JOIN warehouse_inward wi on (sb.mill_name = wi.mill_name AND sb.material_make = wi.material_make and sb.grade = wi.grade and
											  sb.length = wi.length and sb.width = wi.width and sb.thickness = wi.thickness)
INNER JOIN warehouse_inward_details wd on wi.warehouse_inward_id = wd.warehouse_inward_id
SET sb.quantity = IFNULL (sb.quantity,0) + item_qty
where wd.location = sb.location and wi.warehouse_inward_id = id;

ELSE */

INSERT INTO stock_balance (mill_name,material_make,heat_no,plate_no,material_type,grade,length,width,thickness,quantity,location,create_ui,update_ui,create_ts,update_ts)
SELECT wi.mill_name,wi.material_make,item_heat,item_plate,wi.material_type,wi.grade,wi.length,wi.width,wi.thickness,item_qty,item_loc,usr,usr,NOW(),NOW()
FROM warehouse_inward wi
where wi.warehouse_inward_id = id;

/*END IF;*/

set cnt = cnt-1;
end while;
SET ret = 1;
END IF;

END$$

DROP PROCEDURE IF EXISTS `sp_insert_warehouse_outward`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_warehouse_outward`(IN `act_wt` DECIMAL(10,3), IN `act_ut` TINYTEXT, IN `dispatch_no` INT, IN `vehicle_no` VARCHAR(100), IN `vehicle_dt` DATE, IN `usr` VARCHAR(100), OUT `message` VARCHAR(50))
BEGIN

DECLARE cnt INT;

insert into warehouse_outward  (actual_wt, actual_ut, dispatchNo, vehicle_no, vehicle_dt, create_ui,
                                 update_ui, create_ts, update_ts)
values (act_wt,act_ut,dispatch_no,vehicle_no,vehicle_dt,usr,usr,NOW(),NOW());

SELECT COUNT(*) INTO cnt FROM warehouse_outward_temp  where dispatch_order_id = dispatch_no and Isprocessed = 0;

IF (cnt = 0) THEN

/*delete from warehouse_outward_temp 
where dispatch_order_id = dispatch_no; */

update dispatch_order d
set d.is_pending = 'Completed',
    d.update_ui = usr,
    d.update_ts = NOW()
where d.dispatch_order_id = dispatch_no;

END IF;

set message = 'Record added Successfully';

END$$

DROP PROCEDURE IF EXISTS `sp_insert_warehouse_outward_final`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_warehouse_outward_final`(IN `dispatchNo` INT, IN `out_temp_id` INT, IN `stock_id` INT, IN `location` VARCHAR(1000), IN `heatNo` VARCHAR(1000), IN `plateNo` VARCHAR(1000), IN `millName` VARCHAR(1000), IN `make` VARCHAR(1000), IN `grade` VARCHAR(1000), IN `length` INT, IN `width` INT, IN `thickness` DECIMAL(10,3), IN `secWt` DECIMAL(10,3), IN `secWtUnit` TINYTEXT, IN `qty` INT, IN `exisLength` INT, IN `exisWidth` INT, IN `exisThickness` DECIMAL(10,3), IN `newLength` VARCHAR(1000), IN `newWidth` VARCHAR(1000), IN `newThickness` VARCHAR(1000), IN `usr` VARCHAR(100), OUT `message` VARCHAR(100))
BEGIN

DECLARE lst_rec INT;
DECLARE tot_cnt INT;
DECLARE cnt INT;

DECLARE pos_len INT;
DECLARE item_len VARCHAR(1000);
DECLARE inputstring_len VARCHAR(1000);
DECLARE pos_width INT;
DECLARE item_width VARCHAR(1000);
DECLARE inputstring_width VARCHAR(1000);
DECLARE pos_thick INT;
DECLARE item_thick VARCHAR(1000);
DECLARE inputstring_thick VARCHAR(1000);

SET inputstring_len = newLength;
SET inputstring_width = newWidth;
SET inputstring_thick = newThickness;
SET cnt = 0;
set lst_rec = 0;

if (inputstring_len IS NOT NULL AND right(inputstring_len, 1) <> ',') then
SET inputstring_len = concat(inputstring_len, ',');
end if;
if (inputstring_width IS NOT NULL AND right(inputstring_width, 1) <> ',') then
SET inputstring_width = concat(inputstring_width, ',');
end if;
if (inputstring_thick IS NOT NULL AND right(inputstring_thick, 1) <> ',') then
SET inputstring_thick = concat(inputstring_thick, ',');
end if;

SET tot_cnt = (LENGTH(inputstring_len) - LENGTH(REPLACE(inputstring_len, ',', '')));

/* change is_processed in outward_temp */

update warehouse_outward_temp
set Isprocessed = 1,
    update_ui = usr,
    update_ts = NOW(),
    heat_no = heatNo,
    plate_no = plateNo
where outward_temp_id = out_temp_id;

/* insert params 1-17 in warehouse_outward table

insert into warehouse_outward  (dispatchNo, location, heatNo, plateNo, millName, make, grade, length,
                                 width, thickness, secWt, secWtUnit, qty, exisLength, exisWidth, exisThickness,create_ui,
											update_ui,create_ts,update_ts)
values (dispatchNo, location, heatNo, plateNo, millName, make, grade, length,
        width, thickness, secWt, secWtUnit, qty, exisLength, exisWidth, exisThickness,usr,usr,NOW(),NOW());

SELECT LAST_INSERT_ID() INTO lst_rec; */

update stock_balance s
set s.is_delete = 1,
    s.update_ui = usr,
    s.update_ts = NOW()
where s.stock_balance_id = stock_id;


/* Insert params 18-20 in warehouse_outward_final table */
WHILE (tot_cnt > 0) do

set pos_len = INSTR(inputstring_len,',');
set item_len = LEFT(inputstring_len, pos_len-1);
set inputstring_len = substring(inputstring_len, pos_len+1);

set pos_width = INSTR(inputstring_width,',');
set item_width = LEFT(inputstring_width, pos_width-1);
set inputstring_width = substring(inputstring_width, pos_width+1);

set pos_thick = INSTR(inputstring_thick,',');
set item_thick = LEFT(inputstring_thick, pos_thick-1);
set inputstring_thick = substring(inputstring_thick, pos_thick+1);

IF (item_len > 0 AND item_width > 0 AND item_thick > 0) THEN

INSERT INTO stock_balance  (mill_name,material_make,heat_no,plate_no,material_type,grade,
                             length,width,thickness,quantity,location,create_ui,update_ui,create_ts,
                             update_ts)
select s.mill_name,s.material_make,s.heat_no,s.plate_no,s.material_type,s.grade,item_len,item_width,item_thick,1,s.location,usr,
		 usr,NOW(),NOW()	 
from stock_balance s 
where s.stock_balance_id = stock_id;

SELECT LAST_INSERT_ID() INTO lst_rec;

set cnt = cnt+1;

END IF;

INSERT INTO warehouse_outward_final  (dispatch_no,outward_temp_id,parent_stock_id,new_stock_id, newLength, newWidth, newThickness,
                                       create_ui, update_ui, create_ts, update_ts)
values (dispatchNo,out_temp_id,stock_id,lst_rec,item_len, item_width, item_thick, usr, usr, NOW(), NOW());

set lst_rec = 0;

set tot_cnt = tot_cnt - 1;
end while;

SET message = concat('No. of New Plates inserted = ',cnt);
END$$

DROP PROCEDURE IF EXISTS `sp_location_transfer`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_location_transfer`(IN `stock_id` INT, IN `loc` VARCHAR(50), IN `usr` VARCHAR(100), OUT `message` VARCHAR(100))
BEGIN

DECLARE des VARCHAR(100);
DECLARE oloc VARCHAR(100);
DECLARE loc_exists INT;

select s.location INTO oloc from stock_balance s where s.stock_balance_id = stock_id;

set des = concat('Stock transfered from location ',oloc,' to ',loc);

/* If location is new then insert into location table */
SELECT COUNT(*) INTO loc_exists FROM location l where location = loc;
IF (loc_exists = 0) THEN
INSERT INTO location (location,create_ui,update_ui,create_ts,update_ts)
values (loc,usr,usr,NOW(),NOW());
END IF;

update stock_balance s
set s.location = loc,
    s.update_ui = usr,
    s.update_ts = NOW()
where s.stock_balance_id = stock_id;

insert into activity_log  (username, timestamp, description)
values (usr,NOW(),des);

set message = concat('Success: ',des);

END$$

DROP PROCEDURE IF EXISTS `sp_populate_dispatch_details`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_populate_dispatch_details`(IN `dis_ord_id` INT, OUT `message` VARCHAR(50))
BEGIN

/*select do.dispatch_order_id,do.poNo,do.date,do.vehicle,do.handleby,do.transporter_name,do.toUs,do.toParty,
do.toPay,do.prepaid,do.perMTFix,do.lumsum,do.buyerName,do.consigneeName,do.brokerName,do.brokerage,do.brokerageUnit,
do.paymentTerms,do.loadingCharges,do.loadingChargesUnit,do.cuttingCharges,do.cuttingChargesUnit,do.mtc,
do.inspection,do.inspectionCharges,do.utReport,do.labReport,do.toAcc,do.comments,do.total,do.is_pending*/
select *
from dispatch_order do
where do.dispatch_order_id = dis_ord_id;

/*select dd.dispatch_details_ID,dd.dispatch_order_id,dd.make,dd.materialType,dd.grade,dd.length,dd.width,dd.thickness,dd.qty,
dd.actWt,dd.actWtUnit,dd.rate,dd.taxes,dd.excise */
select dd.*
from dispatch_order do
inner join dispatch_details dd on do.dispatch_order_id = dd.dispatch_order_id
where do.dispatch_order_id = dis_ord_id;

set message = 'Success';
END$$

DROP PROCEDURE IF EXISTS `sp_populate_dispatch_orders`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_populate_dispatch_orders`(IN `frmdt` VARCHAR(50), IN `todt` VARCHAR(50), IN `status` VARCHAR(50), OUT `message` VARCHAR(50))
BEGIN

DECLARE maxdte DATE;
DECLARE mindte DATE;


SELECT MAX(date) INTO maxdte FROM dispatch_order;
SELECT MIN(date) INTO mindte FROM dispatch_order;


select do.*
from dispatch_order do
where (DATE(do.date) between  CASE WHEN IFNULL(NULLIF (frmdt ,''),0) = 0 THEN mindte ELSE DATE(frmdt) END and 
											 CASE WHEN IFNULL(NULLIF (todt ,''),0) = 0 THEN maxdte ELSE DATE(todt) END)
and do.is_pending = CASE WHEN status = 'Completed' THEN 'Completed'
								 WHEN status = 'Pending' THEN 'Pending'
								 WHEN status = 'Processing' THEN 'Processing'
								 WHEN status = 'ALL' THEN do.is_pending END;

set message = 'Success';

END$$

DROP PROCEDURE IF EXISTS `sp_populate_location_details`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_populate_location_details`(IN `Make` VARCHAR(1000), IN `Grade` VARCHAR(1000), IN `MillName` VARCHAR(1000), IN `Length` INT, IN `Width` INT, IN `Thickness` DECIMAL(10,3), OUT `message` VARCHAR(100))
BEGIN

CREATE TEMPORARY TABLE outward
select w.make,w.grade,w.mill_name,w.location , SUM(w.taken_qty) out_qty
from warehouse_outward_temp w
where w.make = Make
and w.grade = Grade
and w.mill_name = MillName and w.length >= Length
and w.width >= Width and w.thickness >= Thickness
GROUP BY w.make,w.grade,w.mill_name,w.location;

select s.material_make, s.grade, s.mill_name, s.location 
		 ,(SUM(s.quantity)-IFNULL (o.out_qty,0)) quantity
from stock_balance s
left join outward o on (o.make = s.material_make and o.grade = s.grade and o.mill_name = s.mill_name and o.location = s.location)
where s.material_make = CASE WHEN Make = '' THEN s.material_make ELSE Make END
and s.grade = CASE WHEN Grade = '' THEN s.grade ELSE Grade END 
and s.mill_name = CASE WHEN MillName = '' THEN s.mill_name ELSE MillName END
and s.length >= Length
and s.width >= Width
and s.thickness >= Thickness
and s.is_reserved = 0
and s.is_delete = 0
GROUP BY s.material_make, s.grade, s.mill_name,s.location ;
/*, s.length, s.width, s.thickness, */

DROP TEMPORARY TABLE outward;
set message = 'Success';

END$$

DROP PROCEDURE IF EXISTS `sp_populate_port_inward`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_populate_port_inward`(IN `vendor` VARCHAR(100), IN `vessel` VARCHAR(100), IN `vessel_dte` DATE, OUT `rec_cnt` INT, OUT `message` VARCHAR(100))
BEGIN

DECLARE rec_exists INT;


SELECT COUNT(*) INTO rec_exists FROM port_inward_shipment pis where (pis.vessel_date = vessel_dte AND pis.vendor_name = vendor) OR 
(pis.vessel_date = vessel_dte AND pis.vessel_name = vessel);

IF (rec_exists > 0) THEN

SELECT COUNT(*) INTO rec_cnt 
FROM port_inward pin
INNER JOIN port_inward_shipment pis ON pin.port_inwd_shipment_id = pis.port_inwd_shipment_id
WHERE (pis.vessel_date = vessel_dte AND pis.vendor_name = vendor) OR 
(pis.vessel_date = vessel_dte AND pis.vessel_name = vessel);

SELECT pin.port_inward_id,pin.be_no,pin.material_type,pin.mill_name,pin.material_make,pin.material_grade,pin.description,pin.be_weight,pin.be_wt_unit
FROM port_inward pin
INNER JOIN port_inward_shipment pis ON pin.port_inwd_shipment_id = pis.port_inwd_shipment_id
WHERE (pis.vessel_date = vessel_dte AND pis.vendor_name = vendor) OR 
(pis.vessel_date = vessel_dte AND pis.vessel_name = vessel);
SET message = 'Success';

ELSE SET message = 'No records found for selected combination';

END IF;
END$$

DROP PROCEDURE IF EXISTS `sp_populate_process_outward`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_populate_process_outward`(IN `Make` VARCHAR(1000), IN `Grade` VARCHAR(1000), IN `MillName` VARCHAR(1000), IN `Length` INT, IN `Width` INT, IN `Thickness` DECIMAL(10,3), IN `location` VARCHAR(1000), IN `heat_no` VARCHAR(1000))
BEGIN

IF (heat_no = '') THEN

select s.heat_no
from stock_balance s
where s.mill_name = MillName and s.material_make = Make and s.grade = Grade
and s.length >= Length and s.width >= Width  and s.thickness >= Thickness
and s.location = location and s.is_delete = 0
and s.is_reserved = 0
ORDER BY 1;

ELSE

select s.plate_no
from stock_balance s
where s.mill_name = MillName and s.material_make = Make and s.grade = Grade
and s.length >= Length and s.width >= Width  and s.thickness >= Thickness and s.heat_no = heat_no
and s.location = location and s.is_delete = 0
and s.is_reserved = 0
ORDER BY 1;

END IF;

END$$

DROP PROCEDURE IF EXISTS `sp_populate_process_outward_stock`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_populate_process_outward_stock`(IN `Make` VARCHAR(1000), IN `Grade` VARCHAR(1000), IN `MillName` VARCHAR(1000), IN `Length` INT, IN `Width` INT, IN `Thickness` DECIMAL(10,3), IN `location` VARCHAR(1000), IN `heat_no` VARCHAR(1000), IN `plate_no` VARCHAR(1000))
BEGIN

select s.*
from stock_balance s
where s.mill_name = MillName and s.material_make = Make and s.grade = Grade
and s.length >= Length and s.width >= Width  and s.thickness >= Thickness
and s.location = location  and s.heat_no = heat_no and s.plate_no = plate_no and
s.is_delete = 0
and s.is_reserved = 0
order by Length ASC, Width ASC, Thickness ASC
LIMIT 1;

END$$

DROP PROCEDURE IF EXISTS `sp_populate_warehouse_inward`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_populate_warehouse_inward`(IN `vehicle` VARCHAR(50), IN `vehicle_dt` DATE, OUT `rec_cnt` INT, OUT `message` VARCHAR(50))
BEGIN

DECLARE rec_exists INT;
SET rec_cnt = 0;

SELECT COUNT(*) INTO rec_exists from port_outward po
INNER JOIN port_outward_shipment ps on po.port_out_shipment_id = ps.port_out_shipment_id
WHERE ps.vehicle_number = vehicle and ps.vehicle_date = vehicle_dt;

IF ( rec_exists > 0) THEN


select count(*) INTO rec_cnt
FROM port_outward po
INNER JOIN port_outward_shipment ps on po.port_out_shipment_id = ps.port_out_shipment_id
LEFT JOIN port_inward pi on pi.be_no = po.be_no
WHERE ps.vehicle_number = vehicle and ps.vehicle_date = vehicle_dt;

select po.be_no, po.material_type,pi.mill_name,pi.material_make,po.grade,po.length,po.width,po.thickness,po.actual_wt,po.actual_wt_Unit,po.section_wt,po.section_wt_unit,po.quantity
FROM port_outward po
INNER JOIN port_outward_shipment ps on po.port_out_shipment_id = ps.port_out_shipment_id
LEFT JOIN port_inward pi on pi.be_no = po.be_no
WHERE ps.vehicle_number = vehicle and ps.vehicle_date = vehicle_dt;

SET message = 'Success';

ELSE SET message = 'No records found for selected combination';

END IF;


END$$

DROP PROCEDURE IF EXISTS `sp_report_port_inward`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_report_port_inward`(IN `frmdt` VARCHAR(50), IN `todt` VARCHAR(50), IN `vessel` VARCHAR(50), IN `type` VARCHAR(50), IN `grade` VARCHAR(100), OUT `message` VARCHAR(100))
BEGIN


DECLARE maxdte DATE;
DECLARE mindte DATE;


SELECT MAX(vessel_date) INTO maxdte FROM port_inward_shipment;
SELECT MIN(vessel_date) INTO mindte FROM port_inward_shipment;

select pi.port_inward_id, pid.port_inward_detail_id,ps.vendor_name,ps.vessel_name,ps.vessel_date
,pi.be_no,pi.mill_name,pi.material_make,pi.material_type,pi.material_grade
,sum(pid.length) as length
,sum(pid.width) as width
,sum(pid.thickness) as thickness
,sum(pid.quantity) as quantity
,sum(pid.be_weight) as be_weight
,pid.be_wt_unit
from port_inward_shipment ps
inner join port_inward pi on pi.port_inwd_shipment_id = ps.port_inwd_shipment_id
inner join port_inward_details pid on pid.port_inward_id = pi.port_inward_id
group by pi.port_inward_id
HAVING (DATE(ps.vessel_date) between  CASE WHEN IFNULL(NULLIF (frmdt ,''),0) = 0 THEN mindte ELSE DATE(frmdt) END and 
											 CASE WHEN IFNULL(NULLIF (todt ,''),0) = 0 THEN maxdte ELSE DATE(todt) END)
and ps.vessel_name = CASE WHEN vessel = 'ALL' THEN ps.vessel_name ELSE vessel END
and pi.material_type = CASE WHEN type = 'ALL' THEN pi.material_type ELSE type END
and pi.material_grade = CASE WHEN grade = 'ALL' THEN pi.material_grade ELSE grade END

;


END$$

DROP PROCEDURE IF EXISTS `sp_report_port_inward_edit`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_report_port_inward_edit`(IN `port_inwd_id` INT, IN `vendor` VARCHAR(100), IN `vessel` VARCHAR(100), IN `vessel_dte` DATE, IN `be_no` VARCHAR(100), IN `mill` VARCHAR(100), IN `make` VARCHAR(100), IN `type` VARCHAR(100), IN `grade` VARCHAR(100), IN `length` DECIMAL(10,3), IN `width` DECIMAL(10,3), IN `thick` DECIMAL(10,3), IN `quantity` INT, IN `be_wt` DECIMAL(10,3), IN `be_ut` VARCHAR(50), IN `usr` VARCHAR(50), OUT `message` VARCHAR(100))
BEGIN

update
port_inward_shipment ps
inner join port_inward pi on pi.port_inwd_shipment_id = ps.port_inwd_shipment_id
inner join port_inward_details pid on pid.port_inward_id = pi.port_inward_id
set ps.vendor_name = vendor
	,ps.vessel_name = vessel
	,ps.vessel_date = vessel_dte
	,ps.update_ui = usr
	,ps.update_ts = NOW()
where pid.port_inward_detail_id = port_inwd_id;

update
port_inward_shipment ps
inner join port_inward pi on pi.port_inwd_shipment_id = ps.port_inwd_shipment_id
inner join port_inward_details pid on pid.port_inward_id = pi.port_inward_id
set pid.length = length
	,pid.width = width
	,pid.thickness = thick
	,pid.quantity = quantity
	,pid.be_weight = be_wt
	,pid.be_wt_unit = be_ut
	,pid.update_ui = usr
	,pid.update_ts = NOW()
where pid.port_inward_detail_id = port_inwd_id;


update

port_inward_shipment ps
inner join port_inward pi on pi.port_inwd_shipment_id = ps.port_inwd_shipment_id
inner join port_inward_details pid on pid.port_inward_id = pi.port_inward_id
set pi.be_no = be_no
   ,pi.mill_name = mill
	,pi.material_make = make
	,pi.material_type = type
	,pi.material_grade = grade
	,pi.update_ui = usr
	,pi.update_ts = NOW()
where pid.port_inward_detail_id = port_inwd_id;

SET message = 'Success';
END$$

DROP PROCEDURE IF EXISTS `sp_report_port_outward`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_report_port_outward`(IN `vessel` VARCHAR(100), IN `dispatch` VARCHAR(100), IN `frmdt` VARCHAR(50), IN `todt` VARCHAR(50), OUT `message` VARCHAR(50))
BEGIN

DECLARE maxdte DATE;
DECLARE mindte DATE;


SELECT MAX(vessel_Date) INTO maxdte FROM port_outward;
SELECT MIN(vessel_Date) INTO mindte FROM port_outward;


select po.port_out_id,CASE WHEN IFNULL (pos.warehouse_name,'') = '' OR pos.warehouse_name = ' ' OR pos.warehouse_name = '' THEN pos.customer_name ELSE pos.warehouse_name END Dispatched_To
, pos.vehicle_number, pos.vehicle_date,
po.vessel_name,po.vessel_Date,po.be_no,po.material_type,po.grade,po.length,po.width,po.thickness,po.quantity,
po.section_wt,po.actual_wt,po.actual_wt_Unit,pos.invoice
from port_outward_shipment pos
inner join port_outward po on pos.port_out_shipment_id = po.port_out_shipment_id
where (DATE(po.vessel_Date) between  CASE WHEN IFNULL(NULLIF (frmdt ,''),0) = 0 THEN mindte ELSE DATE(frmdt) END and 
											 CASE WHEN IFNULL(NULLIF (todt ,''),0) = 0 THEN maxdte ELSE DATE(todt) END)

and po.vessel_name = CASE WHEN vessel = 'ALL' THEN po.vessel_name ELSE vessel END
and (pos.warehouse_name = CASE WHEN dispatch = 'ALL' THEN pos.warehouse_name ELSE dispatch END
	  OR pos.customer_name = CASE WHEN dispatch = 'ALL' THEN pos.customer_name ELSE dispatch END);


SET message = 'Success';

END$$

DROP PROCEDURE IF EXISTS `sp_report_port_outward_edit`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_report_port_outward_edit`(IN `port_out_id` INT, IN `disptach` VARCHAR(100), IN `vehicle` VARCHAR(100), IN `veh_dte` DATE, IN `vessel` VARCHAR(100), IN `vessel_dte` DATE, IN `be_no` VARCHAR(100), IN `material` VARCHAR(100), IN `grade` VARCHAR(100), IN `length` DECIMAL(10,3), IN `width` DECIMAL(10,3), IN `thick` DECIMAL(10,3), IN `quantity` INT, IN `section_wt` DECIMAL(10,3), IN `actual_wt` DECIMAL(10,3), IN `actual_ut` VARCHAR(50), IN `invoice` VARCHAR(100),IN `usr` VARCHAR(50), OUT `message` VARCHAR(100))
BEGIN

update 
port_outward_shipment pos
inner join port_outward po on pos.port_out_shipment_id = po.port_out_shipment_id
set pos.warehouse_name = CASE WHEN IFNULL (pos.warehouse_name,'') = '' OR pos.warehouse_name = ' ' OR pos.warehouse_name = '' THEN '' ELSE disptach END,
	 pos.customer_name = CASE WHEN IFNULL (pos.customer_name,'') = '' OR pos.customer_name = ' ' OR pos.customer_name = '' THEN '' ELSE disptach END,

	 pos.vehicle_number = vehicle,
	 pos.vehicle_date = veh_dte
	,pos.update_ui = usr
	,pos.update_ts = NOW()
    ,pos.invoice = invoice
where po.port_out_id = port_out_id;

update 
port_outward_shipment pos
inner join port_outward po on pos.port_out_shipment_id = po.port_out_shipment_id
set po.vessel_name = vessel,
	 po.vessel_Date = vessel_dte,
	 po.be_no = be_no,
	 po.material_type = material,
	 po.grade = grade,
	 po.length = length,
	 po.width = width,
	 po.thickness = thick,
	 po.quantity = quantity,
	 po.section_wt = section_wt,
	 po.actual_wt = actual_wt,
	 po.actual_wt_Unit = actual_ut
	,po.update_ui = usr
	,po.update_ts = NOW()
where po.port_out_id = port_out_id;

SET message = 'Success';

END$$

DROP PROCEDURE IF EXISTS `sp_report_stock_balance`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_report_stock_balance`(IN `Grade` VARCHAR(1000), IN `Make` VARCHAR(1000), IN `MaterialType` VARCHAR(1000), IN `Location` VARCHAR(1000), IN `MillName` VARCHAR(1000), IN `thickness` DECIMAL(10,3), OUT `message` VARCHAR(100))
BEGIN

DECLARE in_str VARCHAR(1000);
DECLARE pos_in_str INT;

IF ( GRADE = 'ALL')
THEN
select s.*,r.customer,f.received_date as 'Date_Inward',f.section_wt,f.section_wt_unit,md.file_name,md.file_size,md.material_id
from stock_balance s
left join stock_reservation r on s.stock_balance_id = r.stock_id
left join ( select wis.received_date, wi.mill_name,wi.material_make,wi.grade,wid.heat_no,wid.plate_no,wid.material_id,wid.section_wt, wid.section_wt_unit
				from warehouse_inward_shipment wis
				inner join warehouse_inward wi on wi.warehouse_inship_id = wis.warehouse_inship_id
				inner join warehouse_inward_details wid on wid.warehouse_inward_id = wi.warehouse_inward_id) f on 
	(s.mill_name = f.mill_name and s.material_make = f.material_make and s.grade = f.grade and 
	 s.heat_no = f.heat_no and s.plate_no = f.plate_no)
left join material_doc md on f.material_id = md.material_id
where s.material_make = CASE WHEN Make = 'ALL' THEN s.material_make ELSE Make END
and s.material_type = CASE WHEN MaterialType = 'ALL' THEN s.material_type ELSE MaterialType END
and s.Location = CASE WHEN Location = 'ALL' THEN s.location ELSE Location END
and s.mill_name = CASE WHEN MillName = 'ALL' THEN s.mill_name ELSE MillName END
and s.thickness = CASE WHEN thickness = 0 THEN s.thickness ELSE thickness END
and s.is_delete = 0;
/*and s.is_reserved = 0; */
SET message = 'Success';
ELSE

SET in_str = Grade;
SET in_str = REPLACE(in_str,',','\',\'') ;
SET in_str = CONCAT('\'',in_str);
SET in_str = CONCAT(in_str,'\'');


select s.*,r.customer,f.received_date as 'Date_Inward',f.section_wt,f.section_wt_unit,md.file_name,md.file_size,md.material_id
from stock_balance s
left join stock_reservation r on s.stock_balance_id = r.stock_id
left join ( select wis.received_date, wi.mill_name,wi.material_make,wi.grade,wid.heat_no,wid.plate_no,wid.material_id,wid.section_wt, wid.section_wt_unit
				from warehouse_inward_shipment wis
				inner join warehouse_inward wi on wi.warehouse_inship_id = wis.warehouse_inship_id
				inner join warehouse_inward_details wid on wid.warehouse_inward_id = wi.warehouse_inward_id) f on 
	(s.mill_name = f.mill_name and s.material_make = f.material_make and s.grade = f.grade and 
	 s.heat_no = f.heat_no and s.plate_no = f.plate_no)
left join material_doc md on f.material_id = md.material_id
where FIND_IN_SET(s.grade,Grade)
and s.material_make = CASE WHEN Make = 'ALL' THEN s.material_make ELSE Make END
and s.material_type = CASE WHEN MaterialType = 'ALL' THEN s.material_type ELSE MaterialType END
and s.Location = CASE WHEN Location = 'ALL' THEN s.location ELSE Location END
and s.mill_name = CASE WHEN MillName = 'ALL' THEN s.mill_name ELSE MillName END
and s.thickness = CASE WHEN thickness = 0 THEN s.thickness ELSE thickness END
and s.is_delete = 0;
/*and s.is_reserved = 0; */
SET message = 'Success';
END IF;
END$$

DROP PROCEDURE IF EXISTS `sp_report_warehouse_inward`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_report_warehouse_inward`(IN `frmdt` VARCHAR(50), IN `todt` VARCHAR(50), IN `receive` VARCHAR(50), IN `type` VARCHAR(50), OUT `message` VARCHAR(50))
BEGIN


DECLARE maxdte DATE;
DECLARE mindte DATE;


SELECT MAX(received_date) INTO maxdte FROM warehouse_inward_shipment;
SELECT MIN(received_date) INTO mindte FROM warehouse_inward_shipment;

select wid.warehouse_in_detail_id,wis.vendor_name as Received_From,wis.vehicle_number,wis.received_date,wis.vendor_name,
wi.be_no,wi.material_type,wi.mill_name,wi.material_make,wi.grade,wid.heat_no,wid.plate_no,wi.length,wi.width,wi.thickness,
wid.section_wt,wid.weight,wid.weight_unit,wid.quantity,wid.location,md.file_name,md.file_size,md.material_id
from warehouse_inward_shipment wis
inner join warehouse_inward wi on wi.warehouse_inship_id = wis.warehouse_inship_id
inner join warehouse_inward_details wid on wid.warehouse_inward_id = wi.warehouse_inward_id
left join material_doc md on wid.material_id = md.material_id
where (DATE(wis.received_date) between  CASE WHEN IFNULL(NULLIF (frmdt ,''),0) = 0 THEN mindte ELSE DATE(frmdt) END and 
											 CASE WHEN IFNULL(NULLIF (todt ,''),0) = 0 THEN maxdte ELSE DATE(todt) END)
and wis.vendor_name = CASE WHEN receive = 'ALL' THEN wis.vendor_name ELSE receive END 
and wi.material_type = CASE WHEN type = 'ALL' THEN wi.material_type ELSE type END;

SET message = 'Success';
END$$

DROP PROCEDURE IF EXISTS `sp_report_warehouse_inward_edit`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_report_warehouse_inward_edit`(IN `inwd_detail_id` INT, IN `vechile` VARCHAR(100), IN `receive_dt` DATE, IN `vendor` VARCHAR(100), IN `be_no` VARCHAR(100), IN `type` VARCHAR(100), IN `mill` VARCHAR(100), IN `material` VARCHAR(100), IN `grade` VARCHAR(100), IN `heat_no` VARCHAR(100), IN `plate_no` VARCHAR(100), IN `length` DECIMAL(10,3), IN `width` DECIMAL(10,3), IN `thick` DECIMAL(10,3), IN `section_wt` DECIMAL(10,3), IN `wt` DECIMAL(10,3), IN `wt_ut` VARCHAR(50), IN `quantity` INT, IN `usr` VARCHAR(50), OUT `message` VARCHAR(100))
BEGIN

DECLARE dtl_id INT;

SELECT warehouse_inward_id INTO dtl_id
FROM warehouse_inward_details
where wd.warehouse_in_detail_id = inwd_detail_id;

update
warehouse_inward wi
inner join warehouse_inward_details wd on wi.warehouse_inward_id = wd.warehouse_inward_id
inner join stock_balance sb on (sb.mill_name = wi.mill_name and sb.material_make = wi.material_make and
										   sb.heat_no = wd.heat_no and sb.plate_no = wd.plate_no and sb.material_type = wi.material_type
											and sb.grade = wi.grade)
set 	sb.mill_name = mill,
		sb.material_make = material,
		sb.heat_no = heat_no,
		sb.plate_no = plate_no,
		sb.material_type = type,
		sb.grade = grade,
		sb.length = length,
	 	sb.width = width,
	 	sb.thickness = thick,
	 	sb.quantity = quantity,
	 	sb.update_ui = usr,
	 	sb.update_ts = NOW()
where wd.warehouse_in_detail_id = inwd_detail_id;
		

update
warehouse_inward_shipment wis
inner join warehouse_inward wi on wi.warehouse_inship_id = wis.warehouse_inship_id
inner join warehouse_inward_details wid on wid.warehouse_inward_id = wi.warehouse_inward_id

set wis.vehicle_number = vechile,
	 wis.received_date = receive_dt,
	 wis.vendor_name = vendor
	,wis.update_ui = usr
	,wis.update_ts = NOW()
where wid.warehouse_in_detail_id = inwd_detail_id;


update
warehouse_inward_shipment wis
inner join warehouse_inward wi on wi.warehouse_inship_id = wis.warehouse_inship_id
inner join warehouse_inward_details wid on wid.warehouse_inward_id = wi.warehouse_inward_id

set wi.be_no = be_no,
	 wi.material_type = type,
	 wi.mill_name = mill,
	 wi.material_make = material,
	 wi.grade = grade
	,wi.update_ui = usr
	,wi.update_ts = NOW()
where wid.warehouse_in_detail_id = inwd_detail_id;

update
warehouse_inward_shipment wis
inner join warehouse_inward wi on wi.warehouse_inship_id = wis.warehouse_inship_id
inner join warehouse_inward_details wid on wid.warehouse_inward_id = wi.warehouse_inward_id

set wid.heat_no = heat_no,
	 wid.plate_no = plate_no,
	 wi.length = length,
	 wi.width = width,
	 wi.thickness = thick,
	 wid.section_wt = section_wt,
	 wid.weight = wt,
	 wid.weight_unit = wt_ut,
	 wid.quantity = quantity
	,wid.update_ui = usr
	,wid.update_ts = NOW()
where wid.warehouse_in_detail_id = inwd_detail_id;

update
warehouse_inward wi
inner join warehouse_inward_details wid on wid.warehouse_inward_id = wi.warehouse_inward_id
set wid.section_wt = (wi.length*wi.width*wi.thickness*7.85) / (1000000000*wid.quantity)
where wi.warehouse_inward_id = dtl_id;

SET message = 'Success';

END$$

DROP PROCEDURE IF EXISTS `sp_report_warehouse_outward`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_report_warehouse_outward`(IN `dispatch_no` INT, OUT `message` VARCHAR(50))
BEGIN

DECLARE totsec_wt DECIMAL(10,3);
DECLARE totact_wt DECIMAL(10,3);
DECLARE act_ut VARCHAR(10);

CREATE TEMPORARY TABLE section
select (di.length*di.width*di.thickness* 7.85) / (1000000000 * 1) sec_wt
from warehouse_outward_final f
inner join warehouse_outward_temp di on f.outward_temp_id = di.outward_temp_id
where di.dispatch_order_id = dispatch_no;

select SUM(sec_wt) INTO totsec_wt from section;

DROP TEMPORARY TABLE section;


select d.actual_wt INTO totact_wt from warehouse_outward d where d.dispatchNo = dispatch_no;
select distinct d.actual_ut INTO act_ut from warehouse_outward d where d.dispatchNo = dispatch_no;

select distinct do.dispatch_order_id , wo.vehicle_dt as 'Outward_Date',di.mill_name as 'millName',di.grade,
di.thickness, di.width, di.length, SUM(di.reqd_qty) as 'Quantity', di.heat_no, di.plate_no,SUM(CAST(((di.length*di.width*di.thickness* 7.85) / (1000000000 *1)) as decimal(10,3))) as 'section_wt',act_ut as 'sec_ut',
totsec_wt as 'SEC.WT_SUM',totact_wt as 'ACT.WT_SUM',SUM(cast(((totact_wt-totsec_wt)/totsec_wt) * ((di.length*di.width*di.thickness* 7.85) / (1000000000 *1)) + ((di.length*di.width*di.thickness* 7.85) / (1000000000 *1)) as decimal(10,3))) as 'ACT.WT',
act_ut as 'actual_ut',wo.vehicle_no as 'vehicle', do.buyerName,do.brokerName, do.handleby
from dispatch_order do
inner join warehouse_outward_temp di on do.dispatch_order_id = di.dispatch_order_id
inner join warehouse_outward wo on wo.dispatchNo = do.dispatch_order_id
inner join warehouse_outward_final wf on (di.dispatch_order_id = wf.dispatch_no and di.outward_temp_id = wf.outward_temp_id)
where do.dispatch_order_id = dispatch_no
group by do.dispatch_order_id , wo.vehicle_dt,
         di.mill_name,di.grade,di.thickness, di.width, di.length,di.heat_no, di.plate_no,
         act_ut,totsec_wt,totact_wt,act_ut,do.vehicle, do.buyerName,do.brokerName, do.handleby;

set message = 'Success';




END$$

DROP PROCEDURE IF EXISTS `sp_stock_reservation`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_stock_reservation`(IN `stock` INT, IN `customer` VARCHAR(100), IN `usr` VARCHAR(100), OUT `message` VARCHAR(100))
BEGIN

DECLARE des VARCHAR(100);
DECLARE des2 VARCHAR(100);
set des = concat('Stock ',stock,' reserved for ', customer);
set des2 = concat('Stock ',stock,' unreserved');

if ( customer <> '') THEN

insert into stock_reservation  (stock_id, customer, create_ui, update_ui, create_ts, update_ts)
values (stock,customer,usr,usr,NOW(),NOW());

insert into activity_log  (username,timestamp,description)
values (usr,NOW(),des);

update stock_balance s
set s.is_reserved = 1,
    s.update_ui = usr,
    s.update_ts = NOW()
where s.stock_balance_id = stock;

set message = concat('Success: Stock Reserved for ', customer);

END IF;

if ( customer = '') THEN

update stock_balance s
set s.is_reserved = 0,
    s.update_ui = usr,
    s.update_ts = NOW()
where s.stock_balance_id = stock;

delete from stock_reservation
where stock_id = stock;

insert into activity_log  (username,timestamp,description)
values (usr,NOW(),des2);

set message = 'Success: Removed Stock Reservation';

END IF;



END$$

DROP PROCEDURE IF EXISTS `sp_update_dispatch_order_status`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_update_dispatch_order_status`(IN `do_order` INT, OUT `total_row_count` INT, OUT `message` varchar(100))
BEGIN
declare v_dispatch_order_id int;
declare v_item_make int;
declare v_item_material int;
declare v_mill_name varchar(1000);
declare v_thickness int;
declare v_length int;
declare v_width int;
declare v_qty int;
declare v_grade varchar(1000);


DECLARE exit_loop int default 0;
declare cursor_dispatch_details cursor for select 
dispatch_order_id
,make
,millName
,thickness
,length
,width
,qty
,grade from dispatch_details where dispatch_order_id = do_order;

-- set exit_loop flag to true if there are no more rows
DECLARE CONTINUE HANDLER FOR NOT FOUND SET exit_loop = 1;

select * from dispatch_details where dispatch_order_id = do_order;
set message = 'success';
set total_row_count = 80;
OPEN cursor_dispatch_details;
   
 -- start looping
dispatch_order_loop: LOOP
     -- read the name from next row into the variables 
FETCH  cursor_dispatch_details INTO 
v_dispatch_order_id
,v_item_make
,v_mill_name
,v_thickness
,v_length
,v_width
,v_qty
,v_grade
;

SET total_row_count = total_row_count + v_qty;
-- check if the exit_loop flag has been set by mysql, 
-- close the cursor and exit the loop if it has.
IF exit_loop = 1 THEN
CLOSE cursor_dispatch_details;
LEAVE dispatch_order_loop;
END IF;
END LOOP dispatch_order_loop;  
END$$

DROP PROCEDURE IF EXISTS `sp_userlogin`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_userlogin`(IN `iuser` VARCHAR(50), IN `ipswd` VARCHAR(50), OUT `omessage` VARCHAR(50))
BEGIN

DECLARE UserExists INT;
DECLARE PswdValid INT;


SET UserExists = 0;
SET PswdValid = 0;

/*IF NULLIF(iuser,'') IS NULL THEN SET omsg = 'Please enter username';
END IF;

IF ipswd = '' THEN SET omsg = 'Please enter password';
END IF;*/


SELECT COUNT(*) INTO UserExists FROM user us where us.user_name = iuser;
SELECT COUNT(*) INTO PswdValid FROM user us where us.user_name = iuser AND us.password = ipswd AND us.isactive = 1;

IF (UserExists = 0) THEN SET omessage = 'Invalid Username';
ELSEIF (PswdValid = 0) THEN SET omessage = 'Invalid Password';
ELSE SET omessage = 'Valid';
END IF;


SELECT distinct us.user_name, ac.access
FROM user us
JOIN user_access uac on us.user_Id = uac.user_Id
JOIN access ac on ac.access_id = uac.access_Id
WHERE us.user_name = iuser;


END$$

DROP PROCEDURE IF EXISTS `sp_user_manage`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_user_manage`(IN `user` VARCHAR(100), IN `pswd` VARCHAR(50), IN `action` VARCHAR(50), IN `roles` VARCHAR(500), IN `usr` VARCHAR(50), OUT `message` VARCHAR(100))
BEGIN

DECLARE user_exists INT;
DECLARE lst_rec INT;
DECLARE tot_cnt INT;
DECLARE in_str VARCHAR(500);
DECLARE pos_in_str INT;
DECLARE item_in_str VARCHAR(100);
DECLARE exist_usrid VARCHAR(50);


SET user_exists = 0;
SET in_str = roles;

if (action = 'delete') THEN

select count(*) INTO user_exists from user u where u.user_name = user;
if (user_exists = 0) THEN
set message = 'User does not exists';
ELSE

delete ua
from user_access ua 
inner join user u on ua.user_Id = u.user_Id
where u.user_name = user;

delete u
from user u
where u.user_name =user;
set message = 'Success';
END IF;

END IF;

/****** Adding User *******/

if (action = 'add') THEN

select count(*) INTO user_exists from user u where u.user_name = user;

if (user_exists > 0) THEN
set message = 'Username already exists.Please select diff username';

ELSE 

if (in_str IS NOT NULL AND right(in_str, 1) <> ',') then
SET in_str = concat(in_str, ',');
end if;

SET tot_cnt = (LENGTH(in_str) - LENGTH(REPLACE(in_str, ',', '')));

INSERT INTO user (user_name,password,create_ui,update_ui,create_ts,update_ts)
VALUES (user,pswd,usr,usr,NOW(),NOW());

SELECT LAST_INSERT_ID() INTO lst_rec;

WHILE (tot_cnt > 0) do

set pos_in_str = INSTR(in_str,',');
set item_in_str = LEFT(in_str, pos_in_str-1);
set in_str = substring(in_str, pos_in_str+1);

insert into user_access (user_Id,access_Id,create_ui,update_ui,create_ts,update_ts)
select lst_rec,a.access_id,usr,usr,NOW(),NOW()
FROM access a
WHERE a.access = item_in_str;
set tot_cnt = tot_cnt - 1;
end while;
set message = 'Success';
END IF;

END IF;

/**** Modifying USer details ******/

if (action = 'update') THEN

select count(*) INTO user_exists from user u where u.user_name = user;
if (user_exists = 0) THEN
set message = 'User does not exists';
ELSE


set exist_usrid = (select user_id from user u where u.user_name = user);

update user u
set u.password = pswd
where u.user_name = user;

delete ua
from user_access ua
inner join user u on u.user_Id = ua.user_Id
where u.user_name = user;

if (in_str IS NOT NULL AND right(in_str, 1) <> ',') then
SET in_str = concat(in_str, ',');
end if;

SET tot_cnt = (LENGTH(in_str) - LENGTH(REPLACE(in_str, ',', '')));

WHILE (tot_cnt > 0) do

set pos_in_str = INSTR(in_str,',');
set item_in_str = LEFT(in_str, pos_in_str-1);
set in_str = substring(in_str, pos_in_str+1);

insert into user_access (user_Id,access_Id,create_ui,update_ui,create_ts,update_ts)
select exist_usrid,a.access_id,usr,usr,NOW(),NOW()
FROM access a
WHERE a.access = item_in_str;
set tot_cnt = tot_cnt - 1;
end while;
set message = 'Success';

END IF;

END IF;

END$$

DROP PROCEDURE IF EXISTS `sp_warehouse_outward_temp`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_warehouse_outward_temp`(IN `do_order` INT, IN `mill` VARCHAR(1000), IN `make` VARCHAR(1000), IN `grade` VARCHAR(1000), IN `thickness` DECIMAL(10,3), IN `width` INT, IN `length` INT, IN `reqd_qnty` INT, IN `sect_wt` DECIMAL(10,3), IN `sec_ut` VARCHAR(50), IN `location` VARCHAR(1000), IN `avlb_qty` VARCHAR(1000), IN `take_qty` VARCHAR(1000), IN `usr` VARCHAR(100), IN `my_dispatch_details_id` INT, OUT `message` VARCHAR(100))
BEGIN

DECLARE dispatch_details_cnt INT;
DECLARE warehouse_outward_temp_cnt INT;	

DECLARE lst_rec INT;
DECLARE pos_loc INT;
DECLARE tot_cnt INT;
DECLARE qty_cnt INT;
DECLARE item_loc VARCHAR(1000);
DECLARE inputstring_loc VARCHAR(1000);

DECLARE pos_avlqty INT;
DECLARE item_avlqty VARCHAR(1000);
DECLARE inputstring_avlqty VARCHAR(1000);

DECLARE pos_qty INT;
DECLARE item_qty VARCHAR(1000);
DECLARE inputstring_qty VARCHAR(1000);

SET inputstring_loc = location;
SET inputstring_avlqty = avlb_qty;
SET inputstring_qty = take_qty;

if (inputstring_loc IS NOT NULL AND right(inputstring_loc, 1) <> ',') then
SET inputstring_loc = concat(location, ',');
end if;

if (inputstring_avlqty IS NOT NULL AND right(inputstring_avlqty, 1) <> ',') then
SET inputstring_avlqty = concat(avlb_qty, ',');
end if;

if (inputstring_qty IS NOT NULL AND right(inputstring_qty, 1) <> ',') then
SET inputstring_qty = concat(take_qty, ',');
end if;

SET tot_cnt = (LENGTH(inputstring_loc) - LENGTH(REPLACE(inputstring_loc, ',', '')));

/*insert into warehouse_outward_log  (dispatch_order_id,create_ui,update_ui,create_ts,update_ts)
values (do_order,usr,usr,NOW(),NOW()); */

-- delete any existing records
delete from warehouse_outward_temp where 
dispatch_order_id = do_order and dispatch_details_id = my_dispatch_details_id;

WHILE (tot_cnt > 0) do
set pos_loc = INSTR(inputstring_loc,',');
set item_loc = LEFT(inputstring_loc, pos_loc-1);
set inputstring_loc = substring(inputstring_loc, pos_loc+1);

set pos_avlqty = INSTR(inputstring_avlqty,',');
set item_avlqty = LEFT(inputstring_avlqty, pos_avlqty-1);
set inputstring_avlqty = substring(inputstring_avlqty, pos_avlqty+1);

set pos_qty = INSTR(inputstring_qty,',');
set item_qty = LEFT(inputstring_qty, pos_qty-1);
set inputstring_qty = substring(inputstring_qty, pos_qty+1);




IF (item_qty = 1) THEN

insert into warehouse_outward_temp  (dispatch_order_id,mill_name,make,grade,thickness,width,length,
												  reqd_qty,sect_wt,sect_ut,location,avail_qty,taken_qty,create_ui,update_ui,
												  create_ts,update_ts,dispatch_details_id)
values (do_order,mill,make,grade,thickness,width,length,reqd_qnty,sect_wt,sec_ut,item_loc,item_avlqty,item_qty,usr,usr,NOW(),NOW(),my_dispatch_details_id);
END IF;

IF (item_qty > 1) THEN

WHILE (item_qty > 0) do
insert into warehouse_outward_temp  (dispatch_order_id,mill_name,make,grade,thickness,width,length,
												  reqd_qty,sect_wt,sect_ut,location,avail_qty,taken_qty,create_ui,update_ui,create_ts,update_ts, dispatch_details_id)
values (do_order,mill,make,grade,thickness,width,length,reqd_qnty,sect_wt,sec_ut,item_loc,item_avlqty,1,usr,usr,NOW(),NOW(),my_dispatch_details_id);

set item_qty = item_qty-1;
end while;
END IF;

set tot_cnt = tot_cnt - 1;
end while;



SET dispatch_details_cnt = (SELECT COUNT(*) FROM dispatch_details where dispatch_order_id = do_order);

create TEMPORARY TABLE outward
select DISTINCT dispatch_details_id from warehouse_outward_temp where dispatch_order_id = do_order;

SET warehouse_outward_temp_cnt = (SELECT COUNT(*) FROM outward);

IF (warehouse_outward_temp_cnt >= dispatch_details_cnt) THEN
 update dispatch_order d
 set d.is_pending = 'Processing'
 where d.dispatch_order_id = do_order;
END IF;

SET message = 'Records added successfully';
END$$

DROP PROCEDURE IF EXISTS `sp_warehouse_outward_temp_find`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_warehouse_outward_temp_find`(IN `dispatch_order_no` INT, IN `mill_name` VARCHAR(500), IN `make` VARCHAR(500), IN `grade` VARCHAR(500), IN `thickness` DECIMAL(10,3), IN `width` INT, IN `length` INT, IN `dispatch_detail_row_id` INT, IN `location_str` VARCHAR(500))
    SQL SECURITY INVOKER
BEGIN

SELECT *,sum(taken_qty) as location_wise_qty FROM warehouse_outward_temp wot WHERE
wot.dispatch_order_id = dispatch_order_no
AND wot.mill_name like mill_name
AND wot.make like make
AND wot.grade like grade
AND wot.thickness = thickness
and wot.width = width
and wot.length = length
AND wot.dispatch_details_id = dispatch_detail_row_id 
group by wot.location
having wot.location like location_str;

END$$

DROP PROCEDURE IF EXISTS `sp_zz`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_zz`(IN `do_order` INT, OUT `message` VARCHAR(500))
BEGIN

DECLARE dispatch_details_cnt INT;
DECLARE warehouse_outward_temp_cnt INT;	

SET dispatch_details_cnt = (SELECT COUNT(*) FROM dispatch_details where dispatch_order_id = do_order);
SET warehouse_outward_temp_cnt = (SELECT COUNT(*) FROM warehouse_outward_temp where dispatch_order_id = do_order);

IF (warehouse_outward_temp_cnt >= dispatch_details_cnt) THEN
SELECT "OK";
ELSE
SELECT "NOT OK";
END IF;

SET message = 'Records added successfully';

END$$

DROP PROCEDURE IF EXISTS `sp_zz_2`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_zz_2`(
IN `do_order` INT, 
IN `make` varchar(1000), 
IN `grade` varchar(1000), 
IN `millname` varchar(1000), 
IN `length` INT,
IN `width` INT,
IN  `thickness` INT,
OUT return_val int)
BEGIN
  DECLARE b INT;
  declare a,p varchar(100);
  declare v_dispatch_details_id, v_qty, v_total_qty int;
  
  DECLARE cur_1 CURSOR FOR select 
dispatch_details_id
,qty
,make
,millName

from dispatch_details where dispatch_order_id = do_order;

  DECLARE CONTINUE HANDLER FOR NOT FOUND
    SET b = 1;
    set v_total_qty = 0;
  OPEN cur_1;
  REPEAT
    FETCH cur_1 INTO v_dispatch_details_id, v_qty, a, p;
    set return_val = concat(return_val,', ',a);
    select v_dispatch_details_id, v_qty, a,p;
    set v_total_qty = v_total_qty + v_qty; 
    UNTIL b = 1
  END REPEAT;
  CLOSE cur_1;
  
  SET @p0='KOREA'; 
  SET @p1='S355 JR / IS2062 E350BR'; 
  SET @p2='POSCO ASIA'; 
  SET @p3='50'; 
  SET @p4='40'; 
  SET @p5='30'; 
  CALL `sp_populate_location_details`(@p0, @p1, @p2, @p3, @p4, @p5, @p6); 
  select @p6;

  
  set return_val = v_total_qty - v_qty;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `access`
--

DROP TABLE IF EXISTS `access`;
CREATE TABLE IF NOT EXISTS `access` (
  `access_id` int(11) NOT NULL,
  `access` varchar(250) NOT NULL DEFAULT '0',
  `create_ui` varchar(200) DEFAULT NULL,
  `update_ui` varchar(200) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `access`
--

INSERT INTO `access` (`access_id`, `access`, `create_ui`, `update_ui`, `create_ts`, `update_ts`) VALUES
(1, 'Manage_Users', '0', '0', '2015-04-17 01:48:57', '2015-04-17 01:49:05'),
(2, 'Port_Entry', '0', '0', '2015-04-17 01:49:42', '2015-04-17 01:49:44'),
(3, 'Port_Report', '0', '0', '2015-04-17 01:49:58', '2015-04-17 01:49:59'),
(4, 'Warehouse_Entry', '0', '0', '2015-04-17 01:50:20', '2015-04-17 01:50:21'),
(5, 'Warehouse_Report', '0', '0', '2015-04-17 01:50:52', '2015-04-17 01:50:50'),
(6, 'Warehouse_Approver', '0', '0', '2015-04-17 01:51:11', '2015-04-17 01:51:12'),
(7, 'Dispatch_Order', NULL, NULL, '2015-05-13 01:13:41', '2015-05-13 01:13:56');

-- --------------------------------------------------------

--
-- Table structure for table `activity_log`
--

DROP TABLE IF EXISTS `activity_log`;
CREATE TABLE IF NOT EXISTS `activity_log` (
  `log_id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL DEFAULT '0',
  `timestamp` datetime DEFAULT NULL,
  `description` varchar(1000) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `dispatch_details`
--

DROP TABLE IF EXISTS `dispatch_details`;
CREATE TABLE IF NOT EXISTS `dispatch_details` (
  `dispatch_details_ID` int(11) NOT NULL,
  `dispatch_order_id` int(11) NOT NULL DEFAULT '0',
  `make` varchar(50) DEFAULT '0',
  `millName` varchar(50) DEFAULT '0',
  `grade` varchar(50) DEFAULT '0',
  `length` varchar(50) DEFAULT '0',
  `width` varchar(50) DEFAULT '0',
  `thickness` varchar(50) DEFAULT '0',
  `qty` int(11) DEFAULT '0',
  `actWt` decimal(10,3) DEFAULT '0.000',
  `actWtUnit` tinytext,
  `rate` decimal(10,3) DEFAULT '0.000',
  `rateUnit` varchar(50) DEFAULT NULL,
  `taxes` varchar(100) DEFAULT '0.00',
  `excise` varchar(100) DEFAULT '0.00',
  `create_ui` varchar(200) DEFAULT '0.00',
  `update_ui` varchar(200) DEFAULT '0.00',
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `dispatch_order`
--

DROP TABLE IF EXISTS `dispatch_order`;
CREATE TABLE IF NOT EXISTS `dispatch_order` (
  `dispatch_order_id` int(11) NOT NULL,
  `poNo` varchar(50) DEFAULT '0',
  `date` date DEFAULT NULL,
  `vehicle` varchar(50) DEFAULT '0',
  `handleby` varchar(100) DEFAULT '0',
  `transporter_name` varchar(100) DEFAULT '0',
  `toUs` varchar(100) DEFAULT '0',
  `toParty` varchar(100) DEFAULT '0',
  `toPay` varchar(100) DEFAULT '0',
  `prepaid` varchar(100) DEFAULT '0',
  `perMTFix` varchar(100) DEFAULT '0',
  `lumsum` varchar(100) DEFAULT '0',
  `buyerName` varchar(100) DEFAULT '0',
  `consigneeName` varchar(100) DEFAULT '0',
  `brokerName` varchar(100) DEFAULT '0',
  `brokerage` varchar(100) DEFAULT '0',
  `brokerageUnit` varchar(50) DEFAULT '0',
  `paymentTerms` varchar(100) DEFAULT '0',
  `loadingCharges` varchar(100) DEFAULT '0',
  `loadingChargesUnit` varchar(50) DEFAULT '0',
  `cuttingCharges` varchar(100) DEFAULT '0',
  `cuttingChargesUnit` varchar(50) DEFAULT '0',
  `mtc` tinytext,
  `inspection` tinytext,
  `inspectionCharges` varchar(50) DEFAULT NULL,
  `utReport` tinytext,
  `labReport` tinytext,
  `toAcc` varchar(100) DEFAULT '0',
  `comments` varchar(500) DEFAULT '0',
  `total` int(10) DEFAULT NULL,
  `is_pending` varchar(100) DEFAULT 'Pending',
  `create_ui` varchar(200) DEFAULT NULL,
  `update_ui` varchar(200) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
CREATE TABLE IF NOT EXISTS `location` (
  `loc_id` int(11) NOT NULL,
  `location` varchar(200) NOT NULL,
  `create_ui` varchar(200) DEFAULT NULL,
  `update_ui` varchar(200) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `material_doc`
--

DROP TABLE IF EXISTS `material_doc`;
CREATE TABLE IF NOT EXISTS `material_doc` (
  `material_id` int(11) NOT NULL,
  `file_name` varchar(200) NOT NULL DEFAULT '0',
  `file_size` double NOT NULL DEFAULT '0',
  `file` longblob,
  `create_ui` varchar(100) DEFAULT NULL,
  `update_ui` varchar(100) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `port_inward`
--

DROP TABLE IF EXISTS `port_inward`;
CREATE TABLE IF NOT EXISTS `port_inward` (
  `port_inward_id` int(11) NOT NULL,
  `port_inwd_shipment_id` int(11) NOT NULL DEFAULT '0',
  `be_no` varchar(50) DEFAULT '0',
  `material_type` varchar(200) DEFAULT '0',
  `mill_name` varchar(200) DEFAULT '0',
  `material_make` varchar(200) DEFAULT '0',
  `material_grade` varchar(100) DEFAULT '0',
  `description` varchar(500) DEFAULT '0',
  `be_weight` decimal(10,3) DEFAULT '0.000',
  `be_wt_unit` tinytext,
  `create_ui` varchar(100) DEFAULT NULL,
  `update_ui` varchar(100) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=100009 DEFAULT CHARSET=latin1 COMMENT='This table will hold all the first hand information about the Vessel contents when the Vessel is received';

--
-- Dumping data for table `port_inward`
--

INSERT INTO `port_inward` (`port_inward_id`, `port_inwd_shipment_id`, `be_no`, `material_type`, `mill_name`, `material_make`, `material_grade`, `description`, `be_weight`, `be_wt_unit`, `create_ui`, `update_ui`, `create_ts`, `update_ts`) VALUES
(1, 1, '1', 'MATERIALTYPE1', 'MILL1', 'MAKE1', 'GRADE1', 'DESCR1', '1.000', 'TON', 'admin', 'admin', '2016-04-07 09:29:09', '2016-04-07 09:29:09'),
(2, 2, '2', 'MATERIAL2', 'MILL2', 'MAKE2', 'GRADE2', 'DESCR2', '2.000', 'TON', 'admin', 'admin', '2016-04-07 09:29:50', '2016-04-07 09:29:50'),
(3, 3, '3', 'MATERIAL3', 'MILL3', 'MAKE3', 'GRADE3', 'DESCR3', '3.000', 'TON', 'admin', 'admin', '2016-04-07 09:30:37', '2016-04-07 09:30:37'),
(4, 4, '41', '41', 'MILL4111', '51', '41', '41', '41.000', 'TON', 'admin', 'admin', '2016-04-09 09:44:27', '2016-04-11 12:29:15'),
(5, 4, '42', '42', '42', '42', '42', '42', '42.000', 'TON', 'admin', 'admin', '2016-04-09 09:44:27', '2016-04-09 09:44:27'),
(6, 5, '1', 'MATERIALTYPE1', 'MILL1', 'MAKE1', 'GRADE1', 'DESCR1', '1.000', 'TON', 'admin', 'admin', '2016-04-11 12:34:32', '2016-04-11 12:34:32'),
(7, 5, '1', 'MATERIAL2', 'MILL1', 'MAKE1', 'GRADE2', 'DESCR2', '2.000', 'TON', 'admin', 'admin', '2016-04-11 12:34:32', '2016-04-11 12:34:32'),
(100007, 7, 'DUMMY', 'MATERIAL1', 'MILL1', 'MAKE1', 'GRADE1', 'DESCR1', '1.000', 'TON', 'admin', 'admin', '2016-04-12 19:35:42', '2016-04-12 19:35:42'),
(100008, 7, '', 'MATERIAL2', 'MILL2', 'MAKE2', 'GRADE2', 'DESCR2', '2.000', 'TON', 'admin', 'admin', '2016-04-12 19:35:42', '2016-04-12 19:35:42');

-- --------------------------------------------------------

--
-- Table structure for table `port_inward_details`
--

DROP TABLE IF EXISTS `port_inward_details`;
CREATE TABLE IF NOT EXISTS `port_inward_details` (
  `port_inward_detail_id` int(11) NOT NULL,
  `port_inward_id` int(11) NOT NULL DEFAULT '0',
  `length` int(10) DEFAULT '0',
  `width` int(10) DEFAULT '0',
  `thickness` decimal(10,3) DEFAULT '0.000',
  `be_weight` decimal(10,3) DEFAULT '0.000',
  `be_wt_unit` tinytext,
  `quantity` int(11) DEFAULT '0',
  `create_ui` varchar(100) DEFAULT NULL,
  `update_ui` varchar(100) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1 COMMENT='This will store detailed level of Information for the Items received at th Port Inward';

--
-- Dumping data for table `port_inward_details`
--

INSERT INTO `port_inward_details` (`port_inward_detail_id`, `port_inward_id`, `length`, `width`, `thickness`, `be_weight`, `be_wt_unit`, `quantity`, `create_ui`, `update_ui`, `create_ts`, `update_ts`) VALUES
(1, 1, 1, 1, '1.000', '1.000', 'TON', 1, 'admin', 'admin', '2016-04-08 15:14:20', '2016-04-08 15:14:20'),
(2, 1, 2, 2, '2.000', '2.000', 'TON', 2, 'admin', 'admin', '2016-04-08 15:14:20', '2016-04-08 15:14:20'),
(3, 1, 3, 3, '3.000', '3.000', 'TON', 3, 'admin', 'admin', '2016-04-08 15:14:20', '2016-04-08 15:14:20'),
(4, 4, 411, 411, '311.000', '31115.000', 'TON', 411, 'admin', 'admin', '2016-04-09 10:01:11', '2016-04-11 12:29:15'),
(5, 4, 412, 412, '412.000', '412.000', 'TON', 412, 'admin', 'admin', '2016-04-09 10:01:11', '2016-04-09 10:01:11'),
(8, 1, 31, 31, '31.000', '31.000', 'TON', 31, 'admin', 'admin', '2016-04-11 10:27:30', '2016-04-11 10:27:30'),
(9, 1, 32, 32, '32.000', '32.000', 'TON', 32, 'admin', 'admin', '2016-04-11 10:27:30', '2016-04-11 10:27:30'),
(10, 1, 333, 33, '33.000', '33.000', 'TON', 33, 'admin', 'admin', '2016-04-11 10:27:30', '2016-04-11 10:27:30'),
(11, 1, 34, 34, '34.000', '34.000', 'TON', 34, 'admin', 'admin', '2016-04-11 10:27:30', '2016-04-11 10:27:30'),
(12, 1, 35, 35, '35.000', '35.000', 'TON', 35, 'admin', 'admin', '2016-04-11 10:27:30', '2016-04-11 10:27:30'),
(13, 1, 41, 1, '41.000', '41.000', 'TON', 41, 'admin', 'admin', '2016-04-11 10:52:33', '2016-04-11 10:52:33'),
(14, 1, 42, 42, '42.000', '42.000', 'TON', 42, 'admin', 'admin', '2016-04-11 10:52:33', '2016-04-11 10:52:33'),
(15, 1, 43, 43, '434.000', '43.000', 'TON', 43, 'admin', 'admin', '2016-04-11 10:52:33', '2016-04-11 10:52:33'),
(16, 4, 3, 2, '1.000', '5.000', 'TON', 4, 'admin', 'admin', '2016-04-11 00:00:00', '2016-04-11 00:00:00'),
(17, 4, 4, 3, '2.000', '6.000', 'TON', 5, 'admin', 'admin', '2016-04-11 00:00:00', '2016-04-11 00:00:00'),
(31, 5, 7, 9, '5.000', '7.000', 'TON', 7, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00'),
(32, 5, 8, 7, '6.000', '6.000', 'TON', 6, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00'),
(39, 100007, 0, 1, '1.000', '2.000', 'TON', 2, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00'),
(40, 100007, 4, 4, '3.000', '0.000', 'TON', 0, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `port_inward_details_deleted`
--

DROP TABLE IF EXISTS `port_inward_details_deleted`;
CREATE TABLE IF NOT EXISTS `port_inward_details_deleted` (
  `port_inward_detail_id` int(11) NOT NULL,
  `port_inward_id` int(11) NOT NULL DEFAULT '0',
  `length` int(10) DEFAULT '0',
  `width` int(10) DEFAULT '0',
  `thickness` decimal(10,3) DEFAULT '0.000',
  `be_weight` decimal(10,3) DEFAULT '0.000',
  `be_wt_unit` tinytext,
  `quantity` int(11) DEFAULT '0',
  `create_ui` varchar(100) DEFAULT NULL,
  `update_ui` varchar(100) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL,
  `delete_ui` varchar(100) NOT NULL,
  `delete_ts` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='This will store detailed level of Information for the Items received at th Port Inward';

--
-- Dumping data for table `port_inward_details_deleted`
--

INSERT INTO `port_inward_details_deleted` (`port_inward_detail_id`, `port_inward_id`, `length`, `width`, `thickness`, `be_weight`, `be_wt_unit`, `quantity`, `create_ui`, `update_ui`, `create_ts`, `update_ts`, `delete_ui`, `delete_ts`) VALUES
(6, 5, 421, 421, '421.000', '421.000', 'TON', 421, 'admin', 'admin', '2016-04-09 00:00:00', '2016-04-09 00:00:00', 'admin', '2016-04-12 00:00:00'),
(7, 5, 422, 422, '422.000', '422.000', 'TON', 422, 'admin', 'admin', '2016-04-09 00:00:00', '2016-04-09 00:00:00', 'admin', '2016-04-12 00:00:00'),
(18, 5, 1, 1, '1.000', '1.000', 'TON', 1, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(19, 5, 1, 1, '1.000', '1.000', 'TON', 1, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(20, 5, 0, 0, '0.000', '0.000', 'TON', 0, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(21, 5, 5, 5, '5.000', '5.000', 'TON', 5, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(22, 5, 5, 5, '5.000', '5.000', 'TON', 5, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(23, 5, 6, 6, '6.000', '6.000', 'TON', 6, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(24, 5, 5, 5, '5.000', '5.000', 'TON', 5, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(25, 5, 6, 6, '6.000', '6.000', 'TON', 6, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(26, 5, 5, 5, '5.000', '5.000', 'TON', 5, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(27, 5, 6, 7, '6.000', '6.000', 'TON', 6, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(28, 5, 0, 0, '0.000', '0.000', 'TON', 0, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(29, 5, 5, 5, '5.000', '5.000', 'TON', 5, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(30, 5, 8, 7, '6.000', '6.000', 'TON', 6, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(31, 5, 7, 9, '5.000', '7.000', 'TON', 7, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(33, 100007, 0, 1, '1.000', '2.000', 'TON', 2, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(34, 100007, 4, 4, '3.000', '0.000', 'TON', 0, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(35, 100007, 0, 1, '1.000', '2.000', 'TON', 2, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(36, 100007, 4, 4, '3.000', '0.000', 'TON', 0, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(37, 100007, 0, 1, '1.000', '2.000', 'TON', 2, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(38, 100007, 4, 4, '3.000', '0.000', 'TON', 0, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00'),
(39, 100007, 0, 1, '1.000', '2.000', 'TON', 2, 'admin', 'admin', '2016-04-12 00:00:00', '2016-04-12 00:00:00', 'admin', '2016-04-12 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `port_inward_shipment`
--

DROP TABLE IF EXISTS `port_inward_shipment`;
CREATE TABLE IF NOT EXISTS `port_inward_shipment` (
  `port_inwd_shipment_id` int(11) NOT NULL,
  `vendor_name` varchar(200) DEFAULT '0',
  `vessel_name` varchar(200) DEFAULT '0',
  `vessel_date` date DEFAULT NULL,
  `create_ui` varchar(250) DEFAULT NULL,
  `update_ui` varchar(250) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 COMMENT='This Table will store the initial shippment details like Vendor name, Vessel name etc';

--
-- Dumping data for table `port_inward_shipment`
--

INSERT INTO `port_inward_shipment` (`port_inwd_shipment_id`, `vendor_name`, `vessel_name`, `vessel_date`, `create_ui`, `update_ui`, `create_ts`, `update_ts`) VALUES
(1, 'VENDOR1', 'VESSEL1', '2016-01-01', 'admin', 'admin', '2016-04-07 09:29:09', '2016-04-07 09:29:09'),
(2, 'VENDOR2', 'VESSEL2', '2016-01-02', 'admin', 'admin', '2016-04-07 09:29:50', '2016-04-07 09:29:50'),
(3, 'VENDOR3', 'VESSEL3', '2016-01-03', 'admin', 'admin', '2016-04-07 09:30:37', '2016-04-07 09:30:37'),
(4, 'VENDOR4', 'VESSEL4', '2016-01-04', 'admin', 'admin', '2016-04-09 09:44:27', '2016-04-11 12:29:15'),
(5, 'VENDOR1', 'VESSEL1', '2016-01-05', 'admin', 'admin', '2016-04-11 12:34:32', '2016-04-11 12:34:32'),
(6, 'VENDOR1', 'VESSEL1', '2016-01-07', 'admin', 'admin', '2016-04-12 19:15:56', '2016-04-12 19:15:56'),
(7, 'VENDOR1', 'VESSEL1', '2016-01-08', 'admin', 'admin', '2016-04-12 19:35:42', '2016-04-12 19:35:42');

-- --------------------------------------------------------

--
-- Table structure for table `port_outward`
--

DROP TABLE IF EXISTS `port_outward`;
CREATE TABLE IF NOT EXISTS `port_outward` (
  `port_out_id` int(11) NOT NULL,
  `port_out_shipment_id` int(11) NOT NULL DEFAULT '0',
  `vessel_name` varchar(200) DEFAULT '0',
  `vessel_Date` date DEFAULT NULL,
  `be_no` varchar(50) DEFAULT '0',
  `material_type` varchar(100) DEFAULT '0',
  `grade` varchar(100) DEFAULT '0',
  `description` varchar(500) DEFAULT '0',
  `length` int(11) DEFAULT '0',
  `width` int(11) DEFAULT '0',
  `thickness` decimal(10,3) DEFAULT '0.000',
  `actual_wt` decimal(10,3) DEFAULT '0.000',
  `actual_wt_Unit` tinytext,
  `section_wt` decimal(10,3) DEFAULT '0.000',
  `section_wt_unit` tinytext,
  `quantity` int(11) DEFAULT '0',
  `create_ui` varchar(200) DEFAULT NULL,
  `update_ui` varchar(200) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `port_outward`
--

INSERT INTO `port_outward` (`port_out_id`, `port_out_shipment_id`, `vessel_name`, `vessel_Date`, `be_no`, `material_type`, `grade`, `description`, `length`, `width`, `thickness`, `actual_wt`, `actual_wt_Unit`, `section_wt`, `section_wt_unit`, `quantity`, `create_ui`, `update_ui`, `create_ts`, `update_ts`) VALUES
(1, 1, 'VESSEL1', '2016-01-05', '1', 'MATERIAL2', 'GRADE2', 'DESCR1', 6, 8, '7.000', '0.000', '', '0.000', 'TON', 6, 'admin', 'admin', '2016-04-12 17:44:35', '2016-04-12 18:57:35');

-- --------------------------------------------------------

--
-- Table structure for table `port_outward_shipment`
--

DROP TABLE IF EXISTS `port_outward_shipment`;
CREATE TABLE IF NOT EXISTS `port_outward_shipment` (
  `port_out_shipment_id` int(11) NOT NULL,
  `warehouse_name` varchar(200) DEFAULT '0',
  `customer_name` varchar(200) DEFAULT '0',
  `vehicle_number` varchar(50) DEFAULT '0',
  `vehicle_date` date DEFAULT NULL,
  `create_ui` varchar(200) DEFAULT NULL,
  `update_ui` varchar(200) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL,
  `invoice` varchar(100) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='This will store the basic Outward details such as Vendor name, Vechicle no assigned to Vendore etc';

--
-- Dumping data for table `port_outward_shipment`
--

INSERT INTO `port_outward_shipment` (`port_out_shipment_id`, `warehouse_name`, `customer_name`, `vehicle_number`, `vehicle_date`, `create_ui`, `update_ui`, `create_ts`, `update_ts`, `invoice`) VALUES
(1, '', 'CUSTOMER1', 'MH15DC0807', '2016-01-06', 'admin', 'admin', '2016-04-12 17:44:35', '2016-04-12 18:57:35', 'AS##12$%$#@3');

-- --------------------------------------------------------

--
-- Table structure for table `stock_balance`
--

DROP TABLE IF EXISTS `stock_balance`;
CREATE TABLE IF NOT EXISTS `stock_balance` (
  `stock_balance_id` int(11) NOT NULL,
  `mill_name` varchar(100) DEFAULT NULL,
  `material_make` varchar(100) DEFAULT NULL,
  `heat_no` varchar(100) DEFAULT NULL,
  `plate_no` varchar(100) DEFAULT NULL,
  `material_type` varchar(100) DEFAULT NULL,
  `grade` varchar(100) DEFAULT NULL,
  `length` int(11) DEFAULT NULL,
  `width` int(10) DEFAULT NULL,
  `thickness` decimal(10,3) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `location` varchar(50) DEFAULT NULL,
  `is_delete` bit(1) DEFAULT b'0',
  `is_reserved` bit(1) DEFAULT b'0',
  `is_modified` bit(1) DEFAULT b'0',
  `create_ui` varchar(100) DEFAULT NULL,
  `update_ui` varchar(100) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `stock_reservation`
--

DROP TABLE IF EXISTS `stock_reservation`;
CREATE TABLE IF NOT EXISTS `stock_reservation` (
  `resv_id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL DEFAULT '0',
  `customer` varchar(100) NOT NULL DEFAULT '0',
  `create_ui` varchar(100) NOT NULL DEFAULT '0',
  `update_ui` varchar(100) NOT NULL DEFAULT '0',
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_Id` int(11) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `isactive` tinyint(4) NOT NULL DEFAULT '1',
  `create_ui` varchar(200) DEFAULT NULL,
  `update_ui` varchar(200) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_Id`, `user_name`, `password`, `isactive`, `create_ui`, `update_ui`, `create_ts`, `update_ts`) VALUES
(1, 'admin', '123456', 1, NULL, NULL, '2015-04-19 00:30:07', NULL),
(2, 'Surrendra', 'vki@123', 1, 'admin', 'admin', '2015-06-09 02:19:04', '2015-06-09 02:19:04'),
(3, 'hardik', 'VKI@123', 1, 'admin', 'admin', '2015-06-09 02:19:41', '2015-06-09 02:19:41'),
(4, 'vaishali', 'vki@123', 1, 'admin', 'admin', '2015-06-09 05:30:27', '2015-06-09 05:30:27'),
(5, 'varsha', 'varshavki@123', 1, 'admin', 'admin', '2015-06-20 04:26:57', '2015-06-20 04:26:57'),
(6, 'shreya', 'shreya@123', 1, 'admin', 'admin', '2015-06-29 05:17:21', '2015-06-29 05:17:21'),
(7, 'harshal', '123456', 1, 'admin', 'admin', '2015-08-24 01:22:21', '2015-08-24 01:22:21');

-- --------------------------------------------------------

--
-- Table structure for table `user_access`
--

DROP TABLE IF EXISTS `user_access`;
CREATE TABLE IF NOT EXISTS `user_access` (
  `user_access_id` int(11) NOT NULL,
  `user_Id` int(11) NOT NULL DEFAULT '0',
  `access_Id` int(11) NOT NULL DEFAULT '0',
  `create_ui` varchar(200) DEFAULT NULL,
  `update_ui` varchar(200) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_access`
--

INSERT INTO `user_access` (`user_access_id`, `user_Id`, `access_Id`, `create_ui`, `update_ui`, `create_ts`, `update_ts`) VALUES
(3, 1, 1, NULL, NULL, NULL, NULL),
(4, 1, 2, NULL, NULL, NULL, NULL),
(7, 1, 3, NULL, NULL, NULL, NULL),
(8, 1, 8, NULL, NULL, NULL, NULL),
(9, 1, 4, NULL, NULL, NULL, NULL),
(10, 1, 5, NULL, NULL, NULL, NULL),
(11, 1, 6, NULL, NULL, NULL, NULL),
(12, 1, 7, NULL, NULL, NULL, NULL),
(22, 4, 2, 'admin', 'admin', '2015-06-09 05:30:27', '2015-06-09 05:30:27'),
(23, 4, 3, 'admin', 'admin', '2015-06-09 05:30:27', '2015-06-09 05:30:27'),
(24, 2, 2, 'admin', 'admin', '2015-06-09 05:32:00', '2015-06-09 05:32:00'),
(25, 2, 3, 'admin', 'admin', '2015-06-09 05:32:00', '2015-06-09 05:32:00'),
(26, 2, 4, 'admin', 'admin', '2015-06-09 05:32:00', '2015-06-09 05:32:00'),
(27, 2, 5, 'admin', 'admin', '2015-06-09 05:32:00', '2015-06-09 05:32:00'),
(28, 6, 3, 'admin', 'admin', '2015-06-29 05:17:21', '2015-06-29 05:17:21'),
(29, 6, 3, 'admin', 'admin', '2015-06-29 05:17:21', '2015-06-29 05:17:21'),
(30, 6, 4, 'admin', 'admin', '2015-06-29 05:17:21', '2015-06-29 05:17:21'),
(31, 6, 5, 'admin', 'admin', '2015-06-29 05:17:21', '2015-06-29 05:17:21'),
(32, 3, 2, 'admin', 'admin', '2015-07-22 05:56:05', '2015-07-22 05:56:05'),
(33, 3, 3, 'admin', 'admin', '2015-07-22 05:56:05', '2015-07-22 05:56:05'),
(34, 3, 4, 'admin', 'admin', '2015-07-22 05:56:05', '2015-07-22 05:56:05'),
(35, 3, 5, 'admin', 'admin', '2015-07-22 05:56:05', '2015-07-22 05:56:05'),
(36, 1, 9, NULL, NULL, NULL, NULL),
(37, 7, 7, 'admin', 'admin', '2015-08-24 01:22:21', '2015-08-24 01:22:21'),
(38, 7, 8, 'admin', 'admin', '2015-08-24 01:22:21', '2015-08-24 01:22:21'),
(39, 7, 1, 'admin', 'admin', '2015-08-24 01:22:21', '2015-08-24 01:22:21'),
(40, 7, 2, 'admin', 'admin', '2015-08-24 01:22:21', '2015-08-24 01:22:21'),
(41, 7, 3, 'admin', 'admin', '2015-08-24 01:22:21', '2015-08-24 01:22:21'),
(42, 7, 9, 'admin', 'admin', '2015-08-24 01:22:21', '2015-08-24 01:22:21'),
(43, 7, 6, 'admin', 'admin', '2015-08-24 01:22:21', '2015-08-24 01:22:21'),
(44, 7, 4, 'admin', 'admin', '2015-08-24 01:22:21', '2015-08-24 01:22:21'),
(45, 7, 5, 'admin', 'admin', '2015-08-24 01:22:21', '2015-08-24 01:22:21'),
(46, 1, 10, NULL, NULL, NULL, NULL),
(47, 1, 11, NULL, NULL, NULL, NULL),
(48, 1, 12, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `warehouse_inward`
--

DROP TABLE IF EXISTS `warehouse_inward`;
CREATE TABLE IF NOT EXISTS `warehouse_inward` (
  `warehouse_inward_id` int(11) NOT NULL,
  `warehouse_inship_id` int(11) DEFAULT NULL,
  `be_no` varchar(50) DEFAULT NULL,
  `material_type` varchar(50) DEFAULT NULL,
  `mill_name` varchar(100) DEFAULT NULL,
  `material_make` varchar(100) DEFAULT NULL,
  `grade` varchar(100) DEFAULT NULL,
  `length` int(10) DEFAULT NULL,
  `width` int(10) DEFAULT NULL,
  `thickness` decimal(10,3) DEFAULT NULL,
  `section_wt` decimal(10,3) DEFAULT NULL,
  `section_wt_unit` tinytext,
  `weight` decimal(10,3) DEFAULT NULL,
  `weight_unit` tinytext,
  `quantity` int(11) DEFAULT NULL,
  `create_ui` varchar(200) DEFAULT NULL,
  `update_ui` varchar(200) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `warehouse_inward_details`
--

DROP TABLE IF EXISTS `warehouse_inward_details`;
CREATE TABLE IF NOT EXISTS `warehouse_inward_details` (
  `warehouse_in_detail_id` int(11) NOT NULL,
  `warehouse_inward_id` int(11) NOT NULL DEFAULT '0',
  `heat_no` varchar(100) DEFAULT '0',
  `plate_no` varchar(100) DEFAULT '0',
  `section_wt` decimal(10,3) DEFAULT '0.000',
  `section_wt_unit` tinytext,
  `weight` decimal(10,3) DEFAULT '0.000',
  `weight_unit` tinytext,
  `quantity` int(11) DEFAULT '0',
  `remark` varchar(500) DEFAULT '0',
  `location` varchar(50) DEFAULT NULL,
  `material_id` int(11) NOT NULL DEFAULT '0',
  `create_ui` varchar(200) DEFAULT NULL,
  `update_ui` varchar(200) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `warehouse_inward_shipment`
--

DROP TABLE IF EXISTS `warehouse_inward_shipment`;
CREATE TABLE IF NOT EXISTS `warehouse_inward_shipment` (
  `warehouse_inship_id` int(11) NOT NULL,
  `vehicle_number` varchar(50) DEFAULT '0',
  `received_date` date DEFAULT NULL,
  `vendor_name` varchar(200) DEFAULT '0',
  `create_ui` varchar(200) DEFAULT NULL,
  `update_ui` varchar(200) DEFAULT NULL,
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `warehouse_outward`
--

DROP TABLE IF EXISTS `warehouse_outward`;
CREATE TABLE IF NOT EXISTS `warehouse_outward` (
  `warehouse_outward_id` int(11) NOT NULL,
  `actual_wt` decimal(10,3) NOT NULL DEFAULT '0.000',
  `actual_ut` tinytext,
  `dispatchNo` int(11) DEFAULT '0',
  `vehicle_no` varchar(100) DEFAULT '0',
  `vehicle_dt` date DEFAULT NULL,
  `create_ui` varchar(200) DEFAULT '0',
  `update_ui` varchar(200) DEFAULT '0',
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `warehouse_outward_final`
--

DROP TABLE IF EXISTS `warehouse_outward_final`;
CREATE TABLE IF NOT EXISTS `warehouse_outward_final` (
  `warehouse_outward_final_id` int(11) NOT NULL,
  `dispatch_no` int(11) NOT NULL DEFAULT '0',
  `outward_temp_id` int(11) NOT NULL DEFAULT '0',
  `parent_stock_id` int(11) NOT NULL DEFAULT '0',
  `new_stock_id` int(11) NOT NULL DEFAULT '0',
  `newLength` int(11) DEFAULT '0',
  `newWidth` int(11) DEFAULT '0',
  `newThickness` decimal(10,3) DEFAULT '0.000',
  `create_ui` varchar(200) DEFAULT '0',
  `update_ui` varchar(200) DEFAULT '0',
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `warehouse_outward_temp`
--

DROP TABLE IF EXISTS `warehouse_outward_temp`;
CREATE TABLE IF NOT EXISTS `warehouse_outward_temp` (
  `outward_temp_id` int(11) NOT NULL,
  `dispatch_order_id` int(11) DEFAULT '0',
  `mill_name` varchar(500) DEFAULT '0',
  `make` varchar(500) DEFAULT '0',
  `grade` varchar(500) DEFAULT '0',
  `thickness` decimal(10,3) DEFAULT '0.000',
  `heat_no` varchar(100) DEFAULT NULL,
  `plate_no` varchar(100) DEFAULT NULL,
  `width` int(11) DEFAULT '0',
  `length` int(11) DEFAULT '0',
  `reqd_qty` int(11) DEFAULT '0',
  `sect_wt` decimal(10,3) DEFAULT '0.000',
  `sect_ut` varchar(50) DEFAULT '0',
  `location` varchar(100) DEFAULT '0',
  `avail_qty` int(11) DEFAULT '0',
  `taken_qty` int(11) DEFAULT '0',
  `Isprocessed` bit(1) DEFAULT b'0',
  `create_ui` varchar(100) DEFAULT '0',
  `update_ui` varchar(100) DEFAULT '0',
  `create_ts` datetime DEFAULT NULL,
  `update_ts` datetime DEFAULT NULL,
  `dispatch_details_id` int(11) NOT NULL DEFAULT '-1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `access`
--
ALTER TABLE `access`
  ADD PRIMARY KEY (`access_id`);

--
-- Indexes for table `activity_log`
--
ALTER TABLE `activity_log`
  ADD PRIMARY KEY (`log_id`);

--
-- Indexes for table `dispatch_details`
--
ALTER TABLE `dispatch_details`
  ADD PRIMARY KEY (`dispatch_details_ID`),
  ADD KEY `dispatch_order_details` (`dispatch_order_id`);

--
-- Indexes for table `dispatch_order`
--
ALTER TABLE `dispatch_order`
  ADD PRIMARY KEY (`dispatch_order_id`);

--
-- Indexes for table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`loc_id`);

--
-- Indexes for table `material_doc`
--
ALTER TABLE `material_doc`
  ADD PRIMARY KEY (`material_id`);

--
-- Indexes for table `port_inward`
--
ALTER TABLE `port_inward`
  ADD PRIMARY KEY (`port_inward_id`),
  ADD KEY `FK_Port_Inward_Shipment_Port_Inward` (`port_inwd_shipment_id`);

--
-- Indexes for table `port_inward_details`
--
ALTER TABLE `port_inward_details`
  ADD PRIMARY KEY (`port_inward_detail_id`),
  ADD KEY `FK_PortInward_PortInwardDetail` (`port_inward_id`);

--
-- Indexes for table `port_inward_details_deleted`
--
ALTER TABLE `port_inward_details_deleted`
  ADD PRIMARY KEY (`port_inward_detail_id`),
  ADD KEY `FK_PortInward_PortInwardDetail` (`port_inward_id`);

--
-- Indexes for table `port_inward_shipment`
--
ALTER TABLE `port_inward_shipment`
  ADD PRIMARY KEY (`port_inwd_shipment_id`),
  ADD UNIQUE KEY `vendor_name_vessel_name_vessel_date` (`vendor_name`,`vessel_name`,`vessel_date`);

--
-- Indexes for table `port_outward`
--
ALTER TABLE `port_outward`
  ADD PRIMARY KEY (`port_out_id`),
  ADD KEY `FK_PortOutward_PortOutwardShipment` (`port_out_shipment_id`);

--
-- Indexes for table `port_outward_shipment`
--
ALTER TABLE `port_outward_shipment`
  ADD PRIMARY KEY (`port_out_shipment_id`),
  ADD UNIQUE KEY `Vehicle_Number_Vehicle_Date` (`vehicle_number`,`vehicle_date`);

--
-- Indexes for table `stock_balance`
--
ALTER TABLE `stock_balance`
  ADD PRIMARY KEY (`stock_balance_id`);

--
-- Indexes for table `stock_reservation`
--
ALTER TABLE `stock_reservation`
  ADD PRIMARY KEY (`resv_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_Id`);

--
-- Indexes for table `user_access`
--
ALTER TABLE `user_access`
  ADD PRIMARY KEY (`user_access_id`),
  ADD KEY `FKUserAccess_User` (`user_Id`),
  ADD KEY `FKUserAccess_Access` (`access_Id`);

--
-- Indexes for table `warehouse_inward`
--
ALTER TABLE `warehouse_inward`
  ADD PRIMARY KEY (`warehouse_inward_id`),
  ADD KEY `FKWarehouseInwrdShip_WarehouseInward` (`warehouse_inship_id`);

--
-- Indexes for table `warehouse_inward_details`
--
ALTER TABLE `warehouse_inward_details`
  ADD PRIMARY KEY (`warehouse_in_detail_id`),
  ADD KEY `FK_WarehouseInward_WarehouseInwrdDetails` (`warehouse_inward_id`),
  ADD KEY `FK_Material_WarehouseInwardDetail` (`material_id`);

--
-- Indexes for table `warehouse_inward_shipment`
--
ALTER TABLE `warehouse_inward_shipment`
  ADD PRIMARY KEY (`warehouse_inship_id`),
  ADD UNIQUE KEY `vehicle_number_Received_date_Vendor_Name` (`vehicle_number`,`received_date`,`vendor_name`);

--
-- Indexes for table `warehouse_outward`
--
ALTER TABLE `warehouse_outward`
  ADD PRIMARY KEY (`warehouse_outward_id`);

--
-- Indexes for table `warehouse_outward_final`
--
ALTER TABLE `warehouse_outward_final`
  ADD PRIMARY KEY (`warehouse_outward_final_id`);

--
-- Indexes for table `warehouse_outward_temp`
--
ALTER TABLE `warehouse_outward_temp`
  ADD PRIMARY KEY (`outward_temp_id`),
  ADD KEY `outward_temp_dispatch_order` (`dispatch_order_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `access`
--
ALTER TABLE `access`
  MODIFY `access_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `activity_log`
--
ALTER TABLE `activity_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `dispatch_details`
--
ALTER TABLE `dispatch_details`
  MODIFY `dispatch_details_ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `dispatch_order`
--
ALTER TABLE `dispatch_order`
  MODIFY `dispatch_order_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `location`
--
ALTER TABLE `location`
  MODIFY `loc_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `material_doc`
--
ALTER TABLE `material_doc`
  MODIFY `material_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `port_inward`
--
ALTER TABLE `port_inward`
  MODIFY `port_inward_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=100009;
--
-- AUTO_INCREMENT for table `port_inward_details`
--
ALTER TABLE `port_inward_details`
  MODIFY `port_inward_detail_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=41;
--
-- AUTO_INCREMENT for table `port_inward_shipment`
--
ALTER TABLE `port_inward_shipment`
  MODIFY `port_inwd_shipment_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `port_outward`
--
ALTER TABLE `port_outward`
  MODIFY `port_out_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `port_outward_shipment`
--
ALTER TABLE `port_outward_shipment`
  MODIFY `port_out_shipment_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `stock_balance`
--
ALTER TABLE `stock_balance`
  MODIFY `stock_balance_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `stock_reservation`
--
ALTER TABLE `stock_reservation`
  MODIFY `resv_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_Id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `user_access`
--
ALTER TABLE `user_access`
  MODIFY `user_access_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=49;
--
-- AUTO_INCREMENT for table `warehouse_inward`
--
ALTER TABLE `warehouse_inward`
  MODIFY `warehouse_inward_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `warehouse_inward_details`
--
ALTER TABLE `warehouse_inward_details`
  MODIFY `warehouse_in_detail_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `warehouse_inward_shipment`
--
ALTER TABLE `warehouse_inward_shipment`
  MODIFY `warehouse_inship_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `warehouse_outward`
--
ALTER TABLE `warehouse_outward`
  MODIFY `warehouse_outward_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `warehouse_outward_final`
--
ALTER TABLE `warehouse_outward_final`
  MODIFY `warehouse_outward_final_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `warehouse_outward_temp`
--
ALTER TABLE `warehouse_outward_temp`
  MODIFY `outward_temp_id` int(11) NOT NULL AUTO_INCREMENT;SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
