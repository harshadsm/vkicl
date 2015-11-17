package vkicl.form;

import org.apache.struts.action.ActionForm;

@SuppressWarnings("serial")
public class BaseForm extends ActionForm {
	private String genericListener = "";
	private String json = "";

	public void clear() {
		genericListener = "";
		json = "";
	}

	public void setGenericListener(String genericListener) {
		this.genericListener = genericListener;
	}

	public String getGenericListener() {
		return genericListener;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}