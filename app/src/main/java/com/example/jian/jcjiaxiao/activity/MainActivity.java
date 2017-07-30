package com.example.jian.jcjiaxiao.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jian.jcjiaxiao.R;
import com.example.jian.jcjiaxiao.utill.UtilGet;
import com.example.jian.jcjiaxiao.entity.coachbean;
import com.example.jian.jcjiaxiao.adapter.coachlogAdapter;
import com.example.jian.jcjiaxiao.entity.coursebean;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import static com.example.jian.jcjiaxiao.activity.LoginActivity.Id;
import static com.example.jian.jcjiaxiao.activity.LoginActivity.Name;
import static com.example.jian.jcjiaxiao.activity.LoginActivity.Number;
import static com.example.jian.jcjiaxiao.activity.LoginActivity.Type;
import static com.example.jian.jcjiaxiao.activity.LoginActivity.UserID;
import static com.example.jian.jcjiaxiao.activity.LoginActivity.bitmap;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tv1;
    int tag=1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    public static String commenthref;
public static int DAY;
    public static int MONTH;
    public static int YEAR;
    private CalendarView cv;
    ArrayList<coursebean> mCoachLogArrayList = new ArrayList<coursebean>();
    private ImageView imageview;

    String responseString2 = null;

    private ListView listView;
    Handler update = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1==1){


                listView.setAdapter(new coachlogAdapter(MainActivity.this, mCoachLogArrayList));

            }


        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setComponent();
        cv = (CalendarView) findViewById(R.id.calendarView);
        listView = (ListView) findViewById(R.id.listview_coachlog);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mCoachLogArrayList.get(position).c_comment.equals("评价教练")){
//跳转评价教练页面
                    commenthref=mCoachLogArrayList.get(position).getHref();
                   startActivity(new Intent().setClass(MainActivity.this,CommentActivity.class));


                }
