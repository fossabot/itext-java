/*
 * This class is based on the C# open source freeware library Clipper:
 * http://www.angusj.com/delphi/clipper.php
 * The original classes were distributed under the Boost Software License:
 *
 * Freeware for both open source and commercial applications
 * Copyright 2010-2014 Angus Johnson
 * Boost Software License - Version 1.0 - August 17th, 2003
 *
 * Permission is hereby granted, free of charge, to any person or organization
 * obtaining a copy of the software and accompanying documentation covered by
 * this license (the "Software") to use, reproduce, display, distribute,
 * execute, and transmit the Software, and to prepare derivative works of the
 * Software, and to permit third-parties to whom the Software is furnished to
 * do so, all subject to the following:
 *
 * The copyright notices in the Software and this entire statement, including
 * the above license grant, this restriction and the following disclaimer,
 * must be included in all copies of the Software, in whole or in part, and
 * all derivative works of the Software, unless such copies or derivative
 * works are solely in the form of machine-executable object code generated by
 * a source language processor.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDERS OR ANYONE DISTRIBUTING THE SOFTWARE BE LIABLE
 * FOR ANY DAMAGES OR OTHER LIABILITY, WHETHER IN CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */
package com.itextpdf.kernel.pdf.canvas.parser.clipper;

import java.util.ArrayList;

/**
 * A pure convenience class to avoid writing {@code List<Path>} everywhere.
 */
public class Paths extends ArrayList<Path> {

    public static Paths closedPathsFromPolyTree( PolyTree polytree ) {
        final Paths result = new Paths();
        //        result.Capacity = polytree.Total;
        result.addPolyNode( polytree, PolyNode.NodeType.CLOSED );
        return result;
    }

    public static Paths makePolyTreeToPaths( PolyTree polytree ) {

        final Paths result = new Paths();
        //        result.Capacity = polytree.Total;
        result.addPolyNode( polytree, PolyNode.NodeType.ANY );
        return result;
    }

    public static Paths openPathsFromPolyTree( PolyTree polytree ) {
        final Paths result = new Paths();
        //        result.Capacity = polytree.ChildCount;
        for (final PolyNode c : polytree.getChilds()) {
            if (c.isOpen()) {
                result.add( c.getPolygon() );
            }
        }
        return result;
    }

    /**
     *
     */

    public Paths() {
        super();
    }

    public Paths( int initialCapacity ) {
        super( initialCapacity );
    }

    public void addPolyNode( PolyNode polynode, PolyNode.NodeType nt ) {
        boolean match = true;
        switch (nt) {
            case OPEN:
                return;
            case CLOSED:
                match = !polynode.isOpen();
                break;
            default:
                break;
        }

        if (polynode.getPolygon().size() > 0 && match) {
            add( polynode.getPolygon() );
        }
        for (final PolyNode pn : polynode.getChilds()) {
            addPolyNode( pn, nt );
        }
    }

    public Paths cleanPolygons() {
        return cleanPolygons( 1.415 );
    }

    public Paths cleanPolygons( double distance ) {
        final Paths result = new Paths( size() );
        for (int i = 0; i < size(); i++) {
            result.add( get( i ).cleanPolygon( distance ) );
        }
        return result;
    }

    public LongRect getBounds() {

        int i = 0;
        final int cnt = size();
        final LongRect result = new LongRect();
        while (i < cnt && get( i ).isEmpty()) {
            i++;
        }
        if (i == cnt) {
            return result;
        }

        result.left = get( i ).get( 0 ).getX();
        result.right = result.left;
        result.top = get( i ).get( 0 ).getY();
        result.bottom = result.top;
        for (; i < cnt; i++) {
            for (int j = 0; j < get( i ).size(); j++) {
                if (get( i ).get( j ).getX() < result.left) {
                    result.left = get( i ).get( j ).getX();
                }
                else if (get( i ).get( j ).getX() > result.right) {
                    result.right = get( i ).get( j ).getX();
                }
                if (get( i ).get( j ).getY() < result.top) {
                    result.top = get( i ).get( j ).getY();
                }
                else if (get( i ).get( j ).getY() > result.bottom) {
                    result.bottom = get( i ).get( j ).getY();
                }
            }
        }
        return result;
    }

    public void reversePaths() {
        for (final Path poly : this) {
            poly.reverse();
        }
    }

}
