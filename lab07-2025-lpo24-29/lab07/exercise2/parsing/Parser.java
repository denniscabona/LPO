package lab07.exercise2.parsing;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.security.Identity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lab07.exercise2.checking.FunName;
import lab07.exercise2.checking.InstructionInterface;
import lab07.exercise2.checking.InstructionRecord;
import lab07.exercise2.checking.VarName;
import static lab07.exercise2.parsing.Instruction.*;

enum Instruction{
	CONST_DEC,
	VAR_DEC,
	FUN_DEC,
	CALL,
	READ,
	WRITE
}

public class Parser implements ParserInterface {
	private static final String regExp;
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
	    this.reader = new LineNumberReader(reader); // creazione di reader secondo il parametro reader
	}

	public InstructionInterface readNext() throws IOException {
	    // completare usando i metodi reset(), matches() e group() dell'oggetto matcher
	    // il metodo readLine() dell'oggetto reader
	    // e i costruttori di FunName, InstructionRecord e 
		var line = reader.readLine(); // salvataggio della riga attuale di reader
		if(line == null) // se la riga è vuota non ne legge più
			return null;
		matcher.reset(line); // pulizia del matcher dalla riga precedente e inserimento di quella nuova
		if(!matcher.matches()) // controllo che tutta la riga faccia parte della regex
			throw new SyntaxError(reader.getLineNumber());
		// dalla riga viene ricavato il suo numero, l'identificatore e l'istruzione (quest'ultima viene associata all'enum)
		var identifier = matcher.group("IDENT"); // salvataggio dell'identificatore presente nella riga
		var lineNumber = reader.getLineNumber(); // salvataggio del numero della riga attuale
		return switch (matcher.group("INSTR")){ // ritorno di un InstructionRecord in base all'istruzione
			case "const" -> new InstructionRecord(new VarName(identifier), true, lineNumber); // true perché const, val e fun
			case "var" -> new InstructionRecord(new VarName(identifier), true, lineNumber); // non possono essere ridichiarate
			case "fun" -> new InstructionRecord(new FunName(identifier), true, lineNumber);
			case "call" -> new InstructionRecord(new FunName(identifier), false, lineNumber);
			case "read" -> new InstructionRecord(new VarName(identifier), false, lineNumber);
			case "write" -> new InstructionRecord(new VarName(identifier), false, lineNumber);
			default -> null;
		};
	}
}
