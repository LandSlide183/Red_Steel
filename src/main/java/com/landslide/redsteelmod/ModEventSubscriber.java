package com.landslide.redsteelmod;

import com.landslide.redsteelmod.blocks.CombinationSmelter;
import com.landslide.redsteelmod.blocks.screen.CombinationSmelterScreen;
import com.landslide.redsteelmod.blocks.screen.container.CombinationSmelterContainer;
import com.landslide.redsteelmod.blocks.tileentity.CombinationSmelterTile;
import com.landslide.redsteelmod.config.RedSteelConfig;
import com.landslide.redsteelmod.init.*;
import com.landslide.redsteelmod.world.gen.OreGeneration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

@EventBusSubscriber(modid = RedSteelMain.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {
    private static final Logger LOGGER = RedSteelMain.LOGGER;
    //this is where the items go
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        registry.registerAll(
                setup(new Item(new Item.Properties().group(ModItemGroups.MOD_MAIN_GROUP)), "steel_ingot"),
                setup(new Item(new Item.Properties().group(ModItemGroups.MOD_MAIN_GROUP)), "refined_aberrite"),
                setup(new Item(new Item.Properties().group(ModItemGroups.MOD_MAIN_GROUP)), "red_steel_ingot"),
                setup(new Item(new Item.Properties().group(ModItemGroups.MOD_MAIN_GROUP)), "refined_redstone"),
                setup(new AxeItem(ModItemMaterials.steel, 6.0F, -3.1F, new Item.Properties().group(ModItemGroups.MOD_TOOL_GROUP)), "steel_axe"),
                setup(new HoeItem(ModItemMaterials.steel, -1.0F, new Item.Properties().group(ModItemGroups.MOD_TOOL_GROUP)), "steel_hoe"),
                setup(new SwordItem(ModItemMaterials.steel, 3, -2.4F, new Item.Properties().group(ModItemGroups.MOD_TOOL_GROUP)), "steel_sword"),
                setup(new PickaxeItem(ModItemMaterials.steel, 1, -2.8F, new Item.Properties().group(ModItemGroups.MOD_TOOL_GROUP)), "steel_pickaxe"),
                setup(new ShovelItem(ModItemMaterials.steel, 1.5F, -3.0F, new Item.Properties().group(ModItemGroups.MOD_TOOL_GROUP)), "steel_shovel"),
                setup(new ArmorItem(ModArmorMaterials.steel, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.MOD_TOOL_GROUP)), "steel_helmet"),
                setup(new ArmorItem(ModArmorMaterials.steel, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.MOD_TOOL_GROUP)), "steel_chestplate"),
                setup(new ArmorItem(ModArmorMaterials.steel, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.MOD_TOOL_GROUP)), "steel_leggings"),
                setup(new ArmorItem(ModArmorMaterials.steel, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.MOD_TOOL_GROUP)), "steel_boots")
        );

        for (final Block block : ForgeRegistries.BLOCKS.getValues()) {
            if (!block.getRegistryName().getNamespace().equals(RedSteelMain.MODID)) {
                continue;
            }
            registry.register(setup(new BlockItem(block, new Item.Properties().group(ModItemGroups.MOD_MAIN_GROUP)), block.getRegistryName()));
        }
        LOGGER.debug("Registered redsteelmod's Item(s) and ItemBlock(s).");
    }

    //this is where the blocks go
    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                setup(new CombinationSmelter(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0F, 6.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2)), "combination_smelter"),
                setup(new Block(Block.Properties.create(Material.EARTH).hardnessAndResistance(6.0F, 7.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2)), "aberrite_block"),
                setup(new Block(Block.Properties.create(Material.EARTH).hardnessAndResistance(5.0F, 6.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2)), "steel_block"),
                setup(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(7.0F, 7.0F).harvestTool(ToolType.PICKAXE).harvestLevel(3)), "red_steel_block"),
                setup(new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F, 4.5F).harvestTool(ToolType.PICKAXE).harvestLevel(2)), "aberrite_ore")
                );
        LOGGER.debug("Registered redsteelmod's Block(s)");
    }

    @SubscribeEvent
    public static void onRegisterTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().registerAll(
                setupTileEntity(CombinationSmelterTile::new, "combination_smelter_tile", ModBlocks.COMBINATION_SMELTER)
        );
    }

    @SubscribeEvent
    public static void onModEventConfig(final ModConfig.ModConfigEvent configEvent) {
        RedSteelConfig.bakeServerConfig();
    }

    @SubscribeEvent
    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        RedSteelConfig.bakeServerConfig();
        OreGeneration.setupOreGeneration();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ModContainers.COMBINATION_SMELTER_CONTAINER, CombinationSmelterScreen::new);
    }

    @SubscribeEvent
    public static void onRegisterContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
            IForgeContainerType.create((windowId, inv, data) -> {
                return new CombinationSmelterContainer(inv, windowId, data.readBlockPos());
            }).setRegistryName("combination_smelter_container")
        );
    }

    public static TileEntityType<?> setupTileEntity(Supplier<? extends TileEntity> classIn, String name, Block... block) {
        return TileEntityType.Builder.create(classIn, block).build(null).setRegistryName(name);
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
        return setup(entry, new ResourceLocation(RedSteelMain.MODID, name));
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
        entry.setRegistryName(registryName);
        return entry;
    }
}
