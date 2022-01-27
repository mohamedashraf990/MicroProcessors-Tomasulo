
public class ReservationStations {
	

	int busy;
	String tag;
	String op;
	int vj;
	int vk;
	String qj;
	String qk;
	String a;
	int destinationRegister;
	int latency;
	
	public ReservationStations(int busy,String tag,String op,int vj,int vk,String qj,String qk,String a,int destinationRegister,int latency) {
		this.busy=busy;
		this.tag=tag;
		this.op=op;
		this.vj=vj;
		this.vk=vk;
		this.qj=qj;
		this.qk=qk;
		this.a=a;
		this.latency=latency;
		this.destinationRegister=destinationRegister;
	}

	public int getDestinationRegister() {
		return destinationRegister;
	}

	public void setDestinationRegister(int destinationRegister) {
		this.destinationRegister = destinationRegister;
	}

	public int getLatency() {
		return latency;
	}

	public void setLatency(int latency) {
		this.latency = latency;
	}

	public void setVj(int vj) {
		this.vj = vj;
	}

	public int getBusy() {
		return busy;
	}

	public void setBusy(int busy) {
		this.busy = busy;
	}

	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public int getVj() {
		return vj;
	}

	public void setvj(int vj) {
		this.vj = vj;
	}

	public int getVk() {
		return vk;
	}

	public void setVk(int vk) {
		this.vk = vk;
	}

	public String getQj() {
		return qj;
	}

	public void setQj(String qj) {
		this.qj = qj;
	}

	public String getQk() {
		return qk;
	}

	public void setQk(String qk) {
		this.qk = qk;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}
}
