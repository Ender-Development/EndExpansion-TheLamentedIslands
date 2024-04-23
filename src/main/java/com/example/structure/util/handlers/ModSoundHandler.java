package com.example.structure.util.handlers;

import com.example.structure.util.ModReference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Keeps track of Sounds and used to call from here
 */
public class ModSoundHandler {
    //Living Sounds
    public static SoundEvent BOSS_HURT;
    public static SoundEvent BOSS_IDLE;
    public static SoundEvent BOSS_SUMMON;
    public static SoundEvent BOSS_DEATH;

    //Action Sounds
    public static SoundEvent BOSS_CAST_AMBIENT;
    public static SoundEvent BOSS_DASH;
    public static SoundEvent BOSS_DRAW_HAMMER;
    public static SoundEvent BOSS_DRAW_SWORD;

    //King Action Sounds
    public static SoundEvent KING_DASH;

    //NuclearBomb Sounds
    public static SoundEvent BOMB_EXPLODE;

    //Knight Household
    public static SoundEvent KNIGHT_STEP;
    public static SoundEvent KNIGHT_HURT;
    public static SoundEvent KNIGHT_DEATH;
    public static SoundEvent KNIGHT_IDLE;
    public static SoundEvent KNIGHT_DASH;
    public static SoundEvent KNIGHT_CAST_HEAL;
    public static SoundEvent KNIGHT_CAST_ATTACK;

    //End Seeker
    public static SoundEvent SEEKER_SHOOT;
    public static SoundEvent SEEKER_HOVER;
    public static SoundEvent SEEKER_HURT;
    public static SoundEvent SEEKER_ELDER_HURT;
    public static SoundEvent SEEKER_DASH;

    //Crystal HUM
    public static SoundEvent RED_CRYSTAL_HUM;
    //Compulsor On
    public static SoundEvent COMPULSOR_HUM;

    //Ashed Parasite
    public static SoundEvent PARASITE_IDLE;
    public static SoundEvent PARASITE_HURT;
    public static SoundEvent PARASITE_DEATH;
    public static SoundEvent PARASITE_STEP;

    //Stalker
    public static SoundEvent STALKER_HURT;
    public static SoundEvent STALKER_ATTACK_1;
    public static SoundEvent STALKER_SPOTTED;
    public static SoundEvent STALKER_SWING;

    public static SoundEvent STALKER_STEP;

    //Biome Ambience
    public static SoundEvent BIOME_AMBIENCE;

    //Chomper
    public static SoundEvent CHOMPER_IDLE;
    public static SoundEvent CHOMPER_HURT;
    public static SoundEvent CHOMPER_BITE;
    public static SoundEvent CHOMPER_LEAP;
    public static SoundEvent CHOMPER_WARN;
    public static SoundEvent CHOMPER_POP_OUT;

    //Unholy Knight
    public static SoundEvent LORD_KNIGHT_HURT;
    public static SoundEvent LORD_KNIGHT_DEATH;
    public static SoundEvent LORD_KNIGHT_FLY;
    public static SoundEvent LORD_SUMMON;

    //Thousand Sword Projectiles
    public static SoundEvent SWORD_HUM;
    public static SoundEvent SWORD_IMPACT;
    public static SoundEvent SWORD_SUMMON;

    //Summon Sword Entity
    public static SoundEvent TARGET_SUMMON;
    public static SoundEvent TARGET_IMPACT;

    //The Avalon
    public static SoundEvent AVALON_IDLE;

    public static SoundEvent AVALON_AGREE;

    public static SoundEvent AVALON_DISAGREE;


