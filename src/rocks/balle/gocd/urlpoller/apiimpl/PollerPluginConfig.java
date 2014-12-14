package rocks.balle.gocd.urlpoller.apiimpl;

import com.thoughtworks.go.plugin.api.config.Property;
import com.thoughtworks.go.plugin.api.material.packagerepository.PackageConfiguration;
import com.thoughtworks.go.plugin.api.material.packagerepository.PackageMaterialConfiguration;
import com.thoughtworks.go.plugin.api.material.packagerepository.PackageMaterialProperty;
import com.thoughtworks.go.plugin.api.material.packagerepository.RepositoryConfiguration;
import com.thoughtworks.go.plugin.api.response.validation.ValidationError;
import com.thoughtworks.go.plugin.api.response.validation.ValidationResult;
import rocks.balle.gocd.urlpoller.ConfigFactory;
import rocks.balle.gocd.urlpoller.PollerConfig;

import static com.thoughtworks.go.plugin.api.config.Property.DISPLAY_NAME;
import static com.thoughtworks.go.plugin.api.config.Property.DISPLAY_ORDER;

public class PollerPluginConfig implements PackageMaterialConfiguration {

    public static final Property CONFIG_URL =
            new PackageMaterialProperty(PollerConfig.POLL_URL)
                    .with(DISPLAY_NAME, "URL")
                    .with(DISPLAY_ORDER, 0);

    public static final Property PATTERN =
            new PackageMaterialProperty(PollerConfig.POLL_PATTERN)
                    .with(DISPLAY_NAME, "Regex pattern")
                    .with(DISPLAY_ORDER, 1);


    @Override
    public RepositoryConfiguration getRepositoryConfiguration() {
        RepositoryConfiguration cfg = new RepositoryConfiguration();
        cfg.add(CONFIG_URL);
        return cfg;
    }

    @Override
    public PackageConfiguration getPackageConfiguration() {
        PackageConfiguration cfg = new PackageConfiguration();
        cfg.add(PATTERN);
        return cfg;
    }

    @Override
    public ValidationResult isRepositoryConfigurationValid(RepositoryConfiguration repositoryConfiguration) {
        ValidationResult validationResult = new ValidationResult();
        try {
            new ConfigFactory(repositoryConfiguration, null).CreateRepoUrl().checkConnection();
        } catch(Exception exc) {
            validationResult.addError(new ValidationError(exc.getMessage()));
        }

        return validationResult;
    }

    @Override
    public ValidationResult isPackageConfigurationValid(PackageConfiguration packageConfiguration, RepositoryConfiguration repositoryConfiguration) {
        ValidationResult validationResult = new ValidationResult();

        try {
            new ConfigFactory(repositoryConfiguration, packageConfiguration).CreateUrlFetcher().Execute();
        } catch(Exception exc) {
            validationResult.addError(new ValidationError(exc.getMessage()));
        }

        return validationResult;
    }
}
