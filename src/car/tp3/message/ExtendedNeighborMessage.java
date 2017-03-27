package car.tp3.message;

import java.io.Serializable;

/**
 * Message envoyé pour indiquer une modification sur les liens de voisinage entre deux 
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class ExtendedNeighborMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1159269461905283591L;
	
	protected final String action;
	
	public ExtendedNeighborMessage(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return action;
	}

}
