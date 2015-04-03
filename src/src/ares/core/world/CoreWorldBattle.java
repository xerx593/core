package src.ares.core.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import src.ares.core.common.util.Chat;

public class CoreWorldBattle extends CoreWorld
{
	private List<String> identities = new ArrayList<>();
	private Random random = new Random();

	public CoreWorldBattle(String name)
	{
		super(name, WorldType.PVP);

		setPvE(false);
		setPvE(false);

		setBlockBreak(true);
		setBlockPlace(true);
		setBlockDecay(true);
		setBlockIgnite(true);

		setEntityExplosion(true);
		setEntityGrief(true);
		setEntitySpawn(true);
		setEntityTarget(true);

		setInteract(false);
		setInventory(false);
		setItemDrop(true);

		setDeathDrops(true);
		setItemDrop(true);
		lockTime(TimeState.DAY);
		setLockWeather(true);

		setupMobNames();
	}

	private void setupMobNames()
	{
		identities.add("William F. Thompson");
		identities.add("Bonita S. Robinson");
		identities.add("Loyd S. Butler");
		identities.add("Mary E. Downey");
		identities.add("Jason E. Stewart");
		identities.add("Thomas K. Skipper");
		identities.add("Michael N. Williams");
		identities.add("Jordan E. Almonte");
		identities.add("Lorena R. Bruce");
		identities.add("Regina R. Willingham");
		identities.add("Cheri R. Horne");
		identities.add("Kevin M. Glynn");
		identities.add("Rhonda V. Hutchison");
		identities.add("Bill T. Hale");
		identities.add("Nikki R. Simons");
		identities.add("David M. Boyd");
		identities.add("Isabel A. Chavarria");
		identities.add("Numbers A. Washington");
		identities.add("Carolyn L. Steffan");
		identities.add("Brian A. Lang");
		identities.add("Debra T. Pabon");
		identities.add("Brandi N. Erwin");
		identities.add("Allen S. Ramirez");
		identities.add("Michael C. Ricker");
		identities.add("Esther H. Pegram");
		identities.add("Scott L. Havel");
		identities.add("Michael A. Murch");
		identities.add("Richard B. Chan");
		identities.add("Willie J. Rodriguez");
		identities.add("Matthew J. Bowles");
		identities.add("Dolores G. Galloway");
		identities.add("Daryl L. Daniels");
		identities.add("Cheryl J.Flesher");
		identities.add("Sherry S. Hoglund");
		identities.add("Stephan A. Ferrell");
	}

	private String getRandomName()
	{
		int randomizer = random.nextInt(identities.size());
		return identities.get(randomizer);
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		LivingEntity entity = event.getEntity();
		entity.setCustomNameVisible(true);
		entity.setCustomName(getRandomName());

		Location spawnLocation = entity.getLocation();
		entity.getWorld().playSound(spawnLocation, Sound.DIG_WOOL, 0.7F, 1.0F);
		entity.getWorld().playEffect(spawnLocation.add(0, 1, 0), Effect.MOBSPAWNER_FLAMES, 0);
	}

	@EventHandler
	public void blockVoidDamage(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		double voidDistance = player.getLocation().getY();

		if (onWorld(player) && voidDistance < -10.0)
		{
			blockVoidDamage(player);
			player.sendMessage(Chat.format("Hub", "Be careful, you might get rekt."));
		}
	}

	private void blockVoidDamage(Player player)
	{
		player.teleport(getWorld().getSpawnLocation().add(0, 2, 0));
		player.playSound(player.getLocation(), Sound.CAT_MEOW, 0.5F, 1.0F);
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event)
	{
		if (onWorld(event.getEntity()) && event.getCause() == DamageCause.FALL)
		{
			event.setDamage(0.0);
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (!onWorld(event.getPlayer()))
			return;

		Player player = event.getPlayer();
		ItemStack itemInHand = player.getItemInHand();
		Action action = event.getAction();

		if (itemInHand == null)
			return;
		if (!itemInHand.hasItemMeta())
			return;
		if (itemInHand.getType() != Material.FEATHER)
			return;
		if (!itemInHand.getItemMeta().getDisplayName().contains("Back to Hub"))
			return;

		try
		{
			if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
			{
				WorldSelector.selectHub(player);
			}
		}
		catch (Exception e)
		{

		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event)
	{
		if (onWorld(event.getEntity()) && event.getEntity().getType() == EntityType.ARROW)
		{
			event.getEntity().remove();
		}
	}
}
