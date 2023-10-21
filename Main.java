package assembler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
	
	//translates one instruction into binary
	private static String translate(String instruction) {
		StringBuilder instructionBin = new StringBuilder();
		//A-instruction
		if(instruction.toCharArray()[0]=='@') {
			instructionBin.append("0");
			instructionBin.append(Code.value(Parser.value(instruction)));
			return instructionBin.toString();
		}
		//C-instruction
		else {
			instructionBin.append("111");
			instructionBin.append(Code.comp(Parser.comp(instruction)));
			instructionBin.append(Code.dest(Parser.dest(instruction)));
			instructionBin.append(Code.jump(Parser.jump(instruction)));
			return instructionBin.toString();
		}
	}
	
	//executes firstPass, which just looks out for label declarations and 
	//enters said labels, alongside their respective values, into the symbol table
	private static void firstPass(String input) {
		//need try-catch block because FileNotFoundException is a run-time error (happens when machine code is executing)
		try {
			BufferedReader br = new BufferedReader(new FileReader(input));
			String line;
			//keeps track of current line (ignores white-space)
			int currentInstruction = 0;
			while((line = br.readLine()) != null) {
				//ignore whitespace
				if(line.replace(" ", "").replace("\t", "").startsWith("//") || line.replace(" ","").replace("\t","").isEmpty()) {
					continue;
				}					
				//if (LABEL) detected, enter it into the symbol table with its value being the line that follows it
				//(even if that's a comment, which doesn't matter since those are ignored by the assembler anyways)
				if((line.replace(" ","").replace("\t", "")).startsWith("(")) {
					char[] lineChars = line.toCharArray();
					StringBuilder sb = new StringBuilder();
					int i = 0;
					while(true) {
						if(i == line.length()) {
							break;
						}
						else if(lineChars[i] == '/') {
							break;
						}
						else {
							sb.append(((Character)lineChars[i]).toString());
							i++;
						}
					}
					String label = sb.toString(); 
					sb = null;
					SymbolTable.add(label.replace("(", "").replace(")","").replace(" ","").replace("\t", ""), ((Integer)currentInstruction).toString());
					continue;
				}
				else {
					currentInstruction++;
				}
				
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void secondPass(String input, String output) {
		//need try-catch block because FileNotFoundException is a run-time error (happens when machine code is executing)
		try {
			//read line (buffered reader reads entire line), then translate it and write it to the specified output file
			FileWriter writer = new FileWriter(output);
		    BufferedReader br = new BufferedReader(new FileReader(input));
		    String line;
		    while ((line = br.readLine()) != null) {
		    	//ignore whitespace (space symbols, tabulator symbols, empty lines and comments) and label declarations
		    	if(line.replace(" ","").replace("\t", "").startsWith("//") || line.replace(" ", "").replace("\t", "").isEmpty() || line.replace(" ", "").replace("\t", "").startsWith("(")){
		    		continue;
		    	}
		    	writer.write(translate(line.replace(" ", "").replace("\t", "")));
		    	writer.write("\n");
		    }
		    br.close();
		    writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	private static void assemble(String input, String output) {
		firstPass(input);
		secondPass(input,output);
	}
	 
	public static void main(String[] args) {
		assemble(args[0],args[1]);
	}
}