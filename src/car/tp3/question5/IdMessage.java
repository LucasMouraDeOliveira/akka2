package car.tp3.question5;

import java.io.Serializable;

public class IdMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5890999170471477113L;

	protected String id;
	
	protected String message;
	
	public IdMessage(String id, String message) {
		this.id = id;
		this.message = message;
	}
	
	public String getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}

}
