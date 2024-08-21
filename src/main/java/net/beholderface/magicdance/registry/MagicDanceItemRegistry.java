package net.beholderface.magicdance.registry;

import net.beholderface.magicdance.MagicDance;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagicDanceItemRegistry {

    private static int count = 0;
    public static final List<Item> items = new ArrayList<>();
    public static final ItemGroup DANCE_GROUP = FabricItemGroupBuilder.create(new Identifier(MagicDance.MOD_ID, "danceitems")).build();
    public static final Item.Settings UNSTACKABLE = new Item.Settings().maxCount(1).group(DANCE_GROUP);
    public static final Item.Settings STACKABLE_16 = UNSTACKABLE.maxCount(16);
    public static final Item.Settings STACKABLE_64 = UNSTACKABLE.maxCount(64);

    //for now this is just a test item in reference to Elden Ring
    public static final Item CASTANETS = register(new Item(UNSTACKABLE), "castanets");

    public static Item register(Item item, String path){
        count++;
        Item output = Registry.register(Registry.ITEM, new Identifier(MagicDance.MOD_ID, path), item);
        items.add(output);
        return output;
    }

    public static void init(){
        String maybeS = count == 1 ? "" : "s";
        MagicDance.LOGGER.info("Registering " + count + " magic dance item"+maybeS+".");
    }
}
