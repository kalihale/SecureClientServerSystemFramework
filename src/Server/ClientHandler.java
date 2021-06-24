package Server;

import Shared.NetworkAccess;
import ObjectsToPass.Queries;
import ObjectsToPass.User;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;


public class ClientHandler extends Thread {
	
	/**
	 * provides a peer-to-peer connection to the client
	 */
	private NetworkAccess networkaccess;
	private ClientHandler clientHandler;
	/**
	 * controls the run thread
	 */
	private boolean go;
	
	/**
	 * the name of this client
	 */
	private String name;
	
	/**
	 * the unique id of this client
	 */
	private int id;
		
	/**
	 * a reference to the server that "has" this ClientHandler
	 */
	private Server server;

	//	<@	ClientHandler has-a UserHandler
	private UserHandler userHandler;
	
	/**
	 * Constructor saves the ID, socket, and reference to the server
	 * then construct the NetworkAccess object
	 * 
	 * @param id: the unique ID for this ClientHandler
	 * @param socket: the peer-to-peer socket for connection to the client
	 * @param server: reference to the server that "has" this ClientHandler
	 */
	public ClientHandler (int id, Socket socket, Server server) 
	{
		this.userHandler = new UserHandler();
		this.server = server;
		this.id = id;
		this.name = Integer.toString(id);
		go = true;
		
		networkaccess = new NetworkAccess(socket);		
	}
	

	public String toString ()
	{
		return name;
	}
	
	/**
	 * getter function for the private name field
	 * 
	 * @return name
	 */
	public String getname ()
	{
		return name;
	}

	public void Stop()
	{
		go = false;
	}

	public int getID()
	{
		return id;
	}
	
	public Server getServer()
	{
		return server;
	}
	
	// -- similar to a main() function in that it is the entry point of
	//    the thread
	public void run () {
		
		// -- server thread runs until the client terminates the connection
		while (go) {
			try {
				Object reply = null;
				Object cmd = networkaccess.readObject();
				if(cmd instanceof User)
				{
					System.out.println("ClientHandler sending object cmd to UserHandler");
					networkaccess.sendObject(this.userHandler.process((User) cmd), false);
				}
				else if(cmd instanceof Queries)
				{
					System.out.println("ClientHandler sending object cmd to CommandProtocol");
					CommandProtocol.processCommand((Queries)cmd, this.networkaccess, this.clientHandler);
				}
				else if(cmd instanceof String)
				{
					System.out.println("cmd is String");
					CommandProtocol.processCommand((String)cmd, networkaccess, this);
				}
				else
				{
					if(cmd == null)
					{
						System.out.println("ClientHandler: cmd is null");
					}
					else
					{
						System.out.println("ClientHandler: Cannot process cmd");
					}
				}
			} 
			catch (IOException | ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
				go = false;
				
			}
			
		}
	}
}
