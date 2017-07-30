package com.example.jian.jcjiaxiao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.jian.jcjiaxiao.R;
import com.example.jian.jcjiaxiao.entity.coachbean;

import java.util.List;

/**
 * Created by jian on 2016/12/7.
 */

public class coachAdapter extends BaseAdapter {
    private List<coachbean> mlist;
    private LayoutInflater mInflater;


    public coachAdapter(Context context, List<coachbean> list) {
        mlist=list;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
//        int count=0;
//       for(int i=0;i<35;i++){
//           if(mlist.get(i).getSid()!=null){
//               count++;
//           }
//
//       }
//        Log.i("s",""+count);
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
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
            convertView =mInflater.inflate(R.layout.listview_coach,null);

            viewHolder.sid= (TextView) convertView.findViewById(R.id.c_id);
            viewHolder.name= (TextView) convertView.findViewById(R.id.c_name);
            viewHolder.plate= (TextView) convertView.findViewById(R.id.c_car);
            viewHolder.sex= (TextView) convertView.findViewById(R.id.c_sex);
            viewHolder.status= (TextView) convertView.findViewById(R.id.c_statues);
            viewHolder.xueshi= (TextView) convertView.findViewById(R.id.c_xueshi);
            viewHolder.comment= (TextView) convertView.findViewById(R.id.c_comment);
            viewHolder.typeforcar= (TextView) convertView.findViewById(R.id.c_typeforcar);
            viewHolder.typeforcourse= (TextView) convertView.findViewById(R.id.c_courese);
            viewHolder.price= (TextView) convertView.findViewById(R.id.c_price);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        coachbean bean=mlist.get(position);
        Log.i("333",""+bean.toString());
        viewHolder.sid.setText("序号："+bean.getSid());
        viewHolder.name.setText("姓名："+bean.getName());
        viewHolder.plate.setText("车牌："+bean.getPlate());
        viewHolder.sex.setText("性别："+bean.getSex());
        viewHolder.status.setText("预约："+bean.getStatus());
        viewHolder.xueshi.setText("学时："+bean.getXueshi());
        viewHolder.comment.setText("总评："+bean.getComment());
        viewHolder.typeforcar.setText("车型："+bean.getTypeforcar());
        viewHolder.typeforcourse.setText("课程："+bean.getTypeforcourse());
        viewHolder.price.setText("价格："+bean.getPrice());
        return convertView;
    }
    class ViewHolder{
        private TextView  sid; //序号
//        private TextView  number;//电话号码
        private TextView name;
        private TextView plate;//车牌
        private TextView sex;
        private TextView status;//状态
        private TextView price;
        private TextView xueshi;
//        private TextView href;
        private TextView comment;
        private TextView typeforcar;
        private TextView typeforcourse;
    }
}