package tree;

import syntaxanalysis.Env;
import syntaxanalysis.StackEnv;
import syntaxanalysis.SymbolTable;

import java.util.ArrayList;

public class TableAmplifierVisitor implements Visitor {

    Env globalTable = StackEnv.top();

    @Override
    public Object visit(FloatConstLeaf f)  {
        return new Type(Type.FLOAT);
    }

    @Override
    public Object visit(IntConstLeaf i) {
        return new Type(Type.INT);
    }

    @Override
    public Object visit(IdLeaf i) {
        SymbolTable.SymbolTableRow row = i.getTableEntry();

        if (row instanceof SymbolTable.VarRow){
            return ((SymbolTable.VarRow) row);
        }

        if (row instanceof SymbolTable.ProcRow){
            return ((SymbolTable.ProcRow) row);
        }

        return null;
    }

    @Override
    public Object visit(StringConstLeaf s) {
        return new Type(Type.STRING);
    }

    @Override
    public Object visit(NullLeaf n) {
        return new Type();
    }

    @Override
    public Object visit(TrueLeaf t) {
        return new Type(Type.BOOL);
    }

    @Override
    public Object visit(FalseLeaf f) {
        return new Type(Type.BOOL);
    }

    @Override
    public Object visit(PlusOp p) {
        return null;
    }

    @Override
    public Object visit(MinusOp m) {
        return null;
    }

    @Override
    public Object visit(UMinusOp u) {
        return null;
    }

    @Override
    public Object visit(MultOp m) {
        return null;
    }

    @Override
    public Object visit(DivOp d) {
        return null;
    }

    @Override
    public Object visit(AndOp a) {
        return null;
    }

    @Override
    public Object visit(OrOp o) {
        return null;
    }

    @Override
    public Object visit(NotOp n) {
        return null;
    }

    @Override
    public Object visit(LtOp l) {
        return null;
    }

    @Override
    public Object visit(LeOp l) {
        return null;
    }

    @Override
    public Object visit(GtOp g) {
        return null;
    }

    @Override
    public Object visit(GeOp g) {
        return null;
    }

    @Override
    public Object visit(NeOp n) {
        return null;
    }

    @Override
    public Object visit(EqOp e) {
        return null;
    }

    @Override
    public Object visit(SimpleAssignOp s) {
        return null;
    }

    @Override
    public Object visit(IfOp i) {
        return null;
    }

    @Override
    public Object visit(ElifOp e) {
        return null;
    }

    @Override
    public Object visit(ElseOp e) {
        return null;
    }

    @Override
    public Object visit(WhileOp w) {
        return null;
    }

    @Override
    public Object visit(DoOp d) {
        return null;
    }

    @Override
    public Object visit(ReadOp r) {
        return null;
    }

    @Override
    public Object visit(WriteOp w) {
        return null;
    }

    @Override
    public Object visit(MultipleAssignOp m) {
        return null;
    }

    @Override
    public Object visit(CallProcOp c) {
        return null;
    }

    @Override
    public Object visit(IdListInitOp i) {
        return null;
    }

    @Override
    public Object visit(ParamDeclOp p) {
        ArrayList<IdLeaf> idList = p.getIdList();
        Type type = p.getType();
        SymbolTable.SymbolTableRow check;

        for(IdLeaf i : idList) {
            if (i instanceof IdLeaf) {
                check = (SymbolTable.SymbolTableRow) i.accept(this);
                if (check instanceof SymbolTable.VarRow) {
                    ((SymbolTable.VarRow) check).setVarType(type);
                }
            }
        }
        return null;
    }

    @Override
    public Object visit(ResultTypeOp r) {
        return r;
    }

    @Override
    public Object visit(ProcOp p) {
        ArrayList<ResultTypeOp> resultTypeList = p.getResultTypeList();
        ArrayList<ParamDeclOp> paramDeclList = p.getParamDeclList();
        //modificare aggiungendo variabile symtable all'interno di procop
        //per poi modificare la tabella specifica della procop
        //al posto di usare la globaltable definita sopra
        for(ResultTypeOp r : resultTypeList){
            r.accept(this);
        }
        for(ParamDeclOp par : paramDeclList){
            par.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(ProcOpBody p) {
        return null;
    }

    @Override
    public Object visit(ProgramOp p) {
        return null;
    }

    @Override
    public Object visit(ReturnExprs r) {
        return null;
    }

    @Override
    public Object visit(Type t) {
        return null;
    }

    @Override
    public Object visit(VarDeclOp v) {
        Type type = v.getType();
        IdListInitOp idListOp = v.getIdListInit();
        ArrayList<IdListInit> idList = idListOp.getIdListInit();
        SymbolTable.SymbolTableRow check;
        IdLeaf idTemp;

        for(IdListInit i : idList){
            if(i instanceof IdLeaf){
                check = (SymbolTable.SymbolTableRow) ((IdLeaf) i).accept(this);
                if(check instanceof SymbolTable.VarRow){
                    ((SymbolTable.VarRow)check).setVarType(type);
                }
            }
            if(i instanceof SimpleAssignOp){
                idTemp = ((SimpleAssignOp) i).getId();
                check = (SymbolTable.SymbolTableRow) idTemp.accept(this);
                if(check instanceof SymbolTable.VarRow){
                    ((SymbolTable.VarRow)check).setVarType(type);
                }
            }
        }
        return null;
    }
}