//                Toast.makeText(MainActivity.this,""+mCoachLogArrayList.get(position).c_comment.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        //为CalendarView组件的日期改变事件添加事件监听器
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, final int year,
                                            final int month, final int dayOfMonth) {
                // TODO Auto-generated method stub
                //使用Toast显示用户选择的日期
                if (tag == 2) {
                Toast.makeText(MainActivity.this,"您有教练未评教，请点击底部未评价记录进行评教。",Toast.LENGTH_SHORT).show();
                } else{
                    DAY = dayOfMonth;
                MONTH = month;
                    YEAR=year;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path = "http://118.254.8.105:9000/servplat/Smart_DateStudy.php?YEAR=" + year + "&MONTH=" + (month + 1) + "&DAY=" + dayOfMonth;
                        //使用httpClient框架做get方式提交
                        //1.创建HttpClient对象
                        HttpClient hc = new DefaultHttpClient();
//                    hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
                        //2.创建httpGet对象，构造方法的参数就是网址
                        HttpGet hg = new HttpGet(path);


                        hg.setHeader("Referer", "http://118.254.8.105:9000/servplat/Smart_DateStudySelect.php?USER_ID=" + UserID);
                        hg.setHeader("Cookie", "UM_distinctid=9; CNZZDATA1261105269=10; PHPSESSID=11");
                        HttpResponse hr = null;
                        try {
                            hr = hc.execute(hg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //拿到响应头中的状态行
                        StatusLine sl = hr.getStatusLine();
                        if (sl.getStatusCode() == 200) {
                            Header[] headers = hr.getHeaders("Content-Encoding");
                            HttpEntity he = hr.getEntity();
                            boolean isGzip = false;
                            headers = hr.getHeaders("Content-Encoding");

                            for (Header h : headers) {
                                if (h.getValue().equals("gzip")) {
                                    //返回头中含有gzip
                                    isGzip = true;
                                }
                            }
                            responseString2 = null;
                            try {
                                if (isGzip) {
                                    GZIPInputStream in = null;
                                    in = new GZIPInputStream(he.getContent());
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                    String line = "";
                                    while ((line = reader.readLine()) != null) {
                                        responseString2 += line;
                                    }
                                } else {

                                    responseString2 = EntityUtils.toString(he);

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Document doc = Jsoup.parse(responseString2);
                            ArrayList<coachbean> mCoachbeanArrayList = new ArrayList<coachbean>();
                            Elements elements;


                            elements = doc.select("tr.TableLineD");
                            //休假教练 为D

int j=0;
                            int i = 0;

                            while (i < elements.size()) {

                                coachbean mcoach = new coachbean();
                                mcoach.setSid(elements.get(i).select("td").get(0).text());
                                mcoach.setName(elements.get(i).select("td").get(1).text());
                                mcoach.setPlate(elements.get(i).select("td").get(2).text());
                                mcoach.setSex(elements.get(i).select("td").get(3).text());
                                mcoach.setTypeforcar(elements.get(i).select("td").get(4).text());
                                mcoach.setTypeforcourse(elements.get(i).select("td").get(5).text());
                                mcoach.setComment(elements.get(i).select("td").get(6).text());
                                mcoach.setStatus(elements.get(i).select("td").get(7).text());
                                mcoach.setPrice(elements.get(i).select("td").get(8).text());
                                mcoach.setXueshi(elements.get(i).select("td").get(9).text());
                                mcoach.setHref(elements.get(i).select("a").attr("href"));
                                mcoach.setIs("no"); //教练休假
//                                Log.i("tag333"," ---"+elements.get(i).select("td").get(0).text());
                                mCoachbeanArrayList.add(
                                        j, mcoach);
                                j++;
                                i++;
                            }


                            for (int i1 = 1; i1 <= 2; i1++) {
int j1=j;
                                elements = doc.select("tr.TableLine" + i1);
//                            Log.i("tag"," ---"+elements.equals("")+"---"+elements.toString());
                                Log.i("tag","size="+elements.size()+"j="+j);
                                if (elements.size() != 0) {
                                    int i3=0;
                                    while(i3<elements.size()) {

                                        if(elements.get(i3).select("td").size()<=1){

                                            i3++;
                                            continue;

                                        }
                                        coachbean mcoach = new coachbean();
                                        mcoach.setSid(elements.get(i3).select("td").get(0).text());
                                        mcoach.setName(elements.get(i3).select("td").get(1).text());
                                        mcoach.setPlate(elements.get(i3).select("td").get(2).text());
                                        mcoach.setSex(elements.get(i3).select("td").get(3).text());
                                        mcoach.setTypeforcar(elements.get(i3).select("td").get(4).text());
                                        mcoach.setTypeforcourse(elements.get(i3).select("td").get(5).text());
                                        mcoach.setComment(elements.get(i3).select("td").get(6).text());
                                        mcoach.setStatus(elements.get(i3).select("td").get(7).text());
                                        mcoach.setPrice(elements.get(i3).select("td").get(8).text());
                                        mcoach.setXueshi(elements.get(i3).select("td").get(9).text());
                                        mcoach.setHref(elements.get(i3).select("a").attr("href"));
                                        mcoach.setIs("is"); //教练工作

                                        mCoachbeanArrayList.add(j1, mcoach);
                                        j1++;

                                        i3++;
                                    }
                                }
                                Log.i("tag","j1="+j1);
                            }

                            Intent intent = new Intent();
//                        Log.i("tag2",""+mCoachbeanArrayList.toString()+mCoachbeanArrayList.size());
                            intent.putExtra("coach", mCoachbeanArrayList);
                            intent.setClass(MainActivity.this, YuyueActivity.class);
                            startActivity(intent);

                        }

                    }
                }).start();
            }
            }


        });

        //底部预约历史记录
        new Thread(new Runnable() {
            @Override
            public void run() {
                UtilGet utilGet = new UtilGet();
                String url = "http://118.254.8.105:9000/servplat/mycenter.php?USER_ID=" + UserID;
                String responeString3 = utilGet.GetResponeString(url);
                Document doc = Jsoup.parse(responeString3);
                Elements elements = doc.select("tr");

                for (int i = 0; i <elements.size()-1; i++) {
                    //使用课程实体。
//                    Log.i("Tag","-"+i +elements.get(i).select("td").get(0).text().toString());
                    coursebean coachlog=new coursebean();
                    coachlog.setC_id( elements.get(i).select("td").get(0).text().toString());
                    coachlog.setC_name( elements.get(i).select("td").get(1).text().toString());
                    coachlog.setC_typecar( elements.get(i).select("td").get(2).text().toString());
                    coachlog.setC_typecourse( elements.get(i).select("td").get(3).text().toString());
                    coachlog.setC_starttime( elements.get(i).select("td").get(4).text().toString());
                    coachlog.setC_statues( elements.get(i).select("td").get(7).text().toString());
                    coachlog.setC_comment( elements.get(i).select("td").get(8).text().toString());
                    coachlog.setHref(elements.get(i).select("td").get(8).select("a").attr("href").toString());
                    Log.i("href",elements.get(i).select("td").get(8).select("a").attr("href").toString());
                    if(!elements.get(i).select("td").get(8).select("a").attr("href").toString().equals(""))
                    tag=2;
                    mCoachLogArrayList.add(coachlog);
                }


                Message msg = update.obtainMessage();
                msg.arg1 =1;
                update.sendMessage(msg);

            }
        }).start();


    }

    void setComponent() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        tv1 = (TextView) headerView.findViewById(R.id.textView1);
//        Log.i("tag",tv1.toString());
        tv2 = (TextView) headerView.findViewById(R.id.textView2);
        tv3 = (TextView) headerView.findViewById(R.id.textView3);
        tv4 = (TextView) headerView.findViewById(R.id.textView4);
        tv1.setText(Id);
        tv2.setText(Name);
        tv3.setText(Number);
        tv4.setText(Type);

        imageview = (ImageView) headerView.findViewById(R.id.imageView);
        imageview.setImageBitmap(bitmap);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Toast.makeText(MainActivity.this, "本页就是科目二的预约啦~", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(MainActivity.this, "正在加班加点开发中~", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            //免责声明
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MianzeActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_manage) {//退出登录逻辑

            SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("status", "0"); //0为注销
            editor.commit();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
