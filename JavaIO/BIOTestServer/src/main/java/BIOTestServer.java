import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by 擎风 on 2017/7/6.
 */
public class BIOTestServer {

    public static void main(String [] args){
        try {
            ServerSocket ssck = new ServerSocket(8888);
            Socket sdsck = ssck.accept();
            InputStream instr = sdsck.getInputStream();
            OutputStream outStr = sdsck.getOutputStream();
            byte[] redata = new byte[1024];
            instr.read(redata);
            System.out.print(new String(redata, 0, 1024));
            outStr.write("he额he".getBytes());
            outStr.flush();

        } catch (Exception e){

        } finally {

        }
    }
}
