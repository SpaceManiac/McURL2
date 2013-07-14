package com.platymuus.mcurl2;

import javax.swing.*;
import java.net.MalformedURLException;
import java.util.Scanner;

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
            if (System.getenv("MCURL_WINDOWS") != null) {
                try {
                    windowsInstall();
                } catch (Exception e) {
                    e.printStackTrace();
                    new AppDialog(null, "Unexpected installation error", e.toString());
                }
            } else {
                new AppDialog(null, "No server specified", "Install McURL and follow links to play.");
            }
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

    private static void windowsInstall() throws Exception {
        // called when environment variable MCURL_WINDOWS is set
        // job is to install McURL to the Windows registry
        System.out.println("Performing Windows installation");

        String exe = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        if (exe.startsWith("/")) {
            exe = exe.substring(1);
        }
        if (!exe.endsWith(".exe")) {
            new AppDialog(null, "Installation failed", "You must install from a .exe file.");
            return;
        }
        exe = exe.replace("/", "\\");
        System.out.println("This file: " + exe);

        String root = "HKCR\\minecraft";
        String values[][] = {
                {root, "", "Minecraft Server (McURL) protocol"},
                {root, "URL Protocol", ""},
                {root + "\\DefaultIcon", "", exe + ",0"},
                {root + "\\shell", "", "open"},
                {root + "\\shell\\open", "", ""},
                {root + "\\shell\\open\\command", "", exe + " %1"}
        };

        Runtime rt = Runtime.getRuntime();
        for (String[] entry : values) {
            String key = entry[0];
            String value = entry[1];
            String data = entry[2];
            String cmd = "REG ADD " + key + " /f /v \"" + value + "\" /d \"" + data + "\"";
            System.out.println(cmd);

            Process p = rt.exec(cmd);
            Scanner err = new Scanner(p.getErrorStream());
            int result = p.waitFor();

            String errorLine = "";
            if (err.hasNextLine()) {
                errorLine = err.nextLine();
            }

            if (errorLine.contains("Access is denied")) {
                new AppDialog(null, "Installation failed", "You must run as administrator to install.");
                return;
            } else if (result != 0) {
                new AppDialog(null, "Installation failed", "Registry error: " + errorLine);
                return;
            }
        }

        new AppDialog(null, "Installation succeeded", "Follow minecraft links to play.");
    }

}
