
public class MySymbol implements Comparable{
	protected String name;
	public int level = 0;
	
	public MySymbol(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof String) return name.compareTo((String)arg0);
		if (arg0 instanceof MySymbol) return name.compareTo(((MySymbol)arg0).getName());
		return -1;
	}
	
	
}
