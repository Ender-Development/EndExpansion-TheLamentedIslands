package com.example.structure.entity.knighthouse;

import com.example.structure.config.MobConfig;
import com.example.structure.config.ModConfig;
import com.example.structure.entity.EntityModBase;
import com.example.structure.entity.Projectile;
import com.example.structure.entity.ai.EntityAerialTimedAttack;
import com.example.structure.entity.ai.EntityFlyMoveHelper;
import com.example.structure.entity.endking.EntityRedCrystal;
import com.example.structure.entity.knighthouse.knightlord.ActionQuickSlash;
import com.example.structure.entity.knighthouse.knightlord.ActionScatterSlash;
import com.example.structure.entity.knighthouse.knightlord.EntityBloodSlash;
import com.example.structure.entity.util.IAttack;
import com.example.structure.entity.util.TimedAttackIniator;
import com.example.structure.util.*;
import com.example.structure.util.handlers.ModSoundHandler;
import com.example.structure.util.handlers.ParticleManager;
import com.sun.jna.platform.win32.WinBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EntityKnightLord extends EntityKnightBase implements IAnimatable, IAttack, IAnimationTickable {

    private static final DataParameter<Boolean> FLYING_MODE = EntityDataManager.createKey(EntityKnightLord.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> PIERCE = EntityDataManager.createKey(EntityKnightLord.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> MULTI_ATTACK = EntityDataManager.createKey(EntityKnightLord.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BLOCKING = EntityDataManager.createKey(EntityKnightLord.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> MULTI_STRIKE = EntityDataManager.createKey(EntityKnightLord.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BLOOD_SLASH = EntityDataManager.createKey(EntityKnightLord.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> COMBO_ATTACK = EntityDataManager.createKey(EntityKnightLord.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SUMMON_CRYSTALS = EntityDataManager.createKey(EntityKnightLord.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SUMMON = EntityDataManager.createKey(EntityKnightLord.class, DataSerializers.BOOLEAN);


    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
     //   nbt.setBoolean("Flying_Mode", this.dataManager.get(FLYING_MODE));
     //   nbt.setBoolean("Pierce", this.dataManager.get(PIERCE));
     //   nbt.setBoolean("Multi_Attack", this.dataManager.get(MULTI_ATTACK));
     //   nbt.setBoolean("Blocking", this.dataManager.get(BLOCKING));
     //   nbt.setBoolean("Multi_Strike", this.dataManager.get(MULTI_STRIKE));
     //   nbt.setBoolean("Summon_Crystals", this.dataManager.get(SUMMON_CRYSTALS));
      //  nbt.setBoolean("Summon", this.dataManager.get(SUMMON));

        nbt.setBoolean("Flying_Mode", this.isFlyingMode());
        nbt.setBoolean("Pierce", this.isPierce());
        nbt.setBoolean("Multi_Attack", this.isMultiAttack());
        nbt.setBoolean("Blocking", this.isBlocking());
        nbt.setBoolean("Multi_Strike", this.isMultiStrike());
        nbt.setBoolean("Summon_Crystals", this.isSummonCrystals());
        nbt.setBoolean("Summon", this.isSummonKnight());
        nbt.setBoolean("Blood_Slash", this.isBloodSlash());
        nbt.setBoolean("Combo_Attack", this.isComboAttack());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
     //   this.dataManager.set(FLYING_MODE, nbt.getBoolean("Flying_Mode"));
     //   this.dataManager.set(PIERCE, nbt.getBoolean("Pierce"));
     //   this.dataManager.set(MULTI_ATTACK, nbt.getBoolean("Multi_Attack"));
     //   this.dataManager.set(BLOCKING, nbt.getBoolean("Blocking"));
     //   this.dataManager.set(MULTI_STRIKE, nbt.getBoolean("Multi_Strike"));
      //  this.dataManager.set(SUMMON_CRYSTALS, nbt.getBoolean("Summon_Crystals"));
      //  this.dataManager.set(SUMMON, nbt.getBoolean("Summon"));

        this.setFlyingMode(nbt.getBoolean("Flying_Mode"));
        this.setPierce(nbt.getBoolean("Pierce"));
        this.setMultiAttack(nbt.getBoolean("Multi_Attack"));
        this.setBlocking(nbt.getBoolean("Blocking"));
        this.setMultiStrike(nbt.getBoolean("Multi_Strike"));
        this.setSummonCrystals(nbt.getBoolean("Summon_Crystals"));
        this.setSummonKnight(nbt.getBoolean("Summon"));
        this.setComboAttack(nbt.getBoolean("Combo_Attack"));
        this.setBloodSlash(nbt.getBoolean("Blood_Slash"));

    }
    public void setFlyingMode(boolean value) {this.dataManager.set(FLYING_MODE, Boolean.valueOf(value));}
    public boolean isFlyingMode() {return this.dataManager.get(FLYING_MODE);}
    public void setPierce(boolean value) {this.dataManager.set(PIERCE, Boolean.valueOf(value));}
    public boolean isPierce() {return this.dataManager.get(PIERCE);}
    public void setBlocking(boolean value) {this.dataManager.set(BLOCKING, Boolean.valueOf(value));}
    public boolean isBlocking() {return this.dataManager.get(BLOCKING);}
    public void setMultiAttack(boolean value) {this.dataManager.set(MULTI_ATTACK, Boolean.valueOf(value));}
    public boolean isMultiAttack() {return this.dataManager.get(MULTI_ATTACK);}
    public void setMultiStrike(boolean value) {this.dataManager.set(MULTI_STRIKE, Boolean.valueOf(value));}
    public boolean isMultiStrike() {return this.dataManager.get(MULTI_STRIKE);}
    public void setSummonCrystals(boolean value) {this.dataManager.set(SUMMON_CRYSTALS, Boolean.valueOf(value));}
    public boolean isSummonCrystals() {return this.dataManager.get(SUMMON_CRYSTALS);}
    public void setSummonKnight(boolean value) {this.dataManager.set(SUMMON, Boolean.valueOf(value));}
    public boolean isSummonKnight() {return this.dataManager.get(SUMMON);}
    public void setBloodSlash(boolean value) {this.dataManager.set(BLOOD_SLASH, Boolean.valueOf(value));}
    public boolean isBloodSlash() {return this.dataManager.get(BLOOD_SLASH);}
    public void setComboAttack(boolean value) {this.dataManager.set(COMBO_ATTACK, Boolean.valueOf(value));}
    public boolean isComboAttack() {return this.dataManager.get(COMBO_ATTACK);}
    private float timeSinceNoTarget = 0;

    public int blockTimer = 40;


    private EntityAIBase attackAi = new EntityAerialTimedAttack(this, 10, 2, 30, new TimedAttackIniator<>(this, 20));
    private final String ANIM_WALKING_ARMS = "walk_upper";
    private final String ANIM_WALKING_LEGS = "walk_lower";
    private final String ANIM_IDLE = "idle";
    private final String ANIM_RING = "ring";
    private final String ANIM_FLYING = "flying";
    private final String ANIM_PIERCE = "fly_pierce";

    private final String ANIM_MULTI_ATTACK = "attack";
    private final String ANIM_STRIKE_ATTACK = "strike";

    private final String ANIM_BLOOD_SLASH = "blood_slash";
    private final String ANIM_COMBO = "combo";
    private final String ANIM_CRYSTALS = "crystals";
    private final String ANIM_BLOCK = "block";

    private final String ANIM_ON_SUMMON = "summon";
    private Consumer<EntityLivingBase> prevAttack;

    Supplier<Projectile> ground_projectiles = () -> new EntityBloodSlash(world, this, (float) MobConfig.unholy_knight_damage, null, ModColors.RED);
    private boolean isGroundAttack = false;
    private AnimationFactory factory = new AnimationFactory(this);
    public EntityKnightLord(World worldIn, float x, float y, float z) {
        super(worldIn, x, y, z);
        addEvent(()-> this.playSound(ModSoundHandler.LORD_SUMMON, 1.5F, 1.0F), 5);
        this.iAmBossMob = true;
    }

    //Particle Call
    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
            if(rand.nextInt(2) == 0) {
                world.setEntityState(this, ModUtils.PARTICLE_BYTE);
            }
    }

    @Override
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);
        if(id == ModUtils.PARTICLE_BYTE) {
            ParticleManager.spawnColoredSmoke(world, getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0, 1.5, 0))), ModColors.RED, new Vec3d(ModRand.getFloat(1) * 0.1,ModRand.getFloat(1) * 0.1,ModRand.getFloat(1) * 0.1));
        }
    }

    int flyTimer = 20;

    @Override
    public void onUpdate() {
        super.onUpdate();

        if(this.lockLook) {

            this.rotationPitch = this.prevRotationPitch;
            this.rotationYaw = this.prevRotationYaw;
            this.rotationYawHead = this.prevRotationYawHead;
            this.renderYawOffset = this.rotationYaw;

        }

        blockTimer--;

        EntityLivingBase target = this.getAttackTarget();
        //Switches to Flying upon spotting the Enemy
        if(target != null && !this.isFlyingMode() && !this.isSummonKnight()) {
            this.tasks.addTask(4, attackAi);
            this.moveHelper = new EntityFlyMoveHelper(this);
            this.navigator = new PathNavigateFlying(this, world);
            this.setFlyingMode(true);
        }

        if(this.isFlyingMode()) {
            if(flyTimer == 20) {
                this.playFlySound();
            }
            flyTimer--;
            if(flyTimer <= 0) {
                flyTimer = 20;
            }
        }

        //If no enemy is present after awhile it will switch back to ground
        if(this.isFlyingMode() && this.timeSinceNoTarget > 200) {
            this.tasks.removeTask(attackAi);
            this.moveHelper = new EntityMoveHelper(this);
            this.navigator = new PathNavigateGround(this, world);
            this.setFlyingMode(false);
        }

        if(this.isSummonCrystals() && !this.isImmovable() || this.isGroundAttack && !this.isImmovable()) {
            this.motionY--;
        }

    }

    public void playFlySound() {
        addEvent(()-> {
            Vec3d currentPos = this.getPositionVector();
            world.playSound(currentPos.x, currentPos.y, currentPos.z, ModSoundHandler.LORD_KNIGHT_FLY, SoundCategory.HOSTILE, 0.7f, 0.9F, true);
        }, 10);
    }



    @Override
    public void entityInit() {
        super.entityInit();
       this.dataManager.register(FLYING_MODE, Boolean.valueOf(false));
       this.dataManager.register(PIERCE, Boolean.valueOf(false));
       this.dataManager.register(MULTI_ATTACK, Boolean.valueOf(false));
       this.dataManager.register(BLOCKING, Boolean.valueOf(false));
       this.dataManager.register(SUMMON_CRYSTALS, Boolean.valueOf(false));
       this.dataManager.register(MULTI_STRIKE, Boolean.valueOf(false));
       this.dataManager.register(BLOOD_SLASH, Boolean.valueOf(false));
       this.dataManager.register(COMBO_ATTACK, Boolean.valueOf(false));
       this.dataManager.register(SUMMON, Boolean.valueOf(true));
    }

    public EntityKnightLord(World worldIn) {
        super(worldIn);
        this.setImmovable(true);
        this.setSize(0.8f, 2.0f);
        this.iAmBossMob = true;
        this.experienceValue = 400;
        addEvent(()-> this.playSound(ModSoundHandler.LORD_SUMMON, 1.5F, 1.0F), 5);
        addEvent(()-> {
            this.setImmovable(false);
            this.setSummonKnight(false);
        }, 10);
    }

    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.34D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double) MobConfig.unholy_knight_health * getHealthModifierAsh());
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue((double) MobConfig.unholy_knight_armor * ModConfig.biome_multiplier);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.7D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(MobConfig.unholy_knight_armor_toughness * ModConfig.biome_multiplier);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }
    @Override
    protected void updateFallState(double y, boolean onGroundIn, @Nonnull IBlockState state, @Nonnull BlockPos pos) {
    }


    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        double distance = Math.sqrt(distanceSq);
        if(!this.isFightMode() && !this.isBlocking() && !this.isSummonKnight()) {
            List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(pierceAttack, multiAttack, multiStrike, summonLine, randomTeleport, combo_attack, blood_slash_attack));
            double[] weights = {
                    (distance > 3 && prevAttack != pierceAttack) ? 1 : 0,
                    (distance < 4 && prevAttack != multiAttack) ? 1 : 0,
                    (distance < 4 && prevAttack != multiStrike) ? 1 : 0,
                    (distance > 3 && prevAttack != summonLine) ? 1 : 0,
                    (distance > 1 && prevAttack != randomTeleport) ? 1 : 0,
                    (distance > 2 && prevAttack != combo_attack && getScaleForNewAttacks()) ? 1 : 0, //MAKE SURE TO HAVE SCALE FACTOR INVOLVED
                    (distance > 3 && prevAttack != blood_slash_attack && getScaleForNewAttacks()) ? 1 : 0 //MAKE SURE TO HAVE SCALE FACTOR INVOLVED
            };

            prevAttack = ModRand.choice(attacks, rand, weights).next();

            prevAttack.accept(target);
        }
        return (this.isBlocking()) ? 60 : 10;
    }

    private final Consumer<EntityLivingBase> combo_attack = (target) -> {
      this.setFightMode(true);
      this.setComboAttack(true);
      this.setImmovable(true);
      addEvent(()-> {
          Vec3d savedPos = target.getPositionVector();
          this.lockLook = true;

        addEvent(()-> {
            this.setImmovable(false);
            double distance = this.getPositionVector().distanceTo(savedPos);
            ModUtils.leapTowards(this, savedPos, (float) (distance * 0.11),0F);
        }, 7);
      }, 7);

      addEvent(()-> {
          this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / (rand.nextFloat() * 0.4F + 0.4f));
          Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.3, 0.75, 0)));
          DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
          float damage = (float) (MobConfig.unholy_knight_damage * getAttackModifierAsh());
          ModUtils.handleAreaImpact(1.25f, (e) -> damage, this, offset, source, 0.5f, 0, false);
          this.lockLook = false;
      }, 20);

      addEvent(()-> {
          Vec3d savedPos = target.getPositionVector();
          this.setImmovable(true);
          addEvent(()-> {
              this.setImmovable(false);
              double distance = this.getPositionVector().distanceTo(savedPos);
              ModUtils.leapTowards(this, savedPos, (float) (distance * 0.065),0F);
              this.lockLook = true;
          }, 7);
      }, 23);

      addEvent(()-> {
          this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / (rand.nextFloat() * 0.4F + 0.4f));
          Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.3, 0.75, 0)));
          DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
          float damage = (float) (MobConfig.unholy_knight_damage * getAttackModifierAsh());
          ModUtils.handleAreaImpact(1.25f, (e) -> damage, this, offset, source, 0.5f, 0, false);
      }, 35);

      addEvent(()-> this.lockLook = false, 40);

      addEvent(()-> this.isGroundAttack = true, 44);

      addEvent(()-> {
        new ActionQuickSlash(ground_projectiles, 0.7F).performAction(this, target);
        this.setImmovable(true);
        this.isGroundAttack = false;
      }, 57);

      addEvent(()-> this.setImmovable(false), 80);

      addEvent(()-> {
        this.setFightMode(false);
        this.setComboAttack(false);
      }, 85);
    };


    private final Consumer<EntityLivingBase> blood_slash_attack = (target) -> {
      this.setFightMode(true);
      this.setBloodSlash(true);
        this.setImmovable(true);

        addEvent(()-> {
            this.setImmovable(false);
            this.isGroundAttack = true;
        }, 17);

        addEvent(()-> {
            //Do Action
            new ActionScatterSlash(ground_projectiles, 0.75F).performAction(this, target);
        }, 20);

        addEvent(()-> {
            this.isGroundAttack = false;
            this.setImmovable(true);
        }, 25);

        addEvent(()-> {
            this.setImmovable(false);
        }, 45);

      addEvent(()-> {
          this.setFightMode(false);
          this.setBloodSlash(false);
      },50);
    };
    private final Consumer<EntityLivingBase> randomTeleport = (target) -> {
        this.setFightMode(true);
        addEvent(()-> {
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.3F));
            new ActionLordTeleport(ModColors.RED).performAction(this, target);
        }, 10);

        addEvent(()-> this.setFightMode(false), 20);
    };

    private final Consumer<EntityLivingBase> summonLine = (target) -> {
      this.setFightMode(true);
      this.setSummonCrystals(true);
      addEvent(()-> {
        this.setImmovable(true);
          Vec3d targetPosition = target.getPositionVector();
          Vec3d throwerPosition = this.getPositionVector();
          Vec3d dir = targetPosition.subtract(throwerPosition).normalize();
          AtomicReference<Vec3d> spawnPos = new AtomicReference<>(throwerPosition);

            addEvent(()-> {
                for (int t = 0; t < 7; t += 1) {
                    int additive = t;
                    addEvent(() -> {
                        ModUtils.lineCallback(throwerPosition.add(dir), throwerPosition.add(dir.scale(additive)), additive * 2, (pos, r) -> {
                            spawnPos.set(pos);
                        });
                        Vec3d initPos = spawnPos.get();
                        EntityRedCrystal crystal = new EntityRedCrystal(this.world);
                        BlockPos blockPos = new BlockPos(initPos.x, initPos.y, initPos.z);
                        crystal.setPosition(blockPos);
                        crystal.playSound(SoundEvents.EVOCATION_FANGS_ATTACK, 1.0f, 1.0f / (rand.nextFloat() * 0.4F + 0.4f));
                        this.world.spawnEntity(crystal);

                    }, t);
                }
            }, 10);
      }, 8);
      addEvent(()-> {
          this.setImmovable(false);
          this.setFightMode(false);
          this.setSummonCrystals(false);
      }, 35);
    };
    private final Consumer<EntityLivingBase> multiStrike = (target) -> {
    this.setFightMode(true);
    this.setMultiStrike(true);

        addEvent(()-> {
            Vec3d targetPos = target.getPositionVector();
            addEvent(()-> {
                if(!this.isBlocking()) {
                    ModUtils.leapTowards(this, targetPos, 0.4f, 0.05f);
                    this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / (rand.nextFloat() * 0.4F + 0.4f));
                    Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.5, 1.3, 0)));
                    DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
                    float damage = (float) (MobConfig.unholy_knight_damage * getAttackModifierAsh());
                    ModUtils.handleAreaImpact(1.0f, (e) -> damage, this, offset, source, 0.5f, 0, false);
                }
            }, 7);
        }, 10);

        addEvent(()-> {
            Vec3d targetPos = target.getPositionVector();
            addEvent(()-> {
            if(!this.isBlocking()) {
                ModUtils.leapTowards(this, targetPos, 0.45f, 0.05f);
                this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / (rand.nextFloat() * 0.4F + 0.4f));
                Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.5, 1.3, 0)));
                DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
                float damage = (float) (MobConfig.unholy_knight_damage * getAttackModifierAsh());
                ModUtils.handleAreaImpact(1.0f, (e) -> damage, this, offset, source, 0.5f, 0, false);
            }
            }, 18);
        }, 15);

    addEvent(()-> {
        this.setFightMode(false);
        this.setMultiStrike(false);
    }, 40);
    };

    private final Consumer<EntityLivingBase> multiAttack = (target) -> {
      this.setFightMode(true);
      this.setMultiAttack(true);
      addEvent(()-> this.lockLook = true, 5);

        addEvent(()-> {
            if(!this.isBlocking()) {
                ModUtils.leapTowards(this, target.getPositionVector(), 0.4f, 0f);
            }
          }, 5);

        addEvent(()-> {
            if(!this.isBlocking()) {
                this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / (rand.nextFloat() * 0.4F + 0.4f));
                Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.5, 1.3, 0)));
                DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
                float damage = (float) (MobConfig.unholy_knight_damage * getAttackModifierAsh());
                ModUtils.handleAreaImpact(1.0f, (e) -> damage, this, offset, source, 0.5f, 0, false);
            }
            addEvent(()-> this.lockLook =false, 5);
        }, 10);

        addEvent(()-> {
            if(!this.isBlocking()) {
                this.lockLook = true;
                ModUtils.leapTowards(this, target.getPositionVector(), 0.43f, 0f);
            }
        }, 25);

        addEvent(()-> {
            if(!this.isBlocking()) {
                this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / (rand.nextFloat() * 0.4F + 0.4f));
                Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.5, 1.3, 0)));
                DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
                float damage = (float) (MobConfig.unholy_knight_damage * getAttackModifierAsh());
                ModUtils.handleAreaImpact(1.0f, (e) -> damage, this, offset, source, 0.5f, 0, false);
            }
        }, 30);

        addEvent(()-> this.lockLook = false, 35);
      addEvent(()-> {
          if(!this.isBlocking()) {
              this.setFightMode(false);
              this.setMultiAttack(false);
          }

      }, 40);
    };

    private final Consumer<EntityLivingBase> pierceAttack = (target) -> {
        this.setFightMode(true);
        this.setPierce(true);
        addEvent(()-> this.lockLook = true, 5);
        Vec3d targetPos = target.getPositionVector();
        addEvent(()-> ModUtils.leapTowards(this, targetPos, 0.9f, 0.1f), 18);
        addEvent(()-> {
            for(int i = 0; i < 10; i += 5) {
                addEvent(()-> {
                    if(!this.isBlocking()) {
                        Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1.3, 1.3, 0)));
                        DamageSource source = ModDamageSource.builder().type(ModDamageSource.MOB).directEntity(this).build();
                        float damage = (float) ((MobConfig.unholy_knight_damage + 1) * getAttackModifierAsh());
                        ModUtils.handleAreaImpact(1.0f, (e) -> damage, this, offset, source, 0.5f, 0, false);
                    }
                }, i);
            }
        }, 18);

        addEvent(()-> this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f / (rand.nextFloat() * 0.4F + 0.4f)), 23);
        addEvent(()-> this.lockLook = false, 35);
        addEvent(()-> {
            if(!this.isBlocking()) {
                this.setFightMode(false);
                this.setPierce(false);
            }
        }, 35);
    };

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "idle_controller", 0, this::predicateIdle));
        animationData.addAnimationController(new AnimationController(this, "arms_controller", 0, this::predicateArms));
        animationData.addAnimationController(new AnimationController(this, "legs_controller", 0, this::predicateLegs));
        animationData.addAnimationController(new AnimationController(this, "blink_controller", 0, this::predicateBlink));
        animationData.addAnimationController(new AnimationController(this, "wings_controller", 20, this::predicateWings));
        animationData.addAnimationController(new AnimationController(this, "attack_controller", 0, this::predicateAttack));
        animationData.addAnimationController(new AnimationController(this, "block_controller", 0, this::predicateBlock));
    }

    private <E extends IAnimatable> PlayState predicateBlock(AnimationEvent<E> event) {
        if(!this.isSummonKnight()) {
            if (this.isBlocking()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_BLOCK, false));
                return PlayState.CONTINUE;
            }
        }
        if(this.isSummonKnight()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_ON_SUMMON, false));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }
    private<E extends IAnimatable> PlayState predicateWings(AnimationEvent<E> event) {
        if(this.isFlyingMode() && !this.isSummonKnight()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_FLYING, true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private<E extends IAnimatable> PlayState predicateAttack(AnimationEvent<E> event) {
        if(this.isFightMode() && !this.isBlocking()) {
            if(this.isPierce()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_PIERCE, false));
                return PlayState.CONTINUE;
            }
            if(this.isMultiAttack()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_MULTI_ATTACK, false));
                return PlayState.CONTINUE;
            }
            if(this.isMultiStrike()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_STRIKE_ATTACK, false));
                return PlayState.CONTINUE;
            }
            if(this.isSummonCrystals()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_CRYSTALS, false));
                return PlayState.CONTINUE;
            }
            if(this.isBloodSlash()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_BLOOD_SLASH, false));
                return PlayState.CONTINUE;
            }
            if(this.isComboAttack()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_COMBO, false));
                return PlayState.CONTINUE;
            }
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    //Keep the Ring constantly turning
    private <E extends IAnimatable> PlayState predicateBlink(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_RING, true));
        return PlayState.CONTINUE;
    }
    //Movement of Arms in Idle and walking
    private <E extends IAnimatable> PlayState predicateArms(AnimationEvent<E> event) {

        if (!(event.getLimbSwingAmount() > -0.10F && event.getLimbSwingAmount() < 0.10F) && !this.isFightMode() && !this.isFlyingMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_WALKING_ARMS, true));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    //Movement of legs in Idle and walking
    private <E extends IAnimatable>PlayState predicateLegs(AnimationEvent<E> event) {
        if(!(event.getLimbSwingAmount() > -0.10F && event.getLimbSwingAmount() < 0.10F) && !this.isFlyingMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_WALKING_LEGS, true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    // Idle Handler
    private<E extends IAnimatable> PlayState predicateIdle(AnimationEvent<E> event) {

        if(event.getLimbSwingAmount() > -0.09F && event.getLimbSwingAmount() < 0.09F && !this.isFlyingMode()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ANIM_IDLE, true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if(this.isFlyingMode()) {
            ModUtils.aerialTravel(this, strafe, vertical, forward);
        } else {
            super.travel(strafe, vertical, forward);
        }

    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (amount > 0.0F && this.canBlockDamageSource(source)) {
            this.damageShield(amount);

            if (!source.isProjectile()) {
                Entity entity = source.getImmediateSource();

                if (entity instanceof EntityLivingBase) {
                    this.blockUsingShield((EntityLivingBase) entity);
                }
            }
            this.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 1.0f, 0.6f + ModRand.getFloat(0.2f));

            return false;
        }
        return super.attackEntityFrom(source, amount);
    }

    private boolean canBlockDamageSource(DamageSource damageSourceIn) {
        if (!damageSourceIn.isUnblockable() && blockTimer <= 0 && !this.isFightMode() && !this.isSummonKnight()) {
            Vec3d vec3d = damageSourceIn.getDamageLocation();
            this.doBlockAction();

            if (vec3d != null) {
                Vec3d vec3d1 = this.getLook(1.0F);
                Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(this.posX, this.posY, this.posZ)).normalize();
                vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);

                return vec3d2.dotProduct(vec3d1) < 0.0D;
            }
        }

        return false;
    }

    protected void doBlockAction() {
        this.setBlocking(true);

        addEvent(()-> {
            this.setBlocking(false);
            this.blockTimer = 40;
        }, 30);
    }

    private static final ResourceLocation LOOT = new ResourceLocation(ModReference.MOD_ID, "knight_lord");
    private static final ResourceLocation LOOT_ARENA = new ResourceLocation(ModReference.MOD_ID, "knight_lord_arena");
    @Override
    protected ResourceLocation getLootTable() {
        if(getScaleForNewAttacks()) {
            return LOOT_ARENA;
        }
        return LOOT;
    }
    @Override
    protected boolean canDropLoot() {
        return true;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSoundHandler.LORD_KNIGHT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundHandler.LORD_KNIGHT_DEATH;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void tick() {

    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
    }
}
