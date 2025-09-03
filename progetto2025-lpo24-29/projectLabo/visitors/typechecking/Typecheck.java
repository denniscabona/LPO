package projectLabo.visitors.typechecking;
import static projectLabo.visitors.typechecking.AtomicType.*;

import projectLabo.environments.EnvironmentException;
import projectLabo.parser.ast.Block;
import projectLabo.parser.ast.Exp;
import projectLabo.parser.ast.Stmt;
import projectLabo.parser.ast.StmtSeq;
import projectLabo.parser.ast.Variable;
import projectLabo.visitors.Visitor;

public class Typecheck implements Visitor<Type> {

	private final StaticEnv env = new StaticEnv();

	// useful to typecheck binary operations where operands must have the same type
	private void checkBinOp(Exp left, Exp right, Type type) {
		type.checkEqual(left.accept(this));
		type.checkEqual(right.accept(this));
	}

	// static semantics for programs; no value returned by the visitor

	@Override
	public Type visitLangProg(StmtSeq stmtSeq) {
		try {
			stmtSeq.accept(this);
		} catch (EnvironmentException e) { // undeclared variable
			throw new TypecheckerException(e);
		}
		return null;
	}

	// static semantics for sequences of statements
	// no value returned by the visitor

	@Override
	public Type visitEmptyStmtSeq() {
		return null;
	}

	@Override
	public Type visitNonEmptyStmtSeq(Stmt first, StmtSeq rest) {
		first.accept(this);
		rest.accept(this);
		return null;
	}
	
	// static semantics for statements; no value returned by the visitor

	@Override
	public Type visitIfStmt(Exp exp, Block thenBlock, Block elseBlock) {
		BOOL.checkEqual(exp.accept(this));
		thenBlock.accept(this);
		if (elseBlock != null)
			elseBlock.accept(this);
		return null;
	}

	@Override	// aggiunta la semantica statica di WhileStmt
	public Type visitWhileStmt(Exp exp, Block block){
		BOOL.checkEqual(exp.accept(this)); // controllo che l'espressione abbia tipo BOOL
		block.accept(this);
		return null;
	}
	
	@Override
	public Type visitPrintStmt(Exp exp) {
		exp.accept(this);
		return null;
	}

	@Override
	public Type visitVarStmt(Variable var, Exp exp) {
		env.dec(var, exp.accept(this));
		return null;
	}

	@Override 
	public Type visitAssertStmt(Exp exp){
		BOOL.checkEqual(exp.accept(this));
		return null;
	}

	@Override
	public Type visitAssignStmt(Variable var, Exp exp){
		var found = env.lookup(var); // controllo che la variabile sia dichiarata
		found.checkEqual(exp.accept(this));
		return null;
	}

	@Override
	public Type visitBlock(StmtSeq stmtSeq) {
		env.enterLevel();
		stmtSeq.accept(this);
		env.exitLevel();
		return null;
	}

	// static semantics of expressions; a type is returned by the visitor

	@Override
	public AtomicType visitAdd(Exp left, Exp right) {
		checkBinOp(left, right, INT);
		return INT;
	}

	@Override
	public AtomicType visitBoolLiteral(boolean value) {
		return BOOL;
	}

	@Override
	public AtomicType visitEq(Exp left, Exp right) {
		left.accept(this).checkEqual(right.accept(this));
		return BOOL;
	}

	@Override 
	public AtomicType visitAnd(Exp left, Exp right){
		checkBinOp(left, right, BOOL);
		return BOOL;
	}

	@Override
	public Type visitFst(Exp exp) {
		return exp.accept(this).toPairType().fstType();
	}

	@Override
	public AtomicType visitIntLiteral(int value) {
		return INT;
	}

	@Override
	public AtomicType visitMinus(Exp exp) {
		INT.checkEqual(exp.accept(this));
		return INT;
	}
	
	@Override
	public AtomicType visitMul(Exp left, Exp right) {
		checkBinOp(left, right, INT);
		return INT;
	}

	@Override
	public PairType visitPairLit(Exp left, Exp right) {
		return new PairType(left.accept(this), right.accept(this));
	}

	@Override
	public Type visitSnd(Exp exp) {
		return exp.accept(this).toPairType().sndType();
	}

	@Override 
	public Type visitNot(Exp exp){
		BOOL.checkEqual(exp.accept(this));
		return BOOL;
	}

