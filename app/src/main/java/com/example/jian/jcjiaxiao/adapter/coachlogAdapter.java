package com.example.jian.jcjiaxiao.adapter;

/**
 * Created by jian on 2017/6/1.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jian.jcjiaxiao.R;
import com.example.jian.jcjiaxiao.entity.coursebean;

import java.util.List;

/**
 * Created by jian on 2017/4/26.
 */

public class coachlogAdapter extends BaseAdapter {

    private List<coursebean> mlist;
    private LayoutInflater mInflater;


    public coachlogAdapter(Context context, List<coursebean> list) {
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
            convertView =mInflater.inflate(R.layout.listview_coachlog,null);
            viewHolder.c_id=(TextView) convertView.findViewById(R.id.log_id);
            viewHolder.c_name=(TextView) convertView.findViewById(R.id.log_name);
            viewHolder.c_statues=(TextView) convertView.findViewById(R.id.log_statue);
            viewHolder.c_typecar=(TextView) convertView.findViewById(R.id.log_typecar);
            viewHolder.c_typecourse=(TextView) convertView.findViewById(R.id.log_typecourese);
            viewHolder.c_comment=(TextView) convertView.findViewById(R.id.log_comment);
            viewHolder.c_time=(TextView) convertView.findViewById(R.id.log_time);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        coursebean bean = mlist.get(position);
        if(bean.getC_statues().equals("未开始")){
            viewHolder.c_statues.setTextColor(Color.RED);
            viewHolder.c_time.setTextColor(Color.RED);
            viewHolder.c_comment.setText(bean.getC_comment());
            viewHolder.c_time.setText(bean.getC_starttime());
            viewHolder.c_statues.setText(bean.getC_statues());
        }else if(bean.getC_comment().equals("评价教练")){
            viewHolder.c_comment.setTextColor(Color.BLUE);
            viewHolder.c_comment.setText(bean.getC_comment());
            viewHolder.c_time.setText(bean.getC_starttime());
            viewHolder.c_statues.setText(bean.getC_statues());

        }else{
            viewHolder.c_statues.setTextColor(Color.GRAY);
            viewHolder.c_time.setTextColor(Color.GRAY);
            viewHolder.c_comment.setText(bean.getC_comment());
            viewHolder.c_time.setText(bean.getC_starttime());
            viewHolder.c_statues.setText(bean.getC_statues());

        }

        viewHolder.c_name.setText(bean.getC_name());
        viewHolder.c_id.setText(bean.getC_id());
        viewHolder.c_typecar.setText(bean.getC_typecar());
        viewHolder.c_typecourse.setText(bean.getC_typecourse());


        return convertView;
    }
    class ViewHolder{
        TextView c_id;
        TextView c_name;
        //TextView c_number;
        TextView c_typecar;
        TextView c_typecourse;
        //       TextView c_starttime;
//        TextView c_endtime;
        TextView c_time;
        TextView c_statues;
        TextView c_comment;
        //TextView c_do;


    }
}
