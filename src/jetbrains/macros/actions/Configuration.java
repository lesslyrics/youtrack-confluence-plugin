package jetbrains.macros.actions;

import com.atlassian.bandana.BandanaManager;
import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.pages.AbstractPage;
import com.atlassian.confluence.pages.actions.PageAware;
import jetbrains.macros.base.SettingsCache;
import jetbrains.macros.util.Service;

/**
 * Created by egor.malyshev on 19.01.2015.
 */
public class Configuration extends ConfluenceActionSupport implements PageAware {

    private final BandanaManager bm;
    private SettingsCache settings;
    private AbstractPage page;
    private String youtrackhost = "";
    private String youtracklogin = "";
    private String youTrackpassword = "";

    public Configuration(BandanaManager bm) {
        this.bm = bm;
        init();
    }

    private void init() {
        settings = Service.getSettingsCache(bm);
        if (settings != null) {
            youtrackhost = Service.getStoredHost(settings);
            youtracklogin = Service.getStoredLogin(settings);
            youTrackpassword = Service.getStoredPassword(settings);
        } else {
            settings = new SettingsCache();
        }
    }

    @Override
    public void validate() {
    }

    @Override
    public AbstractPage getPage() {
        return page;
    }

    public void setPage(AbstractPage page) {
        this.page = page;
    }

    @Override
    public boolean isPageRequired() {
        return false;
    }

    public String getYoutrackhost() {
        return youtrackhost;
    }

    public void setYoutrackhost(String youtrackhost) {
        this.youtrackhost = youtrackhost;
    }

    public String getYoutracklogin() {
        return youtracklogin;
    }

    public void setYoutracklogin(String youtracklogin) {
        this.youtracklogin = youtracklogin;
    }

    public String getYoutrackpassword() {
        return youTrackpassword;
    }

    public void setYoutrackpassword(String youTrackpassword) {
        this.youTrackpassword = youTrackpassword;
    }

    @Override
    public boolean isLatestVersionRequired() {
        return false;
    }

    @Override
    public boolean isViewPermissionRequired() {
        return false;
    }

    public String execute() {
        Service.storeLogin(youtracklogin, settings);
        Service.storeHost(youtrackhost, settings);
        Service.storePassword(youTrackpassword, settings);
        Service.storeSettinsCache(settings, bm, Service.CONTEXT);
        return "success";
    }
}