/*
 * Copyright 2015 NECTEC
 * National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package th.or.nectec.android.widget.thai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import th.or.nectec.entity.ThaiAddress;

/**
 * Created by N. Choatravee on 20/4/2558.
 */
public class ThaiAddressAdapter extends BaseAdapter {

    ArrayList<ThaiAddress> thaiAddressList;
    LayoutInflater mInflater;

    public ThaiAddressAdapter(Context context, List<ThaiAddress> thaiAddressList) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addAll(thaiAddressList);
    }

    public void addAll(Collection<? extends ThaiAddress> collection) {
        thaiAddressList = new ArrayList<>();
        thaiAddressList.addAll(collection);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResources(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResources(position, convertView, parent);
    }

    public View createViewFromResources(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.address_picker_list_item, parent, false);
        }

        TextView text = (TextView) convertView;

        text.setText(getItem(position).getProvince());
        return text;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return thaiAddressList.size();
    }

    @Override
    public ThaiAddress getItem(int position) {
        return thaiAddressList.get(position);
    }
}
