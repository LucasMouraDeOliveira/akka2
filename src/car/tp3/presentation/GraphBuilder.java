package car.tp3.presentation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphBuilder {
	private String properties;

	public GraphBuilder(String properties){
		this.properties = properties;
	}
	
	public void build(Client client) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(properties));
		String line;
		String[] split;
		while(!(line = reader.readLine()).equals("--Actor--") && line != null ){
			split = line.split(" ");
			if(split.length > 3){			
				for(int i = 3;i<split.length;i++)
					client.interprete("create "+split[0]+" "+split[1]+" "+split[2]+" "+split[i]);
			}
		}
		
		while((line = reader.readLine()) != null){
			if(line.split(" ").length == 2){
				client.interprete("link "+line);
			}
		}
		
		reader.close();
	}
}
