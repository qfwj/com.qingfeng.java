/**
 * Copyright(c) 2013-2017 by Puhuifinance Inc.
 * All Rights Reserved
 */

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @ClassName: TestMain
 * @Description: ${DESCRIPTION}
 * @author: zhbo
 * @date: 2017/6/18 21:42
 * @Copyright: 2017 . All rights reserved.
 */
public class TestMain {
    public void sqlTest() throws Exception{
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/agile-portal";
        String username = "root";
        String password = "hanlei124";
        Connection conn = null;
        Class.forName(driver); //classLoader,加载对应驱动
        conn = (Connection) DriverManager.getConnection(url, username, password);
        String sql = "     INSERT INTO ap_sla_info (team_name, data_time, data_value, is_actual)  " +
                " VALUES ('CIF-实时', '20170eew44', 66.0, true);" +
                "INSERT INTO ap_sla_info (team_name, data_time, data_value, is_actual)  " +
                " VALUES ('CIF-实时', '20170w44', 66.0, true);";
        PreparedStatement pstmt;
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public static void main(String args[]) throws Exception{
        File fileTemp = new File("C://Users//puhui//Desktop//微信截图_20170627145831.png");
        InputStream ss = new FileInputStream(fileTemp);

        byte[] byteTest = new byte[ss.available()];
        ss.read(byteTest);
        BASE64Encoder encoder = new BASE64Encoder();
        String ssh = encoder.encode(byteTest);//返回Base64编码过的字节数组字符串
        ss.close();
        BASE64Decoder decoder = new BASE64Decoder();

            //Base64解码
            byte[] b = decoder.decodeBuffer(ssh);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "C://Users//puhui//Desktop//222.jpg";//新生成的图片
            OutputStream out = new FileOutputStream("222.jpg");
            out.write(b);
            out.flush();
            out.close();

       /* while(ss.read(byteTest) != -1){

        }*/
    }
}
