/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2013-2020 Madz. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://raw.github.com/zhongdj/Lifecycle/master/License.txt
 * . See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
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
 * Version 2] license." If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above. However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package net.imadz.lifecycle.meta.object;

import java.util.LinkedList;

import net.imadz.lifecycle.meta.builder.impl.StateMetaBuilderImpl;
import net.imadz.lifecycle.meta.type.StateMetadata;
import net.imadz.lifecycle.meta.type.EventMetadata;

public class FunctionMetadata {

    private final StateMetadata parent;
    private final EventMetadata event;
    private final LinkedList<StateMetadata> nextStates;

    public FunctionMetadata(StateMetaBuilderImpl parent, EventMetadata event, LinkedList<StateMetadata> nextStates) {
        this.parent = parent;
        this.event = event;
        this.nextStates = nextStates;
    }

    public StateMetadata getParent() {
        return parent;
    }

    public EventMetadata getEvent() {
        return event;
    }

    public LinkedList<StateMetadata> getNextStates() {
        return nextStates;
    }
}
