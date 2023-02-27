import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class Crawler implements Runnable {
    private static final int MAX_DEPTH = 3;
    private Thread thread;
    private String link;
    private ArrayList<String> visitedLinks = new ArrayList<String>();
    private int ID;

    public Crawler(String link, int num) {
        this.link = link;
        this.ID = num;

        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        crawl(0, this.link);
    }

    private void crawl(int level, String url) {
        if (level < MAX_DEPTH) {
            Document document = request(url);

            if (document != null) {
                for (Element link : document.select("a[href^=/]")) {
                    String next_link = link.absUrl("href");
                    if (!visitedLinks.contains(next_link)) {
                        // System.out.println("ID: " + ID + " " + next_link);
                        crawl(level++, next_link);
                    }
                }
            }
        }
    }

    private Document request(String url) {
       try {
           Connection connection = Jsoup.connect(url);
           Document document = connection.get();

           if (connection.response().statusCode() == 200) {
               String title = document.title();
               System.out.println("ID: " + ID + " URL: " + url);

               visitedLinks.add(url);
               return document;
           }
           return null;
       }
       catch (IOException e) {
           return null;
       }
    }

    public Thread getThread() {
        return thread;
    }
}
