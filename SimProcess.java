public class SimProcess {
    int pid;
    String procName;
    int totalInstructions;

    SimProcess(int pid, String procName, int totalInstructions){
        this.pid = pid;
        this.procName = procName;
        this.totalInstructions = totalInstructions;
    }

    public ProcessState execute(int i){

        if(i >= totalInstructions){
            return ProcessState.FINISHED;
        }
        else{
            //We only need to print if it is not finished.
            System.out.println("pid: " + pid + ", name " + procName + " instruction number: " + i);
            double x = Math.random();
            if(x < 0.15){
                return ProcessState.BLOCKED;
            }
            else{
                return ProcessState.READY; //not sure if it is really supposed to return this
            }
        }

    }


}


/**
 *
 * SimProcess class. This is a class that simulates a process. It has an int pid, a String procName,
 * and an int totalInstructions.
 * o The constructor takes the pid, procName, and an int representing the total instructions.
 * o execute(int i) works as follows:
 * ▪ It displays a message to the screen with the pid, the name, and the instruction
 * number being executed
 * ▪ if i is greater than or equal to the total instructions it returns FINISHED
 * ▪ otherwise the process blocks with 15% probability (and returns BLOCKED)
 * ▪ if it does not block, it remains READY
 *
 */
