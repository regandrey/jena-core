/*
  (c) Copyright 2005 Hewlett-Packard Development Company, LP
  [See end of file]
  $Id: TestGraphMatchWithInference.java,v 1.1 2005-06-13 10:51:58 jeremy_carroll Exp $
*/

package com.hp.hpl.jena.graph.test;

import com.hp.hpl.jena.graph.*;
import com.hp.hpl.jena.graph.impl.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileUtils;

import java.io.*;

import junit.framework.*;

/**
    Test that an inferred graph and an identical concrete graph 
    compare as equal.

 	@author jjc
*/

public class TestGraphMatchWithInference extends GraphTestBase
    {
    public TestGraphMatchWithInference( String name )
        { super( name ); }

   public static TestSuite suite()
        {
        TestSuite result = new TestSuite( TestGraphMatchWithInference.class );
        return result;
        }
        
   
    public void testBasic()
        {
        Model mrdfs = ModelFactory.createRDFSModel(ModelFactory.createDefaultModel());
        Model concrete = ModelFactory.createDefaultModel();
        concrete.add(mrdfs);
        
        assertIsomorphic(concrete.getGraph(),  mrdfs.getGraph() );
        
        assertIsomorphic( mrdfs.getGraph(), concrete.getGraph() );
        }
    
       
    }

/*
    (c) Copyright 2005 Hewlett-Packard Development Company, LP
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:

    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.

    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.

    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/