package ie.gmit.sw;

import java.io.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServiceHandler extends HttpServlet {
	
	//Variables
	private static final long serialVersionUID = 1L;
	private String remoteHost = null;
	private static long jobNumber = 0;
	private volatile BlockingQueue<Job> inQueue;
	private volatile Map<String, Resultator> outQueue;
	private volatile ExecutorService e;
	private volatile boolean jobComplete = false;
	private volatile String distance = "";
	private final int THREAD_POOL_SIZE = 3;
	
	
	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER"); //Reads the value from the <context-param> in web.xml
		
		inQueue = new LinkedBlockingQueue<Job>(); //inQueue linked blocking queue
		outQueue = new HashMap<String,Resultator>(); //outQueue hashmap
		e = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		//Initialise some request variables with the submitted form info. These are local to this method and thread safe...
		String algo = req.getParameter("cmbAlgorithm");
		String s = req.getParameter("txtS");
		String t = req.getParameter("txtT");
		String taskNumber = req.getParameter("frmTaskNumber");

		StringService ss = null;
		try {
			//Create the RMI, gets the Remote Message Object
			ss = (StringService) Naming.lookup("rmi://localhost:1099/stringservice");
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		}
		
		out.print("<html><head><title>Distributed Systems Assignment</title>");		
		out.print("</head>");		
		out.print("<body>");
		
		//If taskNumber = 0, adds jobNumber
		if (taskNumber == null){
			jobNumber++;
			taskNumber = new String("T" + jobNumber);
			jobComplete = false;

			//Creates job
			Job job = new Job(s, t, algo, taskNumber);

			//Adds job to queue
			inQueue.add(job);

			//Passes job to ServiceHandlerClient
			Runnable client = new ServiceHandlerClient(inQueue, outQueue, ss);

			//Executes client
			e.execute(client);

			
		}else {
			

			
			if (outQueue.containsKey(taskNumber)) {
				// Gets Resultator item from map using tasknumber
				Resultator r = outQueue.get(taskNumber);

				jobComplete = r.isProcessed();

				// Checks to see if the Resultator item is processed
				if (jobComplete == true) {
					
					// Removes processed item from map using taskNumber
					outQueue.remove(taskNumber);
					//Gets distance of current task
					distance = r.getResult();
				}
				
			}
		}

		//If task is complete, form does not need to be sent again
				if(jobComplete){
					out.print("<font color=\"#993333\"><b>");
					out.print("<center>The distance between " + s + " and " + t +" using "+distance+"</center>");
					out.print("<br><br><center>Thank you for using this service!<center>");
				}
				else//If the task is not completed -> resend
				{
					out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
					out.print("<div id=\"r\"></div>");
				
					out.print("<font color=\"#993333\"><b>");
					out.print("RMI Server is located at " + remoteHost);
					out.print("<br>Algorithm: " + algo);		
					out.print("<br>String <i>s</i> : " + s);
					out.print("<br>String <i>t</i> : " + t);
				
					//Refreshes every 10 seconds until task is complete
					out.print("<form name=\"frmRequestDetails\">");
					out.print("<input name=\"cmbAlgorithm\" type=\"hidden\" value=\"" + algo + "\">");
					out.print("<input name=\"txtS\" type=\"hidden\" value=\"" + s + "\">");
					out.print("<input name=\"txtT\" type=\"hidden\" value=\"" + t + "\">");
					out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
					out.print("</form>");								
					out.print("</body>");	
					out.print("</html>");	
					out.print("<font color=\"#993333\"><b>");
					out.print("Please wait while the result is being calculated....");
					out.print("<script>");
					//Form submitted
					out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
					out.print("</script>");
				}
			}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
