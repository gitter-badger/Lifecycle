package net.madz.lifecycle.engine;

import net.madz.lifecycle.annotations.Function;
import net.madz.lifecycle.annotations.Functions;
import net.madz.lifecycle.annotations.StateMachine;
import net.madz.lifecycle.annotations.StateSet;
import net.madz.lifecycle.annotations.TransitionSet;
import net.madz.lifecycle.annotations.relation.InboundWhile;
import net.madz.lifecycle.annotations.relation.InboundWhiles;
import net.madz.lifecycle.annotations.relation.Parent;
import net.madz.lifecycle.annotations.relation.RelateTo;
import net.madz.lifecycle.annotations.relation.RelationSet;
import net.madz.lifecycle.annotations.relation.ValidWhile;
import net.madz.lifecycle.annotations.relation.ValidWhiles;
import net.madz.lifecycle.annotations.state.End;
import net.madz.lifecycle.annotations.state.Initial;

public class MultipleStateMachineTestMetadata extends EngineTestBase {

    @StateMachine
    public static interface PCPurchaseOrderStateMachine {

        @StateSet
        static interface States {

            @Initial
            @Functions({ @Function(transition = Transitions.Confirm.class, value = Confirmed.class),
                    @Function(transition = Transitions.Abort.class, value = Aborted.class) })
            static interface Draft {}
            @Functions({ @Function(transition = Transitions.Start.class, value = Ongoing.class),
                    @Function(transition = Transitions.Abort.class, value = Aborted.class) })
            static interface Confirmed {}
            @Functions({ @Function(transition = Transitions.Complete.class, value = Completed.class),
                    @Function(transition = Transitions.Abort.class, value = Aborted.class) })
            static interface Ongoing {}
            @End
            static interface Completed {}
            @End
            static interface Aborted {}
        }
        @TransitionSet
        static interface Transitions {

            static interface Confirm {}
            static interface Start {}
            static interface Complete {}
            static interface Abort {}
        }
    }
    @StateMachine
    public static interface PCManufactoringOrderStateMachine {

        @StateSet
        static interface States {

            @Initial
            @Function(transition = Transitions.ConfirmBOM.class, value = BOMConfirmed.class)
            static interface Draft {}
            @InboundWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Confirmed.class)
            @Function(transition = Transitions.Plan.class, value = Planned.class)
            @ValidWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class)
            static interface BOMConfirmed {}
            @InboundWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class)
            @Function(transition = Transitions.MakeOSROMComplete.class, value = OSROMReady.class)
            @ValidWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class)
            static interface Planned {}
            @InboundWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class)
            @Function(transition = Transitions.StartAssembling.class, value = AssemblingOnProductLine.class)
            @ValidWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class)
            static interface OSROMReady {}
            @InboundWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class)
            @Function(transition = Transitions.StartDebugging.class, value = DebuggingOnProductLine.class)
            @ValidWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class)
            static interface AssemblingOnProductLine {}
            @InboundWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class)
            @Function(transition = Transitions.PackageComplete.class, value = Packaged.class)
            @ValidWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class)
            static interface DebuggingOnProductLine {}
            @InboundWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class)
            @Function(transition = Transitions.TransferToLogistics.class, value = Done.class)
            @ValidWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class)
            static interface Packaged {}
            @End
            static interface Done {}
        }
        @TransitionSet
        static interface Transitions {

            static interface TransferToLogistics {}
            static interface PackageComplete {}
            static interface StartDebugging {}
            static interface StartAssembling {}
            static interface MakeOSROMComplete {}
            static interface Plan {}
            static interface ConfirmBOM {}
        }
        @RelationSet
        static interface Relations {

            @Parent
            @RelateTo(PCPurchaseOrderStateMachine.class)
            public static interface PurchaseOrder {}
        }
    }
    @StateMachine
    public static interface PCLogisticOrderStateMachine {

        @StateSet
        static interface States {

            @Initial
            @Function(transition = Transitions.Confirm.class, value = Confirmed.class)
            static interface Draft {}
            @InboundWhiles({ @InboundWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class),
                    @InboundWhile(relation = Relations.ManufactureOrder.class, on = PCManufactoringOrderStateMachine.States.Packaged.class) })
            @Function(transition = Transitions.Schedule.class, value = Scheduled.class)
            @ValidWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class)
            static interface Confirmed {}
            @InboundWhiles({ @InboundWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class),
                    @InboundWhile(relation = Relations.ManufactureOrder.class, on = PCManufactoringOrderStateMachine.States.Packaged.class) })
            @Function(transition = Transitions.DoPickup.class, value = Picked.class)
            @ValidWhiles({ @ValidWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class),
                    @ValidWhile(relation = Relations.ManufactureOrder.class, on = PCManufactoringOrderStateMachine.States.Packaged.class) })
            static interface Scheduled {}
            @InboundWhiles({ @InboundWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class),
                    @InboundWhile(relation = Relations.ManufactureOrder.class, on = PCManufactoringOrderStateMachine.States.Packaged.class) })
            @Function(transition = Transitions.StartTransport.class, value = Transporting.class)
            @ValidWhiles({ @ValidWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class),
                    @ValidWhile(relation = Relations.ManufactureOrder.class, on = PCManufactoringOrderStateMachine.States.Done.class) })
            static interface Picked {}
            @InboundWhiles({ @InboundWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class),
                    @InboundWhile(relation = Relations.ManufactureOrder.class, on = PCManufactoringOrderStateMachine.States.Done.class) })
            @Function(transition = Transitions.CustomerConfirmReceive.class, value = Received.class)
            @ValidWhiles({ @ValidWhile(relation = Relations.PurchaseOrder.class, on = PCPurchaseOrderStateMachine.States.Ongoing.class),
                    @ValidWhile(relation = Relations.ManufactureOrder.class, on = PCManufactoringOrderStateMachine.States.Done.class) })
            static interface Transporting {}
            @End
            static interface Received {}
        }
        @TransitionSet
        static interface Transitions {

            static interface Schedule {}
            static interface CustomerConfirmReceive {}
            static interface StartTransport {}
            static interface DoPickup {}
            static interface Confirm {}
        }
        @RelationSet
        static interface Relations {

            @Parent
            @RelateTo(PCPurchaseOrderStateMachine.class)
            public static interface PurchaseOrder {}
            @RelateTo(PCManufactoringOrderStateMachine.class)
            static interface ManufactureOrder {}
        }
    }
}