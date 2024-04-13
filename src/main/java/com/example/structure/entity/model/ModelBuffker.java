package com.example.structure.entity.model;

import com.example.structure.entity.EntityBuffker;
import com.example.structure.entity.model.geo.GeoModelExtended;
import com.example.structure.util.ModReference;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.raw.pojo.Bone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ModelBuffker extends GeoModelExtended<EntityBuffker> {
    public ModelBuffker(ResourceLocation model, ResourceLocation textureDefault, String entityName) {
        super(model, textureDefault, entityName);
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBuffker entityBuffker) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.buffker.json");
    }

    @Override
    public void setLivingAnimations(EntityBuffker entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("HeadJ");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));

    }

    @Override
    public IBone getBone(String boneName) {
        return super.getBone(boneName);
    }
}
