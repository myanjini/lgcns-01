package ex04;

public class MainForCalculator {
    public static void main(String[] args) {
        final long num = 10L;
        
        Calculator imp = new ExeTimeCalculator(new ImpCalculator());
        System.out.println(imp.factorial(num));
        
        Calculator rec = new ExeTimeCalculator(new RecCalculator());
        System.out.println(rec.factorial(num));
    }
}
