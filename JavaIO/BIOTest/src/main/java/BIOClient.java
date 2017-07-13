import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 擎风 on 2017/7/6.
 */
public class BIOClient extends  Thread{
    CountDownLatch latch;
    int index;

    public BIOClient(CountDownLatch latch, int index){
        this.latch = latch;
        this.index = index;
    }

    @Override
    public void run(){
        OutputStream outStr = null;
        InputStream instr = null;
        Socket sck = null;
        try {
            sck = new Socket("localhost", 8888);
            outStr = sck.getOutputStream();
            instr = sck.getInputStream();
            this.latch.await();
            outStr.write(("we测we试we" + this.index).getBytes());
            outStr.flush();
            byte[] contextBytes = new byte[1024];
            instr.read(contextBytes);
            System.out.print(new String(contextBytes, 0, 1024));
            outStr.write("回发响应信息！".getBytes());
            outStr.flush();
        } catch (Exception e){

        } finally {
            try {
                if(outStr != null)
                    outStr.close();
                if(instr != null)
                    instr.close();
                if (sck != null)
                    sck.close();

            } catch (Exception e) {
                System.out.print("finally 抛异常啦啦啦啦");
                e.printStackTrace();
            }
        }
    }
}
