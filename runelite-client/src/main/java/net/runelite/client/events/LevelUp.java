package net.runelite.client.events;

import lombok.Value;

@Value
public class LevelUp
{
	private String skillName;
	private Integer level;
	private String displayName;
}
