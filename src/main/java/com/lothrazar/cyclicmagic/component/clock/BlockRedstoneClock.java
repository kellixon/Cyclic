package com.lothrazar.cyclicmagic.component.clock;
import java.util.Random;
import com.lothrazar.cyclicmagic.block.base.BlockBase;
import com.lothrazar.cyclicmagic.block.base.BlockBaseHasTile;
import com.lothrazar.cyclicmagic.util.UtilParticle;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRedstoneClock extends BlockBaseHasTile {
  public static final PropertyBool POWERED = net.minecraft.block.BlockLever.POWERED;//PropertyBool.create("powered");
  public BlockRedstoneClock() {
    super(Material.IRON);
   
  }
  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, POWERED);
  }
  @Override
  public IBlockState getStateFromMeta(int meta) {
//      if (meta <= 5)
          return this.getDefaultState().withProperty(POWERED,false);
//      else
//          return this.getDefaultState().withProperty(BlockButton.FACING, EnumFacing.VALUES[meta - 6]).withProperty(POWERED, Boolean.TRUE);
  }
  @Override
  public boolean canProvidePower(IBlockState state)
  {
      return true;
  }

  @Override
  public int getMetaFromState(IBlockState state) {
      return  (state.getValue(POWERED) ? 1 : 0);
  }

  @Override
  public TileEntity createTileEntity(World worldIn, IBlockState state) {
    return new TileEntityClock();
  }
  @SideOnly(Side.CLIENT)
  public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    if (stateIn.getValue(POWERED)) {
      UtilParticle.spawnParticle(worldIn, EnumParticleTypes.REDSTONE, pos);
    }
  }
  private int getPower(IBlockAccess world, BlockPos pos) {
    if (world.getTileEntity(pos) instanceof TileEntityClock)
      return ((TileEntityClock) world.getTileEntity(pos)).getPower();
    return 0;
  }
  @Override
  public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    return blockState.getValue(POWERED) ? getPower(blockAccess,pos) : 0;
  }
  @Override
  public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    return blockState.getValue(POWERED) ? getPower(blockAccess,pos) : 0;
  }
}
