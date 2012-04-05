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
 * {@code JsonArray} class represents an immutable JSON array value.
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
public abstract class JsonArray implements JsonValue, Iterable<JsonValue> {


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
     * Returns an unmodifiable list of this JSON array values
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

}