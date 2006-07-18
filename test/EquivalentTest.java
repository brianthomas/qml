
/*
    CVS $Id$

    EquivalentTest.java Copyright (C) 2004 Brian Thomas
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

public class EquivalentTest extends CreateTest
{

    //
    // public methods
    //

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
            ((XMLSerializableObject)o).toXMLWriter(wo);

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

}

