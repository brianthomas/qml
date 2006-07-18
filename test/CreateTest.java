
/*
    CVS $Id$

    CreateTest.java Copyright (C) 2004 Brian Thomas
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

import java.io.*;
import java.util.*;

import net.datamodel.qml.*;

import org.w3c.dom.*;

import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;

/* Here is a little program showing how to build an  object in
 * a program from scratch, rather than from loading from a file.
 */

// TODO: JUnit instead?

// based on original code in Create.java written by Ping Huang, huang@roamer.gsfc.nasa.gov
// modified to present code by Brian Thomas, thomas@adc.gsfc.nasa.gov 

public class CreateTest extends org.apache.tools.ant.Task
{

    // runtime parameters
    protected String outputfile = "test1.xml";
    protected boolean showSuccess = false;
    protected String createType = "atomic";
    protected String outputType = "";
    protected String domPackageType = "Xerces2";

    //
    // public methods
    //

    public void setOutputType (String type) 
    {
        outputType = type;
    }

    public void setQuantityType (String type) 
    {
        createType = type;
    }

    public void setDOMPackageType (String type) 
    {
        domPackageType = type;
    }

    public void setOutputfile (String strfile) 
    {
       outputfile = strfile;
    }
 
    public void setShowsuccess (boolean val) {
       showSuccess = val;
    }

    public void execute()
    throws BuildException
    {


        try 
        {

            Writer wo = new FileWriter(new File(outputfile));
            Object o = null;

            // select type of quantity to make
            if (createType.equals("atomic"))
            {
                   o = createAtomicQuantity("atom1");
            } 
            else if (createType.equals("component"))
            {
                   o = new ComponentImpl ();
            } 
            else if (createType.equals("composite"))
            {
                   o = createCompositeQuantity("comp1");
            } 
            else if (createType.equals("list"))
            {
                   o = createListQuantity("list1");
            } 
            else if (createType.equals("matrix"))
            {
                   o = new MatrixQuantityImpl ();
            }
            else if (createType.equals("trivial"))
            {
                   o = new TrivialQuantityImpl ();
            }

            Specification.getInstance().setPrettyOutput(true);

            // write it out either standalone, or within our document
            if(outputType.equals("document")) {
               Hashtable prefixMap = new Hashtable();
               QMLDocument doc = null; 

               Class docClass = null;
               if(domPackageType.equals("crimson")) { 
                   docClass = Class.forName("net.datamodel.qml.DOMCrimson.QMLDocumentImpl");
               } else { 
                   docClass = Class.forName("net.datamodel.qml.support.DOMXerces2.QMLDocumentImpl");
               }
               doc = (QMLDocument) docClass.newInstance(); 

               // create the prefix map we want to use
               prefixMap.put("",Constants.QML_NAMESPACE_URI);
               prefixMap.put("xsi",Constants.XML_SCHEMA_INSTANCE_NAMESPACE_URI);
               doc.setPrefixNamespaceMappings(prefixMap);

               QMLElement elem = doc.createQMLElementNS(Constants.QML_NAMESPACE_URI, (Quantity) o);
               String schemaLoc = Constants.QML_NAMESPACE_URI+" "+Constants.QML_SCHEMA_NAME;
               elem.setAttribute("xsi:schemaLocation",schemaLoc);
               doc.setDocumentElement(elem);
               doc.toXMLWriter(wo);
            } else { 
               ((XMLSerializableObject)o).toXMLWriter(wo);
            }

            // close/flush writer
            //wo.flush();
            wo.close();
	} 
        catch (Exception e) 
        {
           throw new BuildException("Failed "+createType+" quantity creation:"+e.getMessage());
        }

        if (showSuccess)
           System.err.println("Success");

    }

    protected AtomicQuantityImpl createAtomicQuantity(String id) throws SetDataException,IllegalAccessException
    {
        AtomicQuantityImpl q = new AtomicQuantityImpl ();
        q.setId(id);
        Locator loc = q.createLocator();
        q.setUnits(new UnitsImpl(""));
        q.setDataType(new StringDataType());
        ((StringDataType) q.getDataType()).setWidth(new Integer(4));
        q.setValue("data",loc);
        return q;
    }

    protected CompositeQuantityImpl createCompositeQuantity(String id) throws SetDataException,IllegalAccessException
    {
        CompositeQuantityImpl c = new CompositeQuantityImpl ();
        c.setId(id);
        AtomicQuantityImpl at1 = createAtomicQuantity("at1");
        AtomicQuantityImpl at2 = createAtomicQuantity("at2");
        c.addMember(at1);
        c.addMember(at2);
        return c;
    }


    protected ListQuantityImpl createListQuantity(String id) throws SetDataException,IllegalAccessException
    {

        ListQuantityImpl lq = new ListQuantityImpl ();
        lq.setId(id);
        AtomicQuantityImpl at1 = createAtomicQuantity("at1");
        Locator loc = lq.createLocator();
  
        lq.addMember(at1);
        lq.setUnits(new UnitsImpl("cm"));
        IntegerDataType idatatype = new IntegerDataType();
        idatatype.setWidth(new Integer(2));
        lq.setDataType(idatatype);

        for (int i = 0; i < 100 ; i++)
        {
           lq.setValue(new Integer(i), loc);
           loc.next();
        }  

        return lq;
    }

    protected MatrixQuantityImpl createMatrixQuantity(String id) throws SetDataException,IllegalAccessException
    {

        MatrixQuantityImpl matrixQ = new MatrixQuantityImpl ();
        AtomicQuantityImpl at1 = createAtomicQuantity("at1");

        matrixQ.setId(id);

        matrixQ.setUnits(new UnitsImpl("cm"));

        matrixQ.addMember(at1);

        ListQuantityImpl xAxis = createListQuantity("x");
        xAxis.setSize(5);
        ListQuantityImpl yAxis = createListQuantity("y");
        yAxis.setSize(2);

        AxisFrame frame = new AxisFrameImpl ();
        frame.setId("cartesian");
        frame.addAxis(xAxis);
        frame.addAxis(yAxis);

        matrixQ.addMember(frame);

        Locator loc = matrixQ.createLocator();

   // add values in list order
    loc.setCurrentAxisFrame(null);
    for(int i =0; i < 10; i++) {
       matrixQ.setValue(new Integer(i), loc);
       loc.next();
    }

        return matrixQ;
    }
}

