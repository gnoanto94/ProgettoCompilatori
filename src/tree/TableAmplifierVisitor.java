package tree;

import syntaxanalysis.SymbolTable;
import java.util.ArrayList;


public class TableAmplifierVisitor implements Visitor {

    @Override
    public Object visit(ProgramOp p) {
        ArrayList<VarDeclOp> varDeclList = p.getVarDeclList();
        ArrayList<ProcOp> procList = p.getProcOpList();

        for(VarDeclOp v: varDeclList){
            v.accept(this);
        }

        for(ProcOp o: procList){
            o.accept(this);
        }

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

    @Override
    public Object visit(ProcOp p) {
        ArrayList<ResultTypeOp> resultTypeList = p.getResultTypeList();
        ArrayList<ParamDeclOp> paramDeclList = p.getParamDeclList();

        ArrayList<Type> paramTypes = new ArrayList<>();
        //Ciclo per inserire correttamente i tipi dei parametri nelle entry a loro associate
        if(paramDeclList != null){
            for(ParamDeclOp par : paramDeclList){
                paramTypes.addAll((ArrayList<Type>)par.accept(this));
            }
        }

        //Si recupera la table entry (nella tabella globale) della procedura corrente
        //e si inseriscono le informazioni mancanti sul tipo dei parametri e sui tipi di ritorno
        SymbolTable.SymbolTableRow t = p.getProcSymbolTable().getPrev().lookup(p.getId().getIdEntry());
        if(t instanceof SymbolTable.ProcRow){
            ((SymbolTable.ProcRow) t).setReturnTypes(resultTypeList);
            ((SymbolTable.ProcRow) t).setParamTypes(paramTypes);
        }

        p.getProcBody().accept(this); //per chiamare la visit sul procbody
        return null;
    }

    @Override
    public Object visit(ProcOpBody p) {
        ArrayList<VarDeclOp> varDeclList = p.getVarDeclList();

        if(varDeclList != null){
            for(VarDeclOp v: varDeclList){
                v.accept(this);
            }
        }

        return null;
    }

    @Override
    public Object visit(ParamDeclOp p) {
        ArrayList<IdLeaf> idList = p.getIdList();
        Type type = p.getType();
        SymbolTable.SymbolTableRow check;

        ArrayList<Type> paramTypes = new ArrayList<>();

        //Aggiungiamo alla SymbolTable il tipo associato ad ogni parametro nella lista
        for(IdLeaf i : idList) {
            if (i != null) {
                check = (SymbolTable.SymbolTableRow) i.accept(this);
                if (check instanceof SymbolTable.VarRow) {
                    ((SymbolTable.VarRow) check).setVarType(type);
                }
                paramTypes.add(type);
            }
        }
        return paramTypes;
    }

    @Override
    public Object visit(ResultTypeOp r) {
        return r;
    }

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
        //restituisce la entry nella symbol table di questo idLeaf
        return i.getTableEntry();
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

    /***************************************************************************************************/
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
    public Object visit(ReturnExprs r) {
        return null;
    }

    @Override
    public Object visit(Type t) {
        return null;
    }
}
