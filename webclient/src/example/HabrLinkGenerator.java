package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ThreadLocalRandom;

public class HabrLinkGenerator {

    final static String HABR_URL = "https://habr.com/ru";
    final static String POST_LINK = "https://habr.com/ru/post/";

    public String getRandomHabrLink () throws IOException {
        int lastPostNumber = getLastPostNumber();

        int statusCode;
        String randomLink;

        do {
            int randomNum = ThreadLocalRandom.current().nextInt(1, lastPostNumber + 1);
            randomLink = POST_LINK + randomNum;

            URL url = new URL(randomLink);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            statusCode = http.getResponseCode();
            if (statusCode != 200) {
                System.out.println(randomLink + " Response " + statusCode + ". Generate new link");
            }
        } while (statusCode != 200);

        return randomLink;
    }

    private int getLastPostNumber() throws IOException {
        URL url = new URL(HABR_URL);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        String body;

        //Find first string like <a href="https://habr.com/ru/post/446576/"
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            body = br.lines()
                    .filter(s -> s.contains(POST_LINK))
                    .findFirst()
                    .get();
        }
        String number = body.substring(body.indexOf(POST_LINK) + POST_LINK.length(), body.indexOf("/\""));
        int lastPostNumber = Integer.parseInt(number);
        System.out.println("Last post is " + lastPostNumber);
        return lastPostNumber;
    }

}
