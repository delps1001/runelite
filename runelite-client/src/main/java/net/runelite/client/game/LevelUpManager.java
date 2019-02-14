package net.runelite.client.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.LevelUp;


@Slf4j
@Singleton
public class LevelUpManager
{
	private static final Pattern LEVEL_UP_PATTERN = Pattern.compile(".*Your ([a-zA-Z]+) (?:level is|are)? now (\\d+)\\.");
	private static Set<LevelUp> levelUps = new HashSet<>();
	private EventBus eventBus;

	@Inject
	private Client client;

	@Inject
	public LevelUpManager(EventBus eventBus)
	{
		this.eventBus = eventBus;
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		LevelUp levelUpEvent = null;
		if (client.getWidget(WidgetInfo.LEVEL_UP_LEVEL) != null)
		{
			log.debug("HERE found level up widget");
			levelUpEvent = parseLevelUpWidget();
		}

		if (levelUpEvent != null)
		{
			levelUps.add(levelUpEvent);
		}
		else
		{
			return;
		}

		if (!levelUps.contains(levelUpEvent))
		{
			log.debug("Found level up: {}", levelUpEvent);
			eventBus.post(levelUpEvent);
		}
	}

	private LevelUp parseLevelUpWidget()
	{
		Widget levelChild = client.getWidget(WidgetInfo.LEVEL_UP_LEVEL);
		if (levelChild == null)
		{
			return null;
		}

		Matcher m = LEVEL_UP_PATTERN.matcher(levelChild.getText());
		if (!m.matches())
		{
			return null;
		}

		String skillName = m.group(1);
		String skillLevel = m.group(2);
		return new LevelUp(skillName, new Integer(skillLevel), client.getUsername());
	}

	@Subscribe
	public void onLevelUp(LevelUp event)
	{
		log.debug("OMG FOUND LEVEL UP EVENT: {}", event);
	}
}
