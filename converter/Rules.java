package converter;
import java.util.List;

public class Rules {

	private List<Rule> data;

	public List<Rule> getData() {
		return data;
	}

	public void setData(List<Rule> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Rules [data=" + data + "]";
	}
	
	
}
