package com.chen1335.immersiveEngineeringJs.kubejs.event;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface IEEvents {
    EventGroup GROUP = EventGroup.of("IEEvents");
    EventHandler CREATE_STRUCTURE = GROUP.server("createStructure", () -> CreateStructureEvent.class);
}
