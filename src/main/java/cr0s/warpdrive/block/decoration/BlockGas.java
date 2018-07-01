package cr0s.warpdrive.block.decoration;

import cr0s.warpdrive.block.BlockAbstractBase;
import cr0s.warpdrive.data.CelestialObjectManager;
import cr0s.warpdrive.data.EnumGasColor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Random;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGas extends BlockAbstractBase {
	
	public static final PropertyEnum<EnumGasColor> COLOR = PropertyEnum.create("color", EnumGasColor.class);
	
	public BlockGas(final String registryName) {
		super(registryName, Material.FIRE);
		setHardness(0.0F);
		setUnlocalizedName("warpdrive.decoration.gas");
		
		setDefaultState(getDefaultState().withProperty(COLOR, EnumGasColor.RED));
	}
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, COLOR);
	}
	
	@SuppressWarnings("deprecation")
	@Nonnull
	@Override
	public IBlockState getStateFromMeta(final int metadata) {
		return getDefaultState()
				.withProperty(COLOR, EnumGasColor.get(metadata));
	}
	
	@Override
	public int getMetaFromState(final IBlockState blockState) {
		return blockState.getValue(COLOR).ordinal();
	}
	
	@Nullable
	@Override
	public ItemBlock createItemBlock() {
		return new ItemBlockGas(this);
	}
	
	@Override
	public void getSubBlocks(final CreativeTabs creativeTab, final NonNullList<ItemStack> list) {
		for (final EnumGasColor enumGasColor : EnumGasColor.values()) {
			list.add(new ItemStack(this, 1, enumGasColor.ordinal()));
		}
	}
	
	@Override
	public int damageDropped(final IBlockState blockState) {
		return blockState.getBlock().getMetaFromState(blockState);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean causesSuffocation(final IBlockState state) {
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isOpaqueCube(final IBlockState state) {
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullBlock(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean isAir(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(final IBlockState blockState, @Nonnull final IBlockAccess blockAccess, @Nonnull final BlockPos blockPos) {
		return null;
	}
	
	@Override
	public boolean isReplaceable(final IBlockAccess worldIn, @Nonnull final BlockPos blockPos) {
		return true;
	}
	
	@Override
	public boolean canPlaceBlockAt(final World worldIn, @Nonnull final BlockPos blockPos) {
		return true;
	}
	
	@Override
	public boolean canCollideCheck(final IBlockState state, final boolean hitIfLiquid) {
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@Nonnull
	@Override
	public EnumPushReaction getMobilityFlag(final IBlockState state) {
		return EnumPushReaction.DESTROY;
	}
	
	@Nonnull
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Items.AIR;
	}
	
	@Override
	public int quantityDropped(final Random random) {
		return 0;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isTranslucent(final IBlockState state) {
		return true;
	}
	
	@Nonnull
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(final IBlockState blockState, @Nonnull final IBlockAccess blockAccess, @Nonnull final BlockPos blockPos, final EnumFacing facing) {
		final BlockPos blockPosSide = blockPos.offset(facing);
		final EnumFacing opposite = facing.getOpposite();
		final IBlockState blockStateSide = blockAccess.getBlockState(blockPosSide);
		if (blockStateSide.getBlock() instanceof BlockGas) {
			return blockState.getValue(COLOR) != blockStateSide.getValue(COLOR);
		}
		return !blockAccess.isSideSolid(blockPosSide, opposite, false);
	}
	
	@Override
	public boolean isCollidable() {
		return false;
	}
	
	@Override
	public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
		// Gas blocks are only allowed in space
		if (CelestialObjectManager.hasAtmosphere(world, blockPos.getX(), blockPos.getZ())) {
			world.setBlockToAir(blockPos);
		}
	}
	
	@Override
	public EnumRarity getRarity(final ItemStack itemStack, final EnumRarity rarity) {
		return EnumRarity.COMMON;
	}
}