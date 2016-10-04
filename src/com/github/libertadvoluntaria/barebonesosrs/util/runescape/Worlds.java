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
package com.github.libertadvoluntaria.barebonesosrs.util.runescape;

import java.util.Arrays;

public final class Worlds {

    private static String defaultWorld = "81";

    final public static String[] WORLDS = {
        "1", "2", "3", "4", "5", "6", "8", "9", "10", "11", "12", "13", "14",
        "16", "17", "18", "19", "20", "21", "22", "25", "26", "27", "28", "29",
        "30", "33", "34", "35", "36", "37", "38", "41", "42", "43", "44", "45",
        "46", "49", "50", "51", "52", "53", "54", "57", "58", "59", "60", "61",
        "62", "65", "66", "67", "68", "69", "70", "73", "74", "75", "76", "77",
        "78", "81", "82", "83", "84", "85", "86", "93", "94"
    };

    public static String validateWorld(String world) {
        if (world == null) {
            return defaultWorld;
        }

        if (world.length() <= 0 || world.length() > 3) {
            System.err.println("Invalid world passed, using " + defaultWorld + " instead.");
            return defaultWorld;
        }

        // Verify and potentially fix the world, such that it's correct for us
        if (world.startsWith("3") || world.startsWith("0")) {
            world = world.substring(1);
            if (world.startsWith("0")) {
                try {
                    world = world.substring(1);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Invalid world passed, using " + defaultWorld + " instead.");
                    return defaultWorld;
                }
            }
        }

        if (!Arrays.asList(Worlds.WORLDS).contains(world)) {
            System.err.println("Invalid world passed, using " + defaultWorld + " instead.");
            return defaultWorld;
        }

        return world;
    }

    public static String getDefaultWorld() {
        return defaultWorld;
    }

    public static void setDefaultWorld(String world) {
        defaultWorld = validateWorld(world);
    }
}
