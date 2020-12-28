package tree;

import org.jdom2.Element;

import java.util.ArrayList;

public class TreeVisitor implements Visitor{

    public Object visit(ProgramOp v){
        Element programOp = new Element(Components.PROGRAM_OP);
        Element procOpList = new Element(Components.PROC_OP_LIST);
        Element expr;

        for (ProcOp e : v.getProcOpList()) {
            expr = (Element) ((Visitable) e).accept(this);
            procOpList.addContent(expr);
        }

        programOp.addContent(varDeclList(v.getVarDeclList()));
        programOp.addContent(procOpList);
        return programOp;
    }


    public Object visit(ReturnExprs v){
        Element returnExprs = new Element(Components.RETURN_EXPRS);
        returnExprs.addContent(exprList(v.getExprList()));
        return returnExprs;
    }

    public Object visit(ProcOp v){
        Element procOp = new Element(Components.PROC_OP);
        Element id = (Element) ((Visitable) v.getId()).accept(this);
        Element paramDeclList = new Element(Components.PARAM_DECL_LIST);
        Element resultTypeList = new Element(Components.RESULT_TYPE_LIST);
        Element procOpBody = (Element) ((Visitable) v.getProcBody()).accept(this);
        Element tmp;

        if(v.getParamDeclList() == null){
            paramDeclList.addContent(Components.NULL);
        } else {
            for (ParamDeclOp e : v.getParamDeclList()) {
                tmp = (Element) ((Visitable) e).accept(this);
                paramDeclList.addContent(tmp);
            }
        }
        for (ResultTypeOp e : v.getResultTypeList()) {
            tmp = (Element) ((Visitable) e).accept(this);
            resultTypeList.addContent(tmp);
        }

        procOp.addContent(id);
        procOp.addContent(paramDeclList);
        procOp.addContent(resultTypeList);
        procOp.addContent(procOpBody);
        return procOp;
    }

    public Object visit(ProcOpBody v){
        Element procOpBody = new Element(Components.PROC_BODY_OP);
        Element returnExprs = new Element(Components.RETURN_EXPRS);

        if(v.getReturnExprs() == null){
            returnExprs.addContent(Components.NULL);
        } else {
            returnExprs = (Element) ((Visitable) v.getReturnExprs()).accept(this);
        }
        procOpBody.addContent(varDeclList(v.getVarDeclList()));
        procOpBody.addContent(statList(v.getStatList()));
        procOpBody.addContent(returnExprs);
        return procOpBody;
    }


    /*Declarations*/
    public Object visit(VarDeclOp v){
        Element varDeclOp = new Element(Components.VAR_DECL_OP);
        Element type = (Element) ((Visitable) v.getType()).accept(this);
        Element idListInit = (Element) ((Visitable) v.getIdListInit()).accept(this);

        varDeclOp.addContent(type);
        varDeclOp.addContent(idListInit);
        return varDeclOp;
    }

    public Object visit(ParamDeclOp v){
        Element paramDeclOp = new Element(Components.PARAM_DECL_OP);
        Element type = (Element) ((Visitable) v.getType()).accept(this);
        paramDeclOp.addContent(type);
        paramDeclOp.addContent(idList(v.getIdList()));
        return paramDeclOp;
    }


    public Object visit(IdListInitOp v){
        Element idListInitOp = new Element(Components.ID_LIST_INIT_OP);
        Element idListInit = new Element(Components.ID_LIST);
        Element expr;

        for (IdListInit e : v.getIdListInit()) {
            expr = (Element) ((Visitable) e).accept(this);
            idListInit.addContent(expr);
        }

        idListInitOp.addContent(idListInit);
        return idListInitOp;
    }

    /*Statement*/

    public Object visit(CallProcOp v){
        Element callProcOp = new Element(Components.CALL_PROC_OP);
        Element id = (Element) ((Visitable) v.getId()).accept(this);

        callProcOp.addContent(id);
        callProcOp.addContent(exprList(v.getExprList()));
        return callProcOp;
    }

    public Object visit(WhileOp v){
        Element whileOp = new Element(Components.WHILE_OP);
        Element expr = (Element) ((Visitable) v.getExpr()).accept(this);
        Element doOp = (Element) ((Visitable) v.getDoOp()).accept(this);

        whileOp.addContent(statList(v.getStatList()));
        whileOp.addContent(expr);
        whileOp.addContent(doOp);
        return whileOp;
    }

    public Object visit(WriteOp v){
        Element writeOp = new Element(Components.WRITE_OP);
        writeOp.addContent(exprList(v.getExprList()));
        return writeOp;
    }

    public Object visit(ReadOp v){
        Element readOp = new Element(Components.READ_OP);
        readOp.addContent(idList(v.getIdList()));
        return readOp;
    }

    public Object visit(DoOp v){
        Element doOp = new Element(Components.DO_OP);
        doOp.addContent(statList(v.getStatList()));
        return doOp;
    }

