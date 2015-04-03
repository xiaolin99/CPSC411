
public class VarSymbol extends MySymbol {
	private int offset;
	private int type;
	private int dimentions;
	private int dimentionsSize[];

	public int getOffset() {
		return offset;
	}

	public int getType() {
		return type;
	}

	public int getDimentions() {
		return dimentions;
	}
	
	public VarSymbol(String name, int offset, int type, int dimentions) {
		super(name);
		this.offset = offset;
		this.type = type;
		this.dimentions = dimentions;
		if (dimentions > 0) dimentionsSize = new int[dimentions];
	}
	
	public void setDimentionsSize(int[] arr) {
		if (dimentions < 1) return;
		for (int i = 0; i < dimentions; i ++) dimentionsSize[i] = arr[i];
	}
	
	public int getDimentionsSize(int index) throws TypeException{
		if (dimentions < 1 || index < 0 || index >= dimentions) throw new TypeException("Symbol Error: Array "+name+" does not have dimension "+index);
		return dimentionsSize[index];
	}
}
