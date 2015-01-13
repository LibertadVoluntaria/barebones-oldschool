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
 */

/*
 * Modified by Luis
 */

package com.runeclassic.loader;

import com.runeclassic.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Crawls the RuneScape webclient page - reading and parsing all the properties
 * which are passed into the client applet
 */
public final class WebCrawler {

    private static final Pattern ARCHIVE_PATTERN
        = Pattern.compile("archive=(.*)\\s+");

    private static final Pattern PARAMETER_PATTERN
        = Pattern.compile("<param name=([^\\s]+)\\s+value=([^>]*)>");

    private final Map<String, String> properties = new HashMap<>();

    private final URL url;

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
            = HttpURLConnection.class.cast(url.openConnection());
        connection.setRequestProperty("User-Agent",
            "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1913.0 Safari/537.36");
        connection.setRequestProperty("Accept",
            "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate");

        StringBuilder contents = new StringBuilder();

        BufferedReader stream
            = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        try {
            String read;
            while ((read = stream.readLine()) != null) {
                contents.append(read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            parseWebContents(contents.toString());
            stream.close();
        }
    }

    private void parseWebContents(String contents) {
        // parse the parameters
        Matcher matcher = PARAMETER_PATTERN.matcher(contents);

        while (matcher.find()) {
            String[] tokens = StringUtil.cleanHtml(matcher.group()).split(" ");
            String key = tokens[0].split("=")[1];
            String value = StringUtil.join(tokens[1].split("="), 1);

			//if (value.equals("http://www.runescape.com/slr.ws?orderLPWM")) {
            //  value = "http://silabsoft.org/slr.ws?order=LPWM";
            //}
            properties.put(key, value);
        }

        // parse the archive and main class
        matcher = ARCHIVE_PATTERN.matcher(contents);

        if (matcher.find()) {
            String[] tokens = matcher.group().split(" ");
            properties.put("archive-name", tokens[0].split("=")[1]);
            properties.put("main-class", tokens[1].split("=")[1]);
        }
    }
}
