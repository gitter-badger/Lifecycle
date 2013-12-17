package net.madz.lifecycle.syntax.lm.condition;

import net.madz.lifecycle.SyntaxErrors;
import net.madz.lifecycle.annotations.CompositeState;
import net.madz.lifecycle.annotations.Function;
import net.madz.lifecycle.annotations.LifecycleMeta;
import net.madz.lifecycle.annotations.StateMachine;
import net.madz.lifecycle.annotations.StateSet;
import net.madz.lifecycle.annotations.Transition;
import net.madz.lifecycle.annotations.TransitionSet;
import net.madz.lifecycle.annotations.action.Condition;
import net.madz.lifecycle.annotations.action.ConditionSet;
import net.madz.lifecycle.annotations.action.Conditional;
import net.madz.lifecycle.annotations.action.ConditionalTransition;
import net.madz.lifecycle.annotations.state.End;
import net.madz.lifecycle.annotations.state.Initial;
import net.madz.lifecycle.annotations.state.ShortCut;
import net.madz.lifecycle.syntax.BaseMetaDataTest;
import net.madz.lifecycle.syntax.lm.condition.LMSyntaxConditionMetadata.S1.Conditions.S1_Condition_A;
import net.madz.lifecycle.syntax.lm.condition.LMSyntaxConditionMetadata.S1.States.S1_State_B;
import net.madz.lifecycle.syntax.lm.condition.LMSyntaxConditionMetadata.S1.States.S1_State_C;
import net.madz.lifecycle.syntax.lm.condition.LMSyntaxConditionMetadata.S2.States.EnclosingState.CStates.S2_State_B;
import net.madz.lifecycle.syntax.lm.condition.LMSyntaxConditionMetadata.S2.States.EnclosingState.CStates.S2_State_C;
import net.madz.lifecycle.syntax.lm.condition.LMSyntaxConditionMetadata.S2.States.EnclosingState.Conditions.S2_Condition_A;
import net.madz.lifecycle.syntax.lm.condition.LMSyntaxConditionMetadata.S2.Transitions.S2_Transition_X;

public class LMSyntaxConditionMetadata extends BaseMetaDataTest {

    @StateMachine
    static interface S1 {

        @StateSet
        static interface States {

            @Initial
            @Function(transition = Transitions.S1_Transition_X.class, value = { S1_State_B.class, S1_State_C.class })
            static interface S1_State_A {}
            @End
            static interface S1_State_B {}
            @End
            static interface S1_State_C {}
        }
        @TransitionSet
        static interface Transitions {

            @Conditional(condition = S1_Condition_A.class, judger = VolumeMeasurableTransition.class)
            static interface S1_Transition_X {}
        }
        @ConditionSet
        static interface Conditions {

            static interface S1_Condition_A {

                boolean isVolumeLeft();
            }
        }
        public static class VolumeMeasurableTransition implements ConditionalTransition<S1_Condition_A> {

            @Override
            public Class<?> doConditionJudge(S1_Condition_A t) {
                if ( t.isVolumeLeft() ) {
                    return S1_State_B.class;
                } else {
                    return S1_State_C.class;
                }
            }
        }
    }
    // Positive test
    @LifecycleMeta(S1.class)
    static class PLM_1 {

        private String state;

        @Transition
        public void s1_Transition_X() {}

        @Condition(S1.Conditions.S1_Condition_A.class)
        public S1_Condition_A getConditionA() {
            return null;
        }

        public String getState() {
            return state;
        }

        @SuppressWarnings("unused")
        private void setState(String state) {
            this.state = state;
        }
    }
    @StateMachine
    static interface S2 {

        @StateSet
        static interface States {

            @Initial
            @Function(transition = S2_Transition_X.class, value = EnclosingState.class)
            static interface S2_Intial {}
            @CompositeState
            static interface EnclosingState {

                @StateSet
                static interface CStates {

                    @Initial
                    @Function(transition = CTransitions.CX2.class, value = { S2_State_B.class, S2_State_C.class })
                    static interface S2_State_A {}
                    @End
                    @ShortCut(S2_End_B.class)
                    static interface S2_State_B {}
                    @End
                    @ShortCut(S2_End_C.class)
                    static interface S2_State_C {}
                }
                @TransitionSet
                static interface CTransitions {

