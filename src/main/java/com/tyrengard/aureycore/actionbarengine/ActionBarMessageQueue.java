//package com.tyrengard.aureycore.actionbarengine;
//
//import java.util.HashMap;
//import java.util.UUID;
//import java.util.concurrent.LinkedBlockingQueue;
//
//public class ActionBarMessageQueue extends HashMap<UUID, LinkedBlockingQueue<ActionBarMessage>> {
//    public ActionBarMessageQueue() {
//        super();
//    }
//
//    public void clearQueue(UUID id) {
//        put(id, new LinkedBlockingQueue<>());
//    }
//
//    public void addMessage(UUID id, ActionBarMessage message) {
//        LinkedBlockingQueue<ActionBarMessage> cs = get(id);
//        if (cs == null) cs = new LinkedBlockingQueue<>();
//        cs.add(message);
//        put(id, cs);
//    }
//}
