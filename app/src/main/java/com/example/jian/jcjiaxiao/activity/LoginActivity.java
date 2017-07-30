package com.example.jian.jcjiaxiao.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jian.jcjiaxiao.R;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Runnable mRunnable;
    private EditText et_id;
    private EditText et_pw;
    private Button bt_login;
public static String UserID;
    public static String Id;
    public static String Name;
    public static String Type;
    public static String Number;
    private String ID = "";
    private String PASSWORD = "";
    public static Bitmap bitmap = null;
ConnectivityManager manager;
    String responseString = null;

    void init() {
        et_id = (EditText) findViewById(R.id.et_id);
        et_pw = (EditText) findViewById(R.id.et_pw);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);

    }
    private boolean checkNetworkState() {
        boolean flag = false;
        //得到网络连接信息
        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        if(flag==false){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Looper.prepare();
            Toast.makeText(LoginActivity.this,"请在网络连接状态打开APP！",Toast.LENGTH_SHORT).show();

           finish();
            Looper.loop();
        }

        return flag;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mRunnable = new Runnable() {
            @Override
            public void run() {
                checkNetworkState();
                //登录
                try {
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "正在为您登录请稍后", Toast.LENGTH_SHORT).show();

                    String path = "http://118.254.8.105:9000/servplat/sign.php";
                    //1.创建客户端对象
                    HttpClient hc = new DefaultHttpClient();
                    //2.创建post请求对象
                    HttpPost hp = new HttpPost(path);
                    hp.setHeader("Cookie", "UM_distinctid=9; CNZZDATA1261105269=10; PHPSESSID=11");
                    //封装form表单提交的数据
                    BasicNameValuePair bnvp = new BasicNameValuePair("a", "in");
//                    Log.i("tag", "ID" + ID);
                    BasicNameValuePair bnvp2 = new BasicNameValuePair("login_name", ID);
                    BasicNameValuePair bnvp3 = new BasicNameValuePair("PASSWORD", PASSWORD);
                    List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
                    //把BasicNameValuePair放入集合中
                    parameters.add(bnvp);
                    parameters.add(bnvp3);
                    parameters.add(bnvp2);
                    try {
                        //要提交的数据都已经在集合中了，把集合传给实体对象
                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "gb2312");
//                        entity.setContentEncoding("UTF-8");
                        //设置post请求对象的实体，其实就是把要提交的数据封装至post请求的输出流中
                        hp.setEntity(entity);
                        //3.使用客户端发送post请求
                        HttpResponse hr = hc.execute(hp);
                        if (hr.getStatusLine().getStatusCode() == 200) {
                            Header[] headers = hr.getHeaders("Content-Encoding");
                            boolean isGzip = false;


/////////////////////////////////////////////////////////////////////////////
                            path = "http://118.254.8.105:9000/servplat/mycenter.php";
                            //使用httpClient框架做get方式提交
                            //1.创建HttpClient对象
                            hc = new DefaultHttpClient();
                            //2.创建httpGet对象，构造方法的参数就是网址
                            HttpGet hg = new HttpGet(path);
                            hg.setHeader("Referer", "http://118.254.8.105:9000/servplat/sign.php");
                            hg.setHeader("Cookie", "UM_distinctid=9; CNZZDATA1261105269=10; PHPSESSID=11");
                            hr = hc.execute(hg);
                            //拿到响应头中的状态行
                            StatusLine sl = hr.getStatusLine();

                            if (sl.getStatusCode() == 200) {
                                HttpEntity he = hr.getEntity();

                                headers = hr.getHeaders("Content-Encoding");
                                isGzip = false;
                                for (Header h : headers) {
                                    if (h.getValue().equals("gzip")) {
                                        //返回头中含有gzip
                                        isGzip = true;
                                    }
                                }
                                responseString = null;
                                if (isGzip) {
                                    GZIPInputStream in = new GZIPInputStream(he.getContent());
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                    String line = "";
                                    while ((line = reader.readLine()) != null) {
                                        responseString += line;
                                    }
                                } else {
                                    responseString = EntityUtils.toString(hr.getEntity());
                                }


                            }
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {

                    //从一个URL加载一个Document对象。
                    Document doc = Jsoup.parse(responseString);
                    //选择所在节点
                    Elements elements = doc.select("li.sign");
                    //打印
                    Elements elements2 = doc.select("img");

                    String url = "http://118.254.8.105:9000" + elements2.get(1).select("img").attr("src");
//                    Log.i("Tag",elements.get(0).text());
                    bitmap = getBitmapFromUrl(url);


                    Name = elements.get(1).text();
                    Id = elements.get(0).text();
                    Number = elements.get(2).text();
                    Type = elements.get(3).text();
                    //转换为userID
                    String regEx="[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(Type);
                    UserID=m.replaceAll("").trim();
//                 Log.i("tag", m.replaceAll("").trim());


                } catch (Exception e) {
                    Log.i("mytag", e.toString());
                }
                if (Id.equals("学员账号：") == false) { //验证为正确

                    //存入文件 下次登录直接进入。
                    SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("status", "1"); //1为登录
                    editor.putString("PASSWORD", PASSWORD);
                    editor.putString("ID", ID);
                    editor.commit();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    //在线程中使用toast需要Looper
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "您的账号或者密码错误", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
             };
        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);


        //进行自动登录验证，如果登录过则自动登录
        if (sp.getString("status", "").equals("1")) {
            setContentView(R.layout.welcome); //设置欢迎界面
            PASSWORD = sp.getString("PASSWORD", "");
            ID = sp.getString("ID", "");

            Thread thread = new Thread(mRunnable);
            thread.start();
        } else {
            setContentView(R.layout.activity_login);
            init();
        }


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_login) {

            ID = String.valueOf(et_id.getText());
            PASSWORD = String.valueOf(et_pw.getText());
            Thread thread = new Thread(mRunnable);
            thread.start();
        }
    }

    public static Bitmap getBitmapFromUrl(String imgUrl) {
        URL url;
        Bitmap bitmap = null;
        try {
            url = new URL(imgUrl);
            InputStream is = url.openConnection().getInputStream();

            BufferedInputStream bis = new BufferedInputStream(is);
// bitmap = BitmapFactory.decodeStream(bis); 注释1
            byte[] b = getBytes(is);
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            bis.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static byte[] getBytes(InputStream is) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = is.read(b, 0, 1024)) != -1) {
            baos.write(b, 0, len);
            baos.flush();
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
}
