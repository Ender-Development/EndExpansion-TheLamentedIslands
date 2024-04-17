package com.example.structure.world.api.mines;

import com.example.structure.config.ModConfig;
import com.example.structure.entity.EntityBuffker;
import com.example.structure.entity.EntityEnderKnight;
import com.example.structure.entity.endking.EntityEndKing;
import com.example.structure.entity.knighthouse.EntityEnderShield;
import com.example.structure.entity.seekers.EndSeeker;
import com.example.structure.entity.tileentity.MobSpawnerLogic;
import com.example.structure.entity.tileentity.tileEntityMobSpawner;
import com.example.structure.init.ModBlocks;
import com.example.structure.init.ModEntities;
import com.example.structure.util.ModRand;
import com.example.structure.world.misc.ModStructureTemplate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class MinesTemplate extends ModStructureTemplate {

    public MinesTemplate() {

    }

    public MinesTemplate(TemplateManager manager, String type, BlockPos pos, Rotation rot, int distance, boolean overWriteIn) {
        super(manager, type, pos,distance, rot, overWriteIn);
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random rand, StructureBoundingBox sbb) {
        //Single Mob Spawn
     if(function.startsWith("mob")) {
            if (generateMobSpawn()) {
                world.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER_ASH.getDefaultState(), 2);
                TileEntity tileentity = world.getTileEntity(pos);
                if (tileentity instanceof tileEntityMobSpawner) {
                    ((tileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(
                            new MobSpawnerLogic.MobSpawnData[]{
                                    new MobSpawnerLogic.MobSpawnData(ModEntities.getID(EntityEnderKnight.class), 1),
                                    new MobSpawnerLogic.MobSpawnData(ModEntities.getID(EntityEnderShield.class), 1)
                            },
                            new int[]{5, 1},
                            1,
                            24);
                }
            } else {
                world.setBlockToAir(pos);
            }
        }
    }


    //Generator for Mob Spawns
    public boolean generateMobSpawn() {
        int randomNumberGenerator = ModRand.range(0, 10);
        if (randomNumberGenerator >= 3) {
            return false;
        }
        return true;
    }

    @Override
    public String templateLocation() {
        return "mines";
    }
}
