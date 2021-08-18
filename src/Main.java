import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Algorithm algorithm = new Algorithm();

        while(true){
            algorithm.app();
            TimeUnit.MINUTES.sleep(10);
        }
    }


}
