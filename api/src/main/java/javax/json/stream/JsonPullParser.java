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
 * A JSON pull parser. This is designed to be the most efficient way to
 * read JSON data. A pull parser can be created from many input sources using
 * {@link #create(Reader)}, {@link #create(JsonArray)}, {@link #create(JsonObject)}
 * methods.
 * 
 * <p>
 * A JsonPullReader is used to parse JSON in a pull manner by calling
 * its iterator methods. The iterator's {@code next()} method causes the reader
 * to read the next parse event.
 * <p>
 * For example 1:
 * <p>For empty JSON object { },
 * the iterator would give {<B>START_OBJECT</B> }<B>END_OBJECT</B> parse
 * events at the specified locations. Those events can be accessed using the
 * following code.
 *
 * <code>
 * <pre>
 * Iterator&lt;Event> it = reader.iterator();
 * Event event = it.next(); // START_OBJECT
 * event = it.next();       // END_OBJECT
 * </pre>
 * </code>
 *
 * <p>
 * For example 2:
 * <p>
 * For the following JSON
 * <pre>
 * {
 *   "firstName": "John", "lastName": "Smith", "age": 25,
 *   "phoneNumber": [
 *       { "type": "home", "number": "212 555-1234" },
 *       { "type": "fax", "number": "646 555-4567" }
 *    ]
 * }
 *
 * the iterator would give
 *
 * {<B>START_OBJECT</B>
 *   "firstName"<B>KEY_NAME</B>: "John"<B>VALUE_STRING</B>, "lastName"<B>KEY_NAME</B>: "Smith"<B>VALUE_STRING</B>, "age"<B>KEY_NAME</B>: 25<B>VALUE_NUMBER</B>,
 *   "phoneNumber"<B>KEY_NAME</B> : [<B>START_ARRAY</B>
 *       {<B>START_OBJECT</B> "type"<B>KEY_NAME</B>: "home"<B>VALUE_STRING</B>, "number"<B>KEY_NAME</B>: "212 555-1234"<B>VALUE_STRING</B> }<B>END_OBJECT</B>,
 *       {<B>START_OBJECT</B> "type"<B>KEY_NAME</B>: "fax"<B>VALUE_STRING</B>, "number"<B>KEY_NAME</B>: "646 555-4567"<B>VALUE_STRING</B> }<B>END_OBJECT</B>
 *    ]<B>END_ARRAY</B>
 * }<B>END_OBJECT</B> parse events at the specified locations.
 * </pre>
 * 
 * Here, "John" value is accessed as follows:
 * <code>
 * <pre>
 * Iterator&lt;Event> it = reader.iterator();
 * Event event = it.next(); // START_OBJECT
 * event = it.next();       // KEY_NAME
 * event = it.next();       // VALUE_STRING
 * reader.getString();      // "John"
 * </pre>
 * </code>
 *
 * @author Jitendra Kotamraju
 *
 * <p> TODO Create event objects - Improves type safety, but what about performance ?
 */
public abstract class JsonPullParser implements Iterable<JsonPullParser.Event>, /*Auto*/Closeable {
    /**
     * Event for parser state while parsing the JSON
     */
    public enum Event {
        /**
         * Event for start of a JSON array. This event indicates '[' is parsed.
         */
        START_ARRAY,
        /**
         * Event for start of a JSON object. This event indicates '{' is parsed.
         */
        START_OBJECT,
        /**
         * Event for a name in name(key)/value pair of a JSON object. This event
         * indicates that the key name is parsed. The name/key value itself
         * can be accessed using {@link #getString}
         */
        KEY_NAME,
        /**
         * Event for JSON string value. This event indicates a string value in
         * an array or object is parsed. The string value itself can be
         * accessed using {@link #getString}
         */
        VALUE_STRING,
        /**
         * Event for a number value. This event indicates a number value in
         * an array or object is parsed. The number value itself can be
         * accessed using {@link #getNumber}
         */
        VALUE_NUMBER,
        /**
         * Event for a true value. This event indicates a true value in an
         * array or object is parsed.
         */
        VALUE_TRUE,
        /**
         * Event for a false value. This event indicates a false value in an
         * array or object is parsed.
         */
        VALUE_FALSE,
        /**
         * Event for a null value. This event indicates a null value in an
         * array or object is parsed.
         */
        VALUE_NULL,
        /**
         * Event for end of an object. This event indicates '}' is parsed.
         */
        END_OBJECT,
        /**
         * Event for end of an array. This event indicates ']' is parsed.
         */
        END_ARRAY
    }

    /**
     * Returns name when the state is {@link Event#KEY_NAME} or returns string
     * value when the state is {@link Event#VALUE_STRING}
     * 
     * @return a string
     * @throws IllegalStateException when the event state is not in
     *      KEY_NAME or VALUE_STRING
     */
    public abstract String getString();

    /**
     * Returns a JSON number when the state is {@link Event#VALUE_NUMBER}
     *
     * @return a number
     * @throws IllegalStateException when the state is not VALUE_NUMBER
     *
     * <p>TODO Number type based on the precision ??
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
    public static JsonPullParser create(Reader reader) {
        return JsonProvider.provider().createJsonPullReader(reader);
    }

    /**
     * Creates a JSON pull reader from a JSON array
     *
     * @param array a JSON array
     * @return a JSON pull reader
     */
    public static JsonPullParser create(JsonArray array) {
        return JsonProvider.provider().createJsonPullReader(array);
    }

    /**
     * Creates a JSON pull reader from a JSON object
     *
     * @param object a JSON object
     * @return a JSON pull reader
     */
    public static JsonPullParser create(JsonObject object) {
        return JsonProvider.provider().createJsonPullReader(object);
    }
}