package com.example.light_remote;

public class Blynk_Test {

    private int Int;
    private String command;

    public Blynk_Test(int anInt, String anCommand) {
        Int = anInt;
        command = anCommand;
    }
    public Blynk_Test() {
    }

    public int getInt() {
        return Int;
    }
    public void setInt(int anInt) {
        Int = anInt;
    }
    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }

}