    public Object visit(ElifOp v){
        Element elifOp = new Element(Components.ELIF_OP);
        Element expr = (Element) ((Visitable) v.getExpr()).accept(this);

        elifOp.addContent(expr);
        elifOp.addContent(statList(v.getStatList()));
        return elifOp;
    }

    public Object visit(ElseOp v){
        Element elseOp = new Element(Components.ELSE_OP);

        elseOp.addContent(statList(v.getStatList()));
        return elseOp;
    }

    public Object visit(IfOp v){
        Element ifOp = new Element(Components.IF_OP);
        Element elifList = new Element(Components.ELIF_LIST);
        Element elseOp = new Element(Components.ELSE_OP);
        Element expr = (Element) ((Visitable) v.getExpr()).accept(this);
        Element stat;

        if(v.getElifList() == null){
            elifList.addContent(Components.NULL);
        } else {
            for (ElifOp e : v.getElifList()) {
                stat = (Element) ((Visitable) e).accept(this);
                elifList.addContent(stat);
            }
        }

        if(v.getElseOp() == null){
            elseOp.addContent(Components.NULL);
        } else {
            elseOp = (Element) ((Visitable) v.getElseOp()).accept(this);
        }

        ifOp.addContent(expr);
        ifOp.addContent(statList(v.getStatList()));
        ifOp.addContent(elifList);
        ifOp.addContent(elseOp);
        return ifOp;
    }


    public Object visit(SimpleAssignOp v){
        Element simpleAssignOp = new Element(Components.SIMPLE_ASSIGN);
        Element id, expr;
        id = (Element) ((Visitable) v.getId()).accept(this);
        expr = (Element) ((Visitable) v.getExpr()).accept(this);

        simpleAssignOp.addContent(id);
        simpleAssignOp.addContent(expr);
        return simpleAssignOp;
    }

    public Object visit(MultipleAssignOp v){
        Element multipleAssignOp = new Element(Components.MULTIPLE_ASSIGN_OP);
        multipleAssignOp.addContent(idList(v.getIdList()));
        multipleAssignOp.addContent(exprList(v.getExprList()));
        return multipleAssignOp;
    }

    /*Operatori Relazionali*/
    public Object visit(GeOp v){
        Element geOp = new Element(Components.GE_OP);
        Element left, right;
        left = (Element) ((Visitable) v.getE1()).accept(this);
        right = (Element) ((Visitable) v.getE2()).accept(this);

        geOp.addContent(left);
        geOp.addContent(right);
        return geOp;
    }

    public Object visit(GtOp v){
        Element gtOp = new Element(Components.GT_OP);
        Element left, right;
        left = (Element) ((Visitable) v.getE1()).accept(this);
        right = (Element) ((Visitable) v.getE2()).accept(this);

        gtOp.addContent(left);
        gtOp.addContent(right);
        return gtOp;
    }
    public Object visit(LeOp v){
        Element leOp = new Element(Components.LE_OP);
        Element left, right;
        left = (Element) ((Visitable) v.getE1()).accept(this);
        right = (Element) ((Visitable) v.getE2()).accept(this);

        leOp.addContent(left);
        leOp.addContent(right);
        return leOp;
    }

    public Object visit(LtOp v){
        Element ltOp = new Element(Components.LT_OP);
        Element left, right;
        left = (Element) ((Visitable) v.getE1()).accept(this);
        right = (Element) ((Visitable) v.getE2()).accept(this);

        ltOp.addContent(left);
        ltOp.addContent(right);
        return ltOp;
    }

    public Object visit(NeOp v){
        Element neOp = new Element(Components.NE_OP);
        Element left, right;
        left = (Element) ((Visitable) v.getE1()).accept(this);
        right = (Element) ((Visitable) v.getE2()).accept(this);

        neOp.addContent(left);
        neOp.addContent(right);
        return neOp;
    }

    /*Operatori Aritmetici */
    public Object visit(PlusOp v){
        Element plusOp = new Element(Components.PLUS_OP);
        Element left, right;
        left = (Element) ((Visitable) v.getE1()).accept(this);
        right = (Element) ((Visitable) v.getE2()).accept(this);

        plusOp.addContent(left);
        plusOp.addContent(right);
        return plusOp;
    }

    public Object visit(UMinusOp v){
        Element uminusOp = new Element(Components.UMINUS_OP);
        Element expr;
        expr = (Element) ((Visitable) v.getE1()).accept(this);

        uminusOp.addContent(expr);
        return uminusOp;
    }

    public Object visit(MultOp v){
        Element multOp = new Element(Components.MULT_OP);
        Element left, right;
        left = (Element) ((Visitable) v.getE1()).accept(this);
        right = (Element) ((Visitable) v.getE2()).accept(this);

        multOp.addContent(left);
        multOp.addContent(right);
        return multOp;
    }

