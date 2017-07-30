package com.example.jian.jcjiaxiao.utill;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UtilClient {

    public static String getTextFromStream(InputStream is){

        byte[] b = new byte[9999];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while((len = is.read(b)) != -1){
                bos.write(b, 0, len);
            }
            String text = new String(bos.toByteArray());
            bos.close();
            return text;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}