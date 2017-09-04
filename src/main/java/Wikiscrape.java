import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
/**
 * Created by jamesaudretsch on 9/1/17.
 */
import java.util.regex.Pattern;

// A class to scrape a wikipedia page
public final class Wikiscrape {
    static final String validLinkStart = "/wiki/";
    static final String wikibaseurl = "https://en.wikipedia.org";

    public static String searchFirst(String wikiurl) throws IOException{
        Document doc = Jsoup.connect(wikibaseurl + wikiurl).get();
        Elements pElements = doc.select(".mw-parser-output > p");
        String html = "";

        for(Element elem: pElements){
            html += elem.html();
        }

        String removedParens = html.replaceAll("[(][^)]*[)]", "");
        doc = Jsoup.parse(removedParens);

        String url = "";
        String result = null;
        for (Element elem: doc.select("a")){
             url = elem.attr("href");
             if (Wikiscrape.linkIsValid(url)){
                result = url;
                break;
            }
        }

        return result;
    }

    static boolean linkIsValid(String link){
        // Because all html in parens were removed, cases have to be taken out like "english_(disambiguation)"
        // Links that contain ":" are to wikipedia Help center
        return link.startsWith(validLinkStart) && !link.endsWith("_") && !link.contains(":");
    }

}
