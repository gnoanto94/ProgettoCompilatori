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
    Object visit(PlusOp p) throws Exception;
    Object visit(MinusOp m) throws Exception;
    Object visit(UMinusOp u) throws Exception;
    Object visit(MultOp m) throws Exception;
    Object visit(DivOp d) throws Exception;
    Object visit(AndOp a) throws Exception;
    Object visit(OrOp o) throws Exception;
    Object visit(NotOp n) throws Exception;
    Object visit(LtOp l) throws Exception;
    Object visit(LeOp l) throws Exception;
    Object visit(GtOp g) throws Exception;
    Object visit(GeOp g) throws Exception;
    Object visit(NeOp n) throws Exception;
    Object visit(EqOp e) throws Exception;
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