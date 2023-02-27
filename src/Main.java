import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Crawler> bots = new ArrayList<>();

        bots.add(new Crawler("https://www.poewiki.net/wiki/Path_of_Exile_Wiki", 1));
        bots.add(new Crawler("https://en.wikipedia.org/wiki/Main_Page", 2));
        bots.add(new Crawler("https://terraria.fandom.com/wiki/Terraria_Wiki", 3));

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
