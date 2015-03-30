package src.ares.core.punish;

import src.ares.core.client.OfflineClient;

/**
 * Punishment class offers the base functionality for a Punishment. Any punishment is applied to
 * a specific <b>offender</b> that is punished from the <b>punisher</b>. The duration is not specified
 * from that class, in order to apply a duration, check <code>TimedPunishment</code> Class.
 */
public abstract class Punishment
{
	private OfflineClient offender;
	private OfflineClient punisher;
	private String name;
	private String reason;

	/**
	 * Default Constructor
	 * 
	 * @param name The name of the punishment.
	 * @param reason The reason for the punishment.
	 * @param offender The offender that can be an offline player.
	 * @param punisher The punisher or <b>Swinger</b> of the mute/ban hammer.
	 */
	public Punishment(String name, String reason, OfflineClient offender, OfflineClient punisher)
	{
		this.name = name;
		this.offender = offender;
		this.punisher = punisher;
		this.reason = reason;
	}

	/**
	 * This method should be called when the a new punishment needs to be applied.
	 */
	public abstract void apply();

	public String getName()
	{
		return name;
	}

	public OfflineClient getOffender()
	{
		return offender;
	}

	public OfflineClient getPunisher()
	{
		return punisher;
	}

	public String getReason()
	{
		return reason;
	}
}
