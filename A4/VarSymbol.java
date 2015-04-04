
public class VarSymbol extends MySymbol{
	public int offset;
	public int type;
	public int dimentions;
	public int dimentionsSize[];
	
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
	
	public int getDimentionsSize(int index) throws SymbolException{
		if (dimentions < 1 || index < 0 || index >= dimentions) throw new SymbolException("Symbol Error: Array "+name+" does not have dimension "+index);
		return dimentionsSize[index];
	}
	
	public VarSymbol clone() {
		VarSymbol s = new VarSymbol(name, offset, type, dimentions);
		s.setDimentionsSize(dimentionsSize);
		return s;
	}
	
	
}
