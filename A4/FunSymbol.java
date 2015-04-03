import java.util.ArrayList;


public class FunSymbol extends MySymbol {
	private String lable;
	private ArrayList<Param> param_list;
	private int return_type;
	

	
	public String getLable() {
		return lable;
	}



	public ArrayList<Param> getParam_list() {
		return param_list;
	}



	public int getReturn_type() {
		return return_type;
	}



	public FunSymbol(String name, String lable, int type) {
		super(name);
		this.lable = lable;
		this.return_type = type;
		param_list = new ArrayList<Param>();
	}
	
	public void addParam(Param p) {
		param_list.add(p);
	}
	
	public Param getParam(int index) throws TypeException {
		if (index >= param_list.size()) throw new TypeException("Symbol Error: function "+name+" does not have the "+(index+1)+" arguments");
		return param_list.get(index);
	}

}
