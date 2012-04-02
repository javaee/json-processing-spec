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

import javax.json.JsonArrayVisitor;
import javax.json.spi.JsonProvider;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

/**
 * {@code JsonArray} class represents a JSON array value.
 *
 * <p>A full JsonArray instance can be created from a character stream using
 * {@link #create(Reader)}. It can also be built from scratch by calling the
 * visitor methods since the class implements {@link JsonArrayVisitor}.
 * For example, an instance can be built as follows:
 *
 * <pre>
 * JsonObject homePhone = ...
 * JsonObject faxPhone = ...
 *
 * JsonArray phoneArray = JsonArray.create();
 * phoneArray.visitObject(homePhone);
 * phoneArray.visitObject(faxPhone);
 * </pre>
 *
 * Convienently, method chaining can be used while building a {@code JsonArray}
 * instance. For example, an instance can be built as follows:
 * <pre>
 * JsonArray phoneArray = JsonArray.create()
 *      .addObject(homePhone)
 *      .addObject(faxPhone);
 * </pre>
 *
 * {@code JsonArray} can be written to JSON text as follows:
 * <pre>
 * JsonWriter writer = ...
 * JsonArray arr = ...;
 * arr.accept(writer.visitArray());
 * </pre>
 *
 * Since {@code JsonObject} implements {@link JsonArrayVisitor}, copying
 * an array can be done as follows:
 * <pre>
 * JsonArray newArray = new JsonArray();
 * origArray.accept(newArray);
 * </pre>
 *
 * @author Jitendra Kotamraju
 */
// TODO Should we extend with List<JsonValue>. That means tying with a contract
// TODO and need to implement some not needed methods
public abstract class JsonArray implements JsonValue, Iterable<JsonValue>, JsonArrayVisitor {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a JSON array
     *
     * @return a JsonArray
     */
    public static JsonArray create() {
        return JsonProvider.provider().createArray();
    }

    /**
     * Creates a JSON array value from a character stream
     *
     * @param reader a reader from which JSON is to be read
     * @return a JSON array
     */
    public static JsonArray create(Reader reader) {
        return JsonProvider.provider().createArray(reader);
    }

    /**
     * Makes the specified {@code JsonArrayVisitor} visit this JSON array
     *
     * @param visitor a JSON array value visitor
     */
    public abstract void accept(JsonArrayVisitor visitor);

    /**
     * Returns an iterator to this JSON array values
     *
     * @return an iterator to array values
     */
    public abstract Iterator<JsonValue> getValues();

    /**
     * Returns a list of this JSON array values
     *
     * @return a list of array values
     */
    public abstract List<JsonValue> getValueList();

    /**
     * Returns the value at the specified position in this JSON array values.
     *
     * @param index index of the value to return
     * @return the value at the specified position in this array values
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public abstract JsonValue getValue(int index);

    /**
     * Returns the value at the specified position in this JSON array values.
     *
     * @param index index of the value to return
     * @param clazz value class
     * @return the value at the specified position in this array values
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @SuppressWarnings("unchecked")
    public <T extends JsonValue> T getValue(int index, Class<T> clazz) {
        return (T)getValue(index);
    }

    /**
     * Replaces the value at the specified position in this JSON array values
     * with the specified value
     *
     * @param index index of the value to replace
     * @param value value to be stored at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public abstract void setValue(int index, JsonValue value);

    /**
     * Removes the value at the specified position in this JSON array values.
     * Shifts any subsequent values to the left (subtracts one
     * from their indices).  Returns the value that was removed from the
     * array values.
     *
     * @param index the index of the element to be removed
     * @return the value previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public abstract JsonValue removeValue(int index);

    /**
     * Appends the specified {@code JsonValue} value to the end of this array
     * values
     *
     * @param value a JsonValue value
     * @return this JSON array
     */
    public abstract void addValue(JsonValue value);

    /**
     * Appends the specified {@code JsonArray} value to the end of this array
     * values
     *
     * @param value a JsonArray value
     * @return this JSON array
     */
    public abstract JsonArray addArray(JsonArray value);

    /**
     * Appends the specified {@code JsonObject} value to the end of this array
     * values
     *
     * @param value a JsonObject value
     * @return this JSON array
     */
    public abstract JsonArray addObject(JsonObject value);


    /**
     * Appends the specified {@code JsonString} value to the end of this array
     * values
     *
     * @param value a JsonString value
     * @return this JSON array
     */
    public abstract JsonArray addString(JsonString value);

    /**
     * Appends the specified {@code JsonNumber} value to the end of this array
     * values
     *
     * @param value a JsonNumber value
     * @return this JSON array
     */
    public abstract JsonArray addNumber(JsonNumber value);

    /**
     * A convenience method for {@link #visitString} that allows
     * method chaining.
     *
     * @param value a JSON string value
     * @return this JSON array
     * @see #visitString
     */
    public JsonArray addString(String value) {
        JsonString string = JsonString.create(value);
        return addString(string);
    }

    /**
     * A convenience method for {@link #visitNumber} that allows
     * method chaining.
     *
     * @param value a JSON number value
     * @return this JSON array
     * @see #visitNumber
     */
    public JsonArray addNumber(Number value) {
        JsonNumber number = JsonNumber.create(value);
        return addNumber(number);
    }

    /**
     * A convenience method for {@link #visitTrue} that allows
     * method chaining.
     *
     * @return this JSON array
     * @see #visitTrue
     */
    public JsonArray addTrue() {
        addValue(JsonValue.TRUE);
        return this;
    }

    /**
     * A convenience method for {@link #visitFalse} that allows
     * method chaining.
     *
     * @return this JSON array
     * @see #visitFalse
     */
    public JsonArray addFalse() {
        addValue(JsonValue.FALSE);
        return this;
    }

    /**
     * A convenience method for {@link #visitNull} that allows
     * method chaining.
     *
     * @return this JSON array
     * @see #visitNull
     */
    public JsonArray addNull() {
        addValue(JsonValue.NULL);
        return this;
    }

    /**
     * Appends a JSON string value to the end of this array values
     *
     * @param value a JSON string value
     */
    @Override
    public void visitString(String value) {
        addString(value);
    }

    /**
     * Appends a JSON number value to the end of this array values
     *
     * @param value a JSON number value
     */
    @Override
    public void visitNumber(Number value) {
        addNumber(value);
    }

    /**
     * Appends a JSON true value to the end of this array values
     */
    @Override
    public void visitTrue() {
        addTrue();
    }

    /**
     * Appends a JSON false value to the end of this array values
     */
    @Override
    public void visitFalse() {
        addFalse();
    }

    /**
     * Appends a JSON null value to the end of this array values
     */
    @Override
    public void visitNull() {
        addNull();
    }

    /**
     * Removes all the values from this JSON array.
     * The array will be empty after this call returns.
     */
    public abstract void clear();
}
