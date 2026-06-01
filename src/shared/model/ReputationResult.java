package shared.model;

public class ReputationResult {
    private String indicator;
    private int score;
    private String verdict;

    public ReputationResult(String indicator, int score, String verdict) {
        this.indicator = indicator;
        this.score = score;
        this.verdict = verdict;
    }
    public String getIndicator() { return indicator; }
    public int getScore() { return score; }
    public String getVerdict() { return verdict; }
    public boolean isMalicious() { return score >= 70 || "MALICIOUS".equalsIgnoreCase(verdict); }
    @Override public String toString() { return "ReputationResult{" + indicator + ", score=" + score + ", verdict='" + verdict + "'}"; }
}
