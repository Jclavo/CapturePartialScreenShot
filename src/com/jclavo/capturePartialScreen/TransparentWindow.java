package com.jclavo.capturePartialScreen;

import javax.swing.*;
import java.awt.*;

import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

public class TransparentWindow extends JFrame {

    public TransparentWindow() {
        super("TranslucentWindow");
        setLayout(new GridBagLayout());

        setSize(300,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        // Determine if the GraphicsDevice supports translucency.
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        //If translucent windows aren't supported, exit.
        if (!gd.isWindowTranslucencySupported(TRANSLUCENT)) {
            System.err.println(
                    "Translucency is not supported");
            System.exit(0);
        }

        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create the GUI on the event-dispatching thread
       SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TransparentWindow tw = new TransparentWindow();

                // Set the window to 55% opaque (45% translucent).
                tw.setOpacity(0.25f);

                // Display the window.
                tw.setVisible(true);
            }
        });
    }
}
