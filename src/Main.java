import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        ArrayList<Crawler> bots = new ArrayList<>();
        BlockingQueue<String> linksQueue = new ArrayBlockingQueue<>(100);

        bots.add(new Crawler("https://coinmarketcap.com/", linksQueue));

        for (Crawler crawler : bots) {
            try {
                crawler.getThread().join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
