package car.tp3.message;

import java.io.Serializable;

/**
 * Message de test qui contient un compteur à incrémenter
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class IncrementMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8090625169729160286L;

	protected int nb;

	/**
	 * Initialise le message avec une valeur numérique.
	 * 
	 * @param nb
	 *            la valeur numérique du compteur
	 */
	public IncrementMessage(int nb) {
		this.nb = nb;
	}

	/**
	 * @return la valeur actuelle du compteur
	 */
	public int getNb() {
		return nb;
	}

}
