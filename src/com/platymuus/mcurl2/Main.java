package com.platymuus.mcurl2;

import javax.swing.*;
import java.net.MalformedURLException;

/**
 * The main class for McURL.
 */
public class Main {

    private static String host;
    private static String port;

    public static void main(String[] args) throws MalformedURLException {
        System.out.println("McURL v2.0 by Tad Hardesty");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // argument size handling
        if (args.length == 0) {
            new AppDialog(null, "No server specified", "Install McURL and follow links to play.");
            return;
        }
        if (args.length > 1) {
            System.out.println("Extra command-line arguments ignored");
        }

        // parse the url
        parseUrl(args[0]);
        if (host.equalsIgnoreCase("settings")) {
            new AppDialog(null, "minecraft://settings/", "McURL no longer has a settings page.");
            return;
        }

        try {
            Launcher launcher = new Launcher(new String[]{"--server", host, "--port", port});
            if (!launcher.exists()) {
                new AppDialog(null, "Launcher not found", "Run the normal Minecraft launcher at least once.");
            } else {
                new AppDialog(launcher, host + (port.equals("25565") ? "" : ":" + port), "Choose a profile with the Minecraft launcher.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            new AppDialog(null, "An unexpected error occurred", e.toString());
        }
    }

    private static void parseUrl(String text) {
        // Trim off leading minecraft: and slashes
        if (text.startsWith("minecraft:")) {
            text = text.substring(10);
        }
        while (text.charAt(0) == '/') {
            text = text.substring(1);
        }

        // Trim everything after the first /
        int i = text.indexOf('/');
        if (i >= 0) {
            text = text.substring(0, i);
        }

        // Ignore possible usernames and passwords
        int at = text.indexOf('@');
        if (at >= 0) {
            text = text.substring(at + 1);
        }

        // find the host and port
        int colon = text.indexOf(':');
        if (colon >= 0) {
            host = text.substring(0, colon);
            port = text.substring(colon + 1);
        } else {
            host = text;
            port = "25565";
        }
    }

}
