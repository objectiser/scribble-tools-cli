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

import org.scribble.common.logging.Journal;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.validation.ProtocolValidator;

public class SimpleValidator implements ProtocolValidator {

    public void validate(ProtocolModel model, Journal journal) {
        
        // Create a visitor implementation that can scan the supplied
        // protocol model and report any issues to the journal
        SimpleValidatorVisitor visitor=new SimpleValidatorVisitor(journal);
        
        model.visit(visitor);
    }
}
