package car.tp3.message;

import java.io.Serializable;

public class IncrementMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8090625169729160286L;
	
	protected int nb;
	
	public IncrementMessage(int nb){
		this.nb = nb;
	}
	
	public int getNb() {
		return nb;
	}

}