    public Object visit(DivOp v){
        Element divOp = new Element(Components.DIV_OP);
        Element left, right;
        left = (Element) ((Visitable) v.getE1()).accept(this);
        right = (Element) ((Visitable) v.getE2()).accept(this);

        divOp.addContent(left);
        divOp.addContent(right);
        return divOp;
    }

    public Object visit(MinusOp v){
        Element minusOp = new Element(Components.MINUS_OP);
        Element left, right;
        left = (Element) ((Visitable) v.getE1()).accept(this);
        right = (Element) ((Visitable) v.getE2()).accept(this);

        minusOp.addContent(left);
        minusOp.addContent(right);
        return minusOp;
    }

    public Object visit(EqOp v){
        Element eqOp = new Element(Components.EQ_OP);
        Element left, right;
        left = (Element) ((Visitable) v.getE1()).accept(this);
        right = (Element) ((Visitable) v.getE2()).accept(this);

        eqOp.addContent(left);
        eqOp.addContent(right);
        return eqOp;
    }


    /*Operatori Logici*/
    public Object visit(AndOp v) {
        Element andOp = new Element(Components.AND_OP);
        Element left, right;
        left = (Element) ((Visitable) v.getE1()).accept(this);
        right = (Element) ((Visitable) v.getE2()).accept(this);

        andOp.addContent(left);
        andOp.addContent(right);
        return andOp;
    }

    public Object visit(OrOp v){
        Element orOp = new Element(Components.OR_OP);
        Element left, right;
        left = (Element) ((Visitable) v.getE1()).accept(this);
        right = (Element) ((Visitable) v.getE2()).accept(this);

        orOp.addContent(left);
        orOp.addContent(right);
        return orOp;
    }

    public Object visit(NotOp v){
        Element notOp = new Element(Components.NOT_OP);
        Element expr;
        expr = (Element) ((Visitable) v.getE1()).accept(this);

        notOp.addContent(expr);
        return notOp;
    }


    /*Foglie dell'albero*/
    public Object visit(NullLeaf v){
        return new Element(Components.NULL);
    }

    public Object visit(TrueLeaf v){
        return new Element(Components.TRUE);
    }

    public Object visit(FalseLeaf v){
        return new Element(Components.FALSE);
    }

    public Object visit(StringConstLeaf v){
        Element stringLeaf = new Element(Components.STRING_CONST);
        stringLeaf.addContent(v.getValue());
        return stringLeaf;
    }

    public Object visit(IntConstLeaf v) {
        Element intLeaf = new Element(Components.INT_CONST);
        intLeaf.addContent(String.valueOf(v.getValue()));
        return intLeaf;
    }

    public Object visit(FloatConstLeaf v){
        Element floatLeaf = new Element(Components.FLOAT_CONST);
        floatLeaf.addContent(String.valueOf(v.getValue()));
        return floatLeaf;
    }

    public Object visit(IdLeaf v){
        Element idLeaf = new Element(Components.ID);
        idLeaf.addContent(v.getIdEntry());
        return idLeaf;
    }

    public Object visit(Type v){
        Element type = new Element(Components.TYPE);
        type.addContent(v.getValue());
        return type;
    }

    public Object visit(ResultTypeOp v){
        Element resultTypeOp = new Element(Components.RESULT_TYPE_OP);
        resultTypeOp.addContent(v.getValue());
        return resultTypeOp;
    }

    private Element statList(ArrayList<StatOp> list){

        Element statList = new Element(Components.STAT_LIST);
        Element stat;

        if(list == null){
            statList.addContent(Components.NULL);
        } else {
            for (StatOp e : list) {
                stat = (Element) ((Visitable) e).accept(this);
                statList.addContent(stat);
            }
        }

        return statList;
    }

    private Element exprList(ArrayList<Expr> list){
        Element exprList = new Element(Components.EXPR_LIST);
        Element expr;

        if(list == null){
            exprList.addContent(Components.NULL);
        }
        else{
            for (Expr e : list) {
                expr = (Element) ((Visitable) e).accept(this);
                exprList.addContent(expr);
            }
        }

        return exprList;
    }

    private Element varDeclList(ArrayList<VarDeclOp> list){

        Element varDeclList = new Element(Components.VAR_DECL_LIST);
        Element expr;

        if(list == null){
            varDeclList.addContent(Components.NULL);
        } else {
            for (VarDeclOp e : list) {
                expr = (Element) ((Visitable) e).accept(this);
                varDeclList.addContent(expr);
            }
        }

        return varDeclList;
    }

    private Element idList(ArrayList<IdLeaf> list){
        Element idList = new Element(Components.ID_LIST);
        Element expr;

        for (Expr e : list) {
            expr = (Element) ((Visitable) e).accept(this);
            idList.addContent(expr);
        }
        return idList;
    }
}
