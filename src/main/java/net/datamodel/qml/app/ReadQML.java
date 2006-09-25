
package net.datamodel.qml.app;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.datamodel.qml.support.Constants;
import net.datamodel.qml.support.QMLDocument;
import net.datamodel.qml.support.QMLReader;
import net.datamodel.qml.support.Specification;
import net.datamodel.qml.support.DOMXerces2.QMLDocumentImpl;

/**
 * A little example program showing some manipulation of QML
 * object after it has been read in. This version uses Crimson.
 */
public class ReadQML {

	  //public ReadQML() { }

	  public static void main(String[] argv) {

	    // declare the structure
	    QMLDocument doc = new QMLDocumentImpl();
	    QMLReader r = new QMLReader(doc);

	    Specification spec = Specification.getInstance();

	    try {

	      // parse contents of file into QMLDocument
	      r.parseFile(argv[0]);

	    } catch (java.io.IOException e ) { 
	      System.err.println("ReadQML cant parse file:"+argv[0]+"\n"+e.getMessage());
	    }

	    // output
	    System.err.println("-----\nPRINT OUT STRUCTURE\n-----");

	    // set the output specification
	    spec.setPrettyOutput(true);
	    spec.setPrettyOutputIndentation("  ");
	    //spec.setSerializeValuesStyle(Constants.VALUE_SERIALIZE_TAGGED);
	    spec.setSerializeValuesStyle(Constants.VALUE_SERIALIZE_CONTAINER);
	    //spec.setSerializeRefQuantityStyle(Constants.REF_QUANTITY_EXPAND);

	    try {

	       Writer myWriter = new BufferedWriter(new OutputStreamWriter(System.err));
	       doc.toXMLWriter(myWriter);
	       myWriter.flush();

	/*
	       Element root = doc.getDocumentElement();
	       if (root instanceof QMLElement) {
	          SemanticObject q = ((QMLElement) root).getQuantity();
	          System.err.println("-----\nINTERNAL REP:");
	          ((XMLSerializableObject)q).toXMLWriter(myWriter);
	          myWriter.flush();
	       }
	*/

	    } catch (IOException e ) {
	      System.err.println("Cant print out structure");
	    }

	      System.err.println("-----\nFINISHED");

	/*
	     List qList = doc.getQuantityList(false);
	     System.err.println(" there were "+qList.size()+" top level Q's");
	     Iterator iter = qList.iterator();
	     while (iter.hasNext()) {
	        System.err.println(" Q: "+iter.next());
	     }

	     List qListDeep = doc.getQuantityList(true);
	     System.err.println(" there were "+qListDeep.size()+" total Q's");
	     Iterator iter2 = qListDeep.iterator();
	     while (iter2.hasNext()) {
	        SemanticObject q = (SemanticObject) iter2.next();
	        String id = q.getId();
	        if(id == null) id = "";
	        System.err.println(" deep Q ["+id+"]: "+q);
	     }
	*/

  }

}
