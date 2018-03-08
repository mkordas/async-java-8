package com.michalkordas;

import java.io.IOException;
import org.jsoup.Jsoup;

public class StackOverflow {
    public static void main(String[] args) {
        System.out.println(new StackOverflow().mostRecentUnansweredQuestion());
    }

    String newestQuestion() {
            return download("questions");

    }

    String mostRecentUnansweredQuestion() {
            return download("unanswered/?tab=newest");

    }

    private String download(final String type)  {
        try {
            return Jsoup.connect("http://stackoverflow.com/" + type + "").
            get().select("a.question-hyperlink").get(0).text();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
