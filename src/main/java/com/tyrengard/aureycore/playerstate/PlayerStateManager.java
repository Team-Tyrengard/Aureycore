package com.tyrengard.aureycore.playerstate;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public final class PlayerStateManager implements Listener {
	private static final Set<UUID> playersOnGround = new HashSet<>(), playersJumping = new HashSet<>();
	public static boolean isJumping(Player p) {
		return playersJumping.contains(p.getUniqueId());
	}
	
	@EventHandler(ignoreCancelled = true)
	private void onPlayerMove(PlayerMoveEvent e) {
		handleJumping(e);
	}
	
	private void handleJumping(PlayerMoveEvent e) {
		Player player = e.getPlayer();
        if (player.getVelocity().getY() > 0) {
            double jumpVelocity = 0.42F;
            if (player.hasPotionEffect(PotionEffectType.JUMP))
                jumpVelocity += (player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() + 1) * 0.1F;
            
            if (e.getPlayer().getLocation().getBlock().getType() != Material.LADDER && playersOnGround.contains(player.getUniqueId()) &&
                !player.isOnGround() && Double.compare(player.getVelocity().getY(), jumpVelocity) == 0) 
            	playersJumping.add(player.getUniqueId());
        } 
        else
        	playersJumping.remove(player.getUniqueId());
        
        if (player.isOnGround())
        	playersOnGround.add(player.getUniqueId());
        else
        	playersOnGround.remove(player.getUniqueId());
	}
}
