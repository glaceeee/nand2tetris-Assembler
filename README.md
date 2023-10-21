# nand2tetris-Assembler
The nand2tetris assembler.
Also refer to the first commit message. This one should be somewhat bug free cause its also relatively simple. But again whose to say.
- Main.java creates all the necessary things and handles the whole translation process. As input it gets ONE .asm file.
- Parser.java reads all the necessary info from one command
- Code.java uses SymbolTable.java and its own lookupTable to look up how to translate the given command, that was parsed by Parser.java into binary.
- SymbolTable.java is a lookup table used to look up predefined values like "LCL" or "ARG" and to keep track of user created assembly level variables (which are, in the context of the Jack language, used as static variables)

ok byeee
