package com.github.vacancies.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupUtil {
    public static Document getDocument(String url_template, int page) throws IOException {
        String url = String.format(url_template, page);

        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")
                .referrer(url)
                .get();
    }
}
