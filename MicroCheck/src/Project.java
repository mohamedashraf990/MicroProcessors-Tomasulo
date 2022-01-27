import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Project {

	static String InstructionMemory[]=new String [1024]; //16 
	static int  DataMemory[]=new int [2048];
	static int bus;
	static boolean memoryflag=false;
	public static int InstructionMemoryCounter=0;
	public static StoreBuffers StoreBufferArray[]= new StoreBuffers[5];
	public static LoadBuffers LoadBufferArray[]= new LoadBuffers[5];
	public static RegisterFile Registers[]=new RegisterFile[32];
	static String InstructionBuffer[]=new String [512];		
	public static ReservationStations AddSubBufferArray[]= new ReservationStations[3];
	public static ReservationStations MultDivBufferArray[]= new ReservationStations[3];
	public static int currentClockCycle=0;
	public static int InstructionToFetch=0;
	public static int addsubIndex=0;
	public static int MulDivIndex=0;
	public static int loadIndex=0;
	public static int storeIndex=0;
	public static int instructionCount=0;
	public static String currentlyFetched="";
	public static ArrayList<Integer> destinationsToExecute=new ArrayList<>();
	
	
	
	
	public static void intiate() {
		readFile("Program 1.txt");
		createRegisters();
		fillDataMemory();
		fillRegisters();
		
		while(true) {
			currentClockCycle++;
			int loadCount=getLoadBufferCount();
			int addSubCount=getAddSubBufferCount();
			int mulDivCount=getMulDivBufferCount();
			int storeCount=getStoreBufferCount();
			String Operation="";		
			if(checkActivity()==false) {
				return;
			}else {
				if(currentClockCycle==1) {
					 System.out.println("**************");
					System.out.println("Clock Cycle 1");
					fetch();
					issue(currentlyFetched);
				     loadCount=getLoadBufferCount();
					 addSubCount=getAddSubBufferCount();
					 mulDivCount=getMulDivBufferCount();
					 storeCount=getStoreBufferCount();
					printLoadBuffer();
					printStoreBuffers();
					printAddSubBuffer();
					printMulDivBuffer();
					printRegsiters();
					 System.out.println("**************");
					}
					else if(currentClockCycle==2) {
					System.out.println("Clock Cycle 2");
					optimizer(currentlyFetched);
					fetch();	
					issue(currentlyFetched);
					printLoadBuffer();
					printStoreBuffers();
					printAddSubBuffer();
					printMulDivBuffer();
					printRegsiters();
					 loadCount=getLoadBufferCount();
					 addSubCount=getAddSubBufferCount();
					 mulDivCount=getMulDivBufferCount();
					 storeCount=getStoreBufferCount();
					 System.out.println("**************");
						
				   }else {
				
					// we want to check and also not to get the instruction missed in the future
					
					System.out.println("Clock Cycle "+currentClockCycle);
			
					optimizer(currentlyFetched);
					fetch();
					// we want to know the type of instruction and check against the buffer 
					 loadCount=getLoadBufferCount();
					 addSubCount=getAddSubBufferCount();
					 mulDivCount=getMulDivBufferCount();
					 storeCount=getStoreBufferCount();
					String instruction[]=currentlyFetched.split(" ");
					Operation=instruction[0];
					if(Operation.equals("ADD")||Operation.equals("SUB")) {
						if(addSubCount>=AddSubBufferArray.length) {	
			
							InstructionToFetch--;
					
						}else {
							issue(currentlyFetched);
						}
					}
					
					
					
					if(Operation.equals("MUL")||Operation.equals("DIV")) {
						if(mulDivCount>=MultDivBufferArray.length) {
							InstructionToFetch--;
						}else {	
							issue(currentlyFetched);
						}
					}
					
					if(Operation.equals("LDI")) {
						if(loadCount>=LoadBufferArray.length) {
							InstructionToFetch--;
						}else {
							issue(currentlyFetched);
						}
					}
					
					if(Operation.equals("SW")) {
						if(storeCount>=LoadBufferArray.length) {
							InstructionToFetch--;	
						}else {	
							issue(currentlyFetched);
						}
					}
				
					printLoadBuffer();
					printStoreBuffers();
					printAddSubBuffer();
					printMulDivBuffer();
					printRegsiters();
					 loadCount=getLoadBufferCount();
					 addSubCount=getAddSubBufferCount();
					 mulDivCount=getMulDivBufferCount();
					 storeCount=getStoreBufferCount();
					
					 System.out.println("**************");
				}
			}
		}
		
	}
	
	
	

	
	
	
	public static int getAddSubBufferCount() {
		int counter=0;
		for(int i=0;i<AddSubBufferArray.length;i++) {
			if(AddSubBufferArray[i]!=null) {
				counter++;
			}
		}
		return counter;
	}
	
	public static int getMulDivBufferCount() {
		int counter=0;
		for(int i=0;i<MultDivBufferArray.length;i++) {
			if(MultDivBufferArray[i]!=null) {
				counter++;
			}
		}
		return counter;
	}
	
	public static int getLoadBufferCount() {
		int counter=0;
		for(int i=0;i<LoadBufferArray.length;i++) {
			if(LoadBufferArray[i]!=null) {
				counter++;
			}
		}
		return counter;
	}
	
	public static int getStoreBufferCount() {
		int counter=0;
		for(int i=0;i<StoreBufferArray.length;i++) {
			if(StoreBufferArray[i]!=null) {
				counter++;
			}
		}
		return counter;
	}
	
	
	
	
	public static void fillRegisters() {
	
		for(int i=0;i<Registers.length;i++) {
			RegisterFile xFile=new RegisterFile(0, i, Integer.toString(i));
			Registers[i]=xFile;
		}
	
	}
	
	
	
	public static void printAddSubBuffer() {
		System.out.println("ADDSUB BUFFER CONTENT");
		System.out.println("-----------------------");
		for(int i=0;i<AddSubBufferArray.length;i++) {
			if(AddSubBufferArray[i]!=null) {
				System.out.println("Tag "+AddSubBufferArray[i].getTag()+" Busy "+AddSubBufferArray[i].getBusy()+" Latency "+AddSubBufferArray[i].getLatency()+" Vj "+
			AddSubBufferArray[i].getVj()+" Vk "+AddSubBufferArray[i].getVk()+" Qj "+AddSubBufferArray[i].getQj()+" Qk "+ AddSubBufferArray[i].getQk()+" destination register "+AddSubBufferArray[i].getDestinationRegister());
						
			}else {
				System.out.println("NULL");
			}
		}
		System.out.println("-----------------------");
	}
	public static void printMulDivBuffer() {
		System.out.println("MULLDIV BUFFER CONTENT");
		System.out.println("-----------------------");
		for(int i=0;i<MultDivBufferArray.length;i++) {
			if(MultDivBufferArray[i]!=null) {
				System.out.println("Tag "+MultDivBufferArray[i].getTag()+" Busy "+MultDivBufferArray[i].getBusy()+" Latency "+MultDivBufferArray[i].getLatency()+" Vj "+
						MultDivBufferArray[i].getVj()+" Vk "+MultDivBufferArray[i].getVk()+" Qj "+MultDivBufferArray[i].getQj()+" Qk "+ MultDivBufferArray[i].getQk()+" destination register "+MultDivBufferArray[i].getDestinationRegister());
						
			}else {
				System.out.println("NULL");
			}
		}
		System.out.println("-----------------------");
	}
	
	
	public static void fetch() {
		
		if(InstructionBuffer.length>InstructionToFetch) {
			
			if(InstructionMemory[InstructionToFetch]!=null) {
				InstructionBuffer[InstructionToFetch]=	InstructionMemory[InstructionToFetch];
				currentlyFetched=InstructionBuffer[InstructionToFetch];
				InstructionToFetch++;
			}else {	
				currentlyFetched="";
			}
		}
	}
	
	
	
	

	public static void optimizer(String instruction) {
		int counter=0;
		for(int i=0;i<AddSubBufferArray.length;i++) {
			
		
			if(AddSubBufferArray[i]!=null) {
				if(AddSubBufferArray[i].getQj().equals("") && AddSubBufferArray[i].getQk().equals("")) {
				
					int latencyRemaining=AddSubBufferArray[i].getLatency();
					int destinationRegister=AddSubBufferArray[i].getDestinationRegister();
					System.out.println("this is my destination");
					System.out.println(destinationRegister);

					
					
					if(latencyRemaining==0) {
						int vj=AddSubBufferArray[i].getVj();
						int vk=AddSubBufferArray[i].getVk();
						String op=AddSubBufferArray[i].getOp();
						int destination=AddSubBufferArray[i].getDestinationRegister();
						String tag=AddSubBufferArray[i].getTag();
						executeAddSub(op, destination, vj, vk,tag,i);
						AddSubBufferArray[i]=null;					
					}else {
						System.out.println("This is the destination");
						    if(destinationsToExecute.contains(destinationRegister)&&counter==0) {
						    	AddSubBufferArray[i].setLatency(latencyRemaining-1);
								Registers[AddSubBufferArray[i].getDestinationRegister()].setValue(AddSubBufferArray[i].getTag());
								Registers[AddSubBufferArray[i].getDestinationRegister()].setBusy(1);
							
								counter++;
						    }else if(destinationsToExecute.contains(destinationRegister) && counter!=0) {
						    	AddSubBufferArray[i].setLatency(latencyRemaining-1);

						    }else {
						    	AddSubBufferArray[i].setLatency(latencyRemaining-1);
								Registers[AddSubBufferArray[i].getDestinationRegister()].setValue(AddSubBufferArray[i].getTag());
								Registers[AddSubBufferArray[i].getDestinationRegister()].setBusy(1);
						    }
					}
					destinationsToExecute.add(destinationRegister);
				}
				
			}
			
			
		}

		for(int i=0;i<MultDivBufferArray.length;i++) {
			
			if(MultDivBufferArray[i]!=null) {
				if(MultDivBufferArray[i].getQj().equals("") && MultDivBufferArray[i].getQk().equals("")) {
					int destinationRegister=MultDivBufferArray[i].getDestinationRegister();
				    int latencyRemaining=MultDivBufferArray[i].getLatency();
				
				if(latencyRemaining==0) {
					int vj=MultDivBufferArray[i].getVj();
					int vk=MultDivBufferArray[i].getVk();
					String op=MultDivBufferArray[i].getOp();
					int destination=MultDivBufferArray[i].getDestinationRegister();
					String tag=MultDivBufferArray[i].getTag();
					executeMulDiv(op, destination, vj, vk,tag,i);
					MultDivBufferArray[i]=null;					
				}else {
					
					 System.out.println("This is the counter value "+counter);
					 if(destinationsToExecute.contains(destinationRegister) && counter==0) {
						 MultDivBufferArray[i].setLatency(latencyRemaining-1);
							Registers[MultDivBufferArray[i].getDestinationRegister()].setValue(MultDivBufferArray[i].getTag());
							Registers[MultDivBufferArray[i].getDestinationRegister()].setBusy(1);
						
							counter++;
					 }else if(destinationsToExecute.contains(destinationRegister) && counter!=0) {
						 MultDivBufferArray[i].setLatency(latencyRemaining-1);

					 }else {
						 MultDivBufferArray[i].setLatency(latencyRemaining-1);
							Registers[MultDivBufferArray[i].getDestinationRegister()].setValue(MultDivBufferArray[i].getTag());
							Registers[MultDivBufferArray[i].getDestinationRegister()].setBusy(1);
					 }
				}
				destinationsToExecute.add(destinationRegister);
				}
			}
		}
		
		
		
		for(int i=0;i<LoadBufferArray.length;i++) {
			if(LoadBufferArray[i]!=null) {
				
				int latencyRemaining=LoadBufferArray[i].getlatency();
				int destinationRegister=LoadBufferArray[i].getFirstOperand();
					if(latencyRemaining==0) {

						
						executeLoad(LoadBufferArray[i].getFirstOperand(), LoadBufferArray[i].getAddress(), LoadBufferArray[i].getTag());
						LoadBufferArray[i]=null;
					}else {
						
						
						System.out.println("This is the destination");
					    if(destinationsToExecute.contains(destinationRegister)&&counter==0) {
					    	LoadBufferArray[i].setlatency(latencyRemaining-1);
							Registers[LoadBufferArray[i].getFirstOperand()].setValue(LoadBufferArray[i].getTag());
							Registers[LoadBufferArray[i].getFirstOperand()].setBusy(1);
		
							counter++;
					    }else if(destinationsToExecute.contains(destinationRegister) && counter!=0) {
					    	System.out.println(" i am not decrementing");
					    }else {
					    	LoadBufferArray[i].setlatency(latencyRemaining-1);
							Registers[LoadBufferArray[i].getFirstOperand()].setValue(LoadBufferArray[i].getTag());
							Registers[LoadBufferArray[i].getFirstOperand()].setBusy(1);
					    }
						

					}
					destinationsToExecute.add(destinationRegister);
			}
		}
//		
//		LDI R0 10 6
//		SW R0 500 6

		for(int i=0;i<StoreBufferArray.length;i++) {
			if(StoreBufferArray[i]!=null) {
				// we will have to cases either to wait for the v to be available or start decrementing 
				if(!StoreBufferArray[i].getQ().equals("")) {
				
				}else if(StoreBufferArray[i].getV()!=-1) {
					int LatencyRemaining=StoreBufferArray[i].getLatency();
					System.out.println(LatencyRemaining);
					if(LatencyRemaining==0) {
						int registerToLoadFrom=StoreBufferArray[i].getRegister();
						int address=StoreBufferArray[i].getAddress();
						executeStore(registerToLoadFrom,address);
						StoreBufferArray[i]=null;
					}else {
						StoreBufferArray[i].setLatency(LatencyRemaining-1);	
						
					}
				}
			}
			
		}
	}
	public static void executeMulDiv(String op,int destination,int vj,int vk,String tag,int k) {
		if(op.equals("MUL")) {
			
			int result=vj*vk;
			MultDivBufferArray[k].setA(Integer.toString(result));
			Registers[destination].setValue(Integer.toString(result));
			Registers[destination].setBusy(0);
			MultDivBufferArray[k].setA(result+"");
			for(int i=0;i<AddSubBufferArray.length   ;i++) {
				if(AddSubBufferArray[i]!=null) {
					if(AddSubBufferArray[i].getQj().equals(tag)) {
						AddSubBufferArray[i].setvj(result);
						AddSubBufferArray[i].setQj("");
					}
					if(AddSubBufferArray[i].getQk().equals(tag)) {
						AddSubBufferArray[i].setVk(result);
						AddSubBufferArray[i].setQk("");

					}
				}
			
			}
			for(int i=0;i<MultDivBufferArray.length  ;i++) {
				if(MultDivBufferArray[i]!=null) {
					if(MultDivBufferArray[i].getQj().equals(tag)) {
						MultDivBufferArray[i].setvj(result);
						MultDivBufferArray[i].setQj("");
					}
					if(MultDivBufferArray[i].getQk().equals(tag)) {
						MultDivBufferArray[i].setvj(result);
						MultDivBufferArray[i].setQk("");
					}
				}
			
			}
			
			for(int i=0;i<StoreBufferArray.length && StoreBufferArray[i]!=null;i++) {
				if(StoreBufferArray[i].getQ().equals(tag)) {
					StoreBufferArray[i].setV(result);
					StoreBufferArray[i].setQ("");
				}
			}
			//hena el divide
		}
		else {
			int result=vj/vk;
			MultDivBufferArray[k].setA(Integer.toString(result));
			Registers[destination].setValue(Integer.toString(result));
			Registers[destination].setBusy(0);
			MultDivBufferArray[k].setA(result+"");
			for(int i=0;i<AddSubBufferArray.length  ;i++) {
				if(AddSubBufferArray[i]!=null) {
					if(AddSubBufferArray[i].getQj().equals(tag)) {
						AddSubBufferArray[i].setvj(result);
						AddSubBufferArray[i].setQj("");
					}
					if(AddSubBufferArray[i].getQk().equals(tag)) {
						AddSubBufferArray[i].setvj(result);
						AddSubBufferArray[i].setQk("");
					}
				}
			}
			for(int i=0;i<MultDivBufferArray.length;i++) {
				if(MultDivBufferArray[i]!=null) {
					if(MultDivBufferArray[i].getQj().equals(tag)) {
						MultDivBufferArray[i].setvj(result);
						MultDivBufferArray[i].setQj("");
					}
					if(MultDivBufferArray[i].getQk().equals(tag)) {
						MultDivBufferArray[i].setvj(result);
						MultDivBufferArray[i].setQk("");
					}
				}
				
			}
			
			for(int i=0;i<StoreBufferArray.length && StoreBufferArray[i]!=null;i++) {
				if(StoreBufferArray[i]!=null) {
					if(StoreBufferArray[i].getQ().equals(tag)) {
						StoreBufferArray[i].setV(result);
						StoreBufferArray[i].setQ("");
					}
				}
				
			}
		}
		}
	
	
	public static void executeAddSub(String op,int destination,int vj,int vk,String tag,int k) {
		if(op.equals("ADD")) {
			int result=vj+vk;
			
			
			AddSubBufferArray[k].setA(Integer.toString(result));
			Registers[destination].setValue(Integer.toString(result));
			Registers[destination].setBusy(0);
			AddSubBufferArray[k].setA(result+"");
			//System.out.println(AddSubBufferArray[k].getA()+" ahe el a");
			for(int i=0;i<AddSubBufferArray.length  ;i++) {
				if(AddSubBufferArray[i]!=null) {
					if(AddSubBufferArray[i].getQj().equals(tag)) {
						AddSubBufferArray[i].setvj(result);
						AddSubBufferArray[i].setQj("");
					}
					if(AddSubBufferArray[i].getQk().equals(tag)) {
						AddSubBufferArray[i].setVk(result);
						AddSubBufferArray[i].setQk("");

					}
				}
				
			}
			for(int i=0;i<MultDivBufferArray.length;i++) {
				if(MultDivBufferArray[i]!=null) {
					if(MultDivBufferArray[i].getQj().equals(tag)) {
						MultDivBufferArray[i].setvj(result);
						MultDivBufferArray[i].setQj("");
					}
					if(MultDivBufferArray[i].getQk().equals(tag)) {
						MultDivBufferArray[i].setVk(result);
						MultDivBufferArray[i].setQk("");
					}
				}
				
			}
			
			for(int i=0;i<StoreBufferArray.length;i++) {
				if(StoreBufferArray[i]!=null) {
					if(StoreBufferArray[i].getQ().equals(tag)) {
						StoreBufferArray[i].setV(result);
						StoreBufferArray[i].setQ("");
					}
				}
				
			}
			//hena el subtract
		}else {
			int result=vj-vk;
			AddSubBufferArray[k].setA(Integer.toString(result));
			Registers[destination].setValue(Integer.toString(result));
			Registers[destination].setBusy(0);
			AddSubBufferArray[k].setA(result+"");
			for(int i=0;i<AddSubBufferArray.length && AddSubBufferArray[i]!=null ;i++) {
				if(AddSubBufferArray[i].getQj().equals(tag)) {
					AddSubBufferArray[i].setvj(result);
					AddSubBufferArray[i].setQj("");
				}
				if(AddSubBufferArray[i].getQk().equals(tag)) {
					AddSubBufferArray[i].setvj(result);
					AddSubBufferArray[i].setQk("");

				}
			}
			for(int i=0;i<MultDivBufferArray.length && MultDivBufferArray[i]!=null;i++) {
				if(MultDivBufferArray[i].getQj().equals(tag)) {
					MultDivBufferArray[i].setvj(result);
					MultDivBufferArray[i].setQj("");
				}
				if(MultDivBufferArray[i].getQk().equals(tag)) {
					MultDivBufferArray[i].setvj(result);
					MultDivBufferArray[i].setQk("");
				}
			}
			
			for(int i=0;i<StoreBufferArray.length && StoreBufferArray[i]!=null;i++) {
				if(StoreBufferArray[i].getQ().equals(tag)) {
					StoreBufferArray[i].setV(result);
					StoreBufferArray[i].setQ("");
				}
			}
		}
		}
		
	
	
	
	public static void executeStore(int register,int address) {
		System.out.println("The Register is "+register);
		System.out.println("The Address is "+address);
		System.out.println(Registers[0].getValue());
		int valueToStore=Integer.parseInt(Registers[register].getValue());
		DataMemory[address]=valueToStore;
		
		
		
	}


	public static void executeLoad(int firstOperand,int address,String tag) {
		Registers[firstOperand].setValue(Integer.toString(DataMemory[address]));
		
		for(int i=0;i<AddSubBufferArray.length  ;i++) {
			
			
			if(AddSubBufferArray[i]!=null) {
				if(AddSubBufferArray[i].getQj().equals(tag)) {
					AddSubBufferArray[i].setvj(DataMemory[address]);
					AddSubBufferArray[i].setQj("");
				}
				if(AddSubBufferArray[i].getQk().equals(tag)) {
					AddSubBufferArray[i].setVk(DataMemory[address]);
					AddSubBufferArray[i].setQk("");

				}
			}
			
		}
		for(int i=0;i<MultDivBufferArray.length  ;i++) {
			if(MultDivBufferArray[i]!=null) {
				if(MultDivBufferArray[i].getQj().equals(tag)) {
					MultDivBufferArray[i].setvj(DataMemory[address]);
					MultDivBufferArray[i].setQj("");

				}
				if(MultDivBufferArray[i].getQk().equals(tag)) {
					MultDivBufferArray[i].setVk(DataMemory[address]);
					MultDivBufferArray[i].setQk("");

				}
			}
			
		}
		
		for(int i=0;i<StoreBufferArray.length;i++) {
			if( StoreBufferArray[i]!=null) {
				if(StoreBufferArray[i].getQ().equals(tag)) {
					StoreBufferArray[i].setV(DataMemory[address]);
					StoreBufferArray[i].setQ("");
				}
			}
			
		}
		
	}
	

	
	public static void fillDataMemory() {
		for(int i=0;i<DataMemory.length;i++) {
			DataMemory[i]=i;
		}
	}
	public static int getMax(int x,int y,int z, int w) {
		
		if(x>=y && x>=z && x>=w) {
			return x;
		}else if (y>=x && y>=z && y>=w) {
			return y;
		}else if(z>=x && z>=y && z>=w) {
			return z;
		}
		else
			return w;
	}
	
	public static boolean checkActivity () {
		int loadCount=getLoadBufferCount();
		int addSubCount=getAddSubBufferCount();
		int mulDivCount=getMulDivBufferCount();
		int storeCount=getStoreBufferCount();
		int max=getMax(loadCount, storeCount, addSubCount, mulDivCount);
		
		if(max==0) {
			max=1;
		}
	
		// we want to get maximum beteen 3 numbers
		if(currentClockCycle>2) {
			for(int i=0;i<max;i++) {
				if(isEmpty(AddSubBufferArray, max) && isEmpty(MultDivBufferArray, max) && isEmpty(LoadBufferArray, max)&& isEmpty(StoreBufferArray, max))  {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean isEmpty(ReservationStations AddSubBufferArray[],int max) {
		max=getAddSubBufferCount();
		for(int i=0;i<AddSubBufferArray.length;i++) {
			if(AddSubBufferArray[i]!=null) {
				return false;
			}
		}
		return true;
		
	}

public static boolean isEmpty(LoadBuffers LoadBufferArray[],int max) {
	max=getLoadBufferCount();
	for(int i=0;i<LoadBufferArray.length;i++) {
		if(LoadBufferArray[i]!=null) {
			return false;
		}
	}
	
	return true;
	
}
public static boolean isEmpty(StoreBuffers StoreBufferArray[],int max) {

	for(int i=0;i<StoreBufferArray.length;i++) {
		if(StoreBufferArray[i]!=null) {
			return false;
		}
	}
	return true;
	
}

public static int findEmpty(ReservationStations arr[]) {
	for(int i=0;i<arr.length;i++) {
		if(arr[i]==null) {
			return i;
		}
	}
	return -1;
}


public static int findEmpty(StoreBuffers arr[]) {
	for(int i=0;i<arr.length;i++) {
		if(arr[i]==null) {
			return i;
		}
	}
	return -1;
}

public static int findEmpty(LoadBuffers arr[]) {
	for(int i=0;i<arr.length;i++) {
		if(arr[i]==null) {
			return i;
		}
	}
	return -1;
}


public static void printRegsiters() {
	for(int i=0;i<32;i++) {
		if(!Registers[i].getValue().equals("")) {
			System.out.println("R"+i+" value "+Registers[i].getValue());
		}
		
	}
}

public static void printMemory() {
	for(int i=0;i<InstructionMemory.length;i++) {
		System.out.println(InstructionMemory[i]);
	}
}

public static void printLoadBuffer() {
	System.out.println("LOAD BUFFER CONTENT");
	System.out.println("-----------------------");
	for(int i=0;i<LoadBufferArray.length;i++) {
		if(LoadBufferArray[i]!=null) {
			System.out.println("Tag "+LoadBufferArray[i].getTag()+" Address "+ LoadBufferArray[i].getAddress()+" Busy "+LoadBufferArray[i].getBusy()+" Latency "+LoadBufferArray[i].getlatency()
					+" First Operand "+LoadBufferArray[i].getFirstOperand());
		}else {
			System.out.println("NULL");
		}
	}
	System.out.println("-----------------------");
}





public static void issueStore(String instruction) {
	

	System.out.println(instruction);
	int	storeBufferCount=getStoreBufferCount();

	if(storeBufferCount<StoreBufferArray.length) {
		String substring[]= instruction.split(" ");
		int firstOperand=Integer.parseInt(substring[1].substring(1)+"");
		//System.out.println(firstOperand);
	
		// first operand is the register we want to get value from and save 


		int address=Integer.parseInt(substring[2]);
		// address operand is the location in the data memory we want to save in
		String tag="";
		int v=0;
		String q="";
		int latency=Integer.parseInt(substring[3]);
		if(Registers[firstOperand].getBusy()==1) {
	    q=Registers[firstOperand].getValue();
		}
		else {
			 v= Integer.parseInt(Registers[firstOperand].getValue());
		}
		
		if(findEmpty(StoreBufferArray)!=-1) {
			int index=findEmpty(StoreBufferArray);

			tag="S"+index;
			StoreBuffers instructionToAdd=new StoreBuffers(tag, 1, address, v, q,firstOperand,latency);
			StoreBufferArray[index]=instructionToAdd;
			storeIndex++;

		}
		printStoreBuffers();
	}
}


	public static void printStoreBuffers() {
		System.out.println("STORE BUFFER CONTENT");
		System.out.println("-----------------------");
		for(int i=0;i<StoreBufferArray.length;i++) {
			if(StoreBufferArray[i]!=null) {
				System.out.println("Tag "+StoreBufferArray[i].getTag()+" Address "+ StoreBufferArray[i].getAddress()+" Busy "+StoreBufferArray[i].getBusy()+" Latency "+StoreBufferArray[i].getLatency()
						+" Q Value "+StoreBufferArray[i].getQ()+" V value "+StoreBufferArray[i].getV());
			}else {
				System.out.println("NULL");
			}
		}
		System.out.println("-----------------------");
	}
	public static void readFile(String fileName) {
		  try {
		      File myObj = new File(fileName);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) { 
		        String data = myReader.nextLine();
		  
		        instructionCount++;
		        fillMemory(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		  }

	public static void issue(String instruction) {
		if(!instruction.equals("")) {
			System.out.println(instruction);
			String value[]=new String[3];
			value=instruction.split(" ");
			
			
			switch(value[0]) {
			
			case "ADD": 
				issueAddSub(instruction,value[0]);
			    break;
			case "SUB": 
				issueAddSub(instruction,value[0]);
				break;
			case "LDI": 
				issueLoad(instruction);
				break;
				
			case "SW": 
				issueStore(instruction) ;
				break;
			case "MUL": 
				issueMulDiv(instruction,value[0]);
				break;
			case "DIV": 
				issueMulDiv(instruction,value[0]); 
				break;
			}
		}
		
		
	}
	
	
	public static void issueAddSub(String instruction,String Operation) {
	int	addsubBufferCount=getAddSubBufferCount();
		if(addsubBufferCount<AddSubBufferArray.length) {
			
			String substring[]= instruction.split(" ");
			int firstOperand=Integer.parseInt(substring[2].substring(1)+"");
			int secondOperand=Integer.parseInt(substring[3].substring(1)+"");
			int destinationRegister=Integer.parseInt(substring[1].substring(1)+"");
			
			String op="";
			int vj = 0;
			int vk = 0;
			String qj = "";
			String qk = "";
			String tag="";
		
			int latency=Integer.parseInt(substring[4]);
			if(findEmpty(AddSubBufferArray)!=-1) {
				int index=findEmpty(AddSubBufferArray);
				tag="A"+index;
				
			}

			if(Operation.equals("ADD")) {
				
				op="ADD";
				if(Registers[firstOperand].getBusy()==1) {
					qj=Registers[firstOperand].getValue();

				}else {
					vj=Integer.parseInt(Registers[firstOperand].getValue());
				}	
				if(Registers[secondOperand].getBusy()==1) {
					qk=Registers[secondOperand].getValue();
				}else {
					vk=Integer.parseInt(Registers[secondOperand].getValue());	
				}
			}else {
				op="SUB";
				if(Registers[firstOperand].getBusy()==1) {
					qj=Registers[firstOperand].getValue();

				}else {
					vj=Integer.parseInt(Registers[firstOperand].getValue());
				}	
				if(Registers[secondOperand].getBusy()==1) {
					qk=Registers[secondOperand].getValue();
				}else {
					vk=Integer.parseInt(Registers[secondOperand].getValue());	
				}
			}
			
			
			if(findEmpty(AddSubBufferArray)!=-1) {
				int index=findEmpty(AddSubBufferArray);
				tag="A"+index;
				Registers[destinationRegister].setBusy(1);
				Registers[destinationRegister].setValue(tag);
				ReservationStations instructionToAdd=new ReservationStations(1, tag,op, vj, vk, qj, qk, "",destinationRegister,latency);
				//System.out.println(tag);
				AddSubBufferArray[index]=instructionToAdd;
			

			}
		}else {
			
			// we need to stop issuing maybe we will save this instruction for future 
			//but we need to check that the buffer is empty before we start issuing and if the buffer is not empty we need to save the instruction
			//we were about to issue in the buffer
		}
			
	}
	
	

	
	public static void issueMulDiv(String instruction,String Operation) {
		int	mulDivBufferCount=getMulDivBufferCount();
     if(mulDivBufferCount<MultDivBufferArray.length) {
			
			String substring[]= instruction.split(" ");
		
			int firstOperand=Integer.parseInt(substring[2].substring(1)+"");
			int secondOperand=Integer.parseInt(substring[3].substring(1)+"");
			int destinationRegister=Integer.parseInt(substring[1].substring(1)+"");

			String op="";
			int vj = 0;
			int vk = 0;
			String qj = "";
			String qk = "";
			String tag="";
		
			int latency=Integer.parseInt(substring[4]);
			if(findEmpty(MultDivBufferArray)!=-1) {
				int index=findEmpty(AddSubBufferArray);
				tag="A"+index;
			}

			if(Operation.equals("MUL")) {
				
				op="MUL";
				if(Registers[firstOperand].getBusy()==1) {
					qj=Registers[firstOperand].getValue();

				}else {
					vj=Integer.parseInt(Registers[firstOperand].getValue());
				}	
				if(Registers[secondOperand].getBusy()==1) {
					qk=Registers[secondOperand].getValue();
				}else {
					vk=Integer.parseInt(Registers[secondOperand].getValue());	
				}
			}else {
				op="DIV";
				if(Registers[firstOperand].getBusy()==1) {
					qj=Registers[firstOperand].getValue();

				}else {
					vj=Integer.parseInt(Registers[firstOperand].getValue());
				}	
				if(Registers[secondOperand].getBusy()==1) {
					qk=Registers[secondOperand].getValue();
				}else {
					vk=Integer.parseInt(Registers[secondOperand].getValue());	
				}
			}
			
			
			if(findEmpty(MultDivBufferArray)!=-1) {
				int index=findEmpty(MultDivBufferArray);
				tag="M"+index;
				Registers[destinationRegister].setBusy(1);
				Registers[destinationRegister].setValue(tag);
				ReservationStations instructionToAdd=new ReservationStations(1, tag,op, vj, vk, qj, qk, "",destinationRegister,latency);
				//System.out.println(tag);
				MultDivBufferArray[index]=instructionToAdd;
				MulDivIndex++;

			}
		}
			
	}
	
	
	public static void issueLoad(String instruction) {
		int	loadBufferCount=getLoadBufferCount();
		if(loadBufferCount<LoadBufferArray.length) {
			String substring[]= instruction.split(" ");
			int firstOperand=Integer.parseInt(substring[1].substring(1)+"");
			int address=Integer.parseInt(substring[2]);
			int latency=Integer.parseInt(substring[3]);
			String tag="";
			if(findEmpty(LoadBufferArray)!=-1) {
				int index=findEmpty(LoadBufferArray);
	
				tag="L"+index;
				LoadBuffers instructionToAdd=new LoadBuffers(tag, 1, address,latency,firstOperand);
				LoadBufferArray[index]=instructionToAdd;
				loadIndex++;
			}
		}
	}
	

	
	public static void fillMemory(String data) {
		InstructionMemory[InstructionMemoryCounter]=data;
		InstructionMemoryCounter++;
	}

	public static void createRegisters() {
		for(int i=0;i<32;i++) {
			RegisterFile register=new RegisterFile(0, i, "");
			Registers[i]=register;
		}
	}
	
	
	

	public static void main(String[] args) throws IOException {
		intiate();
		
	}
	
}