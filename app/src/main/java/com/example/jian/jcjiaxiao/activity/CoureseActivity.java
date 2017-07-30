package com.example.jian.jcjiaxiao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jian.jcjiaxiao.R;
import com.example.jian.jcjiaxiao.utill.UtilGet;
import com.example.jian.jcjiaxiao.adapter.courseAdapter;
import com.example.jian.jcjiaxiao.entity.coursebean;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.jian.jcjiaxiao.activity.MainActivity.DAY;
import static com.example.jian.jcjiaxiao.activity.MainActivity.MONTH;
import static com.example.jian.jcjiaxiao.activity.MainActivity.YEAR;
import static com.example.jian.jcjiaxiao.activity.YuyueActivity.Qurl;

public class CoureseActivity extends Activity {
    ListView listView;
    private Context mContext;
    int sysDay;
    int sysMonth;
    int sysHour;
    private NotificationManager myManager = null;
    private Notification myNotification;
    private static final int NOTIFICATION_ID_1 = 1;
public static int doit;
    String responseString;
    TextView mTextView;
    String[] tem = new String[11];
    ArrayList<coursebean> mCoursebeanArrayList;
    Handler mHandler = new Handler() {


        public void handleMessage(final Message msg) {
            if (msg.arg2 == 1) {


//            **********************************************
                AlertDialog.Builder builder = new AlertDialog.Builder(CoureseActivity.this);
                //                         builder.setIcon(R.drawable.shunmulog);
                builder.setTitle("请确认预约");
                LayoutInflater inflater = LayoutInflater.from(CoureseActivity.this);
                View view1 = inflater.inflate(R.layout.alertdialog_sure, null);
                mTextView = (TextView) view1.findViewById(R.id.al_time);
                mTextView.setText("时间为：" + mCoursebeanArrayList.get(msg.arg1).getC_starttime() + "---" + mCoursebeanArrayList.get(msg.arg1).getC_endtime());
                builder.setView(view1);

                //  设置确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        // TODO Auto-generated method stub

                        String path = "http://118.254.8.105:9000/servplat/adddate.php";
                        //1.创建客户端对象
                        HttpClient hc = new DefaultHttpClient();
                        //2.创建post请求对象
                        HttpPost hp = new HttpPost(path);
                        hp.setHeader("Referer", "http://118.254.8.105:9000/servplat/newdate.php?DEPT_ID=" + tem[0] + "&TEACHER_ID=" + tem[1] + "&STUDENT_ID=" + tem[2] + "&CDATE=" + tem[3] + "&START_TIME=" + tem[4] + "&END_TIME=" + tem[5] + "&K2PRICE=" + tem[6] + "&K3PRICE=" + tem[7] + "&KEMUNO=" + tem[8] + "&CANDATE=" + tem[9] + "");
                        hp.setHeader("Cookie", "UM_distinctid=9; CNZZDATA1261105269=10; PHPSESSID=11");
                        //封装form表单提交的数据
                        BasicNameValuePair bnvp = new BasicNameValuePair("DEPT_ID", tem[0]);
                        BasicNameValuePair bnvp1 = new BasicNameValuePair("TEACHER_ID", tem[1]);
                        BasicNameValuePair bnvp2 = new BasicNameValuePair("STUDENT_ID", tem[2]);
                        BasicNameValuePair bnvp3 = new BasicNameValuePair("DRV_CAR_TYPE", tem[10]); //教练分组
                        BasicNameValuePair bnvp4 = new BasicNameValuePair("CDATE", tem[3]);
                        BasicNameValuePair bnvp5 = new BasicNameValuePair("START_TIME", tem[4]);
                        BasicNameValuePair bnvp6 = new BasicNameValuePair("END_TIME", tem[5]);
                        BasicNameValuePair bnvp7 = new BasicNameValuePair("K2PRICE", tem[6]);
                        BasicNameValuePair bnvp8 = new BasicNameValuePair("K3PRICE", tem[7]);
                        BasicNameValuePair bnvp9 = new BasicNameValuePair("KEMUNO", tem[8]);
                        BasicNameValuePair bnvp10 = new BasicNameValuePair("CANDATE", tem[9]);
                        BasicNameValuePair bnvp11 = new BasicNameValuePair("button", "新建预约");
                        BasicNameValuePair bnvp12 = new BasicNameValuePair("PASSWORD", "");

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
                        parameters.add(bnvp8);
                        parameters.add(bnvp9);
                        parameters.add(bnvp10);
                        parameters.add(bnvp11);
                        parameters.add(bnvp12);
                        try {
                            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "gb2312");
//                        entity.setContentEncoding("UTF-8");
                            //设置post请求对象的实体，其实就是把要提交的数据封装至post请求的输出流中
                            hp.setEntity(entity);
                            //3.使用客户端发送post请求
                            HttpResponse hr = hc.execute(hp);
                            Log.i("Tag", hr.getStatusLine().getStatusCode() + "");
                            if (hr.getStatusLine().getStatusCode() == 200) {
                                Toast.makeText(CoureseActivity.this, "预约成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CoureseActivity.this, "预约失败！可选择重启APP试试", Toast.LENGTH_SHORT).show();
                            }
                            refresh();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                //  设置取消按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub

                    }
                });

                builder.create().show();


                super.handleMessage(msg);
            } else if (msg.arg2 == 2) {
                ///撤销操作
                final String url = "http://118.254.8.105:9000/servplat/" + mCoursebeanArrayList.get(msg.arg1).getHref();
                AlertDialog.Builder builder = new AlertDialog.Builder(CoureseActivity.this);
                //                         builder.setIcon(R.drawable.shunmulog);
                builder.setTitle("请确认撤销");

//设置确定
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub


                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                UtilGet utilGet = new UtilGet();
                                Log.i("s", "" + url);
                                utilGet.GetResponeString(url);

                                if (utilGet.getResultCode() == 200) {
                                    //撤销成功
                                    Looper.prepare();
                                    Toast.makeText(CoureseActivity.this, "撤销成功", Toast.LENGTH_SHORT);
                                    Log.i("c", "撤销成功");
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(CoureseActivity.this, "撤销失败！请连接网络后重新尝试", Toast.LENGTH_SHORT);
                                    Log.i("c", "撤销失败");
                                }
refresh();

                                Looper.loop();

                            }
                        }).start();

                    }
                });
                //  设置取消按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                    }
                });
                builder.create().show();
                super.handleMessage(msg);
            } else if (msg.arg2 == 3) {

//抢课
                final int num = msg.arg1;
//            **********************************************
                AlertDialog.Builder builder = new AlertDialog.Builder(CoureseActivity.this);
                //                         builder.setIcon(R.drawable.shunmulog);
                builder.setTitle("请确认抢课时间");
                LayoutInflater inflater = LayoutInflater.from(CoureseActivity.this);
                View view1 = inflater.inflate(R.layout.alertdialog_qiangke, null);
                mTextView = (TextView) view1.findViewById(R.id.al2_time);
                mTextView.setText("时间为：" + mCoursebeanArrayList.get(msg.arg1).getC_starttime() + "---" + mCoursebeanArrayList.get(msg.arg1).getC_endtime());
                builder.setView(view1);

                //  设置确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, final int arg1) {

                        // TODO Auto-generated method stub

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent().setClass(CoureseActivity.this,CancelActivity.class));
                                //1.从系统服务中获得通知管理器
                                Notification.Builder myBuilder = new Notification.Builder(CoureseActivity.this);
                                myManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                PendingIntent pi = PendingIntent.getActivity(
                                        CoureseActivity.this,
                                        100,
                                        new Intent(CoureseActivity.this, CancelActivity.class),
                                        PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    myBuilder.setContentTitle("抢课提示")
                                            .setContentText("这是内容")

                                            .setTicker("抢课通知")
                                            //设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示
                                            .setSmallIcon(R.drawable.icon)
                                            .setContentIntent(pi) //关联
                                    //设置默认声音和震动
//                                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                                            .setAutoCancel(true)//点击后取消
                                            .setWhen(System.currentTimeMillis())//设置通知时间
                                            .setPriority(Notification.PRIORITY_HIGH)//高优先级
                                            .setVisibility(Notification.VISIBILITY_PUBLIC);
                                            //android5.0加入了一种新的模式Notification的显示等级，共有三种：
                                            //VISIBILITY_PUBLIC  只有在没有锁屏时会显示通知
                                            //VISIBILITY_PRIVATE 任何情况都会显示通知
                                            //VISIBILITY_SECRET  在安全锁和没有锁屏的情况下显示通知
//                                            .setContentIntent(pi);  //3.关联PendingIntent
                                }else{

                                    myBuilder.setContentTitle("抢课提示")
                                            .setContentText("这是内容")
                                            .setContentIntent(pi) //关联
                                            .setTicker("抢课通知")
                                            //设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示
                                            .setSmallIcon(R.drawable.icon)

                                            //设置默认声音和震动
//                                            .setDefaults( Notification.DEFAULT_VIBRATE)
                                            .setAutoCancel(true)//点击后取消
                                            .setWhen(System.currentTimeMillis())//设置通知时间
                                           ;

                                }
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                                    myNotification = myBuilder.build();
//                                }
//                                //4.通过通知管理器来发起通知，ID区分通知
//                                myManager.notify(NOTIFICATION_ID_1, myNotification);
                                int j = 0;
                                int tag=0;
                                while (true) {
//轮询是否开启了
                                    try {
                                        //0.5秒的频率
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Document doc = null;
                                    try {
                                        doc = Jsoup.connect(Qurl).get();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    j++;
//                                    Log.i("time","已抢课"+ j + "次（秒）");
                                    myBuilder.setContentText("已为您抢课"+ j + "次，请勿关闭APP，点击本通知可取消抢课");
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        myNotification = myBuilder.build();
                                    }
                                    myManager.notify(num, myNotification);

                                    if (!doc.select("table.smalltable").select("tr").get(num).select("td").get(9).select("a").attr("href").toString().equals("")) {
                                        myBuilder.setContentText("抢课成功！");
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                            myNotification = myBuilder.build();
                                        }
                                        tag=1;
                                        myManager.notify(num, myNotification);
                                        break;
                                    }

                                    if(doit==2){
                                        myBuilder.setContentText("取消抢课成功");
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                            myNotification = myBuilder.build();
                                        }
                                        myManager.notify(num, myNotification);
                                        doit=1;
                                        break;
                                    }

                                }
 if(tag==1) {
//----
     String temp = mCoursebeanArrayList.get(num).getHref();
     String temp2 = temp.substring(25, temp.length() - 2).replaceAll("'", "");
     int a = temp2.indexOf(",");//*第一个出现的索引位置
     int b = 0;
     int i = 1;
     tem[0] = temp2.substring(0, a);
     while (a != -1) {
         b = a;
         a = temp2.indexOf(",", a + 1);//*从这个索引往后开始第一个出现的位置
         if (a == -1)
             tem[i] = temp2.substring(b + 1, temp2.length());
         else
             tem[i] = temp2.substring(b + 1, a);
         i++;
     }

     String url = "http://118.254.8.105:9000/servplat/newdate.php?DEPT_ID=" + tem[0] + "&TEACHER_ID=" + tem[1] + "&STUDENT_ID=" + tem[2] + "&CDATE=" + tem[3] + "&START_TIME=" + tem[4] + "&END_TIME=" + tem[5] + "&K2PRICE=" + tem[6] + "&K3PRICE=" + tem[7] + "&KEMUNO=" + tem[8] + "&CANDATE=" + tem[9] + "";

     //get请求得到预约信息
     UtilGet utilGet = new UtilGet();
     responseString = utilGet.GetResponeString(url);
     Document doc = Jsoup.parse(responseString);
     Elements elements1 = doc.select("input");
     //教练分组较为特殊
     tem[10] = elements1.get(4).attr("value");
     Log.i("tag", "-" + elements1.get(4).attr("value").toString());

     String path = "http://118.254.8.105:9000/servplat/adddate.php";
     //1.创建客户端对象
     HttpClient hc = new DefaultHttpClient();
     //2.创建post请求对象
     HttpPost hp = new HttpPost(path);
     hp.setHeader("Referer", "http://118.254.8.105:9000/servplat/newdate.php?DEPT_ID=" + tem[0] + "&TEACHER_ID=" + tem[1] + "&STUDENT_ID=" + tem[2] + "&CDATE=" + tem[3] + "&START_TIME=" + tem[4] + "&END_TIME=" + tem[5] + "&K2PRICE=" + tem[6] + "&K3PRICE=" + tem[7] + "&KEMUNO=" + tem[8] + "&CANDATE=" + tem[9] + "");
     hp.setHeader("Cookie", "UM_distinctid=9; CNZZDATA1261105269=10; PHPSESSID=11");
     //封装form表单提交的数据
     BasicNameValuePair bnvp = new BasicNameValuePair("DEPT_ID", tem[0]);
     BasicNameValuePair bnvp1 = new BasicNameValuePair("TEACHER_ID", tem[1]);
     BasicNameValuePair bnvp2 = new BasicNameValuePair("STUDENT_ID", tem[2]);
     BasicNameValuePair bnvp3 = new BasicNameValuePair("DRV_CAR_TYPE", tem[10]); //教练分组
     BasicNameValuePair bnvp4 = new BasicNameValuePair("CDATE", tem[3]);
     BasicNameValuePair bnvp5 = new BasicNameValuePair("START_TIME", tem[4]);
     BasicNameValuePair bnvp6 = new BasicNameValuePair("END_TIME", tem[5]);
     BasicNameValuePair bnvp7 = new BasicNameValuePair("K2PRICE", tem[6]);
     BasicNameValuePair bnvp8 = new BasicNameValuePair("K3PRICE", tem[7]);
     BasicNameValuePair bnvp9 = new BasicNameValuePair("KEMUNO", tem[8]);
     BasicNameValuePair bnvp10 = new BasicNameValuePair("CANDATE", tem[9]);
     BasicNameValuePair bnvp11 = new BasicNameValuePair("button", "新建预约");
     BasicNameValuePair bnvp12 = new BasicNameValuePair("PASSWORD", "");

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
     parameters.add(bnvp8);
     parameters.add(bnvp9);
     parameters.add(bnvp10);
     parameters.add(bnvp11);
     parameters.add(bnvp12);
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
             Toast.makeText(CoureseActivity.this, "抢课成功！", Toast.LENGTH_SHORT).show();
         } else {
             Looper.prepare();
             Toast.makeText(CoureseActivity.this, "抢课失败！网络错误", Toast.LENGTH_SHORT).show();
         }
         Looper.loop();

     } catch (Exception e) {
         e.printStackTrace();
     }
     //-----------------------------------------------------------------------------------------------------------
   refresh();
 }


                            }
                        }).start();


                    }
                });
                //  设置取消按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub

                    }
                });

                builder.create().show();


                super.handleMessage(msg);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courese);
        mContext=this;
        final Calendar mCalendar = Calendar.getInstance();
        sysDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        sysMonth = mCalendar.get(Calendar.MONTH);
        sysHour=mCalendar.get(Calendar.HOUR_OF_DAY);
