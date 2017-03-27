package car.tp3.presentation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import car.tp3.question5.MySystemServer;

public class GraphBuilder {
	private String properties;

	public GraphBuilder(String properties){
		this.properties = properties;
	}
	
	public void build(MySystemServer system) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(properties));
		String line;
		String[] split;
		while(!(line = reader.readLine()).equals("--Actor--") && line != null ){
			split = line.split(" ");
			if(split.length > 2){				
				system = split[0];
				address = split[1].split(":");
			}
		}
		
		reader.close();
	}
}
