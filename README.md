# Microprocessors - Tomasulo Algorithm
The main focus of this project is to simulate The Tomasulo Architecture used in MicroProcessors by accepting inputs (MIPS instructions in assembly format) and show step by step how these instructions are executed as well as the content of each reservation station/buffer, the register file and the queue.).

## Code Structure


1. **Instruction Queue :** *This class was mainly used in Saving the instructions
we are fetching in order to be able to issue them in future*
2. **LoadBuffers :** *This class was mainly used in saving Load Instructions in the
buffer in order to start executing them according to the Tomasulu Algorithm*
3. **StoreBuffers :** *: This class was mainly used in saving Store Instructions in the
buffer in order to start executing them according to the Tomasulu Algorithm*
4. **RegisterFile :** *This class was mainly used in Creating our Registers Structure
In order to be able to save values in them*
5. **ReservationStations :** *This class was mainly used in saving ADD/SUB/MUL/
DIV Instructions in the buffer in order to start executing them according to
the Tomasulu Algorithm*
6. **Project :** *This class is used in order to handle the Execution of the Tomasulu
Algorithm and Handle All Cases that may occur while executing the
Algorithm*
7. **Program1.txt :** *This is the file used to add instructions in it and execute them
using Tomasulu Algorithm*

## Test Cases

**Test Case 1: (WAW)**

LDI R1 500 8

LDI R1 505 5

**Test Case 2: (RAW)**

ADD R0 R1 R2 5

ADD R3 R0 R5 5

**Test Case 3: (WAR)**

ADD R0 R1 R2 5

ADD R4 R3 R6 5


**Test Case 4: (Writing to Same Register)**
ADD R0 R0 R1 5

ADD R1 R1 R1 5


## Approach :

**Clock Cycle 1:**
*The code starts by calling the Initiator Method which will kickoff start the
Algorithm
This Method keeps going in an infinite loop increasing number of clock
cycles every iteration until we return from it if we found that All the buffers
are empty and there’s nothing else to execute
At first we check if the clock cycles is 1 then we Fetch the first instruction
from the Instruction Memory and then we Issue this instruction based on it’s
type
If the instruction is ADD OR SUB we directly go to the ADD/SUB Issue
instruction and the same goes for all instructions based on their type*


**Other Clock Cycles:**
*The code continue and call the Optimizer Method which starts looping
through all buffers and check if the latency of the current Element in the
buffer is 0 Then we go to execute functions based on their type
For example if the Operation is add or sub we go the executeaddsub
method and the same goes for all instructions
And in case if their latencies is not yet 0 we check if we can decrement the
latency value so if we can , we proceed and decrement the latencies till it
reaches 0*


## Syntax for Instructions

**ADD R0 R1 R2 5 :**  *Adding values R1 and R2 and saving them in R0 . The latency of this instruction is 5 Cycles*

**SUB R0 R1 R2 4 :** *Subtracting values R1 and R2 and saving them in R0 . The latency of this instruction is 4 Cycles*

**MUL R0 R1 R2 6 :** *Multiplying values R1 and R2 and saving them in R0 . The latency of this instruction is 6 Cycles*

**DIV R0 R1 R2 6 :** *Divide values R1 and R2 and saving them in R0 . The latency of this instruction is 6 Cycles*

**LDI R0 500 4 :** *Loading Values of Memory[500] into R0 . The latency is 4 Cycles*

**SW R0 100 5 :** *Storing values of R0 into Location Memory[100] . The latency of this instruction is 5 Cycles*


