package function;

public class Taylor {
    public static final double eps = 1E-9;
    public static double arcsin(double x) {
        double curr = x;
        double result = 0.0;
        int n = 1;
        if (x == 1.0) {
            return Math.PI/2;
        } else if (x == -1.0) {
            return -Math.PI/2;
        } else if (Math.abs(x) < 1) {
            while (Math.abs(curr) >= eps/10) {
                result += curr;
                curr = curr * x * x * (2 * n - 1) * (2 * n - 1) / ((2 * n) * (2 * n + 1));
                n++;
            }
        } else {
            throw new IllegalArgumentException();
        }
        return result;
    }
}