	@Override	// aggiunta la semantica statica di Diff
	public Type visitDiff(Exp left, Exp right){
		Type leftType = left.accept(this); // salvataggio dei literal dei parametri (INT, BOOL, PAIR o SET)
		Type righType = right.accept(this);
		if(!(leftType instanceof SetType) || !(righType instanceof SetType)) // controllo che siano due Set
			throw new TypecheckerException("Gli operandi di visitUnion devono essere insiemi");

		SetType leftSet = (SetType) leftType; // cast degli insiemi a SetType
		SetType rightSet = (SetType) righType;
		if(!(leftSet.ElemType().equals(rightSet.ElemType()))) // controllo che abbiano gli eleementi dello stesso tipo
			throw new TypecheckerException("Gli operandi di visitUnion devono avere lo stesso tipo");

		return new SetType(leftSet.ElemType()); // ritorna un nuovo Set, il tipo degli elementi coincide con i due insiemi di parametro
	}

	@Override	// aggiunta la semantica statica di Union
	public Type visitUnion(Exp left, Exp right){
		Type leftType = left.accept(this); // salvataggio dei literal dei parametri (INT, BOOL, PAIR o SET)
		Type righType = right.accept(this);
		if(!(leftType instanceof SetType) || !(righType instanceof SetType)) // controllo che siano due Set
			throw new TypecheckerException("Gli operandi di visitUnion devono essere insiemi");

		SetType leftSet = (SetType) leftType; // cast degli insiemi a SetType
		SetType rightSet = (SetType) righType;
		if(!(leftSet.ElemType().equals(rightSet.ElemType()))) // controllo che abbiano gli eleementi dello stesso tipo
			throw new TypecheckerException("Gli operandi di visitUnion devono avere lo stesso tipo");

		return new SetType(leftSet.ElemType()); // ritorna un nuovo Set, il tipo degli elementi coincide con i due insiemi di parametro
	}

	@Override	// aggiunta della semantica statica di IsIN
	public Type visitIsIn(Exp elem, Exp set){
		Type setType = set.accept(this); // salvataggio del literal di set (INT, BOOL, PAIR o SET)
		if(!(setType instanceof SetType)) // controllo che set sia un Set
			throw new TypecheckerException("L'operando destro di visitIsIn non è un SetType");

		Type elemType = elem.accept(this); 
		SetType setSetType = (SetType) setType; // cast di setSetType a SetType
		if(!(elemType.equals(setSetType.ElemType()))) // controllo che elem sia dello stesso tipo degli elementi set
			throw new TypecheckerException("L'operando sinistro di visitIsIn non è dello stesso tipo dell'insieme dell'operando destro");

		return BOOL;
	}

	@Override	// aggiunta la semantica statica di Size
	public Type visitSize(Exp exp){
		Type setType = exp.accept(this); // salvataggio del literal di exp (INT, BOOL, PAIR o SET)
		if(!(setType instanceof SetType)) // controllo che exp sia un Set
			throw new TypecheckerException("L'operando di visitSize non è un insieme");

		return INT;
	}

	@Override	// aggiunta la semantica statica di SetLit (OPEN_BLOCK Exp CLOSE_BLOCK)
	public Type visitSetLit(Exp exp){
		Type setType = exp.accept(this); // salvataggio del literal di exp (INT, BOOL, PAIR o SET)
		if(!(setType instanceof SetType)) // controllo che exp sia un Set
			throw new TypecheckerException("L'operando di visitSetLiteral deve essere un insieme");
		return setType;
	}

	@Override	// aggiunta la semantica statica di SetEnum (OPEN_BLOCK (FOR IDENT IN Exp EXP_SEP) Exp CLOSE_BLOCK)
	public Type visitSetEnum(Variable var, Exp set, Exp elem){
		Type setType = set.accept(this);  // salvataggio del literal di set (INT, BOOL, PAIR o SET)
		if(!(setType instanceof SetType s))	// controllo che set sia un Set
			throw new TypecheckerException("L'operando exp di visitSetEnum non è un insieme");

		Type typeOfSetElem = s.ElemType(); // salvataggio del tipo degli elementi del Set
		env.dec(var, typeOfSetElem); // aggiorno env con var
		Type elType = elem.accept(this);

		return new SetType(elType);
	}
	
	@Override
	public Type visitVariable(Variable var) {
		return env.lookup(var);
	}

}
