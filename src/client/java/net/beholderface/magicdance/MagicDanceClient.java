package net.beholderface.magicdance;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class MagicDanceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientLifecycleEvents.CLIENT_STARTED.register((client)->{
			KeyTrackingManager.init();
		});
		ClientTickEvents.START_WORLD_TICK.register((world)->{
			KeyTrackingManager.tick();
		});
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}