
/*
    CVS $Id$

    LoadTest.java Copyright (C) 2004 Brian Thomas
    GSFC-NASA, Code 695, Greenbelt MD, 20771

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/

package test;

import net.datamodel.qml.*;

import java.util.List;
import java.util.Iterator;
import java.util.Hashtable;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import org.apache.tools.ant.BuildException;

public class LoadTest extends org.apache.tools.ant.Task {

  private String inputfile;
  private String outputfile;
  private boolean showSuccess = false;
  private String dompackageType = "xerces2"; // may also be "crimson";

  public void setDompackagetype (String type) {
      dompackageType = type;
  }

  public void setInputfile (String strfile) {
     inputfile = strfile;
  }

  public void setShowsuccess (boolean val) {
     showSuccess = val;
  }

  public void setOutputfile (String strfile) {
     outputfile = strfile;
  }

  public void execute() 
  throws BuildException 
  {

     // declare the structure
     QMLDocument doc = null;
     Class docClass = null;
     try {
        if(dompackageType.equals("crimson")) {
           docClass = Class.forName("net.datamodel.qml.DOMCrimson.QMLDocumentImpl");
        } else {
           docClass = Class.forName("net.datamodel.qml.support.DOMXerces2.QMLDocumentImpl");
        }
        doc = (QMLDocument) docClass.newInstance();
     } catch (Exception e ) {
        throw new BuildException (e.getMessage());
     }

     // create the reader
     QMLReader r = new QMLReader(doc);

     try {

        r.parseFile(inputfile);

     } catch (java.io.IOException e ) { 
        throw new BuildException("Failed: Cant parse file:"+inputfile+"\n"+e.getMessage());
     } catch (Exception e ) { 
        throw new BuildException(e);
     }

     // set the output specification
     Specification.getInstance().setPrettyOutput(true);
     Specification.getInstance().setPrettyOutputIndentation("  ");

     try {

        // Writer myWriter = new BufferedWriter(new OutputStreamWriter(System.err));
        Writer myWriter = new BufferedWriter(new FileWriter(outputfile));
        doc.toXMLWriter(myWriter);
        myWriter.flush();

     } catch (Exception e ) {
        throw new BuildException("Failed: to re-write object back out.\n"+e.getMessage());
     }

     if (showSuccess) 
        System.err.println("Success");

  }

}


