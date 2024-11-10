import java.util.*;

public class JobQueue {

    protected Queue<PCB> jobs = new LinkedList<>();
    
    // Add a job to the queue
    public void addJob(PCB job) {
        jobs.add(job);
    }
    
    // Get a copy of the current jobs in the queue
    public Queue<PCB> getJobs() {
        return new LinkedList<>(jobs);
    }
    
    // Get the size of the queue
    public int size() {
        return jobs.size();
    }
    
    // Check if the queue is empty
    public boolean isEmpty() {
        return jobs.isEmpty();
    }
    
    // Check if all jobs in the queue are finished
    public boolean AllJobsFinished() {
        for (PCB job : jobs) {
            if (!job.finished) {
                return false;
            }
        }
        return true;
    }
    
    // Serve the next job from the queue (removes the job from the queue)
    public PCB serve() {
        return jobs.poll();  // poll() removes and returns the head of the queue, or null if empty
    }
    public void finishJob(PCB job) {
    	for(PCB job1: jobs) {
    		if(job1.id==job.id)
    			job1.finished=true;
    	}
    }
    
   
}
