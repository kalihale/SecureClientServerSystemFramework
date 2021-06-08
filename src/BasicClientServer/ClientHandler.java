package BasicClientServer;

import ObjectsToPass.QueriesClass;
import ObjectsToPass.User;

import java.io.IOException;
import java.net.Socket;


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
//					reply = this.userHandler.process((QueriesClass) cmd);
					this.userHandler.process((User) cmd);
				}
				else if(cmd instanceof QueriesClass)
				{
					System.out.println("ClientHandler sending object cmd to CommandProtocol");
					CommandProtocol.processCommand((QueriesClass)cmd, this.networkaccess, this.clientHandler);
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
				// -- always receives a String object with a newline (\n)
				//    on the end due to how BufferedReader readLine() works.
				//    The client adds it to the user's string but the BufferedReader
				//    readLine() call strips it off
//				String reply = "";
//				String cmd = networkaccess.readString();
//
//				if(cmd.charAt(0) == 'u')
//				{
//					reply = this.userHandler.process(cmd);
//					System.out.println("ClientHandler sending: " + reply);
//					networkaccess.sendString(reply, false);
//				}
				
				// -- if it is not the termination message, send it back adding the
				//    required (by readLine) "\n"

				// -- if the disconnect string is received then 
				//    close the socket, remove this thread object from the
				//    server's active client thread list, and terminate the thread
				//    this is the server side "command processor"
				//    you will need to define a communication protocol (language) to be used
				//    between the client and the server
				//    e.g. client sends "LOGIN;<username>;<password>\n"
				//         server parses it to "LOGIN", "<username>", "<password>" and performs login function
				//         server responds with "SUCCESS\n"
				//    this is where all the server side Use Cases will be handled
//				else
//				{
//					CommandProtocol.processCommand(cmd, networkaccess, this);
//				}
			} 
			catch (IOException | ClassNotFoundException e) {
				
				e.printStackTrace();
				go = false;
				
			}
			
		}
	}
}
