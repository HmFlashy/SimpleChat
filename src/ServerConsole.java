import java.io.*;

import server.EchoServer;

public class ServerConsole {

	  /**
	   * The default port to listen on.
	   */
	  final public static int DEFAULT_PORT = 6000;
	  
	EchoServer es;
	
	public ServerConsole(int port){
	    es = new EchoServer(port);
        try {
			es.getObs().listen();
		} catch (IOException e) {
		    System.out.println("ERROR - Could not listen for clients!");
		}
	}
	
	public void listen(){
		try {
			es.getObs().listen();
		} catch (IOException e) {
		    System.out.println("ERROR - Could not listen for clients!");
		}
	}

	private void console() {
		try
	    {
	      BufferedReader fromConsole = 
	        new BufferedReader(new InputStreamReader(System.in));
	      String message;

	      while (true) 
	      {
	        message = fromConsole.readLine();
	        es.handleMessageFromAdmin(message);
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println
	        ("Unexpected error while reading from console!");
	    }
	}
	
	 public static void main(String[] args) 
	  {
	    int port = 0; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
		
	    ServerConsole sc = new ServerConsole(port);
	    sc.console();
	  }
}
