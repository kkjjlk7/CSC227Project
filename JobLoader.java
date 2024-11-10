public class JobLoader implements Runnable {

    ReadyQueue readyQ;
    JobQueue jobQ;
    RAM ram;

    JobLoader(ReadyQueue readyQ, JobQueue jobQ, RAM ram) {
        this.readyQ = readyQ;
        this.jobQ = jobQ;
        this.ram = ram;
    }

    @Override
    public void run() {
      
            System.out.println("\nTransferring jobs to readyQueue...");

            
            while(true) {
            	
            for (PCB job : jobQ.jobs) {
            	
            	// checks if there is a free space for the current job and it is not finished
                if(job.memoryRequired<=ram.getFreeMemory() && !job.finished) { 
                    job.finished=true;
                    readyQ.addJob(job);
            	// decrease the memory free space
                ram.decreaseFreeMemory(job.memoryRequired);
                System.out.println("\nJob added to readyQueue - ID: " + job.id + ", Burst Time: " + job.burstTime);
            }
                else {
                	ram.isMemoryFree=false;
                	}
                //end of for loop
                }
            
            if(jobQ.AllJobsFinished()) {
            	break;
                }
            else {
            	
            	while(!ram.isMemoryFree) {
            		try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
            		
				}
            }

        }
            System.out.println("Transfring jobs is finishied");
}

    }

