/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package javax.json.stream;

import javax.json.JsonArrayVisitor;
import javax.json.JsonObjectVisitor;
import javax.json.JsonVisitor;
import java.io.Closeable;
import java.io.Reader;
import java.io.StringReader;

/**
 * A JSON push parser.
 * <p>
 * This class parses JSON and calls the appropriate visit methods of a
 * specified JSON visitor. The push parser can be created as follows:
 *
 * <p>
 * For Example:
 * <code>
 * <pre>
 * JsonVisitor visitor = ...;
 * JsonPushParser parser = new JsonPushParser(...);
 * parser.accept(visitor);
 * parser.close();
 * </pre>
 * </code>
 *
 * <p> For the empty JSON object {}, visitor's visitObject() and the returned
 * object visitor's visitEnd() are called.
 *
 *
 * @author Jitendra Kotamraju
 */
public class JsonPushParser implements /*Auto*/Closeable {

    /**
     * Creates a JSON reader which can be used to parse JSON.
     *
     * @param reader from which JSON is read
     * @return a JSON reader
     */
    public JsonPushParser(Reader reader) {
    }

    /**
     * Calls the appropriate visit methods of a specified JSON visitor.
     *
     * @param visitor a JSON visitor
     * @throws IllegalStateException if called more than once
     */
    public void accept(JsonVisitor visitor) {
    }

    /**
     * Calls the appropriate visit methods of a specified JSON array visitor.
     * If it is known in advance that the JSON that is being parsed is an array,
     * then this method can be used.
     *
     * @param visitor a JSON array visitor
     */
    public void acceptArray(JsonArrayVisitor visitor) {
    }

    /**
     * Calls the appropriate visit methods of a specified JSON object visitor.
     * If it is known in advance that the JSON that is being parsed is an
     * object, then this method can be used.
     *
     * @param visitor a JSON object visitor
     */
    public void acceptObject(JsonObjectVisitor visitor) {
    }

    /**
     * Closes this parser and frees any resources associated with the
     * parser. This doesn't close the underlying input source.
     */
    @Override
    public void close() {
    }
    
    private void test() {
        JsonPushParser parser = new JsonPushParser(new StringReader("[]"));
        parser.accept(new JsonVisitor(){
            @Override
            public JsonArrayVisitor visitArray() {
                return null;
            }

            @Override
            public JsonObjectVisitor visitObject() {
                return null;
            }
        });
        parser.close();
    }

}