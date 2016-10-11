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
		
		private Integer  length = null;
		private Integer  width = null;
		private Double  thickness = null;
		private Double origin_x;
		private Double origin_y; 
		
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

@Override
	public String toString() {
		return "StockForm [stock_Bal_id=" + stock_Bal_id
				+ ", millName=" + (millName) + ", materialType="
				+ (materialType) + ", make="
				+ (make) + ", grade=" + (grade)
				+ ", length="+ (length) + ", width=" + (width)
				+ ", thickness=" + (thickness)  + "]";
	}

}
