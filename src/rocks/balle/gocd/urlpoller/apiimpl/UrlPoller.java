package rocks.balle.gocd.urlpoller.apiimpl;

import com.thoughtworks.go.plugin.api.material.packagerepository.PackageConfiguration;
import com.thoughtworks.go.plugin.api.material.packagerepository.PackageMaterialPoller;
import com.thoughtworks.go.plugin.api.material.packagerepository.PackageRevision;
import com.thoughtworks.go.plugin.api.material.packagerepository.RepositoryConfiguration;
import com.thoughtworks.go.plugin.api.response.Result;
import com.tw.go.plugin.util.RepoUrl;
import org.apache.commons.lang3.time.DateUtils;
import rocks.balle.gocd.urlpoller.ConfigFactory;

import java.util.Date;

/**
 * Created by vagrant on 12/11/14.
 */
public class UrlPoller implements PackageMaterialPoller {

    @Override
    public PackageRevision getLatestRevision(PackageConfiguration packageConfiguration, RepositoryConfiguration repositoryConfiguration) {
        String version = new ConfigFactory(repositoryConfiguration, packageConfiguration)
                .CreateUrlFetcher()
                .Execute();

        return new PackageRevision(version, new Date(0), null);
    }

    @Override
    public PackageRevision latestModificationSince(PackageConfiguration packageConfiguration, RepositoryConfiguration repositoryConfiguration, PackageRevision packageRevision) {
        PackageRevision newRevision = getLatestRevision(packageConfiguration, repositoryConfiguration);

        if(packageRevision==null)
            return newRevision;

        if(newRevision.getRevision().equals(packageRevision.getRevision()))
            return null;

        return newRevision;
    }

    @Override
    public Result checkConnectionToRepository(RepositoryConfiguration repositoryConfiguration) {
        return new Result();
    }

    @Override
    public Result checkConnectionToPackage(PackageConfiguration packageConfiguration, RepositoryConfiguration repositoryConfiguration) {
        RepoUrl url = new ConfigFactory(repositoryConfiguration, packageConfiguration).CreateRepoUrl();

        Result response = new Result();
        try {
            url.checkConnection();
        } catch(Exception exc) {
            response.withErrorMessages(exc.getMessage());
        }

        return response;
    }
}
