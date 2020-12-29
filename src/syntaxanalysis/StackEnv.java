package syntaxanalysis;

import java.util.ArrayList;

public class StackEnv {

    public static ArrayList<Env> stack = new ArrayList<>();
    private static Env saved;
    private static Env top;

    /**
     * Eseguo il push di una Tabella nello stack
     * @param e
     * @return il top dello stack
     */
    public static Env push(Env e){
        if(stack.size() != 0){
            saved = stack.get(0);
        } else {
            saved = null;
        }
        stack.add(0, e);
        return top = stack.get(0);
    }

    /**
     * Eseguo il pop della tabella corrente
     * @return il top dello stack
     */
    public static Env pop(){
        //stack.remove(0);
        //ripristino il top precedente
        stack.remove(saved);
        Env e = stack.set(0, saved);
        if(!stack.contains(e)){
            stack.add(e);
        }
        return top = stack.get(0);
    }

    public static Env top(){
        return top;
    }
}
