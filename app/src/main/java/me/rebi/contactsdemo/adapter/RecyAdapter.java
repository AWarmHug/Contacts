package me.rebi.contactsdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.rebi.contactsdemo.R;
import me.rebi.contactsdemo.bean.Contact;
import me.rebi.contactsdemo.util.FirstLetterUtil;

/**
 * 作者: Warm.
 * 日期: 2017/1/8 16:12.
 * 联系: QQ-865741452.
 * 内容:
 */
public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> {

    private List<Contact> contactList;



    public RecyAdapter(List<Contact> contactList) {
        this.contactList = contactList;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyadapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position==0){
            holder.tv_firstLetter.setVisibility(View.VISIBLE);
            holder.tv_firstLetter.setText(getFirstLetter(contactList.get(position).getPinYin().substring(0,1)));
        }else {
            if (!getFirstLetter(contactList.get(position).getPinYin().substring(0,1)).equals(getFirstLetter(contactList.get(position-1).getPinYin().substring(0,1)))){
                holder.tv_firstLetter.setVisibility(View.VISIBLE);
                holder.tv_firstLetter.setText(getFirstLetter(contactList.get(position).getPinYin().substring(0,1)));
            }else {
                holder.tv_firstLetter.setVisibility(View.GONE);
            }
        }
        holder.tv_surname.setText(contactList.get(position).getName().substring(0,1));
        holder.tv_name.setText(contactList.get(position).getName());
        holder.tv_phone.setText(contactList.get(position).getPhoneNum());
    }

    public String getFirstLetter(String str){
        return FirstLetterUtil.getFirstLetter(str);
    }


    @Override
    public int getItemCount() {
        return contactList.size();
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
