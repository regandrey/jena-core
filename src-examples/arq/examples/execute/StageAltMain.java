/*
 * (c) Copyright 2007 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package arq.examples.execute;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.util.QueryExecUtils;
import com.hp.hpl.jena.sparql.util.StringUtils;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;

/** Example to execute a query but handle the
 *  basic graph patterns in the query in some special way.
 *  Stages are one step in executing a basic graph pattern (BGP).
 *  A StageGenerator builds a StageList and the stage list
 *  is executes with the output (a QueryIterator) of the previous
 *  stage fed int the current stage. 
 */  

public class StageAltMain
{
    static String NS = "http://example/" ;

    public static void main(String[] argv)
    {
        String[] queryString = 
        {
            "PREFIX ns: <"+NS+">" ,
            "SELECT ?v ",
            "{ ?s ns:p1 'xyz' ;",
            "     ns:p2 ?v }"
        } ;

        Query query = QueryFactory.create( StringUtils.join("\n", queryString)) ;
        StageAltEngine.register() ;
        
        QueryExecution engine = QueryExecutionFactory.create(query, makeData()) ;
        QueryExecUtils.executeQuery(query, engine) ;
    }
    
    private static Model makeData()
    {
        Model model = ModelFactory.createDefaultModel() ;
        Resource r = model.createResource(NS+"r") ;
        Property p1 = model.createProperty(NS+"p1") ;
        Property p2 = model.createProperty(NS+"p2") ;
        model.add(r, p1, "xyz") ;
        model.add(r, p2, "abc") ;
        return model ;
    }
}

/*
 * (c) Copyright 2007 Hewlett-Packard Development Company, LP
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */