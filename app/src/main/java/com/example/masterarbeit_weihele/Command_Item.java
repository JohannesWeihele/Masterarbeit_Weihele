package com.example.masterarbeit_weihele;

public class Command_Item {
    String command_name;
    Boolean isCompleted = false;

    public Command_Item(String command_name) {
        this.command_name = command_name;
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


}
