package com.onthebrink.block;

import com.onthebrink.OnTheBrink;
import com.onthebrink.block.custom.FlammableRotatedPillarBlock;
import com.onthebrink.item.ModItems;
import com.onthebrink.world.feature.tree.WoodsCycadGrower;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(OnTheBrink.MOD_ID, Registry.BLOCK_REGISTRY);

    public static final RegistrySupplier<Block> RUBY_ORE = registerBlock("ruby_ore", () -> new Block(BlockBehaviour.Properties
            .of(Material.STONE)
            .strength(4f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
    ), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<LeavesBlock> WOODS_CYCAD_LEAVES = registerBlock("woods_cycad_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<SaplingBlock> WOODS_CYCAD_SPROUT = registerBlock("woods_cycad_sprout", () -> new SaplingBlock(new WoodsCycadGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<RotatedPillarBlock> WOODS_CYCAD_LOG = registerBlock("woods_cycad_log", () -> FlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<RotatedPillarBlock> WOODS_CYCAD_WOOD = registerBlock("woods_cycad_wood", () -> FlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_WOODS_CYCAD_LOG = registerBlock("stripped_woods_cycad_log", () -> FlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_WOODS_CYCAD_WOOD = registerBlock("stripped_woods_cycad_wood", () -> FlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<Block> WOODS_CYCAD_PLANKS = registerBlock("woods_cycad_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<Block> WOODS_CYCAD_CONE = registerBlock("woods_cycad_cone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COCOA).noCollission().noOcclusion()), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<DoorBlock> WOODS_CYCAD_DOOR = registerBlock("woods_cycad_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<FenceBlock> WOODS_CYCAD_FENCE = registerBlock("woods_cycad_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<FenceGateBlock> WOODS_CYCAD_FENCE_GATE = registerBlock("woods_cycad_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<SlabBlock> WOODS_CYCAD_SLAB = registerBlock("woods_cycad_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<StairBlock> WOODS_CYCAD_STAIRS = registerBlock("woods_cycad_stairs", () -> new StairBlock(ModBlocks.WOODS_CYCAD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)), CreativeModeTab.TAB_MISC);

    public static final RegistrySupplier<TrapDoorBlock> WOODS_CYCAD_TRAPDOOR = registerBlock("woods_cycad_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)), CreativeModeTab.TAB_MISC);

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
