package com.chen1335.immersiveEngineeringJs.api;

/**
 * Marker interface for the KubeJS events added by this mod.
 * <p>
 * The 1.21.1 version of this interface also declared {@code cancel}/{@code success}/{@code exit}
 * overrides taking a Rhino {@code Context}. KubeJS 2001 already provides those methods on
 * {@code EventJS}, so on 1.20.1 this interface only marks the event type.
 */
public interface IEKubeEvent
{
}
