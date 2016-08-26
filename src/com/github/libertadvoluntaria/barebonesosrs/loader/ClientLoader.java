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

import com.github.libertadvoluntaria.barebonesosrs.loader.applet.ClientAppletStub;

import java.applet.Applet;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Manages, invokes changes, and keeps track of changes in the client being
 * loaded in an instance, along with loading a new client for the application.
 *
 * @author hikilaka
 * @author luis
 */
public class ClientLoader {

    private static String loadedWorld;

    private URL appletUrl;
    private WebCrawler crawler;
    private ClassLoader classLoader;
    private ClientAppletStub stub;

    public ClientLoader(String world) throws Exception {
        this.loadedWorld = world;
        this.appletUrl
            = new URL("http://oldschool" +
                this.loadedWorld + ".runescape.com/");
        this.crawler = new WebCrawler(appletUrl);
        this.stub = new ClientAppletStub(appletUrl, crawler);
    }

    public void loadClient() throws IOException {
        crawler.start();
    }

    public Applet getApplet() throws Exception {
        String mainClassName = crawler.getProperty("main-class");
        URL[] sources = new URL[] {
            new URL(appletUrl.toString() + crawler.getProperty("archive-name"))
        };
        classLoader = new URLClassLoader(sources);
        Class<?> appletClass = null;
        Applet applet = null;

        try {
            String mainClassNameShortened
                = mainClassName.substring(0, mainClassName.length() -6);
            appletClass
                = classLoader.loadClass(mainClassNameShortened);
            applet = (Applet) (appletClass.getConstructor().newInstance());
        } catch (Exception e) {
            System.err.println("Failed to load or parse main class from gamepack.");
            throw e;
        }

        return applet;
    }

    public ClientAppletStub getAppletStub() {
        return stub;
    }

    /**
     * Returns the {@link ClassLoader} used to load the client's classes.
     * @return  the {@link ClassLoader} instance related to the game's client
     */
    public ClassLoader getLoaderForClient() {
        return classLoader;
    }

    /**
     * Returns a {@link String} representation of the world that we loaded the
     * client in to initially.
     *
     * @return  {@link String} representation of world initially loaded
     */
    public String getLoadedWorld() {
        return loadedWorld;
    }
}
