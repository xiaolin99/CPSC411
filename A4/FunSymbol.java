import java.util.ArrayList;


public class FunSymbol extends MySymbol {
	public String lable;
	public ArrayList<Param> param_list;
	public int return_type;
	

	public FunSymbol(String name, String lable, int type) {
		super(name);
		this.lable = lable;
		this.return_type = type;
		param_list = new ArrayList<Param>();
	}
	
	public void addParam(Param p) {
		param_list.add(p);
	}
	
	public Param getParam(int index) throws SymbolException {
		if (index >= param_list.size()) throw new SymbolException("Symbol Error: function "+name+" does not have the "+(index+1)+" arguments");
		return param_list.get(index);
	}

}
