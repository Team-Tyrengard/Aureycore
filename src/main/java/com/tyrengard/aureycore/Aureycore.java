package com.tyrengard.aureycore;

//import com.tyrengard.aureycore.actionbarengine.ActionBarEngine;
import com.tyrengard.aureycore.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Aureycore extends JavaPlugin {
    @Override
    public void onEnable() {
//        ActionBarEngine.start(this);

        // listeners
        getServer().getPluginManager().registerEvents(new BrewingStandListener(this), this);
    }

    @Override
    public void onDisable() {
//        ActionBarEngine.stop(false);
    }
}
