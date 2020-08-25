package converter;
public class Rule {
	public String name;
	public String type;
	public boolean required;
	public String op;
	public String fields;

	public Rule() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "Rule [name=" + name + ", type=" + type + ", required=" + required + ", op=" + op + ", fields=" + fields
				+ "]";
	}

}