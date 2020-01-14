package com.landslide.redsteelmod;

import com.landslide.redsteelmod.init.ModItemGroups;
import com.landslide.redsteelmod.world.gen.OreGeneration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(modid = RedSteelMain.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {
    private static final Logger LOGGER = RedSteelMain.LOGGER;
    //this is where the items go
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        registry.registerAll(
                setupItem(ModItemGroups.MOD_MAIN_GROUP, "steel_ingot"),
                setupItem(ModItemGroups.MOD_MAIN_GROUP, "refined_aberrite"),
                setupItem(ModItemGroups.MOD_MAIN_GROUP, "red_steel_ingot"),
                setupItem(ModItemGroups.MOD_MAIN_GROUP, "refined_redstone")
        );

        //to turn all of the blocks into items
        ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block.getRegistryName().getNamespace().equals(RedSteelMain.MODID)).forEach(block -> {
            //a line to create new Item.Properties to pass to a new BlockItem which is registered with the registry name of the block
            registry.register(setup(new BlockItem(block, new Item.Properties().group(ModItemGroups.MOD_MAIN_GROUP)), block.getRegistryName()));
        });
        LOGGER.debug("Registered redsteelmod's Item(s) and ItemBlock(s).");
    }

    //this is where the blocks go
    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                setupBlock(Material.IRON, "steel_block", 5.0F, 5.0F, 2),
                setupBlock(Material.IRON, "aberrite_block", 6.0F, 6.0F, 2),
                setupBlock(Material.IRON, "red_steel_block", 6.0F, 6.0F, 3),
                setupBlock(Material.ROCK, "aberrite_ore", 3.0F, 3.0F, 2)
        );
        LOGGER.debug("Registered redsteelmod's Block(s)");
    }


    public static Item setupItem(ItemGroup group, String name) {
        return setup(new Item(new Item.Properties().group(group)), name);
    }

    public static Block setupBlock(Material material, String name, float hardness, float resistance, int harvestLevel) {
        return setup(new Block(Block.Properties.create(material).hardnessAndResistance(hardness, resistance).harvestLevel(harvestLevel)), name);
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
        return setup(entry, new ResourceLocation(RedSteelMain.MODID, name));
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
        entry.setRegistryName(registryName);
        return entry;
    }
}
