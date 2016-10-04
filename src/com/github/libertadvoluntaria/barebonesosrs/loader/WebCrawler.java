/*
 * Copyright (c) 2013 Hikilaka
 *
 * This software is provided 'as-is', without any express or implied
 * warranty. In no event will the authors be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *    1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 *
 *    2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 *
 *    3. This notice may not be removed or altered from any source
 *    distribution.
 *
 * Modified by Luis
 */
package com.github.libertadvoluntaria.barebonesosrs.loader;

import com.github.libertadvoluntaria.barebonesosrs.util.StringUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Crawls the RuneScape webclient page - reading and parsing all the properties
 * which are passed to the client applet.
 *
 * @author hikilaka
 * @author luis
 */
public final class WebCrawler {

    final private URL url;

    final private Map<String, String> properties = new HashMap<>();

    final private static String USER_AGENT
        = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0";

    final private static Pattern ARCHIVE_PATTERN
        = Pattern.compile("archive=(.*)\\s+");

    final private static Pattern PARAMETER_PATTERN
        = Pattern.compile("<param name=([^\\s]+)\\s+value=([^>]*)>");

    public WebCrawler(URL url) {
        this.url = url;
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public void start() throws IOException {
        readWebContents(url);
    }

    private void readWebContents(URL url) throws IOException {
        HttpURLConnection connection
            = (HttpURLConnection) (url.openConnection());
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept",
            "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate");

        StringBuilder contents = new StringBuilder();
        BufferedReader stream = null;
        try {
            InputStreamReader inReader
                = new InputStreamReader(connection.getInputStream());
            stream = new BufferedReader(inReader);

            String read;
            while ((read = stream.readLine()) != null) {
                contents.append(read);
            }
        } catch (UnknownHostException e) {
            System.err.println("Failed to connect to RuneScape, possible connection issue.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            parseWebContents(contents.toString());

            if (stream != null) {
                stream.close();
            }
        }
    }

    private void parseWebContents(String contents) {
        // Parse the parameters.
        Matcher matcher = PARAMETER_PATTERN.matcher(contents);
        while (matcher.find()) {
            String[] tokens = StringUtil.cleanHtml(matcher.group()).split(" ");
            String key = tokens[0].substring(tokens[0].indexOf("=") + 1);
            String value = tokens[1].substring(tokens[1].indexOf("=") + 1);
            properties.put(key, value);
        }

        // Parse the archive and main class.
        matcher = ARCHIVE_PATTERN.matcher(contents);
        if (matcher.find()) {
            String[] tokens = matcher.group().split(" ");
            properties.put("archive-name", tokens[0].split("=")[1]);
            properties.put("main-class", tokens[1].split("=")[1]);
        }
    }
}
