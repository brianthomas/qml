// CVS $Id$
// IllegalEndElementHandlerFunc.java Copyright (c) 2004 Brian Thomas. All rights reserved.
 
/* LICENSE

   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

*/

/* AUTHOR

   Brian Thomas  (baba-luu@earthlink.net)

*/


package net.datamodel.qml.support.handlers;

// import QML stuff
import net.datamodel.qml.support.EndElementHandlerAction;
import net.datamodel.qml.support.QMLDocumentHandler;

import org.apache.log4j.Logger;

public class IllegalEndElementHandlerFunc implements EndElementHandlerAction 
{

	private static final Logger logger = Logger.getLogger(IllegalEndElementHandlerFunc.class);
	
       public void action (QMLDocumentHandler handler )
       {
          // log error message
          logger.error("ERROR: current node in document but has IllegalEndHandlerType (e.g. schema is probably improperly written!)");
       }
}

