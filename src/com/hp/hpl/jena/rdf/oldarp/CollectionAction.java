/*
  (c) Copyright 2003, 2004, 2005 Hewlett-Packard Development Company, LP
  all rights reserved.
  [See end of file]
  $Id: CollectionAction.java,v 1.1 2005-08-01 15:07:05 jeremy_carroll Exp $
*/
package com.hp.hpl.jena.rdf.oldarp;

/**
 * This stuff looks somewhat complicated.
 * The original code has a recursive call to do
 * tails of collections.
 * However, this led to a stack overflow.
 * In keeping with the streaming parser goals
 * the current code outputs triples as fast as it
 * can, without any stack.
 * @author jjc
 *
 */
abstract class CollectionAction {
	ParserSupport X;
	
	CollectionAction(ParserSupport x){
		X=x;
	}
	abstract void terminate();
	
	abstract CollectionAction next(AResourceInternal r);
	/**
	 * 
	 */
	abstract public void cleanUp();
	

}

/*
	(c) Copyright 2003, 2004, 2005 Hewlett-Packard Development Company, LP
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