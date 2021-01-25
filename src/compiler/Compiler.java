package compiler;

import code_generation.CodeGeneratorVisitor;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import semanticanalysis.TableAmplifierVisitor;
import org.jdom2.Document;
import org.jdom2.Element;
import semanticanalysis.TypeCheckingVisitor;
import syntaxanalysis.*;

import java.io.*;

public class Compiler {

    public static void main(String[] args) throws Exception {
        if(args.length == 0){
            throw new Exception("Path del file non presente come argomento");
        }

        String filePath = args[0];
        parser p = new parser(new Lexer(new FileReader(filePath)));
        p.parse();

        //Chiamata al visitor per la costruzione dell'albero
        TreeVisitor treeVisitor = new TreeVisitor();
        Element root = (Element) p.root.accept(treeVisitor);
        Document doc = new Document();
        doc.setRootElement(root);

        XMLOutputter xmlOut = new XMLOutputter();
        xmlOut.setFormat(Format.getPrettyFormat());
        String xml = xmlOut.outputString(doc);
        SAXBuilder builder = new SAXBuilder(XMLReaders.NONVALIDATING);
        String fileName = args[0].substring(args[0].indexOf("/")+1);
        try{
            Document doc2 = builder.build(new StringReader(xml));
            FileOutputStream out = new FileOutputStream(new File("xml_files/" + fileName +".xml"));
            xmlOut.output(doc2, out);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //Chiamata al visitor per arricchimento symboltable
        TableAmplifierVisitor tableVisitor = new TableAmplifierVisitor();
        p.root.accept(tableVisitor);

        PrintStream ps = new PrintStream(new File("generated_c_files/" + fileName.replace(".toy", "") + "_SymbolTable.txt"));
        ps.println("SYMBOL TABLE \n =====================");
        ps.println("Taglia dello stack: " + StackEnv.stack.size());
        for(Env e: StackEnv.stack){
            ps.println(e.getName());
            ps.println(e.getTable());
        }

        //Chiamata al visitor per il Type Checking
        TypeCheckingVisitor typeVisitor = new TypeCheckingVisitor();
        p.root.accept(typeVisitor);

        CodeGeneratorVisitor codeVisitor = new CodeGeneratorVisitor(args[0]);
        p.root.accept(codeVisitor);

    }
}