    public static void registerSounds() {
        BOSS_IDLE = registerSound("boss.idle", "entity");
        BOSS_HURT = registerSound("boss.hurt", "entity");
        BOSS_DEATH = registerSound("boss.death", "entity");
        BOSS_SUMMON = registerSound("boss.summon", "entity");

        BOSS_DRAW_HAMMER = registerSound("boss.hammer", "entity");
        BOSS_DRAW_SWORD = registerSound("boss.sword", "entity");
        BOSS_DASH = registerSound("boss.dash", "entity");
        BOSS_CAST_AMBIENT = registerSound("boss.cast", "entity");

        KING_DASH = registerSound("king.dash", "entity");

        BOMB_EXPLODE = registerSound("king.explode", "entity");

        KNIGHT_STEP = registerSound("knight.step", "entity");
        KNIGHT_HURT = registerSound("knight.hurt", "entity");
        KNIGHT_DEATH = registerSound("knight.death", "entity");
        KNIGHT_IDLE = registerSound("knight.idle", "entity");
        KNIGHT_DASH = registerSound("knight.dash", "entity");
        KNIGHT_CAST_HEAL = registerSound("knight.cast_heal", "entity");
        KNIGHT_CAST_ATTACK = registerSound("knight.cast", "entity");

        LORD_KNIGHT_HURT = registerSound("lord.hurt", "entity");
        LORD_KNIGHT_DEATH = registerSound("lord.death", "entity");
        LORD_KNIGHT_FLY = registerSound("lord.fly", "entity");
        LORD_SUMMON = registerSound("lord.summon", "entity");

        TARGET_SUMMON = registerSound("sword.summon", "entity");
        TARGET_IMPACT = registerSound("sword.impact", "entity");

        SWORD_HUM = registerSound("projectile.hover", "entity");
        SWORD_IMPACT = registerSound("projectile.impact", "entity");
        SWORD_SUMMON = registerSound("projectile.summon", "entity");

        SEEKER_SHOOT = registerSound("seeker.shoot", "entity");
        SEEKER_HOVER = registerSound("seeker.hover", "entity");
        SEEKER_HURT = registerSound("seeker.hurt", "entity");
        SEEKER_ELDER_HURT = registerSound("seeker.hurt_elder", "entity");
        SEEKER_DASH = registerSound("seeker.dash", "entity");

        RED_CRYSTAL_HUM = registerSound("crystal.glow", "block");
        COMPULSOR_HUM = registerSound("compulsor.sound", "block");

        PARASITE_IDLE = registerSound("parasite.idle", "entity");
        PARASITE_HURT = registerSound("parasite.hurt", "entity");
        PARASITE_DEATH = registerSound("parasite.death", "entity");
        PARASITE_STEP = registerSound("parasite.step", "entity");

        STALKER_HURT = registerSound("stalker.hurt", "entity");
        STALKER_ATTACK_1 = registerSound("stalker.attack", "entity");
        STALKER_SPOTTED = registerSound("stalker.spotted", "entity");
        STALKER_SWING = registerSound("stalker.swing", "entity");
        STALKER_STEP = registerSound("stalker.step", "entity");

        CHOMPER_IDLE = registerSound("chomper.idle", "entity");
        CHOMPER_HURT = registerSound("chomper.hurt", "entity");
        CHOMPER_LEAP = registerSound("chomper.leap", "entity");
        CHOMPER_BITE = registerSound("chomper.bite", "entity");
        CHOMPER_WARN = registerSound("chomper.warn", "entity");
        CHOMPER_POP_OUT = registerSound("chomper.pop_out", "entity");

        AVALON_IDLE = registerSound("avalon.idle", "entity");
        AVALON_AGREE = registerSound("avalon.agree", "entity");
        AVALON_DISAGREE = registerSound("avalon.disagree", "entity");

        BIOME_AMBIENCE = registerSound("ambient.ambient", "biome");

    }


    private static SoundEvent registerSound(String name, String category) {
        String fullName = category + "." + name;
        ResourceLocation location = new ResourceLocation(ModReference.MOD_ID, fullName);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(fullName);
        ForgeRegistries.SOUND_EVENTS.register(event);

        return event;
    }
}
