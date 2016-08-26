/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.
 * If not, see <http://www.gnu.org/licenses/gpl.txt>.
 */
package com.github.libertadvoluntaria.barebonesosrs.gui;

import com.github.libertadvoluntaria.barebonesosrs.BarebonesOsrs;
import com.github.libertadvoluntaria.barebonesosrs.loader.ClientLoader;
import com.github.libertadvoluntaria.barebonesosrs.loader.applet.ClientAppletStub;
import com.github.libertadvoluntaria.barebonesosrs.util.FileUtil;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.*;
import javax.imageio.ImageIO;

public class MainGui extends JFrame {

    private String frameName = BarebonesOsrs.getAppName();

    // The size of the game's applet when it is in "fixed" mode.
    final private Dimension GAME_PANEL_FIXED_DIMENSION =
        new Dimension(765, 503);

    private JPanel gamePanel;
    private JLabel loadingLabel;
    private Applet client;
    private BufferedImage icon = null;

    private ClientLoader loader;
    private ClientAppletStub stub;

    public MainGui(String world) throws Exception {
        super();

        this.setTitle(frameName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        try {
            this.loader = new ClientLoader(world);
        } catch (Exception e) {
            System.err.println("Failed to create a new ClientLoader.");
            throw e;
        }
    }

    public void startGui() {
        SwingUtilities.invokeLater(() -> {
            JPopupMenu.setDefaultLightWeightPopupEnabled(false);
            addIcon();
            addComponents();
            setLoadingLabel();

            this.pack();
            this.setLocationRelativeTo(null); // Draw gui in the middle.
            this.setVisible(true);
        });

        try {
            loader.loadClient();
        } catch (Exception e) {
            // TO-DO:   Inform the user that this has happened.
            System.err.println("Failed to load RuneScape's client.");
            e.printStackTrace();
        }

        try {
            loadApplet();
        } catch (Exception e) {
            System.err.println("Failed to load client's applet.");
            e.printStackTrace();
            // fallback();
            return;
        }

        SwingUtilities.invokeLater(() -> {
            addApplet();
            pack();
        });
    }

    private void addIcon() {
        try {
            icon
                = ImageIO.read(FileUtil.loadResource(
                    "res/images/rs-logo.png").openStream());
            this.setIconImage(icon);
        } catch (Exception e) {
            System.err.println("Failed to load icon.");
            e.printStackTrace();
        }
    }

    private void addComponents() {
        addGamePanel();
    }

    private void addGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(Color.BLACK);
        gamePanel.setPreferredSize(GAME_PANEL_FIXED_DIMENSION);
        gamePanel.setMinimumSize(GAME_PANEL_FIXED_DIMENSION);
        this.add(gamePanel, BorderLayout.CENTER);
    }

    private void setLoadingLabel() {
        try {
            URL gifPath =
                FileUtil.loadResource("res/images/oldschool-ani.gif");
            loadingLabel = new JLabel(new ImageIcon(gifPath));
            gamePanel.add(loadingLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            System.err.println("Failed to get RuneScape's loading panel.");
            e.printStackTrace();
        }
    }

    private void loadApplet() throws Exception {
        try {
            client = loader.getApplet();
            stub = loader.getAppletStub();
            client.setStub(stub);
            client.setLayout(null);
            client.setPreferredSize(GAME_PANEL_FIXED_DIMENSION);
            client.init();
            client.start();
        } catch (Exception e) {
            throw e;
        }
    }

    private void addApplet() {
        try {
            gamePanel.remove(loadingLabel);
            loadingLabel = null;    // Let the loading label be garbage
                                    // collected, as it is no longer needed
            gamePanel.add(client, BorderLayout.CENTER);
            stub.setActive(true);
        } catch (Exception e) {
            System.err.println("Failed to add the game's applet.");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
