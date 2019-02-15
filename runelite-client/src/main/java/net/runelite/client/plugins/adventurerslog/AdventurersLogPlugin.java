package net.runelite.client.plugins.adventurerslog;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.LevelUp;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Adventurers Log",
	description = "Submit account events to adventurers log",
	tags = {"adventurer", "adventurers", "adventure", "log"}
)
@Slf4j
public class AdventurersLogPlugin extends Plugin
{
	@Inject
	private AdventurersLogConfig config;

	@Provides
	AdventurersLogConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AdventurersLogConfig.class);
	}


	@Override
	protected void startUp() throws Exception
	{

	}

	@Override
	protected void shutDown() throws Exception
	{

	}

	@Subscribe
	public void onLevelUp(LevelUp event)
	{
		// TODO submit event to database
		// AdventurersLogController.submit(event)
	}
}
