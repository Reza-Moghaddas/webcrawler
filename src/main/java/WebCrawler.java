import java.io.IOException;
import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {

    private static final String WEBSITE= "http://www.sedna.com/";
    private static final int MAX_DEPTH = 3;
    private HashSet<String> links;

    public WebCrawler() {
        links = new HashSet<String>();
    }

    public void getPageLinks(String URL, int depth) {
        if ((!links.contains(URL) && (depth < MAX_DEPTH))) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
                links.add(URL);

                String siteurl = WEBSITE.substring(7);
                Document document = Jsoup.connect(URL).get();

                Elements linksOnPage = document.select("a[href*="+siteurl+"]");

                depth++;
                for (Element page : linksOnPage) {
                    System.out.println(page);
                    getPageLinks(page.attr("abs:href"), depth);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());

            }
        }

    }
    public static void main(String[] args) {
        new WebCrawler().getPageLinks( WEBSITE, 0);
    }
}
