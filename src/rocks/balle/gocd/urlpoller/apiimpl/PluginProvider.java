package rocks.balle.gocd.urlpoller.apiimpl;

import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.material.packagerepository.PackageMaterialConfiguration;
import com.thoughtworks.go.plugin.api.material.packagerepository.PackageMaterialPoller;
import com.thoughtworks.go.plugin.api.material.packagerepository.PackageMaterialProvider;

/**
 * Created by vagrant on 12/12/14.
 */

@Extension
public class PluginProvider implements PackageMaterialProvider  {
    @Override
    public PackageMaterialConfiguration getConfig() {
        return new PollerPluginConfig();
    }

    @Override
    public PackageMaterialPoller getPoller() {
        return new UrlPoller();
    }

}
