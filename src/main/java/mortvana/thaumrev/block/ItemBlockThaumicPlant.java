package mortvana.thaumrev.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import mortvana.melteddashboard.block.FluxGearItemBlock;

import mortvana.thaumrev.block.BlockThaumicPlant;

public class ItemBlockThaumicPlant extends FluxGearItemBlock {

	public ItemBlockThaumicPlant(Block block) {
		super(block);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return field_150939_a.getIcon(2, meta);
	}

}
