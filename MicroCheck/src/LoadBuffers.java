
public class LoadBuffers {
	
	

	int busy;
	int address;
	String tag;
	int latency;
	int firstOperand;
	
	public int getFirstOperand() {
		return firstOperand;
	}


	public void setFirstOperand(int firstOperand) {
		this.firstOperand = firstOperand;
	}


	public LoadBuffers(String tag,int busy,int address,int latency,int firstOperand) {
		this.tag=tag;
		this.busy=busy;
		this.address=address;
		this.latency=latency;
		this.firstOperand=firstOperand;
		
	}
	

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getBusy() {
		return busy;
	}

	public void setBusy(int busy) {
		this.busy = busy;
	}


	public int getlatency() {
		return latency;
	}

	public void setlatency(int latency) {
		this.latency = latency;
	}
	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}



}
