package ie.gmit.sw;

//Adds strings and returns the distance and creates new threads

import java.rmi.RemoteException;

import ie.gmit.algo.*;


public class StringComparator implements Runnable {
		private String s;
		private String t;
		private Resultator r;
		private String algo;
		
		//Initialises algorithms
		private Levenshtein ls = new Levenshtein();
		private HammingDistance hd = new HammingDistance();
		private DamerauLevenshtein dl = new DamerauLevenshtein();
		
		//String comparator constructor
		public StringComparator(String str1, String str2, Resultator r, String algorithm){
			this.s = str1;
			this.t = str2;
			this.r = r;
			this.algo = algorithm;
		}

		public void run() {
			int distance;
			double distanceD;
			float distanceF;
			String distanceS;
			
			try{
				//Choosing string
				if(algo.equalsIgnoreCase("Levenshtein Distance")){
					
					distance = ls.distance(s, t);
					try {
						r.setResult("Levenshtein Distance is: "+distance);
						Thread.sleep(10000);
						r.setProcessed();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if(algo.equalsIgnoreCase("Hamming Distance"))
				{
					distance = hd.distance(s, t);
					try {
						r.setResult("Hamming Distance is: "+distance);
						Thread.sleep(10000);
						r.setProcessed();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if(algo.equalsIgnoreCase("Damerau-Levenshtein Distance"))
				{
					distance = dl.distance(s, t);
					try {
						r.setResult("Damerau-Levenshtein Distance: "+distance);
						Thread.sleep(10000);
						r.setProcessed();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}