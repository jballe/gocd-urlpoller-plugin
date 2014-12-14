package rocks.balle.gocd.urlpoller;

import com.thoughtworks.go.plugin.api.material.packagerepository.PackageConfiguration;
import com.thoughtworks.go.plugin.api.material.packagerepository.RepositoryConfiguration;
import com.tw.go.plugin.util.HttpRepoURL;
import com.tw.go.plugin.util.RepoUrl;

/**
 * Created by vagrant on 12/12/14.
 */
public class ConfigFactory {

    private RepositoryConfiguration _repositoryConfiguration;
    private PackageConfiguration _packageConfiguration;

    public ConfigFactory(RepositoryConfiguration repositoryConfiguration, PackageConfiguration packageConfiguration) {
        _repositoryConfiguration = repositoryConfiguration;
        _packageConfiguration = packageConfiguration;
    }

    public UrlFetcher CreateUrlFetcher() {
        String url = _packageConfiguration.get(PollerConfig.POLL_URL).getValue();
        String pattern = _packageConfiguration.get(PollerConfig.POLL_PATTERN).getValue();

        return  new UrlFetcher(url, pattern);
    }

    public RepoUrl CreateRepoUrl() {
        String url = _packageConfiguration.get(PollerConfig.POLL_URL).getValue();
        return new HttpRepoURL(url, null, null);
    }

}
