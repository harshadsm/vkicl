package vkicl.form;

import java.util.Arrays;

public class ManageForm {

	private String tableName = "";
	private String[] columns = null;
	private String[] data = null;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ManageForm [tableName=" + tableName + ", columns="
				+ Arrays.toString(columns) + ", data=" + Arrays.toString(data)
				+ "]";
	}

}
