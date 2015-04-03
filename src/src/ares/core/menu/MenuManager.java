package src.ares.core.menu;

import java.util.ArrayList;

import src.ares.core.menu.type.MenuBattleOptions;
import src.ares.core.menu.type.MenuHubInventory;
import src.ares.core.menu.type.MenuMinigames;
import src.ares.core.menu.type.MenuProgress;
import src.ares.core.menu.type.MenuSettings;
import src.ares.core.menu.type.MenuWorldSelector;

public class MenuManager
{
	private static MenuManager instance = new MenuManager();

	public static MenuManager getInstance()
	{
		return instance;
	}

	private ArrayList<Menu> menuBag;

	private MenuBattleOptions battleInventoryMenu = new MenuBattleOptions();
	private MenuHubInventory hubInventoryMenu = new MenuHubInventory();
	private MenuWorldSelector worldSelectorMenu = new MenuWorldSelector();
	private MenuProgress progressMenu = new MenuProgress();
	private MenuSettings settingsMenu = new MenuSettings();
	private MenuMinigames minigameMenu = new MenuMinigames();

	public void createMenus()
	{
		menuBag = new ArrayList<>();

		menuBag.add(battleInventoryMenu);
		menuBag.add(hubInventoryMenu);
		menuBag.add(worldSelectorMenu);
		menuBag.add(progressMenu);
		menuBag.add(minigameMenu);
		menuBag.add(settingsMenu);

		for (Menu menu : menuBag)
		{
			menu.registerEvents();
		}
	}

	public MenuBattleOptions getBattleInventoryMenu()
	{
		return battleInventoryMenu;
	}

	public MenuHubInventory getHubInventoryMenu()
	{
		return hubInventoryMenu;
	}

	public MenuProgress getProgressMenu()
	{
		return progressMenu;
	}

	public MenuWorldSelector getWorldSelectorMenu()
	{
		return worldSelectorMenu;
	}

	public MenuSettings getSettingsMenu()
	{
		return settingsMenu;
	}

	public MenuMinigames getMinigameMenu()
	{
		return minigameMenu;
	}
}
