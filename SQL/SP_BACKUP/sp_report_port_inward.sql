CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_report_port_inward`(IN `frmdt` VARCHAR(50), IN `todt` VARCHAR(50), IN `vessel` VARCHAR(50), IN `type` VARCHAR(50), IN `grade` VARCHAR(100), OUT `message` VARCHAR(100))
BEGIN



DECLARE maxdte DATE;

DECLARE mindte DATE;





SELECT MAX(vessel_date) INTO maxdte FROM port_inward_shipment;

SELECT MIN(vessel_date) INTO mindte FROM port_inward_shipment;



select pid.port_inward_detail_id,ps.vendor_name,ps.vessel_name,ps.vessel_date,pi.be_no,pi.mill_name,pi.material_make,pi.material_type,pi.material_grade,

pid.length,pid.width,pid.thickness,pid.quantity,pid.be_weight,pid.be_wt_unit

from port_inward_shipment ps

inner join port_inward pi on pi.port_inwd_shipment_id = ps.port_inwd_shipment_id

inner join port_inward_details pid on pid.port_inward_id = pi.port_inward_id

where (DATE(ps.vessel_date) between  CASE WHEN IFNULL(NULLIF (frmdt ,''),0) = 0 THEN mindte ELSE DATE(frmdt) END and 

											 CASE WHEN IFNULL(NULLIF (todt ,''),0) = 0 THEN maxdte ELSE DATE(todt) END)

and ps.vessel_name = CASE WHEN vessel = 'ALL' THEN ps.vessel_name ELSE vessel END

and pi.material_type = CASE WHEN type = 'ALL' THEN pi.material_type ELSE type END

and pi.material_grade = CASE WHEN grade = 'ALL' THEN pi.material_grade ELSE grade END;





END