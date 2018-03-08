package com.michalkordas;

import java.io.IOException;
import java.net.URLEncoder;
import org.jsoup.Jsoup;

public class Google {
    String hits(String q) {
        try {
            return Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(q, "UTF-8"))
                .userAgent("Mozilla/5.0")
                .get().select("#resultStats").text().replaceAll("&nbsp;", "");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    String topResult(String q) {
        try {
            return Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(q, "UTF-8"))
                .userAgent("Mozilla/5.0")
                .get().select(".g").text().replaceAll("&nbsp;", "");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
