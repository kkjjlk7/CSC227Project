import java.io.*;
import java.util.Scanner;
public class FileReader implements Runnable {

	private JobQueue jobQ ;
	FileReader(JobQueue jobQ){
		this.jobQ = jobQ;
	}
	
	public JobQueue getJobQFromFile() {
		return jobQ;
	}
	@Override
	public void run() {
		System.out.println("\nReading the from the file....");
		
		try {
			// create the file object
			File file = new File("C:\\Users\\kkjjl\\Desktop\\job.txt");
			
			// create a reader for the file using scanner class
			Scanner reader = new Scanner(file);
			
			// create string for filling it with the line in the file 
			String line;
			
			// keep reading until the last line 
			while(reader.hasNextLine()) {
				// get the line 
				line = reader.nextLine();
				
				
				// now split the line how ??  
				// each line will look like this : line = "10:10;200"
				// first split based on this (;) 
				// we will get this array of strings : splitArr1 = [ "10:10" , "200"] 
				// second we will spilt the first index in the previous array based on this (:) 
				// we will get array of string : splitArr2 = ["10" , "10" ]
				String [] splitArr1 = line.split(";");
				String [] splitArr2 = splitArr1[0].split(":");
				
				// here we get the data
				int id = Integer.parseInt( splitArr2[0]);
				
				int burstTime = Integer.parseInt(splitArr2[1]);
				
				int requiredMemory = Integer.parseInt(splitArr1[1]);
				
				//here add  anew job in the queue
				jobQ.addJob(new PCB(id, burstTime, requiredMemory));
				System.out.println("Process with id: "+id+" and burst time: "+burstTime +" and required meomry : "+requiredMemory+" is added to the job queue");
			// end of while loop 	
			}
			System.out.println("\nReading file is finished :) ");
			
		}catch (Exception e) {
			System.out.println("Erorr in reading the file "+e.getMessage());
		}
		
	}

}
