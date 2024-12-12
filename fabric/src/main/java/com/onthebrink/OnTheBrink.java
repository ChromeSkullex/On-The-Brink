package com.onthebrink;

import com.onthebrink.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnTheBrink implements ModInitializer {
	public static final String MOD_ID = "on-the-brink";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Initializing On the Brink!");
		ModItems.registerItems();
	}
}