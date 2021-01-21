package tree;

public interface Visitor {
    Object visit(FloatConstLeaf f);
    Object visit(IntConstLeaf i);
    Object visit(IdLeaf i) throws Exception;
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
    Object visit(SimpleAssignOp s) throws Exception;

    /* Nodi degli Statement */
    Object visit(IfOp i) throws Exception;
    Object visit(ElifOp e) throws Exception;
    Object visit(ElseOp e) throws Exception;
    Object visit(WhileOp w) throws Exception;
    Object visit(DoOp d) throws Exception;
    Object visit(ReadOp r) throws Exception;
    Object visit(WriteOp w) throws Exception;
    Object visit(MultipleAssignOp m) throws Exception;
    Object visit(CallProcOp c) throws Exception;
    Object visit(IdListInitOp i) throws Exception;

    /* Nodi delle Dichiarazioni */
    Object visit(ParamDeclOp p) throws Exception;
    Object visit(ResultTypeOp r);
    Object visit(ProcOp p) throws Exception;
    Object visit(ProcOpBody p) throws Exception;
    Object visit(ProgramOp p) throws Exception;
    Object visit(ReturnExprs r) throws Exception;
    Object visit(Type t);
    Object visit(VarDeclOp v) throws Exception;

}