package com.platymuus.mcurl2;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Finds and calls the already-existing launcher jar with our additional arguments.
 */
public class Launcher {

    /**
     * This is set really high so the launcher won't emit warnings. McURL can still update if needed.
     */
    private static final int BOOTSTRAP_VERSION = 2315;

    private File minecraftDir;
    private File launcherJar;
    private String[] arguments;

    public Launcher(String[] arguments) {
        this.arguments = arguments;
        minecraftDir = getMinecraftDir();
        launcherJar = new File(minecraftDir, "launcher.jar");
        System.out.println("Looking for launcher jar at: " + launcherJar);
    }

    public boolean exists() {
        return launcherJar.exists();
    }

    public void startLauncher() {
        System.out.println("Starting launcher.");
        JFrame frame = createFrame();
        try {
            Class<?> aClass = new URLClassLoader(new URL[]{launcherJar.toURI().toURL()}).loadClass("net.minecraft.launcher.Launcher");
            Constructor constructor = aClass.getConstructor(new Class[]{JFrame.class, File.class, Proxy.class, PasswordAuthentication.class, String[].class, Integer.class});
            constructor.newInstance(frame, minecraftDir, Proxy.NO_PROXY, null, arguments, BOOTSTRAP_VERSION);
            frame.setLocationRelativeTo(null); // The frame resizes itself, so we move it to the middle again
        } catch (Exception e) {
            e.printStackTrace();
            frame.setVisible(false);
            new AppDialog(null, "Error starting launcher", e.toString());
        }
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Minecraft Launcher (via McURL)");
        frame.setSize(854, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    private File getMinecraftDir() {
        String os = System.getProperty("os.name", "").toLowerCase();
        String home = System.getProperty("user.home", ".");

        if (os.contains("win")) {
            String appdata = System.getenv("APPDATA");
            if (appdata != null) {
                return new File(appdata, ".minecraft");
            } else {
                return new File(home, ".minecraft");
            }
        } else if (os.contains("mac")) {
            return new File(home, "Library/Application Support/minecraft");
        } else {
            return new File(home, ".minecraft/");
        }
    }

}
