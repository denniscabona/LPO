package lab07.exercise2.checking;

public record InstructionRecord(NamedElementInterface namedElement, boolean isDeclaration, int lineNumber)
		implements InstructionInterface {

	@Override
	public void check(EnvironmentInterface env) {
	    if(isDeclaration){ // controllo il campo isDeclaration, da qui in poi solo VAR, CONST e FUN vengono passati
			if(!env.declare(namedElement)) // controllo che esista già namedElement nell'hashSet e quindi non lo inserisco
				CheckerError.redeclared(namedElement, lineNumber); // se esiste già allora mando checkError di ridichiarazione
		}else{
			if(!env.find(namedElement)) // cerco nell'hashSet se non è già presente namedElement
				CheckerError.undeclared(namedElement, lineNumber); // se non è presente mando checkError di mancata dichiarazione
		}
	}

}
