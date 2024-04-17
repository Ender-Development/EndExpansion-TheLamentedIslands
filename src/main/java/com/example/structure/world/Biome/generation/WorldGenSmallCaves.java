package com.example.structure.world.Biome.generation;

import com.example.structure.entity.EntityChomper;
import com.example.structure.entity.EntityEnderEyeFly;
import com.example.structure.entity.EntityEnderKnight;
import com.example.structure.entity.knighthouse.EntityEnderShield;
import com.example.structure.entity.tileentity.MobSpawnerLogic;
import com.example.structure.entity.tileentity.tileEntityMobSpawner;
import com.example.structure.init.ModBlocks;
import com.example.structure.init.ModEntities;
import com.example.structure.util.ModRand;
import com.example.structure.world.WorldGenStructure;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenSmallCaves extends WorldGenStructure {
    /**
     * Generates small caves that will hold ruins and mini-structures within, this is a test fabric to hopefully add more onto this biome
     * @param name
     */


    public WorldGenSmallCaves(String name) {
        super("ashbiome/" + name);
    }


    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        BlockPos modified = position.add(15, 0, 15);
        if(getGroundFromAbove(worldIn, position.getX(), position.getZ()) > 50 && getGroundFromAbove(worldIn, modified.getX(), modified.getZ()) > 50 &&
        !worldIn.isAirBlock(position) && !worldIn.isAirBlock(modified)) {
            System.out.println("Generated cave at " + position);
            return super.generate(worldIn, rand, position);

        }
        else {
            System.out.println("returned False");
            return false;
        }
    }


    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random random) {
        if(function.startsWith("mob") && generateMobSpawn()) {
            world.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER_ASH.getDefaultState(), 2);
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof tileEntityMobSpawner) {
                ((tileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(
                        new MobSpawnerLogic.MobSpawnData[]{
                                //PlaceHolder
                                new MobSpawnerLogic.MobSpawnData(ModEntities.getID(EntityChomper.class), 1)
                        },
                        new int[]{1},
                        1,
                        35);
            }
        } else {
            world.setBlockToAir(pos);
        }
    }


    public boolean generateMobSpawn() {
        int randomNumberGenerator = ModRand.range(0, 10);
        if (randomNumberGenerator >= 7) {
            return false;
        }
        return true;
    }


    public static int getGroundFromAbove(World world, int x, int z)
    {
        int y = 255;
        boolean foundGround = false;
        while(!foundGround && y-- >= 31)
        {
            Block blockAt = world.getBlockState(new BlockPos(x,y,z)).getBlock();
            foundGround =  blockAt == ModBlocks.END_ASH || blockAt == ModBlocks.BROWN_END_STONE;
        }

        return y;
    }
}
