/*
    (c) Copyright 2005 Hewlett-Packard Development Company, LP
    All rights reserved - see end of file.
    $Id: HashedBunchMap.java,v 1.5 2005-10-28 13:34:40 chris-dollin Exp $
*/
package com.hp.hpl.jena.mem;

import java.util.Iterator;

import com.hp.hpl.jena.util.iterator.NiceIterator;

/**
    An implementation of BunchMap that does open-addressed hashing.
    @author kers
*/
public class HashedBunchMap extends HashCommon implements BunchMap
    {
    protected Object [] values;
    
    public HashedBunchMap()
        {
        super( 10 );
        values = new Object[capacity];
        }
    
    /**
        Clear this map: all entries are removed. The keys <i>and value</i> array 
        elements are set to null (so the values may be garbage-collected).
    */
    public void clear()
        { for (int i = 0; i < capacity; i += 1) keys[i] = values[i] = null; }  
    
    public Object get( Object key )
        {
        int slot = findSlot( key );
        return slot < 0 ? values[~slot] : null;
        }

    public void put( Object key, Object value )
        {
        int slot = findSlot( key );
        if (slot < 0)
            values[~slot] = value;
        else
            {
            keys[slot] = key;
            values[slot] = value; 
            size += 1;
            if (size == threshold) grow();
            }
        }

    protected void grow()
        {
        Object [] oldContents = keys, oldValues = values;
        final int oldCapacity = capacity;
        growCapacityAndThreshold();
        keys = new Object[capacity];
        values = new Object[capacity];
        for (int i = 0; i < oldCapacity; i += 1)
            {
            Object key = oldContents[i];
            if (key != null) 
                {
                int j = findSlot( key );
                keys[j] = key;
                values[j] = oldValues[i];
                }
            }
        }

    public void remove( Object key )
        {
        int slot = findSlot( key );
        if (slot < 0) removeFrom( ~slot );
        }

    protected void removeAssociatedValues( int here )
        { values[here] = null; }
    
    protected void moveAssociatedValues( int here, int scan )
        { values[here] = values[scan]; }

    public Iterator keyIterator()
        {
        return new NiceIterator()
            {
            int index = capacity - 1;
            
            public boolean hasNext()
                {
                while (index >= 0 && keys[index] == null) index -= 1;
                return index >= 0;
                }
            
            public Object next()
                {
                if (hasNext() == false) noElements( "bunch map keys" );
                return keys[index--];
                }
            };
        }

    }

/*
 * (c) Copyright 2005 Hewlett-Packard Development Company, LP
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