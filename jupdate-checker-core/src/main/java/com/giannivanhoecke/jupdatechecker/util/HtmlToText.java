/*
 * Copyright (c) 2013 Gianni Van Hoecke <gianni.vh@gmail.com>
 */

package com.giannivanhoecke.jupdatechecker.util;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.Reader;

/**
 * User: gvhoecke <gianni.vanhoecke@lin-k.net>
 */
public class HtmlToText extends HTMLEditorKit.ParserCallback {

    StringBuffer s;

    public void parse( Reader in )
            throws IOException {

        s = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        //The third parameter is TRUE to ignore charset directive
        delegator.parse( in, this, Boolean.TRUE );
    }

    public void handleText( char[] text, int pos ) {

        s.append( text );
    }

    public String getText() {

        return s.toString();
    }
}