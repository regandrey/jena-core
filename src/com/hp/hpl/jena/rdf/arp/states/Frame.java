/*
 * (c) Copyright 2005 Hewlett-Packard Development Company, LP
 * [See end of file]
 */

package com.hp.hpl.jena.rdf.arp.states;
import java.net.URISyntaxException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;

import com.hp.hpl.jena.rdf.arp.ARPErrorNumbers;
import com.hp.hpl.jena.rdf.arp.impl.ARPString;
import com.hp.hpl.jena.rdf.arp.impl.AttributeLexer;
import com.hp.hpl.jena.rdf.arp.impl.Names;
import com.hp.hpl.jena.rdf.arp.impl.ParserSupport;
import com.hp.hpl.jena.rdf.arp.impl.URIReference;
import com.hp.hpl.jena.rdf.arp.impl.XMLContext;
import com.hp.hpl.jena.rdf.arp.impl.XMLHandler;

public abstract class Frame extends ParserSupport implements  Names, FrameI, ARPErrorNumbers {
    final XMLContext xml;
    final FrameI parent;
    public XMLContext getXMLContext() {
        return xml;
    }
    public FrameI getParent() {
        return parent;
    }
    public Frame(FrameI p, AttributeLexer ap) throws SAXParseException {
        super(p.getXMLHandler());
        xml = ap.xml(p.getXMLContext());
        parent = p;
    }
    public Frame(FrameI p, XMLContext x) {
        super(p.getXMLHandler());
        xml = x;
        parent = p;
    }
    public Frame(XMLHandler a, XMLContext x) {
        super(a);
        xml = x;
        parent = null;
    }
    public XMLHandler getXMLHandler() {
        return arp;
    }
    public void comment(char[] ch, int start, int length) throws SAXParseException {
     // generally ignore
     }
    
//    public void checkIdSymbol(String id) throws SAXParseException {
//        checkIdSymbol(xml,id);
//    }
    /**
      * endElement is called on the state of the frame created
      * by the matching startElement.
     * @throws SAXParseException 
      *
      */
      public   void endElement() throws SAXParseException {
            // often nothing
        }
    public void processingInstruction(String target, String data)  throws SAXParseException {
       // generally ignore

        warning(
            WARN_PROCESSING_INSTRUCTION_IN_RDF,
            "A processing instruction is in RDF content. No processing was done."
                );
     }
    String resolve(XMLContext x, String uri) throws SAXParseException {
        try {
            return x.resolve(uri);
        }
        catch (URISyntaxException e) {
            badURI(uri,e);
            return uri;
        }
    }
    void    processPropertyAttributes(AttributeLexer ap,
            Attributes atts, XMLContext x) throws SAXParseException {
        if (ap.type!=null) {
            ((HasSubjectFrameI)this).aPredAndObj(RDF_TYPE,
                    URIReference.resolve(this,x,ap.type) );
        }
        int sz = atts.getLength();
        if (ap.count != sz) {
            for (int i=0;i<sz;i++) {
                if (!ap.done.get(i)) {
                    ((HasSubjectFrameI)this).aPredAndObj(
                            URIReference.fromQName(this,atts.getURI(i),atts.getLocalName(i)),
                            new ARPString(this,atts.getValue(i),x.getLang()));
                }
            }
        }
    }
    public void abort() {
      // nothing.
    }

}


/*
 *  (c) Copyright 2005 Hewlett-Packard Development Company, LP
 *  All rights reserved.
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
 