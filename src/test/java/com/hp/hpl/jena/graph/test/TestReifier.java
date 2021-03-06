/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hp.hpl.jena.graph.test;

import java.lang.reflect.Constructor;

import com.hp.hpl.jena.graph.*;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.graph.impl.ReifierFragmentsMap;
import com.hp.hpl.jena.graph.impl.ReifierTripleMap;
import com.hp.hpl.jena.graph.impl.SimpleReifier;
import com.hp.hpl.jena.graph.impl.SimpleReifierFragmentsMap;
import com.hp.hpl.jena.graph.impl.SimpleReifierTripleMap;
import com.hp.hpl.jena.mem.*;
import com.hp.hpl.jena.mem.faster.GraphMemFaster;
import com.hp.hpl.jena.shared.*;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import junit.framework.*;

/**
    This class tests the reifiers of ordinary GraphMem graphs.
	@author kers
*/

public class TestReifier extends AbstractTestReifier
    {
    public TestReifier( String name )
        { super( name ); graphClass = null; style = null; }
        
    protected final Class<? extends Graph> graphClass;
    protected final ReificationStyle style;
    
    public TestReifier( Class<? extends Graph> graphClass, String name, ReificationStyle style ) 
        {
        super( name );
        this.graphClass = graphClass;
        this.style = style;
        }
        
    public static TestSuite suite()
        { 
        TestSuite result = new TestSuite();
        result.addTest( MetaTestGraph.suite( TestReifier.class, GraphMem.class ) );
        result.addTest( MetaTestGraph.suite( TestReifier.class, GraphMemFaster.class ) );
        result.setName(TestReifier.class.getSimpleName());
        return result; 
        }   
        
    @Override public Graph getGraph()
        { return getGraph( style ); }
    
    @Override public Graph getGraph( ReificationStyle style ) 
        {
        try
            {
            Constructor<?> cons = getConstructor( graphClass, new Class[] {ReificationStyle.class} );
            if (cons != null) return (Graph) cons.newInstance( new Object[] { style } );
            Constructor<?> cons2 = getConstructor( graphClass, new Class [] {this.getClass(), ReificationStyle.class} );
            if (cons2 != null) return (Graph) cons2.newInstance( new Object[] { this, style } );
            throw new JenaException( "no suitable graph constructor found for " + graphClass );
            }
        catch (RuntimeException e)
            { throw e; }
        catch (Exception e)
            { throw new JenaException( e ); }
        }        
    
    public void testExtendedConstructorExists()
        {
        GraphBase parent = new GraphBase() {

            @Override public ExtendedIterator<Triple> graphBaseFind( TripleMatch m )
                {
                // TODO Auto-generated method stub
                return null;
                }};
        ReifierTripleMap tm = new SimpleReifierTripleMap();
        ReifierFragmentsMap fm = new SimpleReifierFragmentsMap();
        SimpleReifier sr = new SimpleReifier( parent, tm, fm, ReificationStyle.Minimal );
        }
    }
