package bilibili.alyling.bilibili.alyling.jumpboat.Bilibili.alyLing.Mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = BoatEntity.class)
public abstract class MixinBoatEntity extends Entity implements JumpingMount {
    @Shadow
    private BoatEntity.Location location;

    private boolean hasJumpedfromwater;

    protected MixinBoatEntity(EntityType<?> type, World level){
        super(type,level);
    }

    @Override
    public void setJumpStrength(int strength) {
        if (location == BoatEntity.Location.IN_WATER){
            setVelocity(getVelocity().add(0,.3,0));
            hasJumpedfromwater = true;
        } else if (location == BoatEntity.Location.ON_LAND){
            setVelocity(getVelocity().add(0,.25,0));
        }
    }

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public void startJumping(int height) {
        if (hasJumpedfromwater){
            playSound(SoundEvents.ENTITY_PLAYER_SPLASH,0.4F,1.0F);
            hasJumpedfromwater = false;
        }
    }

    @Override
    public void stopJumping() {

    }
}
