package com.example.jian.jcjiaxiao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jian.jcjiaxiao.R;
import com.example.jian.jcjiaxiao.entity.coursebean;

import java.util.Calendar;
import java.util.List;

import static com.example.jian.jcjiaxiao.activity.MainActivity.DAY;
import static com.example.jian.jcjiaxiao.activity.MainActivity.MONTH;
import static com.example.jian.jcjiaxiao.activity.MainActivity.YEAR;

/**
 * Created by jian on 2017/4/26.
 */

public class courseAdapter extends BaseAdapter {

    private List<coursebean> mlist;
    private LayoutInflater mInflater;


    public courseAdapter(Context context, List<coursebean> list) {
        mlist=list;
        mInflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return  mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView =mInflater.inflate(R.layout.listview_course,null);
            viewHolder.co_id=(TextView) convertView.findViewById(R.id.co_id);
            viewHolder.co_name=(TextView) convertView.findViewById(R.id.co_name);
            viewHolder.co_statues=(TextView) convertView.findViewById(R.id.co_statue);
            viewHolder.co_time=(TextView) convertView.findViewById(R.id.co_time);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Calendar mCalendar = Calendar.getInstance();
        int sysDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        int sysMonth=mCalendar.get(Calendar.MONTH);

        Calendar mCalendar3 = Calendar.getInstance();
        Calendar mCalendar2=Calendar.getInstance();
        mCalendar2.set(YEAR,MONTH,DAY);
        mCalendar.set(mCalendar2.get(Calendar.YEAR),sysMonth,sysDay);
        int days=getDaysBetween(mCalendar3,mCalendar2);
        coursebean bean = mlist.get(position);
        viewHolder.co_time.setText("时间："+bean.getC_starttime()+"--"+bean.getC_endtime());
        viewHolder.co_name.setText("姓名："+bean.getC_name());

        if (bean.getHref().length()!=0&&bean.getC_statues().equals("已被预约")){ //自己预约
            viewHolder.co_statues.setText("可撤销");
            viewHolder.co_statues.setTextColor(Color.RED);

        }
        else if(bean.getC_statues().equals("空闲")&&bean.getHref().length()!=0) //空闲可预约
        {
            viewHolder.co_statues.setTextColor(Color.GREEN);
            viewHolder.co_statues.setText(bean.getC_statues());
        }else if(days>5&&bean.getC_statues().equals("空闲")&&bean.getHref().length()==0){

            viewHolder.co_statues.setTextColor(Color.BLUE);
            viewHolder.co_statues.setText("只可预约五天内");

        }
        else if(days==5&&bean.getC_statues().equals("空闲")&&bean.getHref().length()==0){

            viewHolder.co_statues.setTextColor(Color.GREEN);
            viewHolder.co_statues.setText("点击抢课");


        }else
        {
            viewHolder.co_statues.setTextColor(Color.BLACK);
            viewHolder.co_statues.setText(bean.getC_statues());
        }

        viewHolder.co_id.setText("序号："+bean.getC_id());
        return convertView;
    }
    class ViewHolder{
        TextView co_id;
        TextView co_name;
        //TextView c_number;
        // TextView c_typecar;
        // TextView c_typecourse;
//        TextView c_starttime;
//        TextView c_endtime;
        TextView co_time;
        TextView co_statues;
        //TextView c_do;


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
