package ie.gmit.sw;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class ServiceHandlerClient implements Runnable {
	private BlockingQueue<Job> iq;
	private Resultator result;
	private StringService service;
	private Map<String, Resultator> oq;

	public ServiceHandlerClient(BlockingQueue<Job> inQueue, Map<String, Resultator> outQueue, StringService ss) {
		this.iq = inQueue;
		this.service = ss;
		this.oq = outQueue;
	}

	//Creates a thread
	public void run() {
		Job job = iq.poll();

		try {
			//Thread set to sleep for 10 seconds
			Thread.sleep(10000);
	
			//Sends both strings and algorithms to compare method
			//Sets Resultator as a new instance
			result = service.compare(job.getStr1(), job.getStr2(), job.getAlgo());
			
			//Adds task number and Resultator to OutQueue
			oq.put(job.getTaskNumber(), result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
