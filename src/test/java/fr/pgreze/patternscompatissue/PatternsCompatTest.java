package fr.pgreze.patternscompatissue;

import android.support.v4.util.PatternsCompat;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * See https://issuetracker.google.com/issues/65001273
 */
public class PatternsCompatTest {

    // This test is correct
    @Test
    public void assertLinkingWithTrailingSlashAtEndOfText() throws Exception {
        assertThat(parseWebUrls("http://google.com/"))
                .containsExactly("http://google.com/");
    }

    // This test will fail.
    // Expected: ["http://google.com/"]
    // Found:    ["http://google.com"]
    @Test
    public void assertLinkingWithTrailingSlashNotAtEndOfText() throws Exception {
        assertThat(parseWebUrls("http://google.com/ "))
                .containsExactly("http://google.com/");
    }

    // This test will fail.
    // Expected: ["http://google.com/", "http://google.fr/"]
    // Found:    ["http://google.com", "http://google.fr/"]
    @Test
    public void assertLinkingWithMultipleTrailingSlashLinks() throws Exception {
        assertThat(parseWebUrls("http://google.com/ http://google.fr/"))
                .containsExactly("http://google.com/", "http://google.fr/");
    }

    private List<String> parseWebUrls(String text) {
        List<String> links = new ArrayList<>();
        Matcher matcher = PatternsCompat.AUTOLINK_WEB_URL.matcher(text);
        while (matcher.find()) {
            links.add(matcher.group(0));
        }
        return links;
    }
}
