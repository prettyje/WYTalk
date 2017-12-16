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
import android.widget.Button;
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
    Data data;
    ListChatting listChatting;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main__chat, container, false);
        setImageLoader(options, config, getContext());


        button = (Button)view.findViewById(R.id.button);
        data = new Data();
        listChatting = new ListChatting();

        list = new ArrayList<listItem>();
        listView1 = (ListView) view.findViewById(R.id.listview1);
        myAdapter = new MyAdapter(list);
        listView1.setAdapter(myAdapter);

        /*
        list.add(new listItem(R.drawable.test_image1,"송정은", "하이~~~"));
        list.add(new listItem(R.drawable.test_image2,"백승환","뭐해?"));*/

        try{
            int size = data.userVector.size();
            for (int i = 1; i < size; i++) {

                System.out.println("size>>"+i);
                //data.userVector.elementAt(i).id  아이디
                //data.userVector.elementAt(i).name 이름
                //listChatting.getChatNum(data.userVector.elementAt(i).id )방번호

                if (listChatting.hasIdChatRoom(data.userVector.elementAt(i).id)) {
                    int num = listChatting.getChatNum(data.userVector.elementAt(i).id);
                    list.add(new listItem("drawable://" + R.drawable.test_image1,
                            data.userVector.elementAt(i).name, data.chatDatahash.get(num).lastMsg));
                    System.out.println(data.userVector.elementAt(i).name+ data.chatDatahash.get(num).lastMsg);
                }

                myAdapter.notifyDataSetChanged();
            }

        }catch(Exception e){

            list.add(new listItem("drawable://" + R.drawable.test_image1, "송정은", "하이~~~"));
            list.add(new listItem("drawable://" + R.drawable.test_image2, "백승환", "뭐해?"));
            list.add(new listItem("drawable://" + R.drawable.test_image3, "진소린", "ㄸㄹ~"));
            list.add(new listItem("drawable://" + R.drawable.test_image4, "안형우", "짲으!"));

        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        System.out.println("하이");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    list.clear();

                    int size = data.userVector.size();
                    for (int i = 1; i < size; i++) {

                        System.out.println("size>>"+i);
                        //data.userVector.elementAt(i).id  아이디
                        //data.userVector.elementAt(i).name 이름
                        //listChatting.getChatNum(data.userVector.elementAt(i).id )방번호

                        if (listChatting.hasIdChatRoom(data.userVector.elementAt(i).id)) {
                            int num = listChatting.getChatNum(data.userVector.elementAt(i).id);
                            list.add(new listItem("drawable://" + R.drawable.test_image1,
                                    data.userVector.elementAt(i).name, data.chatDatahash.get(num).lastMsg));
                            System.out.println(data.userVector.elementAt(i).name+ data.chatDatahash.get(num).lastMsg);
                        }

                        myAdapter.notifyDataSetChanged();
                    }

                }catch(Exception e){

                    list.add(new listItem("drawable://" + R.drawable.test_image1, "송정은", "하이~~~"));
                    list.add(new listItem("drawable://" + R.drawable.test_image2, "백승환", "뭐해?"));
                    list.add(new listItem("drawable://" + R.drawable.test_image3, "진소린", "ㄸㄹ~"));
                    list.add(new listItem("drawable://" + R.drawable.test_image4, "안형우", "짲으!"));

                }
            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ChatActivity.class);


                intent.putExtra("User_Name",data.userVector.elementAt(i+1).name); //이름 전달
                intent.putExtra("User_ID",data.userVector.elementAt(i+1).id); //id 전달

                startActivity(intent);
            }
        });
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
