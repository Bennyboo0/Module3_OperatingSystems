import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int QUANTUM = 5;
        SimProcessor processor = new SimProcessor();
        Random random = new Random();

        SimProcess[] processes = new SimProcess[10];
        ProcessControlBlock[] pcbs = new ProcessControlBlock[10];

        int[] instructionCounts = {100, 200, 300, 400, 150, 250, 350, 120, 220, 320};

        for(int i = 0; i < 10; i++) {
            processes[i] = new SimProcess(i + 1, "Proc" + (i + 1), instructionCounts[i]);
            pcbs[i] = new ProcessControlBlock(processes[i]);
        }

        Queue<ProcessControlBlock> readyQueue = new LinkedList<>();
        Queue<ProcessControlBlock> blockedQueue = new LinkedList<>();

        for(int i = 0; i < 10; i++) {
            readyQueue.add(pcbs[i]);
        }

        int instructionsExecuted = 0;

        for(int step = 1; step <= 3000; step++) { //main loop

            if(processor.getCurrentProcess() == null) { //Otherwise the process remains on the processor and the next
                // iteration of the loop will execute its next
                instruction.
                if(!readyQueue.isEmpty()) {
                    ProcessControlBlock nextPCB = readyQueue.poll();

                    System.out.println("Step " + step + " SYS CONTX SWTCH : Restoring process: "
                            + nextPCB.getSimProcess().pid);
                    System.out.println("Instruction: " + nextPCB.getCurrInstruction()
                            + " - R1: " + nextPCB.getRegister0val()
                            + ", R2: " + nextPCB.getRegister1val()
                            + ", R3: " + nextPCB.getRegister2val()
                            + ", R4: " + nextPCB.getRegister3val());

                    processor.setCurrentProcess(nextPCB.getSimProcess());
                    processor.setCurrInstruction(nextPCB.getCurrInstruction());
                    processor.setRegister0(nextPCB.getRegister0val());
                    processor.setRegister1(nextPCB.getRegister1val());
                    processor.setRegister2(nextPCB.getRegister2val());
                    processor.setRegister3(nextPCB.getRegister3val());

                    instructionsExecuted = 0;  //resets the quantum
                } else {
                    System.out.println("Step " + step + " SysIdle Notification: No ready processes available");
                }
            } else { //if the code goes here we need to do a context switch
                System.out.print("Step " + step + " ");
                ProcessState result = processor.executeNextInstruction();
                instructionsExecuted++;

                if(result == ProcessState.FINISHED) {//The process has finished, in this case it does not run again
                    System.out.println("*** Process completed ***");

                    System.out.println("Step " + step + " SYS CONTX SWTCH : Saving process: "
                            + processor.getCurrentProcess().pid);

                    processor.setCurrentProcess(null);
                    instructionsExecuted = 0;

                }
                else if(result == ProcessState.BLOCKED) { //The process has blocked, in this case it is put on the
                    // blocked list and is not run again until it is
                    ready
                    System.out.println("*** Process blocked ***");

                    ProcessControlBlock currentPCB = findPCB(pcbs, processor.getCurrentProcess());
                    System.out.println("Step " + step + " SYS CONTX SWTCH : Saving process: "
                            + processor.getCurrentProcess().pid);
                    System.out.println("Instruction: " + processor.getCurrInstruction()
                            + " - R1: " + processor.getRegister0Value()
                            + ", R2: " + processor.getRegister1Value()
                            + ", R3: " + processor.getRegister2Value()
                            + ", R4: " + processor.getRegister3Value());

                    currentPCB.setCurrInstruction(processor.getCurrInstruction());
                    currentPCB.setRegister0val(processor.getRegister0Value());
                    currentPCB.setRegister1val(processor.getRegister1Value());
                    currentPCB.setRegister2val(processor.getRegister2Value());
                    currentPCB.setRegister3val(processor.getRegister3Value());

                    blockedQueue.add(currentPCB);

                    processor.setCurrentProcess(null);
                    instructionsExecuted = 0;

                }
                else if(instructionsExecuted >= QUANTUM) { //The process has run for a full quantum, in this case it
                    // goes back on the ready list
                    System.out.println("*** Quantum expired ***");

                    ProcessControlBlock currentPCB = findPCB(pcbs, processor.getCurrentProcess());
                    System.out.println("Step " + step + " SYS CONTX SWTCH : Saving process: "
                            + processor.getCurrentProcess().pid);
                    System.out.println("Instruction: " + processor.getCurrInstruction()
                            + " - R1: " + processor.getRegister0Value()
                            + ", R2: " + processor.getRegister1Value()
                            + ", R3: " + processor.getRegister2Value()
                            + ", R4: " + processor.getRegister3Value());

                    currentPCB.setCurrInstruction(processor.getCurrInstruction());
                    currentPCB.setRegister0val(processor.getRegister0Value());
                    currentPCB.setRegister1val(processor.getRegister1Value());
                    currentPCB.setRegister2val(processor.getRegister2Value());
                    currentPCB.setRegister3val(processor.getRegister3Value());

                    readyQueue.add(currentPCB);

                    processor.setCurrentProcess(null);
                    instructionsExecuted = 0;
                }
            }
            /**
             * 
             * After performing a step, regardless of whether it is an instruction execution or a context switch, you
             * should loop through all of the blocked processes and for each one, wake it up with 30% probability. This
             * loop should run ONLY ONCE per iteration of the main loop, even if there is no ready process and the
             * processor is idling.
             *
             */
            Queue<ProcessControlBlock> stillBlocked = new LinkedList<>();
            while(!blockedQueue.isEmpty()) {
                ProcessControlBlock blockedPCB = blockedQueue.poll();
                if(random.nextDouble() < 0.30) {
                    readyQueue.add(blockedPCB);
                } else {
                    stillBlocked.add(blockedPCB);
                }
            }
            blockedQueue.addAll(stillBlocked);
        }

        System.out.println("\nSimulation complete!");
    }

    private static ProcessControlBlock findPCB(ProcessControlBlock[] pcbs, SimProcess process) { //this is the helper method
        for(ProcessControlBlock pcb : pcbs) {
            if(pcb.getSimProcess() == process) {
                return pcb;
            }
        }
        return null;
    }
}
