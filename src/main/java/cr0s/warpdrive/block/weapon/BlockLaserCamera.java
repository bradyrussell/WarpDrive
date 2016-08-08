package cr0s.warpdrive.block.weapon;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import cr0s.warpdrive.WarpDrive;
import cr0s.warpdrive.block.BlockAbstractContainer;
import cr0s.warpdrive.render.ClientCameraHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockLaserCamera extends BlockAbstractContainer {
	
	public BlockLaserCamera(final String registryName) {
		super(Material.IRON);
		setHardness(50.0F);
		setResistance(20.0F * 5 / 3);
		setUnlocalizedName("warpdrive.weapon.LaserCamera");
		setRegistryName(registryName);
		GameRegistry.register(this);
		GameRegistry.registerTileEntity(TileEntityLaserCamera.class, WarpDrive.PREFIX + registryName);
	}
	
	@Nonnull
	@Override
	public TileEntity createNewTileEntity(@Nonnull World world, int metadata) {
		return new TileEntityLaserCamera();
	}
	
	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer entityPlayer, EnumHand hand, @Nullable ItemStack itemStackHeld, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (itemStackHeld == null) {
			TileEntity tileEntity = world.getTileEntity(blockPos);
			if (!ClientCameraHandler.isOverlayEnabled) {
				if (tileEntity instanceof TileEntityLaserCamera) {
					WarpDrive.addChatMessage(entityPlayer, ((TileEntityLaserCamera) tileEntity).getStatus());
				} else {
					WarpDrive.addChatMessage(entityPlayer, new TextComponentTranslation("warpdrive.guide.prefix",
							getLocalizedName()).appendSibling(new TextComponentTranslation("warpdrive.error.badTileEntity")));
					WarpDrive.logger.error("Block " + this + " with invalid tile entity " + tileEntity);
				}
				return false;
			}
		}
		
		return false;
	}
}