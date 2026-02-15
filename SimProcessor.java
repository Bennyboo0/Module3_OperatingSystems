import java.util.Random;

public class SimProcessor {
    private SimProcess currentProcess;

    private int register0;
    private int register1;
    private int register2;
    private int register3;

    private int currInstruction;

    Random random = new Random();

    public SimProcess getCurrentProcess(){
        return currentProcess;
    }

    public void setCurrentProcess(SimProcess process){
        currentProcess = process;
    }

    public int getRegister0Value(){
        return register0;
    }
    public int getRegister1Value(){
        return register1;
    }
    public int getRegister2Value(){
        return register2;
    }
    public int getRegister3Value(){
        return register3;
    }

    public void setRegister0(int value){
        register0 = value;
    }
    public void setRegister1(int value){
        register1 = value;
    }
    public void setRegister2(int value){
        register2 = value;
    }
    public void setRegister3(int value){
        register3 = value;
    }

    public int getCurrInstruction(){
        return currInstruction;
    }

    public void setCurrInstruction(int value){
        currInstruction = value;
    }

    public ProcessState executeNextInstruction(){
        int theCurrInstruction = getCurrInstruction();
        ProcessState result = currentProcess.execute(theCurrInstruction);
        setCurrInstruction(theCurrInstruction + 1);

        //this sets the registers to random values anywhere within
        //java's max range of values -- I'm not sure if this is too large of a range.
        setRegister0(random.nextInt());
        setRegister1(random.nextInt());
        setRegister2(random.nextInt());
        setRegister3(random.nextInt());

        return result;
    }


}

/**
 *
 * SimProcessor class. It has:
 * o A reference to a SimProcess that is the current process, with a getter and a setter
 * o 4 int values to represent four different registers
 * ▪ These should be set with setRegisterValue method(s) and returned with
 * getRegisterValues method(s). You can have four separate getters and setters or
 * have one method for setting and one for getting that take an int as a parameter.
 * o An int, currInstruction, that represents the current instruction, with a getter and setter
 * o An executeNextInstruction() method that calls the execute method of the current
 * process, passing in the value of currInstruction, increments currInstruction, and returns
 * the result of the execute method. Before returning, it sets all 4 registers to randomly
 * generated values to simulate the resulting state of the instruction's execution.
 *
 */
