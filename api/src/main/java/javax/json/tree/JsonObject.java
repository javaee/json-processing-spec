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
import java.util.*;

/**
 * {@code JsonObject} class represents a JSON object value. It
 * also implements {@link JsonObjectVisitor}, hence an instance can
 * be built by calling the visitor methods. For example, an instance can be
 * built as follows:
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
 * Convienently, fluent style can also be used while building a {@code JsonObject}
 * instance. For example, an instance can be built as follows:
 * <pre>
 * JsonObject personObj = JsonObject.create().addString("firstName", "John")
 *         .addString("lastName", "Smith")
 *         .addNumber("age", 25)
 *         .addObject("address", addressObj)
 *         .addArray("phoneNumber", phoneArray);
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
 * TODO 3. Implement {@code Map<String, JsonValue>} Hard to implement lazily ??
 * Too many methods to implement ??
 *
 * <p>
 * TODO 4. define equals() semantics
 *
 * @author Jitendra Kotamraju
 */
public abstract class JsonObject implements JsonValue, JsonObjectVisitor {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a JSON object
     *
     * @return a JSON object
     */
    public static JsonObject create() {
        return JsonProvider.provider().createObject();
    }

    /**
     * Makes the specified {@link JsonObjectVisitor} visit this JSON object
     *
     * @param visitor a JSON object value visitor
     */
    public abstract void accept(JsonObjectVisitor visitor);

    /**
     * Returns the value to which the specified name is mapped,
     * or {@code null} if this object contains no mapping for the name.
     *
     * @param name the name whose associated value is to be returned
     * @return the value to which the specified name is mapped, or
     *         {@code null} if this object contains no mapping for the name
     */
    public abstract JsonValue getValue(String name);

    public abstract void setValue(String name, JsonValue value);

    /**
     * Removes the name/value pair value that is associated with the
     * specified name. Returns the value that is associated with the
     * specified name. The object will not contain the associated name/value
     * pair once the call returns.
     *
     * @param name name whose associated name/value pair is to be removed
     * @return previous value associated with the specified name,
     *         or null if there was no mapping for the name
     */
    public abstract JsonValue remove(String name);

    public abstract Set<String> getNames();

    public abstract Map<String, JsonValue> getNameValueMap();

    /**
     * Adds the specified name/{@code JsonObject} value pair to this JSON object.
     * If the object previously contained a value for the name, the old value
     * is replaced.
     *
     * @param name name/key with which the specified value is to be associated
     * @param value value to be associated with the specified name/key
     * @return this JSON object
     */
    public abstract JsonObject putObject(String name, JsonObject value);

    /**
     * Adds the specified name/{@code JSONArray} value pair to this JSON object.
     * If the object previously contained a value for the name, the old value
     * is replaced.
     *
     * @param name name/key with which the specified value is to be associated
     * @param value value to be associated with the specified name/key
     * @return this JSON object
     */
    public abstract JsonObject putArray(String name, JsonArray value);

    /**
     * Adds the specified name/{@code JsonString} value pair to this JSON object.
     * If the object previously contained a value for the name, the old value
     * is replaced.
     *
     * @param name name/key with which the specified value is to be associated
     * @param value value to be associated with the specified name/key
     * @return this JSON object
     */
    public abstract JsonObject putString(String name, JsonString value);

    /**
     * Adds the specified name/{@code JsonNumber} value pair to this JSON object.
     * If the object previously contained a value for the name, the old value
     * is replaced.
     *
     * @param name name/key with which the specified value is to be associated
     * @param value value to be associated with the specified name/key
     * @return this JSON object
     */
    public abstract JsonObject putNumber(String name, JsonNumber value);

    /**
     * Adds the specified name/{@code JsonTrue} value pair to this JSON object.
     * If the object previously contained a value for the name, the old value
     * is replaced.
     *
     * @param name name/key with which the specified value is to be associated
     * @param value value to be associated with the specified name/key
     * @return this JSON object
     */
    public abstract JsonObject putTrue(String name, JsonTrue value);

