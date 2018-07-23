package cr0s.warpdrive.block.hull;

import cr0s.warpdrive.block.ItemBlockAbstractBase;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockHull extends ItemBlockAbstractBase {
	
	ItemBlockHull(final Block block) {
		super(block);
		
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public ModelResourceLocation getModelResourceLocation(final ItemStack itemStack) {
		if (block instanceof BlockHullStairs) {
			final ResourceLocation resourceLocation = getRegistryName();
			assert resourceLocation != null;
			final String variant = "facing=east,half=bottom,shape=straight";
			return new ModelResourceLocation(resourceLocation, variant);
		}
		return super.getModelResourceLocation(itemStack);
	}
	
	@Nonnull
	@Override
	public String getUnlocalizedName(final ItemStack itemStack) {
		if (itemStack == null || block instanceof BlockHullStairs) {
			return getUnlocalizedName();
		}
		return getUnlocalizedName() + EnumDyeColor.byMetadata( itemStack.getItemDamage() ).getUnlocalizedName();
	}
}
