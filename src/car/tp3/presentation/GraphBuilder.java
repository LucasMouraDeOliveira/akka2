package car.tp3.presentation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * classe qui genere le graph grâce à un fichier.
 * @author bricout
 */
public class GraphBuilder {
	private String properties;

	/**
	 * Initialise le fichier du graph.
	 * @param properties Nom du fichier de properties du graph.
	 */
	public GraphBuilder(String properties) {
		this.properties = properties;
	}

	/**
	 * Envoie les messages au client pour crée les noeuds et les liens.
	 * @param client Le client.
	 * @throws IOException Il peut y avoir un IOException si il y a un problème avec le fichier.
	 */
	public void build(Client client) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(properties));
		String line;
		String[] split;
		while (!(line = reader.readLine()).equals("--Actor--") && line != null) {
			split = line.split(" ");
			if (split.length > 3) {
				for (int i = 3; i < split.length; i++)
					client.interprete("create " + split[0] + " " + split[1] + " " + split[2] + " " + split[i]);
			}
		}

		while ((line = reader.readLine()) != null) {
			if (line.split(" ").length == 2) {
				client.interprete("link " + line);
			}
		}

		reader.close();
	}
}
