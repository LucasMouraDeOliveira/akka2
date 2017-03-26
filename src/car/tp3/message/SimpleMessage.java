package car.tp3.message;

import java.io.Serializable;

public class SimpleMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7159429620247375146L;
	
	protected final String message;
	
	public SimpleMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
