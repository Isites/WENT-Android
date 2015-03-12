package talk2server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Sender {
	 // IO streams
	  private DataOutputStream toServer;
	  private DataInputStream fromServer;
	  Socket socket;
	  public Sender(){
		  try {
	      // Create a socket to connect to the server
	       //Socket socket = new Socket("localhost", 3333);
	         socket = new Socket("114.215.86.189", 3333);
	      // Create an input stream to receive data from the server
	      fromServer = new DataInputStream(
	        socket.getInputStream());       
	      
	      // Create an output stream to send data to the server
	      toServer =
	        new DataOutputStream(socket.getOutputStream());
	    }
	    catch (IOException ex) {
	    }
	  }
	  public String send(String signals){
		  String x = null, s = null; 
		  try {
		        toServer.writeInt(0);
		        //toServer.writeUTF(signals);
		        toServer.flush();

		        x = fromServer.readUTF();
		        s = fromServer.readUTF();
		   }catch (IOException ex) {
		        System.err.println(ex);
		   }
		  return x + s;
	  }
	  public void close() throws IOException{
		  socket.close();
	  }
}