    /**
     * Adds the specified name/{@code JsonFalse} value pair to this JSON object.
     * If the object previously contained a value for the name, the old value
     * is replaced.
     *
     * @param name name/key with which the specified value is to be associated
     * @param value value to be associated with the specified name/key
     * @return this JSON object
     */
    public abstract JsonObject putFalse(String name, JsonFalse value);

    /**
     * Adds the specified name/{@code JsonNull} value pair to this JSON object.
     * If the object previously contained a value for the name, the old value
     * is replaced.
     *
     * @param name name/key with which the specified value is to be associated
     * @param value value to be associated with the specified name/key
     * @return this JSON object
     */
    public abstract JsonObject putNull(String name, JsonNull value);

    /**
     * A convenience method for {@link #visitString} that allows
     * method chaining.
     *
     * @param name name/key with which the specified value is to be associated
     * @param value value to be associated with the specified name/key
     * @return this JSON object
     * @see #visitString
     */
    public JsonObject putString(String name, String value) {
        return putString(name, JsonString.create(value));
    }

    /**
     * A convenience method for {@link #visitNumber} that allows
     * method chaining.
     *
     * @param name name/key with which the specified value is to be associated
     * @param value value to be associated with the specified name/key
     * @return this JSON object
     * @see #visitNumber
     */
    public JsonObject putNumber(String name, Number value) {
        return putNumber(name, JsonNumber.create(value));
    }

    /**
     * A convenience method for {@link #visitTrue} that allows
     * method chaining.
     *
     * @param name name/key with which the specified value is to be associated
     * @return this JSON object
     * @see #visitTrue
     */
    public JsonObject putTrue(String name) {
        return putTrue(name, JsonTrue.JSON_TRUE);
    }

    /**
     * A convenience method for {@link #visitFalse} that allows
     * method chaining.
     *
     * @param name name/key with which the specified value is to be associated
     * @return this JSON object
     * @see #visitFalse
     */
    public JsonObject putFalse(String name) {
        return putFalse(name, JsonFalse.JSON_FALSE);
    }

    /**
     * A convenience method for {@link #visitNull(String)} that allows
     * method chaining.
     *
     * @param name name/key with which the specified value is to be associated
     * @return this JSON object
     * @see #visitNull
     */
    public JsonObject putNull(String name) {
        return putNull(name, JsonNull.JSON_NULL);
    }

    /**
     * Adds the specified name/JSON string value pair to this JSON object.
     * If the object previously contained a value for the name, the old value
     * is replaced.
     *
     * @param name name/key with which the specified value is to be associated
     * @param value value to be associated with the specified name/key
     */
    @Override
    public void visitString(String name, String value) {
        putString(name, value);
    }

    /**
     * Adds the specified name/JSON number value pair to this JSON object.
     * If the object previously contained a value for the name, the old value
     * is replaced.
     *
     * @param name name/key with which the specified value is to be associated
     * @param value value to be associated with the specified name/key
     */
    @Override
    public void visitNumber(String name, Number value) {
        putNumber(name, value);
    }

    /**
     * Adds the specified name/JSON true value pair to this JSON object.
     * If the object previously contained a value for the name, the old value
     * is replaced.
     *
     * @param name name/key with which the specified value is to be associated
     */
    @Override
    public void visitTrue(String name) {
        putTrue(name);
    }

    /**
     * Associates the false value with the specified name/key in this JSON object.
     * If the object previously contained a value for the name, the old value
     * is replaced.
     *
     * @param name name/key with which the specified value is to be associated
     */
    @Override
    public void visitFalse(String name) {
        putFalse(name);
    }

    /**
     * Associates the null value with the specified name/key in this JSON
     * object. If the object previously contained a value for the name, the
     * old value is replaced.
     *
     * @param name name/key with which the specified value is to be associated
     */
    @Override
    public void visitNull(String name) {
        putNull(name);
    }

    /**
     * Removes all of the name/value pairs from this JSON object.
     * The object will be empty after this call returns.
     */
    public abstract void clear();

}