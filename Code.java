package assembler;
import java.util.HashMap;

public class Code {
	
	private static HashMap<String,String> destTable = new HashMap<String,String>();
	private static HashMap<String,String> compTable = new HashMap<String,String>();
	private static HashMap<String,String> jumpTable = new HashMap<String,String>();
	
	static{
		//setup destTable
		destTable.put("null","000");
		destTable.put("M","001");
		destTable.put("D","010");
		destTable.put("MD","011");
		destTable.put("A","100");
		destTable.put("AM","101");
		destTable.put("AD","110");
		destTable.put("AMD","111");
		
		//setup compTable
		compTable.put("0","101010");
		compTable.put("1","111111");
		compTable.put("-1","111010");
		compTable.put("D","001100");
		compTable.put("A","110000"); compTable.put("M","110000");
		compTable.put("!D","001101");
		compTable.put("!A","110001"); compTable.put("!M","110001");
		compTable.put("-D","001111");
		compTable.put("-A","110011"); compTable.put("-M","110011");
		compTable.put("D+1","011111");
		compTable.put("A+1","110111"); compTable.put("M+1","110111");
		compTable.put("D-1","001110");
		compTable.put("A-1","110010"); compTable.put("M-1","110010"); 
		compTable.put("D+A","000010"); compTable.put("D+M","000010");
		compTable.put("A+D","000010"); compTable.put("M+D","000010"); //not specified but for quality of life
		compTable.put("D-A","010011"); compTable.put("D-M","010011");
		compTable.put("A-D","000111"); compTable.put("M-D","000111");
		compTable.put("D&A","000000"); compTable.put("D&M","000000");
		compTable.put("A&D","000000"); compTable.put("M&D","000000"); //not specified but for quality of life
		compTable.put("D|A","010101"); compTable.put("D|M","010101");
		compTable.put("A|D","010101"); compTable.put("M|D","010101"); //not specified but for quality of life
		
		//setup jumpTable
		jumpTable.put("null","000");
		jumpTable.put("JGT","001");
		jumpTable.put("JEQ","010");
		jumpTable.put("JGE","011");
		jumpTable.put("JLT","100");
		jumpTable.put("JNE","101");
		jumpTable.put("JLE","110");
		jumpTable.put("JMP","111");
	};
	
	public static String dest(String destSymbol) {
		return destTable.get(destSymbol);
	}
	
	public static String comp(String compSymbol) {
		StringBuilder compSB = new StringBuilder();
		//a=0 or a=1?
		if(compSymbol.contains("M")) {
			compSB.append("1");
		}
		else {
			compSB.append("0");
		}
		//append ALU input pin values
		compSB.append(compTable.get(compSymbol));
		return compSB.toString();
	}
	
	public static String jump(String jumpSymbol) {
		return jumpTable.get(jumpSymbol);
	}
	
	public static String value(int value) {
		String valueBin = String.format("%15s",Integer.toBinaryString(value)).replace(" ","0");
		return valueBin;
	}
}
