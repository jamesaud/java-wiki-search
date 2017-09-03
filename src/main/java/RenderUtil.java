import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by jamesaudretsch on 9/2/17.
 */
public class RenderUtil {
     String renderContent(String htmlFile) {
        try {
            return new String(Files.readAllBytes(Paths.get(getClass().getResource(htmlFile).toURI())), StandardCharsets.UTF_8);
        }
        catch (IOException e){
            return e.toString();
        }
        catch (URISyntaxException e){
            return e.toString();
        }
    }

}
