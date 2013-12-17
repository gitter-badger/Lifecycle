package net.madz.lifecycle.demo.inheritance;

import net.madz.lifecycle.annotations.LifecycleMeta;
import net.madz.lifecycle.annotations.StateIndicator;
import net.madz.lifecycle.annotations.Transition;
import net.madz.lifecycle.demo.inheritance.meta.PlantScheduleOrderLifecycleMeta;

@LifecycleMeta(PlantScheduleOrderLifecycleMeta.class)
public interface IPlantScheduleOrder {

    @Transition(PlantScheduleOrderLifecycleMeta.Transitions.Start.class)
    void doStartPlantOrder();

    @Transition(PlantScheduleOrderLifecycleMeta.Transitions.Finish.class)
    void doFinishPlantOrder();

    IServiceOrder getServiceOrder();

    @StateIndicator
    String getPlantScheduleOrderState();
}
