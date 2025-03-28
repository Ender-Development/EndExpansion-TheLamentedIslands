package com.example.structure.blocks;

import com.example.structure.config.ProgressionConfig;
import com.example.structure.entity.EntityGroundCrystal;
import com.example.structure.entity.shadowPlayer.EntityShadowPlayer;
import com.example.structure.entity.tileentity.TileEntityUpdater;
import com.example.structure.init.ModCreativeTabs;
import com.example.structure.util.ModUtils;
import com.google.common.base.Predicate;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiFunction;

public class BlockShadowKey extends BlockBase implements IBlockUpdater, ITileEntityProvider {
    private Item activationItem;
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    int counter = 0;
    BiFunction<World, BlockPos, Entity> spawnPortal;

    /**
     * Used to Summon the Crystal Knight via a Block
     * @param name
     */
    public BlockShadowKey(String name) {
        this(name, null
        );
    }

    public BlockShadowKey(String name, Item item) {
        super(name, Material.ROCK, 1000, 10000, SoundType.STONE);
        this.setBlockUnbreakable();
        this.activationItem = item;
        this.hasTileEntity = true;
        this.setCreativeTab(ModCreativeTabs.ITEMS);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public void update(World world, BlockPos pos) {
        counter++;
        if (counter % 5 == 0) {
            List<EntityPlayerSP> list = world.<EntityPlayerSP>getPlayers(EntityPlayerSP.class, new Predicate<EntityPlayerSP>() {
                @Override
                public boolean apply(@Nullable EntityPlayerSP player) {
                    return player.getHeldItem(EnumHand.MAIN_HAND).getItem() == activationItem;
                }
            });

        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityUpdater();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        if(worldIn.getBlockState(pos.up()).isFullBlock()) {
            worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
        }
        worldIn.removeTileEntity(pos);

    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY,
                                    float hitZ) {
        if (playerIn.getHeldItemMainhand() != null && playerIn.getHeldItemMainhand().getItem() == this.activationItem) {
            playerIn.getHeldItem(hand).shrink(1);
            if(!worldIn.isRemote) {
                new EntityShadowPlayer(worldIn).onSummon(pos, playerIn);
            }
            worldIn.setBlockToAir(pos);
            worldIn.setBlockToAir(pos.up());
        } //else {
        //    playerIn.sendStatusMessage(new TextComponentTranslation(ProgressionConfig.lamentor_locked_message, new Object[0]), true);
      //  }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
