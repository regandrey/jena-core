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

package com.hp.hpl.jena.reasoner.rulesys.test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.InfGraph;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.rulesys.DAMLMicroReasonerFactory;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test harness for checking that graphs generated by the main
 * reasoners report the correct capabilities to things like the RDF writer.
 *
 * @author <a href="mailto:der@hplb.hpl.hp.com">Dave Reynolds</a>
 * @version $Revision: 1.1 $
 */

public class TestCapabilities extends TestCase {

    /**
     * Boilerplate for junit
     */
    public TestCapabilities( String name ) {
        super( name );
    }

    /**
     * Boilerplate for junit.
     * This is its own test suite
     */
    public static TestSuite suite() {
        return new TestSuite( TestCapabilities.class );
    }

    /**
     * Test capability returns.
     */
    public void testCapabilityValues() {
        Object[][] testSpec = new Object[][] {
                {ReasonerRegistry.getOWLMicroReasoner(), Boolean.TRUE},
                {ReasonerRegistry.getOWLMiniReasoner(), Boolean.TRUE},
                {ReasonerRegistry.getOWLReasoner(), Boolean.FALSE},
//                {ReasonerRegistry.getDIGReasoner(), Boolean.FALSE},
                {ReasonerRegistry.getRDFSReasoner(), Boolean.TRUE},
                {ReasonerRegistry.getRDFSSimpleReasoner(), Boolean.TRUE},
                {DAMLMicroReasonerFactory.theInstance().create(null), Boolean.TRUE},
        };
        Model data = ModelFactory.createDefaultModel();
        for (int i = 0; i < testSpec.length; i++) {
            Object[] test = testSpec[i];
            Reasoner r = (Reasoner)test[0];
            Boolean safe = (Boolean)test[1];
            InfGraph ig = r.bind(data.getGraph());
            assertEquals(r.toString(), safe.booleanValue(), ig.getCapabilities().findContractSafe());
        }
    }
}
