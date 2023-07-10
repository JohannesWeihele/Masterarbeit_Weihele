package com.example.masterarbeit_weihele.Recycler.CommandRecycler;

public class Command_Item {
    private String command_name;
    private Boolean isCompleted = false;
    private Boolean isFocused = false;

    public Command_Item(String command_name, boolean isCompleted, boolean isFocused) {
        this.command_name = command_name;
        this.isCompleted = isCompleted;
        this.isFocused = isFocused;
    }

    public String getCommand_name() {
        return command_name;
    }

    public void setCommand_name(String command_name) {
        this.command_name = command_name;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Boolean getIsFocused() {return isFocused;}

    public void setIsFocused(Boolean Focused) {isFocused = Focused; }
}
