package lab07.exercise1.parsing;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lab07.exercise2.checking.FunName;
import lab07.exercise2.checking.InstructionRecord;
import lab07.exercise2.checking.VarName;
import static lab07.exercise1.parsing.Instruction.*;

enum Instruction{
	CONST_DEC,
	VAR_DEC,
	FUN_DEC,
	CALL,
	READ,
	WRITE
}
public class Parser implements ParserInterface {
	private static final String regExp;// completare con la definizione di regExp
	private final LineNumberReader reader;
	private final Matcher matcher = Pattern.compile(regExp).matcher("");

	static {
		String opName ="(?<INSTR>const|var|fun|call|read|write)"; // ?<NOME> do un nome al gruppo catturante, utile per dopo
		String space = "\\s*";
		String separator = "\\s+";
		String opIdentifier = "(?<IDENT>[a-zA-Z]\\w*)";
		String comment = "(?://.*)?";
		regExp = space + opName + separator + opIdentifier + space + comment;
	}

	public Parser(Reader reader) {
	    this.reader = new LineNumberReader(reader);
	}

	public boolean readNext() throws IOException {
	    // completare usando i metodi reset(), matches() e group() dell'oggetto matcher
	    // e il metodo readLine() dell'oggetto reader
		var line = reader.readLine(); // prendo la riga di reader
		if(line == null)
			return false;
		matcher.reset(line); // pulisco il matcher dalla riga precedente ci inserisco quella nuova
		if(!matcher.matches()) // controllo che tutta la riga faccia parte della regex
			throw new SyntaxError(reader.getLineNumber());
		// dalla riga mi ricavo suo numero, l'identificatore e l'istruzione (associo quest'ultima all'enum)
		var identifier = matcher.group("IDENT");
		var lineNumber = reader.getLineNumber();
		var instructions = switch (matcher.group("INSTR")){
			case "const" -> CONST_DEC;
			case "var" -> VAR_DEC;
			case "fun" -> FUN_DEC;
			case "call" -> CALL;
			case "read" -> READ;
			case "write" -> WRITE;
			default -> throw new SyntaxError(lineNumber);
		};
		System.out.println("linea: " + lineNumber + " istruzione: " + instructions + " identificatore: " + identifier);
		return true;
	}
}
