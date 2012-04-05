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

package javax.json;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

/**
 * {@code JsonArray} class represents an immutable JSON array value.
 *
 * <p>A full JsonArray instance can be created from a character stream using
 * {@link JsonReader#readObject()}. For example:
 *
 * <code>
 * <pre>
 * JsonReader jsonReader = new JsonReader(...));
 * JsonArray array = (JsonArray)jsonReader.readObject();
 * jsonReader.close();
 * </pre>
 * </code>
 *
 * It can also be built from scratch using {@link JsonBuilder#beginArray()}.
 * <p>
 * For example 1:
 * <code>
 * <pre>
 * An empty JSON array can be built as follows:
 *
 * JsonArray array = new JsonBuilder()
 *     .beginArray()
 *     .endArray()
 * .build();
 * </pre>
 * </code>
 *
 * <p>
 * For example 2:
 * <code>
 * <pre>
 * The following JSON array
 *
 * [
 *     { "type": "home", "number": "212 555-1234" },
 *     { "type": "fax", "number": "646 555-4567" }
 * ]
 *
 * can be built using :
 *
 * JsonArray array = new JsonBuilder()
 *     .beginArray()
 *         .beginObject()
 *             .add("type", "home")
 *             .add("number", "212 555-1234")
 *         .endObject()
 *         .beginObject()
 *             .add("type", "home")
 *             .add("number", "646 555-4567")
 *         .endObject()
 *     .endArray()
 * .build();
 * </pre>
 * </code>
 *
 * {@code JsonArray} can be written to JSON text as follows:
 * <pre>
 * JsonArray arr = ...;
 * JsonWriter writer = new JsonWriter(...)
 * writer.writeObject(arr);
 * writer.close();
 * </pre>
 *
 * @author Jitendra Kotamraju
 */
// TODO Should we extend with List<JsonValue> ??
public interface JsonArray extends JsonValue, Iterable<JsonValue> {

    /**
     * Makes the specified {@code JsonArrayVisitor} visit this JSON array
     *
     * @param visitor a JSON array value visitor
     */
    public void accept(JsonArrayVisitor visitor);

    /**
     * Returns an unmodifiable list of this JSON array values
     *
     * @return a list of array values
     */
    public List<JsonValue> getValues();

    /**
     * Returns the value at the specified position in this JSON array values.
     *
     * @param index index of the value to return
     * @return the value at the specified position in this array values
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public JsonValue getValue(int index);

    /**
     * Returns the value at the specified position in this JSON array values.
     *
     * @param index index of the value to return
     * @param clazz value class
     * @return the value at the specified position in this array values
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public <T extends JsonValue> T getValue(int index, Class<T> clazz);

}