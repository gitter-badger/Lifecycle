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
package net.madz.lifecycle.demo.relational.meta;

import net.madz.lifecycle.annotations.Function;
import net.madz.lifecycle.annotations.Functions;
import net.madz.lifecycle.annotations.StateMachine;
import net.madz.lifecycle.annotations.StateSet;
import net.madz.lifecycle.annotations.TransitionSet;
import net.madz.lifecycle.annotations.relation.InboundWhile;
import net.madz.lifecycle.annotations.relation.InboundWhiles;
import net.madz.lifecycle.annotations.relation.RelationSet;
import net.madz.lifecycle.annotations.state.End;
import net.madz.lifecycle.annotations.state.Initial;
import net.madz.lifecycle.demo.relational.meta.ServiceableLifecycleMeta.Relations.ConcreteTruckResource;
import net.madz.lifecycle.demo.relational.meta.ServiceableLifecycleMeta.Relations.PlantResource;
import net.madz.lifecycle.demo.relational.meta.ServiceableLifecycleMeta.Transitions.Cancel;
import net.madz.lifecycle.demo.relational.meta.ServiceableLifecycleMeta.Transitions.Finish;
import net.madz.lifecycle.demo.relational.meta.ServiceableLifecycleMeta.Transitions.Schedule;
import net.madz.lifecycle.demo.relational.meta.ServiceableLifecycleMeta.Transitions.Start;

@StateMachine
public interface ServiceableLifecycleMeta {

    @StateSet
    public static class States {

        @Initial
        @Function(transition = Schedule.class, value = Queued.class)
        public static class Created {}
        @Functions({ @Function(transition = Start.class, value = Ongoing.class), @Function(transition = Cancel.class, value = Cancelled.class) })
        @InboundWhiles({
                @InboundWhile(relation = PlantResource.class,
                        on = { PlantResourceLifecycleMeta.States.Idle.class, PlantResourceLifecycleMeta.States.Busy.class }),
                @InboundWhile(relation = ConcreteTruckResource.class, on = { ConcreteTruckResourceLifecycleMeta.States.Idle.class,
                        ConcreteTruckResourceLifecycleMeta.States.Busy.class }) })
        // Default @ValidWhiles = @InboundWhiles or Default @ValidWhile =
        // @InboundWhile
        public static class Queued {}
        @Functions({ @Function(transition = Finish.class, value = Finished.class), @Function(transition = Cancel.class, value = Cancelled.class) })
        public static class Ongoing {}
        @End
        public static class Finished {}
        @End
        public static class Cancelled {}
    }
    @TransitionSet
    public static class Transitions {

        public static class Schedule {}
        public static class Start {}
        public static class Finish {}
        public static class Cancel {}
    }
    @RelationSet
    public static class Relations {

        // default to @Relation("plantResource")
        public static class PlantResource {}
        // default to @Relation("concreteTruckResource")
        public static class ConcreteTruckResource {}
    }
}
