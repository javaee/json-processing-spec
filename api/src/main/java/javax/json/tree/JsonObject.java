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

package javax.json.tree;

import javax.json.JsonObjectVisitor;
import javax.json.spi.JsonProvider;
import java.io.Reader;
import java.util.*;

/**
 * {@code JsonObject} class represents an immutable JSON object value.
 *
 * <p>
 * A full JsonObject instance can be created from a character stream using
 * {@link #create(Reader)}. It can also be built from scratch by calling the
 * visitor methods since the class implements {@link JsonObjectVisitor}.
 * For example, an instance can be built as follows:
 *
 * <pre>
 * JsonObject addressObj = ...
 * JsonArray phoneArray = ...
 * 
 * JsonObject personObj = JsonObject.create();
 * personObj.visitString("firstName", "John");
 * personObj.visitString("lastName", "Smith");
 * personObj.visitNumber("age", 25);
 * personObj.visitObject("address", addressObj);
 * personObj.visitArray("phoneNumber", phoneArray);
 * </pre>
 *
 * Convienently, method chaining can also be used while building a
 * {@code JsonObject} instance. For example, an instance can be built as follows:
 * <pre>
 * JsonObject personObj = JsonObject.create().addString("firstName", "John")
 *         .putString("lastName", "Smith")
 *         .putNumber("age", 25)
 *         .putObject("address", addressObj)
 *         .putArray("phoneNumber", phoneArray);
 * </pre>
 *
 * {@code JsonObject} can be written to JSON text as follows:
 * <pre>
 * JsonWriter writer = ...
 * JsonObject obj = ...;
 * obj.accept(writer.visitObject());
 * </pre>
 *
 * Since {@code JsonObject} implements {@link JsonObjectVisitor}, copying
 * an object can be done as follows:
 * <pre>
 * JsonObject newObject = new JsonObject();
 * origObject.accept(newObject);
 * </pre>
 *
 * <p>
 * TODO 1. order of name-value pairs. RFC says "unordered collection of zero or more name/value
 * pairs". Should we preserve the order or not ?
 * <p>
 * TODO 2. Duplicate names. RFC says "The names within an object SHOULD be unique." Shall we
 * allow it or not ? json.org doesn't allow it. Need to experiment with javascript
 *
 * <p>
 * TODO 3. Implement {@code Map&lt;String, JsonValue>} Hard to implement lazily ??
 * Too many methods to implement ??
 *
 * <p>
 * TODO 4. define equals() semantics
 *
 * <p>
 * TODO Serialization
 *
 * <p>
 * TODO 5. Define an iterating order to be predictable like the order in which
 * name/keys are inserted ?? Will help streaming and testing
 *
 * @author Jitendra Kotamraju
 */
public abstract class JsonObject implements JsonValue {

    /**
     * Creates a JSON object from a character stream
     *
     * @param reader a reader from which JSON is to be read     *
     * @return a JSON object
     */
    public static JsonObject create(Reader reader) {
        return JsonProvider.provider().createObject(reader);
    }

    /**
     * Makes the specified {@link JsonObjectVisitor} visit this JSON object
     *
     * @param visitor a JSON object value visitor
     */
    public abstract void accept(JsonObjectVisitor visitor);

    /**
     * Returns the value to which the specified name/key is mapped,
     * or {@code null} if this object contains no mapping for the name/key.
     *
     * @param name the name/key whose associated value is to be returned
     * @return the value to which the specified name is mapped, or
     *         {@code null} if this object contains no mapping for the name/key
     */
    public abstract JsonValue getValue(String name);

    /**
     * Returns the value to which the specified name/key is mapped,
     * or {@code null} if this object contains no mapping for the name/key.
     *
     * @param name the name/key whose associated value is to be returned
     * @param clazz value class
     * @return the value to which the specified name is mapped, or
     *         {@code null} if this object contains no mapping for the name/key
     */
    @SuppressWarnings("unchecked")
    public <T extends JsonValue> T getValue(String name, Class<T> clazz) {
        return (T)getValue(name);
    }

    /**
     * Returns a {@link Set} of the name/keys contained in this JSON object.
     * Any changes to the set do not affect this JSON object.
     *
     * @return a set of the name/keys contained in this JSON object
     */
    public abstract Set<String> getNames();

    /**
     * Returns a {@link Map} of the name(key)/value pairs contained in
     * this JSON object. Any changes to the map do not affect this JSON object.
     *
     * @return a set of the name/keys contained in this JSON object
     */
    public abstract Map<String, JsonValue> getNameValueMap();

}