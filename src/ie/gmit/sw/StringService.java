package ie.gmit.sw;

//This class is an interface that extends remote

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StringService extends Remote{

    public Resultator compare(String s, String t, String algo) throws RemoteException;

}