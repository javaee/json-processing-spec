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

import javax.json.JsonObjectVisitor;
import javax.json.JsonVisitor;
import javax.json.spi.JsonProvider;
import java.io.Closeable;
import java.io.Writer;

/**
 * A JSON generator. It is also a {@code JsonVisitor} and its visit methods
 * are called to generate JSON. For example:
 *
 * <code>
 * <pre>
 * JsonWriter writer = JsonWriter.create(...);
 * JsonObjectVisitor personWriter = writer.visitObject();
 * personWriter.visitString("firstName", "John");
 * personWriter.visitString("lastName", "Smith");
 * personWriter.visitNumber("age", 25);
 * JsonArrayVisitor phoneWriter = personWriter.visitArray("phoneNumber");
 * JsonObjectVisitor homePhoneWriter = phoneWriter.visitObject();
 * homePhoneWriter.visitString("type", "home");
 * homePhoneWriter.visitString("number", "212 555-1234");
 * homePhoneWriter.visitEnd();
 * JsonObjectVisitor faxPhoneWriter = phoneWriter.visitObject();
 * faxPhoneWriter.visitString("type", "fax");
 * faxPhoneWriter.visitString("number", "646 555-4567");
 * faxPhoneWriter.visitEnd();
 * phoneWriter.visitEnd();
 * personWriter.visitEnd();
 * writer.close();
 *
 * would produce a JSON equivalent to the following:
 * {
 *   "firstName": "John", "lastName": "Smith", "age": 25,
 *   "phoneNumber": [
 *       {"type": "home", "number": "212 555-1234"},
 *       {"type": "fax", "number": "646 555-4567"}
 *    ]
 * }
 *
 * </pre>
 * </code>
 *
 * TODO should we add convenience methods with method chaining ?
 *
 * @author Jitendra Kotamraju
 */
public abstract class JsonWriter implements JsonVisitor, /*Auto*/Closeable {

    /**
     * Creates a JSON writer which can be used to write JSON text to the
     * writer.
     *
     * @param writer to which data is written
     * @return a JSON writer
     */
    public static JsonWriter create(Writer writer) {
        return JsonProvider.provider().createJsonWriter(writer);
    }

/*
    TODO will the following any useful ?

    public static JsonObjectVisitor createObject(Writer writer) {
        return JsonProvider.provider().createJsonWriter(writer).visitObject();
    }
    public static JsonArrayVisitor createArray(Writer writer) {
        return JsonProvider.provider().createJsonWriter(writer).visitArray();
    }
*/


    /**
     * Closes this writer and frees any resources associated with the
     * writer. This doesn't close the underlying output source.
     */
    @Override
    public abstract void close();

}