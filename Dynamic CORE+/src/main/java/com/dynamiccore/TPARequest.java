package com.dynamiccore;

import java.util.UUID;

public class TPARequest {
    private final UUID requester;
    private final UUID target;
    private final Type type;

    public TPARequest(UUID requester, UUID target, Type type) {
        this.requester = requester;
        this.target = target;
        this.type = type;
    }

    public UUID getRequester() {
        return requester;
    }

    public UUID getTarget() {
        return target;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        TPA, TPAHERE
    }
}