/*
 * Copyright 2014-2015 Luis <luisg at riseup.net>
 *
 * This file is a part of Barebones OldSchool.
 *
 * Barebones OldSchool is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Barebones OldSchool is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Barebones OldSchool.
 * If not, see <http://www.gnu.org/licenses/gpl.txt>.
 */

package com.github.libertadvoluntaria.gui;

import com.github.libertadvoluntaria.util.FileUtil;

import com.runeclassic.loader.ClientLoader;
import com.runeclassic.loader.applet.ClientAppletStub;

import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;
import javax.imageio.ImageIO;

public class GameFrame extends JFrame {

    private final Dimension GAME_PANEL_DIMENSION = new Dimension(765, 503);
    private final String TITLE = "Old School RuneScape";

    private ClientLoader loader;

    private BufferedImage icon = null;
    private Applet rsApplet;
    private ClientAppletStub stub;
    private JPanel gamePanel;

    public GameFrame(String world) throws Exception {
        super();

        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        try {
            this.loader = new ClientLoader(world);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                addIcon();
                initGamePanel();
                addLoadingLabel();

                pack();
                setLocationRelativeTo(null);
                setVisible(true);
            }
        });

        loadRsApplet();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                addRsApplet();
            }
        });
    }

    private void addIcon() {
        try {
            this.icon = ImageIO.read(
                FileUtil.loadResource("resources/images/rs-logo.png")
                    .openStream());
            setIconImage(icon);
        } catch (Exception e) {
            System.err.println("Failed to load icon, using Java's default.");
            e.printStackTrace();
        }
    }

    private void initGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setPreferredSize(GAME_PANEL_DIMENSION);
        gamePanel.setMinimumSize(GAME_PANEL_DIMENSION);
        gamePanel.setBackground(Color.BLACK);
        gamePanel.setOpaque(true);

        add(gamePanel, BorderLayout.CENTER);
    }

    private void addLoadingLabel() {
        try {
            URL gifPath =
                FileUtil.loadResource("resources/images/oldschool-ani.gif");
            JLabel loadingLabel =new JLabel(new ImageIcon(gifPath));
            gamePanel.add(loadingLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            System.err.println("Failed to get RS loading panel:");
            e.printStackTrace();
        }
    }

    private void loadRsApplet() {
        try {
            rsApplet = loader.getRsApplet();

            stub = loader.getAppletStub();

            rsApplet.setStub(stub);
            rsApplet.setLayout(null);
            rsApplet.setBounds(0, 0, 765, 503);
            rsApplet.init();
            rsApplet.start();
        } catch (Exception e) {

        }
    }

    private void addRsApplet() {
        try {
            gamePanel.removeAll();
            gamePanel.add(rsApplet, BorderLayout.CENTER);
            stub.setActive(true);
        } catch (Exception e) {
            System.err.println("Failed to load applet:");
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
