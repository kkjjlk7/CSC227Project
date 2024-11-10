
public class RAM {

	private final int size = 1024;
	private int freeSize=size;
	 boolean isMemoryFree=true;
	 
	public int getFreeMemory() {
		return freeSize;
	}
	
	public void decreaseFreeMemory(int size) {
		this.freeSize-=size;
	}
	
	public  void increaseFreeMemory(int size) {
		this.freeSize+=size;
		isMemoryFree=true;
	}
	public boolean isMemeoryFree() {
		return isMemoryFree;
	}
	
	
}
