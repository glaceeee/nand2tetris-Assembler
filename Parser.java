package assembler;

public class Parser {
	//Turns a certain assembly command into its destination, computation and jump fields
	//e.g.: AMD=M+D;JEQ => dest() -> "AMD"; comp() -> "M+D"; jump() -> "JEQ" (null if nonexistent)
	private static int currentChar = 0;
	private static int newVariableMemLoc = 16;
	Parser(){};
	
	//returns the value after the '@' in an A-instruction in int
	public static int value(String instruction){
		//scan everything after the '@' and put it in a string which we then convert to int
		StringBuilder valueSB = new StringBuilder();
		char[] instructionChars = instruction.toCharArray();
		for(currentChar = 1; currentChar < instruction.length(); currentChar++) {
			if(instructionChars[currentChar]==' ') {
				currentChar = 0;
				break;
			}
			if(instructionChars[currentChar]=='/') {
				currentChar = 0;
				break;
			}
			valueSB.append(instructionChars[currentChar]);
		}
		//if its @certainInteger just do parseInt() on that
		//if not look up the value
		//if there is no value, create a new variable starting at memory location 16
		try {
			String valueS = valueSB.toString();
			return (int)(Integer.parseInt(valueS));
		} catch (NumberFormatException e) {
			if(SymbolTable.get(valueSB.toString()) != null) {
				return (int)(Integer.parseInt(SymbolTable.get(valueSB.toString())));
			}
			else {
				SymbolTable.add(valueSB.toString(),((Integer)newVariableMemLoc).toString());
				newVariableMemLoc++;
				return newVariableMemLoc-1;
			}
		}
	}
	
	//returns the substring representing the destination field
	public static String dest(String instruction) {
		currentChar = 0;
		//Scan instruction until we hit "=" then return that substring
		char[] instructionChars = instruction.toCharArray();
		StringBuilder destSB = new StringBuilder();
		while(instructionChars[currentChar]!='=') {
			destSB.append(instructionChars[currentChar]);
			currentChar++;
			if(currentChar == ';') {
				currentChar = 0;
				return "null";
			}
			else if(currentChar == instruction.length()) {
				currentChar = 0;
				return "null";
			}
		}
		currentChar = 0;
		return destSB.toString();
	}
	
	//returns the substring representing the computation field
	public static String comp(String instruction) {
		currentChar = 0;
		//Scan until we hit "="
		char[] instructionChars = instruction.toCharArray();
		if(instruction.contains("=")) {
			while(instructionChars[currentChar]!='=') {
				currentChar++;
			}
			currentChar++;
		}

		//keep scanning instruction until we're at the end of the string or we hit ";"
		StringBuilder compSB = new StringBuilder();
		while(currentChar < instructionChars.length) {
			if(instructionChars[currentChar]==' ') {break;}
			if(instructionChars[currentChar]=='/') {break;}
			if(instructionChars[currentChar]==';') {break;}
			compSB.append(instructionChars[currentChar]);
			currentChar++;
		}
		currentChar = 0;
		return compSB.toString();
	}
	
	//returns the substring representing the jump field
	public static String jump(String instruction) {
		currentChar = 0;
		char[] instructionChars = instruction.toCharArray();
		while(true) {
			if(currentChar == instruction.length()) {
				currentChar = 0;
				return "null";
			}
				
			if(instructionChars[currentChar] == ';') {
				break;
			}
			
			else {
				currentChar++;
			}
		}
		currentChar++;
		StringBuilder jumpSB = new StringBuilder();
		while(currentChar < instructionChars.length){
			if(instructionChars[currentChar]==' ') {
				currentChar = 0;
				break;
			}
			
			if(instructionChars[currentChar]=='/') {
				currentChar = 0;
				break;
			}
			
			jumpSB.append(instructionChars[currentChar]);
			currentChar++;
		}
		currentChar = 0;
		return jumpSB.toString();
	}
}
