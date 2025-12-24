package com.chen1335.immersiveEngineeringJs.kubejs.event;

import blusunrize.immersiveengineering.api.multiblocks.TemplateMultiblock;
import com.chen1335.immersiveEngineeringJs.api.IEEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.ICancellableEvent;

public class CreateStructureEvent extends IEEvent implements ICancellableEvent {

    private final TemplateMultiblock multiblock;
    private final Level world;
    private final BlockPos pos;
    private final Direction side;
    private final Player player;

    public CreateStructureEvent(TemplateMultiblock multiblock, Level world, BlockPos pos, Direction side, Player player) {
        this.multiblock = multiblock;
        this.world = world;
        this.pos = pos;
        this.side = side;
        this.player = player;
    }


    public Level getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Direction getSide() {
        return side;
    }

    public Player getPlayer() {
        return player;
    }

    public TemplateMultiblock getMultiblock() {
        return multiblock;
    }
}
