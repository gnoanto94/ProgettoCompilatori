package tree;

public interface Visitor {
    Object visit(FloatConstLeaf f);
    Object visit(IntConstLeaf i);
    Object visit(IdLeaf i);
    Object visit(StringConstLeaf s);
    Object visit(NullLeaf n);
    Object visit(TrueLeaf t);
    Object visit(FalseLeaf f);

    /* Nodi degli Operatori */
    Object visit(PlusOp p);
    Object visit(MinusOp m);
    Object visit(UMinusOp u);
    Object visit(MultOp m);
    Object visit(DivOp d);
    Object visit(AndOp a);
    Object visit(OrOp o);
    Object visit(NotOp n);
    Object visit(LtOp l);
    Object visit(LeOp l);
    Object visit(GtOp g);
    Object visit(GeOp g);
    Object visit(NeOp n);
    Object visit(EqOp e);
    Object visit(SimpleAssignOp s);

    /* Nodi degli Statement */
    Object visit(IfOp i);
    Object visit(ElifOp e);
    Object visit(ElseOp e);
    Object visit(WhileOp w);
    Object visit(DoOp d);
    Object visit(ReadOp r);
    Object visit(WriteOp w);
    Object visit(MultipleAssignOp m);
    Object visit(CallProcOp c);
    Object visit(IdListInitOp i);

    /* Nodi delle Dichiarazioni */
    Object visit(ParamDeclOp p);
    Object visit(ResultTypeOp r);
    Object visit(ProcOp p);
    Object visit(ProcOpBody p);
    Object visit(ProgramOp p);
    Object visit(ReturnExprs r);
    Object visit(Type t);
    Object visit(VarDeclOp v);

}