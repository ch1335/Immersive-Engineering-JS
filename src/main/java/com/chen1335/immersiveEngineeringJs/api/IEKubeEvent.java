package com.chen1335.immersiveEngineeringJs.api;

import dev.latvian.mods.kubejs.event.EventExit;
import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.util.HideFromJS;
import org.jetbrains.annotations.Nullable;

public interface IEKubeEvent extends KubeEvent {
    @HideFromJS
    default Object cancel(Context cx) throws EventExit {
        return cancel(cx, defaultExitValue(cx));
    }

    @HideFromJS
    default Object success(Context cx) throws EventExit {
        return success(cx, defaultExitValue(cx));
    }

    @HideFromJS
    default Object exit(Context cx) throws EventExit {
        return exit(cx, defaultExitValue(cx));
    }

    @HideFromJS
    default Object cancel(Context cx, @Nullable Object value) throws EventExit {
        throw EventResult.Type.INTERRUPT_FALSE.exit(cx, mapExitValue(cx, value));
    }

    @HideFromJS
    default Object success(Context cx, @Nullable Object value) throws EventExit {
        throw EventResult.Type.INTERRUPT_TRUE.exit(cx, mapExitValue(cx, value));
    }

    @HideFromJS
    default Object exit(Context cx, @Nullable Object value) throws EventExit {
        throw EventResult.Type.INTERRUPT_DEFAULT.exit(cx, mapExitValue(cx, value));
    }
}
