public class Cinema {

    protected double amount;
    protected String movie;
    protected boolean dubbed;

    public Cinema(double amount, String movie, boolean dubbed) {
        this.amount = amount;
        this.movie = movie;
        this.dubbed = dubbed;
    }

    public double getAmountReal() {
        return amount;
    }

    public String getNameMovie() {
        return movie;
    }

    public boolean isDubbed() {
        return dubbed;
    }

    public String getTypeAudio() {
        return dubbed ? "Dublado" : "Legendado";
    }
}
