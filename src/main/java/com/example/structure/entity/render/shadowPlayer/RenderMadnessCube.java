package com.example.structure.entity.render.shadowPlayer;

import com.example.structure.entity.knighthouse.EntityEnderShield;
import com.example.structure.entity.model.shadowPlayer.ModelMadnessCube;
import com.example.structure.entity.model.shadowPlayer.ModelShadowPlayer;
import com.example.structure.entity.render.geo.GeoGlowingLayer;
import com.example.structure.entity.render.geo.RenderGeoExtended;
import com.example.structure.entity.shadowPlayer.EntityMadnessCube;
import com.example.structure.entity.shadowPlayer.EntityShadowPlayer;
import com.example.structure.entity.trader.EntityAvalon;
import com.example.structure.util.ModReference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import javax.annotation.Nullable;

public class RenderMadnessCube extends RenderGeoExtended<EntityMadnessCube> {

    public static final ResourceLocation MODEL_RESLOC = new ResourceLocation(ModReference.MOD_ID, "geo/entity/shadow/geo.evil_cube.json");

    public static final ResourceLocation TEXTURE = new ResourceLocation(ModReference.MOD_ID, "textures/entity/evil_cube.png");

    public RenderMadnessCube(RenderManager renderManager) {
        super(renderManager, new ModelMadnessCube(MODEL_RESLOC, TEXTURE, "madness_cube"));
        this.addLayer(new GeoGlowingLayer<EntityMadnessCube>(this, this.TEXTURE_GETTER, this.MODEL_ID_GETTER));

    }

    @Override
    public void doRender(EntityMadnessCube entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, EntityMadnessCube currentEntity) {
        return null;
    }

    @Override
    protected ItemCameraTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        return null;
    }

    @Nullable
    @Override
    protected IBlockState getHeldBlockForBone(String boneName, EntityMadnessCube currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(ItemStack item, String boneName, EntityMadnessCube currentEntity) {

    }

    @Override
    protected void preRenderBlock(IBlockState block, String boneName, EntityMadnessCube currentEntity) {

    }

    @Override
    protected void postRenderItem(ItemStack item, String boneName, EntityMadnessCube currentEntity) {

    }

    @Override
    protected void postRenderBlock(IBlockState block, String boneName, EntityMadnessCube currentEntity) {

    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, EntityMadnessCube currentEntity) {
        return null;
    }
}
