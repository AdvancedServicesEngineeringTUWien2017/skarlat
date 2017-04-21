package at.ac.tuwien.infosys.ui;

import at.ac.tuwien.infosys.MainNavController;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lenaskarlat on 4/10/17.
 */
@Configuration
public class Navigation {
    public List<NavEntry> getNavEntries(){
        return Arrays.asList(
                new NavEntry (MainNavController.INDEX_URL,"index", "Start"),
                new NavEntry (MainNavController.RECEIVE_URL,"receive", "Received data"));
    }

    private static class NavEntry {
        private final String url, viewName, userFriendlyName;

        NavEntry(String url, String viewName, String userFriendlyName) {
            this.url = url;
            this.viewName = viewName;
            this.userFriendlyName = userFriendlyName;
        }

        public String getUrl() {
            return url;
        }

        public String getViewName() {
            return viewName;
        }

        public String getUserFriendlyName() {
            return userFriendlyName;
        }
    }
}