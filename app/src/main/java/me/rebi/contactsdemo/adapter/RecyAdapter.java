package me.rebi.contactsdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.rebi.contactsdemo.R;
import me.rebi.contactsdemo.bean.Contacts;
import me.rebi.contactsdemo.util.FirstLetterUtil;

/**
 * 作者: Warm.
 * 日期: 2017/1/8 16:12.
 * 联系: QQ-865741452.
 * 内容:
 */
public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> {

    private List<Contacts> contactsList;
    private Map<String, Integer> firstLetterMap;


    public Map<String, Integer> getFirstLetterMap() {
        return firstLetterMap;
    }

    public RecyAdapter(List<Contacts> contactsList) {
        this.contactsList = contactsList;
        firstLetterMap=new LinkedHashMap<>();
        for (int i = 0; i < contactsList.size(); i++) {
            // 得到字母
            String firstLetter = getFirstLetter(contactsList.get(i).getPinYin().substring(0, 1).toUpperCase());
            if (!firstLetterMap.containsKey(firstLetter)) {
                firstLetterMap.put(firstLetter, i);
            }
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyadapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position==0){
            holder.tv_firstLetter.setVisibility(View.VISIBLE);
            holder.tv_firstLetter.setText(getFirstLetter(contactsList.get(position).getPinYin().substring(0,1)));
        }else {
            if (!getFirstLetter(contactsList.get(position).getPinYin().substring(0,1)).equals(getFirstLetter(contactsList.get(position-1).getPinYin().substring(0,1)))){
                holder.tv_firstLetter.setVisibility(View.VISIBLE);
                holder.tv_firstLetter.setText(getFirstLetter(contactsList.get(position).getPinYin().substring(0,1)));
            }else {
                holder.tv_firstLetter.setVisibility(View.GONE);
            }
        }
        holder.tv_surname.setText(contactsList.get(position).getName().substring(0,1));
        holder.tv_name.setText(contactsList.get(position).getName());
        holder.tv_phone.setText(contactsList.get(position).getPhoneNum());
    }

    public String getFirstLetter(String str){
        return FirstLetterUtil.getFirstLetter(str);
    }


    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_firstLetter, tv_surname, tv_name, tv_phone;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_firstLetter = (TextView) itemView.findViewById(R.id.tv_firstLetter);
            tv_surname = (TextView) itemView.findViewById(R.id.tv_surname);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
        }
    }

}
