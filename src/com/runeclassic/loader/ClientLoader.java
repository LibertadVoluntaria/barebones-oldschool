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

import com.runeclassic.loader.applet.ClientAppletStub;

import java.applet.Applet;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;

import javax.swing.*;

public class ClientLoader {

    private static String currentWorld;

    private URL appletUrl;
    private WebCrawler crawler;
    private URLClassLoader urlClassLoader;
    private ClientAppletStub stub;

    public ClientLoader(String world) throws Exception {
        this.currentWorld = world;

        this.appletUrl
            = new URL("http://oldschool" + this.currentWorld + ".runescape.com/");
        this.crawler = new WebCrawler(appletUrl);
        this.stub = new ClientAppletStub(this.appletUrl, this.crawler);
        crawler.start();
    }

    public Applet getRsApplet() throws Exception {
        URL[] sources = new URL[] {
            new URL(appletUrl.toString() + crawler.getProperty("archive-name"))
        };

        urlClassLoader = new URLClassLoader(sources);

        String mainClass = crawler.getProperty("main-class");
        Class<?> appletClass = urlClassLoader
            .loadClass(mainClass.substring(0, mainClass.length() - 6));

        Applet applet = (Applet) (appletClass.getConstructor().newInstance());

        return applet;
    }

    public ClientAppletStub getAppletStub() {
        return stub;
    }

    public URLClassLoader getUrlClassLoader() {
        return urlClassLoader;
    }

    public String getWorld() {
        return currentWorld;
    }
}
