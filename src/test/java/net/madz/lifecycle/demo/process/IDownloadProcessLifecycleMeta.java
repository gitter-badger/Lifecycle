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
package net.madz.lifecycle.demo.process;

import net.madz.lifecycle.annotations.Function;
import net.madz.lifecycle.annotations.Functions;
import net.madz.lifecycle.annotations.StateMachine;
import net.madz.lifecycle.annotations.StateSet;
import net.madz.lifecycle.annotations.TransitionSet;
import net.madz.lifecycle.annotations.action.Corrupt;
import net.madz.lifecycle.annotations.action.Fail;
import net.madz.lifecycle.annotations.action.Recover;
import net.madz.lifecycle.annotations.action.Redo;
import net.madz.lifecycle.annotations.action.Timeout;
import net.madz.lifecycle.annotations.state.Corrupted;
import net.madz.lifecycle.annotations.state.End;
import net.madz.lifecycle.annotations.state.Initial;
import net.madz.lifecycle.annotations.state.Running;
import net.madz.lifecycle.annotations.state.Stopped;
import net.madz.lifecycle.demo.process.IDownloadProcessLifecycleMeta.Transitions.Activate;
import net.madz.lifecycle.demo.process.IDownloadProcessLifecycleMeta.Transitions.Deactive;
import net.madz.lifecycle.demo.process.IDownloadProcessLifecycleMeta.Transitions.Err;
import net.madz.lifecycle.demo.process.IDownloadProcessLifecycleMeta.Transitions.Finish;
import net.madz.lifecycle.demo.process.IDownloadProcessLifecycleMeta.Transitions.Pause;
import net.madz.lifecycle.demo.process.IDownloadProcessLifecycleMeta.Transitions.Prepare;
import net.madz.lifecycle.demo.process.IDownloadProcessLifecycleMeta.Transitions.Receive;
import net.madz.lifecycle.demo.process.IDownloadProcessLifecycleMeta.Transitions.Remove;
import net.madz.lifecycle.demo.process.IDownloadProcessLifecycleMeta.Transitions.Restart;
import net.madz.lifecycle.demo.process.IDownloadProcessLifecycleMeta.Transitions.Resume;
import net.madz.lifecycle.demo.process.IDownloadProcessLifecycleMeta.Transitions.Start;

@StateMachine
public interface IDownloadProcessLifecycleMeta {

    @StateSet
    static class States {

        @Initial
        @Functions({ @Function(transition = Prepare.class, value = Queued.class), @Function(transition = Remove.class, value = Removed.class) })
        static class New {}
        @Running
        @Functions({ @Function(transition = Pause.class, value = Paused.class), @Function(transition = Start.class, value = Started.class),
                @Function(transition = Deactive.class, value = InactiveQueued.class), @Function(transition = Remove.class, value = Removed.class) })
        static class Queued {}
        @Running
        @Functions({ @Function(transition = Pause.class, value = Paused.class), @Function(transition = Receive.class, value = Started.class),
                @Function(transition = Deactive.class, value = InactiveStarted.class), @Function(transition = Err.class, value = Failed.class),
                @Function(transition = Finish.class, value = Finished.class), @Function(transition = Remove.class, value = Removed.class) })
        static class Started {}
        @Corrupted(recoverPriority = 1)
        @Functions({ @Function(transition = Activate.class, value = Queued.class), @Function(transition = Remove.class, value = Removed.class) })
        static class InactiveQueued {}
        @Corrupted(recoverPriority = 0)
        @Functions({ @Function(transition = Activate.class, value = Queued.class), @Function(transition = Remove.class, value = Removed.class) })
        static class InactiveStarted {}
        @Stopped
        @Functions({ @Function(transition = Resume.class, value = New.class), @Function(transition = Restart.class, value = New.class),
                @Function(transition = Remove.class, value = Removed.class) })
        static class Paused {}
        @Stopped
        @Functions({ @Function(transition = Restart.class, value = New.class), @Function(transition = Resume.class, value = New.class),
                @Function(transition = Remove.class, value = Removed.class), })
        static class Failed {}
        @Stopped
        @Functions({ @Function(transition = Restart.class, value = New.class), @Function(transition = Remove.class, value = Removed.class), })
        static class Finished {}
        @End
        static class Removed {}
    }
    @TransitionSet
    static class Transitions {

        @Recover
        @Timeout(3000L)
        static class Activate {}
        @Corrupt
        @Timeout(3000L)
        static class Deactive {}
        @Fail
        @Timeout(3000L)
        static class Err {}
        static class Prepare {}
        static class Start {}
        static class Resume {}
        static class Pause {}
        static class Finish {}
        static class Receive {}
        @Redo
        @Timeout(3000L)
        static class Restart {}
        static class Remove {}
    }
}
