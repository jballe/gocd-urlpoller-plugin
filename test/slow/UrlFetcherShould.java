import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import rocks.balle.gocd.urlpoller.UrlFetcher;

import static org.junit.Assert.assertThat;

/**
 * Created by vagrant on 12/12/14.
 */
public class UrlFetcherShould {

    @Test
    public void DownloadFile() {
        String url = "http://download.go.cd/local/releases.json";
        String pattern = "\"type\":\\s+\"windows-server\",\\s*\"name\": \"([^\\\"]+)\"";

        UrlFetcher sut = new UrlFetcher(url, pattern);

        String result = sut.Execute();

        assertThat(result, Is.is("go-server-14.4.0-1341-setup.exe"));
    }
}
