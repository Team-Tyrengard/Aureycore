package com.tyrengard.aureycore.actionbarengine;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.tyrengard.aureycore.common.utils.TaskUtils;

import org.bukkit.plugin.Plugin;

public final class ActionBarEngine implements Listener {
	private static ActionBarEngine instance;
	private static int engineTaskId;
	private static final ActionBarMessageQueue permanentMessages = new ActionBarMessageQueue();
	private static final ActionBarMessageQueue messageQueue = new ActionBarMessageQueue();
	private static final ArrayList<ActionBarMessageQueue> customQueues = new ArrayList<>();
	private ActionBarEngine() {}

	public static void start(Plugin plugin) {
		start(plugin, 2);
	}

	public static void start(Plugin plugin, int ticksPerUpdate) {
		if (instance != null) return;

		instance = new ActionBarEngine();
		plugin.getServer().getPluginManager().registerEvents(instance, plugin);
		engineTaskId = TaskUtils.runTaskPeriodically(plugin, 0, ticksPerUpdate, () -> {
			for (Entry<UUID, LinkedBlockingQueue<ActionBarMessage>> entry : permanentMessages.entrySet()) {
				Player p = Bukkit.getPlayer(entry.getKey());
				if (p == null) {
					messageQueue.remove(entry.getKey());
					permanentMessages.remove(entry.getKey());
				} else {
					ActionBarMessage message = null;
					LinkedBlockingQueue<ActionBarMessage> queueForPlayer = messageQueue.get(entry.getKey());
					if (queueForPlayer != null && !queueForPlayer.isEmpty()) {
						message = queueForPlayer.peek();
						if (message != null && message.tick(ticksPerUpdate)) queueForPlayer.remove();
					} else {
						for (ActionBarMessageQueue queue : customQueues) {
							LinkedBlockingQueue<ActionBarMessage> customQueueForPlayer = queue.get(entry.getKey());
							if (customQueueForPlayer != null && !customQueueForPlayer.isEmpty()) {
								message = customQueueForPlayer.peek();
								if (message != null) {
									if (message.tick(ticksPerUpdate))
										customQueueForPlayer.remove();
									break;
								}
							}
						}

						if (message == null) {
							message = entry.getValue().peek();
							if (message != null && message.tick(ticksPerUpdate)) {
								entry.getValue().remove();
								entry.getValue().add(message); // re-add permanent message to the end of the queue
							}
						}
					}
					if (message != null)
						message.getSender().accept(p);
//						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, message.getTextComponent());
				}
			}
		});
	}

	public static void stop(boolean clearQueues) {
		TaskUtils.cancelTask(engineTaskId);
		PlayerQuitEvent.getHandlerList().unregister(instance);
		instance = null;

		if (clearQueues) {
			permanentMessages.clear();
			messageQueue.clear();
			customQueues.clear();
		}
	}
	
	@EventHandler
	private void onPlayerLeave(PlayerQuitEvent e) {
		UUID id = e.getPlayer().getUniqueId();
		permanentMessages.remove(id);
		messageQueue.remove(id);
		for (ActionBarMessageQueue queue : customQueues)
			queue.remove(id);
	}
	
//	public static void queueMessage(Player p, String text, Color color, int ticks) {
//		TextComponent c = new TextComponent(text);
//		c.setColor(color.toBungeeAPIChatColor());
//		queueMessage(p, false, new ActionBarMessage(c, ticks));
//	}
//
//	public static void queueMessage(Player p, TextComponent component, int ticks) {
//		queueMessage(p, false, new ActionBarMessage(component, ticks));
//	}
//
//	public static void queuePermanentMessage(Player p, String text, int ticks) {
//		queuePermanentMessage(p, text, null, ticks);
//	}
//
//	public static void queuePermanentMessage(Player p, String text, Color color, int ticks) {
//		TextComponent c = new TextComponent(text);
//		if (color != null)
//			c.setColor(color.toBungeeAPIChatColor());
//		queuePermanentMessage(p, c, ticks);
//	}
//
//	public static void queuePermanentMessage(Player p, TextComponent component, int ticks) {
//		queueMessage(p, true, new ActionBarMessage(component, ticks));
//	}
//
//	public static void queuePermanentMessage(Player p, TextComponentGenerator generator, int ticks) {
//		queueMessage(p, true, new ActionBarMessage(generator, ticks));
//	}

	public static void queueMessage(Player p, boolean permanent, ActionBarMessage message) {
		LinkedBlockingQueue<ActionBarMessage> cs = permanent ? permanentMessages.get(p.getUniqueId()) : messageQueue.get(p.getUniqueId());
		if (cs == null) cs = new LinkedBlockingQueue<>();
		cs.add(message);
		if (permanent)
			permanentMessages.put(p.getUniqueId(), cs);
		else
			messageQueue.put(p.getUniqueId(), cs);
	}

	public static void addCustomQueue(ActionBarMessageQueue queue) {
		customQueues.add(queue);
	}
}
