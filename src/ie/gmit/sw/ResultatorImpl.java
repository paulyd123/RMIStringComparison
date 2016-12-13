package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//This class, ResultatorImpl extends UnicastRemoteObject and implements the Resultator interface.
public class ResultatorImpl extends UnicastRemoteObject implements Resultator{
	
	//All variables set to private
	private static final long serialVersionUID = 1L;
	private String s;
	private String t;
	private boolean isProcessed = false;
	private String r;

	//Constructor
	public ResultatorImpl() throws RemoteException{
	}
	
	public ResultatorImpl(String str1, String str2) throws RemoteException{
		
		this.s=str1;
		this.t=str2;
	}

	public String getResult() throws RemoteException {
		
		return r;
	}

	public void setResult(String result) throws RemoteException {
		
		this.r = result;
	}

	public boolean isProcessed() throws RemoteException {	
		
		return isProcessed;
	}

	public void setProcessed() throws RemoteException {
		
		this.isProcessed = true;
	}
}