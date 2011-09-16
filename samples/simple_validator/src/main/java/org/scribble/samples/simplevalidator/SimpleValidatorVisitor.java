/*
 * Copyright 2009 www.scribble.org
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.samples.simplevalidator;

import java.util.Collections;

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.*;

public class SimpleValidatorVisitor extends DefaultVisitor {

    private Journal m_journal=null;
    
    public SimpleValidatorVisitor(Journal journal) {
        m_journal = journal;
    }

    public void accept(Interaction elem) {
        
        // Check if interaction is an Order
        if (elem.getMessageSignature() != null) {
            
            for (TypeReference tref : elem.getMessageSignature().getTypeReferences()) {
    
                if (tref.getName() != null && tref.getName().equalsIgnoreCase("Order")) {
                    
                    m_journal.error(java.util.PropertyResourceBundle.getBundle(
                            "org.scribble.samples.simplevalidator.Messages").
                                    getString("_ORDER_FOUND"), null);
                }
            }
        }
        
    }
}
