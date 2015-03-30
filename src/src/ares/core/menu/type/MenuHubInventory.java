package src.ares.core.menu.type;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import src.ares.core.gadget.Gadget;
import src.ares.core.gadget.GadgetManager;
import src.ares.core.gadget.HandGadget;
import src.ares.core.gadget.ParticleGadget;
import src.ares.core.menu.Menu;

public class MenuHubInventory extends Menu
{
	private int handGadgetSlot;
	private int particleGadgetSlot;
	private int premiumGadgetSlot;
	
	private GadgetManager manager = GadgetManager.getInstance();

	public MenuHubInventory()
	{
		super(Material.CHEST, "Hub Inventory", 45);
	}

	@Override
	protected void InventoryClick(InventoryAction action, ItemStack item, Player player)
	{
		for (Gadget gadget : manager.getGadgetBag())
		{
			if (item.equals(gadget.getDisplay()))
			{
				gadget.enable(player);
			}
		}
	}

	@Override
	protected void InventoryConstruct(Player player)
	{
		handGadgetSlot = 11;
		particleGadgetSlot = 28;
		premiumGadgetSlot = 32;
		
		for (Gadget gadget : manager.getGadgetBag())
		{
			if (gadget instanceof HandGadget)
				AddDisplay(handGadgetSlot++, gadget.getDisplay());
			else if (gadget instanceof ParticleGadget)
			{
				if (gadget.getName().contains("Elite") || gadget.getName().contains("Olympian") || gadget.getName().contains("Titan"))
				{
					AddDisplay(premiumGadgetSlot++, gadget.getDisplay());
				}
				else
				{
					AddDisplay(particleGadgetSlot++, gadget.getDisplay());
				}
			}
		}
	}
}
