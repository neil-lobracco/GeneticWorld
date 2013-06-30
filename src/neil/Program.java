package neil;

import java.util.ArrayList;
import java.util.List;

public class Program
{
	Chromosome chrome;
	int numInstructions;
	double[] regs = new double[5];
	List<Instruction> instructions;
	int nextInstruction;
	
	static class Instruction
	{
		@Override
		public String toString()
		{
			switch (type)
			{
			case SETI:
				return "SETI r["+reg1+"]="+immediate;
			case SET:
				return "SET r["+reg1+"]=reg["+reg2+"]";
			case JMP:
				return "JMP "+immediate;
			case JMPIF:
				return "JMPIF "+immediate+", reg["+reg1+"]";
			case ADD:
				return "ADD reg["+reg1+"]+=reg["+reg2+"]";
			case SUB:
				return "SUB reg["+reg1+"]-=reg["+reg2+"]";
			case DIV:
				return "DIV reg["+reg1+"]/=reg["+reg2+"]";
			case MULT:
				return "MULT reg["+reg1+"]*=reg["+reg2+"]";
			case NOOP:
				return "NOOP";
			case END:
				return "END";
			default:
				return "UNKNOWN";
			}
		}
		enum InstructionType
		{
			SETI, //sets register reg1 to immediate
			SET,  //sets register reg1 to reg2
			JMP,  //jmps to offset immediate
			JMPIF,//jmps if reg1 >= 0
			ADD,  //adds reg2 to reg1
			SUB,  //subs reg2 from reg1
			DIV,  //divs reg1 by reg2
			MULT, //mults reg1 by reg2
			NOOP, //no-op
			END   //terminates
		}
		InstructionType type;
		int reg1, reg2;
		int immediate;
	}

	public Program(Chromosome c)
	{
		chrome = c;
	}
	public void execute(int maxInst)
	{
		for (int i=0;i<regs.length;i++)
			regs[i] = 0;
		instructions = getInstructions();
		numInstructions=0;
		nextInstruction=0;
		boolean terminate = false;
		while (!terminate)
		{
			numInstructions++;
			if (numInstructions > maxInst || nextInstruction >= instructions.size())
				return;
			Instruction i = instructions.get(nextInstruction++);
			switch (i.type)
			{
			case SETI:
				regs[i.reg1] = i.immediate;
				break;
			case SET:
				regs[i.reg1] = regs[i.reg2];
				break;
			case JMPIF:
				if (regs[i.reg1] < 0)
					break;
			case JMP:
				int nxt = nextInstruction + i.immediate;
				if (nxt >= 0 && nxt < instructions.size())
					nextInstruction = nxt;
				break;
			case ADD:
				regs[i.reg1] += regs[i.reg2];
				break;
			case SUB:
				regs[i.reg1] -= regs[i.reg2];
				break;
			case DIV:
				regs[i.reg1] /= regs[i.reg2];
				break;
			case MULT:
				regs[i.reg1] *= regs[i.reg2];
				break;
			case NOOP:
				break;
			case END:
				terminate = true;
				break;
			default:
				throw new Error();
			}
		}
	}
	@Override
	public String toString()
	{
		if (instructions == null)
			instructions = getInstructions();
		StringBuffer sb = new StringBuffer("Program:");
		for (int i=0;i<instructions.size();i++)
		{
			sb.append("\n");
			sb.append(instructions.get(i));
		}
		return sb.toString();
	}
	public int getNumInstructions()
	{
		return numInstructions;
	}
	public List<Instruction> getInstructions()
	{
		List<Instruction> l = new ArrayList<Instruction>();
		int[] seq = chrome.getSequence();
		for (int i = 0; i < (seq.length - 2); i+=3)
		{
			int num1 = seq[i];
			int num2 = seq[i+1];
			int num3 = seq[i+2];
			Instruction next = new Instruction();
			switch (num1 % Instruction.InstructionType.values().length)
			{
			case 0:
				next.type = Instruction.InstructionType.SETI;
				next.reg1 = num2 % regs.length;
				next.immediate = num3 % 10;
				break;
			case 1:
				next.type = Instruction.InstructionType.SET;
				next.reg1 = num2 % regs.length;
				next.reg2 = num3 % regs.length;
				break;
			case 2:
				next.type = Instruction.InstructionType.JMP;
				next.immediate = (num2 % (seq.length / 3)) - l.size();
				break;
			case 3:
				next.type = Instruction.InstructionType.JMPIF;
				next.immediate = (num2 % (seq.length / 3)) - l.size();
				next.reg2 = num3 % regs.length;
				break;
			case 4:
				next.type = Instruction.InstructionType.ADD;
				next.reg1 = num2 % regs.length;
				next.reg2 = num3 % regs.length;
				break;
			case 5:
				next.type = Instruction.InstructionType.SUB;
				next.reg1 = num2 % regs.length;
				next.reg2 = num3 % regs.length;
				break;
			case 6:
				next.type = Instruction.InstructionType.DIV;
				next.reg1 = num2 % regs.length;
				next.reg2 = num3 % regs.length;
				break;
			case 7:
				next.type = Instruction.InstructionType.MULT;
				next.reg1 = num2 % regs.length;
				next.reg2 = num3 % regs.length;
				break;
			case 8:
				next.type = Instruction.InstructionType.NOOP;
				break;
			case 9:
				next.type = Instruction.InstructionType.END;
				break;
			}
			l.add(next);
		}
		return l;
	}
	public double getFinalValue()
	{
		return regs[0];
	}
	
}
