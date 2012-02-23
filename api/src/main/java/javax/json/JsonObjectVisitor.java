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

/**
 * A visitor to visit JSON object.
 *
 * @author Jitendra Kotamraju
 */
public interface JsonObjectVisitor {
    /**
     * Visits a string value member
     *
     * @param name member name
     * @param value a String
     */
    void visitString(String name, String value);

//    void visitNumber(String name, short value);
//    void visitNumber(String name, int value);
//    void visitNumber(String name, long value);
//    void visitNumber(String name, float value);
//    void visitNumber(String name, double value);
//    void visitNumber(String name, BigInteger value);
//    void visitNumber(String name, BigDecimal value);

    /**
     * Visits a number value member.
     *
     * TODO should we have two methods one for integer and one for floating point value
     * TODO should we have separate vistNumber methods like visitNumber(String name, short value)
     *
     * @param name member name
     * @param value a Number. Its type must be one of : {@code Byte},
     * {@code Short}, {@code Integer}, {@code Long}, {@code Float},
     * {@code Double}, {@code BigInteger} and {@code BigDecimal} .
     */
    void visitNumber(String name, Number value);

    /**
     * Visits a true value member
     *
     * @param name member name
     */
    void visitTrue(String name);

    /**
     * Visits a false value member
     *
     * @param name member name
     */
    void visitFalse(String name);

    /**
     * Visits a boolean value member
     *
     * TODO should we separate them into two visit methods vistTrue(), visitFalse()
     *
     * @param name member name
     * @param value
     *
    void visitBoolean(String name, boolean value);
    */

    /**
     * Visits a null value member
     *
     * @param name member name
     */
    void visitNull(String name);

    /**
     * Visits an array value member
     *
     * @param name key
     * @return a visitor to visit array value member
     * TODO null if this object visitor is not interested in visiting the array ?
     */
    JsonArrayVisitor visitArray(String name);

    /**
     * Visits an object value member
     *
     * @param name member name
     * @return a visitor to visit object value member
     * TODO null if this object visitor is not interested in visiting the object ?
     */
    JsonObjectVisitor visitObject(String name);

    /**
     * Visits the end of JSON object. This method, which is the last one to be
     * called, is used to inform the visitor that all the members of the JSON
     * object have been visited.
     */
    void visitEnd();
}
