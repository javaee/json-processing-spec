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
 * also implements {@code JsonObjectVisitor}, hence an instance can
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
 * Convienently, fluent style can be used while building a {@code JsonObject}
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
 * Since {@code JsonObject} implements {@code JsonObjectVisitor}, copying
 * an object can be done as follows:
 * <pre>
 * JsonObject newObject = new JsonObject();
 * origObject.accept(newObject);
 * </pre>
 *
 * TODO 1. order of name-value pairs. RFC says "unordered collection of zero or more name/value
 * pairs". Should we preserve the order or not
 * TODO 2. Duplicate names. RFC says "The names within an object SHOULD be unique." Shall we
 * allow it or not ? json.org doesn't allow it. Need to experiment with javascript
 *
 * @author Jitendra Kotamraju
 */
public abstract class JsonObject implements JsonValue, JsonObjectVisitor {
    private static final long serialVersionUID = 1L;

    public static JsonObject create() {
        return JsonProvider.provider().createObject();
    }

    public abstract void accept(JsonObjectVisitor visitor);

    public abstract JsonValue getValue(String name);

    public abstract void setValue(String name, JsonValue value);

    public abstract JsonValue remove(String name);

    public abstract Set<String> getNames();

    public abstract Map<String, JsonValue> getNameValueMap();

    public abstract JsonObject addObject(String name, JsonObject value);

    public abstract JsonObject addArray(String name, JsonArray value);

    public abstract JsonObject addString(String name, JsonString value);

    public abstract JsonObject addNumber(String name, JsonNumber value);

    public abstract JsonObject addTrue(String name, JsonTrue value);

    public abstract JsonObject addFalse(String name, JsonFalse value);

    public abstract JsonObject addNull(String name, JsonNull value);

    public JsonObject addString(String name, String value) {
        return addString(name, JsonString.create(value));
    }

    public JsonObject addNumber(String name, Number value) {
        return addNumber(name, JsonNumber.create(value));
    }

    public JsonObject addTrue(String name) {
        return addTrue(name, JsonTrue.JSON_TRUE);
    }

    public JsonObject addFalse(String name) {
        return addFalse(name, JsonFalse.JSON_FALSE);
    }

    public JsonObject addNull(String name) {
        return addNull(name, JsonNull.JSON_NULL);
    }

    @Override
    public void visitString(String name, String value) {
        addString(name, value);
    }

    @Override
    public void visitNumber(String name, Number value) {
        addNumber(name, value);
    }

    @Override
    public void visitTrue(String name) {
        addTrue(name);
    }

    @Override
    public void visitFalse(String name) {
        addFalse(name);
    }

    @Override
    public void visitNull(String name) {
        addNull(name);
    }

    public abstract void clear();

}
