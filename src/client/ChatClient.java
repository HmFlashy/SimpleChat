// This file contains material supporting section 3.7 of the textbook:// "Object Oriented Software Engineering" and is issued under the open-source// license found at www.lloseng.com package client;import common.*;import java.io.*;import java.util.Observable;import java.util.Observer;import com.lloseng.ocsf.client.ObservableClient;/** * This class overrides some of the methods defined in the abstract * superclass in order to give more functionality to the client. * * @author Dr Timothy C. Lethbridge * @author Dr Robert Lagani&egrave; * @author Fran&ccedil;ois B&eacute;langer * @version July 2000 */public class ChatClient implements Observer{  //Instance variables **********************************************    /**   * The interface type variable.  It allows the implementation of    * the display method in the client.   */  ChatIF clientUI;   ObservableClient obsClient;  String id;  int port;  String host;    //Constructors ****************************************************    /**   * Constructs an instance of the chat client.   *   * @param host The server to connect to.   * @param port The port number to connect on.   * @param clientUI The interface type variable.   */    public ChatClient(String host, int port, ChatIF clientUI)     throws IOException   {    this.port = port;    this.host = host;    this.clientUI = clientUI;    this.obsClient = new ObservableClient(host, port);    obsClient.addObserver(this);  }    //Instance methods ************************************************  protected void connectionClosed() {		clientUI.display("Connexion fermée ...");		clientUI.handleCode(2);  }    protected void connectionException(Exception exception) {	  clientUI.display("Connexion interrompue...");	  clientUI.handleCode(2);  }    protected void connectionEstablished() {	  clientUI.display("Connexion établie ...");  }      /**   * This method handles all data that comes in from the server.   *   * @param msg The message from the server.   */  public void handleMessageFromServer(Object msg)   {	  String message = msg.toString();	  if(message.startsWith("#")){		  String[] messsplit = message.split(" ");			switch(messsplit[0]){				case "#quit":					try {						clientUI.display("Le serveur s'est arrêté... arrêt de la connexion...");						obsClient.closeConnection();						this.connectionClosed();					} catch (IOException e1) {						quit();					}					break;				case "#stop":					clientUI.display("Le serveur n'accepte plus de nouvelles connexions");					break;				case "#close":					clientUI.display("Déconnexion des clients au serveur");					try {						obsClient.closeConnection();					} catch (IOException e1) {						quit();					}					break; 			}	  } else {		  clientUI.display(message);	  }  }  /**   * This method handles all data coming from the UI               *   * @param message The message from the UI.       */  public void handleMessageFromClientUI(String message)  {	if(message.startsWith("#")){		String[] messsplit = message.split(" ");		System.out.println(message);		switch(messsplit[0]){			case "#quit":				clientUI.display("Arrêt du client...");				try {					obsClient.closeConnection();				} catch (IOException e1) {					// TODO Auto-generated catch block					e1.printStackTrace();				} finally {					quit();				}				break;			case "#logoff":				if(obsClient.isConnected()) {					try {						clientUI.display("Déconnexion du serveur ...");						obsClient.closeConnection();						clientUI.display("Déconnexion réussie");						clientUI.handleCode(2);					} catch (IOException e) {						clientUI.display("Déconnexion impossible ...");						// TODO Auto-generated catch block						e.printStackTrace();					}				} else {					clientUI.display("Tu n'es pas connecté au serveur ...");				}				break;			case "#sethost":				if(messsplit[1] == null){					clientUI.display("Hôte manquant ...");				} else if(obsClient.isConnected()){					clientUI.display("Déconnectez vous du serveur avant de changer d'hôte ...");				} else {					this.host = messsplit[1];					clientUI.display("Hôte changé avec succès !");				}				break;			case "#setport":				if(messsplit[1] == null){					clientUI.display("Port manquant ...");				} else if(obsClient.isConnected()){					clientUI.display("Déconnectez vous du serveur avant de changer de port ...");				} else {					port = Integer.parseInt(messsplit[1]);					clientUI.display("Port changé avec succès !");				}				break;			case "#login":				if(obsClient.isConnected()){					clientUI.display("Vous êtes déjà connecté ...");				} else {						if(messsplit.length < 2){							clientUI.display("Vous devez rentrer un pseudo ...");						} else {							this.id = messsplit[1];							try {								obsClient.openConnection();								obsClient.sendToServer(message);								clientUI.handleCode(1);								clientUI.display("Vous êtes maintenant loggé au serveur...");							} catch (IOException e) {								clientUI.display("Problème d'accès au serveur ...");								// TODO Auto-generated catch block								e.printStackTrace();							}						}									}				break;			case "#getport":				clientUI.display("Port " + this.port);				break;			case "#gethost":				clientUI.display("Hôte " + this.host);				break;		}	}else{		try {			if(!obsClient.isConnected()) {				clientUI.display("Vous n'êtes pas connecté au serveur. Connectez-vous avant.");			} else {				obsClient.sendToServer(message);			}		} catch(IOException e) {	      clientUI.display	        ("Impossibilité d'envoyer le message, coupure de la connexion au serveur..");			try {				obsClient.closeConnection();				this.connectionClosed();			} catch (IOException e1) {				// TODO Auto-generated catch block				e1.printStackTrace();			}	      	    }	}  }    /**   * This method terminates the client.   */  public void quit()  {    System.exit(0);  }		@Override	public void update(Observable arg0, Object arg1) {		if(arg1.toString().equals(ObservableClient.CONNECTION_CLOSED)) {			this.connectionClosed();		} else if(arg1.toString().equals(ObservableClient.CONNECTION_ESTABLISHED)) {			this.connectionEstablished();		} else if(arg1 instanceof Exception) {			this.connectionException((Exception)arg1);		} else {			this.handleMessageFromServer((String)arg1);		}	}}//End of ChatClient class