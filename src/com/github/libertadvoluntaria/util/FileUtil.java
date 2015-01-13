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

package com.github.libertadvoluntaria.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;

public final class FileUtil {

    public static URL loadResource(String path) {
        return FileUtil.class.getClassLoader().getResource(path);
    }

    public static Font
        loadFont(String file) throws FontFormatException, IOException {
        URL fontUrl = loadResource("resources/fonts/" + file);
        return Font.createFont(
            Font.TRUETYPE_FONT, fontUrl.openStream());
    }

    public static ImageIcon loadIcon(String file, String text) {
        URL iconUrl = loadResource("resources/images/icons/" + file);
        return new ImageIcon(iconUrl, text);
    }

    public static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }

    public static void
        writeToFile(File file, String text) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(text);
        }
    }
}
