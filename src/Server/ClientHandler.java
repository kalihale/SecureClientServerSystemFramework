package Server;

import ObjectsToPass.Queries;
import ObjectsToPass.User;
import Security.AsymmetricEncrypt;
import Security.RSA;
import Shared.NetworkAccess;

import javax.crypto.SealedObject;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
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

	//	／(•ㅅ•)＼	ClientHandler has-a UserHandler
	private UserHandler userHandler;

	// ／(•ㅅ•)＼ Encryption module
	private AsymmetricEncrypt<Serializable> encrypt;

	// ／(•ㅅ•)＼ Key pair
	private KeyPair keyPair;

	// ／(•ㅅ•)＼ The public key from the client
	private PublicKey clientPublicKey;
	
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
		// ／(•ㅅ•)＼ Create encryption module
		this.encrypt = new RSA<>();

		// ／(•ㅅ•)＼ Create Server keypair
		this.keyPair = this.encrypt.getKeyPair();
		
		networkaccess = new NetworkAccess(socket);
		// ／(•ㅅ•)＼ Sending server's public key (unique for each client),
		//           receiving client's public key (unique for each client)
		this.clientPublicKey = (PublicKey)networkaccess.sendObject(this.keyPair.getPublic(), true);
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
				Object obj = networkaccess.readObject();
				// ／(•ㅅ•)＼ If object is a String
				if(obj instanceof byte[])
				{
					String cmd = encrypt.decryptString((byte[]) obj, this.keyPair.getPrivate());
					System.out.println("cmd is String");
					CommandProtocol.processCommand((String)cmd, networkaccess, this);
				}
				// ／(•ㅅ•)＼ If object is an object of another type
				else if(obj instanceof SealedObject)
				{
					SealedObject sealedObject = (SealedObject) obj;
					Object cmd = encrypt.decryptObject(sealedObject, this.keyPair.getPrivate());
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
				}
				else
				{
					if(obj == null)
					{
						System.out.println("object is null");
					}
					else
					{
						System.out.println("ClientHandler: Cannot process cmd");
					}
				}
			} 
			catch (IOException | ClassNotFoundException | SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
				go = false;
			}
		}
	}
}
