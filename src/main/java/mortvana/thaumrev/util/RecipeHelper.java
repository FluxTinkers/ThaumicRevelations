package mortvana.thaumrev.util;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.*;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.*;
import thaumcraft.common.lib.utils.Utils;

import mortvana.melteddashboard.util.IStackProvider;
import mortvana.melteddashboard.util.helpers.ItemHelper;
import mortvana.melteddashboard.util.helpers.mod.ThaumcraftHelper;
import mortvana.melteddashboard.util.helpers.science.MathHelper;
import mortvana.melteddashboard.util.libraries.*;

import static mortvana.melteddashboard.util.libraries.ThermalLibrary.*;
import static mortvana.melteddashboard.util.helpers.ItemHelper.cloneStack;

public class RecipeHelper {

	public static void addRecipe(IRecipe recipe) {
		GameRegistry.addRecipe(recipe);
	}

	public static void addRecipe(IRecipe... recipes) {
		for (IRecipe recipe : recipes) {
			GameRegistry.addRecipe(recipe);
		}
	}

	public static ShapedRecipes generateDummyShapedRecipe(ItemStack output, Object... components) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;

		if (components[i] instanceof String[]) {
			String[] astring = (String[])components[i++];

			for (String s1 : astring) {
				++k;
				j = s1.length();
				s = s + s1;
			}
		} else {
			while (components[i] instanceof String) {
				String s2 = (String)components[i++];
				++k;
				j = s2.length();
				s = s + s2;
			}
		}

		HashMap hashmap;

		for (hashmap = new HashMap(); i < components.length; i += 2) {
			Character character = (Character)components[i];
			ItemStack itemstack1 = null;

			if (components[i + 1] instanceof Item) {
				itemstack1 = new ItemStack((Item)components[i + 1]);
			} else if (components[i + 1] instanceof Block) {
				itemstack1 = new ItemStack((Block)components[i + 1], 1, 32767);
			} else if (components[i + 1] instanceof ItemStack) {
				itemstack1 = (ItemStack)components[i + 1];
			}

			hashmap.put(character, itemstack1);
		}

		ItemStack[] aitemstack = new ItemStack[j * k];

		for (int i1 = 0; i1 < j * k; ++i1) {
			char c0 = s.charAt(i1);

			if (hashmap.containsKey(Character.valueOf(c0))) {
				aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
			} else {
				aitemstack[i1] = null;
			}
		}

