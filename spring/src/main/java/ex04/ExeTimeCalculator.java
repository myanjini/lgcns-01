package ex04;

public class ExeTimeCalculator implements Calculator {
    private Calculator delegate;
    
    public ExeTimeCalculator(Calculator delegate) {
        this.delegate = delegate;
    }

    @Override
    public long factorial(long num) {
        long start = System.nanoTime();
        long result = delegate.factorial(num);
        long end = System.nanoTime();
        
        System.out.printf("%s.factorial(%d) 실행시간 : %d\n", 
            this.delegate.getClass().getSimpleName(), num, (end - start));
        
        return result;
    }
}
