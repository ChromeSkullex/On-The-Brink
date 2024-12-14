package com.onthebrink.block;

import com.onthebrink.OnTheBrink;
import com.onthebrink.item.ModCreativeModeTabs;
import com.onthebrink.item.ModItems;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OnTheBrink.MOD_ID);

    public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
        .strength(4f)
        .requiresCorrectToolForDrops()
        .sound(SoundType.STONE)
    ), ModCreativeModeTabs.LEARNING_TAB);

    public static final RegistryObject<Block> SAPPHIRE_BLOCK = registerBlock("sapphire_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
            .strength(4f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
    ), ModCreativeModeTabs.LEARNING_TAB);

    public static final RegistryObject<Block> RUBY_ORE = registerBlock("ruby_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
            .strength(4f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
    ), ModCreativeModeTabs.LEARNING_TAB);

    public static final RegistryObject<Block> SAPPHIRE_ORE = registerBlock("sapphire_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
            .strength(4f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
    ), ModCreativeModeTabs.LEARNING_TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab creativeModeTab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, creativeModeTab);
        return toReturn;
    }

    private  static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab creativeModeTab){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(creativeModeTab)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }


}
