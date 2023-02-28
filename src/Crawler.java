import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Crawler implements Runnable {
    private Thread thread;
    private String link;
    public BlockingQueue<String> linksQueue;

    public Crawler(String url, BlockingQueue<String> queue) {
        this.link = url;
        this.linksQueue = queue;

        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        crawl();
    }

    private void crawl() {
        String next_link = this.link;

        while (next_link != null) {
            Document document = request(next_link);
            next_link = null;

            if (document != null) {
                Element link = document.select("li.next").select("a[href]").first();

                if (link != null) {
                    next_link = link.absUrl("href");
                }
            }
        }
    }

    private Document request(String url) {
       try {
           Connection connection = Jsoup.connect(url);
           Document document = connection.get();

           if (connection.response().statusCode() == 200) {
               System.out.println(" URL: " + url);

               linksQueue.put(url);
               return document;
           }
           return null;
       }
       catch (IOException | InterruptedException e) {
           return null;
       }
    }

    public Thread getThread() {
        return thread;
    }
}
