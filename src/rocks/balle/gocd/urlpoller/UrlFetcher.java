package rocks.balle.gocd.urlpoller;

import com.thoughtworks.go.plugin.api.logging.Logger;
import com.tw.go.plugin.util.HttpRepoURL;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vagrant on 12/12/14.
 */
public class UrlFetcher {
    private String _url;
    private String _pattern;

    private static Logger LOGGER = Logger.getLoggerFor(UrlFetcher.class);

    public UrlFetcher(String url, String pattern) {
        _url = url;
        _pattern = pattern;
    }

    public String Execute() {
        return GetFromPattern(Download());
    }

    private String Download() {
        if(_url==null || _url=="") {
            throw  new RuntimeException("Url must be specified");
        }

        HttpRepoURL repo = new HttpRepoURL(_url, null, null);
        DefaultHttpClient client = repo.getHttpClient();
        HttpGet method= new HttpGet(_url);
        try {
            HttpResponse response = client.execute(method);
            if(response.getStatusLine().getStatusCode() != 200){
                throw new RuntimeException(String.format("HTTP %s, %s",
                        response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase()));
            }

            HttpEntity entity = response.getEntity();
            return IOUtils.toString(entity.getContent());
        } catch(Exception ex) {
            String message = String.format("%s (%s) while downloading %s ",
                    ex.getClass().getSimpleName(),
                    ex.getMessage(), _url);
            LOGGER.error(message);
            throw new RuntimeException(message, ex);
        } finally {
            method.releaseConnection();
            client.getConnectionManager().shutdown();
        }
    }

    private String GetFromPattern(String content) {

        Pattern pattern = Pattern.compile(_pattern);
        Matcher matcher = pattern.matcher(content);
        if(matcher.find()) {
            return matcher.group(matcher.groupCount());
        }

        String message = String.format("Pattern: %s not found in content from %s ",
                _pattern, _url);
        LOGGER.error(message);

        throw new RuntimeException(message);
    }

}