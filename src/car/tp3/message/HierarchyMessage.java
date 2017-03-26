package car.tp3.message;

import java.io.Serializable;

public class HierarchyMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 364349462335903142L;

	protected final String type;
	
	public HierarchyMessage(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

}
