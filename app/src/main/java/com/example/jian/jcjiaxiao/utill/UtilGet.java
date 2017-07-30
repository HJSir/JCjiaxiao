package com.example.jian.jcjiaxiao.utill;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * Created by jian on 2017/4/26.
 */

public class UtilGet {
    String responseString;
int  code;
    public int getResultCode(){
        return code;
    }

   public String  GetResponeString(String url){



        //使用httpClient框架做get方式提交
        //1.创建HttpClient对象
        HttpClient hc = new DefaultHttpClient();
//                    hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        //2.创建httpGet对象，构造方法的参数就是网址
        HttpGet hg = new HttpGet(url);
//        hg.setHeader("Referer", "Referer: http://118.254.8.105:9000/servplat/smartdodate.php?DEPT_ID=3&STUDENT_ID=021677&TEACHER_ID=592853&CDATE=2017-04-27&KEMUNO=1&VM_NUM=%E6%B9%98G-J175%E5%AD%A6");
        hg.setHeader("Cookie", "UM_distinctid=9; CNZZDATA1261105269=10; PHPSESSID=11");
        HttpResponse hr = null;
        try {
            hr = hc.execute(hg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //拿到响应头中的状态行
        StatusLine sl = hr.getStatusLine();
       code=sl.getStatusCode();
        if(sl.getStatusCode() == 200){
            Header[] headers = hr.getHeaders("Content-Encoding");
            HttpEntity he = hr.getEntity();
            boolean isGzip = false;
            headers = hr.getHeaders("Content-Encoding");

            for(Header h:headers){
                if(h.getValue().equals("gzip")){
                    //返回头中含有gzip
                    isGzip = true;
                }
            }
            responseString= null;
            try {
                if(isGzip){
                    GZIPInputStream in= null;
                    in = new GZIPInputStream(he.getContent());
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    String line="";
                    while((line=reader.readLine())!=null){
                        responseString+=line;
                    }
                }else{

                    responseString = EntityUtils.toString(he);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseString;
    }
}
