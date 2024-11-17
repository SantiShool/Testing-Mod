package com.nukateam.nukacraft.common.data.utils;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Collection;
import java.util.Iterator;

public class VoxelShapeHelper {
    public VoxelShapeHelper() {
    }

    public static VoxelShape combineAll(Collection<VoxelShape> shapes) {
        VoxelShape result = Shapes.empty();

        VoxelShape shape;
        for (Iterator var2 = shapes.iterator(); var2.hasNext(); result = Shapes.joinUnoptimized(result, shape, BooleanOp.OR)) {
            shape = (VoxelShape) var2.next();
        }

        return result.optimize();
    }
}
