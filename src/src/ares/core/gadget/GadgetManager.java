package src.ares.core.gadget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import src.ares.core.common.Module;
import src.ares.core.gadget.type.GadgetHandAmbrosiaShooter;
import src.ares.core.gadget.type.GadgetHandFirework;
import src.ares.core.gadget.type.GadgetHandLazerStick;
import src.ares.core.gadget.type.GadgetHandOreoPocket;
import src.ares.core.gadget.type.GadgetHandSnowballRifle;
import src.ares.core.gadget.type.GadgetParticleBreedingLove;
import src.ares.core.gadget.type.GadgetParticleUndeadFlames;
import src.ares.core.gadget.type.GadgetParticleSnowRing;
import src.ares.core.gadget.type.premium.GadgetParticleElite;
import src.ares.core.gadget.type.premium.GadgetParticleOlympian;
import src.ares.core.gadget.type.premium.GadgetParticleTitan;

public class GadgetManager extends Module
{
	private static GadgetManager instance = new GadgetManager();

	public static GadgetManager getInstance()
	{
		return instance;
	}

	private HashMap<Player, Gadget> toggledGadgets;
	private HashMap<Player, Integer> particleTasks = new HashMap<>();
	private List<Gadget> gadgetBag;

	public GadgetManager()
	{
		super("Gadget Manager");
		toggledGadgets = new HashMap<>();
	}

	public void createGadgets()
	{
		gadgetBag = new ArrayList<>();

		gadgetBag.add(new GadgetHandSnowballRifle());
		gadgetBag.add(new GadgetHandFirework());
		gadgetBag.add(new GadgetHandLazerStick());
		gadgetBag.add(new GadgetHandAmbrosiaShooter());
		gadgetBag.add(new GadgetHandOreoPocket());
		// gadgetBag.add(new GadgetHandStacker());

		gadgetBag.add(new GadgetParticleSnowRing());
		gadgetBag.add(new GadgetParticleUndeadFlames());
		gadgetBag.add(new GadgetParticleBreedingLove());
		
		gadgetBag.add(new GadgetParticleElite());
		gadgetBag.add(new GadgetParticleOlympian());
		gadgetBag.add(new GadgetParticleTitan());
	}

	public List<Gadget> getGadgetBag()
	{
		return gadgetBag;
	}
	
	public HashMap<Player, Integer> getParticleTasks()
	{
		return particleTasks;
	}

	public HashMap<Player, Gadget> getToggledGadgets()
	{
		return toggledGadgets;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		
		if (toggledGadgets.containsKey(player))
			toggledGadgets.remove(player);
		
		if (particleTasks.containsKey(player))
		{
			Bukkit.getScheduler().cancelTask(particleTasks.get(player));
			particleTasks.remove(player);
		}
	}
}
