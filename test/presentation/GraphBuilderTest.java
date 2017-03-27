package presentation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import static org.mockito.Mockito.*;

import car.tp3.presentation.Client;
import car.tp3.presentation.GraphBuilder;

public class GraphBuilderTest {
	
		@Test
		public void testGraphBuilder(){
			Client client = new Client();
			client.start();
			Client spy = spy(client);
			try {
				new GraphBuilder("test.properties").build(spy);
			} catch (IOException e) {
			}
			verify(spy).interprete("create system_1 localhost 10001 node1");
			verify(spy).interprete("create system_1 localhost 10001 node2");
			verify(spy).interprete("create system_1 localhost 10001 node3");
			verify(spy).interprete("create system_2 localhost 10002 node4");
			verify(spy).interprete("create system_2 localhost 10002 node5");
			verify(spy).interprete("create system_2 localhost 10002 node6");
			verify(spy).interprete("link node1 node2");
			verify(spy).interprete("link node1 node6");
			verify(spy).interprete("link node2 node3");
			verify(spy).interprete("link node2 node4");
			verify(spy).interprete("link node3 node4");
			verify(spy).interprete("link node4 node6");
			verify(spy).interprete("link node5 node6");
			
		}
}
