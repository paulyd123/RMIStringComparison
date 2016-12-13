package ie.gmit.sw;

//StringServiceImpl Class extends UnicastRemoteObject and implements StringService interface

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StringServiceImpl extends UnicastRemoteObject implements StringService {
	
	private static final long serialVersionUID = 1L;

	public StringServiceImpl() throws RemoteException {
		super();
	}

	public Resultator compare(String s, String t, String algo) throws RemoteException {
		
		//Instance
		Resultator r = new ResultatorImpl();
		
		//Creating and starting thread
		Thread thread = new Thread(new StringComparator(s, t, r, algo));
        thread.start();

        //Returns Resultator
		return r;
	}
}