		return new ShapedRecipes(j, k, aitemstack, output);
	}

	/** SHAPED CRAFTING * */
	public static ShapedOreRecipe addShapedRecipe(ItemStack result, Object... stuff) {
		ShapedOreRecipe r = new ShapedOreRecipe(result, stuff);
		addRecipe(r);
		return r;
	}

	public static ShapedOreRecipe addSquareRecipe(ItemStack result, Object input) {
		return addShapedRecipe(result, "##", "##", '#', input);
	}

	public static ShapedOreRecipe addStickRecipe(ItemStack result, Object input) {
		return addShapedRecipe(cloneStack(result, 4), "#", "#", '#', input);
	}

	public static ShapedOreRecipe addSlabRecipe(ItemStack result, Object input) {
		return addShapedRecipe(cloneStack(result, 6), "###", '#', input);
	}

	public static ShapedOreRecipe addStairRecipe(ItemStack result, Object input) {
		return addShapedRecipe(cloneStack(result, 4), "#  ", "## ", "###", '#', input);
	}

	public static ShapedOreRecipe addSurroundRecipe(ItemStack result, Object center, Object surround) {
		return addShapedRecipe(result, "###", "#x#", "###", '#', surround, 'x', center);
	}

	/** SHAPELESS CRAFTING * */
	public static ShapelessOreRecipe addShapelessRecipe(ItemStack result, Object... input) {
		ShapelessOreRecipe r = new ShapelessOreRecipe(result, input);
		GameRegistry.addRecipe(r);
		return r;
	}

	public static ShapelessOreRecipe addShapelessSizedOreRecipe(ItemStack result, int modifier, String... input) {
		return addShapelessRecipe(cloneStack(result, input.length + modifier), input);
	}

	public static ShapelessOreRecipe generateShapelessSizedOreRecipe(ItemStack result, int modifier, String... input) {
		return new ShapelessOreRecipe(cloneStack(result, input.length + modifier), input);
	}

	public static ShapelessOreRecipe generateShapelessRecipe(ItemStack result, int size, String... input) {
		return new ShapelessOreRecipe(ItemHelper.cloneStack(result, size), input);
	}

	public static ShapelessOreRecipe generateShapelessRecipe(ItemStack result, String... input) {
		return new ShapelessOreRecipe(result, input);
	}

	public static ShapelessOreRecipe addStorageRecipe(ItemStack result, Object input) {
		return addShapelessRecipe(result, input, input, input, input, input, input, input, input, input);
	}

	public static ShapelessOreRecipe addDeblockingRecipe(ItemStack result, Object input) {
		return addShapelessRecipe(cloneStack(result, 4), input);
	}

	public static ShapelessOreRecipe addReverseStorageRecipe(ItemStack result, Object input) {
		return addShapelessRecipe(cloneStack(result, 9), input);
	}

	public static ShapelessOreRecipe addDeslabingRecipe(ItemStack result, Object input) {
		return addShapelessRecipe(result, input, input);
	}

	public static ShapelessOreRecipe addDestairRecipe(ItemStack result, Object input) {
		return addShapelessRecipe(cloneStack(result, 6), input, input, input, input);
	}

	public static ShapelessOreRecipe addAlloyRecipe(ItemStack output, int quantity, String type, String... input) {
		String[] components = new String[input.length];
		for (int i = 0; i < input.length; i++) {
			components[i] = type + input[i];
		}
		return addShapelessRecipe(cloneStack(output, quantity), components);
	}

	/** SMELTING * */
	public static void addSmelting(ItemStack input, ItemStack result, float experience) {
		FurnaceRecipes.smelting().func_151394_a(input, result, experience);
	}

	public static void addSmelting(ItemStack input, ItemStack result) {
		addSmelting(input, result, 0);
	}

	/** ORE DICTIONARY * */
	public static void registerOreDict(ItemStack stack, String... oreDict) {
		for (String name : oreDict) {
			OreDictionary.registerOre(name, stack);
		}
	}

	/** FORGE MULTIPART * */
	public static void registerFMP(Block block, int maxMeta) {
		for (int i = 0; i < maxMeta; i++) {
			registerFMP(new ItemStack(block, 1, i));
		}
	}

	public static void registerFMP(ItemStack stack) {
		FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", stack);
	}

	/** THAUMCRAFT - SHAPED ARCANE CRAFTING * */
	public static ShapedArcaneRecipe addArcaneCraftingRecipe(String research, ItemStack result, AspectList aspects, Object... recipe) {
		return ThaumcraftApi.addArcaneCraftingRecipe(research, result, aspects, recipe);
	}

	/** THAUMCRAFT - SHAPELESS ARCANE CRAFTING * */
	public static ShapelessArcaneRecipe addShapelessArcaneCraftingRecipe(String research, ItemStack result, AspectList aspects, Object... recipe) {
		return ThaumcraftApi.addShapelessArcaneCraftingRecipe(research, result, aspects, recipe);
	}

	public static ShapelessArcaneRecipe addShapelessArcaneCraftingRecipe(String research, IStackProvider result, AspectList aspects, Object... recipe) {
		return ThaumcraftApi.addShapelessArcaneCraftingRecipe(research, result.getStack(), aspects, recipe);
	}

	/** THAUMCRAFT - INFUSION CRAFTING * */
	public static InfusionRecipe addInfusionCraftingRecipe(String research, Object result, int instability, AspectList aspects, ItemStack input, ItemStack... recipe) {
		return ThaumcraftApi.addInfusionCraftingRecipe(research, result, instability, aspects, input, recipe);
	}

	/** THAUMCRAFT - CRUCIBLE CRAFTING * */
	public static CrucibleRecipe addCrucibleRecipe(String key, ItemStack result, Object catalyst, AspectList tags) {
		return ThaumcraftApi.addCrucibleRecipe(key, result, catalyst, tags);
	}

	/** THAUMCRAFT - ASPECTS **/
	public static AspectList addAspects(ItemStack stack, AspectList list) {
		ThaumcraftApi.registerObjectTag(stack, list);
		return list;
	}
	/*public static AspectList addNewAspects(ItemStack stack, AspectList list) {
		ThaumcraftApi.


		ThaumcraftApi.registerObjectTag(stack, list);
		return list;
	}*/

	/*public static AspectList overwriteAspects(ItemStack stack,  AspectStack... aspects) {
		AspectList list = new AspectList(stack);
		for (AspectStack aspect : aspects) {
			list.add(aspect.getAspect(), aspect.getSize());
		}
		//This may be hacky, but given API limitations, it's the most elegant solution.
		if (ThaumcraftApi.objectTags.contains(Arrays.asList(stack.getItem(), stack.getItemDamage()))) {
			//ThaumcraftApi.objectTags.remove(Arrays.asList(stack.getItem(), stack.getItemDamage()));
			for (Aspect aspect : ThaumcraftApi.objectTags.get(Arrays.asList(stack.getItem(), stack.getItemDamage())).getAspects()) {
				ThaumcraftApi.objectTags.get(Arrays.asList(stack.getItem(), stack.getItemDamage())).remove(aspect);
			}
		}
		ThaumcraftApi.registerObjectTag(stack, list);
		return list;
	}*/

	/** THAUMCRAFT - NATIVE CLUSTERS * */
	public static void addCluster(String ore, ItemStack cluster) {
		addCluster(ore, cluster, 1.0F);
	}

	public static void addCluster(String ore, ItemStack cluster, float modifier) {
		for (ItemStack stack : OreDictionary.getOres(ore)) {
			Utils.addSpecialMiningResult(stack, cluster, modifier);
		}
	}

	public static void addCluster(ItemStack ore, ItemStack cluster) {
		addCluster(ore, cluster, 1.0F);
	}

	public static void addCluster(ItemStack ore, ItemStack cluster, float modifier) {
		Utils.addSpecialMiningResult(ore, cluster, modifier);
	}

	public static void addCluster(int oreID, int oreMeta, int clusterID, int clusterMeta, float modifier) {
		String val = oreID + "," + oreMeta + "," + clusterID + "," + clusterMeta + "," + modifier;
		FMLInterModComms.sendMessage("Thaumcraft", "nativeCluster", val);
	}

	public static void addSmeltingBonus(String in, ItemStack out) {
		ThaumcraftApi.addSmeltingBonus(in, out);
	}

	/** THAUMIC REVELATIONS - THAUMIC HAMMERMILL CRAFTING **/
	public static ShapelessOreRecipe addHammermillRecipe(ItemStack result, String input, int alumentum) { //Temporary
		List<String> items = new ArrayList<String>();
		items.add(input);
		//int cost = (int) Math.ceil((double) ALMNT / 1000D);
		for (int i = 0; i < alumentum; i++) {
			items.add("itemAlumentum");
		}
		return addShapelessRecipe(result, items);
	}

	/** THERMAL EXPANSION - FURNACE CRAFTING * */
	public static void addFurnaceRecipe(int flux, ItemStack input, ItemStack output) {
		if (input != null && output != null) {
			NBTTagCompound nbt = new NBTTagCompound();

			nbt.setInteger("energy", flux);
			nbt.setTag("input", new NBTTagCompound());
			nbt.setTag("output", new NBTTagCompound());

			input.writeToNBT(nbt.getCompoundTag("input"));
			output.writeToNBT(nbt.getCompoundTag("output"));
			FMLInterModComms.sendMessage("ThermalExpansion", "FurnaceRecipe", nbt);
		}
	}

	public static void removeFurnaceRecipe(ItemStack input) {
		if (input != null) {
			NBTTagCompound nbt = new NBTTagCompound();

			nbt.setTag("input", new NBTTagCompound());

			input.writeToNBT(nbt.getCompoundTag("input"));
			FMLInterModComms.sendMessage("ThermalExpansion", "RemoveFurnaceRecipe", nbt);
		}
	}

	/** THERMAL EXPANSION - PULVERIZER CRAFTING * */
	public static void addPulverizerRecipe(int flux, ItemStack input, ItemStack output) {
		addPulverizerRecipe(flux, input, output, null, 0);
	}

	public static void addPulverizerRecipe(int flux, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput) {
		addPulverizerRecipe(flux, input, primaryOutput, secondaryOutput, 100);
	}

	public static void addPulverizerRecipe(int flux, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
		if (input != null && primaryOutput != null) {
			NBTTagCompound nbt = new NBTTagCompound();

			nbt.setInteger("energy", flux);
			nbt.setTag("input", new NBTTagCompound());
			nbt.setTag("primaryOutput", new NBTTagCompound());

			if (secondaryOutput != null) {
				nbt.setTag("secondaryOutput", new NBTTagCompound());
			}

			input.writeToNBT(nbt.getCompoundTag("input"));
			primaryOutput.writeToNBT(nbt.getCompoundTag("primaryOutput"));

			if (secondaryOutput != null) {
				secondaryOutput.writeToNBT(nbt.getCompoundTag("secondaryOutput"));
				nbt.setInteger("secondaryChance", secondaryChance);
			}

			FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", nbt);
		}
	}

	public static void addPulverizerRecycleRecipe(ItemStack output, ItemStack input, int amount) {
		addPulverizerRecipe((amount * 300) + 600, input, cloneStack(output, amount));
	}

	public static void addPulverizerRecycleRecipes(ItemStack output, ItemStack[] inputs, int[] amounts) {
		if (output != null && inputs.length == amounts.length) {
			for (int i = 0; i < inputs.length; i++) {
				addPulverizerRecycleRecipe(output, inputs[i], amounts[i]);
			}
		}
	}

	public static void addPulverizerOreRecipe(ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput) {
		addPulverizerRecipe(4000, input, cloneStack(primaryOutput, ThermalLibrary.multPulvDefault), secondaryOutput, 10);
	}

	public static void addPulverizerPoorOreRecipe(ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput) {
		addPulverizerRecipe(3200, input, cloneStack(primaryOutput, ThermalLibrary.multPulvDefault * 3), secondaryOutput, 25);
	}

	public static void removePulverizerRecipe(ItemStack input) {
		if (input != null) {
			NBTTagCompound toSend = new NBTTagCompound();

			toSend.setTag("input", new NBTTagCompound());

			input.writeToNBT(toSend.getCompoundTag("input"));
			FMLInterModComms.sendMessage("ThermalExpansion", "RemovePulverizerRecipe", toSend);
		}
	}

	/** THERMAL EXPANSION - SAWMILL CRAFTING **/

	/** THERMAL EXPANSION - INDUCTION SMELTER CRAFTING * */
	public static void addInductionSmelterRecipe(int flux, ItemStack primaryInput, ItemStack secondaryInput, ItemStack output) {
		addInductionSmelterRecipe(flux, primaryInput, secondaryInput, output, null, 0);
	}

	public static void addInductionSmelterRecipe(int flux, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput, ItemStack secondaryOutput) {
		addInductionSmelterRecipe(flux, primaryInput, secondaryInput, primaryOutput, secondaryOutput, 100);
	}

	public static void addInductionSmelterRecipe(int flux, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
		if (primaryInput != null && secondaryInput != null && primaryOutput != null) {
			NBTTagCompound nbt = new NBTTagCompound();

			nbt.setInteger("energy", flux);
			nbt.setTag("primaryInput", new NBTTagCompound());
			nbt.setTag("secondaryInput", new NBTTagCompound());
			nbt.setTag("primaryOutput", new NBTTagCompound());

			if (secondaryOutput != null) {
				nbt.setTag("secondaryOutput", new NBTTagCompound());
			}

			primaryInput.writeToNBT(nbt.getCompoundTag("primaryInput"));
			secondaryInput.writeToNBT(nbt.getCompoundTag("secondaryInput"));
			primaryOutput.writeToNBT(nbt.getCompoundTag("primaryOutput"));

			if (secondaryOutput != null) {
				secondaryOutput.writeToNBT(nbt.getCompoundTag("secondaryOutput"));
				nbt.setInteger("secondaryChance", secondaryChance);
			}
			FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", nbt);
		}
	}

	public static void addInductionAlloyRecipe(String solventName, int solventAmount, String soluteName, int soluteAmount, ItemStack output) {
		//Dust
		ArrayList<ItemStack> primaryOreList = OreDictionary.getOres("dust" + solventName);
		ArrayList<ItemStack> secondaryOreList = OreDictionary.getOres("dust" + soluteName);

		if (primaryOreList.size() > 0 && secondaryOreList.size() > 0) {
			addInductionSmelterRecipe(1600, cloneStack(primaryOreList.get(0), solventAmount), cloneStack(secondaryOreList.get(0), soluteAmount), ItemHelper.cloneStack(output, solventAmount + soluteAmount));
		}

		primaryOreList = OreDictionary.getOres("ingot" + solventName);
		secondaryOreList = OreDictionary.getOres("ingot" + soluteName);

		if (primaryOreList.size() > 0 && secondaryOreList.size() > 0) {
			addInductionSmelterRecipe(2400, cloneStack(primaryOreList.get(0), solventAmount), cloneStack(secondaryOreList.get(0), soluteAmount), ItemHelper.cloneStack(output, solventAmount + soluteAmount));
		}
	}

	public static void addInductionOreRecipes(String oreName, String ingotName, ItemStack bonusIngot) {
		ItemStack ore = OreDictionary.getOres("ore" + oreName).get(0);
		ItemStack ingot  = OreDictionary.getOres("ingot" + ingotName).get(0);

		addInductionSmelterRecipe(3200, ore, new ItemStack(Blocks.sand), cloneStack(ingot, ThermalLibrary.multSmeltDefault), itemSlagRich, 5);
		addInductionSmelterRecipe(4000, ore, itemSlagRich, cloneStack(ingot, ThermalLibrary.multSmeltSpecial), itemSlag, 75);
		addInductionSmelterRecipe(4000, ore, dustPyrotheum, cloneStack(ingot, ThermalLibrary.multSmeltDefault), itemSlagRich, 15);
		addInductionSmelterRecipe(4000, ore, itemCinnabar, cloneStack(ingot, ThermalLibrary.multSmeltSpecial), bonusIngot == null ? itemSlagRich : bonusIngot, bonusIngot == null ? 75 : 100);
	}

	public static void addInductionOreRecipes(String name, ItemStack bonusIngot) {
		addInductionOreRecipes(name, name, bonusIngot);
	}

	public static void addInductionPoorOreRecipes(String oreName, String nuggetName, ItemStack bonusNugget) {
		ItemStack ore = OreDictionary.getOres("orePoor" + oreName).get(0);
		ItemStack nugget = OreDictionary.getOres("nugget" + nuggetName).get(0);

		addInductionSmelterRecipe(2400, ore, new ItemStack(Blocks.sand), cloneStack(nugget, multSmeltDefault * 3), itemSlagRich, 10);
		addInductionSmelterRecipe(3200, ore, itemSlagRich, cloneStack(nugget, multSmeltSpecial * 3), itemSlag);
		addInductionSmelterRecipe(3200, ore, dustPyrotheum, cloneStack(nugget, multSmeltDefault * 3), itemSlagRich, 20);
		addInductionSmelterRecipe(3200, ore, itemCinnabar, cloneStack(nugget, multSmeltSpecial * 3), bonusNugget == null ? itemSlagRich : bonusNugget, bonusNugget == null ? 85 : 100);
		//TODO: If it makes 9 nuggets, spit out an ingot
		//TODO: Always use TF Nuggets?
	}

	public static void addInductionPoorOreRecipes(String name, ItemStack bonusNugget) {
		addInductionPoorOreRecipes(name, name, bonusNugget);
	}

	public static void removeSmelterRecipe(ItemStack primaryInput, ItemStack secondaryInput) {
		if (primaryInput != null && secondaryInput != null) {
			NBTTagCompound toSend = new NBTTagCompound();

			toSend.setTag("primaryInput", new NBTTagCompound());
			toSend.setTag("secondaryInput", new NBTTagCompound());

			primaryInput.writeToNBT(toSend.getCompoundTag("primaryInput"));
			secondaryInput.writeToNBT(toSend.getCompoundTag("secondaryInput"));
			FMLInterModComms.sendMessage("ThermalExpansion", "RemoveSmelterRecipe", toSend);
		}
	}

	/**
	 * Use this to register an Ore TYPE as a "Blast" recipe - it will require Pyrotheum Dust to smelt. Do not add the prefix. This is an opt-in for ores which
	 * do NOT have vanilla furnace recipes.
	 *
	 * Ex: "Steel" or "ElectrumFlux", not "dustSteel" or "dustElectrumFlux"
	 *
	 * @param oreType
	 */
	public static void addSmelterBlastOre(String oreType) {
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setString("oreType", oreType);

		FMLInterModComms.sendMessage("ThermalExpansion", "SmelterBlastOreType", nbt);
	}

	/** THERMAL EXPANSION - MAGMA CRUCIBLE CRAFTING **/

	/** THERMAL EXPANSION - FLUID TRANSPOSER CRAFTING **/

	/** THERMAL EXPANSION - INSOLATOR CRAFTING **/

	/** THERMAL EXPANSION - MAGMATIC FUEL * */
	public static void addMagmaticFuel(String fluidName, int energy) {
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setString("fluidName", fluidName);
		nbt.setInteger("energy", energy);

		FMLInterModComms.sendMessage("ThermalExpansion", "MagmaticFuel", nbt);
	}

	/** THERMAL EXPANSION - COMPRESSION FUEL * */
	public static void addCompressionFuel(String fluidName, int energy) {
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setString("fluidName", fluidName);
		nbt.setInteger("energy", energy);

		FMLInterModComms.sendMessage("ThermalExpansion", "CompressionFuel", nbt);
	}

	/** THERMAL EXPANSION - REACTANT FUEL * */
	public static void addReactantFuel(String fluidName, int energy) {
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setString("fluidName", fluidName);
		nbt.setInteger("energy", energy);

		FMLInterModComms.sendMessage("ThermalExpansion", "ReactantFuel", nbt);
	}

	/** THERMAL EXPANSION - DYNAMO COOLANT * */
	public static void addCoolant(String fluidName, int energy) {
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setString("fluidName", fluidName);
		nbt.setInteger("energy", energy);

		FMLInterModComms.sendMessage("ThermalExpansion", "Coolant", nbt);
	}

	/** OTHERS/MULTIPLE * */
	public static void registerWithHandlers(ItemStack itemstack, String name, String... oreDict) {
		GameRegistry.registerCustomItemStack(name, itemstack);
		registerFMP(itemstack);
		registerOreDict(itemstack, oreDict);
	}

	public static void addRefractoryOreSmelting(ItemStack input, ItemStack output) {
		addRefractorySmelting(input, output, 1.0F);
	}

	public static void addRefractoryOreSmelting(ItemStack input, ItemStack output, float mult, ItemStack bonusIngot) {
		if (Loader.isModLoaded("ThermalExpansion")) {
			addInductionSmelterRecipe((int) (8000 * mult), input, dustPyrotheum, output, itemSlagRich, 15);
		}
	}

	public static void addRefractorySmelting(ItemStack input, ItemStack output) {
		addRefractorySmelting(input, output, 1.0F);
	}

	public static void addRefractorySmelting(ItemStack input, ItemStack output, float mult) {
		if (Loader.isModLoaded("ThermalExpansion")) {
			addInductionSmelterRecipe((int) (8000 * mult), input, dustPyrotheum, output);
			//TODO: TRv, IE, RC, RoC (Blast), RoC (Pulse Jet), IC2
		}
	}

	//public static void addGrindingRecipes(ItemStack input, ItemStack output) {
	//	addGrindingRecipes(input, output, true, 6);
	//}

	public static void addGrindingRecipes(ItemStack input, ItemStack output, boolean full) {
		addGrindingRecipes(input, output, full, 6);
	}

	public static void addGrindingRecipes(ItemStack input, ItemStack output, boolean full, int powerFactor) {
		if (Loader.isModLoaded("ThermalExpansion") && full) {
			addPulverizerRecipe(powerFactor * 400, input, output);
			//TODO: EIO, IE, RC, RoC, IC2
		}
	}
}
