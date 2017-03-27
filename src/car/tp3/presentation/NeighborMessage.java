package car.tp3.presentation;

import java.io.Serializable;

public class NeighborMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1159269461905283591L;
	
	protected final String action;
	
	public NeighborMessage(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return action;
	}

}
