package vkicl.form;

import java.util.Arrays;

@SuppressWarnings("serial")
public class StockForm extends BaseForm {

	// Inward
		private Integer stock_Bal_id = null;
		
		private String millName = null;
		private String materialType = null;
		private String  make = null;
		private String  grade = null;
		private String  location = null;
		private String  heat_no = null;
		private String  plate_no = null;

		private String  heat_no_2 = null;
		private String  plate_no_2 = null;
		
		public String getHeat_no() {
			return heat_no;
		}

		public void setHeat_no(String heat_no) {
			this.heat_no = heat_no;
		}

		public String getPlate_no() {
			return plate_no;
		}

		public void setPlate_no(String plate_no) {
			this.plate_no = plate_no;
		}

		
		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		private Integer  length = null;
		private Integer  width = null;
		private Double  thickness = null;
		private Double origin_x;
		private Double origin_y; 
		
		private Integer  quantity = null;
		
		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public StockForm() {
		}

	public Integer getStock_Bal_id() {
		return stock_Bal_id;
	}

    public void setStock_Bal_id(Integer stock_Bal_id) {
		this.stock_Bal_id = stock_Bal_id;
	}

    public String  getMillName() {
		return millName;
	}

	public void setMillName(String  millName) {
		this.millName = millName;
	}

    public String  getMaterialType() {
		return materialType;
	}

    public void setMaterialType(String  materialType) {
		this.materialType = materialType;
	}

    public String  getMake() {
		return make;
	}

    public void setMake(String  make) {
		this.make = make;
	}

    public String  getGrade() {
		return grade;
	}

    public void setGrade(String  grade) {
		this.grade = grade;
	}

    public Integer  getLength() {
		return length;
	}

    public void setLength(Integer length) {
		this.length = length;
	}

    public Integer  getWidth() {
		return width;
	}

    public void setWidth(Integer  width) {
		this.width = width;
	}
   
	public Double  getThickness() {
		return thickness;
	}
	
    public void setThickness(Double  thickness) {
		this.thickness = thickness;
	}
    
    

public Double getOrigin_x() {
		return origin_x;
	}

	public void setOrigin_x(Double origin_x) {
		this.origin_x = origin_x;
	}

	public Double getOrigin_y() {
		return origin_y;
	}

	public void setOrigin_y(Double origin_y) {
		this.origin_y = origin_y;
	}

	public String getHeat_no_2() {
		return heat_no_2;
	}

	public void setHeat_no_2(String heat_no_2) {
		this.heat_no_2 = heat_no_2;
	}

	public String getPlate_no_2() {
		return plate_no_2;
	}

	public void setPlate_no_2(String plate_no_2) {
		this.plate_no_2 = plate_no_2;
	}

@Override
	public String toString() {
		return "StockForm [stock_Bal_id=" + stock_Bal_id
				+ ", millName=" + (millName) + ", materialType="
				+ (materialType) + ", make="
				+ (make) + ", grade=" + (grade)
				+ ", length="+ (length) + ", width=" + (width)
				+ ",location="+(location)
				+ ", thickness=" + (thickness)  + "]";
	}

}
