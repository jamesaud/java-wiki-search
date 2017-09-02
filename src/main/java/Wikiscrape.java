import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.util.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by jamesaudretsch on 9/1/17.
 */

// A class to scrape a wikipedia page
public class Wikiscrape {
    static final String validLinkStart = "/wiki/";

    //Returns a set of all links that appear in the body of a wikipedia page
    // wikiurl should be something like "/wiki/topic_of_choice"
    public HashSet<String> search(String wikiurl) throws IOException {
        Document doc = Jsoup.connect("https://en.wikipedia.org" + wikiurl).get();
        Elements linkElements = doc.select("#bodyContent a");
        HashSet<String> links = new HashSet<>();

        for (Element link: linkElements){
            String href = link.attr("href").trim();
            if (this.linkIsValid(href)){
                links.add(href);
            }
        }
        return links;
    }

    boolean linkIsValid(String link){
        return link.startsWith(validLinkStart) && !link.contains(":");
    }
}
