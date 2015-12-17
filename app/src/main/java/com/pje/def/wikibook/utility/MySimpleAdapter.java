package com.pje.def.wikibook.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.pje.def.wikibook.R;
import com.pje.def.wikibook.model.ImageCollection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geoffrey on 17/12/2015.
 */
public class MySimpleAdapter extends SimpleAdapter {

        private Context mContext;
        public LayoutInflater inflater=null;
        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            mContext = context;
            inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View vi=convertView;
            if(convertView==null)
                vi = inflater.inflate(R.layout.book_detail, null);

            HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);
            String isbn = (String) data.get("isbn");
            ImageView image=(ImageView)vi.findViewById(R.id.img_cover);
            if(ImageCollection.exist(isbn)){
                image.setImageBitmap(ImageCollection.getImage(isbn));
            } else {
                image.setImageResource(R.drawable.icone);
            }

            String title = (String) data.get("title");
            String author = (String) data.get("author");
            TextView e_t = (TextView)vi.findViewById(R.id.title);
            e_t.setText(title);
            TextView e_a = (TextView)vi.findViewById(R.id.author);
            e_a.setText(author);
            return vi;
        }

}
