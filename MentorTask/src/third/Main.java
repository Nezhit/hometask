package third;

public class Main {
    public static void main(String[] args) {
        FibonacciCalculator calculator = new FibonacciCalculator();

        ProfilingHandler.profileMethod(calculator, "calculateFibonacci", int.class);
    }
}
