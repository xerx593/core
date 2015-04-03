package src.ares.core.battle.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import src.ares.core.common.crafted.CraftedItemStack;

public class BattleConst
{
	public static final int MAX_GOLD_REWARD = 80;

	public static final int MIN_GOLD_REWARD = 20;

	public static final int COMBAT_TIME = 5;

	public static final int PROTECTION_TIME = 5;

	public static final int MAX_DEATH_OUT_TIME = 5;

	public static final int MIN_DEATH_OUT_TIME = 1;

	public static final int FEATHER_SLOT = 8;

	public static final ItemStack FEATHER = new CraftedItemStack(Material.FEATHER, "Back to Hub").build();

	public static final Sound PROTECTION_SOUND = Sound.NOTE_BASS;

	public static final Sound KIT_EQUIP_SOUND = Sound.HORSE_SADDLE;
}
