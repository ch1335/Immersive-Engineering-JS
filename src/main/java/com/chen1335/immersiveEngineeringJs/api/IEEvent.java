package com.chen1335.immersiveEngineeringJs.api;

import dev.latvian.mods.kubejs.event.EventJS;

/**
 * Base class for the events posted to KubeJS scripts by this mod.
 * <p>
 * On 1.21.1 this class extended NeoForge's {@code Event}. KubeJS 2001 events have to extend
 * {@code EventJS} instead, so the mod bus parent could not be kept.
 */
public class IEEvent extends EventJS implements IEKubeEvent
{
}
