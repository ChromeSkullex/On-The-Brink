package com.onthebrink.block;

import com.onthebrink.OnTheBrink;
import com.onthebrink.block.custom.*;
import com.onthebrink.item.ModItems;
import com.onthebrink.item.custom.CoconutItem;
import com.onthebrink.misc.ModCreativeModeTabs;
import com.onthebrink.world.feature.tree.CoconutTreeGrower;
import com.onthebrink.world.feature.tree.RubberTreeGrower;
import com.onthebrink.world.feature.tree.WoodsCycadGrower;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(OnTheBrink.MOD_ID, Registry.BLOCK_REGISTRY);

    // ------ BUILDING / MACHINES --------

    public static final RegistrySupplier<CoconutLatexCollectorBlock> COCONUT_LATEX_COLLECTOR = registerBlockNoItem("coconut_latex_collector", () -> new CoconutLatexCollectorBlock(BlockBehaviour.Properties.copy(Blocks.COCOA).noCollission().randomTicks()));

    public static final RegistrySupplier<RubberBlock> RUBBER_BLOCK = registerBlock("rubber_block", () -> new RubberBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK).friction(0.6f /* default friction */)), ModCreativeModeTabs.BUILDING);

    public static final RegistrySupplier<PoppyTeaCauldronBlock> POPPY_TEA_CAULDRON = registerBlockNoItem("poppy_tea_cauldron", () -> new PoppyTeaCauldronBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON).randomTicks()));

    public static final RegistrySupplier<TranquilizerCauldronBlock> TRANQUILIZER_CAULDRON = registerBlockNoItem("tranquilizer_cauldron", () -> new TranquilizerCauldronBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON)));

    public static final RegistrySupplier<IronBarsBlock> CHAIN_LINK = registerBlock("chain_link", () -> new IronBarsBlock(BlockBehaviour.Properties.copy(Blocks.GLASS_PANE).sound(SoundType.CHAIN)), ModCreativeModeTabs.BUILDING);

    public static final RegistrySupplier<Block> WOODEN_CRATE = registerBlockNoItem("wooden_crate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    // we are saving a reference to the item so that we can use it as the icon for the creative mode tab
    // in this case, we'll probably write custom logic for it anyway
    public static final RegistrySupplier<Item> WOODEN_CRATE_ITEM = ModItems.ITEMS.register("wooden_crate", () -> new BlockItem(WOODEN_CRATE.get(), new Item.Properties().tab(ModCreativeModeTabs.BUILDING)));

    public static final RegistrySupplier<SlabBlock> BONE_SLAB = registerBlock("bone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)), ModCreativeModeTabs.BUILDING);

    public static final RegistrySupplier<StairBlock> BONE_STAIRS = registerBlock("bone_stairs", () -> new StairBlock(Blocks.BONE_BLOCK.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)), ModCreativeModeTabs.BUILDING);

    public static final RegistrySupplier<RotatedPillarBlock> SOLID_BONE = registerBlock("solid_bone", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)), ModCreativeModeTabs.BUILDING);

    public static final RegistrySupplier<RotatedPillarBlock> CRACKED_SOLID_BONE = registerBlock("cracked_solid_bone", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)), ModCreativeModeTabs.BUILDING);

    public static final RegistrySupplier<RotatedPillarBlock> CRACKED_BONE_BLOCK = registerBlock("cracked_bone_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)), ModCreativeModeTabs.BUILDING);

    public static final RegistrySupplier<WallBlock> BONE_WALL = registerBlock("bone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK)), ModCreativeModeTabs.BUILDING);


    // ------ NATURE ------

    public static final RegistrySupplier<SoulSandBlock> BROWN_MUD = registerBlock("brown_mud", () -> new SoulSandBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND).sound(SoundType.WET_GRASS)), ModCreativeModeTabs.NATURE);

    public static final RegistrySupplier<SeashellsBlock> SEASHELLS = registerBlockNoItem("seashells", () -> new SeashellsBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).strength(0.1f)));

    public static final RegistrySupplier<GlowLichenBlock> BARNACLES = registerBlock("barnacles", () -> new GlowLichenBlock(BlockBehaviour.Properties.copy(Blocks.GLOW_LICHEN).lightLevel(GlowLichenBlock.emission(0)).noOcclusion().sound(SoundType.FUNGUS)), ModCreativeModeTabs.NATURE);

    public static final RegistrySupplier<SpongeBlock> GLASS_SPONGE = registerBlock("glass_sponge", () -> new GlassSpongeBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)), ModCreativeModeTabs.NATURE);

    public static final RegistrySupplier<WetGlassSpongeBlock> WET_GLASS_SPONGE = registerBlock("wet_glass_sponge", () -> new WetGlassSpongeBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)), ModCreativeModeTabs.NATURE);



    // ------ PLANTS (EXCEPT TREES) ------

    public static final RegistrySupplier<DeadBushBlock> PINK_SAND_VERBENA = registerBlock("pink_sand_verbena", () -> new DeadBushBlock(BlockBehaviour.Properties.copy(Blocks.POPPY).noOcclusion()), ModCreativeModeTabs.PLANTS);

    public static final RegistrySupplier<BushBlock> AFRICAN_VIOLET = registerBlock("african_violet", () -> new BushBlock(BlockBehaviour.Properties.copy(Blocks.POPPY).noOcclusion()), ModCreativeModeTabs.PLANTS);

    public static final RegistrySupplier<BushBlock> CHOCOLATE_COSMOS = registerBlock("chocolate_cosmos", () -> new BushBlock(BlockBehaviour.Properties.copy(Blocks.POPPY).noOcclusion()), ModCreativeModeTabs.PLANTS);

    public static final RegistrySupplier<FlowerBlock> SLEEP_POPPY = registerBlockNoItem("sleep_poppy", () -> new FlowerBlock(MobEffects.BLINDNESS, 10, BlockBehaviour.Properties.copy(Blocks.POPPY)));



    // ------- TREES -------

    // DRAGON BLOOD TREE

    public static final RegistrySupplier<RotatedPillarBlock> DRAGON_BLOOD_TREE_LOG = registerBlock("dragon_blood_tree_log", () -> FlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), ModCreativeModeTabs.TREES_AND_WOOD);
    public static final RegistrySupplier<LeavesBlock> DRAGON_BLOOD_TREE_LEAVES = registerBlock("dragon_blood_tree_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), ModCreativeModeTabs.TREES_AND_WOOD);


    // RUBBER TREE

    public static final RegistrySupplier<SaplingBlock> RUBBER_TREE_SAPLING = registerBlock("rubber_tree_sapling", () -> new SaplingBlock(new RubberTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), ModCreativeModeTabs.TREES_AND_WOOD);

    // defined here so that we can control the order it appears in the creative mode tab
    public static final RegistrySupplier<Item> RUBBER_SEED_POD_ITEM = ModItems.ITEMS.register("rubber_seed_pod",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.TREES_AND_WOOD).stacksTo(16)));

    public static final RegistrySupplier<LeavesBlock> RUBBER_TREE_LEAVES = registerBlock("rubber_tree_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<RotatedPillarBlock> RUBBER_TREE_LOG = registerBlock("rubber_tree_log", () -> FlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_RUBBER_TREE_LOG = registerBlock("stripped_rubber_tree_log", () -> FlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), ModCreativeModeTabs.TREES_AND_WOOD);

    // COCONUT TREE

    public static final RegistrySupplier<CoconutSaplingBlock> COCONUT = registerBlockNoItem("coconut", () -> new CoconutSaplingBlock(new CoconutTreeGrower(), BlockBehaviour.Properties.copy(Blocks.COCOA).noCollission()));

    // defined here so that we can control the order it appears in the creative mode tab
    public static final RegistrySupplier<Item> COCONUT_ITEM = ModItems.ITEMS.register("coconut", () -> new CoconutItem(COCONUT));

    public static final RegistrySupplier<LeavesBlock> COCONUT_TREE_LEAVES = registerBlock("coconut_tree_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<RotatedPillarBlock> COCONUT_TREE_LOG = registerBlock("coconut_tree_log", () -> FlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<CoconutCrownBlock> COCONUT_TREE_CROWN = registerBlock("coconut_tree_crown", () -> new CoconutCrownBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).randomTicks()), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<CoconutFruitBlock> COCONUT_FRUIT = registerBlockNoItem("coconut_fruit", () -> new CoconutFruitBlock(BlockBehaviour.Properties.copy(Blocks.COCOA)));


    // WOOD'S CYCAD

    public static final RegistrySupplier<SaplingBlock> WOODS_CYCAD_SPROUT = registerBlockNoItem("woods_cycad_sprout", () -> new SaplingBlock(new WoodsCycadGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    // we are saving a reference to the item so that we can use it as the icon for the creative mode tab
    public static final RegistrySupplier<Item> WOODS_CYCAD_SPROUT_ITEM = ModItems.ITEMS.register("woods_cycad_sprout", () -> new BlockItem(WOODS_CYCAD_SPROUT.get(), new Item.Properties().tab(ModCreativeModeTabs.TREES_AND_WOOD)));

    public static final RegistrySupplier<LeavesBlock> WOODS_CYCAD_LEAVES = registerBlock("woods_cycad_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<RotatedPillarBlock> WOODS_CYCAD_LOG = registerBlock("woods_cycad_log", () -> FlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<Block> WOODS_CYCAD_CONE = registerBlock("woods_cycad_cone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COCOA).noCollission().noOcclusion()), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<RotatedPillarBlock> WOODS_CYCAD_WOOD = registerBlock("woods_cycad_wood", () -> FlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_WOODS_CYCAD_LOG = registerBlock("stripped_woods_cycad_log", () -> FlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_WOODS_CYCAD_WOOD = registerBlock("stripped_woods_cycad_wood", () -> FlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<Block> WOODS_CYCAD_PLANKS = registerBlock("woods_cycad_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<DoorBlock> WOODS_CYCAD_DOOR = registerBlock("woods_cycad_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<TrapDoorBlock> WOODS_CYCAD_TRAPDOOR = registerBlock("woods_cycad_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<FenceBlock> WOODS_CYCAD_FENCE = registerBlock("woods_cycad_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<FenceGateBlock> WOODS_CYCAD_FENCE_GATE = registerBlock("woods_cycad_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<SlabBlock> WOODS_CYCAD_SLAB = registerBlock("woods_cycad_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)), ModCreativeModeTabs.TREES_AND_WOOD);

    public static final RegistrySupplier<StairBlock> WOODS_CYCAD_STAIRS = registerBlock("woods_cycad_stairs", () -> new StairBlock(ModBlocks.WOODS_CYCAD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)), ModCreativeModeTabs.TREES_AND_WOOD);


    public static <T extends Block> RegistrySupplier<T> registerBlockNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block, CreativeModeTab creativeModeTab) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, creativeModeTab);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block, CreativeModeTab creativeModeTab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(creativeModeTab)));

    }

    public static void register(){
        FlammableRotatedPillarBlock.registerAllStripped();

        BLOCKS.register();
    }
}
