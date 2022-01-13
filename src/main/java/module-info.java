module aureycore {
    requires org.bukkit;
    requires java.logging;
    requires bungeecord.chat;
    requires morphia.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires annotations;
    exports com.tyrengard.aureycore.common.anvilevents;
    exports com.tyrengard.aureycore.common.stringformat;
    exports com.tyrengard.aureycore.common.interfaces;
    exports com.tyrengard.aureycore.common.struct;
    exports com.tyrengard.aureycore.common.utils;
    exports com.tyrengard.aureycore.foundation;
    exports com.tyrengard.aureycore.playerstate;
    exports com.tyrengard.aureycore.damageindicator;
    exports com.tyrengard.aureycore.customguis;
    exports com.tyrengard.aureycore;
    exports com.tyrengard.aureycore.common.armorevents;
    exports com.tyrengard.aureycore.foundation.db;
}