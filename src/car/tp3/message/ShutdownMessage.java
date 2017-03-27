package car.tp3.message;

import java.io.Serializable;

/**
 * Message envoy� � un noeud pour lui demander de se d�sactiver. Avant de se
 * d�sactiver, le noeud supprime tous les liens de voisinages qu'il a avec
 * d'autres acteurs.
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class ShutdownMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 577627740000438055L;

}
