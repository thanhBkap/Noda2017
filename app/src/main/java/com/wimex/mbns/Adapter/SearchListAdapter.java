package com.wimex.mbns.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.wimex.mbns.Model.Search;
import com.wimex.mbns.R;
import com.wimex.mbns.SearchActivity;
import com.wimex.mbns.XuLyData.DinhDangChu;

import java.util.ArrayList;

/**
 * Created by admin on 4/14/2017.
 */

public class SearchListAdapter extends BaseAdapter implements Filterable {
    ArrayList<Search> list;
    ArrayList<Search> filteredList;
    Context context;

    public SearchListAdapter(Context context, ArrayList<Search> list, ArrayList<Search> filteredList) {
        this.list = list;
        this.context = context;
        this.filteredList = filteredList;
        // updateData();
    }

  /*  private void updateData() {
        for (int i=0;i<list.size();i++){
            filteredList.add(new Search(list.get(i).getId(),list.get(i).getTenSanPham()));
        }
    }*/

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_search, null);
            holder.txtListSearch = (TextView) convertView.findViewById(R.id.txtListSearch);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtListSearch.setText(filteredList.get(position).getTenSanPham());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Search> filter = new ArrayList<>();
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() <= 0) {
                    for (int i=0;i<list.size();i++){
                        filter.add(new Search(list.get(i).getId(), list.get(i).getTenSanPham()));

                    }
                    filterResults.count = filter.size();
                    filterResults.values = filter;
                } else {
                    String search = DinhDangChu.unAccent(constraint.toString()).toLowerCase();
                    for (int i = 0; i < list.size(); i++) {
                        if (DinhDangChu.unAccent(list.get(i).getTenSanPham()).toLowerCase().contains(search)) {
                            filter.add(new Search(list.get(i).getId(), list.get(i).getTenSanPham()));
                        }
                    }
                    filterResults.count = filter.size();
                    filterResults.values = filter;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null) {
                    filteredList.clear();
                    filteredList = (ArrayList<Search>) results.values;
                    notifyDataSetChanged();
                    if (results.count<=0){
                        SearchActivity.layout_search.setVisibility(View.VISIBLE);
                    }else{
                        SearchActivity.layout_search.setVisibility(View.GONE);
                    }
                }
            }
        };
        return filter;
    }

   /* public void filter2(String charText) {
        charText = charText.toLowerCase();
        if (charText.length() == 0) {
            filteredList.addAll(list);
            for (String s:list_main){
                DiaChiMoi.listDiaChiMoi.add(s);
            }
        } else {
            for (int i=0;i<list_khong_dau.size();i++){
                if (list_khong_dau.get(i).toLowerCase().contains(charText)){
                    list.add(list_main.get(i));
                    DiaChiMoi.listDiaChiMoi.add(list_main.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }*/
    class ViewHolder {
        TextView txtListSearch;
    }
}
