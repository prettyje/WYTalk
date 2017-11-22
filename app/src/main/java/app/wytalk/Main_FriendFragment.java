package app.wytalk;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class Main_FriendFragment extends Fragment {

    private  ListView mylistView;
    private ArrayList<listItem> mylist;
    private MyAdapter myyAdapter;

    private ListView listView;
    private ArrayList<listItem> list;
    private MyAdapter myAdapter;


    private TextView textView;

    ImageLoader imageLoader = ImageLoader.getInstance();
    ImageLoaderConfiguration config = null;
    DisplayImageOptions options = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main__friend, container, false);
        View view1 = inflater.inflate(R.layout.list_item2,container,false); //item

        setImageLoader(options, config, getContext());


        textView = (TextView)view1.findViewById(R.id.chat);
        textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.textlogo));

        mylist = new ArrayList<listItem>();
        list = new ArrayList<listItem>();

        mylistView = (ListView) view.findViewById(R.id.listView0);
        listView = (ListView) view.findViewById(R.id.listView);

        myyAdapter = new MyAdapter(mylist);
        myAdapter = new MyAdapter(list);

        mylistView.setAdapter(myyAdapter);
        listView.setAdapter(myAdapter);


/*내 프로필*/
        mylist.add(new listItem("drawable://" + R.drawable.test_image1, "송정은", "다 잘될거야!"));


 /*친구 목록*/
        list.add(new listItem("drawable://" + R.drawable.test_image2, "백승환", "github"));
        list.add(new listItem("drawable://" + R.drawable.test_image3, "진소린", "^0^"));
        list.add(new listItem("drawable://" + R.drawable.test_image4, "안형우", "고난"));



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity().getApplicationContext(),Main_Chat_ProfileActivity.class);

                intent.putExtra("User_Name",mylist.get(i).name); //이름 전달
                intent.putExtra("User_Profile",mylist.get(i).url); // 프로필 사진 전달
                intent.putExtra("User_Chat",mylist.get(i).chat); // 상태메시지 전달
                intent.putExtra("Check","myP"); //나의 프로필
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity().getApplicationContext(),Main_Chat_ProfileActivity.class);

                intent.putExtra("User_Name",list.get(i).name); //이름 전달
                intent.putExtra("User_Profile",list.get(i).url); // 프로필 사진 전달
                intent.putExtra("User_Chat",list.get(i).chat); // 상태메시지 전달
                intent.putExtra("Check","frP"); //친구 프로필

                startActivity(intent);
            }
        });
    }

    public class listItem {
        //private int profile;
        String url; //프로필 사진
        private String name;  //이름
        private String chat;  //상태메시지

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
                v = inflater.inflate(R.layout.list_item2, parent, false);

                profile = (ImageView) v.findViewById(R.id.profile_image);
                TextView name = (TextView) v.findViewById(R.id.name);
                TextView chat = (TextView) v.findViewById(R.id.chat);


               // profile.setImageResource(getItem(pos).profile);
                name.setText(getItem(pos).name);
                chat.setText(getItem(pos).chat);

                imageLoader.displayImage(list.get(position).url, profile, options); //이미지 처리

            }


            return v;
        }
    }

    public void setImageLoader(DisplayImageOptions options,
                               ImageLoaderConfiguration config, Context context) { //이미지 처리
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
