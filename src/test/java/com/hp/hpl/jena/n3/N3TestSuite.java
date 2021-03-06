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

package com.hp.hpl.jena.n3;

import junit.framework.* ;

/**
 * @author		Andy Seaborne
 * @version 	$Id: N3TestSuite.java,v 1.1 2009-06-29 18:42:06 andy_seaborne Exp $
 */
public class N3TestSuite extends TestSuite
{
	/* JUnit swingUI needed this */
    static public TestSuite suite() {
        return new N3TestSuite() ;
    }
	
	
	private N3TestSuite()
	{
		super("N3") ;
        addTest(TestResolver.suite()) ;
//		addTest(new N3InternalTests()) ;
//		addTest(new N3ExternalTests()) ;
//		addTest(new N3JenaReaderTests()) ;
		addTest(new N3JenaWriterTests()) ;
	}
}