//        Log.i("tag", "sys" + sysMonth + "" + mCalendar.get(Calendar.DAY_OF_YEAR));
//        Log.i("tag", "user" + MONTH + "" + DAY);
        /// 因为handler中有耗时任务 使用严苛模式 而我又没开一个线程
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        //
        mCoursebeanArrayList = (ArrayList<coursebean>) getIntent().getSerializableExtra("course");
        listView = (ListView) findViewById(R.id.listview_course);
        listView.setAdapter(new courseAdapter(this, mCoursebeanArrayList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                Log.i("s", "" + mCoursebeanArrayList.get(position).getHref());
                Calendar mCalendar = Calendar.getInstance();
                Calendar mCalendar2=Calendar.getInstance();
                mCalendar2.set(YEAR,MONTH,DAY);
                mCalendar.set(mCalendar2.get(Calendar.YEAR),sysMonth,sysDay);
                int days=getDaysBetween(mCalendar,mCalendar2);

                if (mCoursebeanArrayList.get(position).getC_statues().equals("已被预约") && mCoursebeanArrayList.get(position).getHref().equals("")) {

                    Toast.makeText(CoureseActivity.this, "此段时间内已有人预约" + mCoursebeanArrayList.get(position).getHref(), Toast.LENGTH_SHORT).show();

                } else if (mCoursebeanArrayList.get(position).getC_statues().equals("已被预约") && !mCoursebeanArrayList.get(position).getHref().equals("")) {

//Log.i("s",""+mCoursebeanArrayList.get(position).getHref());
                    Message message = new Message();
                    message.arg1 = position;
                    message.arg2 = 2;
                    mHandler.sendMessage(message);

                } else if (days>5 && mCoursebeanArrayList.get(position).getC_statues().equals("空闲") && mCoursebeanArrayList.get(position).getHref().equals("")) {
                    Toast.makeText(CoureseActivity.this, "只能预约五天内的~" + mCoursebeanArrayList.get(position).getHref(), Toast.LENGTH_SHORT).show();


                }  else if (days == 5 && mCoursebeanArrayList.get(position).getC_statues().equals("空闲") && mCoursebeanArrayList.get(position).getHref().equals("")) {
                    //第五天可以抢课。

                    Message message = new Message();
                    message.arg1 = position;
                    message.arg2 = 3;
                    mHandler.sendMessage(message);

                } else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            String temp = mCoursebeanArrayList.get(position).getHref();
                            String temp2 = temp.substring(25, temp.length() - 2).replaceAll("'", "");
                            int a = temp2.indexOf(",");//*第一个出现的索引位置
                            int b = 0;
                            int i = 1;
                            tem[0] = temp2.substring(0, a);
                            while (a != -1) {
                                b = a;
                                a = temp2.indexOf(",", a + 1);//*从这个索引往后开始第一个出现的位置
                                if (a == -1)
                                    tem[i] = temp2.substring(b + 1, temp2.length());
                                else
                                    tem[i] = temp2.substring(b + 1, a);
                                i++;
                            }

                            String url = "http://118.254.8.105:9000/servplat/newdate.php?DEPT_ID=" + tem[0] + "&TEACHER_ID=" + tem[1] + "&STUDENT_ID=" + tem[2] + "&CDATE=" + tem[3] + "&START_TIME=" + tem[4] + "&END_TIME=" + tem[5] + "&K2PRICE=" + tem[6] + "&K3PRICE=" + tem[7] + "&KEMUNO=" + tem[8] + "&CANDATE=" + tem[9] + "";

                            //get请求得到预约信息
                            UtilGet utilGet = new UtilGet();
                            responseString = utilGet.GetResponeString(url);
                            Document doc = Jsoup.parse(responseString);
                            Elements elements1 = doc.select("input");
                            //教练分组较为特殊
                            tem[10] = elements1.get(4).attr("value");
                            Log.i("tag", "-" + elements1.get(4).attr("value").toString());
                            //跳转确定

                            Message message = new Message();
                            message.arg1 = position;
                            message.arg2 = 1;
                            mHandler.sendMessage(message);
                        }
                    }).start();

                }
            }
        });

    }
    public void refresh() {

//        new Intent().setClass(this,MainActivity.class);
        mContext.startActivity(new Intent().setClass(mContext,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
    public int getDaysBetween (Calendar d1, Calendar d2) {
        if (d1.after(d2)) {  // swap dates so that d1 is start and d2 is end
            java.util.Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }
}
