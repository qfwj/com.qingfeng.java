import java.util.concurrent.CountDownLatch;

/**
 * Created by 擎风 on 2017/7/6.
 */
public class TestMain {
    public static void main (String [] args)  throws Exception {
        int count = 20;//20个模拟客户端
        CountDownLatch latch = new CountDownLatch(count);
        int i = 0;
        while(true ){
            BIOClient client = new BIOClient(latch, i);
            client.start();
            latch.countDown();
            ++ i;
            if(i == count)
                break;
        }

        synchronized (BIOClient.class) {
            BIOClient.class.wait();
        }
        System.out.print("Hello World!");
    }
}
