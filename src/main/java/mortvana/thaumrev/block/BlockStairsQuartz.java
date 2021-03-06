package mortvana.thaumrev.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import mortvana.melteddashboard.block.IItemBlockProvider;
import mortvana.melteddashboard.util.libraries.ColorLibrary;

public class BlockStairsQuartz extends BlockStairs implements IItemBlockProvider {

	public int color = ColorLibrary.CLEAR;

	public BlockStairsQuartz(Block block, int meta, String name, CreativeTabs tab) {
		super(block, meta);
		setBlockName(name);
		setCreativeTab(tab);
		setLightOpacity(0);
	}

	public BlockStairsQuartz(Block block, int meta, String name, CreativeTabs tab, int color) {
		super(block, meta);
		setBlockName(name);
		setCreativeTab(tab);
		setLightOpacity(0);
		this.color = color;
	}

	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return getRenderColor(world.getBlockMetadata(x, y, z));
	}

	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return color;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}

	@Override
	public int getRarity(int metadata) {
		return 1;
	}
}
