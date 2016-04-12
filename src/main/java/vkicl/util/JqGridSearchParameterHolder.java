package vkicl.util;

import java.util.Arrays;
import java.util.List;

/**
 * {"groupOp":"AND","rules":[{"field":"customerName","op":"cn","data":"wal"}]}
 * 
 * @author Harshad Mahajan
 * 
 */
public class JqGridSearchParameterHolder {
	private String groupOp;
	private List<Rule> rules;

	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	

	@Override
	public String toString() {
		return "JqGridSearchParameterHolder [groupOp=" + groupOp + ", rules="
				+ Arrays.toString(rules.toArray()) + "]";
	}



	public static class Rule {
		private String field;
		private String op;
		private String data;

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getOp() {
			return op;
		}

		public void setOp(String op) {
			this.op = op;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		@Override
		public String toString() {
			return "Rule [field=" + field + ", op=" + op + ", data=" + data
					+ "]";
		}
	

	}
}
