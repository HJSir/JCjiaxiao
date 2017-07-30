package com.example.jian.jcjiaxiao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jian.jcjiaxiao.R;
import com.example.jian.jcjiaxiao.adapter.coachAdapter;
import com.example.jian.jcjiaxiao.entity.coachbean;
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

public class YuyueActivity extends AppCompatActivity {
    ArrayList<coachbean> mCoachbeanArrayList;
    private ListView listView;
    String responseString;
    public static String Qurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuyue);
        mCoachbeanArrayList= (ArrayList<coachbean>) getIntent().getSerializableExtra("coach");

        listView = (ListView) findViewById(R.id.lv_coach);

        listView.setAdapter(new coachAdapter(this, mCoachbeanArrayList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
           Log.i("tag",""+mCoachbeanArrayList.get(position).getIs());

                if(mCoachbeanArrayList.get(position).getIs().equals("no")&&mCoachbeanArrayList.get(position).getHref().equals("")) {
                    Toast.makeText(YuyueActivity.this,"教练休假",Toast.LENGTH_SHORT).show();}
                   else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Qurl = "http://118.254.8.105:9000/servplat/" + mCoachbeanArrayList.get(position).getHref();
//Log.i("url",url);

                            //使用httpClient框架做get方式提交
                            //1.创建HttpClient对象
                            HttpClient hc = new DefaultHttpClient();
//                    hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
                            //2.创建httpGet对象，构造方法的参数就是网址
                            HttpGet hg = new HttpGet(Qurl);
//        hg.setHeader("Referer", "http://118.254.8.105:9000/servplat/Smart_DateStudy.php?YEAR=2017&MONTH=06&DAY=7");
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
                                responseString = null;
                                try {
                                    if (isGzip) {
                                        GZIPInputStream in = null;
                                        in = new GZIPInputStream(he.getContent());
                                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                        String line = "";
                                        while ((line = reader.readLine()) != null) {
                                            responseString += line;
                                        }
                                    } else {

                                        responseString = EntityUtils.toString(he);

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            Document doc = Jsoup.parse(responseString);
                            Elements elements1 = doc.select("tr.TableLine1");
                            Elements elements2 = doc.select("tr.TableLine2");
//                            Log.i("tag",doc.select("table.smalltable").toString());
                            ArrayList<coursebean> mCoursebeanArrayList = new ArrayList<coursebean>();

//                           Log.i("tag2",doc.toString()); Log.i("tag3",elements2.toString());

                            for (int i = 0; i < 5; i++) {
                                if(elements1.size()>i){
                                coursebean bean = new coursebean();

                                bean.setC_id(elements1.get(i).select("td").get(0).text());
                                bean.setC_name(elements1.get(i).select("td").get(1).text());
                                bean.setC_number(elements1.get(i).select("td").get(2).text());
                                bean.setC_typecourse(elements1.get(i).select("td").get(3).text());
                                bean.setC_typecar(elements1.get(i).select("td").get(4).text());
                                bean.setC_starttime(elements1.get(i).select("td").get(6).text());
                                bean.setC_endtime(elements1.get(i).select("td").get(7).text());
                                bean.setC_statues(elements1.get(i).select("td").get(8).text());
                                bean.setC_do(elements1.get(i).select("td").get(9).text());
                                bean.setHref(elements1.get(i).select("td").get(9).select("a").attr("href"));
                                mCoursebeanArrayList.add(bean);}
                                if(elements2.size()>i){
                                coursebean bean2 = new coursebean();
                             Log.i("tag","size="+elements2.size()+" i="+i);
                                bean2.setC_id(elements2.get(i).select("td").get(0).text());
                                bean2.setC_name(elements2.get(i).select("td").get(1).text());
                                bean2.setC_number(elements2.get(i).select("td").get(2).text());
                                bean2.setC_typecourse(elements2.get(i).select("td").get(3).text());
                                bean2.setC_typecar(elements2.get(i).select("td").get(4).text());
                                bean2.setC_starttime(elements2.get(i).select("td").get(6).text());
                                bean2.setC_endtime(elements2.get(i).select("td").get(7).text());
                                bean2.setC_statues(elements2.get(i).select("td").get(8).text());
                                bean2.setC_do(elements2.get(i).select("td").get(9).text());
                                bean2.setHref(elements2.get(i).select("td").get(9).select("a").attr("href"));
                                mCoursebeanArrayList.add(bean2);}
                            }

                            Intent intent = new Intent();
                            intent.putExtra("course", mCoursebeanArrayList);
                            intent.setClass(YuyueActivity.this, CoureseActivity.class);
                            startActivity(intent);

                        }

                    }).start();
                }

            }
        });

    }
}
