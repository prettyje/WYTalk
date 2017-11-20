package app.wytalk;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class Main_ChatFragment extends Fragment {


    private ListView listView1;
    private MyAdapter myAdapter;
    private ArrayList<listItem> list;

    ImageLoader imageLoader = ImageLoader.getInstance();
    ImageLoaderConfiguration config = null;
    DisplayImageOptions options = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_main__chat,container,false);
        setImageLoader(options, config, getContext());


        list = new ArrayList<listItem>();
        listView1 = (ListView) view.findViewById(R.id.listview1);
        myAdapter = new MyAdapter(list);
        listView1.setAdapter(myAdapter);
/*
        list.add(new listItem(R.drawable.test_image1,"송정은", "하이~~~"));
        list.add(new listItem(R.drawable.test_image2,"백승환","뭐해?"));*/
        list.add(new listItem("drawable://" + R.drawable.test_image1 ,"송정은", "하이~~~"));
        list.add(new listItem("drawable://" + R.drawable.test_image2 ,"송정은", "하이~~~"));
        // sf[1][0] = new SearchFood("drawable://" + R.drawable.koreanfood1, "광장", "순희네빈대떡");

        return view;
    }




    public class listItem {
        //private int profile;
        String url;
        private String name;
        private String chat;

        public listItem(String url, String name, String chat) {
            //this.profile = profile;
            this.url = url;
            this.name = name;
            this.chat = chat;
        }

    }

    //Adapter
    public class MyAdapter extends BaseAdapter {

        private ArrayList<listItem> list;

        public MyAdapter(ArrayList<listItem> list) {
            this.list = list;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public listItem getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            View v = convertView;
            ImageView profile;
            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_item, parent, false);

                profile = (ImageView) v.findViewById(R.id.profile_image);
                TextView name = (TextView) v.findViewById(R.id.name);
                TextView chat = (TextView) v.findViewById(R.id.chat);

                //profile.setImageResource(getItem(pos).profile);
                name.setText(getItem(pos).name);
                chat.setText(getItem(pos).chat);
                imageLoader.displayImage(list.get(position).url, profile , options);

               // Glide.with(getContext()).load().into(profile);
            }



            return v;
        }


    }

    public void setImageLoader(DisplayImageOptions options,
                               ImageLoaderConfiguration config, Context context){
        options = new DisplayImageOptions.Builder()
                .considerExifParams(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options)
                .memoryCache(new WeakMemoryCache())
                .writeDebugLogs().build();

        options = new DisplayImageOptions.Builder().considerExifParams(true).cacheInMemory(true)
                .build();
        ImageLoader.getInstance().init(config);
    }

}
