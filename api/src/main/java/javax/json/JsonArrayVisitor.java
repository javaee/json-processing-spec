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
 * A visitor to visit JSON array.
 *
 * @author Jitendra Kotamraju
 */
public interface JsonArrayVisitor {
    /**
     * Visits a string value
     *
     * @param value
     */
    void visitString(String value);

    //    void visitNumber(short value);
    //    void visitNumber(int value);
    //    void visitNumber(long value);
    //    void visitNumber(float value);
    //    void visitNumber(double value);
    //    void visitNumber(BigInteger value);
    //    void visitNumber(BigDecimal value);

    /**
     * Visits a number value.
     *
     * TODO should we have two methods one for integer and one for floating point value
     * TODO should we have separate vistNumber methods like visitNumber(int value)
     *
     * @param value a Number. Its type must be one of : {@code Byte},
     * {@code Short}, {@code Integer}, {@code Long}, {@code Float},
     * {@code Double}, {@code BigInteger} and {@code BigDecimal} .
     */
    void visitNumber(Number value);

    /**
     * Visits a true value
     */
    void visitTrue();

    /**
     * Visits a false value
     */
    void visitFalse();

    /**
     * Visits a boolean value
     *
     * TODO should we separate them into two visit methods vistTrue(), visitFalse()
     *
     * @param value
     *
    void visitBoolean(boolean value);
    */

    /**
     * Visits a null value
     */
    void visitNull();

    /**
     * Visits an array value
     *
     * @return a visitor to visit array value
     * TODO null if this array visitor is not interested in visiting the array,
     * but on what basis a visitor would decide ?
     */
    JsonArrayVisitor visitArray();

    /**
     * Visits an object
     * 
     * @return a visitor to visit object value
     * TODO null if this array visitor is not interested in visiting the object,
     * but on what basis a visitor would decide ?
     */
    JsonObjectVisitor visitObject();

    /**
     * Visits the end of JSON array. This method, which is the last one to be
     * called, is used to inform the visitor that all the values of the JSON
     * array have been visited.
     */
    void visitEnd();
}
