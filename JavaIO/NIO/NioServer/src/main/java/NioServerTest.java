import sun.nio.ch.WindowsSelectorProvider;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Created by qf on 2017/7/15.
 */
public class NioServerTest {
    public  static void  main(String[] args) {
        try {
            ServerSocketChannel chanel = ServerSocketChannel.open();//通过provider实现
            chanel.configureBlocking(false);
            ServerSocket socket = chanel.socket();
            socket.bind(new InetSocketAddress(8888));
            chanel.accept();
            Selector selector = Selector.open();
            chanel.register(selector, SelectionKey.OP_ACCEPT);
            while(true){
                //selector.select();//indefinite无限等待
                if(selector.select(100) == 0) {
                    //================================================
                    //      这里视业务情况，可以做一些然并卵的事情
                    //  超时处理
                    //================================================
                    continue;
                }
               Iterator<SelectionKey> iterable =  selector.selectedKeys().iterator();
                while(iterable.hasNext()){
                    SelectionKey temp = iterable.next();//获取已经ready的chanel的key
                    iterable.remove();//一定要清除掉，否则下次取监听结果会仍然存在这个
                    SelectableChannel selectableChannel = temp.channel();
                    SocketChannel dataChanel = null;
                    /* 进行四种key判断*/
                    if(temp.isValid() && temp.isAcceptable()){
                        dataChanel = ((ServerSocketChannel)selectableChannel).accept();
                        //进行注册
                        dataChanel.register(selector, SelectionKey.OP_READ);
                    }
                    if(temp.isValid() && temp.isConnectable()){

                    }
                    if(temp.isValid() && temp.isReadable()){
                        ByteBuffer buf = (ByteBuffer)temp.attachment();//读取数据
                        ((SocketChannel)selectableChannel).read(buf);
                        buf.flip();
                        //编解码
                        byte[] messageBytes = buf.array();
                        String messageEncode = new String(messageBytes , "UTF-8");
                        String message = URLDecoder.decode(messageEncode, "UTF-8");
                        // String messageEncode = new String(messageBytes , 0 , realLen , "UTF-8");
                        //清空已经读取的缓存，并从新切换为写状态(这里要注意clear()和capacity()两个方法的区别)
                        buf.clear();
                        //Clears this buffer.  The position is set to zero, the limit is set to the capacity, and the mark is discarded.
                        //回发数据，并关闭channel
                        ByteBuffer sendBuffer = ByteBuffer.wrap(URLEncoder.encode("回发处理结果", "UTF-8").getBytes());
                        dataChanel.write(sendBuffer);
                        dataChanel.close();
                    }
                    if(temp.isValid() && temp.isWritable()){

                    }
                }

            }
        } catch (SocketTimeoutException e) {
            System.out.println("超时了");
        } catch (IOException e){

        }

    }
}