                    @Conditional(condition = S2_Condition_A.class, judger = VolumeMeasurableTransition.class)
                    static interface CX2 {}
                }
                @ConditionSet
                static interface Conditions {

                    static interface S2_Condition_A {

                        boolean isVolumeLeft();
                    }
                }
                public static class VolumeMeasurableTransition implements ConditionalTransition<S2_Condition_A> {

                    @Override
                    public Class<?> doConditionJudge(S2_Condition_A t) {
                        if ( t.isVolumeLeft() ) {
                            return S2_State_B.class;
                        } else {
                            return S2_State_C.class;
                        }
                    }
                }
            }
            @End
            static interface S2_End_B {}
            @End
            static interface S2_End_C {}
        }
        @TransitionSet
        static interface Transitions {

            static interface S2_Transition_X {}
        }
    }
    @LifecycleMeta(S2.class)
    static class PLM_2 {

        private String state;

        @Transition
        public void s2_Transition_X() {}

        @Transition
        public void cX2() {}

        @Condition(S2.States.EnclosingState.Conditions.S2_Condition_A.class)
        public S2_Condition_A getConditionA() {
            return null;
        }

        public String getState() {
            return state;
        }

        @SuppressWarnings("unused")
        private void setState(String state) {
            this.state = state;
        }
    }
    @StateMachine
    static interface S3 extends S1 {}
    @LifecycleMeta(S3.class)
    static class PLM_3 {

        private String state;

        @Transition
        public void s1_Transition_X() {}

        @Condition(S3.Conditions.S1_Condition_A.class)
        public S1_Condition_A getConditionA() {
            return null;
        }

        public String getState() {
            return state;
        }

        @SuppressWarnings("unused")
        private void setState(String state) {
            this.state = state;
        }
    }
    // ////////////////////////////////////////////////////////////////
    // Negative Tests Meta Data
    // ////////////////////////////////////////////////////////////////
    @LifecycleMeta(S1.class)
    static class NLM_1 {

        static final String errorCode = SyntaxErrors.LM_CONDITION_REFERENCE_INVALID;
        private String state;

        @Transition
        public void s1_Transition_X() {}

        @Condition(S1.Transitions.S1_Transition_X.class)
        public S1_Condition_A getConditionA() {
            return null;
        }

        public String getState() {
            return state;
        }

        @SuppressWarnings("unused")
        private void setState(String state) {
            this.state = state;
        }
    }
    @LifecycleMeta(S1.class)
    static class NLM_2 {

        static final String errorCode = SyntaxErrors.LM_CONDITION_MULTIPLE_METHODS_REFERENCE_SAME_CONDITION;
        private String state;

        @Transition
        public void s1_Transition_X() {}

        @Condition(S1.Conditions.S1_Condition_A.class)
        public S1_Condition_A getConditionA() {
            return null;
        }

        @Condition(S1.Conditions.S1_Condition_A.class)
        public S1_Condition_A getConditionA2() {
            return null;
        }

        public String getState() {
            return state;
        }

        @SuppressWarnings("unused")
        private void setState(String state) {
            this.state = state;
        }
    }
    @LifecycleMeta(S1.class)
    static class NLM_3 {

        static final String errorCode = SyntaxErrors.LM_CONDITION_NOT_COVERED;
        private String state;

        @Transition
        public void s1_Transition_X() {}

        public String getState() {
            return state;
        }

        @SuppressWarnings("unused")
        private void setState(String state) {
            this.state = state;
        }
    }
    @LifecycleMeta(S1.class)
    static class ConditionObjectDoesNotImplementConditionClass {

        static final String errorCode = SyntaxErrors.LM_CONDITION_OBJECT_DOES_NOT_IMPLEMENT_CONDITION_INTERFACE;
        private String state;

        @Transition
        public void s1_Transition_X() {}

        public String getState() {
            return state;
        }

        @Condition(S1.Conditions.S1_Condition_A.class)
        public String getConditionA() {
            return null;
        }

        @SuppressWarnings("unused")
        private void setState(String state) {
            this.state = state;
        }
    }
}
