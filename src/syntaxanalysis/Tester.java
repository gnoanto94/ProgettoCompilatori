package syntaxanalysis;

import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import tree.TreeVisitor;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.*;

public class Tester {

    public static void main(String[] args) throws Exception {
        if(args.length == 0){
            throw new Exception("Path del file non presente come argomento");
        }

        String filePath = args[0];
        parser p = new parser(new Lexer(new FileReader(filePath)));
        p.parse();

        System.out.println("SYMBOL TABLE \n =====================");
//        for(Env e = p.currentTable; e != null; e = e.getNext()){
//            System.out.println(e.getTable());
//        }
        System.out.println("Taglia dello stack: " + StackEnv.stack.size());
        for(Env e: StackEnv.stack){
            System.out.println(e.getTable());
        }

        TreeVisitor visitor = new TreeVisitor();
        Element root = (Element) p.root.accept(visitor);
        Document doc = new Document();
        doc.setRootElement(root);

        XMLOutputter xmlOut = new XMLOutputter();
        xmlOut.setFormat(Format.getPrettyFormat());
        String xml = xmlOut.outputString(doc);
        SAXBuilder builder = new SAXBuilder(XMLReaders.NONVALIDATING);

        try{
            Document doc2 = builder.build(new StringReader(xml));
            String fileName = args[0].substring(args[0].indexOf("/")+1);
            FileOutputStream out = new FileOutputStream(new File("xml_files/" + fileName +".xml"));
            xmlOut.output(doc2, out);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
