/*
  (c) Copyright 2002, Hewlett-Packard Development Company, LP
  [See end of file]
  $Id: PrefixMappingImpl.java,v 1.16 2004-07-02 11:39:58 chris-dollin Exp $
*/

package com.hp.hpl.jena.shared.impl;

import com.hp.hpl.jena.rdf.model.impl.Util;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.HashUtils;

import java.util.*;
import org.apache.xerces.util.XMLChar;

/**
    An implementation of PrefixMapping. The mappings are stored in a hash
    map, with the reverse lookup done with a linear search. This may need
    improving but could get complicated. The test for a legal prefix is left to
    xerces's XMLChar.isValidNCName() predicate.
        
 	@author kers
*/
public class PrefixMappingImpl implements PrefixMapping
    {
    private Map map;
    private boolean locked;
    
    public PrefixMappingImpl()
        { map = HashUtils.createMap(); }
           
    public PrefixMapping lock()
        { 
        locked = true; 
        return this;
        }
        
    public PrefixMapping setNsPrefix( String prefix, String uri ) 
        {
        checkUnlocked();
        checkLegal( prefix );
        if (!prefix.equals( "" )) removeExistingNonDefault( uri );
        if (!prefix.equals( "" )) checkProper( uri );
        map.put( prefix, uri );
        return this;
        }
    
    public PrefixMapping removeNsPrefix( String prefix )
        {
        checkUnlocked();
        map.remove( prefix );
        return this;
        }
        
    protected void checkUnlocked()
        { if (locked) throw new JenaLockedException( this ); }
        
    private void checkProper( String uri )
        {
        if (!isNiceURI( uri )) throw new RuntimeException( "horrible " + uri );
        }
        
    public static boolean isNiceURI( String uri )
        {
        if (uri.equals( "" )) return false;
        char last = uri.charAt( uri.length() - 1 );
        return Util.notNameChar( last ); // )last == '/' || last == '#';
        }
 
    private void removeExistingNonDefault( String uri )
        {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext())
            {
            Map.Entry e = (Map.Entry) it.next();
            if (e.getValue().equals( uri )
                && !e.getKey().equals("")) 
                {
                map.remove( e.getKey() );
                return;
                }
            }
        }
        
    /**
        Add the bindings of other to our own. We defer to the general case 
        because we have to ensure the URIs are checked.
        
        @param other the PrefixMapping whose bindings we are to add to this.
    */
    public PrefixMapping setNsPrefixes( PrefixMapping other )
        { return setNsPrefixes( other.getNsPrefixMap() ); }
        
    /**
        Add the bindings in the map to our own. This will fail with a ClassCastException
        if any key or value is not a String; we make no guarantees about order or
        completeness if this happens. It will fail with an IllegalPrefixException if
        any prefix is illegal; similar provisos apply.
        
         @param other the Map whose bindings we are to add to this.
    */
    public PrefixMapping setNsPrefixes( Map other )
        {
        checkUnlocked();
        Iterator it = other.entrySet().iterator();
        while (it.hasNext())
            {
            Map.Entry e = (Map.Entry) it.next();
            setNsPrefix( (String) e.getKey(), (String) e.getValue() );
            }
        return this;
        }
         
    /**
        Checks that a prefix is "legal" - it must be a valid XML NCName.
    */
    private void checkLegal( String prefix )
        {
        if (prefix.length() > 0 && !XMLChar.isValidNCName( prefix ))
            throw new PrefixMapping.IllegalPrefixException( prefix ); 
        }
        
    public String getNsPrefixURI( String prefix ) 
        { return (String) map.get( prefix ); }
        
    public Map getNsPrefixMap()
        { return HashUtils.createMap( map ); }
        
    public String getNsURIPrefix( String uri )
        {
        Map.Entry e = findMapping( uri, false );
        return e == null ? null : (String) e.getKey();
        }
        
    /**
        Expand a prefixed URI. There's an assumption that any URI of the form
        Head:Tail is subject to mapping if Head is in the prefix mapping. So, if
        someone takes it into their heads to define eg "http" or "ftp" we have problems.
    */
    public String expandPrefix( String prefixed )
        {
        int colon = prefixed.indexOf( ':' );
        if (colon < 0) 
            return prefixed;
        else
            {
            String prefix = prefixed.substring( 0, colon );
            String uri = (String) map.get( prefix );
            return uri == null ? prefixed : uri + prefixed.substring( colon + 1 );
            } 
        }
        
    /**
        Answer a readable (we hope) representation of this prefix mapping.
    */
    public String toString()
        { return "pm:" + map; }
        
    /**
        Answer the qname for <code>uri</code> which uses a prefix from this
        mapping, or null if there isn't one.
    <p>
        Relies on <code>splitNamespace</code> to carve uri into namespace and
        localname components; this ensures that the localname is legal and we just
        have to (reverse-)lookup the namespace in the prefix table.
        
     	@see com.hp.hpl.jena.shared.PrefixMapping#qnameFor(java.lang.String)
     */
    public String qnameFor( String uri )
        { 
        int split = Util.splitNamespace( uri );
        String ns = uri.substring( 0, split ), local = uri.substring( split );
        if (local.equals( "" )) return null;
        Map.Entry e = findMapping( ns, false );
        return e == null ? null : (String) e.getKey() + ":" + local;
        }
    
    /**
        Obsolete - use shortForm.
     	@see com.hp.hpl.jena.shared.PrefixMapping#usePrefix(java.lang.String)
     */
    public String usePrefix( String uri )
        { return shortForm( uri ); }
    
    /**
        Compress the URI using the prefix mapping. This version of the code looks
        through all the maplets and checks each candidate prefix URI for being a
        leading substring of the argument URI. There's probably a much more
        efficient algorithm available, preprocessing the prefix strings into some
        kind of search table, but for the moment we don't need it.
    */
    public String shortForm( String uri )
        {
        Map.Entry e = findMapping( uri, true );
        return e == null ? uri : e.getKey() + ":" + uri.substring( ((String) e.getValue()).length() );
        }
        
    /**
        Answer a map entry in which the value is an initial substring of <code>uri</code>.
        If <code>partial</code> is false, then the value must equal <code>uri</code>.
        
        Does a linear search of the entire map, so not terribly efficient for large maps.
        
        @param uri the value to search for
        @param true if the match can be any leading substring, false for exact match
        @return some entry (k, v) such that uri starts with v [equal for partial=false]
    */
    private Map.Entry findMapping( String uri, boolean partial )
        {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext())
            {
            Map.Entry e = (Map.Entry) it.next();
            String ss = (String) e.getValue();
            if (uri.startsWith( ss ) && (partial || ss.length() == uri.length())) return e;
            } 
        return null;         
        }    

    }


/*
    (c) Copyright 2003 Hewlett-Packard Development Company, LP
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