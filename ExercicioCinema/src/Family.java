public class Family extends Cinema{

    private int numberPeoples;

    public Family(double amount, String movie, boolean dubbed, int numberPeoples) {
        super(amount, movie, dubbed);
        this.numberPeoples = numberPeoples;
    }

    @Override
    public double getAmountReal() {
        double total = amount * numberPeoples;
        if(numberPeoples > 3) {
            total *= 0.95;
        }
        return total;
    }

    public int getNumberPeoples() {
        return numberPeoples;
    }
}
