package com.platymuus.mcurl2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The dialog window used by McURL.
 */
public class AppDialog extends JFrame {

    private final JPanel content;
    private final Launcher launcher;

    public AppDialog(Launcher launcher, String text1, String text2) {
        super("Minecraft URL Launcher");
        this.launcher = launcher;

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        setContentPane(content);

        JLabel title = new JLabel("Minecraft URL Launcher");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14));
        JLabel label1 = new JLabel(text1);
        JLabel label2 = new JLabel(text2);
        JButton button = new JButton(launcher == null ? "Close" : "Launch");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                launchOrClose();
            }
        });
        button.setFocusable(false);
        JLabel credits = new JLabel("McURL 2.0 \u00a9 2013 by Tad Hardesty");

        title.setAlignmentX(0.5f);
        label1.setAlignmentX(0.5f);
        label2.setAlignmentX(0.5f);
        button.setAlignmentX(0.5f);
        credits.setAlignmentX(0.5f);

        content.add(title);
        content.add(Box.createVerticalStrut(5));
        content.add(label1);
        content.add(Box.createVerticalStrut(5));
        content.add(label2);
        content.add(Box.createVerticalStrut(5));
        content.add(button);
        content.add(Box.createVerticalStrut(5));
        content.add(credits);

        pack();
        Dimension size = new Dimension(getWidth(), button.getHeight());
        button.setMinimumSize(size);
        button.setPreferredSize(size);
        button.setMaximumSize(size);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void launchOrClose() {
        if (launcher != null) {
            dispose();
            launcher.startLauncher();
        } else {
            System.exit(0);
        }
    }

}
