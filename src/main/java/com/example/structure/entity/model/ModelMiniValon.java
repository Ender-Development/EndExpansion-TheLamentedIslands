package com.example.structure.entity.model;

import com.example.structure.entity.knighthouse.EntityEnderShield;
import com.example.structure.entity.trader.EntityMiniValon;
import com.example.structure.util.ModReference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ModelMiniValon extends AnimatedGeoModel<EntityMiniValon> {
    @Override
    public ResourceLocation getModelLocation(EntityMiniValon object) {
        return new ResourceLocation(ModReference.MOD_ID, "geo/entity/effects/geo.mini_valon.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMiniValon object) {
        return new ResourceLocation(ModReference.MOD_ID, "textures/entity/mini_valon.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityMiniValon animatable) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.mini_valon.json");
    }

    @Override
    public void setLivingAnimations(EntityMiniValon entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));


    }

    @Override
    public IBone getBone(String boneName) {
        return super.getBone(boneName);
    }
}
