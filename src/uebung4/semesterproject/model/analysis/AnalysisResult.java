package uebung4.semesterproject.model.analysis;

import java.util.ArrayList;
import java.util.List;

public class AnalysisResult {

    private final int quality;
    private final String rating;
    private final List<String> details;
    private final List<String> hints;

    public AnalysisResult(int quality, String rating, List<String> details, List<String> hints) {
        this.quality = quality;
        this.rating = rating;
        this.details = details;
        this.hints = hints;
    }

    public int getQuality() {
        return quality;
    }

    public String getRating() {
        return rating;
    }

    public List<String> getDetails() {
        return new ArrayList<>(details);
    }

    public boolean hasDetails() {
        return !details.isEmpty();
    }

    public List<String> getHints() {
        return new ArrayList<>(hints);
    }

    public boolean hasHints() {
        return !hints.isEmpty();
    }
}
