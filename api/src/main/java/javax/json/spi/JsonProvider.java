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

package javax.json.spi;

import javax.json.JsonException;
import javax.json.stream.JsonPullReader;
import javax.json.stream.JsonPushReader;
import javax.json.stream.JsonWriter;
import javax.json.tree.*;
import javax.xml.ws.WebServiceException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Jitendra Kotamraju
 */
public abstract class JsonProvider {

    /**
     * A constant representing the property used to lookup the
     * name of a <code>Provider</code> implementation
     * class.
     */
    static public final String JSON_PROVIDER_PROPERTY
            = "javax.json.spi.JsonProvider";

    /**
     * A constant representing the name of the default
     * <code>Provider</code> implementation class.
     **/
    // Using two strings so that package renaming doesn't change it
    static final String DEFAULT_JSON_PROVIDER
            = "org.glassfish.json.GlassFishJsonProvider";


    /**
     * Creates a new instance of Provider
     */
    protected JsonProvider() {
    }

    /**
     *
     * Creates a new provider object.
     * <p>
     * The algorithm used to locate the provider subclass to use consists
     * of the following steps:
     * <p>
     * <ul>
     * <li>
     *   If a resource with the name of
     *   <code>META-INF/services/javax.json.spi.JsonProvider</code>
     *   exists, then its first line, if present, is used as the UTF-8 encoded
     *   name of the implementation class.
     * </li>
     * <li>
     *   If the $java.home/lib/json.properties file exists and it is readable by
     *   the <code>java.util.Properties.load(InputStream)</code> method and it contains
     *   an entry whose key is <code>javax.json.spi.JsonProvider</code>, then the value of
     *   that entry is used as the name of the implementation class.
     * </li>
     * <li>
     *   If a system property with the name <code>javax.json.spi.JsonProvider</code>
     *   is defined, then its value is used as the name of the implementation class.
     * </li>
     * <li>
     *   Finally, a default implementation class name is used.
     * </li>
     * </ul>
     *
     */
    public static JsonProvider provider() {
        try {
            return (JsonProvider)FactoryFinder.find(JSON_PROVIDER_PROPERTY, DEFAULT_JSON_PROVIDER);
        } catch (JsonException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new JsonException("Unable to create JsonProvider", ex);
        }
    }

    /**
     * Creates a JSON writer
     *
     * @param writer
     * @return
     */
    public abstract JsonWriter createJsonWriter(Writer writer);

    /**
     * Creates a JSON pull reader
     *
     * @param reader
     * @return
     */
    public abstract JsonPullReader createJsonPullReader(Reader reader);

    /**
     * Creates a JSON push reader
     *
     * @param reader
     * @return
     */
    public abstract JsonPushReader createJsonPushReader(Reader reader);

    /**
     * Creates a JSON reader from array
     *
     * @param array
     * @return
     */
    public abstract JsonPullReader createJsonPullReader(JsonArray array);

    /**
     * Creates a JSON reader from object
     *
     * @param object
     * @return
     */
    public abstract JsonPullReader createJsonPullReader(JsonObject object);

    /**
     * Creates a JSON array value
     *
     * @return
     */
    public abstract JsonArray createArray();

    /**
     * Creates a JSON null value
     *
     * @return
     */
    public abstract JsonNull createNull();

    /**
     * Creates a JSON number value
     *
     * @return
     */
    public abstract JsonNumber createNumber(Number value);

    /**
     * Creates a JSON string value
     *
     * @return
     */
    public abstract JsonString createString(String value);

    /**
     * Creates a JSON object value
     *
     * @return
     */
    public abstract JsonObject createObject();

    /**
     * Creates a JSON true value
     *
     * @return
     */
    public abstract JsonTrue createTrue();

    /**
     * Creates a JSON false value
     *
     * @return
     */
    public abstract JsonFalse createFalse();
}
