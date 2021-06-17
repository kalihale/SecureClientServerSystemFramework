package BasicClientServer;


import ObjectsToPass.User;

public class Client {
	
	/*
	 
	 For use on a single computer with loopback, use 127.0.0.1 or localhost
	 
	 Windows From ipconfig:
	 
	 Wireless LAN adapter Wireless Network Connection:

     Connection-specific DNS Suffix  . : clunet.edu
     Link-local IPv6 Address . . . . . : fe80::1083:3e22:f5a1:a3ec%11
     IPv4 Address. . . . . . . . . . . : 199.107.222.115 <======= This address works
     Subnet Mask . . . . . . . . . . . : 255.255.240.0
     Default Gateway . . . . . . . . . : 199.107.210.2
     
     MacOS From System preferences     
     Network category, read the IP address directly
     
	 */
	
	/**
	 * provides a peer-to-peer connection to the server
	 */
	private NetworkAccess networkaccess;
  	
	/**
	 * Creates a peer-to-peer connection to the server
	 * 
	 * @param ip: the IP address of the server
	 * @param port: the port on which the server is listening
	 */
	public Client (String ip, int port)
	{
		networkaccess = new NetworkAccess(ip, port);
	}
	
	
	/**
	 * Disconnects the client from the server
	 */
	public void disconnect ()
	{
    	String text = "disconnect";
		networkaccess.sendObject(text,  false);
		networkaccess.close();		
	}

	public String sendString(String info)
	{
		String reply = (String)networkaccess.sendObject(info, true);
		System.out.println("Client recieves: " + reply);
		return reply;
	}
	public Object sendObject(Object obj)
	{
		Object reply = networkaccess.sendObject(obj, true);
		return reply;
	}
	
	
	/**
	 * start up the client
	 * 
	 * @param args: command line arguments (unused)
	 */
//	public static void main(String[] args) {
//
//		String host = "localhost";
//		int port = 8000;
//		// -- instantiate a Client object
//		//    the constructor will attempt to connect to the server
//		Client client = new Client(host, port);
//
//		// -- send message to server and receive reply.
//		String commandString;
//		String replyString;
//
//		for (int i = 0; i < 10; ++i) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			};
//
//			// -- send a String to the server and wait for the response
//			commandString = "hello";
//			System.out.println("CLIENT send:  " + commandString);
//			replyString = (String)client.networkaccess.sendObject(commandString, true);
//			System.out.println("CLIENT receive: " + replyString);
//
//		}
//
//		// -- send an unrecognized command String to the server and wait for the response
//		commandString = "huh?";
//		System.out.println("CLIENT send:  " + commandString);
//		replyString = (String)client.networkaccess.sendObject(commandString, true);
//		System.out.println("CLIENT receive: " + replyString);
//
//		// -- disconnect from the server
//		client.disconnect();
//	}
//	public static void main(String[] args)
//	{
//		String host = "localhost";
//		int port = 8000;
//		System.out.println("Creating new client");
//		Client client = new Client(host, port);
//
//		System.out.println("sending anon user");
//		client.networkaccess.sendObject(new User(), false);
//		System.out.println("Sending Kali user");
//		User kali = new User();
//		kali.setUsername("kali");
//		kali.setAction(1);
//		client.sendObject(kali);
//		System.out.println("Sending String: clear");
//		client.sendObject("clear");
//		System.out.println("Sending Maya user");
//		User maya = new User();
//		maya.setUsername("maya");
//		maya.setAction(2);
//		client.networkaccess.sendObject(maya, false);
//		System.out.println("Sending Kali user");
//		kali.setAction(3);
//		kali.setUsername("maya");
//		client.sendObject(kali);
//		client.disconnect();
//	}
	public static void main(String[] args)
	{
		String host = "localhost";
		int port = 8000;
		System.out.println("Creating new client");
		Client client = new Client(host, port);

		User kali = new User(4,"k301", "kali", "password", "",
				"kalihale@callutheran.edu", "manager");
		client.sendObject(kali);
		client.disconnect();
	}
}
