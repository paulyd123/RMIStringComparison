package ie.gmit.sw;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servant {

		public static void main(String[] args) throws Exception{
			
		//Creates an instance of the class StringService and  then passes the string as an argument to its constructor
		StringService service = new StringServiceImpl();
		
		//Starts RMI registry on port 1099
		LocateRegistry.createRegistry(1099);
		
		//Binds remote object to the registry with the name "stringservice"
		Naming.rebind("stringservice", service);
		
		//Outputs message to let user know server is ready
		System.out.println("Server ready....");
	}
}