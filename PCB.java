
public class PCB {

	int id;
	private int origianlBurstTime; 
	int burstTime;
	int memoryRequired;
	int waitingTime;
	int turnaroundTime;
	boolean finished=false;
	
	 public PCB (int id, int burstTime, int memoryRequired) {
		this.id = id;
		this.burstTime=burstTime;
		this.memoryRequired= memoryRequired;
		this.waitingTime=0;
		this.turnaroundTime=0;
		this.origianlBurstTime=burstTime;
	 }
	 
	public  int getOrigianlBurstTime() {
		return origianlBurstTime;
				}
	 
	 
}
