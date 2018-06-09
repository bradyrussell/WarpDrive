package cr0s.warpdrive.block.decoration;

import cr0s.warpdrive.block.ItemBlockAbstractBase;
import cr0s.warpdrive.data.EnumDecorativeType;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ItemBlockDecorative extends ItemBlockAbstractBase {
	
	public ItemBlockDecorative(final Block block) {
		super(block);
		setHasSubtypes(true);
	}
	
	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModelResourceLocation(final ItemStack itemStack) {
		final int damage = itemStack.getItemDamage();
		if (damage < 0 || damage > EnumDecorativeType.length) {
			throw new IllegalArgumentException(String.format("Invalid damage %d for %s", damage, itemStack.getItem()));
		}
		ResourceLocation resourceLocation = getRegistryName();
		final String variant = String.format("type=%s", EnumDecorativeType.get( itemStack.getItemDamage() ).getName());
		return new ModelResourceLocation(resourceLocation, variant);
	}
	
	@Nonnull
	@Override
	public String getUnlocalizedName(final ItemStack itemStack) {
		if (itemStack == null) {
			return getUnlocalizedName();
		}
		return getUnlocalizedName() + EnumDecorativeType.get(itemStack.getItemDamage()).getName();
	}
}
