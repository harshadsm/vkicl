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

END