package jetbrains.macros.actions;

import com.atlassian.bandana.BandanaManager;
import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.pages.AbstractPage;
import com.atlassian.confluence.pages.actions.PageAware;
import com.atlassian.confluence.setup.bandana.ConfluenceBandanaContext;
import jetbrains.macros.base.MacroWithPersistableSettingsBase;
import jetbrains.macros.base.SettingsCache;

/**
 * Created by egor.malyshev on 19.01.2015.
 */
public class Configuration extends ConfluenceActionSupport implements PageAware {

    private final BandanaManager bm;
    private final ConfluenceBandanaContext context;
    private SettingsCache settings;
    private AbstractPage page;
    private String youtrackhost = "";
    private String youtracklogin = "";
    private String youTrackpassword = "";

    public Configuration(BandanaManager bm) {
        this.bm = bm;
        context = new ConfluenceBandanaContext();
        settings = (SettingsCache) bm.getValue(context, MacroWithPersistableSettingsBase.SETTINGS_KEY);
        if (settings != null) {
            youtrackhost = (String) settings.storage.get("host");
            youtracklogin = (String) settings.storage.get("login");
            youTrackpassword = (String) settings.storage.get("password");
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

    @Override
    public String toString() {
        return "Configuration{" +
                "youtrackhost='" + youtrackhost + '\'' +
                ", youtracklogin='" + youtracklogin + '\'' +
                ", youTrackpassword='" + youTrackpassword + '\'' +
                '}';
    }

    public String execute() {
        System.out.println(this.toString());
        settings.storage.put("host", youtrackhost);
        settings.storage.put("password", youTrackpassword);
        settings.storage.put("login", youtracklogin);
        bm.setValue(context, MacroWithPersistableSettingsBase.SETTINGS_KEY, settings);
        return "success";
    }
}
