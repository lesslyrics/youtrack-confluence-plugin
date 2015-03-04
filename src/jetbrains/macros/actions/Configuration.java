package jetbrains.macros.actions;

import com.atlassian.bandana.BandanaManager;
import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.pages.AbstractPage;
import com.atlassian.confluence.pages.actions.PageAware;
import jetbrains.macros.util.SettingsManager;

/**
 * Created by egor.malyshev on 19.01.2015.
 */
public class Configuration extends ConfluenceActionSupport implements PageAware {

    private final BandanaManager bm;
    private AbstractPage page;

    public Configuration(BandanaManager bm) {
        this.bm = bm;
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
        return SettingsManager.getInstance(bm).getStoredHost();
    }

    public void setYoutrackhost(String youtrackhost) {
        SettingsManager.getInstance(bm).storeHost(youtrackhost);
    }

    public String getYoutracklogin() {
        return SettingsManager.getInstance(bm).getStoredLogin();
    }

    public void setYoutracklogin(String youtracklogin) {
        SettingsManager.getInstance(bm).storeLogin(youtracklogin);
    }

    public String getYoutrackpassword() {
        return SettingsManager.getInstance(bm).getStoredPassword();
    }

    public void setYoutrackpassword(String youTrackpassword) {
        SettingsManager.getInstance(bm).storePassword(youTrackpassword);
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
        return "success";
    }
}