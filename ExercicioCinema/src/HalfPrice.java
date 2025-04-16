public class HalfPrice extends Cinema{


    public HalfPrice(double amount, String movie, boolean dubbed) {
        super(amount, movie, dubbed);
    }

    @Override
    public double getAmountReal() {
        return amount / 2.0;
    }
}
