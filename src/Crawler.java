import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class Crawler {

    public static void main(String[] args) {
        String url = "https://www.poewiki.net/wiki/Path_of_Exile_Wiki";
        crawl(0, url, new ArrayList<String>());
    }

    private static void crawl(int level, String url, ArrayList<String> visited) {
        if(level < 2) {
            Document document = request(url, visited);
            System.out.println("Level: " + level);

            if(document != null) {
                for(Element link : document.select("a[href]")) {
                    String next_link = link.absUrl("href");
                    if(!visited.contains(next_link)) {
                        crawl(level++, next_link, visited);
                    }
                }
            }
        }
    }

    private static Document request(String url, ArrayList<String> visited) {
        try {
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();

            if(connection.response().statusCode() == 200) {
                System.out.println("Link: " + url);
                System.out.println(document.title());
                visited.add(url);

                return document;
            }
            return null;
        }
        catch(IOException e) {
            return null;
        }
    }
}
