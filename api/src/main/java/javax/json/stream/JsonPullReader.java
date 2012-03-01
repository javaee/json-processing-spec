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

import javax.json.spi.JsonProvider;
import javax.json.tree.JsonArray;
import javax.json.tree.JsonObject;
import java.io.Closeable;
import java.io.Reader;

/**
 * A JSON pull parser.
 *
 * @author Jitendra Kotamraju
 */
/*
  Notes to myself:
  1. Create event objects. Improves type safety, but what about
  performance ?

 */
public abstract class JsonPullReader implements Iterable<JsonPullReader.Event>, /*Auto*/Closeable {
    /**
     * Event for parser state while parsing the JSON
     */
    public enum Event {
        /**
         * Event for start of an array
         */
        START_ARRAY,
        /**
         * Event for start of an object
         */
        START_OBJECT,
        /**
         * Event for name in name/value pair in an object
         */
        KEY_NAME,
        /**
         * Event for string value
         */
        VALUE_STRING,
        /**
         * Event for number value
         */
        VALUE_NUMBER,
        /**
         * Event for true value
         */
        VALUE_TRUE,
        /**
         * Event for false value
         */
        VALUE_FALSE,
        /**
         * Event for null value
         */
        VALUE_NULL,
        /**
         * Event for end of an object
         */
        END_OBJECT,
        /**
         * Event for end of an array
         */
        END_ARRAY
    }

    /**
     * Returns name when the state is {@link Event#KEY_NAME} or returns string value when the state is
     * {@link Event#VALUE_STRING}
     * 
     * @return a string
     * @throws IllegalStateException when the event state is not in KEY_NAME or VALUE_STRING
     */
    public abstract String getString();

    /**
     * Returns a JSON number when the state is {@link Event#VALUE_NUMBER}
     *
     * @return a number
     * @throws IllegalStateException when the state is not VALUE_NUMBER
     */
    public abstract Number getNumber();

    /**
     * Closes this reader and frees any resources associated with the
     * reader. This doesn't close the underlying input source.
     */
    @Override
    public abstract void close();

    /**
     * Creates a JSON pull reader from a character stream
     *
     * @param reader a reader from which JSON is to be read
     * @return a JSON pull reader
     */
    public static JsonPullReader create(Reader reader) {
        return JsonProvider.provider().createJsonPullReader(reader);
    }

    /**
     * Creates a JSON pull reader from a JSON array
     *
     * @param array a JSON array
     * @return a JSON pull reader
     */
    public static JsonPullReader create(JsonArray array) {
        return JsonProvider.provider().createJsonPullReader(array);
    }

    /**
     * Creates a JSON pull reader from a JSON object
     *
     * @param object a JSON object
     * @return a JSON pull reader
     */
    public static JsonPullReader create(JsonObject object) {
        return JsonProvider.provider().createJsonPullReader(object);
    }
}
