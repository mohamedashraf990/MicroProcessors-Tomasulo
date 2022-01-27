

public class StoreBuffers {
	
	int busy;
	int address;
	String tag;
	int latency;
	int v;
	String q;
	int register;

	

	
	public int getRegister() {
		return register;
	}


	public void setRegister(int register) {
		this.register = register;
	}


	public StoreBuffers(String tag,int busy,int address,int v,String q,int register,int latency) {
		this.tag=tag;
		this.busy=busy;
		this.address=address;
		this.v=v;
		this.q=q;
		this.latency=latency;
		this.register=register;
	}
	

	public int getLatency() {
		return latency;
	}


	public void setLatency(int latency) {
		this.latency = latency;
	}


	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}


	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
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

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}


	
	

}



