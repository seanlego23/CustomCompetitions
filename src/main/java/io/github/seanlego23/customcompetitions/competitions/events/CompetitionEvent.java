package io.github.seanlego23.customcompetitions.competitions.events;

public enum CompetitionEvent {
    PLAYER_JOIN("onPlayerJoin"),
    PLAYER_LEAVE("onPlayerLeave"),
    PLAYER_KICK("onPlayerKick"),
    PLAYER_DEATH("onPlayerDeath"),
    PLAYER_RESPAWN("onPlayerRespawn"),

    //Per-world (or per world group) permissions is preferred for this. Bukkit requires a plugin to handle this.
    PLAYER_COMPLETE_ADVANCEMENT("onPlayerCompleteAdvancement"),

    PLAYER_ENTER_BED("onPlayerEnterBed"),
    PLAYER_LEAVE_BED("onPlayerLeaveBed"),
    PLAYER_FILL_BUCKET("onPlayerFillBucket"),
    PLAYER_EMPTY_BUCKET("onPlayerEmptyBucket"),
    PLAYER_CAPTURE_BUCKET("onPlayerCaptureMobWithBucket"),
    PLAYER_CHANGED_WORLD("onPlayerChangeWorld"),
    PLAYER_DROP_ITEM("onPlayerDropItem"),
    PLAYER_EDIT_BOOK("onPlayerEditBook"),
    PLAYER_EGG_THROW("onPlayerThrowEgg"),
    PLAYER_EXP_CHANGE("onPlayerExpChange"),
    PLAYER_FISH("onPlayerFish"),
    PLAYER_HARVEST("onPlayerHarvest"),
    PLAYER_ENTITY_INTERACT("onPlayerInteractWithEntity"),
    PLAYER_INTERACT("onPlayerInteract"),
    PLAYER_BREAK_ITEM("onPlayerBreakItem"),
    PLAYER_CONSUME("onPlayerConsume"),
    PLAYER_DAMAGE_ITEM("onPlayerDamageItem"),
    PLAYER_MEND_ITEM("onPlayerMendItem"),
    PLAYER_EXP_LEVEL_CHANGE("onPlayerExpLevelChange"),

    //Should have minimal actions applied to this
    PLAYER_MOVE("onPlayerMove"),

    PLAYER_PICKUP_PROJECTILE("onPlayerPickupProjectile"),
    PLAYER_PICKUP_ITEM("onPlayerPickupItem"),
    PLAYER_PORTAL("onPlayerPortal"),
    PLAYER_DISCOVER_RECIPE("onPlayerDiscoverRecipe"),
    PLAYER_RIPTIDE("onPlayerRiptide"),
    PLAYER_SHEAR("onPlayerShearEntity"),
    PLAYER_STATISTIC_INC("onPlayerStatisticIncrement"),
    PLAYER_TAKE_BOOK("onPlayerTakeBookFromLectern"),
    PLAYER_TELEPORT("onPlayerTeleport"),

    PLAYER_BREED("onPlayerBreed"),
    PLAYER_DAMAGE("onPlayerDamage"),
    PLAYER_PLACE_ENTITY("onPlayerPlaceEntity"),
    PLAYER_RESURRECT("onPlayerResurrect"),
    PLAYER_SHOOT_BOW("onPlayerShootBow"),
    PLAYER_TAME("onPlayerTameAnimal"),
    PLAYER_LEASH("onPlayerLeashAnimal"),
    PLAYER_UNLEASH("onPlayerUnleashAnimal"),
    PLAYER_ANGER_ZOMBIE_PIGLIN("onPlayerAngerZombifiedPiglin"),
    PLAYER_ANGER_PIGLIN("onPlayerAngerPiglin"),
    PLAYER_ANGER_PIGLIN_BRUTE("onPlayerAngerPiglinBrute"),
    PLAYER_DISTRACT_PIGLIN("onPlayerDistractPiglin"),
    PLAYER_ANGER_ENDERMAN("onPlayerAngerEnderman"),
    PLAYER_ANGER_IRON_GOLEM("onPlayerAngerIronGolem"),
    PLAYER_ANGER_LLAMA("onPlayerAngerLlama"),
    PLAYER_ANGER_WOLF("onPlayerAngerWolf"),
    PLAYER_DIE_WOOL("onPlayerDieSheep"),
    PLAYER_SPAWN_ENDERMITE("onPlayerSpawnEndermite"),
    PLAYER_START_RAID("onPlayerStartRaid"),
    PLAYER_WIN_RAID("onPlayerFinishRaid"),
    PLAYER_CLOSE_PUFFERFISH("onPlayer"),

    ENTITY_DAMAGE("onEntityDamage"),
    ENTITY_DEATH("onEntityDeath"),

    PROJECTILE_LAUNCH("onProjectileLaunch"),
    PROJECTILE_HIT("onProjectileHit"),
    ;

    private String name;

    //TODO: Add event class as parameter
    CompetitionEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
