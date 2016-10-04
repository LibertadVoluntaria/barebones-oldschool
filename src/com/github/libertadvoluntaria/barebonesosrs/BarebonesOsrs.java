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
package com.github.libertadvoluntaria.barebonesosrs;

import com.github.libertadvoluntaria.barebonesosrs.gui.MainGui;
import com.github.libertadvoluntaria.barebonesosrs.util.runescape.Worlds;

public class BarebonesOsrs {

   final private static String APP_NAME = "OldSchool RuneScape";

   public static void main(String[] args) {
        MainGui mainGui;
        String world;

        if (args.length >= 1) {
            world = Worlds.validateWorld(args[0]);
        } else {
            world = Worlds.getDefaultWorld();
        }

        try {
            mainGui = new MainGui(world);
            mainGui.startGui();
        } catch (Exception e) {
            // TO-DO:   Open a small dialogue window informing the user that
            //          something went wrong.
            System.err.println("An unexpected and probably fatal exception/error has gone uncaught.");
            System.err.println("Voluntarily exiting the application.");
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println("Game has started.");
    }

   private static void setJvmArgs() {

   }

   public static String getAppName() {
       return APP_NAME;
   }
}
