package com.example.jian.jcjiaxiao.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jian.jcjiaxiao.R;
import com.example.jian.jcjiaxiao.utill.UtilGet;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


import static com.example.jian.jcjiaxiao.activity.MainActivity.commenthref;

public class CommentActivity extends AppCompatActivity {
String  pinjiagrade="90";
    Button mButton;
    TextView tc_comment;
    final String[] nItems = {"非常满意", "满意", "不满意"};

    TextView tv_tcname;
    TextView tv_usname;
    TextView tv_data;
    TextView tv_coursetype;
    String tcname="田丽";
    String usname="黄剑";
    String data="2017-5-21";
    String coursetype="科目二";
Handler mHandler=new Handler(){
    @Override
    public void handleMessage(Message msg) {

        if(msg.arg1==1){

            tv_coursetype.setText("课程:"+coursetype);
      tv_usname.setText("学员："+usname);
            tv_tcname.setText("教练:"+tcname);
            tv_data.setText("时间："+data);

        }


        super.handleMessage(msg);
    }
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        tc_comment= (TextView) findViewById(R.id.tv_comment);
  tv_coursetype=(TextView)findViewById(R.id.tv_typecourese);
        tv_data= (TextView) findViewById(R.id.tv_data);
        tv_tcname= (TextView) findViewById(R.id.tv_tcname);
        tv_usname= (TextView) findViewById(R.id.tv_usname);
        new Thread(new Runnable() {
            @Override
            public void run() {


                UtilGet utilGet = new UtilGet();
                String url = "http://118.254.8.105:9000/servplat/"+commenthref;
                Log.i("Tag",url);
                String responeString3 = utilGet.GetResponeString(url);
                Document doc = Jsoup.parse(responeString3);
                Elements elements = doc.select("td.TableData");
                tcname=elements.get(1).text().toString();
                usname=elements.get(3).text().toString();
                coursetype=elements.get(5).text().toString();
                data=elements.get(7).text().toString();

                Message message= new Message();
                message.arg1=1;
                mHandler.sendMessage(message);



            }
        }).start();
        tc_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);// 自定义对话框
                builder.setSingleChoiceItems(nItems, 0, new DialogInterface.OnClickListener() {// 2默认的选中

                    @Override
                    public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                        //showToast(which+"");
                        tc_comment.setText("评价教练："+nItems[which]);
                        if(which==0){
                            pinjiagrade="90";
                        }
                        if(which==1){
                            pinjiagrade="60";
                        }
                        if(which==2){
                            pinjiagrade="40";
                        }
                        dialog.dismiss();//随便点击一个item消失对话框，不用点击确认取消
                    }
                });
                builder.show();// 让弹出框显示


            }
        });
        mButton= (Button) findViewById(R.id.bt_sure);




        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               new Thread(new Runnable() {
                    @Override
                    public void run() {

                        UtilGet utilGet = new UtilGet();
                        String url = "http://118.254.8.105:9000/servplat/"+commenthref;
                        String responeString3 = utilGet.GetResponeString(url);
                        Document doc = Jsoup.parse(responeString3);
                        Elements elements = doc.select("input");
                        String path = "http://118.254.8.105:9000/servplat/updateteacherpinjia.php";
                        //1.创建客户端对象
                        HttpClient hc = new DefaultHttpClient();
                        //2.创建post请求对象
                        HttpPost hp = new HttpPost(path);
                        hp.setHeader("Referer", url);
                        hp.setHeader("Cookie", "UM_distinctid=9; CNZZDATA1261105269=10; PHPSESSID=11");
                        //封装form表单提交的数据
                        BasicNameValuePair bnvp = new BasicNameValuePair("TEACHER_PINJIA",pinjiagrade);
                        BasicNameValuePair bnvp1 = new BasicNameValuePair("DEPT_ID",    elements.get(0).attr("value"));
                        BasicNameValuePair bnvp2 = new BasicNameValuePair("TEACHER_ID",    elements.get(1).attr("value"));
                        BasicNameValuePair bnvp3 = new BasicNameValuePair("CDATE",    elements.get(2).attr("value")); //教练分组
                        BasicNameValuePair bnvp4 = new BasicNameValuePair("STUDENT_ID",    elements.get(3).attr("value"));
                        BasicNameValuePair bnvp5 = new BasicNameValuePair("searchtype",    elements.get(4).attr("value"));
                        BasicNameValuePair bnvp6 = new BasicNameValuePair("go_back",    elements.get(5).attr("value"));

                        BasicNameValuePair bnvp7 = new BasicNameValuePair("button", "确定");


                        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
                        //把BasicNameValuePair放入集合中
                        parameters.add(bnvp);
                        parameters.add(bnvp1);
                        parameters.add(bnvp3);
                        parameters.add(bnvp2);
                        parameters.add(bnvp4);
                        parameters.add(bnvp5);
                        parameters.add(bnvp6);
                        parameters.add(bnvp7);

                        try {
                            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "gb2312");
//                        entity.setContentEncoding("UTF-8");
                            //设置post请求对象的实体，其实就是把要提交的数据封装至post请求的输出流中
                            hp.setEntity(entity);
                            //3.使用客户端发送post请求
                            HttpResponse hr = hc.execute(hp);
                            Log.i("Tag", hr.getStatusLine().getStatusCode() + "");

                            if (hr.getStatusLine().getStatusCode() == 200) {
                                Looper.prepare();
                                Toast.makeText(CommentActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                Looper.prepare();
                                Toast.makeText(CommentActivity.this, "评价失败！可选择重启APP试试", Toast.LENGTH_SHORT).show();
                            }
                          startActivity(new Intent().setClass(CommentActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                            Looper.loop();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
                ).start();

            }
        });


    }
}
