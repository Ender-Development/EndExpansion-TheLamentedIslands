package com.example.structure.entity.model;

import com.example.structure.entity.EntityChomper;
import com.example.structure.entity.EntityController;
import com.example.structure.entity.barrend.guard.EntityBarrendGuard;
import com.example.structure.entity.model.geo.GeoModelExtended;
import com.example.structure.util.ModReference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ModelBarrendGuard extends GeoModelExtended<EntityBarrendGuard> {

    public ModelBarrendGuard(ResourceLocation model, ResourceLocation textureDefault, String entityName) {
        super(model, textureDefault, entityName);
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBarrendGuard animatable) {
        return new ResourceLocation(ModReference.MOD_ID, "animations/animation.barrend_guard.json");
    }

    @Override
    public void setLivingAnimations(EntityBarrendGuard entity, Integer uniqueID, AnimationEvent customPredicate) {
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
