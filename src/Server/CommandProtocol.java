package Server;

import Shared.NetworkAccess;
import ObjectsToPass.Queries;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author reinhart
 *
 */
public class CommandProtocol {

	/**
	 * commands and their responses
	 */
	private static HashMap<String, String> commands;	
	static {
	    commands = new HashMap<>();
	    commands.put("disconnect", "");
	    commands.put("hello", "world!");
	}
	
	

	/**
	 * process commands sent to the server
	 * @param cmd: command to be processed
	 * @param na: NetworkAccess object for communication
	 * @param ch: ClientHandler object requesting the processing
	 */
	public static void processCommand(String cmd, NetworkAccess na, ClientHandler ch)
	{
		System.out.println("SERVER receive: " + cmd);
		
		if (cmd.equals("disconnect")) {

			// -- no response to the client is necessary
			na.close();
			ch.getServer().removeID(ch.getID());
			ch.stop();
		}
		else if (cmd.equals("hello")) {
				
			// -- client is expecting a response
			na.sendObject("world!", false);
			
		}
		else if(cmd.equals("clear"))
		{
			System.out.println("clear");
		}
		else {
			try
			{
				na.sendObject(ServerDataBase.getInfo(cmd), false);
			}
			catch(SQLException e)
			{
				na.sendObject("SQLERROR in CommandProtocol from ServerDataBase.getInfo" + e, false);
			}
			
		}		
	}

	/**
	 * @param obj: Object to be processed
	 * @param na: NetworkAccess object for communication
	 * @param ch: ClientHandler object requesting the processing
	 */
	public static void processCommand(Queries obj, NetworkAccess na, ClientHandler ch)
	{

	}
	
	/**
	 * for testing the capabilities of the HashMap
	 * Not used in the actual Client/Server system
	 * 
	 * @param args: command line arguments (unused)
	 */
//	public static void main (String[] args) {
//		System.out.println(commands.get("disconnect"));
//		System.out.println(commands.get("hello"));
//
//		Set<String>keys = commands.keySet();
//		for (String key : keys) {
//			System.out.println(key);
//		}
//
//		System.out.println(commands.get("goodbye"));
//	}
}
