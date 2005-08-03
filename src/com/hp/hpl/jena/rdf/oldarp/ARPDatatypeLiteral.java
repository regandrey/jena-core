/*
 * (c) Copyright 2002, 2003, 2004, 2005 Hewlett-Packard Development Company, LP  
 * [see end of file]
 */

package com.hp.hpl.jena.rdf.oldarp;


/**
 * @author Jeremy J. Carroll
 * 
 */
class ARPDatatypeLiteral implements ALiteral {

    final private String datatype;
   // final private String lang;
    final private String lexForm;
    ARPDatatypeLiteral(ARPString lexf,URIReference dt){
       datatype = dt.getURI();
    //   lang = lexf.getLang();
       lexForm = lexf.toString();
    }
    /**
     * @see com.hp.hpl.jena.rdf.oldarp.ALiteral#isWellFormedXML()
     */
    public boolean isWellFormedXML() {
        return false; //datatype.equals(ARPString.RDFXMLLiteral);
    }
    /**
     * @see com.hp.hpl.jena.rdf.oldarp.ALiteral#getParseType()
     */
    public String getParseType() {
        return null;
    }
    public String toString() {
        return lexForm;
    }

    /**
     * @see com.hp.hpl.jena.rdf.oldarp.ALiteral#getDatatypeURI()
     */
    public String getDatatypeURI() {
        return datatype;
    }

    /**
     * @see com.hp.hpl.jena.rdf.oldarp.ALiteral#getLang()
     */
    public String getLang() {
        return ""; //lang;
    }

}

/*
 * (c) Copyright 2002, 2003, 2004, 2005 Hewlett-Packard Development Company, LP
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