package com.lothrazar.cyclicmagic.registry;

import com.lothrazar.cyclicmagic.event.FuelHandler;
import com.lothrazar.cyclicmagic.util.Const;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FuelRegistry {

	private static boolean enabled;

	public static void register() {

		if (enabled) {
			GameRegistry.registerFuelHandler(new FuelHandler());
		}
	}

	public static void syncConfig(Configuration config) {

		String category = Const.ConfigCategory.items;
 
		Property prop = config.get(category, "More Furnace Fuel", true, "Tons more wood and plant related items now can burn as fuel");

		enabled = prop.getBoolean();

	}
}
