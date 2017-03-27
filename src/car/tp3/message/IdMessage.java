package car.tp3.message;

import java.io.Serializable;

/**
 * Message possédant un identifiant sous la forme d'une chaine de caractères.
 * Le message doit être interprêté par un acteur seulement s'il n'est pas référencé dans la "base de données" de l'acteur
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class IdMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5890999170471477113L;

	protected String id;
	
	protected String message;
	
	/**
	 * Crée un message avec un identifiant unique et un contenu
	 * 
	 * @param id l'identifiant du message
	 * @param message le contenu du message
	 */
	public IdMessage(String id, String message) {
		this.id = id;
		this.message = message;
	}
	
	/**
	 * @return l'id du message
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return le contenu du message
	 */
	public String getMessage() {
		return message;
	}

}
