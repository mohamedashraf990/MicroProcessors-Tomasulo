
public class RegisterFile {
	
	
	int busy;
	int number;
	String value;
	public int getBusy() {
		return busy;
	}
	public void setBusy(int busy) {
		this.busy = busy;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	

	public RegisterFile(int busy,int number,String value ) {
		this.busy=busy;
		this.number=number;
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}


}
