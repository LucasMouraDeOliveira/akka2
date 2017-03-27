package car.tp3.message;

import java.io.Serializable;

/**
 * Message envoyé pour indiquer une modification sur les liens de voisinage
 * entre deux acteurs. Le type du message est soit "create" (création de lien)
 * soit "delete" suppression de lien.
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class ExtendedNeighborMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1159269461905283591L;

	protected final String action;

	/***
	 * Crée un nouveau message de modification de lien
	 * 
	 * @param action
	 *            le type de modification (création ou suppression)
	 */
	public ExtendedNeighborMessage(String action) {
		this.action = action;
	}

	/**
	 * @return le type de modification du message
	 */
	public String getAction() {
		return action;
	}

}
