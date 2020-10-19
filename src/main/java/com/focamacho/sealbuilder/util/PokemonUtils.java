package com.focamacho.sealbuilder.util;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

public class PokemonUtils {

    public static ItemStack getPokemonAsItem(Pokemon pokemon) {
        return ItemStackUtil.snapshotOf(ItemPixelmonSprite.getPhoto(pokemon)).createStack();
    }

}
