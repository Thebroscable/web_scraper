import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.jsoup.nodes.Document;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Document> linksQueue = new ArrayBlockingQueue<>(10);

        Crawler crawler = new Crawler("https://www.coingecko.com/", linksQueue);
        Scraper scraper = new Scraper(linksQueue, crawler);

        try {
            scraper.getThread().join();
        }
        catch (InterruptedException e) {

        }

        for (HashMap<String, String> line : scraper.scrapedData) {
            System.out.println(line);
        }
    }
}
