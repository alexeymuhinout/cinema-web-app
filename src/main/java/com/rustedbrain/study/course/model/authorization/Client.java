package com.rustedbrain.study.course.model.authorization;

public class Client extends User {

    private boolean permanentlyBanned;

    public boolean isPermanentlyBanned() {
        return permanentlyBanned;
    }

    public void setPermanentlyBanned(boolean permanentlyBanned) {
        this.permanentlyBanned = permanentlyBanned;
    }
}
