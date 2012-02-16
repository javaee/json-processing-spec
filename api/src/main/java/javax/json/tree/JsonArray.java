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
import java.util.Iterator;
import java.util.List;

/**
 * {@code JsonArray} class represents a JSON array value. It
 * also implements {@code JsonArrayVisitor}, hence an instance can
 * be built by calling the visitor methods. For example, an instance can be
 * built as follows:
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
 * Convienently, fluent style can be used while building a {@code JsonArray}
 * instance. For example, an instance can be built as follows:
 * <pre>
 * JsonArray phoneArray = JsonArray.create()
 *      .visitObject(homePhone)
 *      .visitObject(faxPhone);
 * </pre>
 *
 * {@code JsonArray} can be written to JSON text as follows:
 * <pre>
 * JsonWriter writer = ...
 * JsonArray arr = ...;
 * arr.accept(writer.visitArray());
 * </pre>
 *
 * Since {@code JsonObject} implements {@code JsonArrayVisitor}, copying
 * an array can be done as follows:
 * <pre>
 * JsonArray newArray = new JsonArray();
 * origArray.accept(newArray);
 * </pre>
 *
 * @author Jitendra Kotamraju
 */
public abstract class JsonArray implements JsonValue, Iterable<JsonValue>, JsonArrayVisitor {

    private static final long serialVersionUID = 1L;

    public static JsonArray create() {
        return JsonProvider.provider().createArray();
    }

    public abstract void accept(JsonArrayVisitor visitor);

    public abstract Iterator<JsonValue> getValues();

    public abstract List<JsonValue> getValueList();

    public abstract JsonValue getValue(int index);
    
    public abstract void setValue(int index, JsonValue value);

    public abstract JsonValue removeValue(int index);

    public abstract JsonArray addArray(JsonArray value);

    public abstract JsonArray addObject(JsonObject value);

    public abstract JsonArray addTrue(JsonTrue value);

    public abstract JsonArray addFalse(JsonFalse value);

    public abstract JsonArray addNull(JsonNull value);

    public abstract JsonArray addString(JsonString value);

    public abstract JsonArray addNumber(JsonNumber value);

    public JsonArray addString(String value) {
        JsonString string = JsonString.create(value);
        return addString(string);
    }

    public JsonArray addNumber(Number value) {
        JsonNumber number = JsonNumber.create(value);
        return addNumber(number);
    }

    public JsonArray addTrue() {
        return addTrue(JsonTrue.JSON_TRUE);
    }

    public JsonArray addFalse() {
        return addFalse(JsonFalse.JSON_FALSE);
    }

    public JsonArray addNull() {
        return addNull(JsonNull.JSON_NULL);
    }

    @Override
    public void visitString(String str) {
        addString(str);
    }

    @Override
    public void visitNumber(Number number) {
        addNumber(number);
    }

    @Override
    public void visitTrue() {
        addTrue();
    }

    @Override
    public void visitFalse() {
        addFalse();
    }

    @Override
    public void visitNull() {
        addNull();
    }

    public abstract void clear();
}
