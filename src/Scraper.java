import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class Scraper implements Runnable {
    private BlockingQueue<Document> linksQueue;
    private Thread thread;
    public ArrayList<HashMap<String, String>> scrapedData;
    private Crawler crawler;

    public Scraper(BlockingQueue<Document> queue, Crawler crawler) {
        this.linksQueue = queue;
        this.crawler = crawler;
        this.scrapedData = new ArrayList<HashMap<String, String>>();

        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        while (this.crawler.getThread().isAlive()) {
            if (linksQueue.peek() != null) {
                Document document = linksQueue.poll();
                System.out.println(document.title());
                scrap(document);
            }
        }
    }

    private void scrap(Document document) {
        Elements elements = document.select("tbody[data-target=currencies.contentBox] tr");
        for (Element element : elements) {
            scrapedData.add(scrapRow(element));
        }
    }

    private HashMap<String, String> scrapRow(Element element) {
        HashMap<String, String> row = new HashMap<String, String>();

        row.put("name", element.select("td.coin-name span").get(0).text());
        row.put("symbol", element.select("td.coin-name span").get(1).text());
        row.put("price", element.select("td.price span").text());
        row.put("1h", element.select("td.change1h span").text());
        row.put("24h", element.select("td.change24h span").text());
        row.put("7d", element.select("td.change7d span").text());
        row.put("24h_volume", element.select("td.lit span").text());
        row.put("mkt_cap", element.select("td.cap span").text());

        return row;
    }

    public Thread getThread() {
        return this.thread;
    }

}
