/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011 Oracle and/or its affiliates. All rights reserved.
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

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Helps in building a JSON array
 *
 * @author Jitendra Kotamraju
 * @see JsonBuilder
 * @see javax.json.stream.JsonGenerator
 */
public interface JsonArrayBuilder<T> {
    /**
     * TODO not needed since add(JsonValue.NULL) can be used ??
     *
     * @return
     */
    public T endArray();

    /**
     * TODO not needed since add(JsonValue.NULL) can be used ??
     *
     * @return this array builder
     */
    public JsonArrayBuilder<T> add(JsonValue value);

    /**
     * TODO not needed since add(JsonValue.NULL) can be used ??
     *
     * @return this array builder
     */
    public JsonArrayBuilder<T> add(String value);

    /**
     * TODO not needed since add(JsonValue.NULL) can be used ??
     *
     * @return this array builder
     */
    public JsonArrayBuilder<T> add(BigDecimal value);

    /**
     * TODO not needed since add(JsonValue.NULL) can be used ??
     *
     * @return this array builder
     */
    public JsonArrayBuilder<T> add(BigInteger value);

    /**
     * TODO not needed since add(JsonValue.NULL) can be used ??
     *
     * @return this array builder
     */
    public JsonArrayBuilder<T> add(int value);

    /**
     * TODO not needed since add(JsonValue.NULL) can be used ??
     *
     * @return this array builder
     */
    public JsonArrayBuilder<T> add(long value);

    /**
     * TODO not needed since add(JsonValue.NULL) can be used ??
     *
     * @return this array builder
     */
    public JsonArrayBuilder<T> add(double value);

    /**
     * TODO not needed since add(JsonValue.TRUE|FALSE) can be used ??
     *
     * @return this array builder
     */
    public JsonArrayBuilder<T> add(boolean value);

    /**
     * TODO not needed since add(JsonValue.NULL) can be used ??
     *
     * @return this array builder
     */
    public JsonArrayBuilder<T> addNull();

    /**
     *
     * @param values
     * @return
     */
    public JsonArrayBuilder<T> addArray(Iterable<JsonValue> values);

    /**
     * Returns a JSON array builder to build a new child object
     *
     * @return an array builder
     */
    public JsonObjectBuilder<JsonArrayBuilder<T>> beginObject();

    /**
     * Returns a JSON array builder to build a new child array
     *
     * @return an array builder
     */
    public JsonArrayBuilder<JsonArrayBuilder<T>> beginArray();

}