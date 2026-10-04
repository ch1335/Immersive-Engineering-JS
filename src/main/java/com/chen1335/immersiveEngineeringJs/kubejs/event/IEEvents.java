package com.chen1335.immersiveEngineeringJs.kubejs.event;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface IEEvents
{
	EventGroup GROUP = EventGroup.of("IEEvents");

	/**
	 * {@code hasResult()} is required for {@code event.cancel()} to be callable from scripts, and for
	 * the mixin that posts this event to observe the cancellation.
	 */
	EventHandler CREATE_STRUCTURE = GROUP.server("createStructure", () -> CreateStructureEvent.class).hasResult();
}
