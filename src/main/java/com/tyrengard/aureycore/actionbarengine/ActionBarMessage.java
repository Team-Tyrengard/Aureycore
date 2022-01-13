package com.tyrengard.aureycore.actionbarengine;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class ActionBarMessage {
    final Consumer<Player> sender;
    final int ticks;

    public ActionBarMessage(Consumer<Player> sender, int ticks) {
        this.sender = sender;
        this.ticks = ticks;
    }

    int runtime = 0;

    boolean tick(int t) {
        runtime += t;
        return runtime >= ticks;
    }

    Consumer<Player> getSender() { return sender; }
}
