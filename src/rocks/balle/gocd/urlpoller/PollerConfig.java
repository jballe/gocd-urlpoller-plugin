package rocks.balle.gocd.urlpoller;

/**
 * Created by vagrant on 12/12/14.
 */
public class PollerConfig {
    public static final String POLL_URL = "URL";
    public static final String POLL_PATTERN = "PATTERN";
    public static final String POLL_HEADER = "HEADER";


    private String _url;
    private String _pattern;

    public PollerConfig(String url, String pattern) {
        _url = url;
        _pattern = pattern;
    }

    public String get_pattern() {
        return _pattern;
    }

    public String get_url() {
        return _url;
    }
}
