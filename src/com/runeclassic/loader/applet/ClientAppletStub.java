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
package com.runeclassic.loader.applet;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.URL;

import com.runeclassic.loader.WebCrawler;

public final class ClientAppletStub implements AppletStub {

    private final URL url;

    private final WebCrawler crawler;

    private boolean active = false;

    public ClientAppletStub(URL url, WebCrawler crawler) {
        this.url = url;
        this.crawler = crawler;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public URL getDocumentBase() {
        return url;
    }

    @Override
    public URL getCodeBase() {
        return url;
    }

    @Override
    public String getParameter(String name) {
        return crawler.getProperty(name);
    }

    @Override
    public AppletContext getAppletContext() {
        throw new RuntimeException("called getAppletContext()");
    }

    @Override
    public void appletResize(int width, int height) {

    }

}
