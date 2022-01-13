package com.tyrengard.aureycore.actionbarengine;

import net.md_5.bungee.api.chat.TextComponent;

@FunctionalInterface
public interface SimpleTextComponentGenerator extends TextComponentGenerator {
    String generateString();
    default TextComponent generate() {
        return new TextComponent(generateString());
    }
}
