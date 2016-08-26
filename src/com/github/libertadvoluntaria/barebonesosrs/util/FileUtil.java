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
package com.github.libertadvoluntaria.barebonesosrs.util;

import java.net.URL;

/**
 * Utility to handle file and otherwise IO operations cleanly.
 *
 * @author Luis
 */
public final class FileUtil {

    /**
     * Loads a file from the internal resources dir.
     *
     * @param   path   {@link String} representation of the path to the
     *                  internal file
     * @return          the {@link URL} to the resource
     *
     * @see             URL
     */
    public static URL loadResource(String path) {
        return FileUtil.class.getClassLoader().getResource(path);
    }
}
