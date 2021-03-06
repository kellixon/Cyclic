package com.lothrazar.cyclicmagic.entity.projectile;
import com.lothrazar.cyclicmagic.registry.SoundRegistry;
import com.lothrazar.cyclicmagic.util.UtilItemStack;
import com.lothrazar.cyclicmagic.util.UtilSound;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityMagicNetFull extends EntityThrowableDispensable {
  public static class FactoryBall implements IRenderFactory<EntityMagicNetFull> {
    @Override
    public Render<? super EntityMagicNetFull> createRenderFor(RenderManager rm) {
      return new RenderBall<EntityMagicNetFull>(rm, "net");
    }
  }
  private ItemStack captured;
  public EntityMagicNetFull(World worldIn) {
    super(worldIn);
  }
  public EntityMagicNetFull(World worldIn, EntityLivingBase ent, ItemStack c) {
    super(worldIn, ent);
    this.captured = c;
  }
  public EntityMagicNetFull(World worldIn, double x, double y, double z, ItemStack c) {
    super(worldIn, x, y, z);
    this.captured = c;
  }
  @Override
  protected void processImpact(RayTraceResult mop) {
    if (captured == null || captured.hasTagCompound() == false) {
      //client desync maybe
      return;
    }
    Entity spawnEntity = EntityList.createEntityFromNBT(captured.getTagCompound(), this.getEntityWorld());
    if (spawnEntity != null) {
      spawnEntity.readFromNBT(captured.getTagCompound());
      spawnEntity.setLocationAndAngles(this.posX, this.posY + 1.1F, this.posZ, this.rotationYaw, 0.0F);
      this.getEntityWorld().spawnEntity(spawnEntity);
      if (spawnEntity instanceof EntityLivingBase) {
        UtilSound.playSound((EntityLivingBase) spawnEntity, SoundRegistry.byeaa);
        UtilItemStack.dropItemStackInWorld(this.getEntityWorld(), this.getPosition(), new ItemStack(captured.getItem()));
      }
    }
    this.setDead();
  }
}