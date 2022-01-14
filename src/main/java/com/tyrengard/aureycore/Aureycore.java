package com.tyrengard.aureycore;

//import com.tyrengard.aureycore.actionbarengine.ActionBarEngine;
import com.tyrengard.aureycore.damageindicator.DamageIndicatorAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class Aureycore extends JavaPlugin {
    @Override
    public void onEnable() {
//        ActionBarEngine.start(this);
        DamageIndicatorAPI.start(this);
    }

    @Override
    public void onDisable() {
//        ActionBarEngine.stop(false);
        DamageIndicatorAPI.stop();

    }
}
