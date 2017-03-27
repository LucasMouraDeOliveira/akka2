package car.tp3.message;

import java.io.Serializable;

/**
 * Message transmis à un acteur pour lui signifier qu'il a une relation
 * père/fils avec l'expéditeur du message
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class HierarchyMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 364349462335903142L;

	protected final String type;

	/**
	 * Crée un message de hiérarchie avec un type (père ou fils)
	 * 
	 * @param type
	 *            le type de la relation
	 */
	public HierarchyMessage(String type) {
		this.type = type;
	}

	/**
	 * @return le type de la relation
	 */
	public String getType() {
		return type;
	}

}
