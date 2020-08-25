package converter;
public class PropertyDetails {

	private Class type;
	private Object value;

	public PropertyDetails() {
		// TODO Auto-generated constructor stub
	}

	public PropertyDetails(Class type, Object value) {
		this.type = type;
		this.value = value;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
