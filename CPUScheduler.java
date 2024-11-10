import java.util.*;
public class CPUScheduler {

     static ReadyQueue readyQ = new ReadyQueue();
  static JobQueue jobQ = new JobQueue();
  
  static int timeQuantum = 10;
   static List<PCB> completedJobs = new ArrayList<>();
    private static RAM ram = new RAM();
     static Thread FileReaderThread = new Thread(new FileReader(jobQ));
    static Thread jobLoaderThread = new Thread(new JobLoader(readyQ, jobQ, ram));
    private static int currentTime = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        showMenu();
        int option = readOption(input);

        FileReaderThread.start();

        try {
            FileReaderThread.join(); // Wait for the FileReader to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        jobLoaderThread.start();

      try {
		Thread.sleep(100);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

        performOption(option);
        printResults();
    }

    public static int readOption(Scanner input) {
    	int option=input.nextInt();
    	while(option>3) {
    		System.out.println("Invalid option , please try again");
        	option=input.nextInt();
    	}
    	return option;
    }
    private static void performOption(int option) {
        switch (option) {
            case 1:
            	
            	while(true) {
                FCFS();// First-Come-First-Serve
                
           
                if(jobQ.AllJobsFinished()) {
                	break;
                }
                else {
                	while(ram.isMemoryFree && ! jobQ.AllJobsFinished()) {
                	try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                	}
                
            	} 
                break;
            case 2:
            	while(true) {
                RR(); // Round-Robin
                if(jobQ.AllJobsFinished()) {
                	break;
                }
                else {
                	while(ram.isMemoryFree && ! jobQ.AllJobsFinished()) {
                	try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                	}
            	}
                break;
            case 3:
            	while(true) {
                SJF(); // Shortest Job First
                if(jobQ.AllJobsFinished()) {
                	break;
                }
                else {
                	while(ram.isMemoryFree && ! jobQ.AllJobsFinished()) {
                	try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                	}
            	}
                break;
            
        }
    }

    // First-Come-First-Serve (FCFS) scheduling
    private static void FCFS() {

        // Process jobs in the ready queue in FCFS order
        while (!readyQ.isEmpty()) {
            PCB job = readyQ.serve(); // Retrieve the next job from the ready queue
           
            System.out.println("\nProcessing job ID (FCFS): " + job.id);
            
            job.waitingTime = currentTime;
            
            currentTime += job.burstTime;
            
            job.turnaroundTime = currentTime;
           
            completedJobs.add(job);
         // Free memory after the job is done
            ram.increaseFreeMemory(job.memoryRequired); 
            
            }
        }
    
   

    // Round-Robin (RR) scheduling with 10ms time quantum
    private static void RR() {

        while (!readyQ.isEmpty()) {
            PCB job = readyQ.serve();

            // Case 1: Job needs more time than the time quantum
            if (job.burstTime > timeQuantum) {
            	//Increase the current time with time quantum not burst time 
            	// why ? because the the burst the time is bigger than the burst time
                currentTime += timeQuantum;
                
                // decrease the burst time
                job.burstTime -= timeQuantum;
                
             // Put the job back in the queue, because we didn't finish from it yet
                readyQ.addJob(job); 
            }
            // Case 2: Job completes within the time quantum
            else {
                System.out.println("\nProcessing job ID (RR): " + job.id);
                //Here increase the current time with the burst time
                currentTime += job.burstTime;
                
                
                job.turnaroundTime = currentTime;
                job.waitingTime = job.turnaroundTime - job.getOrigianlBurstTime();
                
                completedJobs.add(job);
                
             // Free memory after the job is done
                ram.increaseFreeMemory(job.memoryRequired); 
            }
        }
    }

    // Shortest Job First (SJF) scheduling
    private static void SJF() {
    	
        List<PCB> sortedList = new ArrayList<>(readyQ.getJobs());
        // Sort jobs by burst time (shortest job first)
        
        sortedList.sort(new Comparator<PCB>() {
            @Override
            public int compare(PCB job1, PCB job2) {
                return Integer.compare(job1.burstTime, job2.burstTime);
            }
        });
        for (PCB job : sortedList) {
            System.out.println("\nProcessing job ID (SJF): " + job.id);
            
            job.waitingTime = currentTime;
            
            job.turnaroundTime = job.burstTime + job.waitingTime;
            
            currentTime += job.burstTime;
            
            completedJobs.add(job);
         // Free memory after the job is done
            ram.increaseFreeMemory(job.memoryRequired);
            readyQ.removeJob(job);
        }
    }

    // Show scheduling algorithm menu
    private static void showMenu() {
        System.out.println("\nChoose Scheduling Algorithm:");
        System.out.println("1. First-Come-First-Serve (FCFS)");
        System.out.println("2. Round-Robin (RR-10)");
        System.out.println("3. Shortest Job First (SJF)");
    }

    // Print the results of the job executions
    private static void printResults() {
    	System.out.println("\n\t\t\t Results: ");
        System.out.println("\nJob ID\tBurst Time\tWaiting Time\tTurnaround Time");
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        for (PCB job : completedJobs) {
            System.out.println(job.id + "\t" + job.getOrigianlBurstTime() + "\t\t" + job.waitingTime + "\t\t" + job.turnaroundTime);
            totalWaitingTime += job.waitingTime;
            totalTurnaroundTime += job.turnaroundTime;
        }

        if (!completedJobs.isEmpty()) {
            System.out.println("\nAverage Waiting Time: " + (double) totalWaitingTime / completedJobs.size());
            System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / completedJobs.size());
        } else {
            System.out.println("\nNo jobs completed.");
        }
    }
}
