package com.blockradius.admin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MagicLinkRegistry {

    public static class MagicContext {
        private final int producerId;
        private final int projectIndex;

        public MagicContext(int producerId, int projectIndex) {
            this.producerId = producerId;
            this.projectIndex = projectIndex;
        }

        public int getProducerId() {
            return producerId;
        }

        public int getProjectIndex() {
            return projectIndex;
        }
    }

    // Simple in-memory map for dev / beta
    private final Map<String, MagicContext> store = new ConcurrentHashMap<>();

    public void register(String magicId, int producerId, int projectIndex) {
        store.put(magicId, new MagicContext(producerId, projectIndex));
    }

    public MagicContext resolve(String magicId) {
        return store.get(magicId);
    }
}
