package com.peterkomar.carfeatures.Activity;

public class Messages {
    private static StringBuilder msg = null;

    public static void info(String message) {
        if (Messages.msg == null) {
            Messages.msg = new StringBuilder();
        }
        Messages.msg.append(String.format("Info: %s\n", message));
    }

    public static void cleanMessages() {
        Messages.msg = null;
        Messages.msg = new StringBuilder();
    }

    public static void error(String message) {
        if (Messages.msg == null) {
            Messages.msg = new StringBuilder();
        }
        Messages.msg.append(String.format("Error: %s\n", message));
    }

    public static String getStrLogs() {
        return Messages.msg.toString();
    }
}
