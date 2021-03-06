package mortvana.thaumrev.common;

import java.util.*;

import mortvana.melteddashboard.item.data.*;

import mortvana.thaumrev.api.util.enums.EnumEquipmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchPage;

//import magicbees.api.MagicBeesAPI;

import mortvana.melteddashboard.block.BlockStorageBase;
import mortvana.melteddashboard.block.FluxGearItemBlock;
import mortvana.melteddashboard.intermod.thaumcraft.research.DummyResearchItem;
import mortvana.melteddashboard.intermod.thaumcraft.research.FluxGearResearchItem;
import mortvana.melteddashboard.inventory.FluxGearCreativeTab;
import mortvana.melteddashboard.util.helpers.*;
import mortvana.melteddashboard.util.helpers.mod.ThaumcraftHelper;

import mortvana.thaumrev.block.*;
import mortvana.thaumrev.item.*;
import mortvana.thaumrev.item.data.ArmorBehaviorPrimalRobes;
import mortvana.thaumrev.util.*;

import mortvana.thaumrev.item.ItemArmorInfusable;
import mortvana.thaumrev.world.ThaumRevWorldGenerator;

import static mortvana.thaumrev.library.ThaumRevLibrary.oreGravelSphalerite;
import static thaumcraft.api.aspects.Aspect.*;
import static mortvana.melteddashboard.util.helpers.ItemHelper.cloneStack;
import static mortvana.melteddashboard.util.libraries.ColorLibrary.*;
import static mortvana.melteddashboard.util.libraries.ThaumcraftLibrary.*;
import static mortvana.melteddashboard.util.libraries.ThermalLibrary.*;
import static mortvana.melteddashboard.util.libraries.StringLibrary.*;
import static mortvana.thaumrev.common.ThaumRevConfig.*;
import static mortvana.thaumrev.library.ThaumRevLibrary.*;
import static mortvana.thaumrev.util.RecipeHelper.*;

public class ThaumRevContent {

	public static void preInit() {
		generalTab = new FluxGearCreativeTab("Thaumic Revelations", "thaumicRevelations");
		createBlocks();
		createItems();
		registerBlocks();
		registerItems();
		loadMaterials();
		loadArmor();
		loadTools();
		loadAspects();
	}

	public static void init() {
		loadBlocks();
		loadMetalBlocks();
		loadItems();
		loadMetalItems();
		loadOtherItems();
		loadBaubles();
		addTooltips();

		((FluxGearCreativeTab) generalTab).setItem(amuletWarden);
		aluminiumArc();

		loadRecipes();
		loadOreRecipes();
		loadElementalMetalRecipes();
		loadSimpleAlloyMetalRecipes();
		loadSpecialAlloyMetalRecipes();
		loadEquipmentAlloyMetalRecipes();
		loadGemRecipes();
		loadMiscMetalRecipes();
		loadAlloyingRecipes();
		loadMetalIntegrationRecipes();
		loadDustRecipes();
		loadThaumicRecipes();
		loadRunicRecipes();
		loadWardenicRecipes();
		loadMagneoturgicRecipes();
		loadTransmuationRecipes();
		loadClusterRecipes();
		loadClusters();
		addLoot();
		ThaumRevWorldGenerator.registerPoorOres();
	}

	public static void postInit() {
		ResearchCategories.registerCategory(RESEARCH_KEY_MAIN, new ResourceLocation(RESOURCE_PREFIX, "textures/items/baubles/amuletWarden.png"), new ResourceLocation(RESOURCE_PREFIX, "textures/gui/gui_researchbackthaumrev.png"));
		ResearchCategories.registerCategory(RESEARCH_KEY_METAL, new ResourceLocation(RESOURCE_PREFIX, "textures/items/research/clusterIcon.png"), new ResourceLocation(RESOURCE_PREFIX, "textures/gui/gui_researchbackthaumrev.png"));
		postInitRecipes();

		setResearchLevel();
		//determineTempus();
		//forbiddenAspects();

		loadResearch();
		initResearch();
		registerResearch();
		setPages();
	}

	public static void loadComplete() {
		loadGeneralAspects();
		loadMetalAspects();
		loadEquipmentAspects();
	}

	public static void createBlocks() {
		blockThaumicPlant = new BlockThaumicPlant();
		blockOre = new BlockOre();
		blockPoorOre = new BlockPoorOre();
		blockGravelOre = new BlockGravelOre();

		blockWoodDecor = new BlockWoodDecor();
		blockStoneDecor = new BlockStoneDecor();

		blockStorageOre = new BlockStorageBase("thaumrev.storageOre1", generalTab, DIR_STORAGE, UNLOC_STORAGE, RARITY_STORAGE_ORE, NAMES_STORAGE_ORE, MINE_LVL_STORAGE_ORE, HARDNESS_STORAGE_ORE, RESISTANCE_STORAGE_ORE, LIGHT_STORAGE_ORE, COLOR_STORAGE_ORE);
		blockStorageAlloy1 = new BlockStorageBase("thaumrev.storageAlloy1", generalTab, DIR_STORAGE, UNLOC_STORAGE, RARITY_STORAGE_ALLOY_1, NAMES_STORAGE_ALLOY_1, MINE_LVL_STORAGE_ALLOY_1, HARDNESS_STORAGE_ALLOY_1, RESISTANCE_STORAGE_ALLOY_1, LIGHT_STORAGE_ALLOY_1, COLOR_STORAGE_ALLOY_1).setSignal(14, 8);
		blockStorageSpecial = new BlockStorageBase("thaumrev.storageSpecial", generalTab, DIR_STORAGE, UNLOC_STORAGE, RARITY_STORAGE_SPECIAL, NAMES_STORAGE_SPECIAL, MINE_LVL_STORAGE_SPECIAL, HARDNESS_STORAGE_SPECIAL, RESISTANCE_STORAGE_SPECIAL, LIGHT_STORAGE_SPECIAL, COLOR_STORAGE_SPECIAL);
		blockStorageEquipment = new BlockStorageBase("thaumrev.storageEquipment", generalTab, DIR_STORAGE, UNLOC_STORAGE, RARITY_STORAGE_EQUIPMENT, NAMES_STORAGE_EQUIPMENT, MINE_LVL_STORAGE_EQUIPMENT, HARDNESS_STORAGE_EQUIPMENT, RESISTANCE_STORAGE_EQUIPMENT, LIGHT_STORAGE_EQUIPMENT, COLOR_STORAGE_EQUIPMENT, SIGNAL_STORAGE_EQUIPMENT);
		blockStorageGem = new BlockStorageGem("thaumrev.storageGem", generalTab, DIR_STORAGE, UNLOC_STORAGE, RARITY_STORAGE_GEM, NAMES_STORAGE_GEM_NAME, MINE_LVL_STORAGE_GEM, HARDNESS_STORAGE_GEM, RESISTANCE_STORAGE_GEM, LIGHT_STORAGE_GEM, COLOR_STORAGE_GEM, LINK_STORAGE_GEM_IND, LINK_STORAGE_GEM_META);
		blockStorageMisc = new BlockStorageLinked("thaumrev.storageMisc", generalTab, DIR_STORAGE, UNLOC_STORAGE, RARITY_STORAGE_MISC, NAMES_STORAGE_MISC_INT, MINE_LVL_STORAGE_MISC, HARDNESS_STORAGE_MISC, RESISTANCE_STORAGE_MISC, LIGHT_STORAGE_MISC, COLOR_STORAGE_MISC, LINK_STORAGE_MISC_IND, LINK_STORAGE_MISC_META).setCustomNames(NAMES_STORAGE_MISC_LOC);

		//blockStoneSlab = new BlockStoneSlab();
		//blockStoneSlabDouble = new BlockStoneSlab(blockStoneSlab);
		//blockWardenicQuartzStairs = new BlockStairsQuartz(blockStoneDecor, 2, "thaumrev.blockWardenicQuartzStair", generalTab, COLOR_WQRZ);
		blockMundaneCrop = new BlockMundaneCrop();
	}

	public static void createItems() {
		generalItem = new ItemThaumRev();
		thaumicBauble = new ItemThaumicBauble();

		focusPurity = new ItemFocusPurity();
	}

	public static void registerBlocks() {
		GameRegistry.registerBlock(blockThaumicPlant, ItemBlockThaumicPlant.class, "blockThaumicPlant");
		GameRegistry.registerBlock(blockOre, FluxGearItemBlock.class, "blockOre");
		GameRegistry.registerBlock(blockPoorOre, FluxGearItemBlock.class, "blockPoorOre");
		GameRegistry.registerBlock(blockGravelOre, FluxGearItemBlock.class, "blockGravelOre");

		GameRegistry.registerBlock(blockWoodDecor, FluxGearItemBlock.class, "blockWoodDecor");
		GameRegistry.registerBlock(blockStoneDecor, FluxGearItemBlock.class, "blockStoneDecor");

		GameRegistry.registerBlock(blockStorageOre, FluxGearItemBlock.class, "blockStorageOre1");
		GameRegistry.registerBlock(blockStorageAlloy1, FluxGearItemBlock.class, "blockStorageAlloy1");
		GameRegistry.registerBlock(blockStorageSpecial, FluxGearItemBlock.class, "blockStorageSpecial");
		GameRegistry.registerBlock(blockStorageEquipment, FluxGearItemBlock.class, "blockStorageEquipment");
		GameRegistry.registerBlock(blockStorageGem, FluxGearItemBlock.class, "blockStorageGem");
		GameRegistry.registerBlock(blockStorageMisc, FluxGearItemBlock.class, "blockStorageMisc");

		//GameRegistry.registerBlock(blockStoneSlab, FluxGearItemBlock.class, "stoneSlab");
		//GameRegistry.registerBlock(blockStoneSlabDouble, FluxGearItemBlock.class, "stoneSlabDouble");
		//GameRegistry.registerBlock(blockWardenicQuartzStairs, FluxGearItemBlock.class, "wardenicQuartzStairs");
		GameRegistry.registerBlock(blockMundaneCrop, "cropMundane");
	}

	public static void registerItems() {
		GameRegistry.registerItem(focusPurity, "focusPurity");
	}

	public static void loadMaterials() {
		matPrimal = EnumHelper.addArmorMaterial("PRIMAL", 25, new int[] {1, 3, 2, 1}, 25);
		matBronzeChain = EnumHelper.addArmorMaterial("BRONZE_CHAIN", 20, new int[] {2, 5, 4, 2}, 25);

		matWardencloth = EnumHelper.addArmorMaterial("WARDENCLOTH", 40, new int[] {1, 3, 2, 1}, 30);
		matWardenicChain = EnumHelper.addArmorMaterial("WARDENIC_CHAIN", 25, new int[] {2, 5, 4, 1}, 25);
		matWardenicSteel = EnumHelper.addArmorMaterial("WARDENIC_STEEL", 150, new int[] {3, 7, 5, 2}, 20);
		matWardenicComposite = EnumHelper.addArmorMaterial("WARDENIC_COMPOSITE", 0, new int[] {4, 8, 6, 3}, 25);
		matWardenicAwakened = EnumHelper.addArmorMaterial("WARDENIC_AWAKENED", 0, new int[] {5, 9, 7, 4}, 30);

		matFluxRobes = EnumHelper.addArmorMaterial("FLUX_ROBES", 0, new int[] {1, 3, 2, 1}, 25);

		dataEnchCottonGoggles = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.HELMET, "mithril", "goggles", NULL, EnumRarity.uncommon).setDiscount(5).setRepair(INGOT + MTHR).setGoggles(true);
		dataEnchCottonRobe = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.CHESTPLATE, "enchCotton", "robe", NULL, EnumRarity.uncommon).setDiscount(2).setRepair(M0004);
		dataEnchCottonPants = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.PANTS, "enchCotton", "pants", NULL, EnumRarity.uncommon).setDiscount(2).setRepair(M0004);
		dataEnchCottonBoots = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.BOOTS, "enchCotton", "boots", NULL, EnumRarity.uncommon).setDiscount(2).setRepair(M0004);

		dataBronzeChainCoif = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.HELMET, "bronzeChain", "coif", NULL, EnumRarity.uncommon).setRepair(CHAIN_ORE + TBRZ);
		dataBronzeChainmail = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.CHESTPLATE, "bronzeChain", "mail", NULL, EnumRarity.uncommon).setRepair(CHAIN_ORE + TBRZ);
		dataBronzeChainGreaves = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.PANTS, "bronzeChain", "greaves", NULL, EnumRarity.uncommon).setRepair(CHAIN_ORE + TBRZ);
		dataBronzeChainBoots = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.BOOTS, "bronzeChain", "boots", NULL, EnumRarity.uncommon).setRepair(CHAIN_ORE + TBRZ);

		dataPrimalGoggles = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.HELMET, "primal", "goggles", NULL, COLOR_THAUM, EnumRarity.uncommon).setDiscount(5).setRepair("itemEnchantedFabricCotton").setBehavior(ArmorBehaviorPrimalRobes.instance).setGoggles(true);
		dataPrimalRobe = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.CHESTPLATE, "primal", "robe", NULL, COLOR_THAUM, EnumRarity.uncommon).setDiscount(2).setRepair("itemEnchantedFabricCotton").setBehavior(ArmorBehaviorPrimalRobes.instance);
		dataPrimalPants = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.PANTS, "primal", "pants", NULL, COLOR_THAUM, EnumRarity.uncommon).setDiscount(2).setRepair("itemEnchantedFabricCotton").setBehavior(ArmorBehaviorPrimalRobes.instance);
		dataPrimalBoots = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.BOOTS, "primal", "boots", NULL, COLOR_THAUM, EnumRarity.uncommon).setDiscount(1).setRepair("itemEnchantedFabricCotton").setBehavior(ArmorBehaviorPrimalRobes.instance);

		dataWardenclothSkullcap = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.HELMET, "wardencloth", "skullcap", ITEM, COLOR_TEAL, EnumRarity.uncommon, 1);
		dataWardenclothTunic = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.CHESTPLATE, "wardencloth", "tunic", ITEM, COLOR_TEAL, EnumRarity.uncommon, 2);
		dataWardenclothPants = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.PANTS, "wardencloth", "pants", ITEM, COLOR_TEAL, EnumRarity.uncommon, 1);
		dataWardenclothBoots = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.BOOTS, "wardencloth", "boots", ITEM, COLOR_TEAL, EnumRarity.uncommon, 1);

		dataWardenicChainCoif = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.HELMET, "wardenicChain", "coif", NULL, EnumRarity.uncommon).setRepair(CHAIN_ORE + WBRZ);
		dataWardenicChainmail = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.CHESTPLATE, "wardenicChain", "mail", NULL, EnumRarity.uncommon).setRepair(CHAIN_ORE + WBRZ);
		dataWardenicChainGreaves = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.PANTS, "wardenicChain", "greaves", NULL, EnumRarity.uncommon).setRepair(CHAIN_ORE + WBRZ);
		dataWardenicChainBoots = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.BOOTS, "wardenicChain", "boots", NULL, EnumRarity.uncommon).setRepair(CHAIN_ORE + WBRZ);

		dataWardenicSteelHelmet = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.HELMET, "wardenicSteel", "helmet", PLATE, EnumRarity.uncommon);
		dataWardenicSteelChestplate = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.CHESTPLATE, "wardenicSteel", "chestplate", PLATE, EnumRarity.uncommon);
		dataWardenicSteelGreaves = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.PANTS, "wardenicSteel", "greaves", PLATE, EnumRarity.uncommon);
		dataWardenicSteelBoots = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.BOOTS, "wardenicSteel", "boots", PLATE, EnumRarity.uncommon);

		dataWardenicCompositeHelmet = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.HELMET, "wardenicComposite", "helmet", PLATE, EnumRarity.rare).setUnbreakable(true);
		dataWardenicCompositeChestplate = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.CHESTPLATE, "wardenicComposite", "chestplate", PLATE, EnumRarity.rare).setUnbreakable(true);
		dataWardenicCompositeGreaves = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.PANTS, "wardenicComposite", "greaves", PLATE, EnumRarity.rare).setUnbreakable(true);
		dataWardenicCompositeBoots = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.BOOTS, "wardenicComposite", "boots", PLATE, EnumRarity.rare).setUnbreakable(true);

		dataWardenicAwakenedHelmet = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.HELMET, "wardenicAwakened", "helmet", NULL, EnumRarity.epic).setUnbreakable(true);
		dataWardenicAwakenedChestplate = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.CHESTPLATE, "wardenicAwakened", "chestplate", NULL, EnumRarity.epic).setUnbreakable(true);
		dataWardenicAwakenedGreaves = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.PANTS, "wardenicAwakened", "greaves", NULL, EnumRarity.epic).setUnbreakable(true);
		dataWardenicAwakenedBoots = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.BOOTS, "wardenicAwakened", "boots", NULL, EnumRarity.epic).setUnbreakable(true);

		if (ThaumRevConfig.getFluxed) {
			dataFluxRobeGoggles = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.HELMET, "fluxRobes", "goggles", NULL, COLOR_FLUX, EnumRarity.uncommon).setFluxData(40000, 200, 0.75, false, 250).setDiscount(5).setBehavior(ArmorBehaviorFlux.instance).setGoggles(true);
			dataFluxRobeRobe = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.CHESTPLATE, "fluxRobes", "vest", NULL, COLOR_FLUX, EnumRarity.uncommon).setFluxData(40000, 200, 0.75, false, 250).setDiscount(2).setBehavior(ArmorBehaviorFlux.instance);
			dataFluxRobePants = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.PANTS, "fluxRobes", "pants", NULL, COLOR_FLUX, EnumRarity.uncommon).setFluxData(40000, 200, 0.75, false, 250).setDiscount(2).setBehavior(ArmorBehaviorFlux.instance);
			dataFluxRobeBoots = new ArmorDataAdv(RESOURCE_PREFIX, EnumEquipmentType.BOOTS, "fluxRobes", "boots", NULL, COLOR_FLUX, EnumRarity.uncommon).setFluxData(40000, 200, 0.75, false, 250).setDiscount(1).setBehavior(ArmorBehaviorFlux.instance);
		}
	}

	public static void loadArmor() {
		enchCottonGoggles = new ItemArmorInfusable(ThaumcraftApi.armorMatSpecial, 4, 0, dataEnchCottonGoggles);
		enchCottonRobe = new ItemArmorInfusable(ThaumcraftApi.armorMatSpecial, 1, 1, dataEnchCottonRobe);
		enchCottonPants = new ItemArmorInfusable(ThaumcraftApi.armorMatSpecial, 2, 2, dataEnchCottonPants);
		enchCottonBoots = new ItemArmorInfusable(ThaumcraftApi.armorMatSpecial, 1, 3, dataEnchCottonBoots);

		primalGoggles = new ItemArmorInfusable(matPrimal, 4, 0, dataPrimalGoggles);
		primalRobe = new ItemArmorInfusable(matPrimal, 1, 1, dataPrimalRobe);
		primalPants = new ItemArmorInfusable(matPrimal, 2, 2, dataPrimalPants);
		primalBoots = new ItemArmorInfusable(matPrimal, 1, 3, dataPrimalBoots);

		bronzeChainHelmet = new ItemArmorInfusable(matBronzeChain, 1, 0, dataBronzeChainCoif);
		bronzeChainmail = new ItemArmorInfusable(matBronzeChain, 1, 1, dataBronzeChainmail);
		bronzeChainGreaves = new ItemArmorInfusable(matBronzeChain, 1, 2, dataBronzeChainGreaves);
		bronzeChainBoots = new ItemArmorInfusable(matBronzeChain, 1, 3, dataBronzeChainBoots);

		wardenclothSkullcap = new ItemArmorInfusable(matWardencloth, 0, 0, dataWardenclothSkullcap);
		wardenclothTunic = new ItemArmorInfusable(matWardencloth, 0, 1, dataWardenclothTunic);
		wardenclothPants = new ItemArmorInfusable(matWardencloth, 0, 2, dataWardenclothPants);
		wardenclothBoots = new ItemArmorInfusable(matWardencloth, 0, 3, dataWardenclothBoots);

		wardenicChainCoif = new ItemArmorInfusable(matWardenicChain, 1, 0, dataWardenicChainCoif);
		wardenicChainmail = new ItemArmorInfusable(matWardenicChain, 1, 1, dataWardenicChainmail);
		wardenicChainGreaves = new ItemArmorInfusable(matWardenicChain, 1, 2, dataWardenicChainGreaves);
		wardenicChainBoots = new ItemArmorInfusable(matWardenicChain, 1, 3, dataWardenicChainBoots);

		wardenicPlateHelmet = new ItemArmorInfusable(matWardenicSteel, 2, 0, dataWardenicSteelHelmet);
		wardenicChestplate = new ItemArmorInfusable(matWardenicSteel, 2, 1, dataWardenicSteelChestplate);
		wardenicPlateGreaves = new ItemArmorInfusable(matWardenicSteel, 2, 2, dataWardenicSteelGreaves);
		wardenicPlateBoots = new ItemArmorInfusable(matWardenicSteel, 2, 3, dataWardenicSteelBoots);

		wardenicCompositeHelmet = new ItemArmorInfusable(matWardenicComposite, 2, 0, dataWardenicCompositeHelmet);
		wardenicCompositeChestplate = new ItemArmorInfusable(matWardenicComposite, 2, 1, dataWardenicCompositeChestplate);
		wardenicCompositeGreaves = new ItemArmorInfusable(matWardenicComposite, 2, 2, dataWardenicCompositeGreaves);
		wardenicCompositeBoots = new ItemArmorInfusable(matWardenicComposite, 2, 3, dataWardenicCompositeBoots);

		wardenicAwakenedHelmet = new ItemArmorInfusable(matWardenicComposite, 2, 0, dataWardenicAwakenedHelmet);
		wardenicAwakenedChestplate = new ItemArmorInfusable(matWardenicComposite, 2, 1, dataWardenicAwakenedChestplate);
		wardenicAwakenedGreaves = new ItemArmorInfusable(matWardenicComposite, 2, 2, dataWardenicAwakenedGreaves);
		wardenicAwakenedBoots = new ItemArmorInfusable(matWardenicComposite, 2, 3, dataWardenicAwakenedBoots);

		if (ThaumRevConfig.getFluxed) {
			fluxRobeGoggles = new ItemArmorInfusable(matFluxRobes, 4, 0, dataFluxRobeGoggles);
			fluxRobeVest = new ItemArmorInfusable(matFluxRobes, 1, 1, dataFluxRobeRobe);
			fluxRobePants = new ItemArmorInfusable(matFluxRobes, 2, 2, dataFluxRobePants);
			fluxRobeBoots = new ItemArmorInfusable(matFluxRobes, 1, 3, dataFluxRobeBoots);
		}
	}

	public static void loadTools() {}

	public static void loadAspects() {
		aspectExcubitor = new Aspect("excubitor", 0x3CD4FC, new Aspect[] {ELDRITCH, DEATH}, new ResourceLocation(RESOURCE_PREFIX, "textures/aspects/exubitor.png"), 771);
		aspectPatefactio = new Aspect("patefactio", 0x3971AD, new Aspect[] {TRAVEL, MIND}, new ResourceLocation(RESOURCE_PREFIX, "textures/aspects/revelatiofez.png"), 771);
		if (ThaumRevConfig.getFluxed) {
			aspectMagnes = new Aspect("magnes", 0x515970, new Aspect[] {METAL, ENERGY}, new ResourceLocation(RESOURCE_PREFIX, "textures/aspects/magnes.png"), 771);
			aspectFluxus = new Aspect("fluxus", COLOR_FLUX, new Aspect[] {aspectMagnes, MECHANISM}, new ResourceLocation(RESOURCE_PREFIX, "textures/aspects/fluxus.png"), 771);
		}
	}

	public static void loadBlocks() {
		excubituraRose = new ItemStack(blockThaumicPlant, 1, 0);
		wildCotton = new ItemStack(blockThaumicPlant, 1, 1);
		wildThistle = new ItemStack(blockThaumicPlant, 1, 2);

		shiverpearl = new ItemStack(blockThaumicPlant, 1, 5);
		stormypearl = new ItemStack(blockThaumicPlant, 1, 6);
		stonypearl = new ItemStack(blockThaumicPlant, 1, 7);

		oreChalcocite = new ItemStack(blockOre, 1, 0);
		oreSphalerite = new ItemStack(blockOre, 1, 1);
		oreCassiterite = new ItemStack(blockOre, 1, 2);
		oreMillerite = new ItemStack(blockOre, 1, 3);
		oreNativeSilver = new ItemStack(blockOre, 1, 4);
		oreGalena = new ItemStack(blockOre, 1, 5);
		oreXenotime = new ItemStack(blockOre, 1, 6);
		oreWolframite = new ItemStack(blockOre, 1, 7);
		oreIridosmium = new ItemStack(blockOre, 1, 8);
		oreBismuthinite = new ItemStack(blockOre, 1, 9);
		oreTennantite = new ItemStack(blockOre, 1, 10);
		oreTetrahedrite = new ItemStack(blockOre, 1, 11);
		orePyrope = new ItemStack(blockOre, 1, 12);
		oreDioptase = new ItemStack(blockOre, 1, 13);
		oreJethrineSapphire = new ItemStack(blockOre, 1, 14);

		RecipeHelper.registerOreDict(oreChalcocite, ORE + CU, ORE + CUS);
		RecipeHelper.registerOreDict(oreSphalerite, ORE + ZN, ORE + ZNS);
		RecipeHelper.registerOreDict(oreCassiterite, ORE + SN, ORE +SNO);
		RecipeHelper.registerOreDict(oreMillerite, ORE + NI, NIS);
		RecipeHelper.registerOreDict(oreNativeSilver, ORE + AG, ORE + NAG);
		RecipeHelper.registerOreDict(oreGalena, ORE + PB, ORE + PBS);
		RecipeHelper.registerOreDict(oreXenotime, ORE + YPO);
		RecipeHelper.registerOreDict(oreWolframite, ORE + W, ORE + WFE);
		RecipeHelper.registerOreDict(oreIridosmium, ORE + IROS);
		RecipeHelper.registerOreDict(oreBismuthinite, ORE + BI, ORE + BIS);
		RecipeHelper.registerOreDict(oreTennantite, ORE + CAS);
		RecipeHelper.registerOreDict(oreTetrahedrite, ORE + CSB);
		RecipeHelper.registerOreDict(orePyrope, ORE + PYRP);
		RecipeHelper.registerOreDict(oreDioptase, ORE + DIOP);
		RecipeHelper.registerOreDict(oreJethrineSapphire, ORE + JSPH);

		orePoorChalcocite = new ItemStack(blockPoorOre, 1, 0);
		orePoorSphalerite = new ItemStack(blockPoorOre, 1, 1);
		orePoorCassiterite = new ItemStack(blockPoorOre, 1, 2);
		orePoorMillerite = new ItemStack(blockPoorOre, 1, 3);
		orePoorNativeSilver = new ItemStack(blockPoorOre, 1, 4);
		orePoorGalena = new ItemStack(blockPoorOre, 1, 5);
		orePoorXenotime = new ItemStack(blockPoorOre, 1, 6);
		orePoorWolframite = new ItemStack(blockPoorOre, 1, 7);
		orePoorIridosmium = new ItemStack(blockPoorOre, 1, 8);
		orePoorBismuthinite = new ItemStack(blockPoorOre, 1, 9);
		orePoorTennantite = new ItemStack(blockPoorOre, 1, 10);
		orePoorTetrahedrite = new ItemStack(blockPoorOre, 1, 11);

		RecipeHelper.registerOreDict(orePoorChalcocite, ORE + POOR + CU, ORE + POOR + CUS);
		RecipeHelper.registerOreDict(orePoorSphalerite, ORE + POOR + ZN, ORE + POOR + ZNS);
		RecipeHelper.registerOreDict(orePoorCassiterite, ORE + POOR + SN, ORE + POOR + SNO);
		RecipeHelper.registerOreDict(orePoorMillerite, ORE + POOR + NI, ORE + POOR + NIS);
		RecipeHelper.registerOreDict(orePoorNativeSilver, ORE + POOR + AG, ORE + POOR + NAG);
		RecipeHelper.registerOreDict(orePoorGalena, ORE + POOR + PB, ORE + POOR + PBS);
		RecipeHelper.registerOreDict(orePoorXenotime, ORE + POOR + YPO);
		RecipeHelper.registerOreDict(orePoorWolframite, ORE + POOR + W, ORE + POOR + WFE);
		RecipeHelper.registerOreDict(orePoorIridosmium, ORE + POOR + IROS);
		RecipeHelper.registerOreDict(orePoorBismuthinite, ORE + POOR + BI, ORE + POOR + BIS);
		RecipeHelper.registerOreDict(orePoorTennantite, ORE + POOR + CAS);
		RecipeHelper.registerOreDict(orePoorTetrahedrite, ORE + POOR + CSB);

		oreGravelChalcocite = new ItemStack(blockGravelOre, 1, 0);
		oreGravelSphalerite = new ItemStack(blockGravelOre, 1, 1);
		oreGravelCassiterite = new ItemStack(blockGravelOre, 1, 2);
		oreGravelMillerite = new ItemStack(blockGravelOre, 1, 3);
		oreGravelNativeSilver = new ItemStack(blockGravelOre, 1, 4);
		oreGravelGalena = new ItemStack(blockGravelOre, 1, 5);
		oreGravelXenotime = new ItemStack(blockGravelOre, 1, 6);
		oreGravelWolframite = new ItemStack(blockGravelOre, 1, 7);
		oreGravelIridosmium = new ItemStack(blockGravelOre, 1, 8);
		oreGravelBismuthinite = new ItemStack(blockGravelOre, 1, 9);
		oreGravelTennantite = new ItemStack(blockGravelOre, 1, 10);
		oreGravelTetrahedrite = new ItemStack(blockGravelOre, 1, 11);

		RecipeHelper.registerOreDict(oreGravelChalcocite, ORE + CU, ORE + CUS);
		RecipeHelper.registerOreDict(oreGravelSphalerite, ORE + ZN, ORE + ZNS);
		RecipeHelper.registerOreDict(oreGravelCassiterite, ORE + SN, ORE + SNO);
		RecipeHelper.registerOreDict(oreGravelMillerite, ORE + NI, ORE + NIS);
		RecipeHelper.registerOreDict(oreGravelNativeSilver, ORE + AG, ORE + NAG);
		RecipeHelper.registerOreDict(oreGravelGalena, ORE + PB, ORE + PBS);
		RecipeHelper.registerOreDict(oreGravelXenotime, ORE + YPO);
		RecipeHelper.registerOreDict(oreGravelWolframite, ORE + W, ORE + WFE);
		RecipeHelper.registerOreDict(oreGravelIridosmium, ORE + IROS);
		RecipeHelper.registerOreDict(oreGravelBismuthinite, ORE + BI, ORE + BIS);
		RecipeHelper.registerOreDict(oreGravelTennantite, ORE + CAS);
		RecipeHelper.registerOreDict(oreGravelTetrahedrite, ORE + CSB);

		wardenicObsidian = new ItemStack(blockStoneDecor, 1, 0);
		eldritchStone = new ItemStack(blockStoneDecor, 1, 1);
		blockWardenicQuartzChiseled = new ItemStack(blockStoneDecor, 1, 2);
		blockWardenicQuartzPillar = new ItemStack(blockStoneDecor, 1, 3);
		blockRedquartzChiseled = new ItemStack(blockStoneDecor, 1, 6);
		blockRedquartzPillar = new ItemStack(blockStoneDecor, 1, 7);
		//thaumicStone = new ItemStack(blockStoneDecor, 1, 10);
		//infernalBlastBrick = new ItemStack(blockStoneDecor, 1, 11);
		//shadowforgeBrick = new ItemStack(blockStoneDecor, 1, 12);

		RecipeHelper.registerOreDict(wardenicObsidian, "blockWardenicObsidian");
		RecipeHelper.registerOreDict(eldritchStone, "blockEldritchStone");
		for (int i = 2; i < 6; i++) {
			RecipeHelper.registerOreDict(new ItemStack(blockStoneDecor, 1, i), BLOCK + WQRZ);
		}
		for (int i = 6; i < 10; i++) {
			RecipeHelper.registerOreDict(new ItemStack(blockStoneDecor, 1, i), BLOCK + RQZT);
		}
		//RecipeHelper.registerOreDict(thaumicStone, "blockThaumicStone");
		//RecipeHelper.registerOreDict(infernalBlastBrick, "blockInfernalBastFurnaceBrick");
		//RecipeHelper.registerOreDict(shadowforgeBrick, "blockShadowforgeBrick");

		impregnatedGreatwood = new ItemStack(blockWoodDecor, 1, 0);
		corruptedGreatwood = new ItemStack(blockWoodDecor, 1, 1);
		hardenedSilverwood = new ItemStack(blockWoodDecor, 1, 2);

		RecipeHelper.registerOreDict(impregnatedGreatwood, "plankGreatwoodImpregnated");
		RecipeHelper.registerOreDict(corruptedGreatwood, "plankGreatwoodCorrupted");
		RecipeHelper.registerOreDict(hardenedSilverwood, "plankSilverwoodHardened");

		//slabWardenicObsidian = new ItemStack(blockStoneSlab, 1, 0);
		//slabEldritch = new ItemStack(blockStoneSlab, 1, 1);
		//slabWardenicQuartz = new ItemStack(blockStoneSlab, 1, 2);

		//RecipeHelper.registerOreDict(slabWardenicObsidian, "slabWardenicObsidian");
		//RecipeHelper.registerOreDict(slabEldritch, "slabEldritchStone");
		//RecipeHelper.registerOreDict(slabWardenicQuartz, "slabWardenicQuartz");

		//stairsWardenicQuartz = new ItemStack(blockWardenicQuartzStairs, 1, 0);
	}

	public static void loadMetalBlocks() {
		blockCopper = new ItemStack(blockStorageOre, 1, 0);
		blockZinc = new ItemStack(blockStorageOre, 1, 1);
		blockTin = new ItemStack(blockStorageOre, 1, 2);
		blockNickel = new ItemStack(blockStorageOre, 1, 3);
		blockSilver = new ItemStack(blockStorageOre, 1, 4);
		blockLead = new ItemStack(blockStorageOre, 1, 5);
		blockLutetium = new ItemStack(blockStorageOre, 1, 6);
		blockTungsten = new ItemStack(blockStorageOre, 1, 7);
		blockIridium = new ItemStack(blockStorageOre, 1, 8);
		blockBismuth = new ItemStack(blockStorageOre, 1, 9);
		blockArsenic = new ItemStack(blockStorageOre, 1, 10);
		blockAntimony = new ItemStack(blockStorageOre, 1, 11);
		blockNeodymium = new ItemStack(blockStorageOre, 1, 12);
		blockOsmium = new ItemStack(blockStorageOre, 1, 13);
		blockPalladium = new ItemStack(blockStorageOre, 1, 14);
		blockAluminium = new ItemStack(blockStorageOre, 1, 15);

		for (int i = 0; i < NAMES_STORAGE_ORE.length; i++) {
			RecipeHelper.registerOreDict(new ItemStack(blockStorageOre, 1, i), BLOCK + NAMES_STORAGE_ORE[i]);
		}

		blockBrass = new ItemStack(blockStorageAlloy1, 1, 0);
		blockBronze = new ItemStack(blockStorageAlloy1, 1, 1);
		blockArsenicalBronze = new ItemStack(blockStorageAlloy1, 1, 2);
		blockAntimonialBronze = new ItemStack(blockStorageAlloy1, 1, 3);
		blockBismuthBronze = new ItemStack(blockStorageAlloy1, 1, 4);
		blockMithril = new ItemStack(blockStorageAlloy1, 1, 5);
		blockAlumiuiumBronze = new ItemStack(blockStorageAlloy1, 1, 6);
		blockCupronickel = new ItemStack(blockStorageAlloy1, 1, 7);
		blockRiftishBronze = new ItemStack(blockStorageAlloy1, 1, 8);
		blockConstantan = new ItemStack(blockStorageAlloy1, 1, 9);
		blockInvar = new ItemStack(blockStorageAlloy1, 1, 10);
		blockElectrum = new ItemStack(blockStorageAlloy1, 1, 11);
		blockWardenicMetal = new ItemStack(blockStorageAlloy1, 1, 12);
		blockDullRedsolder = new ItemStack(blockStorageAlloy1, 1, 13);
		blockRedsolder = new ItemStack(blockStorageAlloy1, 1, 14);

		for (int i = 0; i < NAMES_STORAGE_ALLOY_1.length; i++) {
			if (i != 5) {
				RecipeHelper.registerOreDict(new ItemStack(blockStorageAlloy1, 1, i), BLOCK + NAMES_STORAGE_ALLOY_1[i]);
			}
		}
		RecipeHelper.registerOreDict(blockMithril, BLOCK + MTHR);

		blockThaumicElectrum = new ItemStack(blockStorageSpecial, 1, 0);
		blockThaumicRiftishBronze = new ItemStack(blockStorageSpecial, 1, 1);
		blockSteel = new ItemStack(blockStorageSpecial, 1, 2);
		blockThaumicSteel = new ItemStack(blockStorageSpecial, 1, 3);
		blockVoidbrass = new ItemStack(blockStorageSpecial, 1, 4);
		blockVoidsteel = new ItemStack(blockStorageSpecial, 1, 5);
		blockVoidtungsten = new ItemStack(blockStorageSpecial, 1, 6);
		blockVoidcupronickel = new ItemStack(blockStorageSpecial, 1, 7);

		for (int i = 0; i < NAMES_STORAGE_SPECIAL.length; i++) {
			RecipeHelper.registerOreDict(new ItemStack(blockStorageSpecial, 1, i), BLOCK + NAMES_STORAGE_SPECIAL[i]);
		}

		blockWardenicBronze = new ItemStack(blockStorageEquipment, 1, 0);
		blockWardenicSteel = new ItemStack(blockStorageEquipment, 1, 1);
		blockWardenicRiftishBronze = new ItemStack(blockStorageEquipment, 1, 2);
		blockWardenicComposite = new ItemStack(blockStorageEquipment, 1, 3);
		blockArcaneRedsolder = new ItemStack(blockStorageEquipment, 1, 4);
		blockRedbronze = new ItemStack(blockStorageEquipment, 1, 5);
		blockHardenedRedbronze = new ItemStack(blockStorageEquipment, 1, 6);
		blockFluxsteel = new ItemStack(blockStorageEquipment, 1, 7);
		blockFluxedTungsten = new ItemStack(blockStorageEquipment, 1, 8);
		blockMagneoturgicComposite = new ItemStack(blockStorageEquipment, 1, 9);
		blockFluxedComposite = new ItemStack(blockStorageEquipment, 1, 10);
		blockResonantFluxedComposite = new ItemStack(blockStorageEquipment, 1, 11);
		blockEmpoweredVoidbrass = new ItemStack(blockStorageEquipment, 1, 12);
		blockCrimsonThaumium = new ItemStack(blockStorageEquipment, 1, 13);
		blockOccultVoidtungsten = new ItemStack(blockStorageEquipment, 1, 14);

		for (int i = 0; i < NAMES_STORAGE_EQUIPMENT.length; i++) {
			RecipeHelper.registerOreDict(new ItemStack(blockStorageEquipment, 1, i), BLOCK + NAMES_STORAGE_EQUIPMENT[i]);
		}

		blockPyrope = new ItemStack(blockStorageGem, 1, 0);
		blockDioptase = new ItemStack(blockStorageGem, 1, 1);
		blockJethrineSapphire = new ItemStack(blockStorageGem, 1, 2);
		blockJethrinePyroptase = new ItemStack(blockStorageGem, 1, 3);
		blockWardenicCrystal = new ItemStack(blockStorageGem, 1, 4);
		blockActivatedWardenicCrystal = new ItemStack(blockStorageGem, 1, 5);
		blockAwakenedWardenicCrystal = new ItemStack(blockStorageGem, 1, 6);

		blockWardenicQuartz = new ItemStack(blockStorageGem, 1, 8);
		blockInfusedQuartz = new ItemStack(blockStorageGem, 1, 9);
		blockRedquartz = new ItemStack(blockStorageGem, 1, 10);

		for (int i = 0; i < LINK_STORAGE_GEM_META.length; i++) {
			RecipeHelper.registerOreDict(new ItemStack(blockStorageGem, 1, LINK_STORAGE_GEM_META[i]), BLOCK + NAMES_STORAGE_GEM_ORE[i]);
		}

		blockLanthanides = new ItemStack(blockStorageMisc, 1, 0);
		blockXenotimeJunk = new ItemStack(blockStorageMisc, 1, 1);
		blockIridosmium = new ItemStack(blockStorageMisc, 1, 2);
		blockThaumicBronze = new ItemStack(blockStorageMisc, 1, 8);
		blockOsmiumLutetium = new ItemStack(blockStorageMisc, 1, 9);
		blockVoidmetal = new ItemStack(blockStorageMisc, 1, 15);

		for (int i = 0; i < LINK_STORAGE_MISC_META.length; i++) {
			RecipeHelper.registerOreDict(new ItemStack(blockStorageMisc, 1, LINK_STORAGE_MISC_META[i]), BLOCK + NAMES_STORAGE_MISC_INT[i]);
		}
		//block = new ItemStack(blockStorage, 1, );
		//RecipeHelper.registerOreDict(new ItemStack(blockStorageOre, 1, ), "block");
	}

	public static void loadItems() {
		itemCotton = generalItem.addOreDictItem(0, M0000);
		itemCottonFiber = generalItem.addOreDictItem(1, M0001);
		itemCottonFabric = generalItem.addOreDictItem(2, M0002);
		itemCottonTreated = generalItem.addOreDictItem(3, M0003);
		itemCottonEnchanted = generalItem.addOreDictItem(4, M0004);
		itemThistleLeaf = generalItem.addOreDictItem(5, M0005);
		itemThistleFlower = generalItem.addOreDictItem(6, M0006);

		itemArcaneSingularity = generalItem.addOreDictItem(10, "itemArcaneSingularity");
		itemStabilizedSingularity = generalItem.addOreDictItem(11, "itemStabilizedSingularity");
		itemAnimatedPiston = generalItem.addOreDictItem(12, "itemAnimatedPiston");

		itemAspectOrbReceptorMakeshift = generalItem.addOreDictItem(20, "itemAspectOrbReceptorMakeshift");
		itemAspectOrbReceptorAdvanced = generalItem.addOreDictItem(21, "itemAspectOrbReceptorAdvanced");

		itemEldritchCog = generalItem.addOreDictItem(30, "itemEldritchCog");
		itemEldritchKeystone = generalItem.addOreDictItem(31, "itemEldritchKeystone");
		itemSoulFragment = generalItem.addOreDictItem(32, "itemSoulFragment");
		itemPodCinderpearl = generalItem.addOreDictItem(33, "itemPodCinderpearl");
		itemPodShiverpearl = generalItem.addOreDictItem(34, "itemPodShiverpearl");
		itemPodStormypearl = generalItem.addOreDictItem(35, "itemPodStormypearl");
		itemPodStonypearl = generalItem.addOreDictItem(36, "itemPodStonypearl");

		shaftGreatwood = generalItem.addOreDictItem(40, "shaftGreatwood", "itemShaftGreatwood");
		plankGreatwoodEnchanted = generalItem.addOreDictItemWithEffect(41, "plankEnchantedGreatwood", "plankGreatwood", "itemPlankEnchantedGreatwood");
		shaftGreatwoodEnchanted = generalItem.addOreDictItem(42, new ItemEntry("shaftEnchantedGreatwood").setEnchanted(true).setTexture("shaftGreatwood"), "itemShaftEnchantedGreatwood");

		itemSilverwoodDensified = generalItem.addOreDictItem(50, "densifiedSilverwood", "itemDensifiedSilverwood");
		plankSilverwoodEnchanted = generalItem.addOreDictItemWithEffect(51, "plankEnchantedSilverwood", "plankSilverwood", "itemPlankEnchantedSilverwood");
		plankSilverwoodEnchantedDensified = generalItem.addOreDictItemWithEffect(52, "plankDensifiedSilverwood", "itemPlankEnchantedDensifiedSilverwood");
		plankSilverwoodConsecrated = generalItem.addOreDictItemWithEffect(53, "plankConsecratedSilverwood", "itemPlankConsecratedSilverwood");
		shaftSilverwoodEnchanted = generalItem.addOreDictItemWithEffect(54, "shaftEnchantedSilverwood", "shaftSilverwood", "itemShaftEnchantedSilverwood");
		shaftSilverwoodConsecrated = generalItem.addOreDictItemWithEffect(55, "shaftConsecratedSilverwood", "itemShaftConsecratedSilverwood");

		chainThaumicBronze = generalItem.addColorizedOreDictItem(60, CHAIN + TBRZ, CHAIN, COLOR_TBRZ, CHAIN_ORE + TBRZ);


		seedExcubitura = generalItem.addOreDictItem(950, SEED + EXCU);
		seedCotton = generalItem.addOreDictItem(951, SEED + CTTN);
		seedThistle = generalItem.addOreDictItem(952, SEED + THSL);
		seedShimmerleaf = generalItem.addOreDictItem(953, SEED + SHML);
		seedCinderpearl = generalItem.addOreDictItem(954, SEED + CNDP);
		seedShiverpearl = generalItem.addOreDictItem(955, SEED + SHVP);
		seedStormypearl = generalItem.addOreDictItem(956, SEED + STMP);
		seedStonypearl = generalItem.addOreDictItem(957, SEED + STNP);

		//WARDENIC ARSENAL
		itemExcubituraPetal = generalItem.addOreDictItem(1000, "itemExcubituraPetal");
		itemExcubituraPaste = generalItem.addOreDictItem(1001, paste);
		itemFabricExcubitura = generalItem.addOreDictItem(1002, "itemExcubituraFabric");
		itemWardencloth = generalItem.addOreDictItem(1003, wardencloth);

		excubituraOilRaw = generalItem.addOreDictItem(1035, "itemExcubituraOilRaw", "itemExcubituraOilUnprocessed");
		excubituraOil = generalItem.addOreDictItem(1036, oilExcu);
		chainWardenicBronze = generalItem.addColorizedOreDictItem(1037, CHAIN + WBRZ, CHAIN, COLOR_WBRZ, CHAIN_ORE + WBRZ);
		chainPrimalBronze = generalItem.addOreDictItem(1038, new ItemEntryColorizedOverlay(chainPBronze, 1).setTextureOverlay("overlayCharmChain").setColorData(CHAIN, chainPBronze, COLOR_WBRZ), chainPBronze);
		plateWardenicBronzeMirror = generalItem.addOreDictItem(1039, PLATE_ALT + WBRZ + MIRROR);

		excubituraOilPure = generalItem.addOreDictItem(1070, ITEM + EXCU + OIL + PURE);
		chainWardenicSteel = generalItem.addColorizedOreDictItem(1071, CHAIN + WDST, CHAIN, COLOR_WDST, CHAIN_ORE + WDST);
		chainWardenicSteelOiled = generalItem.addColorizedOreDictItemWithEffect(1072, CHAIN + WDST + OILED, CHAIN, COLOR_WDST, CHAIN_ORE + WDST + OILED);
		plateWardenicSteelDetailed = generalItem.addOreDictItem(1073, PLATE_ALT + WDST + DETAILED);
		plateWardenicSteelRunic = generalItem.addOreDictItem(1074, PLATE_ALT + WDST + RUNIC);
		plateWardenicSteelConsecrated = generalItem.addOreDictItem(1075, PLATE_ALT + WDST + CONSECRATED);

		plateWardenicCompositeInfused = generalItem.addOreDictItem(1105, M1105);
		plateWardenicCompositeFitted = generalItem.addOreDictItem(1106, M1106);
		plateWardenicCompositeDetailed = generalItem.addOreDictItem(1107, M1107);
		plateWardenicCompositeRunic = generalItem.addOreDictItem(1108, M1108);
		plateWardenicCompositeConsecrated = generalItem.addOreDictItem(1109, M1109);
		plateWardenicCompositePrimal = generalItem.addOreDictItem(1110, M1110);

		//itemSilverclothPrimal = generalItem.addOreDictItem(1125, "itemSilverclothPrimal");

		itemEssenceOfAwakening = generalItem.addOreDictItem(1140, "itemEssenceOfAwakening");

		wardenicHardener = generalItem.addOreDictItem(1175, "itemWardenicHardener");

		if (ThaumRevConfig.getFluxed) {
			itemFabricRedstone = generalItem.addOreDictItem(1200, M1200);
			itemRedcloth = generalItem.addOreDictItem(1201, "itemRedcloth");
			itemRedclothCapacitive = generalItem.addOreDictItem(1202, "itemRedclothCapacitive");

			itemCapacitorCoreArcaneRedsolder = generalItem.addOreDictItem(1600, M1600);
		}

		powderBlizzBackup = generalItem.addColorizedOreDictItem(4900, POWDER + BLIZZ, POWDER, COLOR_BLIZZ, DUST + BLIZZ);
		powderBlitzBackup = generalItem.addColorizedOreDictItem(4901, POWDER + BLITZ, POWDER, COLOR_BLITZ, DUST + BLITZ);
		powderBasalzBackup = generalItem.addColorizedOreDictItem(4902, POWDER + BASALZ, POWDER, COLOR_BASALZ, DUST + BASALZ);

		rodBlizzBackup = generalItem.addColorizedOreDictItem(4905, ROD + BLIZZ, ROD, COLOR_BLIZZ);
		rodBlitzBackup = generalItem.addColorizedOreDictItem(4906, ROD + BLITZ, ROD, COLOR_BLITZ);
		rodBasalzBackup = generalItem.addColorizedOreDictItem(4907, ROD + BASALZ, ROD, COLOR_BASALZ);

		if (LoadedHelper.isThermalFoundationLoaded) {
			generalItem.setHidden(true, 4900, 4901, 4902, 4905, 4906, 4907);
		}
	}

	public static void loadMetalItems() {
		ingotCopper = generalItem.addColorizedOreDictItem(5000, INGOT + CU, INGOT, COLOR_CU);
		ingotZinc = generalItem.addColorizedOreDictItem(5001, INGOT + ZN, INGOT, COLOR_ZN);
		ingotTin = generalItem.addColorizedOreDictItem(5002, INGOT + SN, INGOT, COLOR_SN);
		ingotNickel = generalItem.addColorizedOreDictItem(5003, INGOT + NI, INGOT, COLOR_NI);
		ingotSilver = generalItem.addColorizedOreDictItem(5004, INGOT + AG, INGOT, COLOR_AG);
		ingotLead = generalItem.addColorizedOreDictItem(5005, INGOT + PB, INGOT, COLOR_PB);
		ingotLutetium = generalItem.addColorizedOreDictItem(5006, INGOT + LU, INGOT, COLOR_LU, 1);
		ingotTungsten = generalItem.addColorizedOreDictItem(5007, INGOT + W, INGOT, COLOR_W, 1);
		ingotIridium = generalItem.addColorizedOreDictItem(5008, INGOT + IR, INGOT, COLOR_IR, 2);
		ingotBismuth = generalItem.addColorizedOreDictItem(5009, INGOT + BI, INGOT, COLOR_BI);
		ingotArsenic = generalItem.addColorizedOreDictItem(5010, INGOT + AS, INGOT, COLOR_AS);
		ingotAntimony = generalItem.addColorizedOreDictItem(5011, INGOT + SB, INGOT, COLOR_SB);
		ingotNeodymium = generalItem.addColorizedOreDictItem(5012, INGOT + ND, INGOT, COLOR_ND, 1);
		ingotOsmium = generalItem.addColorizedOreDictItem(5013, INGOT + OS, INGOT, COLOR_OS, 1);
		ingotPalladium = generalItem.addColorizedOreDictItem(5014, INGOT + PD, INGOT, COLOR_PD, 1);
		ingotAluminium = generalItem.addColorizedOreDictItem(5015, INGOT + AL, INGOT, COLOR_AL);

		ingotBrass = generalItem.addColorizedOreDictItem(5032, INGOT + CUZN, INGOT, COLOR_CUZN);
		ingotBronze = generalItem.addColorizedOreDictItem(5033, INGOT + CUSN, INGOT, COLOR_CUSN);
		ingotArsenicalBronze = generalItem.addColorizedOreDictItem(5034, INGOT + CUAS, INGOT, COLOR_CUAS);
		ingotAntimonialBronze = generalItem.addColorizedOreDictItem(5035, INGOT + CUSB, INGOT, COLOR_CUSB);
		ingotBismuthBronze = generalItem.addColorizedOreDictItem(5036, INGOT + CUBI, INGOT, COLOR_CUBI);
		ingotMithril = generalItem.addColorizedOreDictItem(5037, INGOT + MTHL, INGOT, COLOR_MTHR, 1, INGOT + MTHR);
		ingotAluminiumBronze = generalItem.addColorizedOreDictItem(5038, INGOT + CUAL, INGOT, COLOR_CUAL);
		ingotCupronickel = generalItem.addColorizedOreDictItem(5039, INGOT + CUNI, INGOT, COLOR_CUNI);
		ingotRiftishBronze = generalItem.addColorizedOreDictItem(5040, INGOT + RBRZ, INGOT, COLOR_RBRZ, 1);
		ingotConstantan = generalItem.addColorizedOreDictItem(5041, INGOT + CNST, INGOT, COLOR_CNST);
		ingotInvar = generalItem.addColorizedOreDictItem(5042, INGOT + FENI, INGOT, COLOR_FENI);
		ingotElectrum = generalItem.addColorizedOreDictItem(5043, INGOT + AUAG, INGOT, COLOR_AUAG);
		ingotWardenicMetal = generalItem.addColorizedOreDictItem(5044, INGOT + WRDM, INGOT, COLOR_WRDM);
		ingotDullRedsolder = generalItem.addColorizedOreDictItem(5045, INGOT + DRDS, INGOT, COLOR_DRDS);
		ingotRedsolder = generalItem.addColorizedOreDictItem(5046, INGOT + RDSR, INGOT, COLOR_RDSR);

		ingotThaumicElectrum = generalItem.addColorizedOreDictItemWithEffect(5064, INGOT + TELC, INGOT, COLOR_TELC, 1);
		ingotThaumicRiftishBronze = generalItem.addColorizedOreDictItem(5065, INGOT + TRBR, INGOT, COLOR_TRBR, 1);
		ingotSteel = generalItem.addColorizedOreDictItem(5066, INGOT + STEL, INGOT, COLOR_STEL, 1);
		ingotThaumicSteel = generalItem.addColorizedOreDictItem(5067, INGOT + TSTL, INGOT, COLOR_TSTL, 1);
		ingotVoidbrass = generalItem.addColorizedOreDictItem(5068, INGOT + VBRS, INGOT, COLOR_VBRS, 1);
		ingotVoidsteel = generalItem.addColorizedOreDictItem(5069, INGOT + VSTL, INGOT, COLOR_VSTL, 1);
		ingotVoidtungsten = generalItem.addColorizedOreDictItemWithEffect(5070, INGOT + VDWT, INGOT, COLOR_VDWT, 2);
		ingotVoidcupronickel = generalItem.addColorizedOreDictItem(5071, INGOT + VCPN, INGOT, COLOR_VCPN, 1);

		ingotWardenicBronze = generalItem.addColorizedOreDictItem(5080, INGOT + WBRZ, INGOT, COLOR_WBRZ);
		ingotWardenicSteel = generalItem.addColorizedOreDictItem(5081, INGOT + WDST, INGOT, COLOR_WDST, 1);
		ingotWardenicRiftishBronze = generalItem.addColorizedOreDictItem(5082, INGOT + WRBR, INGOT, COLOR_WRBR, 1);
		ingotWardenicComposite = generalItem.addOreDictItemWithEffect(5083, INGOT + WCMP, 2);
		ingotArcaneRedsolder = generalItem.addColorizedOreDictItem(5084, INGOT + ARDS, INGOT, COLOR_ARDS);
		ingotRedbronze = generalItem.addColorizedOreDictItem(5085, INGOT + RDBR, INGOT, COLOR_RDBR);
		ingotHardenedRedbronze = generalItem.addColorizedOreDictItem(5086, INGOT + HRBR, INGOT, COLOR_HRBR, 1);
		ingotFluxsteel = generalItem.addColorizedOreDictItem(5087, INGOT + FSTL, INGOT, COLOR_FSTL, 1);
		ingotFluxedTungsten = generalItem.addColorizedOreDictItem(5088, INGOT + FLXW, INGOT, COLOR_FLXW, 2);
		ingotMagneoturgicComposite = generalItem.addOreDictItem(5089, INGOT + MCMP, 2);
		ingotFluxedComposite = generalItem.addColorizedOreDictItemWithEffect(5090, INGOT + FCMP, INGOT, COLOR_FLUX, 2);
		ingotResonantFluxedComposite = generalItem.addColorizedOreDictItemWithEffect(5091, INGOT + RCMP, INGOT, COLOR_RCMP, 3);
		ingotEmpoweredVoidbrass = generalItem.addColorizedOreDictItem(5092, INGOT + EVBS, INGOT, COLOR_EVBS, 1);
		ingotCrimsonThaumium = generalItem.addColorizedOreDictItem(5093, INGOT + CTHM, INGOT, COLOR_CTHM, 1);
		ingotOccultVoidtungsten = generalItem.addColorizedOreDictItemWithEffect(5094, INGOT + OCVW, INGOT, COLOR_OCVW, 2);

		gemPyrope = generalItem.addOreDictItem(5096, GEM + PYRP, 2);
		gemDioptase = generalItem.addOreDictItem(5097, GEM + DIOP, 2);
		gemJethrineSapphire = generalItem.addOreDictItem(5098, GEM + JSPH, 2);
		gemJethrinePyroptase = generalItem.addOreDictItem(5099, GEM + JPRT, 3);
		gemWardenicCrystal = generalItem.addOreDictItem(5100, GEM + WCRS, 2);
		gemActivatedWardenicCrystal = generalItem.addOreDictItem(5101, GEM + AWCR, 2);
		gemAwakenedWardenicCrystal = generalItem.addOreDictItem(5102, GEM + WWCR, 3);

		gemWardenicQuartz = generalItem.addColorizedOreDictItem(5104, GEM + WQRZ, QUARTZ, COLOR_WQRZ, 1);
		gemInfusedQuartz = generalItem.addOreDictItem(5105, GEM + IQRZ, 1);
		gemRedquartz = generalItem.addColorizedOreDictItem(5106, GEM + RQRZ, QUARTZ, COLOR_FLUX, 1, GEM + RQZT);

		ingotLanthanides = generalItem.addColorizedOreDictItem(5112, INGOT + YPO, INGOT, COLOR_LNTH, 1, INGOT + LNTH);
		ingotXenotimeJunk = generalItem.addColorizedOreDictItem(5113, INGOT + LAMM, INGOT, COLOR_YPOJ, 1, INGOT + YPOJ);
		ingotIridosmium = generalItem.addColorizedOreDictItem(5114, INGOT + IROS, INGOT, COLOR_IROS, 2);

		ingotThaumicBronze = generalItem.addColorizedOreDictItemWithEffect(5120, INGOT + TBRZ, INGOT, COLOR_TBRZ);
		ingotOsmiumLutetium = generalItem.addColorizedOreDictItemWithEffect(5121, INGOT + OSLU, INGOT, COLOR_OSLU, 2);

		nuggetCopper = generalItem.addColorizedOreDictItem(5200, NUGGET + CU, NUGGET, COLOR_CU);
		nuggetZinc = generalItem.addColorizedOreDictItem(5201, NUGGET + ZN, NUGGET, COLOR_ZN);
		nuggetTin = generalItem.addColorizedOreDictItem(5202, NUGGET + SN, NUGGET, COLOR_SN);
		nuggetNickel = generalItem.addColorizedOreDictItem(5203, NUGGET + NI, NUGGET, COLOR_NI);
		nuggetSilver = generalItem.addColorizedOreDictItem(5204, NUGGET + AG, NUGGET, COLOR_AG);
		nuggetLead = generalItem.addColorizedOreDictItem(5205, NUGGET + PB, NUGGET, COLOR_PB);
		nuggetLutetium = generalItem.addColorizedOreDictItem(5206, NUGGET + LU, NUGGET, COLOR_LU, 1);
		nuggetTungsten = generalItem.addColorizedOreDictItem(5207, NUGGET + W, NUGGET, COLOR_W, 1);
		nuggetIridium = generalItem.addColorizedOreDictItem(5208, NUGGET + IR, NUGGET, COLOR_IR, 2);
		nuggetBismuth = generalItem.addColorizedOreDictItem(5209, NUGGET + BI, NUGGET, COLOR_BI);
		nuggetArsenic = generalItem.addColorizedOreDictItem(5210, NUGGET + AS, NUGGET, COLOR_AS);
		nuggetAntimony = generalItem.addColorizedOreDictItem(5211, NUGGET + SB, NUGGET, COLOR_SB);
		nuggetNeodymium = generalItem.addColorizedOreDictItem(5212, NUGGET + ND, NUGGET, COLOR_ND, 1);
		nuggetOsmium = generalItem.addColorizedOreDictItem(5213, NUGGET + OS, NUGGET, COLOR_OS, 1);
		nuggetPalladium = generalItem.addColorizedOreDictItem(5214, NUGGET + PD, NUGGET, COLOR_PD, 1);
		nuggetAluminium = generalItem.addColorizedOreDictItem(5215, NUGGET + AL, NUGGET, COLOR_AL);

		nuggetBrass = generalItem.addColorizedOreDictItem(5232, NUGGET + CUZN, NUGGET, COLOR_CUZN);
		nuggetBronze = generalItem.addColorizedOreDictItem(5233, NUGGET + CUSN, NUGGET, COLOR_CUSN);
		nuggetArsenicalBronze = generalItem.addColorizedOreDictItem(5234, NUGGET + CUAS, NUGGET, COLOR_CUAS);
		nuggetAntimonialBronze = generalItem.addColorizedOreDictItem(5235, NUGGET + CUSB, NUGGET, COLOR_CUSB);
		nuggetBismuthBronze = generalItem.addColorizedOreDictItem(5236, NUGGET + CUBI, NUGGET, COLOR_CUBI);
		nuggetMithril = generalItem.addColorizedOreDictItem(5237, NUGGET + MTHL, NUGGET, COLOR_MTHR, 1, NUGGET + MTHR);
		nuggetAluminiumBronze = generalItem.addColorizedOreDictItem(5238, NUGGET + CUAL, NUGGET, COLOR_CUAL);
		nuggetCupronickel = generalItem.addColorizedOreDictItem(5239, NUGGET + CUNI, NUGGET, COLOR_CUNI);
		nuggetRiftishBronze = generalItem.addColorizedOreDictItem(5240, NUGGET + RBRZ, NUGGET, COLOR_RBRZ, 1);
		nuggetConstantan = generalItem.addColorizedOreDictItem(5241, NUGGET + CNST, NUGGET, COLOR_CNST);
		nuggetInvar = generalItem.addColorizedOreDictItem(5242, NUGGET + FENI, NUGGET, COLOR_FENI);
		nuggetElectrum = generalItem.addColorizedOreDictItem(5243, NUGGET + AUAG, NUGGET, COLOR_AUAG);
		nuggetWardenicMetal = generalItem.addColorizedOreDictItem(5244, NUGGET + WRDM, NUGGET, COLOR_WRDM);
		nuggetDullRedsolder = generalItem.addColorizedOreDictItem(5245, NUGGET + DRDS, NUGGET, COLOR_DRDS);
		nuggetRedsolder = generalItem.addColorizedOreDictItem(5246, NUGGET + RDSR, NUGGET, COLOR_RDSR);

		nuggetThaumicElectrum = generalItem.addColorizedOreDictItemWithEffect(5264, NUGGET + TELC, NUGGET, COLOR_TELC, 1);
		nuggetThaumicRiftishBronze = generalItem.addColorizedOreDictItem(5265, NUGGET + TRBR, NUGGET, COLOR_TRBR, 1);
		nuggetSteel = generalItem.addColorizedOreDictItem(5266, NUGGET + STEL, NUGGET, COLOR_STEL, 1);
		nuggetThaumicSteel = generalItem.addColorizedOreDictItem(5267, NUGGET + TSTL, NUGGET, COLOR_TSTL, 1);
		nuggetVoidbrass = generalItem.addColorizedOreDictItem(5268, NUGGET + VBRS, NUGGET, COLOR_VBRS, 1);
		nuggetVoidsteel = generalItem.addColorizedOreDictItem(5269, NUGGET + VSTL, NUGGET, COLOR_VSTL, 1);
		nuggetVoidtungsten = generalItem.addColorizedOreDictItemWithEffect(5270, NUGGET + VDWT, NUGGET, COLOR_VDWT, 2);
		nuggetVoidcupronickel = generalItem.addColorizedOreDictItem(5271, NUGGET + VCPN, NUGGET, COLOR_VCPN, 1);

		nuggetWardenicBronze = generalItem.addColorizedOreDictItem(5280, NUGGET + WBRZ, NUGGET, COLOR_WBRZ);
		nuggetWardenicSteel = generalItem.addColorizedOreDictItem(5281, NUGGET + WDST, NUGGET, COLOR_WDST, 1);
		nuggetWardenicRiftishBronze = generalItem.addColorizedOreDictItem(5282, NUGGET + WRBR, NUGGET, COLOR_WRBR, 1);
		nuggetWardenicComposite = generalItem.addColorizedOreDictItemWithEffect(5283, NUGGET + WCMP, NUGGET, COLOR_WCMP, 2);
		nuggetArcaneRedsolder = generalItem.addColorizedOreDictItem(5284, NUGGET + ARDS, NUGGET, COLOR_ARDS);
		nuggetRedbronze = generalItem.addColorizedOreDictItem(5285, NUGGET + RDBR, NUGGET, COLOR_RDBR);
		nuggetHardenedRedbronze = generalItem.addColorizedOreDictItem(5286, NUGGET + HRBR, NUGGET, COLOR_HRBR, 1);
		nuggetFluxsteel = generalItem.addColorizedOreDictItem(5287, NUGGET + FSTL, NUGGET, COLOR_FSTL, 1);
		nuggetFluxedTungsten = generalItem.addColorizedOreDictItem(5288, NUGGET + FLXW, NUGGET, COLOR_FLXW, 2);
		nuggetMagneoturgicComposite = generalItem.addColorizedOreDictItem(5289, NUGGET + MCMP, NUGGET, COLOR_MCMP, 2);
		nuggetFluxedComposite = generalItem.addColorizedOreDictItemWithEffect(5290, NUGGET + FCMP, NUGGET, COLOR_FLUX, 2);
		nuggetResonantFluxedComposite = generalItem.addColorizedOreDictItemWithEffect(5291, NUGGET + RCMP, NUGGET, COLOR_RCMP, 3);
		nuggetEmpoweredVoidbrass = generalItem.addColorizedOreDictItem(5292, NUGGET + EVBS, NUGGET, COLOR_EVBS, 1);
		nuggetCrimsonThaumium = generalItem.addColorizedOreDictItem(5293, NUGGET + CTHM, NUGGET, COLOR_CTHM, 1);
		nuggetOccultVoidtungsten = generalItem.addColorizedOreDictItemWithEffect(5294, NUGGET + OCVW, NUGGET, COLOR_OCVW, 2);

		shardPyrope = generalItem.addOreDictItem(5296, SHARD + PYRP, 2, NUGGET + PYRP);
		shardDioptase = generalItem.addOreDictItem(5297, SHARD + DIOP, 2, NUGGET + DIOP);
		shardJethrineSapphire = generalItem.addOreDictItem(5298, SHARD + JSPH, 2, NUGGET + JSPH);
		shardJethrinePyroptase = generalItem.addOreDictItem(5299, SHARD + JPRT, 3, NUGGET + JPRT);
		shardWardenicCrystal = generalItem.addOreDictItem(5300, SHARD + WCRS, 2, NUGGET + WCRS);
		shardActivatedWardenicCrystal = generalItem.addOreDictItem(5301, SHARD + AWCR, 2, NUGGET + AWCR);
		shardAwakenedWardenicCrystal = generalItem.addOreDictItem(5302, SHARD + WWCR, 3, NUGGET + WWCR);

		shardWardenicQuartz = generalItem.addColorizedOreDictItem(5304, SHARD + WQRZ, SHARD_QUARTZ, COLOR_WQRZ, 1, NUGGET + WQRZ);
		shardInfusedQuartz = generalItem.addOreDictItem(5305, SHARD + IQRZ, 1, NUGGET + IQRZ);
		shardRedquartz = generalItem.addColorizedOreDictItem(5306, NUGGET + RQRZ, SHARD_QUARTZ, COLOR_FLUX, 1, NUGGET + RQZT);

		nuggetLanthanides = generalItem.addColorizedOreDictItem(5312, NUGGET + YPO, NUGGET, COLOR_LNTH, 1, NUGGET + LNTH);
		nuggetXenotimeJunk = generalItem.addColorizedOreDictItem(5313, NUGGET + LAMM, NUGGET, COLOR_YPOJ, 1, NUGGET + YPOJ);
		nuggetIridosmium = generalItem.addColorizedOreDictItem(5314, NUGGET + IROS, NUGGET, COLOR_IROS, 2);

		nuggetThaumicBronze = generalItem.addColorizedOreDictItemWithEffect(5320, NUGGET + TBRZ, NUGGET, COLOR_TBRZ);
		nuggetOsmiumLutetium = generalItem.addColorizedOreDictItemWithEffect(5321, NUGGET + OSLU, NUGGET, COLOR_OSLU, 2);

		dustCopper = generalItem.addColorizedOreDictItem(5400, DUST + CU, DUST, COLOR_CU);
		dustZinc = generalItem.addColorizedOreDictItem(5401, DUST + ZN, DUST, COLOR_ZN);
		dustTin = generalItem.addColorizedOreDictItem(5402, DUST + SN, DUST, COLOR_SN);
		dustNickel = generalItem.addColorizedOreDictItem(5403, DUST + NI, DUST, COLOR_NI);
		dustSilver = generalItem.addColorizedOreDictItem(5404, DUST + AG, DUST, COLOR_AG);
		dustLead = generalItem.addColorizedOreDictItem(5405, DUST + PB, DUST, COLOR_PB);
		dustLutetium = generalItem.addColorizedOreDictItem(5406, DUST + LU, DUST, COLOR_LU, 1);
		dustTungsten = generalItem.addColorizedOreDictItem(5407, DUST + W, DUST, COLOR_W, 1);
		dustIridium = generalItem.addColorizedOreDictItem(5408, DUST + IR, DUST, COLOR_IR, 2);
		dustBismuth = generalItem.addColorizedOreDictItem(5409, DUST + BI, DUST, COLOR_BI);
		dustArsenic = generalItem.addColorizedOreDictItem(5410, DUST + AS, DUST, COLOR_AS);
		dustAntimony = generalItem.addColorizedOreDictItem(5411, DUST + SB, DUST, COLOR_SB);
		dustNeodymium = generalItem.addColorizedOreDictItem(5412, DUST + ND, DUST, COLOR_ND, 1);
		dustOsmium = generalItem.addColorizedOreDictItem(5413, DUST + OS, DUST, COLOR_OS, 1);
		dustPalladium = generalItem.addColorizedOreDictItem(5414, DUST + PD, DUST, COLOR_PD, 1);
		dustAluminium = generalItem.addColorizedOreDictItem(5415, DUST + AL, DUST, COLOR_AL);

		dustBrass = generalItem.addColorizedOreDictItem(5432, DUST + CUZN, DUST, COLOR_CUZN);
		dustBronze = generalItem.addColorizedOreDictItem(5433, DUST + CUSN, DUST, COLOR_CUSN);
		dustArsenicalBronze = generalItem.addColorizedOreDictItem(5434, DUST + CUAS, DUST, COLOR_CUAS);
		dustAntimonialBronze = generalItem.addColorizedOreDictItem(5435, DUST + CUSB, DUST, COLOR_CUSB);
		dustBismuthBronze = generalItem.addColorizedOreDictItem(5436, DUST + CUBI, DUST, COLOR_CUBI);
		dustMithril = generalItem.addColorizedOreDictItem(5437, DUST + MTHL, DUST, COLOR_MTHR, 1, DUST + MTHR);
		dustAluminiumBronze = generalItem.addColorizedOreDictItem(5438, DUST + CUAL, DUST, COLOR_CUAL);
		dustCupronickel = generalItem.addColorizedOreDictItem(5439, DUST + CUNI, DUST, COLOR_CUNI);
		dustRiftishBronze = generalItem.addColorizedOreDictItem(5440, DUST + RBRZ, DUST, COLOR_RBRZ, 1);
		dustConstantan = generalItem.addColorizedOreDictItem(5441, DUST + CNST, DUST, COLOR_CNST);
		dustInvar = generalItem.addColorizedOreDictItem(5442, DUST + FENI, DUST, COLOR_FENI);
		dustElectrum = generalItem.addColorizedOreDictItem(5443, DUST + AUAG, DUST, COLOR_AUAG);
		dustWardenicMetal = generalItem.addColorizedOreDictItem(5444, DUST + WRDM, DUST, COLOR_WRDM);
		dustDullRedsolder = generalItem.addColorizedOreDictItem(5445, DUST + DRDS, DUST, COLOR_DRDS);
		dustRedsolder = generalItem.addColorizedOreDictItem(5446, DUST + RDSR, DUST, COLOR_RDSR);

		dustThaumicElectrum = generalItem.addColorizedOreDictItemWithEffect(5464, DUST + TELC, DUST, COLOR_TELC, 1);
		dustThaumicRiftishBronze = generalItem.addColorizedOreDictItem(5465, DUST + TRBR, DUST, COLOR_TRBR, 1);
		dustSteel = generalItem.addColorizedOreDictItem(5466, DUST + STEL, DUST, COLOR_STEL, 1);
		dustThaumicSteel = generalItem.addColorizedOreDictItem(5467, DUST + TSTL, DUST, COLOR_TSTL, 1);
		dustVoidbrass = generalItem.addColorizedOreDictItem(5468, DUST + VBRS, DUST, COLOR_VBRS, 1);
		dustVoidsteel = generalItem.addColorizedOreDictItem(5469, DUST + VSTL, DUST, COLOR_VSTL, 1);
		dustVoidtungsten = generalItem.addColorizedOreDictItemWithEffect(5470, DUST + VDWT, DUST, COLOR_VDWT, 2);
		dustVoidcupronickel = generalItem.addColorizedOreDictItem(5471, DUST + VCPN, DUST, COLOR_VCPN, 1);

		dustWardenicBronze = generalItem.addColorizedOreDictItem(5480, DUST + WBRZ, DUST, COLOR_WBRZ);
		dustWardenicSteel = generalItem.addColorizedOreDictItem(5481, DUST + WDST, DUST, COLOR_WDST, 1);
		dustWardenicRiftishBronze = generalItem.addColorizedOreDictItem(5482, DUST + WRBR, DUST, COLOR_WRBR, 1);
		dustWardenicComposite = generalItem.addColorizedOreDictItemWithEffect(5483, DUST + WCMP, DUST, COLOR_WCMP, 2);
		dustArcaneRedsolder = generalItem.addColorizedOreDictItem(5484, DUST + ARDS, DUST, COLOR_ARDS);
		dustRedbronze = generalItem.addColorizedOreDictItem(5485, DUST + RDBR, DUST, COLOR_RDBR);
		dustHardenedRedbronze = generalItem.addColorizedOreDictItem(5486, DUST + HRBR, DUST, COLOR_HRBR, 1);
		dustFluxsteel = generalItem.addColorizedOreDictItem(5487, DUST + FSTL, DUST, COLOR_FSTL, 1);
		dustFluxedTungsten = generalItem.addColorizedOreDictItem(5488, DUST + FLXW, DUST, COLOR_FLXW, 2);
		dustMagneoturgicComposite = generalItem.addColorizedOreDictItem(5489, DUST + MCMP, DUST, COLOR_MCMP, 2);
		dustFluxedComposite = generalItem.addColorizedOreDictItemWithEffect(5490, DUST + FCMP, DUST, COLOR_FLUX, 2);
		dustResonantFluxedComposite = generalItem.addColorizedOreDictItemWithEffect(5491, DUST + RCMP, DUST, COLOR_RCMP, 3);
		dustEmpoweredVoidbrass = generalItem.addColorizedOreDictItem(5492, DUST + EVBS, DUST, COLOR_EVBS, 1);
		dustCrimsonThaumium = generalItem.addColorizedOreDictItem(5493, DUST + CTHM, DUST, COLOR_CTHM, 1);
		dustOccultVoidtungsten = generalItem.addColorizedOreDictItemWithEffect(5494, DUST + OCVW, DUST, COLOR_OCVW, 2);

		dustPyrope = generalItem.addOreDictItem(5496, DUST + PYRP, 2);
		dustDioptase = generalItem.addOreDictItem(5497, DUST + DIOP, 2);
		dustJethrineSapphire = generalItem.addOreDictItem(5498, DUST + JSPH, 2);
		dustJethrinePyroptase = generalItem.addOreDictItem(5499, DUST + JPRT, 3);
		dustWardenicCrystal = generalItem.addOreDictItem(5500, DUST + WCRS, 2);
		dustActivatedWardenicCrystal = generalItem.addOreDictItem(5501, DUST + AWCR, 2);
		dustAwakenedWardenicCrystal = generalItem.addOreDictItem(5502, DUST + WWCR, 3);

		dustWardenicQuartz = generalItem.addColorizedOreDictItem(5504, DUST + WQRZ, DUST, COLOR_WQRZ, 1);
		dustInfusedQuartz = generalItem.addOreDictItem(5505, DUST + IQRZ, 1);
		dustRedquartz = generalItem.addColorizedOreDictItem(5506, DUST + RQRZ, DUST, COLOR_FLUX, 1, DUST + RQZT);

		dustLanthanides = generalItem.addColorizedOreDictItem(5512, DUST + YPO, DUST, COLOR_LNTH, 1, DUST + LNTH);
		dustXenotimeJunk = generalItem.addColorizedOreDictItem(5513, DUST + LAMM, DUST, COLOR_YPOJ, 1, DUST + YPOJ);
		dustIridosmium = generalItem.addColorizedOreDictItem(5514, DUST + IROS, DUST, COLOR_IROS, 2);

		dustThaumicBronze = generalItem.addColorizedOreDictItemWithEffect(5520, DUST + TBRZ, DUST, COLOR_TBRZ);
		dustOsmiumLutetium = generalItem.addColorizedOreDictItemWithEffect(5521, DUST + OSLU, DUST, COLOR_OSLU, 2);

		tinyCopper = generalItem.addColorizedOreDictItem(5600, TINY_DUST + CU, TINY_DUST, COLOR_CU);
		tinyZinc = generalItem.addColorizedOreDictItem(5601, TINY_DUST + ZN, TINY_DUST, COLOR_ZN);
		tinyTin = generalItem.addColorizedOreDictItem(5602, TINY_DUST + SN, TINY_DUST, COLOR_SN);
		tinyNickel = generalItem.addColorizedOreDictItem(5603, TINY_DUST + NI, TINY_DUST, COLOR_NI);
		tinySilver = generalItem.addColorizedOreDictItem(5604, TINY_DUST + AG, TINY_DUST, COLOR_AG);
		tinyLead = generalItem.addColorizedOreDictItem(5605, TINY_DUST + PB, TINY_DUST, COLOR_PB);
		tinyLutetium = generalItem.addColorizedOreDictItem(5606, TINY_DUST + LU, TINY_DUST, COLOR_LU, 1);
		tinyTungsten = generalItem.addColorizedOreDictItem(5607, TINY_DUST + W, TINY_DUST, COLOR_W, 1);
		tinyIridium = generalItem.addColorizedOreDictItem(5608, TINY_DUST + IR, TINY_DUST, COLOR_IR, 2);
		tinyBismuth = generalItem.addColorizedOreDictItem(5609, TINY_DUST + BI, TINY_DUST, COLOR_BI);
		tinyArsenic = generalItem.addColorizedOreDictItem(5610, TINY_DUST + AS, TINY_DUST, COLOR_AS);
		tinyAntimony = generalItem.addColorizedOreDictItem(5611, TINY_DUST + SB, TINY_DUST, COLOR_SB);
		tinyNeodymium = generalItem.addColorizedOreDictItem(5612, TINY_DUST + ND, TINY_DUST, COLOR_ND, 1);
		tinyOsmium = generalItem.addColorizedOreDictItem(5613, TINY_DUST + OS, TINY_DUST, COLOR_OS, 1);
		tinyPalladium = generalItem.addColorizedOreDictItem(5614, TINY_DUST + PD, TINY_DUST, COLOR_PD, 1);
		tinyAluminium = generalItem.addColorizedOreDictItem(5615, TINY_DUST + AL, TINY_DUST, COLOR_AL);

		tinyBrass = generalItem.addColorizedOreDictItem(5632, TINY_DUST + CUZN, TINY_DUST, COLOR_CUZN);
		tinyBronze = generalItem.addColorizedOreDictItem(5633, TINY_DUST + CUSN, TINY_DUST, COLOR_CUSN);
		tinyArsenicalBronze = generalItem.addColorizedOreDictItem(5634, TINY_DUST + CUAS, TINY_DUST, COLOR_CUAS);
		tinyAntimonialBronze = generalItem.addColorizedOreDictItem(5635, TINY_DUST + CUSB, TINY_DUST, COLOR_CUSB);
		tinyBismuthBronze = generalItem.addColorizedOreDictItem(5636, TINY_DUST + CUBI, TINY_DUST, COLOR_CUBI);
		tinyMithril = generalItem.addColorizedOreDictItem(5637, TINY_DUST + MTHL, TINY_DUST, COLOR_MTHR, 1, TINY_DUST + MTHR);
		tinyAluminiumBronze = generalItem.addColorizedOreDictItem(5638, TINY_DUST + CUAL, TINY_DUST, COLOR_CUAL);
		tinyCupronickel = generalItem.addColorizedOreDictItem(5639, TINY_DUST + CUNI, TINY_DUST, COLOR_CUNI);
		tinyRiftishBronze = generalItem.addColorizedOreDictItem(5640, TINY_DUST + RBRZ, TINY_DUST, COLOR_RBRZ, 1);
		tinyConstantan = generalItem.addColorizedOreDictItem(5641, TINY_DUST + CNST, TINY_DUST, COLOR_CNST);
		tinyInvar = generalItem.addColorizedOreDictItem(5642, TINY_DUST + FENI, TINY_DUST, COLOR_FENI);
		tinyElectrum = generalItem.addColorizedOreDictItem(5643, TINY_DUST + AUAG, TINY_DUST, COLOR_AUAG);
		tinyWardenicMetal = generalItem.addColorizedOreDictItem(5644, TINY_DUST + WRDM, TINY_DUST, COLOR_WRDM);
		tinyDullRedsolder = generalItem.addColorizedOreDictItem(5645, TINY_DUST + DRDS, TINY_DUST, COLOR_DRDS);
		tinyRedsolder = generalItem.addColorizedOreDictItem(5646, TINY_DUST + RDSR, TINY_DUST, COLOR_RDSR);

		tinyThaumicElectrum = generalItem.addColorizedOreDictItemWithEffect(5664, TINY_DUST + TELC, TINY_DUST, COLOR_TELC, 1);
		tinyThaumicRiftishBronze = generalItem.addColorizedOreDictItem(5665, TINY_DUST + TRBR, TINY_DUST, COLOR_TRBR, 1);
		tinySteel = generalItem.addColorizedOreDictItem(5666, TINY_DUST + STEL, TINY_DUST, COLOR_STEL, 1);
		tinyThaumicSteel = generalItem.addColorizedOreDictItem(5667, TINY_DUST + TSTL, TINY_DUST, COLOR_TSTL, 1);
		tinyVoidbrass = generalItem.addColorizedOreDictItem(5668, TINY_DUST + VBRS, TINY_DUST, COLOR_VBRS, 1);
		tinyVoidsteel = generalItem.addColorizedOreDictItem(5669, TINY_DUST + VSTL, TINY_DUST, COLOR_VSTL, 1);
		tinyVoidtungsten = generalItem.addColorizedOreDictItemWithEffect(5670, TINY_DUST + VDWT, TINY_DUST, COLOR_VDWT, 2);
		tinyVoidcupronickel = generalItem.addColorizedOreDictItem(5671, TINY_DUST + VCPN, TINY_DUST, COLOR_VCPN, 1);

		tinyWardenicBronze = generalItem.addColorizedOreDictItem(5680, TINY_DUST + WBRZ, TINY_DUST, COLOR_WBRZ);
		tinyWardenicSteel = generalItem.addColorizedOreDictItem(5681, TINY_DUST + WDST, TINY_DUST, COLOR_WDST, 1);
		tinyWardenicRiftishBronze = generalItem.addColorizedOreDictItem(5682, TINY_DUST + WRBR, TINY_DUST, COLOR_WRBR, 1);
		tinyWardenicComposite = generalItem.addColorizedOreDictItemWithEffect(5683, TINY_DUST + WCMP, TINY_DUST, COLOR_WCMP, 2);
		tinyArcaneRedsolder = generalItem.addColorizedOreDictItem(5684, TINY_DUST + ARDS, TINY_DUST, COLOR_ARDS);
		tinyRedbronze = generalItem.addColorizedOreDictItem(5685, TINY_DUST + RDBR, TINY_DUST, COLOR_RDBR);
		tinyHardenedRedbronze = generalItem.addColorizedOreDictItem(5686, TINY_DUST + HRBR, TINY_DUST, COLOR_HRBR, 1);
		tinyFluxsteel = generalItem.addColorizedOreDictItem(5687, TINY_DUST + FSTL, TINY_DUST, COLOR_FSTL, 1);
		tinyFluxedTungsten = generalItem.addColorizedOreDictItem(5688, TINY_DUST + FLXW, TINY_DUST, COLOR_FLXW, 2);
		tinyMagneoturgicComposite = generalItem.addColorizedOreDictItem(5689, TINY_DUST + MCMP, TINY_DUST, COLOR_MCMP, 2);
		tinyFluxedComposite = generalItem.addColorizedOreDictItemWithEffect(5690, TINY_DUST + FCMP, TINY_DUST, COLOR_FLUX, 2);
		tinyResonantFluxedComposite = generalItem.addColorizedOreDictItemWithEffect(5691, TINY_DUST + RCMP, TINY_DUST, COLOR_RCMP, 3);
		tinyEmpoweredVoidbrass = generalItem.addColorizedOreDictItem(5692, TINY_DUST + EVBS, TINY_DUST, COLOR_EVBS, 1);
		tinyCrimsonThaumium = generalItem.addColorizedOreDictItem(5693, TINY_DUST + CTHM, TINY_DUST, COLOR_CTHM, 1);
		tinyOccultVoidtungsten = generalItem.addColorizedOreDictItemWithEffect(5694, TINY_DUST + OCVW, TINY_DUST, COLOR_OCVW, 2);

		tinyPyrope = generalItem.addOreDictItem(5696, TINY_DUST + PYRP, 2);
		tinyDioptase = generalItem.addOreDictItem(5697, TINY_DUST + DIOP, 2);
		tinyJethrineSapphire = generalItem.addOreDictItem(5698, TINY_DUST + JSPH, 2);
		tinyJethrinePyroptase = generalItem.addOreDictItem(5699, TINY_DUST + JPRT, 3);
		tinyWardenicCrystal = generalItem.addOreDictItem(5700, TINY_DUST + WCRS, 2);
		tinyActivatedWardenicCrystal = generalItem.addOreDictItem(5701, TINY_DUST + AWCR, 2);
		tinyAwakenedWardenicCrystal = generalItem.addOreDictItem(5702, TINY_DUST + WWCR, 3);

		tinyWardenicQuartz = generalItem.addColorizedOreDictItem(5704, TINY_DUST + WQRZ, TINY_DUST, COLOR_WQRZ, 1);
		tinyInfusedQuartz = generalItem.addOreDictItem(5705, TINY_DUST + IQRZ, 1);
		tinyRedquartz = generalItem.addColorizedOreDictItem(5706, TINY_DUST + RQRZ, TINY_DUST, COLOR_FLUX, 1, TINY_DUST + RQZT);

		tinyLanthanides = generalItem.addColorizedOreDictItem(5712, TINY_DUST + YPO, TINY_DUST, COLOR_LNTH, 1, TINY_DUST + LNTH);
		tinyXenotimeJunk = generalItem.addColorizedOreDictItem(5713, TINY_DUST + LAMM, TINY_DUST, COLOR_YPOJ, 1, TINY_DUST + YPOJ);
		tinyIridosmium = generalItem.addColorizedOreDictItem(5714, TINY_DUST + IROS, TINY_DUST, COLOR_IROS, 2);

		tinyThaumicBronze = generalItem.addColorizedOreDictItemWithEffect(5720, TINY_DUST + TBRZ, TINY_DUST, COLOR_TBRZ);
		tinyOsmiumLutetium = generalItem.addColorizedOreDictItemWithEffect(5721, TINY_DUST + OSLU, TINY_DUST, COLOR_OSLU, 2);

		plateCopper = generalItem.addColorizedOreDictItem(5800, PLATE + CU, PLATE, COLOR_CU);
		plateZinc = generalItem.addColorizedOreDictItem(5801, PLATE + ZN, PLATE, COLOR_ZN);
		plateTin = generalItem.addColorizedOreDictItem(5802, PLATE + SN, PLATE, COLOR_SN);
		plateNickel = generalItem.addColorizedOreDictItem(5803, PLATE + NI, PLATE, COLOR_NI);
		plateSilver = generalItem.addColorizedOreDictItem(5804, PLATE + AG, PLATE, COLOR_AG);
		plateLead = generalItem.addColorizedOreDictItem(5805, PLATE + PB, PLATE, COLOR_PB);
		plateLutetium = generalItem.addColorizedOreDictItem(5806, PLATE + LU, PLATE, COLOR_LU, 1);
		plateTungsten = generalItem.addColorizedOreDictItem(5807, PLATE + W, PLATE, COLOR_W, 1);
		plateIridium = generalItem.addColorizedOreDictItem(5808, PLATE + IR, PLATE, COLOR_IR, 2);
		plateBismuth = generalItem.addColorizedOreDictItem(5809, PLATE + BI, PLATE, COLOR_BI);
		plateArsenic = generalItem.addColorizedOreDictItem(5810, PLATE + AS, PLATE, COLOR_AS);
		plateAntimony = generalItem.addColorizedOreDictItem(5811, PLATE + SB, PLATE, COLOR_SB);
		plateNeodymium = generalItem.addColorizedOreDictItem(5812, PLATE + ND, PLATE, COLOR_ND, 1);
		plateOsmium = generalItem.addColorizedOreDictItem(5813, PLATE + OS, PLATE, COLOR_OS, 1);
		platePalladium = generalItem.addColorizedOreDictItem(5814, PLATE + PD, PLATE, COLOR_PD, 1);
		plateAluminium = generalItem.addColorizedOreDictItem(5815, PLATE + AL, PLATE, COLOR_AL);

		plateBrass = generalItem.addColorizedOreDictItem(5832, PLATE + CUZN, PLATE, COLOR_CUZN);
		plateBronze = generalItem.addColorizedOreDictItem(5833, PLATE + CUSN, PLATE, COLOR_CUSN);
		plateArsenicalBronze = generalItem.addColorizedOreDictItem(5834, PLATE + CUAS, PLATE, COLOR_CUAS);
		plateAntimonialBronze = generalItem.addColorizedOreDictItem(5835, PLATE + CUSB, PLATE, COLOR_CUSB);
		plateBismuthBronze = generalItem.addColorizedOreDictItem(5836, PLATE + CUBI, PLATE, COLOR_CUBI);
		plateMithril = generalItem.addColorizedOreDictItem(5837, PLATE + MTHL, PLATE, COLOR_MTHR, 1, PLATE + MTHR);
		plateAluminiumBronze = generalItem.addColorizedOreDictItem(5838, PLATE + CUAL, PLATE, COLOR_CUAL);
		plateCupronickel = generalItem.addColorizedOreDictItem(5839, PLATE + CUNI, PLATE, COLOR_CUNI);
		plateRiftishBronze = generalItem.addColorizedOreDictItem(5840, PLATE + RBRZ, PLATE, COLOR_RBRZ, 1);
		plateConstantan = generalItem.addColorizedOreDictItem(5841, PLATE + CNST, PLATE, COLOR_CNST);
		plateInvar = generalItem.addColorizedOreDictItem(5842, PLATE + FENI, PLATE, COLOR_FENI);
		plateElectrum = generalItem.addColorizedOreDictItem(5843, PLATE + AUAG, PLATE, COLOR_AUAG);
		plateWardenicMetal = generalItem.addColorizedOreDictItem(5844, PLATE + WRDM, PLATE, COLOR_WRDM);
		plateDullRedsolder = generalItem.addColorizedOreDictItem(5845, PLATE + DRDS, PLATE, COLOR_DRDS);
		plateRedsolder = generalItem.addColorizedOreDictItem(5846, PLATE + RDSR, PLATE, COLOR_RDSR);

		plateThaumicElectrum = generalItem.addColorizedOreDictItemWithEffect(5864, PLATE + TELC, PLATE, COLOR_TELC, 1);
		plateThaumicRiftishBronze = generalItem.addColorizedOreDictItem(5865, PLATE + TRBR, PLATE, COLOR_TRBR, 1);
		plateSteel = generalItem.addColorizedOreDictItem(5866, PLATE + STEL, PLATE, COLOR_STEL, 1);
		plateThaumicSteel = generalItem.addColorizedOreDictItem(5867, PLATE + TSTL, PLATE, COLOR_TSTL, 1);
		plateVoidbrass = generalItem.addColorizedOreDictItem(5868, PLATE + VBRS, PLATE, COLOR_VBRS, 1);
		plateVoidsteel = generalItem.addColorizedOreDictItem(5869, PLATE + VSTL, PLATE, COLOR_VSTL, 1);
		plateVoidtungsten = generalItem.addColorizedOreDictItemWithEffect(5870, PLATE + VDWT, PLATE, COLOR_VDWT, 2);
		plateVoidcupronickel = generalItem.addColorizedOreDictItem(5871, PLATE + VCPN, PLATE, COLOR_VCPN, 1);

		plateWardenicBronze = generalItem.addColorizedOreDictItem(5880, PLATE + WBRZ, PLATE, COLOR_WBRZ);
		plateWardenicSteel = generalItem.addColorizedOreDictItem(5881, PLATE + WDST, PLATE, COLOR_WDST, 1);
		plateWardenicRiftishBronze = generalItem.addColorizedOreDictItem(5882, PLATE + WRBR, PLATE, COLOR_WRBR, 1);
		plateWardenicComposite = generalItem.addColorizedOreDictItemWithEffect(5883, PLATE + WCMP, PLATE, COLOR_WCMP, 2);
		plateArcaneRedsolder = generalItem.addColorizedOreDictItem(5884, PLATE + ARDS, PLATE, COLOR_ARDS);
		plateRedbronze = generalItem.addColorizedOreDictItem(5885, PLATE + RDBR, PLATE, COLOR_RDBR);
		plateHardenedRedbronze = generalItem.addColorizedOreDictItem(5886, PLATE + HRBR, PLATE, COLOR_HRBR, 1);
		plateFluxsteel = generalItem.addColorizedOreDictItem(5887, PLATE + FSTL, PLATE, COLOR_FSTL, 1);
		plateFluxedTungsten = generalItem.addColorizedOreDictItem(5888, PLATE + FLXW, PLATE, COLOR_FLXW, 2);
		plateMagneoturgicComposite = generalItem.addColorizedOreDictItem(5889, PLATE + MCMP, PLATE, COLOR_MCMP, 2);
		plateFluxedComposite = generalItem.addColorizedOreDictItemWithEffect(5890, PLATE + FCMP, PLATE, COLOR_FLUX, 2);
		plateResonantFluxedComposite = generalItem.addColorizedOreDictItemWithEffect(5891, PLATE + RCMP, PLATE, COLOR_RCMP, 3);
		plateEmpoweredVoidbrass = generalItem.addColorizedOreDictItem(5892, PLATE + EVBS, PLATE, COLOR_EVBS, 1);
		plateCrimsonThaumium = generalItem.addColorizedOreDictItem(5893, PLATE + CTHM, PLATE, COLOR_CTHM, 1);
		plateOccultVoidtungsten = generalItem.addColorizedOreDictItemWithEffect(5894, PLATE + OCVW, PLATE, COLOR_OCVW, 2);

		plateThaumicBronze = generalItem.addColorizedOreDictItemWithEffect(5920, PLATE + TBRZ, PLATE, COLOR_TBRZ);
		plateOsmiumLutetium = generalItem.addColorizedOreDictItemWithEffect(5921, PLATE + OSLU, PLATE, COLOR_OSLU, 2);

		rawBrass = generalItem.addOreDictItem(6032, INGOT + RAW + CUZN);
		rawBronze = generalItem.addOreDictItem(6033, INGOT + RAW + CUSN);
		rawArsenicalBronze = generalItem.addOreDictItem(6034, INGOT + RAW + CUAS);
		rawAntimonialBronze = generalItem.addOreDictItem(6035, INGOT + RAW + CUSB);
		rawBismuthBronze = generalItem.addOreDictItem(6036, INGOT + RAW + CUBI);
		rawMithril = generalItem.addOreDictItem(6037, INGOT + RAW + MTHL, 1, INGOT + RAW + MTHR);
		rawAluminiumBronze = generalItem.addOreDictItem(6038, INGOT + RAW + CUAL);
		rawCupronickel = generalItem.addOreDictItem(6039, INGOT + RAW + CUNI);
		rawRiftishBronze = generalItem.addOreDictItem(6040, INGOT + RAW + RBRZ);
		rawConstantan = generalItem.addOreDictItem(6041, INGOT + RAW + CNST);
		rawInvar = generalItem.addOreDictItem(6042, INGOT + RAW + FENI);
		rawElectrum = generalItem.addOreDictItem(6043, INGOT + RAW + AUAG);
		rawWardenicMetal = generalItem.addOreDictItem(6044, INGOT + RAW + WRDM);
		rawDullRedsolder = generalItem.addOreDictItem(6045, INGOT + RAW + DRDS);
		rawRedsolder = generalItem.addOreDictItem(6046, INGOT + RAW + RDSR);

		rawWardenicComposite = generalItem.addOreDictItem(6083, INGOT + RAW + WCMP, 2);
		rawArcaneRedsolder = generalItem.addOreDictItem(6084, INGOT + RAW + ARDS);
		rawMagneoturgicComposite = generalItem.addOreDictItem(6089, INGOT + RAW + MCMP, 2);

		blendJethrinePyrotase = generalItem.addOreDictItem(6099, BLEND + JPRT, 2, BLEND_ORE + JPRT);

		rawThaumicBronze = generalItem.addOreDictItem(6120, INGOT + RAW + TBRZ);
		rawOsLu = generalItem.addOreDictItem(6121, INGOT + RAW + OSLU, 2);

		smeltedWardenicComposite = generalItem.addColorizedOreDictItemWithEffect(6283, INGOT + SMELTED + WCMP, INGOT, COLOR_WCMP, 2);
		smeltedMagneoturgicComposite = generalItem.addColorizedOreDictItem(6289, INGOT + SMELTED + MCMP, INGOT, COLOR_MCMP, 2);
		smeltedFluxedComposite = generalItem.addColorizedOreDictItemWithEffect(6290, INGOT + SMELTED + FCMP, INGOT, COLOR_FLUX, 2);
		smeltedResonantFluxedComposite = generalItem.addColorizedOreDictItemWithEffect(6291, INGOT + SMELTED + RCMP, INGOT, COLOR_RCMP, 2);

		coatedThaumicBronze = generalItem.addOreDictItem(6320, INGOT + COATED + TBRZ);
		coatedOsLu = generalItem.addOreDictItem(6321, INGOT + COATED + OSLU, 2);

		firedThaumicBronze = generalItem.addOreDictItem(6520, INGOT + FIRED + TBRZ);
		firedOsLu = generalItem.addOreDictItem(6521, INGOT + FIRED + OSLU, 2);
	}

	public static void loadOtherItems() {
		clusterZinc = generalItem.addOreDictItem(7001, new ItemEntryColorizedOverlay(CLUSTER + ZN).setTextureOverlay(CLUSTER_OVER).setColorData(CLUSTER, CLUSTER + ZN, COLOR_ORE_ZNS), CLUSTER + ZN);
		clusterAluminium = generalItem.addOreDictItem(7002, new ItemEntryColorizedOverlay(CLUSTER + AL).setTextureOverlay(CLUSTER_OVER).setColorData(CLUSTER, CLUSTER + AL, COLOR_AL), CLUSTER + AL);
		clusterNickel = generalItem.addOreDictItem(7003, new ItemEntryColorizedOverlay(CLUSTER + NI).setTextureOverlay(CLUSTER_OVER).setColorData(CLUSTER, CLUSTER + NI, COLOR_ORE_NIS), CLUSTER + NI);
		clusterPlatinum = generalItem.addOreDictItem(7004, new ItemEntryColorizedOverlay(CLUSTER + PT).setTextureOverlay(CLUSTER_OVER).setColorData(CLUSTER, CLUSTER + PT, COLOR_PT), CLUSTER + PT);
		clusterXenotime = generalItem.addOreDictItem(7006, new ItemEntryColorizedOverlay(CLUSTER + YPO).setTextureOverlay(CLUSTER_OVER).setColorData(CLUSTER, CLUSTER + YPO, COLOR_ORE_YPO), CLUSTER + YPO);
		clusterTungsten = generalItem.addOreDictItem(7007, new ItemEntryColorizedOverlay(CLUSTER + W).setTextureOverlay(CLUSTER_OVER).setColorData(CLUSTER, CLUSTER + W, COLOR_ORE_WFE), CLUSTER + W);
		clusterIridosmium = generalItem.addOreDictItem(7008, new ItemEntryColorizedOverlay(CLUSTER + IROS).setTextureOverlay(CLUSTER_OVER).setColorData(CLUSTER, CLUSTER + IROS, COLOR_IROS), CLUSTER + IROS);
		clusterBismuth = generalItem.addOreDictItem(7009, new ItemEntryColorizedOverlay(CLUSTER + BI).setTextureOverlay(CLUSTER_OVER).setColorData(CLUSTER, CLUSTER + BI, COLOR_ORE_BIS), CLUSTER + BI);
		clusterTennantite = generalItem.addOreDictItem(7010, new ItemEntryColorizedOverlay(CLUSTER + CAS).setTextureOverlay(CLUSTER_OVER).setColorData(CLUSTER, CLUSTER + CAS, COLOR_ORE_CAS), CLUSTER + CAS);
		clusterTetrahedrite = generalItem.addOreDictItem(7011, new ItemEntryColorizedOverlay(CLUSTER + CSB).setTextureOverlay(CLUSTER_OVER).setColorData(CLUSTER, CLUSTER + CSB, COLOR_ORE_CSB), CLUSTER + CSB);

		carbonSlag = generalItem.addOreDictItem(7100, SLAG + C);
		ceramicSlag = generalItem.addOreDictItem(7101, SLAG + CRMC);
		thaumicSlag = generalItem.addOreDictItem(7102, SLAG + THMC);
		fluonicSlag = generalItem.addOreDictItem(7103, SLAG + FLNC);

		dustAer = generalItem.addColorizedOreDictItem(9400, DUST + AER, MAGIC_DUST, COLOR_AER);
		dustIgnis = generalItem.addColorizedOreDictItem(9401, DUST + IGNIS, MAGIC_DUST, COLOR_IGNIS);
		dustAqua = generalItem.addColorizedOreDictItem(9402, DUST + AQUA, MAGIC_DUST, COLOR_AQUA);
		dustTerra = generalItem.addColorizedOreDictItem(9403, DUST + TERRA, MAGIC_DUST, COLOR_TERRA);
		dustOrdo = generalItem.addColorizedOreDictItem(9404, DUST + ORDO, MAGIC_DUST, COLOR_ORDO);
		dustPerditio = generalItem.addColorizedOreDictItem(9405, DUST + PERDITIO, MAGIC_DUST, COLOR_PERDITIO);
		dustIron = generalItem.addColorizedOreDictItem(9406, DUST + FE, DUST, COLOR_FE);
		dustGold = generalItem.addColorizedOreDictItem(9407, DUST + AU, DUST, COLOR_AU);
		dustThaumium = generalItem.addColorizedOreDictItem(9408, DUST + THMM, DUST, COLOR_THMM);
		dustVoidmetal = generalItem.addColorizedOreDictItem(9409, DUST + VMTL, DUST, COLOR_VMTL);
		dustSulfur = generalItem.addColorizedOreDictItem(9410, DUST + S, DUST, COLOR_S);
		dustSaltpeter = generalItem.addColorizedOreDictItem(9411, DUST + KNO, DUST, COLOR_KNO);
		//Salis Mundus
		dustPrimalEssence = generalItem.addOreDictItem(9413, DUST + PRES);
		//Redstone

		dustWardenicBinder = generalItem.addOreDictItem(9440, DUST + WDBC);

		dustRedstoneReduced = generalItem.addColorizedOreDictItem(9460, DUST + RSRD, DUST, COLOR_RSRD, 1);
		dustRedstonePurified = generalItem.addColorizedOreDictItem(9461, DUST + RSPR, DUST, COLOR_RSPR, 1);
		dustRedstoneEnriched = generalItem.addColorizedOreDictItem(9462, DUST + RSNR, DUST, COLOR_RSNR, 1);
		dustContainmentGlass = generalItem.addColorizedOreDictItem(9463, DUST + CNTG, DUST, COLOR_CNTG);
		dustTreatingCompound = generalItem.addColorizedOreDictItem(9464, DUST + MGTC, MAGIC_DUST, COLOR_MGTC, 1);
		dustPiezomagneticCompound = generalItem.addColorizedOreDictItem(9465, DUST + PMGC, MAGIC_DUST, COLOR_PMGC, 2);
		dustGeomagneticCompound = generalItem.addColorizedOreDictItem(9466, DUST + GMGC, MAGIC_DUST, COLOR_GMGC, 2);
		dustConversionCompound = generalItem.addColorizedOreDictItem(9467, DUST + MGCC, MAGIC_DUST, COLOR_MGCC, 2);
		dustContainmentCompound = generalItem.addOreDictItem(9468, new ItemEntryColorizedOverlay(DUST + RSCC, 3).setTextureOverlay(DUST + RSCC + OVER).setColorData(MAGIC_DUST, DUST + RSCC, COLOR_RSCC), DUST + RSCC);

		tinyAer = generalItem.addColorizedOreDictItem(9600, TINY_DUST + AER, TINY_MAGIC_DUST, COLOR_AER);
		tinyIgnis = generalItem.addColorizedOreDictItem(9601, TINY_DUST + IGNIS, TINY_MAGIC_DUST, COLOR_IGNIS);
		tinyAqua = generalItem.addColorizedOreDictItem(9602, TINY_DUST + AQUA, TINY_MAGIC_DUST, COLOR_AQUA);
		tinyTerra = generalItem.addColorizedOreDictItem(9603, TINY_DUST + TERRA, TINY_MAGIC_DUST, COLOR_TERRA);
		tinyOrdo = generalItem.addColorizedOreDictItem(9604, TINY_DUST + ORDO, TINY_MAGIC_DUST, COLOR_ORDO);
		tinyPerditio = generalItem.addColorizedOreDictItem(9605, TINY_DUST + PERDITIO, TINY_MAGIC_DUST, COLOR_PERDITIO);
		tinyIron = generalItem.addColorizedOreDictItem(9606, TINY_DUST + FE, TINY_DUST, COLOR_FE);
		tinyGold = generalItem.addColorizedOreDictItem(9607, TINY_DUST + AU, TINY_DUST, COLOR_AU);
		tinyThaumium = generalItem.addColorizedOreDictItem(9608, TINY_DUST + THMM, TINY_DUST, COLOR_THMM);
		tinyVoidmetal = generalItem.addColorizedOreDictItem(9609, TINY_DUST + VMTL, TINY_DUST, COLOR_VMTL);
		tinySulfur = generalItem.addColorizedOreDictItem(9610, TINY_DUST + S, TINY_DUST, COLOR_S);
		tinySaltpeter = generalItem.addColorizedOreDictItem(9611, TINY_DUST + KNO, TINY_DUST, COLOR_KNO);
		tinySalisMundus = generalItem.addOreDictItem(9612, TINY_DUST + SALIS);
		tinyPrimalEssence = generalItem.addOreDictItem(9613, TINY_DUST + PRES);
		tinyRedstone = generalItem.addColorizedOreDictItem(9614, TINY_DUST + RS, TINY_DUST, COLOR_RS);

		tinyWardenicCompound = generalItem.addOreDictItem(9640, TINY_DUST + WDBC);

		tinyRedstoneReduced = generalItem.addColorizedOreDictItem(9660, TINY_DUST + RSRD, TINY_DUST, COLOR_RSRD, 1);
		tinyRedstonePurified = generalItem.addColorizedOreDictItem(9661, TINY_DUST + RSPR, TINY_DUST, COLOR_RSPR, 1);
		tinyRedstoneEnriched = generalItem.addColorizedOreDictItem(9662, TINY_DUST + RSNR, TINY_DUST, COLOR_RSNR, 1);
		tinyContainmentGlass = generalItem.addColorizedOreDictItem(9663, TINY_DUST + CNTG, TINY_DUST, COLOR_CNTG);
		tinyTreatingCompound = generalItem.addColorizedOreDictItem(9664, TINY_DUST + MGTC, TINY_MAGIC_DUST, COLOR_MGTC, 1);
		tinyPiezomagneticCompound = generalItem.addColorizedOreDictItem(9665, TINY_DUST + PMGC, TINY_MAGIC_DUST, COLOR_PMGC, 2);
		tinyGeomagneticCompound = generalItem.addColorizedOreDictItem(9666, TINY_DUST + GMGC, TINY_MAGIC_DUST, COLOR_GMGC, 2);
		tinyConversionCompound = generalItem.addColorizedOreDictItem(9667, TINY_DUST + MGCC, TINY_MAGIC_DUST, COLOR_MGCC, 2);
		tinyContainmentCompound = generalItem.addOreDictItem(9668, new ItemEntryColorizedOverlay(TINY_DUST + RSCC, 3).setTextureOverlay(TINY_DUST + RSCC + OVER).setColorData(TINY_MAGIC_DUST, TINY_DUST + RSCC, COLOR_RSCC), TINY_DUST + RSCC);

		aluDenseTemp = generalItem.addItem(30000, _ALUD);
	}

	public static void loadBaubles() {
		amuletWarden = new ItemStack(thaumicBauble, 1, 0);
		ringLove = new ItemStack(thaumicBauble, 1, 1);
		pendantPrimal = new ItemStack(thaumicBauble, 1, 2);
	}

	public static void addTooltips() {
		((ItemThaumRev) generalItem).unobtainable = Arrays.asList(21, 32, 50, 52, 953, 954, 955, 956, 957, 1140, 5006, 5008, 5010, 5011, 5012, 5069, 5070, 5071, 5085, 5086, 5087, 5088, 5089, 5090, 5091, 5092, 5093, 5094, 5099, 5102, 5113, 5121, 5206, 5208, 5210, 5211, 5212, 5269, 5270, 5271, 5285, 5286, 5287, 5288, 5289, 5290, 5291, 5292, 5293, 5294, 5299, 5302, 5313, 5321, 5406, 5408, 5410, 5411, 5412, 5469, 5470, 5471, 5485, 5486, 5487, 5488, 5489, 5490, 5491, 5492, 5493, 5494, 5499, 5502, 5513, 5521, 5606, 5608, 5610, 5611, 5612, 5669, 5670, 5671, 5685, 5686, 5687, 5688, 5689, 5690, 5691, 5692, 5693, 5694, 5699, 5702, 5713, 5721, 5806, 5808, 5810, 5811, 5812, 5869, 5870, 5871, 5885, 5886, 5887, 5888, 5889, 5890, 5891, 5892, 5893, 5894, 5921, 6083, 6089, 6099, 6121, 6321, 6521, 7100, 7101, 7102, 7103, 9461, 9462, 9463, 9464, 9465, 9466, 9467, 9468, 9661, 9662, 9663, 9664, 9665, 9666, 9667, 9668);
		((ItemThaumRev) generalItem).unobtainableMod = Arrays.asList(5007, 5013, 5015, 5038, 5040, 5066, 5067, 5081, 5082, 5083, 5114, 5207, 5213, 5215, 5238, 5240, 5266, 5267, 5281, 5282, 5283, 5314, 5407, 5413, 5415, 5438, 5440, 5466, 5467, 5481, 5482, 5483, 5514, 5607, 5613, 5615, 5638, 5640, 5666, 5667, 5681, 5682, 5683, 5714, 5807, 5813, 5815, 5838, 5840, 5866, 5867, 5881, 5882, 5883, 6038, 6040, 9410, 9411, 9610, 9611);
		((ItemThaumRev) generalItem).noUses = Arrays.asList(5, 6, 12, 30, 31, 40, 41, 42, 54, 55, 950, 6283, 6289, 6290, 6291);
	}

	public static void aluminiumArc() {
		registerOreDict(new ItemStack(Items.clay_ball), ITEM + CLAY);
		registerOreDict(new ItemStack(Items.glass_bottle), BOTLE);
		registerOreDict(new ItemStack(Items.quartz), GEM + QRTZ);
		registerOreDict(itemAlumentum, ALMNT);
		registerOreDict(itemNitor, NITOR);
		registerOreDict(dustSalisMundus, salisMundus);
		registerOreDict(itemVoidSeed, VSEED);
		registerOreDict(itemEnchantedFabric, enchSilk);
		registerOreDict(itemQuicksilverDrop, nHg);
		registerOreDict(itemShardBalanced, shardBalanced);
	}

	public static void loadRecipes() {
		RecipeHelper.addSmelting(planksSilverwood, hardenedSilverwood, 0.5F);

		//recipeQuartzChiseled = RecipeHelper.addShapedRecipe(blockWardenicQuartzChiseled, "X", "X", 'X', "slabWardenicQuartz");
		//recipeQuartzPillar = RecipeHelper.addShapedRecipe(ItemHelper.cloneStack(blockWardenicQuartzPillar, 2), "X", "X", 'X', "blockWardenicQuartz");

		//TODO: v0.0.2
		/*recipeQuartzResetChiseled = RecipeHelper.addShapelessRecipe(blockWardenicQuartz, blockWardenicQuartzChiseled);
		recipeQuartzResetPillar = RecipeHelper.addShapelessRecipe(blockWardenicQuartz, blockWardenicQuartzPillar);

		recipeWardsidianSlab = RecipeHelper.addSlabRecipe(slabWardenicObsidian, "blockWardenicObsidian");
		recipeEldritchSlab = RecipeHelper.addSlabRecipe(slabEldritch, "blockEldritchStone");
		recipeQuartzSlab = RecipeHelper.addSlabRecipe(slabWardenicQuartz, "blockWardenicQuartz");

		recipeWardsidianDeslab = RecipeHelper.addDeslabingRecipe(wardenicObsidian, "slabWardenicObsidian");
		recipeEldritchDeslab = RecipeHelper.addDeslabingRecipe(eldritchStone, "slabEldritchStone");
		recipeQuartzDeslab = RecipeHelper.addDeslabingRecipe(gemWardenicQuartz, "slabWardenicQuartz");

		recipeQuartzStair = RecipeHelper.addStairRecipe(stairsWardenicQuartz, "blockWardenicQuartz");
		recipeQuartzDestair = RecipeHelper.addDestairRecipe(blockWardenicQuartz, stairsWardenicQuartz);*/

		recipeGreatwoodShaft = addShapedRecipe(cloneStack(shaftGreatwood, 6), "x  ", " x ", "  x", 'x', planksGreatwood);

		recipeCottonFiber = addShapelessRecipe(itemCottonFiber, itemCotton);
		recipeCottonFabric = addStorageRecipe(itemCottonFabric, itemCottonFiber);

		addSmelting(coatedThaumicBronze, firedThaumicBronze, 2.0F);

		/*addRefractorySmelting(coatedOsLu, firedOsLu, 2.0F);

		addRefractoryOreSmelting(oreWolframite, ingotTungsten, 1.0F, ingotFe);
		addRefractoryOreSmelting(oreIridosmium, ingotIridosmium, 1.0F, ingotFe);

		addRefractorySmelting(dustTungsten, ingotTungsten);
		addRefractorySmelting(dustOsmium, ingotOsmium);
		addRefractorySmelting(dustIridium, ingotIridium);
		addRefractorySmelting(dustOsmiumLutetium, ingotOsmiumLutetium, 1.5F);
		addRefractorySmelting(dustVoidtungsten, ingotVoidtungsten);*/

		if (LoadedHelper.isThermalExpansionLoaded) {
			addPulverizerRecycleRecipe(gemWardenicQuartz, blockWardenicQuartz, 4);
			addPulverizerRecycleRecipe(gemWardenicQuartz, blockWardenicQuartzChiseled, 4);
			addPulverizerRecycleRecipe(gemWardenicQuartz, blockWardenicQuartzPillar, 4);
			//addPulverizerRecycleRecipe(gemWardenicQuartz, slabWardenicQuartz, 2);
			//addPulverizerRecycleRecipe(gemWardenicQuartz, stairsWardenicQuartz, 6);
		}

		//Temporary
		recipeAluDenseTemp = addShapelessRecipe(aluDenseTemp, ALMNT, ALMNT, ALMNT, ALMNT);
	}

	public static void loadOreRecipes() {
		addSmelting(oreChalcocite, ingotCopper, 0.85F);
		addSmelting(oreSphalerite, ingotZinc, 0.95F);
		addSmelting(oreCassiterite, ingotTin, 0.975F);
		addSmelting(oreMillerite, ingotNickel, 1.2F);
		addSmelting(oreNativeSilver, ingotSilver, 1.5F);
		addSmelting(oreGalena, ingotLead, 1.0F);
		addSmelting(oreXenotime, ingotLanthanides, 1.0F); //Rare Earths mock your primitive furnace-based attempts at separating them, but will smelt.
		//Tungsten laughs at your mundane smelting
		//Refractory Alloys mock your simple furnace
		addSmelting(oreBismuthinite, ingotBismuth, 1.15F);
		addSmelting(oreTennantite, ingotArsenicalBronze, 1.35F);
		addSmelting(oreTetrahedrite, ingotAntimonialBronze, 1.35F);
		addSmelting(orePyrope, gemPyrope, 1.0F);
		addSmelting(oreDioptase, gemDioptase, 1.0F);
		addSmelting(oreJethrineSapphire, gemJethrineSapphire, 1.0F);

		addSmelting(orePoorChalcocite, cloneStack(nuggetCopper, 2), 0.085F);
		addSmelting(orePoorSphalerite, cloneStack(nuggetZinc, 2), 0.095F);
		addSmelting(orePoorCassiterite, cloneStack(nuggetTin, 2), 0.0975F);
		addSmelting(orePoorMillerite, cloneStack(nuggetNickel, 2), 0.12F);
		addSmelting(orePoorNativeSilver, cloneStack(nuggetSilver, 2), 0.15F);
		addSmelting(orePoorGalena, cloneStack(nuggetLead, 2), 0.1F);
		addSmelting(orePoorXenotime, cloneStack(nuggetLanthanides, 2), 0.1F); //Rare Earths mock your primitive furnace-based attempts at separating them, but will smelt.
		//Tungsten laughs at your mundane smelting
		//Refractory Alloys mock your simple furnace
		addSmelting(orePoorBismuthinite, cloneStack(nuggetBismuth, 2), 0.115F);
		addSmelting(orePoorTennantite, cloneStack(nuggetArsenicalBronze, 2), 0.135F);
		addSmelting(orePoorTetrahedrite, cloneStack(nuggetAntimonialBronze, 2), 0.135F);

		addSmelting(oreGravelChalcocite, ingotCopper, 0.85F);
		addSmelting(oreGravelSphalerite, ingotZinc, 0.95F);
		addSmelting(oreGravelCassiterite, ingotTin, 0.975F);
		addSmelting(oreGravelMillerite, ingotNickel, 1.2F);
		addSmelting(oreGravelNativeSilver, ingotSilver, 1.5F);
		addSmelting(oreGravelGalena, ingotLead, 1.0F);
		addSmelting(oreGravelXenotime, ingotLanthanides, 1.0F); //Rare Earths mock your primitive furnace-based attempts at separating them, but will smelt.
		//Tungsten laughs at your mundane smelting
		//Refractory Alloys mock your simple furnace
		addSmelting(oreGravelBismuthinite, ingotBismuth, 1.15F);
		addSmelting(oreGravelTennantite, ingotArsenicalBronze, 1.35F);
		addSmelting(oreGravelTetrahedrite, ingotAntimonialBronze, 1.35F);

		if (LoadedHelper.isThermalExpansionLoaded) {
			ThaumicRevelations.logger.info("Loading Thermal Expansion Compatibility Ore Recipes");

			addShapelessRecipe(ingotLanthanides, ORE + YPO, "dustPyrotheum");
			addShapelessRecipe(ingotArsenicalBronze, ORE + CAS, "dustPyrotheum");
			addShapelessRecipe(ingotAntimonialBronze, ORE + CSB, "dustPyrotheum");

			addPulverizerOreRecipe(oreSphalerite, dustZinc, dustLeadTF);
			addPulverizerOreRecipe(oreXenotime, dustLanthanides, dustArsenic);
			addPulverizerOreRecipe(oreWolframite, dustTungsten, dustIronTF);
			addPulverizerRecipe(4800, oreIridosmium, cloneStack(dustIridosmium, multPulvDefault), dustIronTF, 15);
			addPulverizerOreRecipe(oreBismuthinite, dustBismuth, dustLeadTF);
			addPulverizerOreRecipe(oreTennantite, dustArsenicalBronze, dustSilverTF);
			addPulverizerRecipe(4000, oreTetrahedrite, cloneStack(dustAntimonialBronze, multPulvDefault), itemQuicksilverDrop);
			addPulverizerRecipe(4000, oreDioptase, cloneStack(gemDioptase, multPulvDefault));
			addPulverizerRecipe(4000, orePyrope, cloneStack(gemPyrope, multPulvDefault));
			addPulverizerRecipe(4000, oreJethrineSapphire, cloneStack(gemJethrineSapphire, multPulvDefault));

			addInductionOreRecipes(ZN, ingotLeadTF);
			addInductionOreRecipes(YPO, LNTH, dustArsenic);
			addInductionOreRecipes(BI, ingotLeadTF);
			addInductionOreRecipes(CAS, CUAS, ingotSilverTF);
			addInductionOreRecipes(CSB, CUSB, quicksilver);

			addInductionSmelterRecipe(12000, oreWolframite, dustPyrotheum, cloneStack(ingotTungsten, multSmeltDefault), itemSlagRich, 20);
			addInductionSmelterRecipe(16000, oreWolframite, itemCinnabar, cloneStack(ingotTungsten, multSmeltSpecial), new ItemStack(Items.iron_ingot), 125);
			addInductionSmelterRecipe(12000, oreIridosmium, dustPyrotheum, cloneStack(ingotIridosmium, multSmeltDefault), itemSlagRich, 25);
			addInductionSmelterRecipe(16000, oreIridosmium, itemCinnabar, cloneStack(ingotIridosmium, multSmeltSpecial), new ItemStack(Items.iron_ingot), 150);

			addShapelessRecipe(cloneStack(nuggetCopper, 3), ORE + POOR + CU, "dustPyrotheum");
			addShapelessRecipe(cloneStack(nuggetZinc, 3), ORE + POOR + ZN, "dustPyrotheum");
			addShapelessRecipe(cloneStack(nuggetTin, 3), ORE + POOR + SN, "dustPyrotheum");
			addShapelessRecipe(cloneStack(nuggetNickel, 3), ORE + POOR + NI, "dustPyrotheum");
			addShapelessRecipe(cloneStack(nuggetSilver, 3), ORE + POOR + AG, "dustPyrotheum");
			addShapelessRecipe(cloneStack(nuggetLead, 3), ORE + POOR + PB, "dustPyrotheum");
			addShapelessRecipe(cloneStack(nuggetLanthanides, 3), ORE + POOR + YPO, "dustPyrotheum");
			addShapelessRecipe(cloneStack(nuggetTungsten, 3), ORE + POOR + WFE, "dustPyrotheum");
			addShapelessRecipe(cloneStack(nuggetIridosmium, 3), ORE + POOR + IROS, "dustPyrotheum");
			addShapelessRecipe(cloneStack(nuggetBismuth, 3), ORE + POOR + BI, "dustPyrotheum");
			addShapelessRecipe(cloneStack(nuggetArsenicalBronze, 3), ORE + POOR + CAS, "dustPyrotheum");
			addShapelessRecipe(cloneStack(nuggetAntimonialBronze, 3), ORE + POOR + CSB, "dustPyrotheum");

			addPulverizerPoorOreRecipe(orePoorChalcocite, tinyCopper, tinyGold);
			addPulverizerPoorOreRecipe(orePoorSphalerite, tinyZinc, tinyLead);
			addPulverizerPoorOreRecipe(orePoorCassiterite, tinyTin, tinyIron);
			addPulverizerPoorOreRecipe(orePoorMillerite, tinyNickel, tinyPalladium);
			addPulverizerPoorOreRecipe(orePoorNativeSilver, tinySilver, tinyLead);
			addPulverizerPoorOreRecipe(orePoorGalena, tinyLead, tinySilver);
			addPulverizerPoorOreRecipe(orePoorXenotime, tinyLanthanides, tinyArsenic);
			addPulverizerPoorOreRecipe(orePoorWolframite, tinyTungsten, tinyIron);
			addPulverizerRecipe(4000, orePoorIridosmium, cloneStack(tinyIridosmium, multPulvDefault * 3), tinyIron, 20);
			addPulverizerPoorOreRecipe(orePoorBismuthinite, tinyBismuth, tinyLead);
			addPulverizerPoorOreRecipe(orePoorTennantite, tinyArsenicalBronze, tinySilver);
			addPulverizerPoorOreRecipe(orePoorTetrahedrite, cloneStack(tinyAntimonialBronze, multPulvDefault * 3), itemQuicksilverDrop);

			addInductionPoorOreRecipes(CU, new ItemStack(Items.gold_nugget));
			addInductionPoorOreRecipes(ZN, ingotLead);
			addInductionPoorOreRecipes(SN, nuggetIronTF);
			addInductionPoorOreRecipes(NI, nuggetPalladium);
			addInductionPoorOreRecipes(AG, nuggetLeadTF);
			addInductionPoorOreRecipes(PB, nuggetSilverTF);
			addInductionPoorOreRecipes(YPO, LNTH, dustArsenic);
			addInductionPoorOreRecipes(BI, nuggetLeadTF);
			addInductionPoorOreRecipes(CAS, CUAS, nuggetSilverTF);
			addInductionPoorOreRecipes(CSB, CUSB, itemQuicksilverDrop);

			addInductionSmelterRecipe(8000, orePoorWolframite, dustPyrotheum, cloneStack(nuggetTungsten, multSmeltDefault * 3), itemSlagRich, 25);
			addInductionSmelterRecipe(12000, orePoorWolframite, itemCinnabar, cloneStack(nuggetTungsten, (multSmeltSpecial * 3) + 1), nuggetIronTF, 150);
			addInductionSmelterRecipe(8000, orePoorIridosmium, dustPyrotheum, cloneStack(nuggetIridosmium, multSmeltDefault * 3), itemSlagRich, 30);
			addInductionSmelterRecipe(12000, orePoorIridosmium, itemCinnabar, cloneStack(nuggetIridosmium, (multSmeltSpecial * 3) + 1), cloneStack(nuggetIronTF, 2));
		}
	}

	public static void loadElementalMetalRecipes() {
		addStorageRecipe(blockCopper, INGOT + CU);
		addStorageRecipe(ingotCopper, NUGGET + CU);
		addReverseStorageRecipe(ingotCopper, BLOCK + CU);
		addReverseStorageRecipe(nuggetCopper, INGOT + CU);
		addStorageRecipe(dustCopper, TINY_DUST + CU);
		addReverseStorageRecipe(tinyCopper, DUST + CU);
		addSmelting(dustCopper, ingotCopper);
		addSmelting(plateCopper, ingotCopper);
		addArcaneCraftingRecipe(keyAlumentum, plateCopper, monorder, "A", "I", 'I', INGOT + CU, 'A', ALMNT);
		addGrindingRecipes(ingotCopper, dustCopper, false);
		addGrindingRecipes(plateCopper, dustCopper, true);

		addStorageRecipe(blockZinc, INGOT + ZN);
		addStorageRecipe(ingotZinc, NUGGET + ZN);
		addReverseStorageRecipe(ingotZinc, BLOCK + ZN);
		addReverseStorageRecipe(nuggetZinc, INGOT + ZN);
		addStorageRecipe(dustZinc, TINY_DUST + ZN);
		addReverseStorageRecipe(tinyZinc, DUST + ZN);
		addSmelting(dustZinc, ingotZinc);
		addSmelting(plateZinc, ingotZinc);
		addArcaneCraftingRecipe(keyAlumentum, plateZinc, monorder, "A", "I", 'I', INGOT + ZN, 'A', ALMNT);
		addGrindingRecipes(ingotZinc, dustZinc, false);
		addGrindingRecipes(plateZinc, dustZinc, true);

		addStorageRecipe(blockTin, INGOT + SN);
		addStorageRecipe(ingotTin, NUGGET + SN);
		addReverseStorageRecipe(ingotTin, BLOCK + SN);
		addReverseStorageRecipe(nuggetTin, INGOT + SN);
		addStorageRecipe(dustTin, TINY_DUST + SN);
		addReverseStorageRecipe(tinyTin, DUST + SN);
		addSmelting(dustTin, ingotTin);
		addSmelting(plateTin, ingotTin);
		addArcaneCraftingRecipe(keyAlumentum, plateTin, monorder, "A", "I", 'I', INGOT + SN, 'A', ALMNT);
		addGrindingRecipes(ingotTin, dustTin, false);
		addGrindingRecipes(plateTin, dustTin, true);

		addStorageRecipe(blockNickel, INGOT + NI);
		addStorageRecipe(ingotNickel, NUGGET + NI);
		addReverseStorageRecipe(ingotNickel, BLOCK + NI);
		addReverseStorageRecipe(nuggetNickel, INGOT + NI);
		addStorageRecipe(dustNickel, TINY_DUST + NI);
		addReverseStorageRecipe(tinyNickel, DUST + NI);
		addSmelting(dustNickel, ingotNickel);
		addSmelting(plateNickel, ingotNickel);
		addArcaneCraftingRecipe(keyAlumentum, plateNickel, monorder, "A", "I", 'I', INGOT + NI, 'A', ALMNT);
		addGrindingRecipes(ingotNickel, dustNickel, false);
		addGrindingRecipes(plateNickel, dustNickel, true);

		addStorageRecipe(blockSilver, INGOT + AG);
		addStorageRecipe(ingotSilver, NUGGET + AG);
		addReverseStorageRecipe(ingotSilver, BLOCK + AG);
		addReverseStorageRecipe(nuggetSilver, INGOT + AG);
		addStorageRecipe(dustSilver, TINY_DUST + AG);
		addReverseStorageRecipe(tinySilver, DUST + AG);
		addSmelting(dustSilver, ingotSilver);
		addSmelting(plateSilver, ingotSilver);
		addArcaneCraftingRecipe(keyAlumentum, plateSilver, monorder, "A", "I", 'I', INGOT + AG, 'A', ALMNT);
		addGrindingRecipes(ingotSilver, dustSilver, false);
		addGrindingRecipes(plateSilver, dustSilver, true);

		addStorageRecipe(blockLead, INGOT + PB);
		addStorageRecipe(ingotLead, NUGGET + PB);
		addReverseStorageRecipe(ingotLead, BLOCK + PB);
		addReverseStorageRecipe(nuggetLead, INGOT + PB);
		addStorageRecipe(dustLead, TINY_DUST + PB);
		addReverseStorageRecipe(tinyLead, DUST + PB);
		addSmelting(dustLead, ingotLead);
		addSmelting(plateLead, ingotLead);
		addArcaneCraftingRecipe(keyAlumentum, plateLead, monorder, "A", "I", 'I', INGOT + PB, 'A', ALMNT);
		addGrindingRecipes(ingotLead, dustLead, false);
		addGrindingRecipes(plateLead, dustLead, true);

		addStorageRecipe(blockLutetium, INGOT + LU);
		addStorageRecipe(ingotLutetium, NUGGET + LU);
		addReverseStorageRecipe(ingotLutetium, BLOCK + LU);
		addReverseStorageRecipe(nuggetLutetium, INGOT + LU);
		addStorageRecipe(dustLutetium, TINY_DUST + LU);
		addReverseStorageRecipe(tinyLutetium, DUST + LU);
		addSmelting(dustLutetium, ingotLutetium);
		addSmelting(plateLutetium, ingotLutetium);
		addArcaneCraftingRecipe(keyAlumentum, plateLutetium, monorder, "A", "I", 'I', INGOT + LU, 'A', ALMNT);
		addGrindingRecipes(ingotLutetium, dustLutetium, false);
		addGrindingRecipes(plateLutetium, dustLutetium, true);

		addStorageRecipe(blockTungsten, INGOT + W);
		addStorageRecipe(ingotTungsten, NUGGET + W);
		addReverseStorageRecipe(ingotTungsten, BLOCK + W);
		addReverseStorageRecipe(nuggetTungsten, INGOT + W);
		addStorageRecipe(dustTungsten, TINY_DUST + W);
		addReverseStorageRecipe(tinyTungsten, DUST + W);
		//Too high melting point for regular furnace
		addArcaneCraftingRecipe(keyAlumentum, plateTungsten, monorder, "A", "I", "A", 'I', INGOT + W, 'A', ALMNT);
		addGrindingRecipes(ingotTungsten, dustTungsten, false);
		addGrindingRecipes(plateTungsten, dustTungsten, true);

		addStorageRecipe(blockIridium, INGOT + IR);
		addStorageRecipe(ingotIridium, NUGGET + IR);
		addReverseStorageRecipe(ingotIridium, BLOCK + IR);
		addReverseStorageRecipe(nuggetIridium, INGOT + IR);
		addStorageRecipe(dustIridium, TINY_DUST + IR);
		addReverseStorageRecipe(tinyIridium, DUST + IR);
		//Too high melting point for regular furnace
		addArcaneCraftingRecipe(keyAlumentum, plateIridium, monorder, "A", "I", "A", 'I', INGOT + IR, 'A', ALMNT);
		addGrindingRecipes(ingotIridium, dustIridium, false);
		addGrindingRecipes(plateIridium, dustIridium, true);

		addStorageRecipe(blockBismuth, INGOT + BI);
		addStorageRecipe(ingotBismuth, NUGGET + BI);
		addReverseStorageRecipe(ingotBismuth, BLOCK + BI);
		addReverseStorageRecipe(nuggetBismuth, INGOT + BI);
		addStorageRecipe(dustBismuth, TINY_DUST + BI);
		addReverseStorageRecipe(tinyBismuth, DUST + BI);
		addSmelting(dustBismuth, ingotBismuth);
		addSmelting(plateBismuth, ingotBismuth);
		addArcaneCraftingRecipe(keyAlumentum, plateBismuth, monorder, "A", "I", 'I', INGOT + BI, 'A', ALMNT);
		addGrindingRecipes(ingotBismuth, dustBismuth, false);
		addGrindingRecipes(plateBismuth, dustBismuth, true);

		addStorageRecipe(blockArsenic, INGOT + AS);
		addStorageRecipe(ingotArsenic, NUGGET + AS);
		addReverseStorageRecipe(ingotArsenic, BLOCK + AS);
		addReverseStorageRecipe(nuggetArsenic, INGOT + AS);
		addStorageRecipe(dustArsenic, TINY_DUST + AS);
		addReverseStorageRecipe(tinyArsenic, DUST + AS);
		addSmelting(dustArsenic, ingotArsenic);
		addSmelting(plateArsenic, ingotArsenic);
		addArcaneCraftingRecipe(keyAlumentum, plateArsenic, monorder, "A", "I", 'I', INGOT + AS, 'A', ALMNT);
		addGrindingRecipes(ingotArsenic, dustArsenic, false);
		addGrindingRecipes(plateArsenic, dustArsenic, true);

		addStorageRecipe(blockAntimony, INGOT + SB);
		addStorageRecipe(ingotAntimony, NUGGET + SB);
		addReverseStorageRecipe(ingotAntimony, BLOCK + SB);
		addReverseStorageRecipe(nuggetAntimony, INGOT + SB);
		addStorageRecipe(dustAntimony, TINY_DUST + SB);
		addReverseStorageRecipe(tinyAntimony, DUST + SB);
		addSmelting(dustAntimony, ingotAntimony);
		addSmelting(plateAntimony, ingotAntimony);
		addArcaneCraftingRecipe(keyAlumentum, plateAntimony, monorder, "A", "I", 'I', INGOT + SB, 'A', ALMNT);
		addGrindingRecipes(ingotAntimony, dustAntimony, false);
		addGrindingRecipes(plateAntimony, dustAntimony, true);

		addStorageRecipe(blockNeodymium, INGOT + ND);
		addStorageRecipe(ingotNeodymium, NUGGET + ND);
		addReverseStorageRecipe(ingotNeodymium, BLOCK + ND);
		addReverseStorageRecipe(nuggetNeodymium, INGOT + ND);
		addStorageRecipe(dustNeodymium, TINY_DUST + ND);
		addReverseStorageRecipe(tinyNeodymium, DUST + ND);
		addSmelting(dustNeodymium, ingotNeodymium);
		addSmelting(plateNeodymium, ingotNeodymium);
		addArcaneCraftingRecipe(keyAlumentum, plateNeodymium, monorder, "A", "I", 'I', INGOT + ND, 'A', ALMNT);
		addGrindingRecipes(ingotNeodymium, dustNeodymium, false);
		addGrindingRecipes(plateNeodymium, dustNeodymium, true);

		addStorageRecipe(blockOsmium, INGOT + OS);
		addStorageRecipe(ingotOsmium, NUGGET + OS);
		addReverseStorageRecipe(ingotOsmium, BLOCK + OS);
		addReverseStorageRecipe(nuggetOsmium, INGOT + OS);
		addStorageRecipe(dustOsmium, TINY_DUST + OS);
		addReverseStorageRecipe(tinyOsmium, DUST + OS);
		//Too high melting point for regular furnace
		addArcaneCraftingRecipe(keyAlumentum, plateOsmium, monorder, " A ", " I ", "A A", 'I', INGOT + OS, 'A', ALMNT);
		addGrindingRecipes(ingotOsmium, dustOsmium, false);
		addGrindingRecipes(plateOsmium, dustOsmium, true);

		addStorageRecipe(blockPalladium, INGOT + PD);
		addStorageRecipe(ingotPalladium, NUGGET + PD);
		addReverseStorageRecipe(ingotPalladium, BLOCK + PD);
		addReverseStorageRecipe(nuggetPalladium, INGOT + PD);
		addStorageRecipe(dustPalladium, TINY_DUST + PD);
		addReverseStorageRecipe(tinyPalladium, DUST + PD);
		addSmelting(dustPalladium, ingotPalladium);
		addSmelting(platePalladium, ingotPalladium);
		addArcaneCraftingRecipe(keyAlumentum, platePalladium, monorder, "A", "I", 'I', INGOT + PD, 'A', ALMNT);
		addGrindingRecipes(ingotPalladium, dustPalladium, false);
		addGrindingRecipes(platePalladium, dustPalladium, true);

		addStorageRecipe(blockAluminium, INGOT + AL);
		addStorageRecipe(ingotAluminium, NUGGET + AL);
		addReverseStorageRecipe(ingotAluminium, BLOCK + AL);
		addReverseStorageRecipe(nuggetAluminium, INGOT + AL);
		addStorageRecipe(dustAluminium, TINY_DUST + AL);
		addReverseStorageRecipe(tinyAluminium, DUST + AL);
		addSmelting(dustAluminium, ingotAluminium);
		addSmelting(plateAluminium, ingotAluminium);
		addArcaneCraftingRecipe(keyAlumentum, plateAluminium, monorder, "A", "I", 'I', INGOT + AL, 'A', ALMNT);
		addGrindingRecipes(ingotAluminium, dustAluminium, false);
		addGrindingRecipes(plateAluminium, dustAluminium, true);
	}

	public static void loadSimpleAlloyMetalRecipes() {
		addStorageRecipe(blockBrass, INGOT + CUZN);
		addStorageRecipe(ingotBrass, NUGGET + CUZN);
		addReverseStorageRecipe(ingotBrass, BLOCK + CUZN);
		addReverseStorageRecipe(nuggetBrass, INGOT + CUZN);
		addStorageRecipe(dustBrass, TINY_DUST + CUZN);
		addReverseStorageRecipe(tinyBrass, DUST + CUZN);
		addSmelting(dustBrass, ingotBrass);
		addSmelting(plateBrass, ingotBrass);
		addArcaneCraftingRecipe(keyAlumentum, plateBrass, monorder, "A", "I", 'I', INGOT + CUZN, 'A', ALMNT);
		addGrindingRecipes(ingotBrass, dustBrass, false);
		addGrindingRecipes(plateBrass, dustBrass, true);

		addStorageRecipe(blockBronze, INGOT + CUSN);
		addStorageRecipe(ingotBronze, NUGGET + CUSN);
		addReverseStorageRecipe(ingotBronze, BLOCK + CUSN);
		addReverseStorageRecipe(nuggetBronze, INGOT + CUSN);
		addStorageRecipe(dustBronze, TINY_DUST + CUSN);
		addReverseStorageRecipe(tinyBronze, DUST + CUSN);
		addSmelting(dustBronze, ingotBronze);
		addSmelting(plateBronze, ingotBronze);
		addArcaneCraftingRecipe(keyAlumentum, plateBronze, monorder, "A", "I", 'I', INGOT + CUSN, 'A', ALMNT);
		addGrindingRecipes(ingotBronze, dustBronze, false);
		addGrindingRecipes(plateBronze, dustBronze, true);

		addStorageRecipe(blockArsenicalBronze, INGOT + CUAS);
		addStorageRecipe(ingotArsenicalBronze, NUGGET + CUAS);
		addReverseStorageRecipe(ingotArsenicalBronze, BLOCK + CUAS);
		addReverseStorageRecipe(nuggetArsenicalBronze, INGOT + CUAS);
		addStorageRecipe(dustArsenicalBronze, TINY_DUST + CUAS);
		addReverseStorageRecipe(tinyArsenicalBronze, DUST + CUAS);
		addSmelting(dustArsenicalBronze, ingotArsenicalBronze);
		addSmelting(plateArsenicalBronze, ingotArsenicalBronze);
		addArcaneCraftingRecipe(keyAlumentum, plateArsenicalBronze, monorder, "A", "I", 'I', INGOT + CUAS, 'A', ALMNT);
		addGrindingRecipes(ingotArsenicalBronze, dustArsenicalBronze, false);
		addGrindingRecipes(plateArsenicalBronze, dustArsenicalBronze, true);

		addStorageRecipe(blockAntimonialBronze, INGOT + CUSB);
		addStorageRecipe(ingotAntimonialBronze, NUGGET + CUSB);
		addReverseStorageRecipe(ingotAntimonialBronze, BLOCK + CUSB);
		addReverseStorageRecipe(nuggetAntimonialBronze, INGOT + CUSB);
		addStorageRecipe(dustAntimonialBronze, TINY_DUST + CUSB);
		addReverseStorageRecipe(tinyAntimonialBronze, DUST + CUSB);
		addSmelting(dustAntimonialBronze, ingotAntimonialBronze);
		addSmelting(plateAntimonialBronze, ingotAntimonialBronze);
		addArcaneCraftingRecipe(keyAlumentum, plateAntimonialBronze, monorder, "A", "I", 'I', INGOT + CUSB, 'A', ALMNT);
		addGrindingRecipes(ingotAntimonialBronze, dustAntimonialBronze, false);
		addGrindingRecipes(plateAntimonialBronze, dustAntimonialBronze, true);

		addStorageRecipe(blockBismuthBronze, INGOT + CUBI);
		addStorageRecipe(ingotBismuthBronze, NUGGET + CUBI);
		addReverseStorageRecipe(ingotBismuthBronze, BLOCK + CUBI);
		addReverseStorageRecipe(nuggetBismuthBronze, INGOT + CUBI);
		addStorageRecipe(dustBismuthBronze, TINY_DUST + CUBI);
		addReverseStorageRecipe(tinyBismuthBronze, DUST + CUBI);
		addSmelting(dustBismuthBronze, ingotBismuthBronze);
		addSmelting(plateBismuthBronze, ingotBismuthBronze);
		addArcaneCraftingRecipe(keyAlumentum, plateBismuthBronze, monorder, "A", "I", 'I', INGOT + CUBI, 'A', ALMNT);
		addGrindingRecipes(ingotBismuthBronze, dustBismuthBronze, false);
		addGrindingRecipes(plateBismuthBronze, dustBismuthBronze, true);

		addStorageRecipe(blockMithril, INGOT + MTHR);
		addStorageRecipe(ingotMithril, NUGGET + MTHR);
		addReverseStorageRecipe(ingotMithril, BLOCK + MTHR);
		addReverseStorageRecipe(nuggetMithril, INGOT + MTHR);
		addStorageRecipe(dustMithril, TINY_DUST + MTHR);
		addReverseStorageRecipe(tinyMithril, DUST + MTHR);
		addSmelting(dustMithril, ingotMithril);
		addSmelting(plateMithril, ingotMithril);
		addArcaneCraftingRecipe(keyAlumentum, plateMithril, monorder, "A", "I", 'I', INGOT + MTHR, 'A', ALMNT);
		addGrindingRecipes(ingotMithril, dustMithril, false);
		addGrindingRecipes(plateMithril, dustMithril, true);

		addStorageRecipe(blockAlumiuiumBronze, INGOT + CUAL);
		addStorageRecipe(ingotAluminiumBronze, NUGGET + CUAL);
		addReverseStorageRecipe(ingotAluminiumBronze, BLOCK + CUAL);
		addReverseStorageRecipe(nuggetAluminiumBronze, INGOT + CUAL);
		addStorageRecipe(dustAluminiumBronze, TINY_DUST + CUAL);
		addReverseStorageRecipe(tinyAluminiumBronze, DUST + CUAL);
		addSmelting(dustAluminiumBronze, ingotAluminiumBronze);
		addSmelting(plateAluminiumBronze, ingotAluminiumBronze);
		addArcaneCraftingRecipe(keyAlumentum, plateAluminiumBronze, monorder, "A", "I", 'I', INGOT + CUAL, 'A', ALMNT);
		addGrindingRecipes(ingotAluminiumBronze, dustAluminiumBronze, false);
		addGrindingRecipes(plateAluminiumBronze, dustAluminiumBronze, true);

		addStorageRecipe(blockCupronickel, INGOT + CUNI);
		addStorageRecipe(ingotCupronickel, NUGGET + CUNI);
		addReverseStorageRecipe(ingotCupronickel, BLOCK + CUNI);
		addReverseStorageRecipe(nuggetCupronickel, INGOT + CUNI);
		addStorageRecipe(dustCupronickel, TINY_DUST + CUNI);
		addReverseStorageRecipe(tinyCupronickel, DUST + CUNI);
		addSmelting(dustCupronickel, ingotCupronickel);
		addSmelting(plateCupronickel, ingotCupronickel);
		addArcaneCraftingRecipe(keyAlumentum, plateCupronickel, monorder, "A", "I", 'I', INGOT + CUNI, 'A', ALMNT);
		addGrindingRecipes(ingotCupronickel, dustCupronickel, false);
		addGrindingRecipes(plateCupronickel, dustCupronickel, true);

		addStorageRecipe(blockRiftishBronze, INGOT + RBRZ);
		addStorageRecipe(ingotRiftishBronze, NUGGET + RBRZ);
		addReverseStorageRecipe(ingotRiftishBronze, BLOCK + RBRZ);
		addReverseStorageRecipe(nuggetRiftishBronze, INGOT + RBRZ);
		addStorageRecipe(dustRiftishBronze, TINY_DUST + RBRZ);
		addReverseStorageRecipe(tinyRiftishBronze, DUST + RBRZ);
		addSmelting(dustRiftishBronze, ingotRiftishBronze);
		addSmelting(plateRiftishBronze, ingotRiftishBronze);
		addArcaneCraftingRecipe(keyAlumentum, plateRiftishBronze, monorder, "A", "I", 'I', INGOT + RBRZ, 'A', ALMNT);
		addGrindingRecipes(ingotRiftishBronze, dustRiftishBronze, false);
		addGrindingRecipes(plateRiftishBronze, dustRiftishBronze, true);

		addStorageRecipe(blockConstantan, INGOT + CNST);
		addStorageRecipe(ingotConstantan, NUGGET + CNST);
		addReverseStorageRecipe(ingotConstantan, BLOCK + CNST);
		addReverseStorageRecipe(nuggetConstantan, INGOT + CNST);
		addStorageRecipe(dustConstantan, TINY_DUST + CNST);
		addReverseStorageRecipe(tinyConstantan, DUST + CNST);
		addSmelting(dustConstantan, ingotConstantan);
		addSmelting(plateConstantan, ingotConstantan);
		addArcaneCraftingRecipe(keyAlumentum, plateConstantan, monorder, "A", "I", 'I', INGOT + CNST, 'A', ALMNT);
		addGrindingRecipes(ingotConstantan, dustConstantan, false);
		addGrindingRecipes(plateConstantan, dustConstantan, true);

		addStorageRecipe(blockInvar, INGOT + FENI);
		addStorageRecipe(ingotInvar, NUGGET + FENI);
		addReverseStorageRecipe(ingotInvar, BLOCK + FENI);
		addReverseStorageRecipe(nuggetInvar, INGOT + FENI);
		addStorageRecipe(dustInvar, TINY_DUST + FENI);
		addReverseStorageRecipe(tinyInvar, DUST + FENI);
		addSmelting(dustInvar, ingotInvar);
		addSmelting(plateInvar, ingotInvar);
		addArcaneCraftingRecipe(keyAlumentum, plateInvar, monorder, "A", "I", 'I', INGOT + FENI, 'A', ALMNT);
		addGrindingRecipes(ingotInvar, dustInvar, false);
		addGrindingRecipes(plateInvar, dustInvar, true);

		addStorageRecipe(blockElectrum, INGOT + AUAG);
		addStorageRecipe(ingotElectrum, NUGGET + AUAG);
		addReverseStorageRecipe(ingotElectrum, BLOCK + AUAG);
		addReverseStorageRecipe(nuggetElectrum, INGOT + AUAG);
		addStorageRecipe(dustElectrum, TINY_DUST + AUAG);
		addReverseStorageRecipe(tinyElectrum, DUST + AUAG);
		addSmelting(dustElectrum, ingotElectrum);
		addSmelting(plateElectrum, ingotElectrum);
		addArcaneCraftingRecipe(keyAlumentum, plateElectrum, monorder, "A", "I", 'I', INGOT + AUAG, 'A', ALMNT);
		addGrindingRecipes(ingotElectrum, dustElectrum, false);
		addGrindingRecipes(plateElectrum, dustElectrum, true);

		addStorageRecipe(blockWardenicMetal, INGOT + WRDM);
		addStorageRecipe(ingotWardenicMetal, NUGGET + WRDM);
		addReverseStorageRecipe(ingotWardenicMetal, BLOCK + WRDM);
		addReverseStorageRecipe(nuggetWardenicMetal, INGOT + WRDM);
		addStorageRecipe(dustWardenicMetal, TINY_DUST + WRDM);
		addReverseStorageRecipe(tinyWardenicMetal, DUST + WRDM);
		addSmelting(dustWardenicMetal, ingotWardenicMetal);
		addSmelting(plateWardenicMetal, ingotWardenicMetal);
		addArcaneCraftingRecipe(keyAlumentum, plateWardenicMetal, monorder, "A", "I", 'I', INGOT + WRDM, 'A', ALMNT);
		addGrindingRecipes(ingotWardenicMetal, dustWardenicMetal, false);
		addGrindingRecipes(plateWardenicMetal, dustWardenicMetal, true);

		addStorageRecipe(blockDullRedsolder, INGOT + DRDS);
		addStorageRecipe(ingotDullRedsolder, NUGGET + DRDS);
		addReverseStorageRecipe(ingotDullRedsolder, BLOCK + DRDS);
		addReverseStorageRecipe(nuggetDullRedsolder, INGOT + DRDS);
		addStorageRecipe(dustDullRedsolder, TINY_DUST + DRDS);
		addReverseStorageRecipe(tinyDullRedsolder, DUST + DRDS);
		addSmelting(dustDullRedsolder, ingotDullRedsolder);
		addSmelting(plateDullRedsolder, ingotDullRedsolder);
		addArcaneCraftingRecipe(keyAlumentum, plateDullRedsolder, monorder, "A", "I", 'I', INGOT + DRDS, 'A', ALMNT);
		addGrindingRecipes(ingotDullRedsolder, dustDullRedsolder, false);
		addGrindingRecipes(plateDullRedsolder, dustDullRedsolder, true);

		addStorageRecipe(blockRedsolder, INGOT + RDSR);
		addStorageRecipe(ingotRedsolder, NUGGET + RDSR);
		addReverseStorageRecipe(ingotRedsolder, BLOCK + RDSR);
		addReverseStorageRecipe(nuggetRedsolder, INGOT + RDSR);
		addStorageRecipe(dustRedsolder, TINY_DUST + RDSR);
		addReverseStorageRecipe(tinyRedsolder, DUST + RDSR);
		addSmelting(dustRedsolder, ingotRedsolder);
		addSmelting(plateRedsolder, ingotRedsolder);
		addArcaneCraftingRecipe(keyAlumentum, plateRedsolder, monorder, "A", "I", 'I', INGOT + RDSR, 'A', ALMNT);
		addGrindingRecipes(ingotRedsolder, dustRedsolder, false);
		addGrindingRecipes(plateRedsolder, dustRedsolder, true);
	}

	public static void loadSpecialAlloyMetalRecipes() {
		addStorageRecipe(blockThaumicElectrum, INGOT + TELC);
		addStorageRecipe(ingotThaumicElectrum, NUGGET + TELC);
		addReverseStorageRecipe(ingotThaumicElectrum, BLOCK + TELC);
		addReverseStorageRecipe(nuggetThaumicElectrum, INGOT + TELC);
		addStorageRecipe(dustThaumicElectrum, TINY_DUST + TELC);
		addReverseStorageRecipe(tinyThaumicElectrum, DUST + TELC);
		addSmelting(dustThaumicElectrum, ingotThaumicElectrum);
		addSmelting(plateThaumicElectrum, ingotThaumicElectrum);
		addArcaneCraftingRecipe(keyAlumentum, plateThaumicElectrum, monorder, "A", "I", 'I', INGOT + TELC, 'A', ALMNT);
		addGrindingRecipes(ingotThaumicElectrum, dustThaumicElectrum, false);
		addGrindingRecipes(plateThaumicElectrum, dustThaumicElectrum, true);

		addStorageRecipe(blockThaumicRiftishBronze, INGOT + TRBR);
		addStorageRecipe(ingotThaumicRiftishBronze, NUGGET + TRBR);
		addReverseStorageRecipe(ingotThaumicRiftishBronze, BLOCK + TRBR);
		addReverseStorageRecipe(nuggetThaumicRiftishBronze, INGOT + TRBR);
		addStorageRecipe(dustThaumicRiftishBronze, TINY_DUST + TRBR);
		addReverseStorageRecipe(tinyThaumicRiftishBronze, DUST + TRBR);
		addSmelting(dustThaumicRiftishBronze, ingotThaumicRiftishBronze);
		addSmelting(plateThaumicRiftishBronze, ingotThaumicRiftishBronze);
		addArcaneCraftingRecipe(keyAlumentum, plateThaumicRiftishBronze, monorder, "A", "I", "A", 'I', INGOT + TRBR, 'A', ALMNT);
		addGrindingRecipes(ingotThaumicRiftishBronze, dustThaumicRiftishBronze, false);
		addGrindingRecipes(plateThaumicRiftishBronze, dustThaumicRiftishBronze, true);

		addStorageRecipe(blockSteel, INGOT + STEL);
		addStorageRecipe(ingotSteel, NUGGET + STEL);
		addReverseStorageRecipe(ingotSteel, BLOCK + STEL);
		addReverseStorageRecipe(nuggetSteel, INGOT + STEL);
		addStorageRecipe(dustSteel, TINY_DUST + STEL);
		addReverseStorageRecipe(tinySteel, DUST + STEL);
		addSmelting(dustSteel, ingotSteel);
		addSmelting(plateSteel, ingotSteel);
		addArcaneCraftingRecipe(keyAlumentum, plateSteel, monorder, "A", "I", "A", 'I', INGOT + STEL, 'A', ALMNT);
		addGrindingRecipes(ingotSteel, dustSteel, false);
		addGrindingRecipes(plateSteel, dustSteel, true);

		addStorageRecipe(blockThaumicSteel, INGOT + TSTL);
		addStorageRecipe(ingotThaumicSteel, NUGGET + TSTL);
		addReverseStorageRecipe(ingotThaumicSteel, BLOCK + TSTL);
		addReverseStorageRecipe(nuggetThaumicSteel, INGOT + TSTL);
		addStorageRecipe(dustThaumicSteel, TINY_DUST + TSTL);
		addReverseStorageRecipe(tinyThaumicSteel, DUST + TSTL);
		addSmelting(dustThaumicSteel, ingotThaumicSteel);
		addSmelting(plateThaumicSteel, ingotThaumicSteel);
		addArcaneCraftingRecipe(keyAlumentum, plateThaumicSteel, monorder, "A", "I", "A", 'I', INGOT + TSTL, 'A', ALMNT);
		addGrindingRecipes(ingotThaumicSteel, dustThaumicSteel, false);
		addGrindingRecipes(plateThaumicSteel, dustThaumicSteel, true);

		addStorageRecipe(blockVoidbrass, INGOT + VBRS);
		addStorageRecipe(ingotVoidbrass, NUGGET + VBRS);
		addReverseStorageRecipe(ingotVoidbrass, BLOCK + VBRS);
		addReverseStorageRecipe(nuggetVoidbrass, INGOT + VBRS);
		addStorageRecipe(dustVoidbrass, TINY_DUST + VBRS);
		addReverseStorageRecipe(tinyVoidbrass, DUST + VBRS);
		addSmelting(dustVoidbrass, ingotVoidbrass);
		addSmelting(plateVoidbrass, ingotVoidbrass);
		addArcaneCraftingRecipe(keyAlumentum, plateVoidbrass, monorder, "A", "I", "A", 'I', INGOT + VBRS, 'A', ALMNT);
		addGrindingRecipes(ingotVoidbrass, dustVoidbrass, false);
		addGrindingRecipes(plateVoidbrass, dustVoidbrass, true);

		addStorageRecipe(blockVoidsteel, INGOT + VSTL);
		addStorageRecipe(ingotVoidsteel, NUGGET + VSTL);
		addReverseStorageRecipe(ingotVoidsteel, BLOCK + VSTL);
		addReverseStorageRecipe(nuggetVoidsteel, INGOT + VSTL);
		addStorageRecipe(dustVoidsteel, TINY_DUST + VSTL);
		addReverseStorageRecipe(tinyVoidsteel, DUST + VSTL);
		addSmelting(dustVoidsteel, ingotVoidsteel);
		addSmelting(plateVoidsteel, ingotVoidsteel);
		addArcaneCraftingRecipe(keyAlumentum, plateVoidsteel, monorder, " A ", " I ", "A A", 'I', INGOT + VSTL, 'A', ALMNT);
		addGrindingRecipes(ingotVoidsteel, dustVoidsteel, false);
		addGrindingRecipes(plateVoidsteel, dustVoidsteel, true);

		addStorageRecipe(blockVoidtungsten, INGOT + VDWT);
		addStorageRecipe(ingotVoidtungsten, NUGGET + VDWT);
		addReverseStorageRecipe(ingotVoidtungsten, BLOCK + VDWT);
		addReverseStorageRecipe(nuggetVoidtungsten, INGOT + VDWT);
		addStorageRecipe(dustVoidtungsten, TINY_DUST + VDWT);
		addReverseStorageRecipe(tinyVoidtungsten, DUST + VDWT);
		//Too high melting point for regular furnace
		addArcaneCraftingRecipe(keyAlumentum, plateVoidtungsten, monorder, "AAA", "AIA", "AAA", 'I', INGOT + VDWT, 'A', ALMNT);
		addGrindingRecipes(ingotVoidtungsten, dustVoidtungsten, false);
		addGrindingRecipes(plateVoidtungsten, dustVoidtungsten, true);

		addStorageRecipe(blockVoidcupronickel, INGOT + VCPN);
		addStorageRecipe(ingotVoidcupronickel, NUGGET + VCPN);
		addReverseStorageRecipe(ingotVoidcupronickel, BLOCK + VCPN);
		addReverseStorageRecipe(nuggetVoidcupronickel, INGOT + VCPN);
		addStorageRecipe(dustVoidcupronickel, TINY_DUST + VCPN);
		addReverseStorageRecipe(tinyVoidcupronickel, DUST + VCPN);
		addSmelting(dustVoidcupronickel, ingotVoidcupronickel);
		addSmelting(plateVoidcupronickel, ingotVoidcupronickel);
		addArcaneCraftingRecipe(keyAlumentum, plateVoidcupronickel, monorder, "A", "I", 'I', INGOT + VCPN, 'A', ALMNT);
		addGrindingRecipes(ingotVoidcupronickel, dustVoidcupronickel, false);
		addGrindingRecipes(plateVoidcupronickel, dustVoidcupronickel, true);
	}

	public static void loadEquipmentAlloyMetalRecipes() {
		addStorageRecipe(blockWardenicBronze, INGOT + WBRZ);
		addStorageRecipe(ingotWardenicBronze, NUGGET + WBRZ);
		addReverseStorageRecipe(ingotWardenicBronze, BLOCK + WBRZ);
		addReverseStorageRecipe(nuggetWardenicBronze, INGOT + WBRZ);
		addStorageRecipe(dustWardenicBronze, TINY_DUST + WBRZ);
		addReverseStorageRecipe(tinyWardenicBronze, DUST + WBRZ);
		addSmelting(dustWardenicBronze, ingotWardenicBronze);
		addSmelting(plateWardenicBronze, ingotWardenicBronze);
		addArcaneCraftingRecipe(keyAlumentum, plateWardenicBronze, monorder, "A", "I", 'I', INGOT + WBRZ, 'A', ALMNT);
		addGrindingRecipes(ingotWardenicBronze, dustWardenicBronze, false);
		addGrindingRecipes(plateWardenicBronze, dustWardenicBronze, true);

		addStorageRecipe(blockWardenicSteel, INGOT + WDST);
		addStorageRecipe(ingotWardenicSteel, NUGGET + WDST);
		addReverseStorageRecipe(ingotWardenicSteel, BLOCK + WDST);
		addReverseStorageRecipe(nuggetWardenicSteel, INGOT + WDST);
		addStorageRecipe(dustWardenicSteel, TINY_DUST + WDST);
		addReverseStorageRecipe(tinyWardenicSteel, DUST + WDST);
		addSmelting(dustWardenicSteel, ingotWardenicSteel);
		addSmelting(plateWardenicSteel, ingotWardenicSteel);
		recipeWardenSteelPlate = addArcaneCraftingRecipe(keyWardenPlate, plateWardenicSteel, monorder, " A ", "AIA", " A ", 'A', itemAlumentum, 'I', INGOT + WDST); //Special
		addGrindingRecipes(ingotWardenicSteel, dustWardenicSteel, false);
		addGrindingRecipes(plateWardenicSteel, dustWardenicSteel, true);

		addStorageRecipe(blockWardenicRiftishBronze, INGOT + WRBR);
		addStorageRecipe(ingotWardenicRiftishBronze, NUGGET + WRBR);
		addReverseStorageRecipe(ingotWardenicRiftishBronze, BLOCK + WRBR);
		addReverseStorageRecipe(nuggetWardenicRiftishBronze, INGOT + WRBR);
		addStorageRecipe(dustWardenicRiftishBronze, TINY_DUST + WRBR);
		addReverseStorageRecipe(tinyWardenicRiftishBronze, DUST + WRBR);
		addSmelting(dustWardenicRiftishBronze, ingotWardenicRiftishBronze);
		addSmelting(plateWardenicRiftishBronze, ingotWardenicRiftishBronze);
		addArcaneCraftingRecipe(keyAlumentum, plateWardenicRiftishBronze, monorder, " A ", " I ", "A A", 'A', itemAlumentum, 'I', INGOT + WRBR);
		addGrindingRecipes(ingotWardenicRiftishBronze, dustWardenicRiftishBronze, false);
		addGrindingRecipes(plateWardenicRiftishBronze, dustWardenicRiftishBronze, true);

		addStorageRecipe(blockWardenicComposite, INGOT + WCMP);
		addStorageRecipe(ingotWardenicComposite, NUGGET + WCMP);
		addReverseStorageRecipe(ingotWardenicComposite, BLOCK + WCMP);
		addReverseStorageRecipe(nuggetWardenicComposite, INGOT + WCMP);
		addStorageRecipe(dustWardenicComposite, TINY_DUST + WCMP);
		addReverseStorageRecipe(tinyWardenicComposite, DUST + WCMP);
		addSmelting(dustWardenicComposite, smeltedWardenicComposite);
		addSmelting(plateWardenicComposite, smeltedWardenicComposite);
		recipeWardenicCompositePlate = addArcaneCraftingRecipe(keyWardenCompositePlate, plateWardenicComposite, new AspectList().add(ORDER, 1), " A ", "AIA", " A ", 'A', aluDenseTemp, 'I', INGOT + WCMP); //Special
		addGrindingRecipes(ingotWardenicComposite, dustWardenicComposite, false);
		addGrindingRecipes(plateWardenicComposite, dustWardenicComposite, true);

		addStorageRecipe(blockArcaneRedsolder, INGOT + ARDS);
		addStorageRecipe(ingotArcaneRedsolder, NUGGET + ARDS);
		addReverseStorageRecipe(ingotArcaneRedsolder, BLOCK + ARDS);
		addReverseStorageRecipe(nuggetArcaneRedsolder, INGOT + ARDS);
		addStorageRecipe(dustArcaneRedsolder, TINY_DUST + ARDS);
		addReverseStorageRecipe(tinyArcaneRedsolder, DUST + ARDS);
		addSmelting(dustArcaneRedsolder, ingotArcaneRedsolder);
		addSmelting(plateArcaneRedsolder, ingotArcaneRedsolder);
		addArcaneCraftingRecipe(keyAlumentum, plateArcaneRedsolder, monorder, "A", "I", 'I', INGOT + ARDS, 'A', ALMNT); //TODO
		addGrindingRecipes(ingotArcaneRedsolder, dustArcaneRedsolder, false);
		addGrindingRecipes(plateArcaneRedsolder, dustArcaneRedsolder, true);

		addStorageRecipe(blockRedbronze, INGOT + RDBR);
		addStorageRecipe(ingotRedbronze, NUGGET + RDBR);
		addReverseStorageRecipe(ingotRedbronze, BLOCK + RDBR);
		addReverseStorageRecipe(nuggetRedbronze, INGOT + RDBR);
		addStorageRecipe(dustRedbronze, TINY_DUST + RDBR);
		addReverseStorageRecipe(tinyRedbronze, DUST + RDBR);
		addSmelting(dustRedbronze, ingotRedbronze);
		addSmelting(plateRedbronze, ingotRedbronze);
		addArcaneCraftingRecipe(keyAlumentum, plateRedbronze, monorder, "A", "I", "A", 'I', INGOT + RDBR, 'A', ALMNT);
		addGrindingRecipes(ingotRedbronze, dustRedbronze, false);
		addGrindingRecipes(plateRedbronze, dustRedbronze, true);

		addStorageRecipe(blockHardenedRedbronze, INGOT + HRBR);
		addStorageRecipe(ingotHardenedRedbronze, NUGGET + HRBR);
		addReverseStorageRecipe(ingotHardenedRedbronze, BLOCK + HRBR);
		addReverseStorageRecipe(nuggetHardenedRedbronze, INGOT + HRBR);
		addStorageRecipe(dustHardenedRedbronze, TINY_DUST + HRBR);
		addReverseStorageRecipe(tinyHardenedRedbronze, DUST + HRBR);
		addSmelting(dustHardenedRedbronze, ingotHardenedRedbronze);
		addSmelting(plateHardenedRedbronze, ingotHardenedRedbronze);
		addArcaneCraftingRecipe(keyAlumentum, plateHardenedRedbronze, monorder, " A ", "AIA", " A ", 'I', INGOT + HRBR, 'A', ALMNT);
		addGrindingRecipes(ingotHardenedRedbronze, dustHardenedRedbronze, false);
		addGrindingRecipes(plateHardenedRedbronze, dustHardenedRedbronze, true);

		addStorageRecipe(blockFluxsteel, INGOT + FSTL);
		addStorageRecipe(ingotFluxsteel, NUGGET + FSTL);
		addReverseStorageRecipe(ingotFluxsteel, BLOCK + FSTL);
		addReverseStorageRecipe(nuggetFluxsteel, INGOT + FSTL);
		addStorageRecipe(dustFluxsteel, TINY_DUST + FSTL);
		addReverseStorageRecipe(tinyFluxsteel, DUST + FSTL);
		addSmelting(dustFluxsteel, ingotFluxsteel);
		addSmelting(plateFluxsteel, ingotFluxsteel);
		addArcaneCraftingRecipe(keyAlumentum, plateFluxsteel, monorder, "A A", "AIA", " A ", 'I', INGOT + FSTL, 'A', ALMNT);
		addGrindingRecipes(ingotFluxsteel, dustFluxsteel, false);
		addGrindingRecipes(plateFluxsteel, dustFluxsteel, true);

		addStorageRecipe(blockFluxedTungsten, INGOT + FLXW);
		addStorageRecipe(ingotFluxedTungsten, NUGGET + FLXW);
		addReverseStorageRecipe(ingotFluxedTungsten, BLOCK + FLXW);
		addReverseStorageRecipe(nuggetFluxedTungsten, INGOT + FLXW);
		addStorageRecipe(dustFluxedTungsten, TINY_DUST + FLXW);
		addReverseStorageRecipe(tinyFluxedTungsten, DUST + FLXW);
		//Too high melting point for regular furnace
		addArcaneCraftingRecipe(keyAlumentum, plateFluxedTungsten, monorder, "AAA", "AIA", "AAA", 'I', INGOT + FLXW, 'A', ALMNT);
		addGrindingRecipes(ingotFluxedTungsten, dustFluxedTungsten, false);
		addGrindingRecipes(plateFluxedTungsten, dustFluxedTungsten, true);

		addStorageRecipe(blockMagneoturgicComposite, INGOT + MCMP);
		addStorageRecipe(ingotMagneoturgicComposite, NUGGET + MCMP);
		addReverseStorageRecipe(ingotMagneoturgicComposite, BLOCK + MCMP);
		addReverseStorageRecipe(nuggetMagneoturgicComposite, INGOT + MCMP);
		addStorageRecipe(dustMagneoturgicComposite, TINY_DUST + MCMP);
		addReverseStorageRecipe(tinyMagneoturgicComposite, DUST + MCMP);
		//Too high melting point for regular furnace
		addArcaneCraftingRecipe(keyAlumentum, plateMagneoturgicComposite, monorder, " A ", " I ", "A A", 'I', INGOT + MCMP, 'A', aluDenseTemp);
		addGrindingRecipes(ingotMagneoturgicComposite, dustMagneoturgicComposite, false);
		addGrindingRecipes(plateMagneoturgicComposite, dustMagneoturgicComposite, true);

		addStorageRecipe(blockFluxedComposite, INGOT + FCMP);
		addStorageRecipe(ingotFluxedComposite, NUGGET + FCMP);
		addReverseStorageRecipe(ingotFluxedComposite, BLOCK + FCMP);
		addReverseStorageRecipe(nuggetFluxedComposite, INGOT + FCMP);
		addStorageRecipe(dustFluxedComposite, TINY_DUST + FCMP);
		addReverseStorageRecipe(tinyFluxedComposite, DUST + FCMP);
		//Too high melting point for regular furnace
		addArcaneCraftingRecipe(keyAlumentum, plateFluxedComposite, monorder, "A A", "AIA", " A ", 'I', INGOT + FCMP, 'A', aluDenseTemp);
		addGrindingRecipes(ingotFluxedComposite, dustFluxedComposite, false);
		addGrindingRecipes(plateFluxedComposite, dustFluxedComposite, true);

		addStorageRecipe(blockResonantFluxedComposite, INGOT + RCMP);
		addStorageRecipe(ingotResonantFluxedComposite, NUGGET + RCMP);
		addReverseStorageRecipe(ingotResonantFluxedComposite, BLOCK + RCMP);
		addReverseStorageRecipe(nuggetResonantFluxedComposite, INGOT + RCMP);
		addStorageRecipe(dustResonantFluxedComposite, TINY_DUST + RCMP);
		addReverseStorageRecipe(tinyResonantFluxedComposite, DUST + RCMP);
		//Too high melting point for regular furnace;
		addArcaneCraftingRecipe(keyAlumentum, plateResonantFluxedComposite, monorder, "AAA", "AIA", "AAA", 'I', INGOT + RCMP, 'A', aluDenseTemp);
		addGrindingRecipes(ingotResonantFluxedComposite, dustResonantFluxedComposite, false);
		addGrindingRecipes(plateResonantFluxedComposite, dustResonantFluxedComposite, true);

		addStorageRecipe(blockEmpoweredVoidbrass, INGOT + EVBS);
		addStorageRecipe(ingotEmpoweredVoidbrass, NUGGET + EVBS);
		addReverseStorageRecipe(ingotEmpoweredVoidbrass, BLOCK + EVBS);
		addReverseStorageRecipe(nuggetEmpoweredVoidbrass, INGOT + EVBS);
		addStorageRecipe(dustEmpoweredVoidbrass, TINY_DUST + EVBS);
		addReverseStorageRecipe(tinyEmpoweredVoidbrass, DUST + EVBS);
		addSmelting(dustEmpoweredVoidbrass, ingotEmpoweredVoidbrass);
		addSmelting(plateEmpoweredVoidbrass, ingotEmpoweredVoidbrass);
		addArcaneCraftingRecipe(keyAlumentum, plateEmpoweredVoidbrass, monorder, "A", "I", "A", 'I', INGOT + EVBS, 'A', ALMNT);
		addGrindingRecipes(ingotEmpoweredVoidbrass, dustEmpoweredVoidbrass, false);
		addGrindingRecipes(plateEmpoweredVoidbrass, dustEmpoweredVoidbrass, true);

		addStorageRecipe(blockCrimsonThaumium, INGOT + CTHM);
		addStorageRecipe(ingotCrimsonThaumium, NUGGET + CTHM);
		addReverseStorageRecipe(ingotCrimsonThaumium, BLOCK + CTHM);
		addReverseStorageRecipe(nuggetCrimsonThaumium, INGOT + CTHM);
		addStorageRecipe(dustCrimsonThaumium, TINY_DUST + CTHM);
		addReverseStorageRecipe(tinyCrimsonThaumium, DUST + CTHM);
		addSmelting(dustCrimsonThaumium, ingotCrimsonThaumium);
		addSmelting(plateCrimsonThaumium, ingotCrimsonThaumium);
		addArcaneCraftingRecipe(keyAlumentum, plateCrimsonThaumium, monorder, " A ", "AIA", " A ", 'I', INGOT + CTHM, 'A', ALMNT);
		addGrindingRecipes(ingotCrimsonThaumium, dustCrimsonThaumium, false);
		addGrindingRecipes(plateCrimsonThaumium, dustCrimsonThaumium, true);

		addStorageRecipe(blockOccultVoidtungsten, INGOT + OCVW);
		addStorageRecipe(ingotOccultVoidtungsten, NUGGET + OCVW);
		addReverseStorageRecipe(ingotOccultVoidtungsten, BLOCK + OCVW);
		addReverseStorageRecipe(nuggetOccultVoidtungsten, INGOT + OCVW);
		addStorageRecipe(dustOccultVoidtungsten, TINY_DUST + OCVW);
		addReverseStorageRecipe(tinyOccultVoidtungsten, DUST + OCVW);
		//Too high melting point for regular furnace
		addArcaneCraftingRecipe(keyAlumentum, plateOccultVoidtungsten, monorder, " A ", "AIA", " A ", 'I', INGOT + OCVW, 'A', aluDenseTemp);
		addGrindingRecipes(ingotOccultVoidtungsten, dustOccultVoidtungsten, false);
		addGrindingRecipes(plateOccultVoidtungsten, dustOccultVoidtungsten, true);
	}

	public static void loadGemRecipes() {
		addStorageRecipe(blockPyrope, GEM + PYRP);
		addStorageRecipe(gemPyrope, NUGGET + PYRP);
		addReverseStorageRecipe(gemPyrope, BLOCK + PYRP);
		addReverseStorageRecipe(shardPyrope, GEM + PYRP);
		addStorageRecipe(dustPyrope, TINY_DUST + PYRP);
		addReverseStorageRecipe(tinyPyrope, DUST + PYRP);
		addGrindingRecipes(gemPyrope, dustPyrope, true);

		addStorageRecipe(blockDioptase, GEM + DIOP);
		addStorageRecipe(gemDioptase, NUGGET + DIOP);
		addReverseStorageRecipe(gemDioptase, BLOCK + DIOP);
		addReverseStorageRecipe(shardDioptase, GEM + DIOP);
		addStorageRecipe(dustDioptase, TINY_DUST + DIOP);
		addReverseStorageRecipe(tinyDioptase, DUST + DIOP);
		addGrindingRecipes(gemDioptase, dustDioptase, true);

		addStorageRecipe(blockJethrineSapphire, GEM + JSPH);
		addStorageRecipe(gemJethrineSapphire, NUGGET + JSPH);
		addReverseStorageRecipe(gemJethrineSapphire, BLOCK + JSPH);
		addReverseStorageRecipe(shardJethrineSapphire, GEM + JSPH);
		addStorageRecipe(dustJethrineSapphire, TINY_DUST + JSPH);
		addReverseStorageRecipe(tinyJethrineSapphire, DUST + JSPH);
		addGrindingRecipes(gemJethrineSapphire, dustJethrineSapphire, true);

		addStorageRecipe(blockJethrinePyroptase, GEM + JPRT);
		addStorageRecipe(gemJethrinePyroptase, NUGGET + JPRT);
		addReverseStorageRecipe(gemJethrinePyroptase, BLOCK + JPRT);
		addReverseStorageRecipe(shardJethrinePyroptase, GEM + JPRT);
		addStorageRecipe(dustJethrinePyroptase, TINY_DUST + JPRT);
		addReverseStorageRecipe(tinyJethrinePyroptase, DUST + JPRT);
		addGrindingRecipes(gemJethrinePyroptase, dustJethrinePyroptase, true, 8);

		addStorageRecipe(blockWardenicCrystal, GEM + WCRS);
		addStorageRecipe(gemWardenicCrystal, NUGGET + WCRS);
		addReverseStorageRecipe(gemWardenicCrystal, BLOCK + WCRS);
		addReverseStorageRecipe(shardWardenicCrystal, GEM + WCRS);
		addStorageRecipe(dustWardenicCrystal, TINY_DUST + WCRS);
		addReverseStorageRecipe(tinyWardenicCrystal, DUST + WCRS);
		addGrindingRecipes(gemWardenicCrystal, dustWardenicCrystal, true, 8);

		addStorageRecipe(blockActivatedWardenicCrystal, GEM + AWCR);
		addStorageRecipe(gemActivatedWardenicCrystal, NUGGET + AWCR);
		addReverseStorageRecipe(gemActivatedWardenicCrystal, BLOCK + AWCR);
		addReverseStorageRecipe(shardActivatedWardenicCrystal, GEM + AWCR);
		addStorageRecipe(dustActivatedWardenicCrystal, TINY_DUST + AWCR);
		addReverseStorageRecipe(tinyActivatedWardenicCrystal, DUST + AWCR);
		addGrindingRecipes(gemActivatedWardenicCrystal, dustActivatedWardenicCrystal, true, 8);

		addStorageRecipe(blockAwakenedWardenicCrystal, GEM + WWCR);
		addStorageRecipe(gemAwakenedWardenicCrystal, NUGGET + WWCR);
		addReverseStorageRecipe(gemAwakenedWardenicCrystal, BLOCK + WWCR);
		addReverseStorageRecipe(shardAwakenedWardenicCrystal, GEM + WWCR);
		addStorageRecipe(dustAwakenedWardenicCrystal, TINY_DUST + WWCR);
		addReverseStorageRecipe(tinyAwakenedWardenicCrystal, DUST + WWCR);
		addGrindingRecipes(gemAwakenedWardenicCrystal, dustAwakenedWardenicCrystal, true, 10);

		recipeQuartzBlock = addSquareRecipe(blockWardenicQuartz, GEM + WQRZ);
		addStorageRecipe(gemWardenicQuartz, NUGGET + WQRZ);
		recipeQuartzDeblock = addDeblockingRecipe(gemWardenicQuartz, blockWardenicQuartz); //Special
		addReverseStorageRecipe(shardWardenicQuartz, GEM + WQRZ);
		addStorageRecipe(dustWardenicQuartz, TINY_DUST + WQRZ);
		addReverseStorageRecipe(tinyWardenicQuartz, DUST + WQRZ);
		addGrindingRecipes(gemWardenicQuartz, dustWardenicQuartz, true, 4);

		addSquareRecipe(blockInfusedQuartz, GEM + IQRZ);
		addStorageRecipe(gemInfusedQuartz, NUGGET + IQRZ);
		addDeblockingRecipe(gemInfusedQuartz, blockInfusedQuartz); //Special
		addReverseStorageRecipe(shardInfusedQuartz, GEM + IQRZ);
		addStorageRecipe(dustInfusedQuartz, TINY_DUST + IQRZ);
		addReverseStorageRecipe(tinyInfusedQuartz, DUST + IQRZ);
		addGrindingRecipes(gemInfusedQuartz, dustInfusedQuartz, true, 5);

		addSquareRecipe(blockRedquartz, GEM + RQZT);
		addStorageRecipe(gemRedquartz, NUGGET + RQZT);
		addDeblockingRecipe(gemRedquartz, blockRedquartz); //Special
		addReverseStorageRecipe(shardRedquartz, GEM + RQZT);
		addStorageRecipe(dustRedquartz, TINY_DUST + RQZT);
		addReverseStorageRecipe(tinyRedquartz, DUST + RQZT);
		addGrindingRecipes(gemRedquartz, dustRedquartz, true, 4);
	}

	public static void loadMiscMetalRecipes() {
		addStorageRecipe(blockLanthanides, INGOT + LNTH);
		addStorageRecipe(ingotLanthanides, NUGGET + LNTH);
		addReverseStorageRecipe(ingotLanthanides, BLOCK + LNTH);
		addReverseStorageRecipe(nuggetLanthanides, INGOT + LNTH);
		addStorageRecipe(dustLanthanides, TINY_DUST + LNTH);
		addReverseStorageRecipe(tinyLanthanides, DUST + LNTH);
		addSmelting(dustLanthanides, ingotLanthanides);
		addGrindingRecipes(ingotLanthanides, dustLanthanides, false);

		addStorageRecipe(blockXenotimeJunk, INGOT + YPOJ);
		addStorageRecipe(ingotXenotimeJunk, NUGGET + YPOJ);
		addReverseStorageRecipe(ingotXenotimeJunk, BLOCK + YPOJ);
		addReverseStorageRecipe(nuggetXenotimeJunk, INGOT + YPOJ);
		addStorageRecipe(dustXenotimeJunk, TINY_DUST + YPOJ);
		addReverseStorageRecipe(tinyXenotimeJunk, DUST + YPOJ);
		addSmelting(dustXenotimeJunk, ingotXenotimeJunk);
		addGrindingRecipes(ingotXenotimeJunk, dustXenotimeJunk, false);

		addStorageRecipe(blockIridosmium, INGOT + IROS);
		addStorageRecipe(ingotIridosmium, NUGGET + IROS);
		addReverseStorageRecipe(ingotIridosmium, BLOCK + IROS);
		addReverseStorageRecipe(nuggetIridosmium, INGOT + IROS);
		addStorageRecipe(dustIridosmium, TINY_DUST + IROS);
		addReverseStorageRecipe(tinyIridosmium, DUST + IROS);
		//Too high melting point for regular furnace
		addGrindingRecipes(ingotIridosmium, dustIridosmium, false);

		addStorageRecipe(blockThaumicBronze, INGOT + TBRZ);
		addStorageRecipe(ingotThaumicBronze, NUGGET + TBRZ);
		addReverseStorageRecipe(ingotThaumicBronze, BLOCK + TBRZ);
		addReverseStorageRecipe(nuggetThaumicBronze, INGOT + TBRZ);
		addStorageRecipe(dustThaumicBronze, TINY_DUST + TBRZ);
		addReverseStorageRecipe(tinyThaumicBronze, DUST + TBRZ);
		addSmelting(dustThaumicBronze, ingotThaumicBronze);
		addSmelting(plateThaumicBronze, ingotThaumicBronze);
		addArcaneCraftingRecipe(keyAlumentum, plateThaumicBronze, monorder, "A", "I", 'I', INGOT + TBRZ, 'A', ALMNT);
		addGrindingRecipes(ingotThaumicBronze, dustThaumicBronze, false);
		addGrindingRecipes(plateThaumicBronze, dustThaumicBronze, true);

		addStorageRecipe(blockOsmiumLutetium, INGOT + OSLU);
		addStorageRecipe(ingotOsmiumLutetium, NUGGET + OSLU);
		addReverseStorageRecipe(ingotOsmiumLutetium, BLOCK + OSLU);
		addReverseStorageRecipe(nuggetOsmiumLutetium, INGOT + OSLU);
		addStorageRecipe(dustOsmiumLutetium, TINY_DUST + OSLU);
		addReverseStorageRecipe(tinyOsmiumLutetium, DUST + OSLU);
		//Too high melting point for regular furnace
		addArcaneCraftingRecipe(keyAlumentum, plateOsmiumLutetium, monorder, "A A", "AIA", " A ", 'I', INGOT + OSLU, 'A', ALMNT);
		addGrindingRecipes(ingotOsmiumLutetium, dustOsmiumLutetium, false);
		addGrindingRecipes(plateOsmiumLutetium, dustOsmiumLutetium, true);

				/*RecipeHelper.addStorageRecipe(block$Mineral, "ingot$MineralOre");
		RecipeHelper.addStorageRecipe(ingot$Mineral, "nugget$MineralOre");
		RecipeHelper.addReverseStorageRecipe(ingot$Mineral, "block$MineralOre");
		RecipeHelper.addReverseStorageRecipe(nugget$Mineral, "ingot$MineralOre");
		RecipeHelper.addStorageRecipe(dust$Mineral, "dustTiny$MineralOre");
		RecipeHelper.addReverseStorageRecipe(tiny$Mineral, "dust$MineralOre");
		RecipeHelper.addSmelting(dust$Mineral, ingot$Mineral);
		RecipeHelper.addSmelting(plate$Mineral, ingot$Mineral);
		RecipeHelper.addArcaneCraftingRecipe(keyAlumentum, plate$Mineral, monorder, "A", "I", 'I', "ingot$MineralOre", ALMNT);
		RecipeHelper.addGrindingRecipes(ingot$Mineral, dust$Mineral);
		RecipeHelper.addGrindingRecipes(plate$Mineral, dust$Mineral);*/
	}

	public static void loadAlloyingRecipes() {
		recipeCuZnBi = new ShapelessOreRecipe[2];
		recipeDullRedsolder = new ShapelessOreRecipe[2];
		recipeWardenMetal = new ShapelessOreRecipe[2];

		recipeCuZn = addAlloyRecipe(rawBrass, 4, INGOT, CU, CU, CU, ZN);
		recipeCuSn = addAlloyRecipe(rawBronze, 4, INGOT, CU, CU, CU, SN);
		recipeCuAs = addAlloyRecipe(rawArsenicalBronze, 4, INGOT, CU, CU, CU, AS);
		recipeCuSb = addAlloyRecipe(rawAntimonialBronze, 4, INGOT, CU, CU, CU, SB);
		recipeCuZnBi[0] = addAlloyRecipe(rawBismuthBronze, 8, INGOT, CUZN, CUZN, CUZN, CUZN, CU, CU, CU, BI);
		recipeCuZnBi[1] = addAlloyRecipe(rawBismuthBronze, 8, INGOT, CU, CU, CU, CU, CU, CU, ZN, BI);
		recipeCuAsSb = addAlloyRecipe(rawMithril, 2, INGOT, CUAS, CUSB);
		if (ThaumRevConfig.backwardsAlBronze) {
			recipeCuAl = addAlloyRecipe(rawAluminiumBronze, 4, INGOT, CU, AL, AL, AL);
		} else {
			recipeCuAl = addAlloyRecipe(rawAluminiumBronze, 4, INGOT, CU, CU, CU, AL);
		}
		recipeCuNi = addAlloyRecipe(rawCupronickel, 4, INGOT, CU, CU, CU, NI);
		recipeRBrz = addAlloyRecipe(rawRiftishBronze, 9, INGOT, MTHR, MTHR, MTHR, MTHR, CUBI, CUBI, CUSN, CUNI, CUAL);
		recipeCnst = addAlloyRecipe(rawConstantan, 2, INGOT, CU, NI);
		recipeFeNi = addAlloyRecipe(rawInvar, 3, INGOT, FE, FE, NI);
		recipeAuAg = addAlloyRecipe(rawElectrum, 2, INGOT, AU, AG);
		recipeWardenMetal[0] = addShapelessRecipe(cloneStack(rawWardenicMetal, 9), INGOT + THMM, INGOT + THMM, INGOT + THMM, INGOT + PD, DUST + WDBC, INGOT + CUZN, INGOT + AUAG, INGOT + ZN, HG_TC);
		recipeDullRedsolder[0] = addShapelessRecipe(cloneStack(rawDullRedsolder, 8), INGOT + CU, INGOT + SN, INGOT + AG, INGOT + PB, INGOT + PB, INGOT + PB, INGOT + PB, DUST + RS, DUST + RS);
		recipeRedsolder = addShapelessRecipe(cloneStack(rawRedsolder, 6), INGOT + DRDS, INGOT + DRDS, INGOT + DRDS, INGOT + DRDS, INGOT + DRDS, DUST + RS, DUST + RS, DUST + RS, DUST + RS);

		recDustCuZnBi = new ShapelessOreRecipe[2];

		recDustCuZn = addAlloyRecipe(dustBrass, 4, DUST, CU, CU, CU, ZN);
		recDustCuSn = addAlloyRecipe(dustBronze, 4, DUST, CU, CU, CU, SN);
		recDustCuAs = addAlloyRecipe(dustArsenicalBronze, 4, DUST, CU, CU, CU, AS);
		recDustCuSb = addAlloyRecipe(dustAntimonialBronze, 4, DUST, CU, CU, CU, SB);
		recDustCuZnBi[0] = addAlloyRecipe(dustBismuthBronze, 8, DUST, CUZN, CUZN, CUZN, CUZN, CU, CU, CU, BI);
		recDustCuZnBi[1] = addAlloyRecipe(dustBismuthBronze, 8, DUST, CU, CU, CU, CU, CU, CU, ZN, BI);
		recDustCuAsSb = addAlloyRecipe(dustMithril, 2, DUST, CUAS, CUSB);
		if (ThaumRevConfig.backwardsAlBronze) {
			recDustCuAl = addAlloyRecipe(dustAluminiumBronze, 4, DUST, CU, AL, AL, AL);
		} else {
			recDustCuAl = addAlloyRecipe(dustAluminiumBronze, 4, DUST, CU, CU, CU, AL);
		}
		recDustCuNi = addAlloyRecipe(dustCupronickel, 4, DUST, CU, CU, CU, NI);
		recDustRBrz = addAlloyRecipe(dustRiftishBronze, 9, DUST, MTHR, MTHR, MTHR, MTHR, CUBI, CUBI, CUSN, CUNI, CUAL);
		recDustCnst = addAlloyRecipe(dustConstantan, 2, DUST, CU, NI);
		recDustFeNi = addAlloyRecipe(dustInvar, 3, DUST, FE, FE, NI);
		recDustAuAg = addAlloyRecipe(dustElectrum, 2, DUST, AU, AG);
		recipeWardenMetal[1] = addShapelessRecipe(cloneStack(dustWardenicMetal, 9), DUST + THMM, DUST + THMM, DUST + THMM, DUST + PD, DUST + WDBC, DUST + CUZN, DUST + AUAG, DUST + ZN, HG_TC);
		recipeDullRedsolder[1] = addAlloyRecipe(dustDullRedsolder, 8, DUST, CU, SN, AG, PB, PB, PB, PB, RS, RS);
		recDustRedsolder = addAlloyRecipe(dustRedsolder, 6, DUST, DRDS, DRDS, DRDS, DRDS, DRDS, RS, RS, RS, RS);

		addAlloyRecipe(rawRiftishBronze, 1, NUGGET, MTHR, MTHR, MTHR, MTHR, CUBI, CUBI, CUSN, CUNI, CUAL);

		addAlloyRecipe(tinyBrass, 4, TINY_DUST, CU, CU, CU, ZN);
		addAlloyRecipe(tinyBronze, 4, TINY_DUST, CU, CU, CU, SN);
		addAlloyRecipe(tinyArsenicalBronze, 4, TINY_DUST, CU, CU, CU, AS);
		addAlloyRecipe(tinyAntimonialBronze, 4, TINY_DUST, CU, CU, CU, SB);
		addAlloyRecipe(tinyBismuthBronze, 8, TINY_DUST, CUZN, CUZN, CUZN, CUZN, CU, CU, CU, BI);
		addAlloyRecipe(tinyBismuthBronze, 8, TINY_DUST, CU, CU, CU, CU, CU, CU, ZN, BI);
		addAlloyRecipe(tinyMithril, 2, TINY_DUST, CUAS, CUSB);
		if (ThaumRevConfig.backwardsAlBronze) {
			addAlloyRecipe(tinyAluminiumBronze, 4, TINY_DUST, CU, AL, AL, AL);
		} else {
			addAlloyRecipe(tinyAluminiumBronze, 4, TINY_DUST, CU, CU, CU, AL);
		}
		addAlloyRecipe(tinyCupronickel, 4, TINY_DUST, CU, CU, CU, NI);
		addAlloyRecipe(dustRiftishBronze, 1, TINY_DUST, MTHR, MTHR, MTHR, MTHR, CUBI, CUBI, CUSN, CUNI, CUAL);
		addAlloyRecipe(tinyConstantan, 2, TINY_DUST, CU, NI);
		addAlloyRecipe(tinyInvar, 3, TINY_DUST, FE, FE, NI);
		addAlloyRecipe(tinyElectrum, 2, TINY_DUST, AU, AG);
		addShapelessRecipe(dustWardenicMetal, TINY_DUST + THMM, TINY_DUST + THMM, TINY_DUST + THMM, TINY_DUST + PD, TINY_DUST + WDBC, TINY_DUST + CUZN, TINY_DUST + AUAG, TINY_DUST + ZN, "itemDropQuicksilver");
		//addAlloyRecipe(tinyDullRedsolder, 8, TINY_DUST, CU, SN, AG, PB, PB, PB, PB, RS, RS);
		//addAlloyRecipe(tinyRedsolder, 6, TINY_DUST, DRDS, DRDS, DRDS, DRDS, DRDS, RS, RS, RS, RS);

		addSmelting(rawBrass, ingotBrass, 0.5F);
		addSmelting(rawBronze, ingotBronze, 0.5F);
		addSmelting(rawArsenicalBronze, ingotArsenicalBronze, 0.5F);
		addSmelting(rawAntimonialBronze, ingotAntimonialBronze, 0.5F);
		addSmelting(rawBismuthBronze, ingotBismuthBronze, 0.5F);
		addSmelting(rawMithril, ingotMithril, 0.5F);
		addSmelting(rawAluminiumBronze, ingotAluminiumBronze, 0.5F);
		addSmelting(rawCupronickel, ingotCupronickel, 0.5F);
		addSmelting(rawRiftishBronze, ingotRiftishBronze, 0.5F);
		addSmelting(rawConstantan, ingotConstantan, 0.5F);
		addSmelting(rawInvar, ingotInvar, 0.5F);
		addSmelting(rawElectrum, ingotElectrum, 0.5F);
		addSmelting(rawWardenicMetal, ingotWardenicMetal, 0.5F);
		addSmelting(rawDullRedsolder, ingotDullRedsolder, 0.5F);
		addSmelting(rawRedsolder, ingotRedsolder, 0.5F);
		addSmelting(rawArcaneRedsolder, ingotArcaneRedsolder, 0.5F);

		if (LoadedHelper.isThermalExpansionLoaded) {
			addInductionAlloyRecipe(CU, 3, ZN, 1, ingotBrass);
			addInductionAlloyRecipe(CU, 3, AS, 1, ingotArsenicalBronze);
			addInductionAlloyRecipe(CU, 3, SB, 1, ingotAntimonialBronze);
			addInductionAlloyRecipe(CUAS, 1, CUSB, 1, ingotMithril);
			addInductionAlloyRecipe(CU, ThaumRevConfig.backwardsAlBronze ? 1 : 3, AL, ThaumRevConfig.backwardsAlBronze ? 3 : 1, ingotAluminiumBronze);
			addInductionAlloyRecipe(CU, 3, NI, 1, ingotCupronickel);
		}
	}

	public static void loadMetalIntegrationRecipes() {
		if (LoadedHelper.isThermalExpansionLoaded) {
			addInductionSmelterRecipe(8000, cloneStack(dustTungsten, 2), dustPyrotheum, cloneStack(ingotTungsten, 2));
			addInductionSmelterRecipe(8000, cloneStack(dustIridium, 2), dustPyrotheum, cloneStack(ingotIridium, 2));
			addInductionSmelterRecipe(8000, cloneStack(dustOsmium, 2), dustPyrotheum, cloneStack(ingotOsmium, 2));
			addInductionSmelterRecipe(8000, cloneStack(dustVoidtungsten, 2), dustPyrotheum, cloneStack(ingotVoidtungsten, 2));
			addInductionSmelterRecipe(8000, cloneStack(dustFluxedTungsten, 2), dustPyrotheum, cloneStack(ingotFluxedTungsten, 2));
			addInductionSmelterRecipe(8000, cloneStack(dustOccultVoidtungsten, 2), dustPyrotheum, cloneStack(ingotOccultVoidtungsten, 2));
			addInductionSmelterRecipe(8000, cloneStack(dustIridosmium, 2), dustPyrotheum, cloneStack(ingotIridosmium, 2));
			addInductionSmelterRecipe(8000, cloneStack(dustOsmiumLutetium, 2), dustPyrotheum, cloneStack(ingotOsmiumLutetium, 2));

			addInductionSmelterRecipe(800, cloneStack(dustWardenicComposite, 2), new ItemStack(Blocks.sand), cloneStack(smeltedWardenicComposite, 2), itemSlag, 25);
		}
	}

	public static void loadDustRecipes() {
		addSmelting(itemShardAir, dustAer);
		addReverseStorageRecipe(tinyAer, DUST + AER);
		addStorageRecipe(dustAer, TINY_DUST + AER);

		addSmelting(itemShardFire, dustIgnis);
		addReverseStorageRecipe(tinyIgnis, DUST + IGNIS);
		addStorageRecipe(dustIgnis, TINY_DUST + IGNIS);

		addSmelting(itemShardWater, dustAqua);
		addReverseStorageRecipe(tinyAqua, DUST + AQUA);
		addStorageRecipe(dustAqua, TINY_DUST + AQUA);

		addSmelting(itemShardEarth, dustTerra);
		addReverseStorageRecipe(tinyTerra, DUST + TERRA);
		addStorageRecipe(dustTerra, TINY_DUST + TERRA);

		addSmelting(itemShardOrder, dustOrdo);
		addReverseStorageRecipe(tinyOrdo, DUST + ORDO);
		addStorageRecipe(dustOrdo, TINY_DUST + ORDO);

		addSmelting(itemShardEntropy, dustPerditio);
		addReverseStorageRecipe(tinyPerditio, DUST + PERDITIO);
		addStorageRecipe(dustPerditio, TINY_DUST + PERDITIO);

		addReverseStorageRecipe(tinyIron, DUST + FE);
		addStorageRecipe(dustIron, TINY_DUST + FE);
		addSmelting(dustIron, new ItemStack(Items.iron_ingot));

		addReverseStorageRecipe(tinyGold, DUST + AU);
		addStorageRecipe(dustGold, TINY_DUST + AU);
		addSmelting(dustGold, new ItemStack(Items.gold_ingot));

		addReverseStorageRecipe(tinyThaumium, DUST + THMM);
		addStorageRecipe(dustThaumium, TINY_DUST + THMM);
		addSmelting(dustThaumium, ingotThaumium);

		addReverseStorageRecipe(tinyVoidmetal, DUST + VMTL);
		addStorageRecipe(dustVoidmetal, TINY_DUST + VMTL);
		addSmelting(dustVoidmetal, ingotVoidmetal);
		addReverseStorageRecipe(ingotVoidmetal, blockVoidmetal);
		if (!LoadedHelper.isWitchingGadgetsLoaded) {
			addStorageRecipe(blockVoidmetal, ingotVoidmetal);
		}

		addReverseStorageRecipe(tinySulfur, DUST + S);
		addStorageRecipe(dustSulfur, TINY_DUST + S);

		addReverseStorageRecipe(tinySaltpeter, DUST + KNO);
		addStorageRecipe(dustSaltpeter, TINY_DUST + KNO);

		recipeSalisTiny = addReverseStorageRecipe(tinySalisMundus, DUST + SALIS);
		recipeSalis = addStorageRecipe(dustSalisMundus, TINY_DUST + SALIS);

		addReverseStorageRecipe(tinyPrimalEssence, DUST + PRES);
		addStorageRecipe(dustPrimalEssence, TINY_DUST + PRES);

		recipeBinderTiny = addReverseStorageRecipe(tinyWardenicCompound, DUST + WDBC);
		recipeBinderCombine = addStorageRecipe(dustWardenicBinder, TINY_DUST + WDBC);

		addReverseStorageRecipe(tinyRedstoneReduced, DUST + RSRD);
		addStorageRecipe(dustRedstoneReduced, TINY_DUST + RSRD);

		addReverseStorageRecipe(tinyRedstonePurified, DUST + RSPR);
		addStorageRecipe(dustRedstonePurified, TINY_DUST + RSPR);

		addReverseStorageRecipe(tinyRedstoneEnriched, DUST + RSNR);
		addStorageRecipe(dustRedstoneEnriched, TINY_DUST + RSNR);

		addReverseStorageRecipe(tinyContainmentGlass, DUST + CNTG);
		addStorageRecipe(dustContainmentGlass, TINY_DUST + CNTG);

		addReverseStorageRecipe(tinyTreatingCompound, DUST + MGTC);
		addStorageRecipe(dustTreatingCompound, TINY_DUST + MGTC);

		addReverseStorageRecipe(tinyPiezomagneticCompound, DUST + PMGC);
		addStorageRecipe(dustPiezomagneticCompound, TINY_DUST + PMGC);

		addReverseStorageRecipe(tinyGeomagneticCompound, DUST + GMGC);
		addStorageRecipe(dustGeomagneticCompound, TINY_DUST + GMGC);

		addReverseStorageRecipe(tinyConversionCompound, DUST + MGCC);
		addStorageRecipe(dustConversionCompound, TINY_DUST + MGCC);

		addReverseStorageRecipe(tinyContainmentCompound, DUST + RSCC);
		addStorageRecipe(dustContainmentCompound, TINY_DUST + RSCC);
	}

	public static void loadThaumicRecipes() {
		recipeTreatedCotton = addArcaneCraftingRecipe(keyCotton, itemCottonTreated, ThaumcraftHelper.newPrimalAspectList(2), " S ", "FCF", " F ", 'S', salisPinch, 'F', M0001, 'C', M0002);
		recipeEnchantedCotton = addCrucibleRecipe(keyCotton, itemCottonEnchanted, M0003, new AspectList().add(CLOTH, 2).add(MAGIC, 1));

		recipeEnchCottonGoggles = addArcaneCraftingRecipe(keyCottonRobes, new ItemStack(enchCottonGoggles), ThaumcraftHelper.newPrimalAspectList(5, 10, 5, 6, 4, 4), "CMC", "C C", "TMT", 'C', M0004, 'M', INGOT + MTHR, 'T', stackThaumometer);
		recipeEnchCottonRobes = addArcaneCraftingRecipe(keyCottonRobes, new ItemStack(enchCottonRobe), new AspectList().add(AIR, 5).add(ORDER, 2).add(ENTROPY, 2), "C C", "CCC", "CCC", 'C', M0004);
		recipeEnchCottonPants = addArcaneCraftingRecipe(keyCottonRobes, new ItemStack(enchCottonPants), new AspectList().add(WATER, 5).add(ORDER, 2).add(ENTROPY, 2), "CCC", "C C", "C C", 'C', M0004);
		recipeEnchCottonBoots = addArcaneCraftingRecipe(keyCottonRobes, new ItemStack(enchCottonBoots), new AspectList().add(EARTH, 4).add(ORDER, 2).add(ENTROPY, 2), "C C", "C C", 'C', M0004);

		recipeOrbReceptorBasic = addArcaneCraftingRecipe(keyAspectOrbBasic, ItemHelper.cloneStack(itemAspectOrbReceptorMakeshift, 2), ThaumcraftHelper.newPrimalAspectList(5, 2, 5, 2, 2, 1), "BGB", "NQN", "ITI", 'B', NUGGET + CUZN, 'G', "paneGlass", 'N', NUGGET + TBRZ, 'Q', nHg, 'I', NUGGET + FE, 'T', NUGGET + THMM);

		recipePrimalGoggles = addInfusionCraftingRecipe(keyPrimalRobes, new ItemStack(primalGoggles), 5, ThaumcraftHelper.newPrimalAspectList(8).add(MAGIC, 16).add(ENERGY, 16).add(CRAFT, 16).add(ORDER, 16), new ItemStack(enchCottonGoggles), ingotThaumicElectrum, ingotThaumicRiftishBronze, itemCottonEnchanted, itemCottonEnchanted, dustSalisMundus, dustSalisMundus, dustSalisMundus, dustSalisMundus, tinyPrimalEssence, itemPrimalCharm);
		recipePrimalRobes = addInfusionCraftingRecipe(keyPrimalRobes, new ItemStack(primalRobe), 5, ThaumcraftHelper.newPrimalAspectList(8).add(MAGIC, 16).add(ENERGY, 16).add(CRAFT, 16).add(ORDER, 16), new ItemStack(enchCottonRobe), ingotThaumicElectrum, ingotThaumicRiftishBronze, ingotThaumicRiftishBronze, itemCottonEnchanted, dustSalisMundus, dustSalisMundus, dustSalisMundus, dustSalisMundus, tinyPrimalEssence, itemPrimalCharm);
		recipePrimalPants = addInfusionCraftingRecipe(keyPrimalRobes, new ItemStack(primalPants), 5, ThaumcraftHelper.newPrimalAspectList(8).add(MAGIC, 16).add(ENERGY, 16).add(CRAFT, 16).add(ORDER, 16), new ItemStack(enchCottonPants), ingotThaumicElectrum, ingotThaumicRiftishBronze, ingotThaumicRiftishBronze, itemCottonEnchanted, dustSalisMundus, dustSalisMundus, dustSalisMundus, dustSalisMundus, tinyPrimalEssence, itemPrimalCharm);
		recipePrimalBoots = addInfusionCraftingRecipe(keyPrimalRobes, new ItemStack(primalBoots), 5, ThaumcraftHelper.newPrimalAspectList(8).add(MAGIC, 16).add(ENERGY, 16).add(CRAFT, 16).add(ORDER, 16), new ItemStack(enchCottonBoots), ingotThaumicElectrum, ingotThaumicRiftishBronze, ingotThaumicRiftishBronze, itemCottonEnchanted, dustSalisMundus, dustSalisMundus, dustSalisMundus, dustSalisMundus, tinyPrimalEssence, itemPrimalCharm);

		recipePrimalPendant = addInfusionCraftingRecipe(keyPrimalPendant, pendantPrimal, 8, ThaumcraftHelper.newPrimalAspectList(64).add(MAGIC, 128).add(ENERGY, 128).add(AURA, 64).add(VOID, 48).add(CRAFT, 32), amuletVisStorage, dustSalisMundus, dustSalisMundus, dustSalisMundus, dustSalisMundus, dustPrimalEssence, dustPrimalEssence, itemPrimalCharm, itemPrimalCharm, new ItemStack(Items.nether_star), new ItemStack(Items.nether_star), focusPrimal, focusPrimal);

		recipeThaumicBronzeRaw = addShapelessArcaneCraftingRecipe(keyThaumicBronze, rawThaumicBronze, new AspectList().add(ORDER, 5).add(EARTH, 5).add(FIRE, 5), nBronze, nBronze, nBronze, nBronze, nBronze, nBronze, "nuggetThaumium", "nuggetThaumium", "nuggetBrass");
		recipeThaumicBronzeCoated = addShapelessArcaneCraftingRecipe(keyThaumicBronze, coatedThaumicBronze, new AspectList().add(EARTH, 5).add(WATER, 5), "ingotThaumicBronzeRaw", nHg, salisPinch, "itemClay");

		recipeThaumicSteel = addInfusionCraftingRecipe(keyThaumicSteel, ingotThaumicSteel, 1, new AspectList().add(MAGIC, 4), ingotSteel, tinySalisMundus, tinySalisMundus, tinySalisMundus);
		recipeBlockThaumicSteel = addInfusionCraftingRecipe(keyThaumicSteel, blockThaumicSteel, 2, new AspectList().add(MAGIC, 36), blockSteel, dustSalisMundus, dustSalisMundus, dustSalisMundus);
		recipeThaumicRBronze = addInfusionCraftingRecipe(keyThaumicRBronze, ingotThaumicRiftishBronze, 1, new AspectList().add(MAGIC, 8), ingotRiftishBronze, tinySalisMundus, tinySalisMundus, tinySalisMundus, itemQuicksilverDrop);
		recipeThaumicElectrum = addCrucibleRecipe(keyThaumicElectrum, ingotThaumicElectrum, INGOT + AUAG, new AspectList().add(MAGIC, 6).add(ENERGY, 3));

		recipeThaumicBronzeChain = addArcaneCraftingRecipe(keyBronzeChain, ItemHelper.cloneStack(chainThaumicBronze, 12), new AspectList().add(ORDER, 10).add(FIRE, 5), " X ", "X X", 'X', "ingotThaumicBronze");

		recipeBronzeChainHelmet = addArcaneCraftingRecipe(keyArmorBronzeChain, new ItemStack(bronzeChainHelmet), new AspectList().add(ORDER, 10).add(EARTH, 5).add(FIRE, 5), "XXX", "X X", 'X', CHAIN_ORE + TBRZ);
		recipeBronzeChainmail = addArcaneCraftingRecipe(keyArmorBronzeChain, new ItemStack(bronzeChainmail), new AspectList().add(ORDER, 25).add(EARTH, 12).add(FIRE, 12), "X X", "XXX", "XXX", 'X', CHAIN_ORE + TBRZ);
		recipeBronzeChainGreaves = addArcaneCraftingRecipe(keyArmorBronzeChain, new ItemStack(bronzeChainGreaves), new AspectList().add(ORDER, 20).add(EARTH, 10).add(FIRE, 10), "XXX", "X X", "X X", 'X', CHAIN_ORE + TBRZ);
		recipeBronzeChainBoots = addArcaneCraftingRecipe(keyArmorBronzeChain, new ItemStack(bronzeChainBoots), new AspectList().add(ORDER, 5).add(EARTH, 3).add(FIRE, 3), "X X", "X X", 'X', CHAIN_ORE + TBRZ);

		recipeEldritchStone = addArcaneCraftingRecipe(keyEldritchStone, ItemHelper.cloneStack(eldritchStone, 8), new AspectList().add(EARTH, 1).add(ENTROPY, 2), "XXX", "XVX", "XXX", 'X', "stone", 'V', VSEED);

		recipeEldritchCog = addArcaneCraftingRecipe(keyVoidmetalWorking, itemEldritchCog, new AspectList().add(FIRE, 5).add(ORDER, 5).add(ENTROPY, 10), " X ", "X X", " X ", 'X', INGOT + VMTL);
		recipeEldritchKeystone = addArcaneCraftingRecipe(keyVoidmetalWorking, itemEldritchKeystone, ThaumcraftHelper.newPrimalAspectList(5, 15, 5, 10, 15, 25), "CIQ", "BSB", "QIC", 'C', "itemEldritchCog", 'I', INGOT + VMTL, 'Q', HG_TC, 'B', INGOT + VBRS, 'S', "itemStabilizedSingularity");

		recipesPods = new ShapelessOreRecipe[4];
		recipesPods[0] = addShapelessRecipe(itemPodCinderpearl, cinderpearl);
		recipesPods[1] = addShapelessRecipe(itemPodShiverpearl, shiverpearl);
		recipesPods[2] = addShapelessRecipe(itemPodStormypearl, stormypearl);
		recipesPods[3] = addShapelessRecipe(itemPodStonypearl, stonypearl);
		recipePowderCinderpearl = addCrucibleRecipe(keyElementalPowders, new ItemStack(Items.blaze_powder, 3, 0), itemPodCinderpearl, new AspectList().add(FIRE, 2).add(MAGIC, 1));
		if (LoadedHelper.isThermalFoundationLoaded) {
			recipePowderShiverpearl = addCrucibleRecipe(keyElementalPowders, cloneStack(powderBlizz, 3), itemPodShiverpearl, new AspectList().add(COLD, 2).add(WATER, 1));
			recipePowderStormypearl = addCrucibleRecipe(keyElementalPowders, cloneStack(powderBlitz, 3), itemPodStormypearl, new AspectList().add(WEATHER, 2).add(AIR, 1));
			recipePowderStonypearl = addCrucibleRecipe(keyElementalPowders, cloneStack(powderBasalz, 3), itemPodStonypearl, new AspectList().add(ENTROPY, 2).add(EARTH, 1));
		} else {
			recipePowderShiverpearl = addCrucibleRecipe(keyElementalPowders, cloneStack(powderBlizzBackup, 3), itemPodShiverpearl, new AspectList().add(COLD, 2).add(WATER, 1));
			recipePowderStormypearl = addCrucibleRecipe(keyElementalPowders, cloneStack(powderBlitzBackup, 3), itemPodStormypearl, new AspectList().add(WEATHER, 2).add(AIR, 1));
			recipePowderStonypearl = addCrucibleRecipe(keyElementalPowders, cloneStack(powderBasalzBackup, 3), itemPodStonypearl, new AspectList().add(ENTROPY, 2).add(EARTH, 1));
		}
	}

	public static void loadRunicRecipes() {
		//recipeRunicInfuser = addArcaneCraftingRecipe(keyRunicInfuser, runicInfuser, ThaumcraftHelper.newPrimalAspectList(25, 25, 25, 25, 25, 25), "QRQ", "SBS", "ITI", 'Q', nHg, 'R', visRelay, 'S', arcStoneSlab, 'B', shardBalanced, 'I', "ingotThaumium", 'T', table);
		recipeArcaneSingularity = addShapelessArcaneCraftingRecipe(keyRunicInfuser, itemArcaneSingularity, ThaumcraftHelper.newPrimalAspectList(2, 10, 0, 0, 5, 5), itemAlumentum, itemNitor); //TODO: v0.0.8: Runic Infuser

		recipeStableSingularity = addShapelessArcaneCraftingRecipe(keyRunicInfuser, itemStabilizedSingularity, ThaumcraftHelper.newPrimalAspectList(7, 15, 5, 5, 35, 10), itemArcaneSingularity, redstone, salisMundus); //TODO: v0.0.8: Runic Infuser

		recipePrimalEssence = addInfusionCraftingRecipe(keyPrimalEssence, dustPrimalEssence, 5, ThaumcraftHelper.newPrimalAspectList(32).add(MAGIC, 64).add(ENERGY, 24), itemStabilizedSingularity, dustAer, dustIgnis, dustAqua, dustTerra, dustOrdo, dustPerditio, dustSalisMundus, dustSalisMundus);

		recipeEnchGreatwood = addShapelessArcaneCraftingRecipe(keyEnchGreatwood, plankGreatwoodEnchanted, new AspectList().add(ORDER, 5), planksGreatwood, DUST + SALIS);
		recipeEnchGreatwoodShaft = addArcaneCraftingRecipe(keyEnchGreatwood, cloneStack(shaftGreatwoodEnchanted, 4), new AspectList().add(ENTROPY, 1), "x", "x", 'x', plankGreatwoodEnchanted);

		recipeAniPiston = addArcaneCraftingRecipe(keyAniPiston, itemAnimatedPiston, new AspectList().add(AIR, 5), "IGI", "TAT", "BRB", 'I', "nuggetIron", 'G', greatwoodSlab, 'T', "nuggetThaumium", 'A', "shardAir", 'B', "nuggetBrass", 'R', "dustRedstone"); //TODO: v0.0.8: Runic Infuser

		recipeEnchSilverwood = addShapelessArcaneCraftingRecipe(keyEnchSilverwood, plankSilverwoodEnchanted, new AspectList().add(ORDER, 5), hardenedSilverwood, DUST + SALIS, DUST + SALIS); //TODO: v0.0.8: Runic Infuser
		recipeEnchSilverwoodShaft = addArcaneCraftingRecipe(keyEnchSilverwood, cloneStack(shaftSilverwoodEnchanted, 4), new AspectList().add(ENTROPY, 2).add(ORDER, 1), "x", "x", 'x', plankSilverwoodEnchanted);

		recipeVoidbrass = addShapelessArcaneCraftingRecipe(keyVoidbrass, cloneStack(ingotVoidbrass, 2), new AspectList().add(FIRE, 10).add(ORDER, 10).add(ENTROPY, 20), INGOT + CUZN, HG_TC, VSEED, ALMNT, DUST + SALIS);

		recipeConsSilverwood = addInfusionCraftingRecipe(keyConsSilverwood, plankSilverwoodConsecrated, 3, new AspectList().add(ORDER, 10).add(FIRE, 5), plankSilverwoodEnchanted, plankSilverwoodEnchanted, plankSilverwoodEnchanted, plankSilverwoodEnchanted, plankSilverwoodEnchanted, tinySalisMundus, tinySalisMundus, nuggetSilver, itemQuicksilverDrop, itemNitor, nuggetPalladium); //TODO: Infusionize //TODO: v0.0.9: Alchemical Infuser
		recipeConsSilverwoodShaft = addArcaneCraftingRecipe(keyConsSilverwood, cloneStack(shaftSilverwoodConsecrated, 4), ThaumcraftHelper.newPrimalAspectList(1, 1, 1, 2, 3, 5), "x", "x", 'x', plankSilverwoodConsecrated);

		//recipeDarkAlchemicalInfuser = addArcaneCraftingRecipe(keyDarkRunicInfuser, darkRunicInfuser, ThaumcraftHelper.newPrimalAspectList(20, 10, 15, 15, 25, 35), "GVG", "MSM", "ORO", 'G', nAu, 'V', voidSeed, 'M', mirror, 'S', stableSingularity, 'O', obsTotem, 'R', runicInfuser);

	}

	public static void loadWardenicRecipes() {
		recipeExcubituraPaste = addShapelessArcaneCraftingRecipe(keyExcubituraPaste, itemExcubituraPaste, new AspectList().add(EARTH, 5).add(ENTROPY, 3), "itemExcubituraPetal", "itemExcubituraPetal", salisPinch, new ItemStack(Items.bowl));

		recipeExcubituraFabric = addArcaneCraftingRecipe(keyWardencloth, ItemHelper.cloneStack(itemFabricExcubitura, 8), new AspectList().add(ORDER, 5), "FFF", "FPF", "FFF", 'F', M0004, 'P', paste);
		recipeWardencloth = addCrucibleRecipe(keyWardencloth, itemWardencloth, "itemExcubituraFabric", new AspectList().add(CLOTH, 1).add(ARMOR, 1).add(aspectExcubitor, 1));

		recipeWardenclothSkullcap = addArcaneCraftingRecipe(keyArmorWardencloth, new ItemStack(wardenclothSkullcap), ThaumcraftHelper.newPrimalAspectList(10), "WEW", "E E", 'E', M0004, 'W', wardencloth);
		recipeWardenclothTunic = addArcaneCraftingRecipe(keyArmorWardencloth, new ItemStack(wardenclothTunic), ThaumcraftHelper.newPrimalAspectList(20), "W W", "WEW", "EWE", 'E', M0004, 'W', wardencloth);
		recipeWardenclothPants = addArcaneCraftingRecipe(keyArmorWardencloth, new ItemStack(wardenclothPants), ThaumcraftHelper.newPrimalAspectList(15), "EWE", "E E", "W W", 'E', M0004, 'W', wardencloth);
		recipeWardenclothBoots = addArcaneCraftingRecipe(keyArmorWardencloth, new ItemStack(wardenclothBoots), ThaumcraftHelper.newPrimalAspectList(10), "W W", "E E", 'E', M0004, 'W', wardencloth);

		recipeExcubituraOilUnproc = addShapelessArcaneCraftingRecipe(keyExcubituraOil, excubituraOilRaw, new AspectList().add(EARTH, 1).add(ORDER, 1), itemPhial, paste, paste, paste, paste);
		recipeExcubituraOil = addShapelessArcaneCraftingRecipe(keyExcubituraOil, excubituraOil, ThaumcraftHelper.newPrimalAspectList(0, 10, 5, 5, 25, 5), "itemExcubituraOilUnprocessed", nHg, salisPinch, itemAlumentum); //TODO: v0.0.8: Runic Infuser

		recipeWardenBronzeChain = addArcaneCraftingRecipe(keyWardenChain, ItemHelper.cloneStack(chainWardenicBronze, 6), ThaumcraftHelper.newPrimalAspectList(5, 5, 0, 5, 10, 0), "TTT", "SOS", "TTT", 'T', CHAIN_ORE + TBRZ, 'S', salisPinch, 'O', oilExcu);
		recipePrimalBronzeChain = addArcaneCraftingRecipe(keyWardenChain, ItemHelper.cloneStack(chainPrimalBronze, 2), ThaumcraftHelper.newPrimalAspectList(10).add(ORDER, 10), "NCS", "PBP", "SCN", 'N', NUGGET + CUZN, 'C', CHAIN_ORE + WBRZ, 'S', salisPinch, 'P', itemPrimalCharm, 'B', shardBalanced);
		recipeWardenBronze = addArcaneCraftingRecipe(keyWardenChain, ItemHelper.cloneStack(ingotWardenicBronze, 3), ThaumcraftHelper.newPrimalAspectList(5, 5, 0, 5, 10, 0), "SOS", "III", "SOS", 'S', salisPinch, 'O', oilExcu, 'I', INGOT + TBRZ);
		recipeWardenBronzePlate = addArcaneCraftingRecipe(keyWardenChain, ItemHelper.cloneStack(plateWardenicBronzeMirror, 2), ThaumcraftHelper.newPrimalAspectList(5, 5, 0, 5, 15, 0), "IQC", "SOS", "CRI", 'I', INGOT + TBRZ, 'C', CHAIN_ORE + WBRZ, 'S', salisPinch, 'Q', nHg, 'O', oilExcu, 'R', "nuggetThaumium");

		recipeWardenicChainHelmet = addArcaneCraftingRecipe(keyArmorWardenChain, new ItemStack(wardenicChainCoif), ThaumcraftHelper.newPrimalAspectList(20), "PWP", "W W", 'W', CHAIN_ORE + WBRZ, 'P', chainPBronze);
		recipeWardenicChainmail = addArcaneCraftingRecipe(keyArmorWardenChain, new ItemStack(wardenicChainmail), ThaumcraftHelper.newPrimalAspectList(42), "P P", "WMW", "WWW", 'W', CHAIN_ORE + WBRZ, 'P', chainPBronze, 'M', "itemPlateWardenicBronzeMirror");
		recipeWardenicChainGreaves = addArcaneCraftingRecipe(keyArmorWardenChain, new ItemStack(wardenicChainGreaves), ThaumcraftHelper.newPrimalAspectList(35), "WMW", "P P", "W W", 'W', CHAIN_ORE + WBRZ, 'P', chainPBronze, 'M', "itemPlateWardenicBronzeMirror");
		recipeWardenicChainBoots = addArcaneCraftingRecipe(keyArmorWardenChain, new ItemStack(wardenicChainBoots), ThaumcraftHelper.newPrimalAspectList(13), "P P", "W W", 'W', CHAIN_ORE + WBRZ, 'P', chainPBronze);

		recipePureOil = addShapelessArcaneCraftingRecipe(keyPureOil, excubituraOilPure, ThaumcraftHelper.newPrimalAspectList(0, 10, 10, 15, 50, 5), oilExcu, oilExcu, oilExcu, oilExcu, salisMundus, "itemArcaneSingularity", itemPhial); //TODO: v0.0.9: Alchemical Infuser

		recipeWardenSteel = addInfusionCraftingRecipe(keyWardenSteel, blockWardenicSteel, 2, new AspectList().add(METAL, 12).add(ARMOR, 8).add(TOOL, 8).add(ORDER, 16).add(MAGIC, 16).add(aspectExcubitor, 8), blockThaumicSteel, dustSalisMundus, dustSalisMundus, dustSalisMundus, dustSalisMundus, dustSalisMundus, dustSalisMundus, excubituraOilPure, quicksilver);

		recipeWardenicHardener = addInfusionCraftingRecipe(keyWardenicObsidian, wardenicHardener, 2, new AspectList().add(EARTH, 8).add(COLD, 8).add(MAGIC, 4).add(ORDER, 8), itemStabilizedSingularity, new ItemStack(Items.redstone), dustSalisMundus, excubituraOilPure, itemShardWater, itemShardWater, itemShardOrder);
		if (LoadedHelper.isThermalFoundationLoaded) {
			recipeWardenicHardenerAlt = addInfusionCraftingRecipe(keyWardenicObsidian, wardenicHardener, 2, new AspectList().add(EARTH, 8).add(COLD, 8).add(MAGIC, 4).add(ORDER, 8), itemStabilizedSingularity, new ItemStack(Items.redstone), dustSalisMundus, excubituraOilPure, dustCryotheum);
		}

		recipeWardenSteelChain = addArcaneCraftingRecipe(keyWardenPlate, ItemHelper.cloneStack(chainWardenicSteel, 12), new AspectList().add(ORDER, 15).add(FIRE, 10), " X ", "X X", 'X', "ingotWardenicSteel");
		recipeWardenSteelChainOiled = addArcaneCraftingRecipe(keyWardenPlate, ItemHelper.cloneStack(chainWardenicSteelOiled, 6), ThaumcraftHelper.newPrimalAspectList(10, 10, 0, 10, 25, 5), "CCC", "SOS", "CCC", 'C', "itemChainWardenicSteel", 'S', salisMundus, 'O', "itemExcubituraOilPure");

		//recipeWardenSteelPlate = addArcaneCraftingRecipe(keyWardenPlate, plateWardenicSteel, new AspectList().add(ORDER, 1), " A ", "ASA", " A ", 'A', itemAlumentum, 'S', "ingotWardenicSteel");
		recipeDetailedSteelPlate = addArcaneCraftingRecipe(keyWardenPlate, plateWardenicSteelDetailed, ThaumcraftHelper.newPrimalAspectList(0, 10, 5, 5, 15, 0), "TCB", "QPQ", "BCT", 'T', "nuggetThaumium", 'B', "nuggetBrass", 'C', CHAIN_ORE + WDST + "Oiled", 'Q', nHg, 'P', "plateWardenicSteel");
		recipeRunicSteelPlate = addInfusionCraftingRecipe(keyWardenPlate, plateWardenicSteelRunic, 1, new AspectList().add(ARMOR, 4).add(MAGIC, 4).add(ENERGY, 4).add(FLIGHT, 4), plateWardenicSteelDetailed, tinySalisMundus, tinySalisMundus, tinySalisMundus, tinySalisMundus, itemWardencloth, new ItemStack(Items.redstone));
		recipesConsecratedSteelPlate = addInfusionCraftingRecipe(keyWardenPlate, plateWardenicSteelConsecrated, 2, new AspectList().add(ARMOR, 8).add(MAGIC, 4).add(ORDER, 4), plateWardenicSteelRunic, nuggetSilver, itemQuicksilverDrop, new ItemStack(Items.glowstone_dust), plankSilverwoodConsecrated, tinySalisMundus);

		recipeWardenicPlateHelmet = addArcaneCraftingRecipe(keyArmorWardenSteel, new ItemStack(wardenicPlateHelmet), ThaumcraftHelper.newPrimalAspectList(30), "CRC", "R R", 'R', "itemPlateWardenicSteelRunic", 'C', "itemPlateWardenicSteelConsecrated");
		recipeWardenicChestplate = addArcaneCraftingRecipe(keyArmorWardenSteel, new ItemStack(wardenicChestplate), ThaumcraftHelper.newPrimalAspectList(70), "C C", "CRC", "RCR", 'R', "itemPlateWardenicSteelRunic", 'C', "itemPlateWardenicSteelConsecrated");
		recipeWardenicPlateGreaves = addArcaneCraftingRecipe(keyArmorWardenSteel, new ItemStack(wardenicPlateGreaves), ThaumcraftHelper.newPrimalAspectList(50), "RCR", "R R", "C C", 'R', "itemPlateWardenicSteelRunic", 'C', "itemPlateWardenicSteelConsecrated");
		recipeWardenicPlateBoots = addArcaneCraftingRecipe(keyArmorWardenSteel, new ItemStack(wardenicPlateBoots), ThaumcraftHelper.newPrimalAspectList(20), "R R", "C C", 'R', "itemPlateWardenicSteelRunic", 'C', "itemPlateWardenicSteelConsecrated");

		recipeWardenicQuartz = addCrucibleRecipe(keyQuartz, gemWardenicQuartz, "gemQuartz", new AspectList().add(MAGIC, 4).add(CRYSTAL, 2).add(ENERGY, 2).add(aspectExcubitor, 1));
		recipeWardenicQuartzDust = addCrucibleRecipe(keyQuartz, dustWardenicQuartz, GEM + WQRZ, new AspectList().add(ENTROPY, 2));
		recipeWardenicQuartzReconst = addCrucibleRecipe(keyQuartz, gemWardenicQuartz, DUST + WQRZ, new AspectList().add(ORDER, 2).add(CRYSTAL, 4));
		recipeWardenicQuartzInf = addInfusionCraftingRecipe(keyQuartz, gemInfusedQuartz, 2, new AspectList().add(aspectExcubitor, 4).add(MAGIC, 8).add(CRYSTAL, 4), blockWardenicQuartz, dustSalisMundus, dustSalisMundus, dustSalisMundus, excubituraOilPure);

		recipeWardenicCrystal = addCrucibleRecipe(keyWardenCrystal, gemWardenicCrystal, GEM + IQRZ, new AspectList().add(CRYSTAL, 32).add(AURA, 8).add(ORDER, 8).add(aspectExcubitor, 16));
		recipeWardenicCrystalDust = addCrucibleRecipe(keyWardenCrystal, dustWardenicCrystal, GEM +WCRS, new AspectList().add(ENTROPY, 4));
		recipeWardenicCrystalReconst = addCrucibleRecipe(keyWardenCrystal, gemWardenicCrystal, DUST + WCRS, new AspectList().add(ORDER, 4).add(CRYSTAL, 8));
		recipeWardenicBinder = addInfusionCraftingRecipe(keyWardenCrystal, ItemHelper.cloneStack(dustWardenicBinder, 8), 2, new AspectList().add(MAGIC, 8).add(ENERGY, 4).add(aspectExcubitor, 8), dustWardenicCrystal, dustInfusedQuartz, dustInfusedQuartz, dustSalisMundus, dustSalisMundus, dustSalisMundus, dustSalisMundus, quicksilver, excubituraOilPure);
		recipeActivatedWardenicCrystal = addInfusionCraftingRecipe(keyWardenCrystal, gemActivatedWardenicCrystal, 4, new AspectList().add(CRYSTAL, 8).add(AURA, 4).add(ORDER, 8).add(aspectExcubitor, 8).add(ENERGY, 32), gemWardenicCrystal, dustWardenicBinder, dustWardenicBinder, dustWardenicBinder, dustWardenicBinder, new ItemStack(Items.redstone), quicksilver, dustSalisMundus, new ItemStack(Items.glowstone_dust));

		recipeWardenRBronze = addInfusionCraftingRecipe(keyWardenBronze, ingotWardenicRiftishBronze, 2, new AspectList().add(aspectExcubitor, 4).add(MAGIC, 4).add(ARMOR, 2).add(TOOL, 2), ingotThaumicRiftishBronze, tinyWardenicCompound, tinyWardenicCompound, tinyWardenicCompound, tinySalisMundus);

		recipeWardenicCompositeRaw = addArcaneCraftingRecipe(keyWardenComposite, ItemHelper.cloneStack(rawWardenicComposite, 9), ThaumcraftHelper.newPrimalAspectList(0, 9, 0, 18, 27, 0), "SSS", "BBB", "WWW", 'S', INGOT + WDST, 'B', INGOT + WRBR, 'W', INGOT + WRDM);
		/*TODO*/recipeWardenicCompositeIngot = addInfusionCraftingRecipe(keyWardenComposite, ingotWardenicComposite, 3, new AspectList().add(METAL, 4).add(MAGIC, 4).add(aspectExcubitor, 4).add(ARMOR, 2).add(TOOL, 2).add(ORDER, 4), rawWardenicComposite, dustWardenicBinder, dustWardenicBinder, dustSalisMundus, dustInfusedQuartz);

		//recipeWardenicCompositePlate = addArcaneCraftingRecipe(keyWardenCompositePlate, plateWardenicComposite, new AspectList().add(ORDER, 1), " A ", "AIA", " A ", 'A', aluDenseTemp, 'I', "ingotWardenicComposite");
		recipeFittedCompositePlate = addArcaneCraftingRecipe(keyWardenCompositePlate, plateWardenicCompositeFitted, ThaumcraftHelper.newPrimalAspectList(0, 15, 0, 10, 15, 0), "CNW", "BPB", "WNC", 'C', "itemChainWardenicSteelOiled", 'W', wardencloth, 'N', "nuggetWardenicSteel", 'B', "dustWardenicBindingCompound", 'P', "plateWardenicComposite");
		recipeDetailedCompositePlate = addArcaneCraftingRecipe(keyWardenCompositePlate, plateWardenicCompositeDetailed, ThaumcraftHelper.newPrimalAspectList(0, 10, 5, 10, 15, 0), "EQE", "TPT", "BSB", 'E', "nuggetThaumicElectrum", 'Q', "quicksilver", 'T', "nuggetThaumium", 'P', "itemPlateWardenicCompositeFitted", 'B', "nuggetBismuthBronze", 'S', salisMundus);

		recipeRunicCompositePlate = addInfusionCraftingRecipe(keyWardenCompositeFitting, plateWardenicCompositeRunic, 2, new AspectList().add(ARMOR, 4).add(MAGIC, 8).add(ENERGY, 4).add(FLIGHT, 4).add(aspectExcubitor, 2), plateWardenicCompositeDetailed, dustSalisMundus, dustSalisMundus, nuggetThaumicElectrum, new ItemStack(Items.redstone));
		recipeConsecratedCompositePlate = addInfusionCraftingRecipe(keyWardenCompositeFitting, plateWardenicCompositeConsecrated, 4, new AspectList().add(ARMOR, 8).add(MAGIC, 12).add(ORDER, 4).add(ENERGY, 4).add(aspectExcubitor, 2), plateWardenicCompositeRunic, nuggetSilver, itemQuicksilverDrop, new ItemStack(Items.glowstone_dust), plankSilverwoodConsecrated, plankSilverwoodConsecrated, dustSalisMundus);
		recipePrimalCompositePlate = addInfusionCraftingRecipe(keyWardenCompositeFitting, plateWardenicCompositePrimal, 6, ThaumcraftHelper.newPrimalAspectList(8).add(MAGIC, 16).add(ENERGY, 8).add(aspectExcubitor, 4).add(ARMOR, 4), plateWardenicCompositeConsecrated, itemPrimalCharm, dustSalisMundus, dustSalisMundus, itemShardBalanced, nuggetBrass, quicksilver);

		recipeWardenicCompositeHelmet = addArcaneCraftingRecipe(keyArmorWardenComposite, new ItemStack(wardenicCompositeHelmet), ThaumcraftHelper.newPrimalAspectList(65), "PCP", "C C", 'C', "itemPlateWardenicCompositeConsecrated", 'P', "itemPlateWardenicCompositePrimal");
		recipeWardenicCompositeChestplate = addArcaneCraftingRecipe(keyArmorWardenComposite, new ItemStack(wardenicCompositeChestplate), ThaumcraftHelper.newPrimalAspectList(110), "P P", "CPC", "CPC", 'C', "itemPlateWardenicCompositeConsecrated", 'P', "itemPlateWardenicCompositePrimal");
		recipeWardenicCompositeGreaves = addArcaneCraftingRecipe(keyArmorWardenComposite, new ItemStack(wardenicCompositeGreaves), ThaumcraftHelper.newPrimalAspectList(100), "PCP", "C C", "C C", 'C', "itemPlateWardenicCompositeConsecrated", 'P', "itemPlateWardenicCompositePrimal");
		recipeWardenicCompositeBoots = addArcaneCraftingRecipe(keyArmorWardenComposite, new ItemStack(wardenicCompositeBoots), ThaumcraftHelper.newPrimalAspectList(60), "P P", "C C", 'C', "itemPlateWardenicCompositeConsecrated", 'P', "itemPlateWardenicCompositePrimal");

		recipeWardenicCrystalAwakened = addInfusionCraftingRecipe(keyWardenCrystalAwakened, gemAwakenedWardenicCrystal, 4, ThaumcraftHelper.newPrimalAspectList(16).add(MAGIC, 32).add(aspectExcubitor, 64).add(CRYSTAL, 16).add(ENERGY, 48), gemWardenicCrystal, dustWardenicBinder, dustWardenicBinder, dustWardenicBinder, dustWardenicBinder, excubituraOilPure, excubituraOilPure, dustSalisMundus, new ItemStack(Items.nether_star));
		recipeWardenicEssenceAwakened = addInfusionCraftingRecipe(keyWardenEssenceAwakened, itemEssenceOfAwakening, 8, ThaumcraftHelper.newPrimalAspectList(16).add(MAGIC, 48).add(aspectExcubitor, 128).add(ENERGY, 48).add(TOOL, 32).add(ARMOR, 32).add(CRAFT, 48), itemStabilizedSingularity, dustAwakenedWardenicCrystal, dustAwakenedWardenicCrystal, dustAwakenedWardenicCrystal, dustAwakenedWardenicCrystal, dustPrimalEssence, dustPrimalEssence, dustPrimalEssence, dustWardenicBinder, dustWardenicBinder, dustSalisMundus, dustSalisMundus, dustJethrinePyroptase, plateWardenicCompositePrimal, plateWardenicCompositePrimal, plateWardenicCompositePrimal, ingotPalladium);
	}

	public static void loadMagneoturgicRecipes() {
		if (ThaumRevConfig.getFluxed) {
			recipeRedstoneReduced = addCrucibleRecipe(keyRedstoneReduced, dustRedstoneReduced, DUST + RS, new AspectList().add(ORDER, 1).add(ENTROPY, 1).add(ENERGY, 1));

			recipeArcaneRedsolder = new ShapelessArcaneRecipe[2];
			recipeArcaneRedsolder[0] = addShapelessArcaneCraftingRecipe(keyArcaneRedsolder, cloneStack(rawArcaneRedsolder, 6), new AspectList().add(EARTH, 6).add(FIRE, 3), INGOT + RDSR, INGOT + RDSR, INGOT + RDSR, INGOT + RDSR, INGOT + RDSR, INGOT + RDSR, DUST + RSRD, DUST + RSRD, HG_TC);
			recipeArcaneRedsolder[1] = addShapelessArcaneCraftingRecipe(keyArcaneRedsolder, cloneStack(dustArcaneRedsolder, 6), new AspectList().add(EARTH, 6).add(FIRE, 3), DUST + RDSR, DUST + RDSR, DUST + RDSR, DUST + RDSR, DUST + RDSR, DUST + RDSR, DUST + RSRD, DUST + RSRD, HG_TC);

			recipeCapCoreRedsolder = addArcaneCraftingRecipe(keyCapCoreRedsolder, cloneStack(itemCapacitorCoreArcaneRedsolder, 2), new AspectList().add(WATER, 5).add(FIRE, 5).add(ORDER, 10), "RGR", "IRI", "NGN", 'I', INGOT + ARDS, 'N', NUGGET + ARDS, 'R', DUST + RSRD, 'G', "paneGlass");
			recipeRedstoneFabric = addShapelessArcaneCraftingRecipe(keyRedcloth, cloneStack(itemFabricRedstone, 4), ThaumcraftHelper.newPrimalAspectList(1, 2, 3, 5, 10, 1), M0004, M0004, M0004, M0004, DUST + RSRD, DUST + RSRD, HG_TC, DUST + SALIS);
			recipeRedcloth = addCrucibleRecipe(keyRedcloth, itemRedcloth, itemFabricRedstone, new AspectList().add(ENERGY, 2).add(CLOTH, 1).add(ARMOR, 1));
			recipeRedclothCapacitive = addShapelessArcaneCraftingRecipe(keyRedcloth, itemRedclothCapacitive, new AspectList().add(ORDER, 3), itemRedcloth, itemCapacitorCoreArcaneRedsolder, tinyRedstoneReduced, tinySalisMundus);

			recipeFluxRobesGoggles = addArcaneCraftingRecipe(keyArmorFluxRobes, new ItemStack(fluxRobeGoggles), ThaumcraftHelper.newPrimalAspectList(10), "RBR", "C C", "TBT", 'R', itemRedcloth, 'C', itemRedclothCapacitive, 'B', INGOT + RBRZ, 'T', stackThaumometer);
			recipeFluxRobesVest = addArcaneCraftingRecipe(keyArmorFluxRobes, new ItemStack(fluxRobeVest), ThaumcraftHelper.newPrimalAspectList(20), "R R", "CRC", "RRR", 'R', itemRedcloth, 'C', itemRedclothCapacitive);
			recipeFluxRobesPants = addArcaneCraftingRecipe(keyArmorFluxRobes, new ItemStack(fluxRobePants), ThaumcraftHelper.newPrimalAspectList(15), "RRR", "C C", "R R", 'R', itemRedcloth, 'C', itemRedclothCapacitive);
			recipeFluxRobesShoes = addArcaneCraftingRecipe(keyArmorFluxRobes, new ItemStack(fluxRobeBoots), ThaumcraftHelper.newPrimalAspectList(10), "C C", "R R", 'R', itemRedcloth, 'C', itemRedclothCapacitive);
		}
	}

	public static void loadTransmuationRecipes() {
		recipeTransNickel = addCrucibleRecipe(keyTransmutationNi, ItemHelper.cloneStack(nuggetNickel, 3), NUGGET + NI, new AspectList().add(METAL, 2).add(VOID, 1));
		recipeTransAluminium = addCrucibleRecipe(keyTransmutationAl, ItemHelper.cloneStack(nuggetAluminium, 3), NUGGET + AL, new AspectList().add(METAL, 2).add(ORDER, 1));
		recipeTransNeodymium = addCrucibleRecipe(keyTransmutationNd, ItemHelper.cloneStack(nuggetNeodymium, 3), NUGGET + ND, new AspectList().add(METAL, 2).add(EARTH, 1).add(ENERGY, 1));
		recipeTransZinc = addCrucibleRecipe(keyTransmutationZn, ItemHelper.cloneStack(nuggetZinc, 3), NUGGET + ZN, new AspectList().add(METAL, 2).add(CRYSTAL, 1));
		recipeTransArsenic = addCrucibleRecipe(keyTransmutationAs, ItemHelper.cloneStack(nuggetArsenic, 3), NUGGET + AS, new AspectList().add(METAL, 2).add(POISON, 1));
		recipeTransAntimony = addCrucibleRecipe(keyTransmutationSb, ItemHelper.cloneStack(nuggetAntimony, 3), NUGGET + SB, new AspectList().add(METAL, 2).add(POISON, 1));
		recipeTransBismuth = addCrucibleRecipe(keyTransmutationBi, ItemHelper.cloneStack(nuggetBismuth, 3), NUGGET + BI, new AspectList().add(METAL, 2).add(ORDER, 1));
		recipeTransTungsten = addCrucibleRecipe(keyTransmutationW, ItemHelper.cloneStack(nuggetTungsten, 3), NUGGET + W, new AspectList().add(METAL, 2).add(MECHANISM, 1).add(ARMOR, 1));
		recipeTransLutetium = addCrucibleRecipe(keyTransmutationLu, ItemHelper.cloneStack(nuggetLutetium, 3), NUGGET + LU, new AspectList().add(METAL, 2).add(EARTH, 1).add(VOID, 1));
		recipeTransPalladium = addCrucibleRecipe(keyTransmutationPd, ItemHelper.cloneStack(nuggetPalladium, 3), NUGGET + PD, new AspectList().add(METAL, 2).add(GREED, 1).add(EXCHANGE, 1));
		if (OreDictionary.doesOreNameExist(NUGGET + PT)) {
			ItemStack nPt = OreDictionary.getOres(NUGGET + PT).get(0);
			recipeTransPlatinum = addCrucibleRecipe(keyTransmutationPt, ItemHelper.cloneStack(nPt, 3), NUGGET + PT, new AspectList().add(METAL, 2).add(GREED, 1).add(EXCHANGE, 1));
		}
		recipeTransOsmium = addCrucibleRecipe(keyTransmutationOs, ItemHelper.cloneStack(nuggetOsmium, 3), NUGGET + OS, new AspectList().add(METAL, 2).add(ARMOR, 1).add(ORDER, 1));
		recipeTransIridium = addCrucibleRecipe(keyTransmutationIr, ItemHelper.cloneStack(nuggetIridium, 3), NUGGET + IR, new AspectList().add(METAL, 2).add(ARMOR, 1).add(ORDER, 1).add(MECHANISM, 1).add(ENERGY, 1));

	}

	public static void loadClusterRecipes() {
		recipeClusterNickel = addCrucibleRecipe(keyClusterNi, clusterNickel, ORE + NI, new AspectList().add(METAL, 1).add(ORDER, 1));
		if (OreDictionary.doesOreNameExist(ORE + AL)) {
			recipeClusterAluminium = addCrucibleRecipe(keyClusterAl, clusterXenotime, ORE +AL, new AspectList().add(METAL, 1).add(ORDER, 1));
		}
		recipeClusterXenotime = addCrucibleRecipe(keyClusterYPO, clusterXenotime, ORE + YPO, new AspectList().add(METAL, 1).add(ORDER, 1));
		recipeClusterZinc = addCrucibleRecipe(keyClusterZn, clusterZinc, ORE + ZN, new AspectList().add(METAL, 1).add(ORDER, 1));
		recipeClusterBismuth = addCrucibleRecipe(keyClusterBi, clusterBismuth, ORE + BI, new AspectList().add(METAL, 1).add(ORDER, 1));
		recipeClusterTennantite = addCrucibleRecipe(keyClusterCuAs, clusterTennantite, ORE + CAS, new AspectList().add(METAL, 1).add(ORDER, 1));
		recipeClusterTertahedrite = addCrucibleRecipe(keyClusterCuSb, clusterTetrahedrite, ORE + CSB, new AspectList().add(METAL, 1).add(ORDER, 1));
		recipeClusterTungsten = addCrucibleRecipe(keyClusterW, clusterTungsten, ORE + W, new AspectList().add(METAL, 1).add(ORDER, 1));
		if (OreDictionary.doesOreNameExist(ORE + PT)) {
			recipeClusterPlatinum = addCrucibleRecipe(keyClusterPt, clusterPlatinum, ORE + PT, new AspectList().add(METAL, 1).add(ORDER, 1));
		}
		recipeClusterIridosmium = addCrucibleRecipe(keyClusterIrOs, clusterIridosmium, ORE + IROS, new AspectList().add(METAL, 1).add(ORDER, 2));

		addSmelting(clusterNickel, cloneStack(ingotNickel, 2), 1.8F);
		addSmelting(clusterAluminium, cloneStack(ingotAluminium, 2), 1.5F);
		addSmelting(clusterXenotime, cloneStack(ingotLanthanides, 2), 1.5F);
		addSmelting(clusterZinc, cloneStack(ingotZinc, 2), 1.425F);
		addSmelting(clusterBismuth, cloneStack(ingotBismuth, 2), 1.725F);
		addSmelting(clusterTennantite, cloneStack(ingotArsenicalBronze, 2), 2.025F);
		addSmelting(clusterTetrahedrite, cloneStack(ingotAntimonialBronze, 2), 2.025F);
		//addSmelting(clusterIridosmium, ingotIridosmium, 3.0F);
		if (OreDictionary.doesOreNameExist(INGOT + PT)) {
			addSmelting(clusterPlatinum, cloneStack(OreDictionary.getOres(INGOT + PT).get(0), 2), 2.25F);
		}

		addSmeltingBonus(CLUSTER + NI, nuggetPalladium);
		addSmeltingBonus(CLUSTER + AL, nuggetAluminium);
		addSmeltingBonus(CLUSTER + YPO, nuggetLanthanides);
		addSmeltingBonus(CLUSTER + ZN, nuggetZinc);
		addSmeltingBonus(CLUSTER + BI, nuggetBismuth);
		addSmeltingBonus(CLUSTER + CAS, nuggetArsenicalBronze);
		addSmeltingBonus(CLUSTER + CSB, nuggetAntimonialBronze);
		addSmeltingBonus(CLUSTER + W, nuggetTungsten);
		addSmeltingBonus(CLUSTER + IROS, nuggetIridosmium);
		if (OreDictionary.doesOreNameExist(NUGGET + PT)) {
			addSmeltingBonus(CLUSTER + PT, OreDictionary.getOres(NUGGET + PT).get(0));
		}
	}

	public static void loadClusters() {
		addCluster(ORE + ZN, clusterZinc);
		addCluster(ORE + AL, clusterAluminium);
		addCluster(ORE + AL_, clusterAluminium); //Why do we spell it wrong in the U.S.? Because America is where terminology goes to die!
		addCluster(ORE + NI, clusterNickel);
		addCluster(ORE + YPO, clusterXenotime);
		addCluster(ORE + W, clusterTungsten);
		addCluster(ORE + IROS, clusterIridosmium);
		addCluster(ORE + BI, clusterBismuth);
		addCluster(ORE + CAS, clusterTennantite);
		addCluster(ORE + CSB, clusterTetrahedrite);
	}

	public static void addLoot() {
		generalItem.addContainer(1001, new ItemStack(Items.bowl));
		generalItem.addContainer(1036, itemPhial);
		generalItem.addContainer(1070, itemPhial);

		generalItem.addLoot(6520, ingotThaumicBronze, thaumicSlag);
		generalItem.addLoot(6521, ingotOsmiumLutetium, fluonicSlag);
	}

	public static void postInitRecipes() {
		List recipes = CraftingManager.getInstance().getRecipeList();
		ListIterator<IRecipe> iterator = recipes.listIterator();
		while (iterator.hasNext()) {
			IRecipe r = iterator.next();
			if (r.getRecipeOutput() != null && r.getRecipeOutput().getItem() == Items.blaze_powder) {
				if (r instanceof ShapedRecipes && ((ShapedRecipes) r).recipeItems.length == 1) {
					ItemStack component = ((ShapedRecipes) r).recipeItems[0];
					if (component.getItem() == cinderpearl.getItem() && component.getItemDamage() == cinderpearl.getItemDamage()){
						iterator.remove();
					}
				}
			}
		}
	}

	public static void setResearchLevel() {
		/*int lvl = researchLevelRaw;
		if (lvl < -1) {
			ThaumicRevelations.logger.error("Someone manually set our difficulty to " + lvl + "! Value should be between -1 and 2, inclusive. SETTING IT TO -1 FOR THIS LAUNCH!");
			lvl = -1;
		} else if (lvl > 2) {
			ThaumicRevelations.logger.error("Someone manually set our difficulty to " + lvl + "! I know challenges are fun, but the value should be between -1 and 2, inclusive. SETTING IT TO 2 FOR THIS LAUNCH!");
			lvl = 2;
		}
		if (lvl == -1) {
			try {
				Object obj = Class.forName("thaumcraft.common.config.Config").getField("researchDifficulty").get(null);
				if (obj instanceof Integer) {
					int temp = (Integer) obj;
					if (temp < 2 && temp > -2) {
						lvl = temp + 1;
					} else {
						ThaumicRevelations.logger.error("Thaumcraft's config data was not a value it should be! RESORTING TO DEFAULT OF 1!");
						lvl = 1;
					}
				} else {
					ThaumicRevelations.logger.error("Thaumcraft's config data was not a type it should be! RESORTING TO DEFAULT OF 1!");
					lvl = 1;
				}
			} catch (Exception ex) {
				ThaumicRevelations.logger.error("Thaumic Revelations couldn't find Thaumcraft's config to set our research information through hacky reflection! RESORTING TO DEFAULT OF 1!");
				lvl = 1;
			}
		}*/
		researchLevel = 2;//lvl;
	}

	/*public static void determineTempus() {
		// Thanks for the API hook, Myst!
		if (LoadedHelper.isMagicBeesLoaded) {
			Object protoTempus = MagicBeesAPI.thaumcraftAspectTempus;

			if (protoTempus != null && protoTempus instanceof Aspect) {
				tempus = (Aspect) protoTempus;
				return;
			}
		}
		tempus = new Aspect("tempus", 0xB68CFF, new Aspect[] {Aspect.VOID, Aspect.ORDER}, new ResourceLocation(RESOURCE_PREFIX, "textures/aspects/tempus.png"), 1);
		if (LoadedHelper.isForestryLoaded) {
			ThaumicRevelations.logger.info("What's the matter? What's with the lack of Magic Bees? You not like bees? DO YOU NOT LIKE THE BEES!?!? HUH!?!? YOU SOME APIPHOBIC LOSER!?!?");
		}
		timeyWimey();
	}*/

	//public static void forbiddenAspect() {
	//	if (LoadedHelper.isForbiddenMagicLoaded) {
	//		try {
	//			fox.spiteful.forbidden.DarkAspects.NETHER
	//		}
	//	}
	//}

	public static void loadResearch() {
		String key = RESEARCH_KEY_MAIN;
		researchThaumRev = new FluxGearResearchItem(keyThaumRev, key, new AspectList(), 0, 0, 0, potato);
		researchMaterial = new FluxGearResearchItem(keyMaterial, key, new AspectList(), -1, 3, 0, itemPodCinderpearl);
		researchMinerals = new FluxGearResearchItem(keyMinerals, key, new AspectList(), -2, 2, 0, oreDioptase);
		researchAlloys = new FluxGearResearchItem(keyAlloys, key, new AspectList(), -3, 6, 0, ingotBrass);

		researchThaumicBronze = new FluxGearResearchItem(keyThaumicBronze, key, new AspectList().add(METAL, 4).add(MAGIC, 3).add(ORDER, 1), -3, 8, 1, ingotThaumicBronze);
		researchBronzeChain = new FluxGearResearchItem(keyBronzeChain, key, new AspectList().add(METAL, 4).add(MAGIC, 2).add(CRAFT, 3), -3, 10, 1, chainThaumicBronze);
		researchArmorBronzeChain = new FluxGearResearchItem(keyArmorBronzeChain, key, new AspectList().add(METAL, 4).add(MAGIC, 3).add(ARMOR, 3), -5, 11, 1, new ItemStack(bronzeChainmail));
		researchThaumicRBronze = new FluxGearResearchItem(keyThaumicRBronze, key, new AspectList().add(MAGIC, 5).add(METAL, 4).add(ORDER, 1), -5, 7, 2, ingotThaumicRiftishBronze);
		researchThaumicSteel = new FluxGearResearchItem(keyThaumicSteel, key, new AspectList().add(METAL, 4).add(MAGIC, 4).add(CRAFT, 3), -3, 4, 1, ingotThaumicSteel);
		researchThaumicElectrum = new FluxGearResearchItem(keyThaumicElectrum, key, new AspectList().add(METAL, 4).add(MAGIC, 4).add(GREED, 2).add(ENERGY, 2).add(ORDER, 1), -5, 5, 2, ingotThaumicElectrum);
		researchCotton = new FluxGearResearchItem(keyCotton, key, new AspectList().add(CLOTH, 4).add(ARMOR, 3).add(MAGIC, 3), -8, 4, 1, itemCottonEnchanted);
		researchCottonRobes = new FluxGearResearchItem(keyCottonRobes, key, new AspectList().add(CLOTH, 4).add(ARMOR, 3).add(MAGIC, 3), -9, 6, 1, new ItemStack(enchCottonRobe));
		researchPrimalRobes = new FluxGearResearchItem(keyPrimalRobes, key, new AspectList().add(MAGIC, 4).add(CLOTH, 4).add(ARMOR, 3).add(ENERGY, 2).add(SENSES, 2).add(ThaumcraftHelper.newPrimalAspectList(1)), -7, 6, 3, new ItemStack(primalRobe));
		researchPrimalPendant = new FluxGearResearchItem(keyPrimalPendant, key, new AspectList().add(MAGIC, 4).add(AURA, 4).add(TOOL, 3).add(ENERGY, 2).add(VOID, 2).add(ThaumcraftHelper.newPrimalAspectList(1)), -7, 8, 3, pendantPrimal);

		researchAspectOrbBasic = new FluxGearResearchItem(keyAspectOrbBasic, key, new AspectList().add(MAGIC, 4).add(TOOL, 4).add(CRAFT, 3).add(ENERGY, 3), 1, 4, 2, itemAspectOrbReceptorMakeshift);

		researchRunicInfuser = new FluxGearResearchItem(keyRunicInfuser, key, new AspectList().add(ENERGY, 4).add(MAGIC, 4).add(AURA, 2).add(CRAFT, 3).add(MECHANISM, 4), 1, 6, 2, itemArcaneSingularity);
		researchStabilizedSingularity = new FluxGearResearchItem(keyStabilizedSingularity, key, new AspectList().add(ENERGY, 3).add(MAGIC, 3).add(ENTROPY, 2).add(ORDER, 2).add(LIGHT, 2), -1, 6, 1, itemStabilizedSingularity);
		researchPrimalEssence = new FluxGearResearchItem(keyPrimalEssence, key, ThaumcraftHelper.newPrimalAspectList(3).add(MAGIC, 4).add(ENERGY, 2), -1, 5, 2, dustPrimalEssence);
		researchEnchGreatwood = new FluxGearResearchItem(keyEnchGreatwood, key, new AspectList().add(TREE, 3).add(MAGIC, 3).add(TOOL, 2), -1, 7, 1, plankGreatwoodEnchanted);
		researchAniPiston = new FluxGearResearchItem(keyAniPiston, key, new AspectList().add(AIR, 3).add(MECHANISM, 3).add(MOTION, 3), 0, 8, 1, itemAnimatedPiston);
		researchEnchSilverwood = new FluxGearResearchItem(keyEnchSilverwood, key, new AspectList().add(TREE, 3).add(MAGIC, 3).add(AURA, 3).add(ORDER, 3), 7, 8, 1, plankSilverwoodEnchanted);
		researchVoidbrass = new FluxGearResearchItem(keyVoidbrass, key, new AspectList().add(ELDRITCH, 4).add(METAL, 4).add(TOOL, 3).add(ENERGY, 2).add(CRAFT, 2), 4, 11, 2, ingotVoidbrass);
		researchConsSilverwood = new FluxGearResearchItem(keyConsSilverwood, key, new AspectList().add(AURA, 5).add(TREE, 4).add(MAGIC, 4).add(ORDER, 3).add(ENERGY, 2), 11, 9, 2, plankSilverwoodConsecrated);

		researchEldritchStone = new FluxGearResearchItem(keyEldritchStone, key, new AspectList().add(EARTH, 3).add(ELDRITCH, 3).add(DARKNESS, 2), 9, 4, 1, eldritchStone);
		researchVoidmetalWorking = new FluxGearResearchItem(keyVoidmetalWorking, key, new AspectList().add(METAL, 3).add(ELDRITCH, 3).add(MECHANISM, 3).add(ENERGY, 2), 11, 6, 1, itemEldritchCog);

		researchElementalPowders = new FluxGearResearchItem(keyElementalPowders, key, new AspectList().add(EXCHANGE, 4).add(FIRE, 2).add(COLD, 2).add(WEATHER, 2).add(ENTROPY, 2), 5, 9, 1, new ItemStack(Items.blaze_powder));

		researchWardenry = new FluxGearResearchItem(keyWardenry, key, new AspectList().add(aspectExcubitor, 4).add(MAGIC, 3).add(ELDRITCH, 2).add(ENERGY, 2), -2, 0, 2, excubituraRose);
		researchExcubituraPaste = new FluxGearResearchItem(keyExcubituraPaste, key, new AspectList().add(PLANT, 4).add(MAGIC, 4).add(aspectExcubitor, 1), -4, 2, 1, itemExcubituraPaste);
		researchWardencloth = new FluxGearResearchItem(keyWardencloth, key, new AspectList().add(MAGIC, 4).add(CLOTH, 4).add(ARMOR, 3).add(aspectExcubitor, 1), -6, 3, 1, itemWardencloth);
		researchArmorWardencloth = new FluxGearResearchItem(keyArmorWardencloth, key, new AspectList().add(ARMOR, 4).add(MAGIC, 4).add(CLOTH, 4).add(aspectExcubitor, 4), -8, 2, 1, new ItemStack(wardenclothTunic));

		researchExcubituraOil = new FluxGearResearchItem(keyExcubituraOil, key, new AspectList().add(MAGIC, 4).add(PLANT, 3).add(aspectExcubitor, 2).add(WATER, 3), -5, -1, 1, excubituraOil);
		researchWardenChain = new FluxGearResearchItem(keyWardenChain, key, new AspectList().add(METAL, 4).add(MAGIC, 4).add(ARMOR, 3).add(aspectExcubitor, 2), -7, -2, 1, chainWardenicBronze);
		researchArmorWardenChain = new FluxGearResearchItem(keyArmorWardenChain, key, new AspectList().add(ARMOR, 4).add(METAL, 4).add(MAGIC, 4).add(aspectExcubitor, 4), -9, -3, 1, new ItemStack(wardenicChainmail));

		researchPureOil = new FluxGearResearchItem(keyPureOil, key, new AspectList().add(MAGIC, 4).add(WATER, 4).add(aspectExcubitor, 3).add(ENERGY, 3), -6, -4, 2, excubituraOilPure);
		researchWardenSteel = new FluxGearResearchItem(keyWardenSteel, key, new AspectList().add(METAL, 5).add(MAGIC, 4).add(TOOL, 2).add(ARMOR, 2).add(aspectExcubitor, 3), -8, -5, 2, ingotWardenicSteel);
		researchWardenPlate = new FluxGearResearchItem(keyWardenPlate, key, new AspectList().add(METAL, 4).add(MAGIC, 3).add(ARMOR, 3).add(aspectExcubitor, 3), -10, -6, 2, plateWardenicSteel);
		researchArmorWardenSteel = new FluxGearResearchItem(keyArmorWardenSteel, key, new AspectList().add(ARMOR, 4).add(METAL, 4).add(MAGIC, 4).add(aspectExcubitor, 4), -12, -8, 2, new ItemStack(wardenicChestplate));
		researchWardenicObsidian = new FluxGearResearchItem(keyWardenicObsidian, key, new AspectList().add(EARTH, 4).add(CRYSTAL, 4).add(FIRE, 3).add(MAGIC, 3).add(ARMOR, 4).add(aspectExcubitor, 1), -3, -2, 2, wardenicObsidian);

		researchQuartz = new FluxGearResearchItem(keyQuartz, key, new AspectList().add(CRYSTAL, 4).add(MAGIC, 4).add(aspectExcubitor, 4).add(ENERGY, 3).add(TOOL, 2), -7, -7, 2, gemWardenicQuartz);
		researchWardenCrystal = new FluxGearResearchItem(keyWardenCrystal, key, new AspectList().add(MAGIC, 4).add(CRYSTAL, 4).add(aspectExcubitor, 4).add(ENERGY, 3).add(ORDER, 3), -9, -8, 2, gemWardenicCrystal);
		researchWardenBronze = new FluxGearResearchItem(keyWardenBronze, key, new AspectList().add(METAL, 5).add(MAGIC, 4).add(aspectExcubitor, 4).add(ARMOR, 3).add(TOOL, 3), -11, -10, 2, ingotWardenicRiftishBronze);
		researchWardenComposite = new FluxGearResearchItem(keyWardenComposite, key, new AspectList().add(METAL, 6).add(MAGIC, 4).add(ARMOR, 4).add(TOOL, 3).add(aspectExcubitor, 4), -12, -12, 2, plateWardenicComposite);
		researchWardenCompositePlate = new FluxGearResearchItem(keyWardenCompositePlate, key, new AspectList().add(METAL, 3).add(MAGIC, 2).add(aspectExcubitor, 1), -11, -14, 1, plateWardenicComposite);
		researchWardenCompositeFitting = new FluxGearResearchItem(keyWardenCompositeFitting, key, new AspectList().add(METAL, 5).add(MAGIC, 4).add(ARMOR, 4).add(aspectExcubitor, 4).add(ENERGY, 2), -9, -15, 3, plateWardenicCompositeConsecrated);
		researchArmorWardenComposite = new FluxGearResearchItem(keyArmorWardenComposite, key, new AspectList().add(ARMOR, 4).add(METAL, 4).add(aspectExcubitor, 4).add(MAGIC, 4).add(ORDER, 4), -7, -16, 3, new ItemStack(wardenicCompositeChestplate));

		researchWardenCrystalAwakened = new FluxGearResearchItem(keyWardenCrystalAwakened, key, ThaumcraftHelper.newPrimalAspectList(3).add(aspectExcubitor, 6).add(MAGIC, 6).add(CRYSTAL, 4).add(ENERGY, 6), -5, -10, 3, gemAwakenedWardenicCrystal);
		researchWardenEssenceAwakened = new FluxGearResearchItem(keyWardenEssenceAwakened, key, ThaumcraftHelper.newPrimalAspectList(2).add(aspectExcubitor, 6).add(MAGIC, 6).add(CRAFT, 4).add(ENERGY, 6), -5, -12, 3, itemEssenceOfAwakening);

		if (ThaumRevConfig.getFluxed) {
			researchMagneoturgy = new FluxGearResearchItem(keyMagneoturgy, key, new AspectList().add(aspectFluxus, 4).add(MAGIC, 3).add(aspectMagnes, 2).add(ENERGY, 2), 0, -2, 2, new ItemStack(Items.redstone));
			researchRedstoneReduced = new FluxGearResearchItem(keyRedstoneReduced, key, new AspectList().add(aspectFluxus, 1).add(ENERGY, 4).add(EXCHANGE, 3), -1, -4, 1, dustRedstoneReduced);
			researchArcaneRedsolder = new FluxGearResearchItem(keyArcaneRedsolder, key, new AspectList().add(METAL, 4).add(aspectFluxus, 1).add(EXCHANGE, 3).add(ENERGY, 2), 1, -4, 1, ingotArcaneRedsolder);
			researchCapCoreRedsolder = new FluxGearResearchItem(keyCapCoreRedsolder, key, new AspectList().add(ENERGY, 4).add(aspectFluxus, 2).add(VOID, 2).add(METAL, 1), 0, -6, 2, itemCapacitorCoreArcaneRedsolder);
			researchRedcloth = new FluxGearResearchItem(keyRedcloth, key, new AspectList().add(CLOTH, 4).add(ENERGY, 4).add(aspectFluxus, 2).add(MAGIC, 1), -2, -6, 1, itemRedcloth);
			researchArmorFluxRobes = new FluxGearResearchItem(keyArmorFluxRobes, key, new AspectList().add(ARMOR, 4).add(ENERGY, 4).add(CLOTH, 4).add(aspectFluxus, 4), -3, -4, 1, new ItemStack(fluxRobeVest));
		}

		key = RESEARCH_KEY_METAL;

		researchTransmutationFe = new DummyResearchItem(keyTransmutationFe, key, transFe, catAlch, -33, -8, nuggetIronTC).registerResearchItem();
		researchTransmutationSn = new DummyResearchItem(keyTransmutationSn, key, transSn, catAlch, -36, -14, nuggetTinTC).setParents(keyTransmutationFe).registerResearchItem();
		researchTransmutationAg = new DummyResearchItem(keyTransmutationAg, key, transAg, catAlch, -38, -12, nuggetSilverTC).setParents(keyTransmutationFe).registerResearchItem();
		researchTransmutationPb = new DummyResearchItem(keyTransmutationPb, key, transPb, catAlch, -34, -15, nuggetLeadTC).setParents(keyTransmutationFe).registerResearchItem();
		researchTransmutationAu = new DummyResearchItem(keyTransmutationAu, key, transAu, catAlch, -32, -15, new ItemStack(Items.gold_nugget)).setParents(keyTransmutationFe).registerResearchItem();

		researchTransmutationNi = new FluxGearResearchItem(keyTransmutationNi, key, new AspectList().add(METAL, 3).add(EXCHANGE, 2).add(VOID, 1), -39, -10, 1, nuggetNickel);
		researchTransmutationAl = new FluxGearResearchItem(keyTransmutationAl, key, new AspectList().add(METAL, 3).add(EXCHANGE, 2).add(ORDER, 1), -27, -10, 1, nuggetAluminium);
		researchTransmutationNd = new FluxGearResearchItem(keyTransmutationNd, key, new AspectList().add(METAL, 3).add(EXCHANGE, 2).add(ENERGY, 1), -34, -1, 1, nuggetNeodymium);
		researchTransmutationZn = new FluxGearResearchItem(keyTransmutationZn, key, new AspectList().add(METAL, 3).add(EXCHANGE, 2).add(CRYSTAL, 1), -39, -17, 1, nuggetZinc);
		researchTransmutationAs = new FluxGearResearchItem(keyTransmutationAs, key, new AspectList().add(METAL, 3).add(EXCHANGE, 2).add(POISON, 1), -36, -21, 1, nuggetArsenic);
		researchTransmutationSb = new FluxGearResearchItem(keyTransmutationSb, key, new AspectList().add(METAL, 3).add(EXCHANGE, 2).add(POISON, 1), -35, -19, 1, nuggetAntimony);
		researchTransmutationBi = new FluxGearResearchItem(keyTransmutationBi, key, new AspectList().add(METAL, 3).add(EXCHANGE, 2).add(ORDER, 1), -35, -17, 1, nuggetBismuth);
		researchTransmutationW = new FluxGearResearchItem(keyTransmutationW, key, new AspectList().add(METAL, 4).add(EXCHANGE, 2).add(MECHANISM, 1).add(ARMOR, 1), -33, -17, 1, nuggetTungsten);
		researchTransmutationLu = new FluxGearResearchItem(keyTransmutationLu, key, new AspectList().add(METAL, 3).add(EXCHANGE, 2).add(EARTH, 1), -33, 1, 1, nuggetLutetium);
		researchTransmutationPd = new FluxGearResearchItem(keyTransmutationPd, key, new AspectList().add(METAL, 3).add(EXCHANGE, 3), -41, -10, 1, nuggetPalladium);
		if (OreDictionary.doesOreNameExist(NUGGET + PT)) {
			researchTransmutationPt = new FluxGearResearchItem(keyTransmutationPt, key, new AspectList().add(METAL, 3).add(EXCHANGE, 3).add(GREED, 1), -31, -21, 1, OreDictionary.getOres(NUGGET + PT).get(0));
		}
		researchTransmutationOs = new FluxGearResearchItem(keyTransmutationOs, key, new AspectList().add(METAL, 3).add(EXCHANGE, 2).add(ARMOR, 1).add(ORDER, 1), -31, -23, 1, nuggetOsmium);
		researchTransmutationIr = new FluxGearResearchItem(keyTransmutationIr, key, new AspectList().add(METAL, 3).add(EXCHANGE, 2).add(ARMOR, 1).add(ORDER, 1).add(MECHANISM, 1).add(ENERGY, 1), -29, -24, 1, nuggetIridium);

		researchClusterFe = new DummyResearchItem(keyClusterFe, key, pureFe, catAlch, 8, -12, clusterIron).registerResearchItem();
		researchClusterCu = new DummyResearchItem(keyClusterCu, key, pureCu, catAlch, 12, -17, clusterCopper).setParents(keyClusterFe).registerResearchItem();
		researchClusterSn = new DummyResearchItem(keyClusterSn, key, pureSn, catAlch, 14, -15, clusterTin).setParents(keyClusterFe).registerResearchItem();
		researchClusterPb = new DummyResearchItem(keyClusterPb, key, purePb, catAlch, 14, -13, clusterLead).setParents(keyClusterFe).registerResearchItem();
		researchClusterAu = new DummyResearchItem(keyClusterAu, key, pureAu, catAlch, 13, -11, clusterGold).setParents(keyClusterFe).registerResearchItem();

		researchClusterNi = new FluxGearResearchItem(keyClusterNi, key, new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 2).add(VOID, 1), 8, -18, 1, clusterNickel);
		if (OreDictionary.doesOreNameExist(ORE + AL)) {
			researchClusterAl = new FluxGearResearchItem(keyClusterAl, key, new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 3), 11, -7, 1, clusterAluminium);
		}
		researchClusterYPO = new FluxGearResearchItem(keyClusterYPO, key, new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 2).add(EARTH, 2).add(ENERGY, 1), 2, -13, 1, clusterXenotime);
		researchClusterZn = new FluxGearResearchItem(keyClusterZn, key, new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 2).add(CRYSTAL, 1), 20, -15, 1, clusterZinc);
		researchClusterBi = new FluxGearResearchItem(keyClusterBi, key, new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 3), 16, -14, 1, clusterBismuth);
		researchClusterCuAs = new FluxGearResearchItem(keyClusterCuAs, key, new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 2).add(EXCHANGE, 1).add(POISON, 1), 13, -19, 1, clusterTennantite);
		researchClusterCuSb = new FluxGearResearchItem(keyClusterCuSb, key, new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 2).add(EXCHANGE, 1).add(POISON, 1), 15, -17, 1, clusterTetrahedrite);
		researchClusterW = new FluxGearResearchItem(keyClusterW, key, new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 2).add(MECHANISM, 1).add(ARMOR, 1), 16, -12, 1, clusterTungsten);
		if (OreDictionary.doesOreNameExist(ORE + PT)) {
			researchClusterPt = new FluxGearResearchItem(keyClusterPt, key, new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 2).add(EXCHANGE, 1).add(GREED, 1), 6, -20, 1, clusterPlatinum);
		}
		researchClusterIrOs = new FluxGearResearchItem(keyClusterIrOs, key, new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 4).add(ARMOR, 2).add(MECHANISM, 1).add(ENERGY, 1), 7, -24, 1, clusterIridosmium);

	}

	public static void initResearch() {
		researchThaumRev.setRound().setSpecial().setAutoUnlock().setSiblings(keyMaterial);
		researchMaterial.setRound().setSiblings(keyMinerals, keyAlloys).setStub();
		researchMinerals.setRound().setStub();
		researchAlloys.setRound().setStub();

		researchThaumicBronze.setParents(keyAlloys).setParentsHidden(keyThaumium);
		researchBronzeChain.setParents(keyThaumicBronze).setSecondary();
		researchArmorBronzeChain.setParents(keyBronzeChain).setSecondary();
		researchThaumicRBronze.setParents(keyThaumicBronze).setParentsHidden(keyInfusion);
		researchThaumicSteel.setParents(keyAlloys).setParentsHidden(keyInfusion).setItemTriggers(OreDictHelper.getOreArray("ingotSteel"));
		researchThaumicElectrum.setParents(keyAlloys).setParentsHidden(keyThaumium);
		researchCotton.setParentsHidden(keyFabric);
		researchCottonRobes.setParents(keyCotton).setParentsHidden(keyGoggles).setSecondary();
		researchPrimalRobes.setParents(keyCottonRobes, keyThaumicRBronze, keyThaumicElectrum).setParentsHidden(keyPrimalEssence, keyGoggles, keyNitor, keyInfusion, keyEnchant, keyFocusPrimal, keyVoidRobes);
		researchPrimalPendant.setParents(keyPrimalRobes).setParentsHidden(keyVisAmulet);

		researchAspectOrbBasic.setParents(keyMaterial).setParentsHidden(keyThaumicBronze, keyThaumium);

		researchRunicInfuser.setParents(keyMaterial).setParentsHidden(keyThaumium, keyAlumentum, keyNitor, keyVisPower);
		researchStabilizedSingularity.setParents(keyRunicInfuser).setSecondary();
		researchPrimalEssence.setParents(keyStabilizedSingularity).setParentsHidden(keyInfusion);
		researchEnchGreatwood.setParents(keyRunicInfuser).setSecondary();
		researchAniPiston.setParents(keyRunicInfuser).setSecondary();
		researchEnchSilverwood.setParents(keyRunicInfuser).setSecondary();
		researchVoidbrass.setParents(keyRunicInfuser);
		researchConsSilverwood.setParents(keyEnchSilverwood);

		researchEldritchStone.setParentsHidden(keyVoidSeed).setSpecial().setStub().setAutoUnlock();
		researchVoidmetalWorking.setParents(keyEldritchStone).setParentsHidden(keyVoidmetal, keyVoidbrass).setSecondary();

		researchElementalPowders.setParents(keyAlchManufacture);

		researchWardenry.setParents(keyThaumRev).setRound().setSpecial().setHidden().setItemTriggers(excubituraRose);

		researchExcubituraPaste.setConcealed().setParents(keyWardenry);
		researchWardencloth.setParents(keyExcubituraPaste, keyCotton);
		researchArmorWardencloth.setParents(keyWardencloth).setSecondary();

		researchExcubituraOil.setConcealed().setParents(keyExcubituraPaste).setParentsHidden(keyAlumentum);
		researchWardenChain.setParents(keyExcubituraOil);
		researchArmorWardenChain.setParents(keyWardenChain).setSecondary();

		researchPureOil.setParents(keyExcubituraOil).setParentsHidden(keyRunicInfuser);
		researchWardenSteel.setParents(keyPureOil).setParentsHidden(keyThaumicSteel);
		researchWardenicObsidian.setParents(keyPureOil).setParentsHidden(keyRunicInfuser).setSecondary().setHidden().setItemTriggers(new ItemStack(Blocks.obsidian));
		researchWardenPlate.setParents(keyWardenSteel).setParentsHidden(keyWardencloth, keyThaumicHammermill, keyEnchSilverwood);
		researchArmorWardenSteel.setParents(keyWardenPlate).setSecondary();


		//WIP
		researchQuartz.setParents(keyPureOil).setParentsHidden(keyInfusion);
		researchWardenCrystal.setParents(keyQuartz).setSecondary();
		researchWardenBronze.setParents(keyWardenCrystal).setParentsHidden(keyThaumicRBronze);
		researchWardenComposite.setParents(keyWardenBronze).setParentsHidden(keyThaumicBronze, keyWardenSteel);
		researchWardenCompositePlate.setParents(keyWardenComposite).setParentsHidden(keyThaumicHammermill).setSecondary();
		researchWardenCompositeFitting.setParents(keyWardenCompositePlate).setParentsHidden(keyWardencloth, keyEnchSilverwood);
		researchArmorWardenComposite.setParents(keyWardenCompositeFitting).setSecondary();

		researchWardenCrystalAwakened.setParents(keyWardenCrystal);
		researchWardenEssenceAwakened.setParents(keyWardenCrystalAwakened).setParentsHidden(keyWardenCompositeFitting);

		if (ThaumRevConfig.getFluxed) {
			researchMagneoturgy.setParents(keyThaumRev).setRound().setSpecial().setHidden().setItemTriggers(new ItemStack(Items.redstone), new ItemStack(Blocks.redstone_block));
			researchRedstoneReduced.setConcealed().setParents(keyMagneoturgy);
			researchArcaneRedsolder.setParents(keyRedstoneReduced).setSecondary();
			researchCapCoreRedsolder.setParents(keyArcaneRedsolder);
			researchRedcloth.setParents(keyCapCoreRedsolder, keyRedstoneReduced).setParentsHidden(keyCotton);
			researchArmorFluxRobes.setParents(keyRedcloth).setParentsHidden(keyGoggles);
		}

		researchTransmutationNi.setParents(keyTransmutationFe).setSecondary();
		researchTransmutationAl.setParents(keyTransmutationFe).setSecondary();
		researchTransmutationNd.setParents(keyTransmutationFe).setSecondary();
		researchTransmutationZn.setParents(keyTransmutationSn).setSecondary();
		researchTransmutationAs.setParents(keyTransmutationSn).setSecondary();
		researchTransmutationSb.setParents(keyTransmutationSn).setSecondary();
		researchTransmutationBi.setParents(keyTransmutationSn, keyTransmutationPb).setSecondary();
		researchTransmutationW.setParents(keyTransmutationPb, keyTransmutationAu).setHidden().setSecondary();
		researchTransmutationLu.setParents(keyTransmutationNd).setSecondary();
		researchTransmutationPd.setParents(keyTransmutationNi, keyTransmutationAg).setSecondary();
		if (OreDictionary.doesOreNameExist(NUGGET + PT)) {
			researchTransmutationPt.setParents(keyTransmutationAu).setParentsHidden(keyTransmutationPd).setHidden().setItemTriggers(getPlatinumTriggers()).setSecondary();
			researchTransmutationOs.setParents(keyTransmutationW, keyTransmutationPt).setSecondary();
		} else {
			researchTransmutationOs.setParents(keyTransmutationW).setParentsHidden(keyTransmutationAu, keyTransmutationPd).setSecondary();
		}
		researchTransmutationIr.setParents(keyTransmutationOs).setHidden().setSecondary();

		researchClusterNi.setParents(keyClusterFe).setSecondary();
		if (OreDictionary.doesOreNameExist(ORE + AL)) {
			researchClusterAl.setParents(keyClusterFe).setSecondary();
		}
		researchClusterYPO.setParents(keyClusterFe).setSecondary();
		researchClusterZn.setParents(keyClusterSn).setSecondary();
		researchClusterBi.setParents(keyClusterSn, keyClusterPb).setSecondary();
		researchClusterCuAs.setParents(keyClusterSn, keyClusterCu).setSecondary();
		researchClusterCuSb.setParents(keyClusterSn, keyClusterCu).setSecondary();
		researchClusterW.setParents(keyClusterPb, keyClusterAu).setSecondary();
		if (OreDictionary.doesOreNameExist(ORE + PT)) {
			researchClusterPt.setParents(keyClusterNi).setParentsHidden(keyClusterAu).setSecondary();
			researchClusterIrOs.setParents(keyClusterPt).setParentsHidden(keyClusterW).setSecondary();
		} else {
			researchClusterIrOs.setParentsHidden(keyClusterW).setSecondary();
		}

		/*if (researchLevel == 0) { //EASY-MODE
			((FluxGearResearchItem) researchThaumRev).addSiblings(keyAniPiston);
			researchAniPiston.setStub();
			researchThaumicBronze.setSiblings(keyBronzeChain, keyArmorBronzeChain);
			researchBronzeChain.setStub();
			researchArmorBronzeChain.setStub();
			researchRunicInfuser.setSiblings(keyEnchSilverwood);
			researchEnchSilverwood.setStub();
			researchExcubituraPaste.setParentsHidden(keyCotton, keyGoggles).setSiblings(keyWardencloth, keyArmorWardencloth);
			researchWardencloth.setSecondary().setStub();
			researchArmorWardencloth.setStub();
			researchExcubituraOil.setParentsHidden(keyBronzeChain, keyGoggles).setSiblings(keyWardenChain, keyArmorWardenChain);
			researchWardenChain.setSecondary().setStub();
			researchArmorWardenChain.setStub();
			researchPureOil.setParentsHidden(keyThaumicBronze, keyInfusion).setSiblings(keyWardenSteel);
			researchWardenSteel.setStub();
			((FluxGearResearchItem) researchWardenPlate).addParentsHidden(keyGoggles).setSiblings(keyArmorWardenSteel);
			researchArmorWardenSteel.setStub();
		} else if (researchLevel == 2) { //HARD-MODE*/
			researchAniPiston.setParentsHidden(keyBellows);
			researchWardencloth.setParentsHidden(keyCotton);
			researchWardenChain.setParentsHidden(keyBronzeChain);
			researchArmorWardenChain.setParentsHidden(keyArmorBronzeChain);
			researchWardenSteel.setParentsHidden(keyThaumicBronze, keyInfusion);
		/*} else { //NORMAL-MODE
			researchBronzeChain.setSiblings(keyArmorBronzeChain);
			researchArmorBronzeChain.setStub();
			researchWardencloth.setParentsHidden(keyCotton, keyGoggles).setSiblings(keyArmorWardencloth);
			researchArmorWardencloth.setStub();
			researchWardenChain.setParentsHidden(keyBronzeChain, keyGoggles).setSiblings(keyArmorWardenChain);
			researchArmorWardenChain.setStub();
			researchWardenSteel.setParentsHidden(keyThaumicBronze, keyInfusion);
			((FluxGearResearchItem) researchWardenPlate).addParentsHidden(keyGoggles).setSiblings(keyArmorWardenSteel);
			researchArmorWardenSteel.setStub();
		}*/
	}

	public static void registerResearch() {
		researchThaumRev.registerResearchItem();
		researchMaterial.registerResearchItem();
		researchMinerals.registerResearchItem();
		//if (enableAlloys()) {
			researchAlloys.registerResearchItem();
		//}

		researchThaumicBronze.registerResearchItem();
		researchBronzeChain.registerResearchItem();
		researchArmorBronzeChain.registerResearchItem();
		researchThaumicRBronze.registerResearchItem();
		researchThaumicSteel.registerResearchItem();
		researchThaumicElectrum.registerResearchItem();
		researchCotton.registerResearchItem();
		researchCottonRobes.registerResearchItem();
		researchPrimalRobes.registerResearchItem();
		researchPrimalPendant.registerResearchItem();

		researchAspectOrbBasic.registerResearchItem();

		researchRunicInfuser.registerResearchItem();
		researchStabilizedSingularity.registerResearchItem();
		researchPrimalEssence.registerResearchItem();
		researchEnchGreatwood.registerResearchItem();
		researchAniPiston.registerResearchItem();
		researchEnchSilverwood.registerResearchItem();
		researchVoidbrass.registerResearchItem();
		researchConsSilverwood.registerResearchItem();

		researchEldritchStone.registerResearchItem();
		researchVoidmetalWorking.registerResearchItem();

		researchElementalPowders.registerResearchItem();

		researchWardenry.registerResearchItem();
		researchExcubituraPaste.registerResearchItem();
		researchWardencloth.registerResearchItem();
		researchArmorWardencloth.registerResearchItem();

		researchExcubituraOil.registerResearchItem();
		researchWardenChain.registerResearchItem();
		researchArmorWardenChain.registerResearchItem();

		researchPureOil.registerResearchItem();
		researchWardenSteel.registerResearchItem();
		researchWardenicObsidian.registerResearchItem();
		researchWardenPlate.registerResearchItem();
		researchArmorWardenSteel.registerResearchItem();

		researchQuartz.registerResearchItem();
		researchWardenCrystal.registerResearchItem();
		researchWardenBronze.registerResearchItem();
		researchWardenComposite.registerResearchItem();
		researchWardenCompositePlate.registerResearchItem();
		researchWardenCompositeFitting.registerResearchItem();
		researchArmorWardenComposite.registerResearchItem();

		researchWardenCrystalAwakened.registerResearchItem();
		researchWardenEssenceAwakened.registerResearchItem();

		if (ThaumRevConfig.getFluxed) {
			researchMagneoturgy.registerResearchItem();
			researchRedstoneReduced.registerResearchItem();
			researchArcaneRedsolder.registerResearchItem();
			researchCapCoreRedsolder.registerResearchItem();
			researchRedcloth.registerResearchItem();
			researchArmorFluxRobes.registerResearchItem();
		}

		researchTransmutationNi.registerResearchItem();
		researchTransmutationAl.registerResearchItem();
		researchTransmutationNd.registerResearchItem();
		researchTransmutationZn.registerResearchItem();
		researchTransmutationAs.registerResearchItem();
		researchTransmutationSb.registerResearchItem();
		researchTransmutationBi.registerResearchItem();
		researchTransmutationW.registerResearchItem();
		researchTransmutationLu.registerResearchItem();
		researchTransmutationPd.registerResearchItem();
		if (OreDictionary.doesOreNameExist(NUGGET + PT)) {
			researchTransmutationPt.registerResearchItem();
		}
		researchTransmutationOs.registerResearchItem();
		researchTransmutationIr.registerResearchItem();

		researchClusterNi.registerResearchItem();
		if (OreDictionary.doesOreNameExist(ORE + AL)) {
			researchClusterAl.registerResearchItem();
		}
		researchClusterYPO.registerResearchItem();
		researchClusterZn.registerResearchItem();
		researchClusterBi.registerResearchItem();
		researchClusterCuAs.registerResearchItem();
		researchClusterCuSb.registerResearchItem();
		researchClusterW.registerResearchItem();
		if (OreDictionary.doesOreNameExist(ORE + PT)) {
			researchClusterPt.registerResearchItem();
		}
		researchClusterIrOs.registerResearchItem();

	}

	public static void setPages() {
		researchThaumRev.setPages(new ResearchPage("0"));
		researchMaterial.setPages(getMaterialPages());
		researchMinerals.setPages(getMineralPages());
		researchAlloys.setPages(getAlloyPages());

		researchThaumicBronze.setPages(new ResearchPage("0"), new ResearchPage(recipeThaumicBronzeRaw), new ResearchPage(recipeThaumicBronzeCoated), new ResearchPage(coatedThaumicBronze), new ResearchPage("1"));
		researchBronzeChain.setPages(new ResearchPage("0"), new ResearchPage(recipeThaumicBronzeChain));
		researchArmorBronzeChain.setPages(new ResearchPage("0"), new ResearchPage(recipeBronzeChainHelmet), new ResearchPage(recipeBronzeChainmail), new ResearchPage(recipeBronzeChainGreaves), new ResearchPage(recipeBronzeChainBoots));
		researchThaumicRBronze.setPages(new ResearchPage("0"), new ResearchPage(recipeThaumicRBronze));
		researchThaumicSteel.setPages(new ResearchPage("0"), new ResearchPage(recipeThaumicSteel), new ResearchPage(recipeBlockThaumicSteel));
		researchThaumicElectrum.setPages(new ResearchPage("0"), new ResearchPage(recipeThaumicElectrum));
		researchCotton.setPages(new ResearchPage("0"), new ResearchPage(recipeCottonFiber), new ResearchPage(recipeCottonFabric), new ResearchPage(recipeTreatedCotton), new ResearchPage(recipeEnchantedCotton));
		researchCottonRobes.setPages(new ResearchPage("0"), new ResearchPage("1"), new ResearchPage(recipeEnchCottonGoggles), new ResearchPage(recipeEnchCottonRobes), new ResearchPage(recipeEnchCottonPants), new ResearchPage(recipeEnchCottonBoots));
		researchPrimalRobes.setPages(new ResearchPage("0"), new ResearchPage("1"), new ResearchPage("2"), new ResearchPage(recipePrimalGoggles), new ResearchPage(recipePrimalRobes), new ResearchPage(recipePrimalPants), new ResearchPage(recipePrimalBoots));
		researchPrimalPendant.setPages(new ResearchPage("0"), new ResearchPage(recipePrimalPendant));

		researchAspectOrbBasic.setPages(new ResearchPage("0"), new ResearchPage(recipeOrbReceptorBasic));

		researchRunicInfuser.setPages(new ResearchPage("0"), new ResearchPage(recipeArcaneSingularity));
		researchStabilizedSingularity.setPages(new ResearchPage("0"), new ResearchPage(recipeStableSingularity));
		researchPrimalEssence.setPages(new ResearchPage("0"), new ResearchPage(recipePrimalEssence));
		researchEnchGreatwood.setPages(new ResearchPage("0"), new ResearchPage(recipeEnchGreatwood), new ResearchPage(recipeEnchGreatwoodShaft));
		researchAniPiston.setPages(new ResearchPage("0"), new ResearchPage(recipeAniPiston));
		researchEnchSilverwood.setPages(new ResearchPage("0"), new ResearchPage(recipeEnchSilverwood), new ResearchPage(recipeEnchSilverwoodShaft));
		researchVoidbrass.setPages(new ResearchPage("0"), new ResearchPage(recipeVoidbrass));
		researchConsSilverwood.setPages(new ResearchPage("0"), new ResearchPage(recipeConsSilverwood), new ResearchPage(recipeConsSilverwoodShaft));

		researchEldritchStone.setPages(new ResearchPage("0"), new ResearchPage(recipeEldritchStone));
		researchVoidmetalWorking.setPages(new ResearchPage("0"), new ResearchPage(recipeEldritchCog), new ResearchPage("1"), new ResearchPage(recipeEldritchKeystone));

		researchElementalPowders.setPages(new ResearchPage("0"), new ResearchPage(recipesPods), new ResearchPage(recipePowderCinderpearl), new ResearchPage(recipePowderShiverpearl), new ResearchPage(recipePowderStormypearl), new ResearchPage(recipePowderStonypearl));

		researchWardenry.setPages(new ResearchPage("0"), new ResearchPage("1"));
		researchExcubituraPaste.setPages(new ResearchPage("0"), new ResearchPage(recipeExcubituraPaste));
		researchWardencloth.setPages(new ResearchPage("0"), new ResearchPage(recipeExcubituraFabric), new ResearchPage("1"), new ResearchPage(recipeWardencloth));
		researchArmorWardencloth.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenclothSkullcap), new ResearchPage(recipeWardenclothTunic), new ResearchPage(recipeWardenclothPants), new ResearchPage(recipeWardenclothBoots));

		researchExcubituraOil.setPages(new ResearchPage("0"), new ResearchPage(recipeExcubituraOilUnproc), new ResearchPage(recipeExcubituraOil));
		researchWardenChain.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenBronzeChain), new ResearchPage(recipePrimalBronzeChain), new ResearchPage(recipeWardenBronze), new ResearchPage(recipeWardenBronzePlate));
		researchArmorWardenChain.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenicChainHelmet), new ResearchPage(recipeWardenicChainmail), new ResearchPage(recipeWardenicChainGreaves), new ResearchPage(recipeWardenicChainBoots));

		researchPureOil.setPages(new ResearchPage("0"), new ResearchPage(recipePureOil));
		researchWardenSteel.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenSteel));
		researchWardenPlate.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenSteelChain), new ResearchPage(recipeWardenSteelChainOiled), new ResearchPage(recipeWardenSteelPlate), new ResearchPage(recipeDetailedSteelPlate), new ResearchPage(recipeRunicSteelPlate), new ResearchPage(recipesConsecratedSteelPlate));
		researchArmorWardenSteel.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenicPlateHelmet), new ResearchPage(recipeWardenicChestplate), new ResearchPage(recipeWardenicPlateGreaves), new ResearchPage(recipeWardenicPlateBoots));
		if (LoadedHelper.isThermalFoundationLoaded) {
			researchWardenicObsidian.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenicHardener), new ResearchPage(recipeWardenicHardenerAlt));
		} else {
			researchWardenicObsidian.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenicHardener));
		}

		researchQuartz.setPages(new ResearchPage("0"), new ResearchPage("1"), new ResearchPage(recipeWardenicQuartz), new ResearchPage(recipeWardenicQuartzInf), new ResearchPage(recipeWardenicQuartzDust), new ResearchPage(recipeWardenicQuartzReconst), new ResearchPage(recipeQuartzBlock), /*new ResearchPage(recipeQuartzChiseled), new ResearchPage(recipeQuartzPillar), new ResearchPage(recipeQuartzSlab), new ResearchPage(recipeQuartzStair),*/ new ResearchPage(recipeQuartzDeblock)/*, new ResearchPage(recipeQuartzDeslab), new ResearchPage(recipeQuartzDestair), new ResearchPage(recipeQuartzResetChiseled), new ResearchPage(recipeQuartzResetPillar)*/);
		researchWardenCrystal.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenicCrystal), new ResearchPage(recipeWardenicCrystalDust), new ResearchPage(recipeWardenicCrystalReconst), new ResearchPage("1"), new ResearchPage(recipeWardenicBinder), new ResearchPage(recipeBinderTiny), new ResearchPage(recipeBinderCombine), new ResearchPage(recipeActivatedWardenicCrystal));
		researchWardenBronze.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenRBronze));
		researchWardenComposite.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenMetal), new ResearchPage(recipeWardenicCompositeRaw), new ResearchPage(recipeWardenicCompositeIngot));
		researchWardenCompositePlate.setPages(new ResearchPage("0"), new ResearchPage(recipeAluDenseTemp), new ResearchPage(recipeWardenicCompositePlate), new ResearchPage(recipeFittedCompositePlate), new ResearchPage(recipeDetailedCompositePlate));
		researchWardenCompositeFitting.setPages(new ResearchPage("0"), new ResearchPage(recipeRunicCompositePlate), new ResearchPage(recipeConsecratedCompositePlate), new ResearchPage(recipePrimalCompositePlate)/**/);
		researchArmorWardenComposite.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenicCompositeHelmet), new ResearchPage(recipeWardenicCompositeChestplate), new ResearchPage(recipeWardenicCompositeGreaves), new ResearchPage(recipeWardenicCompositeBoots));

		researchWardenCrystalAwakened.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenicCrystalAwakened));
		researchWardenEssenceAwakened.setPages(new ResearchPage("0"), new ResearchPage(recipeWardenicEssenceAwakened));

		if (ThaumRevConfig.getFluxed) {
			researchMagneoturgy.setPages(new ResearchPage("0"));
			researchRedstoneReduced.setPages(new ResearchPage("0"), new ResearchPage(recipeRedstoneReduced));
			researchArcaneRedsolder.setPages(new ResearchPage("0"), new ResearchPage(recipeDullRedsolder), new ResearchPage(recipeArcaneRedsolder), new ResearchPage("1"));
			researchCapCoreRedsolder.setPages(new ResearchPage("0"), new ResearchPage(recipeCapCoreRedsolder));
			researchRedcloth.setPages(new ResearchPage("0"), new ResearchPage(recipeRedstoneFabric), new ResearchPage(recipeRedcloth), new ResearchPage(recipeRedclothCapacitive));
			researchArmorFluxRobes.setPages(new ResearchPage("0"), new ResearchPage(recipeFluxRobesGoggles), new ResearchPage(recipeFluxRobesVest), new ResearchPage(recipeFluxRobesPants), new ResearchPage(recipeFluxRobesShoes));
		}

		researchTransmutationNi.setPages(new ResearchPage("0"), new ResearchPage(recipeTransNickel));
		researchTransmutationAl.setPages(new ResearchPage("0"), new ResearchPage(recipeTransAluminium));
		researchTransmutationNd.setPages(new ResearchPage("0"), new ResearchPage(recipeTransNeodymium));
		researchTransmutationZn.setPages(new ResearchPage("0"), new ResearchPage(recipeTransZinc));
		researchTransmutationAs.setPages(new ResearchPage("0"), new ResearchPage(recipeTransArsenic));
		researchTransmutationSb.setPages(new ResearchPage("0"), new ResearchPage(recipeTransAntimony));
		researchTransmutationBi.setPages(new ResearchPage("0"), new ResearchPage(recipeTransBismuth));
		researchTransmutationW.setPages(new ResearchPage("0"), new ResearchPage(recipeTransTungsten));
		researchTransmutationLu.setPages(new ResearchPage("0"), new ResearchPage(recipeTransLutetium));
		researchTransmutationPd.setPages(new ResearchPage("0"), new ResearchPage(recipeTransPalladium));
		if (OreDictionary.doesOreNameExist(NUGGET + PT)) {
			researchTransmutationPt.setPages(new ResearchPage("0"), new ResearchPage(recipeTransPlatinum));
		}
		researchTransmutationOs.setPages(new ResearchPage("0"), new ResearchPage(recipeTransOsmium));
		researchTransmutationIr.setPages(new ResearchPage("0"), new ResearchPage(recipeTransIridium));

		researchClusterNi.setPages(new ResearchPage("0"), new ResearchPage(recipeClusterNickel));
		if (OreDictionary.doesOreNameExist(ORE + AL)) {
			researchClusterAl.setPages(new ResearchPage("0"), new ResearchPage(recipeClusterAluminium));
		}
		researchClusterYPO.setPages(new ResearchPage("0"), new ResearchPage(recipeClusterXenotime));
		researchClusterZn.setPages(new ResearchPage("0"), new ResearchPage(recipeClusterZinc));
		researchClusterBi.setPages(new ResearchPage("0"), new ResearchPage(recipeClusterBismuth));
		researchClusterCuAs.setPages(new ResearchPage("0"), new ResearchPage(recipeClusterTennantite));
		researchClusterCuSb.setPages(new ResearchPage("0"), new ResearchPage(recipeClusterTertahedrite));
		researchClusterW.setPages(new ResearchPage("0"), new ResearchPage(recipeClusterTungsten));
		if (OreDictionary.doesOreNameExist(ORE + PT)) {
			researchClusterPt.setPages(new ResearchPage("0"), new ResearchPage(recipeClusterPlatinum));
		}
		researchClusterIrOs.setPages(new ResearchPage("0"), new ResearchPage(recipeClusterIridosmium));
	}

	public static void timeyWimey() {
		//FRESH COPY-PASTA FROM MAGIC BEES FOR THOSE CRAZY PEOPLE WHO DON'T USE MAGIC BEES!!!!
		addAspects(new ItemStack(Items.clock), new AspectList().add(tempus, 4));
		addAspects(new ItemStack(Items.repeater), new AspectList().add(tempus, 2));
	}

	public static void loadGeneralAspects() {
		addAspects(excubituraRose, new AspectList().add(PLANT, 2).add(MAGIC, 2).add(aspectExcubitor, 3));
		addAspects(wildCotton, new AspectList().add(PLANT, 3).add(CLOTH, 3));
		addAspects(wildThistle, new AspectList().add(PLANT, 3).add(WEAPON, 2).add(SENSES, 1));
		addAspects(shiverpearl, new AspectList().add(PLANT, 2).add(COLD, 1).add(MAGIC, 1).add(WATER, 2));
		addAspects(stormypearl, new AspectList().add(PLANT, 2).add(WEATHER, 1).add(MAGIC, 1).add(AIR, 2));
		addAspects(stonypearl, new AspectList().add(PLANT, 2).add(ENTROPY, 1).add(MAGIC, 1).add(EARTH, 2));

		addAspects(oreChalcocite, new AspectList().add(METAL, 3).add(EARTH, 1).add(EXCHANGE, 1));
		addAspects(oreSphalerite, new AspectList().add(METAL, 3).add(EARTH, 1).add(CRYSTAL, 1));
		addAspects(oreCassiterite, new AspectList().add(METAL, 3).add(ENTROPY, 1).add(CRYSTAL, 1));
		addAspects(oreMillerite, new AspectList().add(METAL, 3).add(EARTH, 1).add(VOID, 1));
		addAspects(oreNativeSilver, new AspectList().add(METAL, 3).add(ENTROPY, 1).add(GREED, 1));
		addAspects(oreGalena, new AspectList().add(METAL, 3).add(ENTROPY, 1).add(ORDER, 1));
		addAspects(oreXenotime, new AspectList().add(METAL, 3).add(EARTH, 2).add(ENERGY, 1));
		addAspects(oreWolframite, new AspectList().add(METAL, 3).add(EARTH, 1).add(ARMOR, 1).add(MECHANISM, 1));
		addAspects(oreIridosmium, new AspectList().add(METAL, 3).add(ORDER, 1).add(ENTROPY, 1).add(ARMOR, 2).add(LIGHT, 1).add(ENERGY, 1));
		addAspects(oreBismuthinite, new AspectList().add(METAL, 3).add(ORDER, 1).add(ENTROPY, 1));
		addAspects(oreTennantite, new AspectList().add(METAL, 3).add(EXCHANGE, 1).add(POISON, 1));
		addAspects(oreTetrahedrite, new AspectList().add(METAL, 3).add(EXCHANGE, 1).add(POISON, 1));
		addAspects(orePyrope, new AspectList().add(CRYSTAL, 3).add(EARTH, 1).add(FIRE, 3).add(ENERGY, 1)); //TODO: 2 IGNIS, 1 INFERNUS IF FM IS LOADED
		addAspects(oreDioptase, new AspectList().add(CRYSTAL, 3).add(EARTH, 1).add(ORDER, 2).add(EXCHANGE, 1).add(ENERGY, 1));
		addAspects(oreJethrineSapphire, new AspectList().add(CRYSTAL, 3).add(EARTH, 1).add(ENERGY, 4));

		addAspects(oreGravelChalcocite, new AspectList().add(METAL, 3).add(EARTH, 1).add(EXCHANGE, 1));
		addAspects(oreGravelSphalerite, new AspectList().add(METAL, 3).add(EARTH, 1).add(CRYSTAL, 1));
		addAspects(oreGravelCassiterite, new AspectList().add(METAL, 3).add(ENTROPY, 1).add(CRYSTAL, 1));
		addAspects(oreGravelMillerite, new AspectList().add(METAL, 3).add(EARTH, 1).add(VOID, 1));
		addAspects(oreGravelNativeSilver, new AspectList().add(METAL, 3).add(ENTROPY, 1).add(GREED, 1));
		addAspects(oreGravelGalena, new AspectList().add(METAL, 3).add(ENTROPY, 1).add(ORDER, 1));
		addAspects(oreGravelXenotime, new AspectList().add(METAL, 3).add(EARTH, 2).add(ENERGY, 1));
		addAspects(oreGravelWolframite, new AspectList().add(METAL, 3).add(EARTH, 1).add(ARMOR, 1).add(MECHANISM, 1));
		addAspects(oreGravelIridosmium, new AspectList().add(METAL, 3).add(ORDER, 1).add(ENTROPY, 1).add(ARMOR, 2).add(LIGHT, 1).add(ENERGY, 1));
		addAspects(oreGravelBismuthinite, new AspectList().add(METAL, 3).add(ORDER, 1).add(ENTROPY, 1));
		addAspects(oreGravelTennantite, new AspectList().add(METAL, 3).add(EXCHANGE, 1).add(POISON, 1));
		addAspects(oreGravelTetrahedrite, new AspectList().add(METAL, 3).add(EXCHANGE, 1).add(POISON, 1));

		addAspects(itemCotton, new AspectList().add(CLOTH, 1).add(PLANT, 1));
		addAspects(itemCottonFiber, new AspectList().add(CLOTH, 1));
		addAspects(itemCottonFabric, new AspectList().add(CLOTH, 8).add(PLANT, 1));
		addAspects(itemCottonTreated, new AspectList().add(CLOTH, 9).add(PLANT, 1).add(MAGIC, 2).add(ORDER, 1));
		addAspects(itemCottonEnchanted, new AspectList().add(CLOTH, 9).add(PLANT, 1).add(MAGIC, 3).add(ORDER, 1).add(ARMOR, 1));

		addAspects(itemThistleLeaf, new AspectList().add(PLANT, 2).add(WEAPON, 1));
		addAspects(itemThistleFlower, new AspectList().add(PLANT, 2).add(WEAPON, 1).add(SENSES, 1));

		addAspects(itemArcaneSingularity, new AspectList().add(ENERGY, 8).add(FIRE, 6).add(MAGIC, 4).add(ENTROPY, 2).add(ORDER, 4).add(LIGHT, 2));
		addAspects(itemStabilizedSingularity, new AspectList().add(ENERGY, 12).add(FIRE, 8).add(ORDER, 12).add(MAGIC, 8).add(ENTROPY, 2).add(LIGHT, 2));
		addAspects(itemAnimatedPiston, new AspectList().add(METAL, 3).add(AIR, 3).add(ENERGY, 1).add(MOTION, 5));

		addAspects(plankGreatwoodEnchanted, new AspectList().add(TREE, 1).add(MAGIC, 2).add(ORDER, 1));

		addAspects(itemPodCinderpearl, new AspectList().add(PLANT, 2).add(FIRE, 2).add(MAGIC, 2));
		addAspects(itemPodShiverpearl, new AspectList().add(PLANT, 2).add(COLD, 1).add(MAGIC, 1).add(WATER, 2));
		addAspects(itemPodStormypearl, new AspectList().add(PLANT, 2).add(WEATHER, 1).add(MAGIC, 1).add(AIR, 2));
		addAspects(itemPodStonypearl, new AspectList().add(PLANT, 2).add(ENTROPY, 1).add(MAGIC, 1).add(EARTH, 2));

		addAspects(seedExcubitura, new AspectList().add(PLANT, 1).add(aspectExcubitor, 1));
		addAspects(seedCotton, new AspectList().add(PLANT, 1));
		addAspects(seedThistle, new AspectList().add(PLANT, 1));
		addAspects(seedShimmerleaf, new AspectList().add(PLANT, 1).add(MAGIC, 1));
		addAspects(seedCinderpearl, new AspectList().add(PLANT, 1).add(FIRE, 1));
		addAspects(seedShiverpearl, new AspectList().add(PLANT, 1).add(WATER, 1));
		addAspects(seedStormypearl, new AspectList().add(PLANT, 1).add(AIR, 1));
		addAspects(seedStonypearl, new AspectList().add(PLANT, 1).add(EARTH, 1));

		addAspects(itemExcubituraPetal, new AspectList().add(PLANT, 2).add(MAGIC, 2).add(aspectExcubitor, 2));
		addAspects(itemExcubituraPaste, new AspectList().add(PLANT, 3).add(MAGIC, 4).add(aspectExcubitor, 4));
		addAspects(itemFabricExcubitura, new AspectList().add(CLOTH, 9).add(MAGIC, 2).add(aspectExcubitor, 1));
		addAspects(itemFabricExcubitura, new AspectList().add(CLOTH, 10).add(MAGIC, 2).add(aspectExcubitor, 2).add(ARMOR, 1));

		addAspects(excubituraOilRaw, new AspectList().add(aspectExcubitor, 16).add(MAGIC, 12).add(PLANT, 4).add(ORDER, 1));
		addAspects(excubituraOil, new AspectList().add(aspectExcubitor, 16).add(MAGIC, 12).add(PLANT, 4).add(ORDER, 6).add(ENERGY, 2));

	}

	public static void loadMetalAspects() {
		addAspects(blockCopper, new AspectList().add(METAL, 20).add(EXCHANGE, 6));
		addAspects(blockZinc, new AspectList().add(METAL, 20).add(CRYSTAL, 6));
		addAspects(blockTin, new AspectList().add(METAL, 20).add(CRYSTAL, 6));
		addAspects(blockNickel, new AspectList().add(METAL, 20).add(VOID, 6));
		addAspects(blockSilver, new AspectList().add(METAL, 20).add(GREED, 6));
		addAspects(blockLead, new AspectList().add(METAL, 20).add(ORDER, 6));
		addAspects(blockLutetium, new AspectList().add(METAL, 20).add(EARTH, 6).add(VOID, 6));
		addAspects(blockTungsten, new AspectList().add(METAL, 20).add(MECHANISM, 6).add(ARMOR, 6));
		addAspects(blockIridium, new AspectList().add(METAL, 20).add(ARMOR, 6).add(ORDER, 6).add(MECHANISM, 6).add(ENERGY, 6));
		addAspects(blockBismuth, new AspectList().add(METAL, 20).add(ORDER, 6));
		addAspects(blockArsenic, new AspectList().add(METAL, 20).add(POISON, 6));
		addAspects(blockAntimony, new AspectList().add(METAL, 20).add(POISON, 6));
		addAspects(blockNeodymium, new AspectList().add(METAL, 20).add(EARTH, 6).add(ENERGY, 6));
		addAspects(blockOsmium, new AspectList().add(METAL, 20).add(ARMOR, 6).add(ORDER, 6));
		addAspects(blockPalladium, new AspectList().add(METAL, 20).add(GREED, 6).add(EXCHANGE, 6));
		addAspects(blockAluminium, new AspectList().add(METAL, 20).add(ORDER, 6));

		addAspects(blockBrass, new AspectList().add(METAL, 20).add(MECHANISM, 6));
		addAspects(blockBronze, new AspectList().add(METAL, 20).add(TOOL, 6));
		addAspects(blockArsenicalBronze, new AspectList().add(METAL, 20).add(TOOL, 6));
		addAspects(blockAntimonialBronze, new AspectList().add(METAL, 20).add(ARMOR, 6));
		addAspects(blockBismuthBronze, new AspectList().add(METAL, 20).add(TOOL, 6));
		addAspects(blockMithril, new AspectList().add(METAL, 20).add(TOOL, 6).add(ARMOR,6));
		addAspects(blockAlumiuiumBronze, new AspectList().add(METAL, 20).add(CRAFT, 6));
		addAspects(blockCupronickel, new AspectList().add(METAL, 20).add(MECHANISM, 6));
		addAspects(blockRiftishBronze, new AspectList().add(METAL, 20).add(CRAFT, 6).add(TOOL, 6));
		addAspects(blockConstantan, new AspectList().add(METAL, 20).add(MECHANISM, 6));
		addAspects(blockInvar, new AspectList().add(METAL, 20).add(ARMOR, 6));
		addAspects(blockElectrum, new AspectList().add(METAL, 20).add(GREED, 6));
		addAspects(blockWardenicMetal, new AspectList().add(METAL, 20).add(aspectExcubitor, 6));
		addAspects(blockDullRedsolder, new AspectList().add(METAL, 20).add(ENERGY, 6));
		addAspects(blockRedsolder, new AspectList().add(METAL, 20).add(aspectFluxus, 6));

		addAspects(blockThaumicElectrum, new AspectList().add(METAL, 20).add(GREED, 6).add(ENERGY, 6).add(MAGIC, 13));
		addAspects(blockThaumicRiftishBronze, new AspectList().add(METAL, 20).add(CRAFT, 6).add(TOOL, 6).add(MAGIC, 13));
		addAspects(blockSteel, new AspectList().add(METAL, 27).add(ORDER, 6));
		addAspects(blockThaumicSteel, new AspectList().add(METAL, 27).add(ORDER, 6).add(MAGIC, 13));
		addAspects(blockVoidbrass, new AspectList().add(METAL, 13).add(VOID, 13).add(DARKNESS, 6).add(TOOL, 6).add(ELDRITCH, 6));
		addAspects(blockVoidsteel, new AspectList().add(METAL, 20).add(VOID, 13).add(DARKNESS, 6).add(ORDER, 6).add(ELDRITCH, 6));
		addAspects(blockVoidtungsten, new AspectList().add(METAL, 20).add(VOID, 20).add(DARKNESS, 13).add(ELDRITCH, 13).add(MECHANISM, 6).add(ARMOR, 6));
		addAspects(blockVoidcupronickel, new AspectList().add(METAL, 20).add(VOID, 13).add(DARKNESS, 6).add(MECHANISM, 6).add(ELDRITCH, 6));


		addAspects(ingotCopper, new AspectList().add(METAL, 3).add(EXCHANGE, 1));
		addAspects(ingotZinc, new AspectList().add(METAL, 3).add(CRYSTAL, 1));
		addAspects(ingotTin, new AspectList().add(METAL, 3).add(CRYSTAL, 1));
		addAspects(ingotNickel, new AspectList().add(METAL, 3).add(VOID, 1));
		addAspects(ingotSilver, new AspectList().add(METAL, 3).add(GREED, 1));
		addAspects(ingotLead, new AspectList().add(METAL, 3).add(ORDER, 1));
		addAspects(ingotLutetium, new AspectList().add(METAL, 3).add(EARTH, 1).add(VOID, 1));
		addAspects(ingotTungsten, new AspectList().add(METAL, 3).add(MECHANISM, 1).add(ARMOR, 1));
		addAspects(ingotIridium, new AspectList().add(METAL, 3).add(ARMOR, 1).add(ORDER, 1).add(MECHANISM, 1).add(ENERGY, 1));
		addAspects(ingotBismuth, new AspectList().add(METAL, 3).add(ORDER, 1));
		addAspects(ingotArsenic, new AspectList().add(METAL, 3).add(POISON, 1));
		addAspects(ingotAntimony, new AspectList().add(METAL, 3).add(POISON, 1));
		addAspects(ingotNeodymium, new AspectList().add(METAL, 3).add(EARTH, 1).add(ENERGY, 1));
		addAspects(ingotOsmium, new AspectList().add(METAL, 3).add(ARMOR, 1).add(ORDER, 1));
		addAspects(ingotPalladium, new AspectList().add(METAL, 3).add(GREED, 1).add(EXCHANGE, 1));
		addAspects(ingotAluminium, new AspectList().add(METAL, 3).add(ORDER, 1));

		addAspects(ingotBrass, new AspectList().add(METAL, 3).add(MECHANISM, 1));
		addAspects(ingotBronze, new AspectList().add(METAL, 3).add(TOOL, 1));
		addAspects(ingotArsenicalBronze, new AspectList().add(METAL, 3).add(TOOL, 1));
		addAspects(ingotAntimonialBronze, new AspectList().add(METAL, 3).add(ARMOR, 1));
		addAspects(ingotBismuthBronze, new AspectList().add(METAL, 3).add(TOOL, 1));
		addAspects(ingotMithril, new AspectList().add(METAL, 3).add(TOOL, 1).add(ARMOR, 1));
		addAspects(ingotAluminiumBronze, new AspectList().add(METAL, 3).add(CRAFT, 1));
		addAspects(ingotCupronickel, new AspectList().add(METAL, 3).add(MECHANISM, 1));
		addAspects(ingotRiftishBronze, new AspectList().add(METAL, 3).add(CRAFT, 1).add(TOOL, 1));
		addAspects(ingotConstantan, new AspectList().add(METAL, 3).add(MECHANISM, 1));
		addAspects(ingotInvar, new AspectList().add(METAL, 3).add(ARMOR, 1));
		addAspects(ingotElectrum, new AspectList().add(METAL, 3).add(GREED, 1));
		addAspects(ingotWardenicMetal, new AspectList().add(METAL, 3).add(aspectExcubitor, 1));
		addAspects(ingotDullRedsolder, new AspectList().add(METAL, 3).add(ENERGY, 1));
		addAspects(ingotRedsolder, new AspectList().add(METAL, 3).add(aspectFluxus, 1));

		addAspects(ingotThaumicElectrum, new AspectList().add(METAL, 3).add(GREED, 1).add(ENERGY, 1).add(MAGIC, 2));
		addAspects(ingotThaumicRiftishBronze, new AspectList().add(METAL, 3).add(CRAFT, 1).add(TOOL, 1).add(MAGIC, 2));
		addAspects(ingotSteel, new AspectList().add(METAL, 4).add(ORDER, 1));
		addAspects(ingotThaumicSteel, new AspectList().add(METAL, 4).add(ORDER, 1).add(MAGIC, 2));
		addAspects(ingotVoidbrass, new AspectList().add(METAL, 2).add(VOID, 2).add(DARKNESS, 1).add(TOOL, 1).add(ELDRITCH, 1));
		addAspects(ingotVoidsteel, new AspectList().add(METAL, 3).add(VOID, 2).add(DARKNESS, 1).add(ORDER, 1).add(ELDRITCH, 1));
		addAspects(ingotVoidtungsten, new AspectList().add(METAL, 3).add(VOID, 3).add(DARKNESS, 2).add(ELDRITCH, 2).add(MECHANISM, 1).add(ARMOR, 1));
		addAspects(ingotVoidcupronickel, new AspectList().add(METAL, 3).add(VOID, 2).add(DARKNESS, 1).add(MECHANISM, 1).add(ELDRITCH, 1));




		addAspects(gemPyrope, new AspectList().add(CRYSTAL, 4).add(FIRE, 3).add(ENERGY, 1)); //TODO: 2 IGNIS, 1 INFERNUS IF FM IS LOADED
		addAspects(gemDioptase, new AspectList().add(CRYSTAL, 4).add(ORDER, 2).add(EXCHANGE, 1).add(ENERGY, 1));
		addAspects(gemJethrineSapphire, new AspectList().add(CRYSTAL, 4).add(ENERGY, 4));
		addAspects(gemJethrinePyroptase, new AspectList().add(CRYSTAL, 4).add(FIRE, 3).add(ORDER, 2).add(EXCHANGE, 1).add(ENERGY, 5)); //TODO: 2 IGNIS, 1 INFERNUS IF FM IS LOADED



		addAspects(nuggetCopper, new AspectList().add(METAL, 1));
		addAspects(nuggetZinc, new AspectList().add(METAL, 1));
		addAspects(nuggetTin, new AspectList().add(METAL, 1));
		addAspects(nuggetNickel, new AspectList().add(METAL, 1));
		addAspects(nuggetSilver, new AspectList().add(METAL, 1));
		addAspects(nuggetLead, new AspectList().add(METAL, 1));
		addAspects(nuggetLutetium, new AspectList().add(METAL, 1));
		addAspects(nuggetTungsten, new AspectList().add(METAL, 1));
		addAspects(nuggetIridium, new AspectList().add(METAL, 1).add(ORDER, 1));
		addAspects(nuggetBismuth, new AspectList().add(METAL, 1));
		addAspects(nuggetArsenic, new AspectList().add(METAL, 1));
		addAspects(nuggetAntimony, new AspectList().add(METAL, 1));
		addAspects(nuggetNeodymium, new AspectList().add(METAL, 1));
		addAspects(nuggetOsmium, new AspectList().add(METAL, 1));
		addAspects(nuggetPalladium, new AspectList().add(METAL, 1));
		addAspects(nuggetAluminium, new AspectList().add(METAL, 1));

		addAspects(nuggetBrass, new AspectList().add(METAL, 1));
		addAspects(nuggetBronze, new AspectList().add(METAL, 1));
		addAspects(nuggetArsenicalBronze, new AspectList().add(METAL, 1));
		addAspects(nuggetAntimonialBronze, new AspectList().add(METAL, 1));
		addAspects(nuggetBismuthBronze, new AspectList().add(METAL, 1));
		addAspects(nuggetMithril, new AspectList().add(METAL, 1));
		addAspects(nuggetAluminiumBronze, new AspectList().add(METAL, 1));
		addAspects(nuggetCupronickel, new AspectList().add(METAL, 1));
		addAspects(nuggetRiftishBronze, new AspectList().add(METAL, 1).add(ORDER, 1));
		addAspects(nuggetConstantan, new AspectList().add(METAL, 1));
		addAspects(nuggetInvar, new AspectList().add(METAL, 1));
		addAspects(nuggetElectrum, new AspectList().add(METAL, 1));
		addAspects(nuggetWardenicMetal, new AspectList().add(METAL, 1));
		addAspects(nuggetDullRedsolder, new AspectList().add(METAL, 1));
		addAspects(nuggetRedsolder, new AspectList().add(METAL, 1));

		addAspects(nuggetThaumicElectrum, new AspectList().add(METAL, 1));
		addAspects(nuggetThaumicRiftishBronze, new AspectList().add(METAL, 1));
		addAspects(nuggetSteel, new AspectList().add(METAL, 1));
		addAspects(nuggetThaumicSteel, new AspectList().add(METAL, 1));
		addAspects(nuggetVoidbrass, new AspectList().add(METAL, 1));
		addAspects(nuggetVoidsteel, new AspectList().add(METAL, 1));
		addAspects(nuggetVoidtungsten, new AspectList().add(METAL, 1).add(VOID, 1));
		addAspects(nuggetVoidcupronickel, new AspectList().add(METAL, 1));




		addAspects(dustCopper, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(EXCHANGE, 1));
		addAspects(dustZinc, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(CRYSTAL, 1));
		addAspects(dustTin, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(CRYSTAL, 1));
		addAspects(dustNickel, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(VOID, 1));
		addAspects(dustSilver, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(GREED, 1));
		addAspects(dustLead, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(ORDER, 1));
		addAspects(dustLutetium, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(EARTH, 1).add(VOID, 1));
		addAspects(dustTungsten, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(MECHANISM, 1).add(ARMOR, 1));
		addAspects(dustIridium, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(ARMOR, 1).add(ORDER, 1).add(MECHANISM, 1).add(ENERGY, 1));
		addAspects(dustBismuth, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(ORDER, 1));
		addAspects(dustArsenic, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(POISON, 1));
		addAspects(dustAntimony, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(POISON, 1));
		addAspects(dustNeodymium, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(EARTH, 1).add(ENERGY, 1));
		addAspects(dustOsmium, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(ARMOR, 1).add(ORDER, 1));
		addAspects(dustPalladium, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(GREED, 1).add(EXCHANGE, 1));
		addAspects(dustAluminium, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(ORDER, 1));

		addAspects(dustBrass, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(MECHANISM, 1));
		addAspects(dustBronze, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(TOOL, 1));
		addAspects(dustArsenicalBronze, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(TOOL, 1));
		addAspects(dustAntimonialBronze, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(ARMOR, 1));
		addAspects(dustBismuthBronze, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(TOOL, 1));
		addAspects(dustMithril, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(TOOL, 1).add(ARMOR, 1));
		addAspects(dustAluminiumBronze, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(CRAFT, 1));
		addAspects(dustCupronickel, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(MECHANISM, 1));
		addAspects(dustRiftishBronze, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(CRAFT, 1).add(TOOL, 1));
		addAspects(dustConstantan, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(MECHANISM, 1));
		addAspects(dustInvar, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(ARMOR, 1));
		addAspects(dustElectrum, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(GREED, 1));
		addAspects(dustWardenicMetal, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(aspectExcubitor, 1));
		addAspects(dustDullRedsolder, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(ENERGY, 1));
		addAspects(dustRedsolder, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(aspectFluxus, 1));

		addAspects(dustThaumicElectrum, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(GREED, 1).add(ENERGY, 1).add(MAGIC, 2));
		addAspects(dustThaumicRiftishBronze, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(CRAFT, 1).add(TOOL, 1).add(MAGIC, 2));
		addAspects(dustSteel, new AspectList().add(METAL, 3).add(ENTROPY, 1).add(ORDER, 1));
		addAspects(dustThaumicSteel, new AspectList().add(METAL, 3).add(ENTROPY, 1).add(ORDER, 1).add(MAGIC, 2));
		addAspects(dustVoidbrass, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(VOID, 1).add(DARKNESS, 1).add(TOOL, 1).add(ELDRITCH, 1));
		addAspects(dustVoidsteel, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(VOID, 2).add(DARKNESS, 1).add(ORDER, 1).add(ELDRITCH, 1));
		addAspects(dustVoidtungsten, new AspectList().add(METAL, 3).add(VOID, 3).add(DARKNESS, 2).add(ELDRITCH, 2).add(MECHANISM, 1).add(ARMOR, 1));
		addAspects(dustVoidcupronickel, new AspectList().add(METAL, 2).add(ENTROPY, 1).add(VOID, 2).add(DARKNESS, 1).add(MECHANISM, 1).add(ELDRITCH, 1));


		addAspects(tinyCopper, new AspectList().add(METAL, 1));
		addAspects(tinyZinc, new AspectList().add(METAL, 1));
		addAspects(tinyTin, new AspectList().add(METAL, 1));
		addAspects(tinyNickel, new AspectList().add(METAL, 1));
		addAspects(tinySilver, new AspectList().add(METAL, 1));
		addAspects(tinyLead, new AspectList().add(METAL, 1));
		addAspects(tinyLutetium, new AspectList().add(METAL, 1));
		addAspects(tinyTungsten, new AspectList().add(METAL, 1));
		addAspects(tinyIridium, new AspectList().add(METAL, 1).add(ORDER, 1));
		addAspects(tinyBismuth, new AspectList().add(METAL, 1));
		addAspects(tinyArsenic, new AspectList().add(METAL, 1));
		addAspects(tinyAntimony, new AspectList().add(METAL, 1));
		addAspects(tinyNeodymium, new AspectList().add(METAL, 1));
		addAspects(tinyOsmium, new AspectList().add(METAL, 1));
		addAspects(tinyPalladium, new AspectList().add(METAL, 1));
		addAspects(tinyAluminium, new AspectList().add(METAL, 1));

		addAspects(tinyBrass, new AspectList().add(METAL, 1));
		addAspects(tinyBronze, new AspectList().add(METAL, 1));
		addAspects(tinyArsenicalBronze, new AspectList().add(METAL, 1));
		addAspects(tinyAntimonialBronze, new AspectList().add(METAL, 1));
		addAspects(tinyBismuthBronze, new AspectList().add(METAL, 1));
		addAspects(tinyMithril, new AspectList().add(METAL, 1));
		addAspects(tinyAluminiumBronze, new AspectList().add(METAL, 1));
		addAspects(tinyCupronickel, new AspectList().add(METAL, 1));
		addAspects(tinyRiftishBronze, new AspectList().add(METAL, 1).add(ENTROPY, 1));
		addAspects(tinyConstantan, new AspectList().add(METAL, 1));
		addAspects(tinyInvar, new AspectList().add(METAL, 1));
		addAspects(tinyElectrum, new AspectList().add(METAL, 1));
		addAspects(tinyWardenicMetal, new AspectList().add(METAL, 1));
		addAspects(tinyDullRedsolder, new AspectList().add(METAL, 1));
		addAspects(tinyRedsolder, new AspectList().add(METAL, 1));

		addAspects(tinyThaumicElectrum, new AspectList().add(METAL, 1));
		addAspects(tinyThaumicRiftishBronze, new AspectList().add(METAL, 1));
		addAspects(tinySteel, new AspectList().add(METAL, 1));
		addAspects(tinyThaumicSteel, new AspectList().add(METAL, 1));
		addAspects(tinyVoidbrass, new AspectList().add(METAL, 1));
		addAspects(tinyVoidsteel, new AspectList().add(METAL, 1));
		addAspects(tinyVoidtungsten, new AspectList().add(METAL, 1).add(VOID, 1));
		addAspects(tinyVoidcupronickel, new AspectList().add(METAL, 1));


		addAspects(plateCopper, new AspectList().add(METAL, 2).add(ARMOR, 1).add(EXCHANGE, 1));
		addAspects(plateZinc, new AspectList().add(METAL, 2).add(ARMOR, 1).add(CRYSTAL, 1));
		addAspects(plateTin, new AspectList().add(METAL, 2).add(ARMOR, 1).add(CRYSTAL, 1));
		addAspects(plateNickel, new AspectList().add(METAL, 2).add(ARMOR, 1).add(VOID, 1));
		addAspects(plateSilver, new AspectList().add(METAL, 2).add(ARMOR, 1).add(GREED, 1));
		addAspects(plateLead, new AspectList().add(METAL, 2).add(ARMOR, 1).add(ORDER, 1));
		addAspects(plateLutetium, new AspectList().add(METAL, 2).add(ARMOR, 1).add(EARTH, 1).add(VOID, 1));
		addAspects(plateTungsten, new AspectList().add(METAL, 2).add(ARMOR, 1).add(MECHANISM, 1).add(ARMOR, 1));
		addAspects(plateIridium, new AspectList().add(METAL, 2).add(ARMOR, 2).add(ORDER, 1).add(MECHANISM, 1).add(ENERGY, 1));
		addAspects(plateBismuth, new AspectList().add(METAL, 2).add(ARMOR, 1).add(ORDER, 1));
		addAspects(plateArsenic, new AspectList().add(METAL, 2).add(ARMOR, 1).add(POISON, 1));
		addAspects(plateAntimony, new AspectList().add(METAL, 2).add(ARMOR, 1).add(POISON, 1));
		addAspects(plateNeodymium, new AspectList().add(METAL, 2).add(ARMOR, 1).add(EARTH, 1).add(ENERGY, 1));
		addAspects(plateOsmium, new AspectList().add(METAL, 2).add(ARMOR, 2).add(ORDER, 1));
		addAspects(platePalladium, new AspectList().add(METAL, 2).add(ARMOR, 1).add(GREED, 1).add(EXCHANGE, 1));
		addAspects(plateAluminium, new AspectList().add(METAL, 2).add(ARMOR, 1).add(ORDER, 1));

		addAspects(plateBrass, new AspectList().add(METAL, 2).add(ARMOR, 1).add(MECHANISM, 1));
		addAspects(plateBronze, new AspectList().add(METAL, 2).add(ARMOR, 1).add(TOOL, 1));
		addAspects(plateArsenicalBronze, new AspectList().add(METAL, 2).add(ARMOR, 1).add(TOOL, 1));
		addAspects(plateAntimonialBronze, new AspectList().add(METAL, 2).add(ARMOR, 2));
		addAspects(plateBismuthBronze, new AspectList().add(METAL, 2).add(ARMOR, 1).add(TOOL, 1));
		addAspects(plateMithril, new AspectList().add(METAL, 2).add(ARMOR, 2).add(TOOL, 1));
		addAspects(plateAluminiumBronze, new AspectList().add(METAL, 2).add(ARMOR, 1).add(CRAFT, 1));
		addAspects(plateCupronickel, new AspectList().add(METAL, 2).add(ARMOR, 1).add(MECHANISM, 1));
		addAspects(plateRiftishBronze, new AspectList().add(METAL, 2).add(ARMOR, 1).add(CRAFT, 1).add(TOOL, 1));
		addAspects(plateConstantan, new AspectList().add(METAL, 2).add(ARMOR, 1).add(MECHANISM, 1));
		addAspects(plateInvar, new AspectList().add(METAL, 2).add(ARMOR, 2));
		addAspects(plateElectrum, new AspectList().add(METAL, 2).add(ARMOR, 1).add(GREED, 1));
		addAspects(plateWardenicMetal, new AspectList().add(METAL, 2).add(ARMOR, 1).add(aspectExcubitor, 1));
		addAspects(plateDullRedsolder, new AspectList().add(METAL, 2).add(ARMOR, 1).add(ENERGY, 1));
		addAspects(plateRedsolder, new AspectList().add(METAL, 2).add(ARMOR, 1).add(aspectFluxus, 1));

		addAspects(plateThaumicElectrum, new AspectList().add(METAL, 2).add(ARMOR, 1).add(GREED, 1).add(ENERGY, 1).add(MAGIC, 2));
		addAspects(plateThaumicRiftishBronze, new AspectList().add(METAL, 2).add(ARMOR, 1).add(CRAFT, 1).add(TOOL, 1).add(MAGIC, 2));
		addAspects(plateSteel, new AspectList().add(METAL, 3).add(ARMOR, 1).add(ORDER, 1));
		addAspects(plateThaumicSteel, new AspectList().add(METAL, 3).add(ARMOR, 1).add(ORDER, 1).add(MAGIC, 2));
		addAspects(plateVoidbrass, new AspectList().add(METAL, 2).add(ARMOR, 1).add(VOID, 1).add(DARKNESS, 1).add(TOOL, 1).add(ELDRITCH, 1));
		addAspects(plateVoidsteel, new AspectList().add(METAL, 2).add(ARMOR, 1).add(VOID, 2).add(DARKNESS, 1).add(ORDER, 1).add(ELDRITCH, 1));
		addAspects(plateVoidtungsten, new AspectList().add(METAL, 3).add(VOID, 2).add(DARKNESS, 2).add(ELDRITCH, 2).add(MECHANISM, 1).add(ARMOR, 2));
		addAspects(plateVoidcupronickel, new AspectList().add(METAL, 2).add(ARMOR, 1).add(VOID, 2).add(DARKNESS, 1).add(MECHANISM, 1).add(ELDRITCH, 1));
	}

	public static void loadEquipmentAspects() {

	}

	public static ResearchPage[] getMaterialPages() {
		List<ResearchPage> list = new ArrayList<ResearchPage>(3);
		list.add(new ResearchPage("shaft"));
		list.add(new ResearchPage(recipeGreatwoodShaft));

		list.add(new ResearchPage("salis"));
		list.add(new ResearchPage(new IRecipe[] {recipeSalisTiny, recipeSalis}));

		list.add(new ResearchPage("primal"));
		//list.add(new ResearchPage());

		ResearchPage[] pages = new ResearchPage[list.size()];
		list.toArray(pages);
		return pages;
	}

	public static ResearchPage[] getMineralPages() {
		List<ResearchPage> list = new ArrayList<ResearchPage>(20);
		list.add(new ResearchPage("0"));
		list.add(new ResearchPage("1"));
		list.add(new ResearchPage("cu"));
		if (obviousSmelting) {
			list.add(new ResearchPage(oreChalcocite));
		}
		list.add(new ResearchPage("zn"));
		if (obviousSmelting) {
			list.add(new ResearchPage(oreSphalerite));
		}
		list.add(new ResearchPage("sn"));
		if (obviousSmelting) {
			list.add(new ResearchPage(oreCassiterite));
		}
		list.add(new ResearchPage("ni"));
		if (obviousSmelting) {
			list.add(new ResearchPage(oreMillerite));
		}
		list.add(new ResearchPage("ag"));
		if (obviousSmelting) {
			list.add(new ResearchPage(oreNativeSilver));
		}
		list.add(new ResearchPage("pb"));
		if (obviousSmelting) {
			list.add(new ResearchPage(oreGalena));
		}
		list.add(new ResearchPage("ypo"));
		if (obviousSmelting) {
			list.add(new ResearchPage(oreXenotime));
		}
		list.add(new ResearchPage("w"));
		list.add(new ResearchPage("iros"));
		list.add(new ResearchPage("bi"));
		if (obviousSmelting) {
			list.add(new ResearchPage(oreBismuthinite));
		}
		list.add(new ResearchPage("cuas"));
		if (obviousSmelting) {
			list.add(new ResearchPage(oreTennantite));
		}
		list.add(new ResearchPage("cusb"));
		if (obviousSmelting) {
			list.add(new ResearchPage(oreTetrahedrite));
		}
		list.add(new ResearchPage("pyrope"));
		list.add(new ResearchPage("dioptase"));
		list.add(new ResearchPage("fluonic"));
		list.add(new ResearchPage("pdal"));
		list.add(new ResearchPage("s1"));
		list.add(new ResearchPage("s2"));

		ResearchPage[] pages = new ResearchPage[list.size()];
		list.toArray(pages);
		return pages;
	}

	public static ResearchPage[] getAlloyPages() {
		List<ResearchPage> list = new ArrayList<ResearchPage>(8);
		List<ResearchPage> smelting = new ArrayList<ResearchPage>(8);
		List<IRecipe> dusts = new ArrayList<IRecipe>(8);
		List<IRecipe> tiny = new ArrayList<IRecipe>(8);

		boolean start = false;

		if (enableBrass) {
			start = true;
			list.add(new ResearchPage("0"));
			list.add(new ResearchPage(recipeCuZn));
			smelting.add(new ResearchPage(rawBrass));
			dusts.add(recDustCuZn);
		}
		if (enableBronze) {
			if (!start) {
				list.add(new ResearchPage("a"));
				start = true;
			} else {
				list.add(new ResearchPage("1"));
			}
			list.add(new ResearchPage(recipeCuSn));
			smelting.add(new ResearchPage(rawBronze));
			dusts.add(recDustCuSn);
		}
		if (enableBismuthBronze) {
			if (!start) {
				list.add(new ResearchPage("b"));
				start = true;
			} else {
				list.add(new ResearchPage("2"));
			}
			list.add(new ResearchPage(recipeCuZnBi));
			smelting.add(new ResearchPage(rawBismuthBronze));
			dusts.add(recDustCuZnBi[0]);
			dusts.add(recDustCuZnBi[1]);
		}
		if (enableMithril) {
			if (!start) {
				list.add(new ResearchPage("c"));
				start = true;
			} else {
				list.add(new ResearchPage("3"));
			}
			list.add(new ResearchPage(recipeCuAsSb));
			smelting.add(new ResearchPage(rawMithril));
			dusts.add(recDustCuAsSb);
		}
		if (enableAlBronze) {
			if (!start) {
				list.add(new ResearchPage("d"));
				start = true;
			} else {
				list.add(new ResearchPage("4"));
			}
			list.add(new ResearchPage(recipeCuAl));
			smelting.add(new ResearchPage(rawAluminiumBronze));
			dusts.add(recDustCuAl);
		}
		if (enableCupronickel) {
			if (!start) {
				list.add(new ResearchPage("e"));
				start = true;
			} else {
				list.add(new ResearchPage("5"));
			}
			list.add(new ResearchPage(recipeCuNi));
			smelting.add(new ResearchPage(rawCupronickel));
			dusts.add(recDustCuNi);
		}
		if (enableRiftishBronze) {
			if (!start) {
				list.add(new ResearchPage("f"));
				start = true;
			} else {
				list.add(new ResearchPage("6"));
			}
			list.add(new ResearchPage(recipeRBrz));
			smelting.add(new ResearchPage(rawRiftishBronze));
			dusts.add(recDustRBrz);
		}
		if (enableConstantan || enableInvar || enableElectrum) {
			char val = getCIEValue();
			if (val == '*') {
				if (!start) {
					list.add(new ResearchPage("g"));
				} else {
					list.add(new ResearchPage("7"));
				}
			} else {
				if (!start) {
					list.add(new ResearchPage("g" + val));
				} else {
					list.add(new ResearchPage("7" + val));
				}
			}
		}
		if (enableConstantan) {
			list.add(new ResearchPage(recipeCnst));
			smelting.add(new ResearchPage(rawConstantan));
			dusts.add(recDustCnst);
		}
		if (enableInvar) {
			list.add(new ResearchPage(recipeFeNi));
			smelting.add(new ResearchPage(rawInvar));
			dusts.add(recDustFeNi);
		}
		if (enableElectrum) {
			list.add(new ResearchPage(recipeAuAg));
			smelting.add(new ResearchPage(rawElectrum));
			dusts.add(recDustAuAg);
		}

        /*if (ThaumRevConfig.enableOsLu) {
            list.add(new ResearchPage(recipeOsLu));
            list.add(new ResearchPage(recCoatOsLu));
            smelting.add(new ResearchPage(coatedOsLu));
        }*/

		if (!dusts.isEmpty()) {
			list.add(new ResearchPage("dust"));
			IRecipe[] dust = new IRecipe[dusts.size()];
			dusts.toArray(dust);
			list.add(new ResearchPage(dust));
		}
	    /*if (!tiny.isEmpty()) {
            list.add(new ResearchPage("tiny"));
            list.add(new ResearchPage(tiny));
        }*/

		if (ThaumRevConfig.obviousSmelting) {
			list.add(new ResearchPage("smelt"));
			list.addAll(smelting);
		}

		ResearchPage[] pages = new ResearchPage[list.size()];
		list.toArray(pages);
		return pages;
	}

	public static boolean enableAlloys() {
		return enableBrass || enableBronze || enableBismuthBronze || enableMithril || enableAlBronze || enableCupronickel || enableRiftishBronze || enableConstantan || enableInvar || enableElectrum;
	}

	public static char getCIEValue() {
		if (enableConstantan && enableInvar && enableElectrum) {
			return '*';
		} else if (enableConstantan) {
			if (enableInvar) {
				return 'c';
			} else if (enableElectrum) {
				return 'b';
			} else {
				return '1';
			}
		} else if (enableInvar) {
			if (enableElectrum) {
				return 'a';
			} else {
				return '2';
			}
		} else if (enableElectrum) {
			return '3';
		} else {
			return '0';
		}
	}

	public static ItemStack[] getPlatinumTriggers() {
		List<ItemStack> triggers = new ArrayList<ItemStack>();
		triggers.addAll(OreDictionary.getOres(INGOT + PT));
		triggers.addAll(OreDictionary.getOres(NUGGET + PT));
		triggers.addAll(OreDictionary.getOres(DUST + PT));
		triggers.addAll(OreDictionary.getOres(BLOCK + PT));
		triggers.addAll(OreDictionary.getOres(TINY_DUST + PT));

		ItemStack[] ret = new ItemStack[triggers.size()];
		triggers.toArray(ret);
		return ret;
	}

	public static boolean isGoodClimate(BiomeGenBase biome, float minTemp, float maxTemp, float minRain, float maxRain) {
		float temp = biome.temperature;
		float rain = biome.rainfall;

		return minTemp <= temp && temp <= maxTemp && minRain <= rain && rain <= maxRain;
	}

	/**
	 * @param world - World the Excubitura Rose is generating in...
	 * @param x     - X coordinate, duh.
	 * @param y     - Y coordinate, duh.
	 * @param z     - Z coordinate, duh.
	 *
	 * @return - A modifier for generation of Excubitura Roses. Also used to determine growth speed.
	 */
	public static double getExcubituraModifier(World world, int x, int y, int z) {
		double modifier = 5D;
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		// First off, these guys don't grow in the End, Nether, or Mushroom Biomes. They also won't grow in dead places and wastelands.
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.END) || BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER) || BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MUSHROOM) ||
				BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.DEAD) || BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.WASTELAND)) {
			return 0D;
		}

		// Modify the modifier for temperature.
		double temp = (double) biome.getFloatTemperature(x, y, z);
		if (temp < 0.35D) {
			modifier -= (0.70D - (temp * 2));
		} else if (temp > 0.75D) {
			modifier -= (temp - 0.75D);
		}

		// They don't like snow...
		if (biome.getEnableSnow()) {
			modifier /= 8;
		}

		// Modify the modifier for rainfall.
		double rain = (double) biome.rainfall;
		if (rain < 0.2D) {
			modifier -= (2D - (rain * 10));
		} else if (rain < 0.35D) {
			modifier -= (1.4D - (rain * 4));
		} else if (rain > 0.85D) {
			modifier -= ((2 * rain) - 1.70D);
		} else if (rain > 0.75D) {
			modifier -= (rain - .75D);
		}

		// They really don't like being dried out or drenched...
		if (rain > 0.1D || rain < 0.95D) {
			modifier /= 10.0D;
		}

		// They like moderate temperatures
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.HOT) || BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.COLD)) {
			modifier *= 0.75D;
		} else {
			modifier *= 1.1D;
		}

		// They like a decent amount of water, but not too much
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.DRY)) {
			modifier *= 0.7D;
		} else if (!BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.WET)) {
			modifier *= 1.1D;
		}

		// They are just like regular plants...
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SPARSE)) {
			modifier *= 0.75D;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.LUSH)) {
			modifier *= 1.65D;
		}

		// They dislike jungles, and aren't fond of savannas. They do like coniferous forests though.
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.JUNGLE)) {
			modifier *= 0.5D;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SAVANNA)) {
			modifier *= 0.75D;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.CONIFEROUS)) {
			modifier *= 1.25D;
		}

		// They dislike saltwater, but love a good stream!
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.BEACH) || BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.OCEAN)) {
			modifier *= .625D;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.RIVER)) { //Something about the planet Miranda...
			modifier *= 1.375D;
		}

		// No snow! Even bigger no to deserts!
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SNOWY)) {
			modifier /= 4.0D;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SANDY)) {
			modifier /= 8.0D;
		}

		// Take your sorry ass back to Florida...
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SWAMP)) {
			modifier *= 0.1875D;
		}

		// They do like some clay in the soil...
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MESA)) {
			modifier *= 1.05D;
		}

		// Plains are good, Forests are better!
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.PLAINS)) {
			modifier *= 1.125D;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.FOREST)) {
			modifier *= 1.75D;
		}

		// Mountains are good, but they prefer big hills.
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MOUNTAIN)) {
			modifier *= 1.25D;
		}
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.HILLS)) {
			modifier *= 1.5D;
		}

		// Use Botania for the lushest hair possible. Trust me, I'm a wereporcupine.
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.LUSH)) {
			modifier *= 2.0D;
		}

		// 3 FOR THE PRICE OF 2 IF YOU WANT TO BELIEVE...
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SPOOKY)) {
			modifier *= 1.5D;
		}

		// They are a thaumic rose. They like magical biomes.
		if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MAGICAL)) {
			modifier *= 10.0D;
		}

		return modifier * 2.0D;
	}

	public static AspectList monorder = new AspectList().add(Aspect.ORDER, 1);

	public static Aspect tempus;

	@Deprecated
	public static String redstone = "dustRedstone";

	@Deprecated
	public static String enchSilk = "itemEnchantedFabricSilk";
	@Deprecated
	public static String mirror = "itemMirroredGlass";
	@Deprecated
	public static String salisMundus = "dustSalisMundus";

	@Deprecated
	public static String shardBalanced = "shardBalanced";

	@Deprecated
	public static String nHg = "itemDropQuicksilver";

	@Deprecated
	public static String iBs = "ingotBrass";

	@Deprecated
	public static String nBronze = "nuggetBronze";

	@Deprecated
	public static String salisPinch = "dustTinySalisMundus";

	@Deprecated
	public static String paste = "itemExcubituraPaste";

	@Deprecated
	public static String wardencloth = "itemWardencloth";

	@Deprecated
	public static String oilExcu = "itemExcubituraOil";
	@Deprecated
	public static String chainPBronze = "chainPrimalBronze";

	@Deprecated
	public static String quartz = "gemWardenicQuartz";
}
