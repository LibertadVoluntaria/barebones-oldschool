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

package com.github.libertadvoluntaria;

import com.github.libertadvoluntaria.game.GameFrame;
import com.github.libertadvoluntaria.util.runescape.Worlds;

public class Application {

   public static void main(String[] args) {
        GameFrame gameFrame;

        String world;

        if (args.length >= 1) {
            world = Worlds.validateWorld(args[0]);
        } else {
            world = Worlds.getDefaultWorld();
        }

        try {
            gameFrame = new GameFrame(world);

            gameFrame.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}