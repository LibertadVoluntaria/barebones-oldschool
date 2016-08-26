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

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Launcher {

    public static void main(String[] args) {
        URL jarUrl= Launcher.class
            .getProtectionDomain().getCodeSource().getLocation();
        String jarLocStr = null;
        try {
            jarLocStr = Paths.get(jarUrl.toURI()).toString();
        } catch (URISyntaxException e) {
            System.err.println("Something wacky related to that jar's location just happened. It is probably unrecoverable, exiting.");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            jarLocStr = URLDecoder.decode(jarLocStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.err.println("You somehow don't have UTF-8, let\'s hope we can somehow do without.");
        }

        //jarLocStr = jarLocStr.replaceFirst("file:", "");

        String commandBaseStr = "javaw -Xmx256m -Dsun.java2d.noddraw=true -XX:CompileThreshold=1500 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -cp";
        String commandStr = commandBaseStr + " " + "\"" + jarLocStr + "\"" +
            " " + "com.github.libertadvoluntaria.barebonesosrs.BarebonesOsrs";


        //String[] argsArr = commandStr.split(" ");
        //ArrayList argsList = new ArrayList<>();
        ProcessBuilder procBuild = new ProcessBuilder();
        procBuild.command(commandStr);

        try {
            Process barebonesOsrsProc = procBuild.start();
        } catch (Exception e) {
            System.err.println("Something probably unrecoverable has happened, exiting.");
            e.printStackTrace();
            System.exit(1);
        }

        /*
        try {
            Runtime.getRuntime().exec(commandStr);
        } catch (Exception e) {
            System.err.println("Something probably unrecoverable has happened, exiting.");
            e.printStackTrace();
            System.exit(1);
        }
                */
        System.exit(0);
    }
}
