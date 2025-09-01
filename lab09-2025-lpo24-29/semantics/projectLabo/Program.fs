open Semantics

(* some simple tests with the static semantics *)

let stmt1_1 = VarStmt(Name "x", IntLiteral 0)

let stmt2_1 = AssignStmt(Name "x", Add(Variable(Name "x"), IntLiteral 1))

let stmt3_1 = PrintStmt(Variable(Name "x"))

let stmt4_1 = AssertStmt(Eq(Variable(Name "x"), IntLiteral(1)))

let prog1 =
    LangProg(
        NonEmptyStmtSeq(
            stmt1_1,
            NonEmptyStmtSeq(stmt2_1, NonEmptyStmtSeq(stmt3_1, NonEmptyStmtSeq(stmt4_1, EmptyStmtSeq)))
        )
    )

typecheckProg prog1

(*
   var x=0;
   x=x+1;
   print x;
   assert x==1

no type errors
*)

let stmt1_2 = VarStmt(Name "x", PairLit(IntLiteral 0, BoolLiteral false))

let stmt2_2 = PrintStmt(Add(Fst(Variable(Name "x")), IntLiteral 1))

let stmt3_2 = PrintStmt(And(Snd(Variable(Name "x")), BoolLiteral true))

let stmt4_2 = AssignStmt(Name "x", PairLit(IntLiteral 1, BoolLiteral true))

let prog2 =
    LangProg(
        NonEmptyStmtSeq(
            stmt1_2,
            NonEmptyStmtSeq(stmt2_2, NonEmptyStmtSeq(stmt3_2, NonEmptyStmtSeq(stmt4_2, EmptyStmtSeq)))
        )
    )

typecheckProg prog2

(*
   var x=0,false;
   print fst x+1;
   print snd x && true;
   x=1,true

no type errors
*)

let stmt1_3 = VarStmt(Name "x", IntLiteral 0)

let stmt2_3 =
    IfStmt(
        Eq(Variable(Name "x"), IntLiteral 0),
        Block(
            NonEmptyStmtSeq(
                VarStmt(Name "x", BoolLiteral false),
                NonEmptyStmtSeq(PrintStmt(And(Variable(Name "x"), BoolLiteral true)), EmptyStmtSeq)
            )
        ),
        EmptyBlock
    )

let stmt3_3 = AssignStmt(Name "x", IntLiteral 2)

let prog3 =
    LangProg(NonEmptyStmtSeq(stmt1_3, NonEmptyStmtSeq(stmt2_3, NonEmptyStmtSeq(stmt3_3, EmptyStmtSeq))))

typecheckProg prog3

(*
   var x=0;
   if(x==0){
      var x=false;
      print x && true
   };
   x=2

no type errors
*)


(* some simple tests with the dynamic semantics *)

let stmt1_4 = VarStmt(Name "x", IntLiteral 0)

let stmt2_4 = AssignStmt(Name "x", Add(Variable(Name "x"), IntLiteral 1))

let stmt3_4 = PrintStmt(Variable(Name "x"))

let stmt4_4 = AssertStmt(Eq(Variable(Name "x"), IntLiteral(1)))

let prog4 =
    LangProg(
        NonEmptyStmtSeq(
            stmt1_4,
            NonEmptyStmtSeq(stmt2_4, NonEmptyStmtSeq(stmt3_4, NonEmptyStmtSeq(stmt4_4, EmptyStmtSeq)))
        )
    )

executeProg prog4
printfn ""

(*
   var x=0;
   x=x+1;
   print x;
   assert x==1

prints:
1
*)

let stmt1_5 = VarStmt(Name "x", PairLit(IntLiteral 0, BoolLiteral false))

let stmt2_5 = PrintStmt(Variable(Name "x"))

let stmt3_5 = PrintStmt(Add(Fst(Variable(Name "x")), IntLiteral 1))

let stmt4_5 = AssertStmt(Not(And(Snd(Variable(Name "x")), BoolLiteral true)))

let stmt5_5 = AssignStmt(Name "x", PairLit(IntLiteral 1, BoolLiteral true))

let prog5 =
    LangProg(
        NonEmptyStmtSeq(
            stmt1_5,
            NonEmptyStmtSeq(
                stmt2_5,
                NonEmptyStmtSeq(stmt3_5, NonEmptyStmtSeq(stmt4_5, NonEmptyStmtSeq(stmt5_5, EmptyStmtSeq)))
            )
        )
    )

executeProg prog5
printfn ""
(*
   var x=0,false;
   print x;
   print fst x+1;
   assert !(snd x && true);
   x=1,true

prints:

(0,False)
1
*)

let stmt1_6 = VarStmt(Name "x", IntLiteral 0)

let stmt2_6 =
    IfStmt(
        Eq(Variable(Name "x"), IntLiteral 0),
        Block(
            NonEmptyStmtSeq(
                AssignStmt(Name "x", IntLiteral 2),
                NonEmptyStmtSeq(
                    VarStmt(Name "x", BoolLiteral false),
                    NonEmptyStmtSeq(AssertStmt(Not(And(Variable(Name "x"), BoolLiteral true))), EmptyStmtSeq)
                )
            )
        ),
        EmptyBlock
    )

let stmt3_6 = AssignStmt(Name "x", Add(Variable(Name "x"), IntLiteral 1))

let stmt4_6 = PrintStmt(Variable(Name "x"))

let prog6 =
    LangProg(
        NonEmptyStmtSeq(
            stmt1_6,
            NonEmptyStmtSeq(stmt2_6, NonEmptyStmtSeq(stmt3_6, NonEmptyStmtSeq(stmt4_6, EmptyStmtSeq)))
        )
    )


executeProg prog6
printfn ""
(*
   var x=0;
   if(x==0){
      x=2;
      var x=false;
      assert !(x && true)
   };
   x=x+1;
   print x

prints:

3
*)

(*  these programs are not type correct, but execute correctly *)

let stmt1_7 = VarStmt(Name "x", IntLiteral 0)

let stmt2_7 = AssignStmt(Name "x", Add(IntLiteral 1, Variable(Name "x")))

let stmt3_7 = AssignStmt(Name "x", Eq(IntLiteral 1, Variable(Name "x")))

let stmt4_7 = AssertStmt(Variable(Name "x"))

let prog7 =
    LangProg(
        NonEmptyStmtSeq(
            stmt1_7,
            NonEmptyStmtSeq(stmt2_7, NonEmptyStmtSeq(stmt3_7, NonEmptyStmtSeq(stmt4_7, EmptyStmtSeq)))
        )
    )

executeProg prog7
printfn ""
(*
   var x=0;
   x=x+1;
   x=1==x;
   assert x

prints no output

*)

let stmt1_8 = VarStmt(Name "x", PairLit(IntLiteral 0, BoolLiteral false))

let stmt2_8 =
    AssertStmt(Not(Eq(Variable(Name "x"), PairLit(BoolLiteral false, IntLiteral 0))))

let prog8 =
    LangProg(NonEmptyStmtSeq(stmt1_8, NonEmptyStmtSeq(stmt2_8, EmptyStmtSeq)))

executeProg prog8

(*
   var x=0,false;
   assert !(x==(false,0))

prints no output

*)
