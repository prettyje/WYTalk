package app.wytalk;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Main_FriendFragment extends Fragment {


    Data data;
    int size = 0;

    private ListView mylistView;
    private ArrayList<listItem> mylist;
    private MyAdapter myyAdapter;

    private ListView listView;
    private ArrayList<listItem> list;
    private MyAdapter myAdapter;
    private TextView textView;

    Button button;

    ImageLoader imageLoader = ImageLoader.getInstance();
    ImageLoaderConfiguration config = null;
    DisplayImageOptions options = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        data = new Data();
        View view = inflater.inflate(R.layout.fragment_main__friend, container, false);
        View view1 = inflater.inflate(R.layout.list_item2, container, false); //item

        setImageLoader(options, config, getContext());


        button = (Button) view.findViewById(R.id.button);

        textView = (TextView) view1.findViewById(R.id.chat);
        textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.textlogo));

        mylist = new ArrayList<listItem>();
        list = new ArrayList<listItem>();

        mylistView = (ListView) view.findViewById(R.id.listView0);
        listView = (ListView) view.findViewById(R.id.listView);

        myyAdapter = new MyAdapter(mylist);
        myAdapter = new MyAdapter(list);

        mylistView.setAdapter(myyAdapter);
        listView.setAdapter(myAdapter);


        try {
            /***내 프로필***/


            mylist.add(new listItem("drawable://" + R.drawable.test_image1,
                    data.userVector.elementAt(0).name, data.userVector.elementAt(0).stateMsg));

         /*   mylist.add(new listItem("drawable://" + data.userVector.elementAt(0).id,
                    data.userVector.elementAt(0).name, data.userVector.elementAt(0).stateMsg));
*/
            myyAdapter.notifyDataSetChanged();

            /***친구 목록***/

            size = data.userVector.size();
            for (int i = 1; i < size; i++) {
                list.add(new listItem("drawable://" + R.drawable.test_image1,
                        data.userVector.elementAt(i).name, data.userVector.elementAt(i).stateMsg));
                myyAdapter.notifyDataSetChanged();
            }


        } catch (NullPointerException e) {

            mylist.add(new listItem("drawable://" + R.drawable.test_image1, "실패", "실패"));
            list.add(new listItem("drawable://" + R.drawable.test_image2, "백승환", "github"));
            list.add(new listItem("drawable://" + R.drawable.test_image3, "진소린", "^0^"));
            list.add(new listItem("drawable://" + R.drawable.test_image4, "안형우", "고난"));
        }


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    // list.clear();
                    mylist.clear();
                    /***내 프로필***/


                    //   String path = Images.Media.insertImage(context.getContentResolver(), objImage,null, null);
                    //  Uri image1= Uri.parse(path);

                   /* String path = MediaStore.Images.Media.insertImage(getContext() .getContentResolver(),
                            data.imgdatahash.get(data.userVector.elementAt(0).id),null,null);
                    */
                    // Uri image1 = Uri.parse(path);

                    /***     Uri.parse(MediaStore.Images.Media.insertImage(getContext() .getContentResolver(),
                     data.imgdatahash.get(data.userVector.elementAt(0).id),null,null))***/

                    //data.imgdatahash.get(data.userVector.elementAt(0).id)
                    // Drawable d = new BitmapDrawable(getResources(),data.imgdatahash.get(data.userVector.elementAt(0).id));

                    Bitmap bitmap = data.imgdatahash.get(data.userVector.elementAt(0).id);
                    //String path = MediaStore.Images.Media.insertImage(getContext() .getContentResolver(),
                    //      bitmap,null,null);

                    String path = getBase64String(bitmap);

                    String s = Uri.decode(path);

                    mylist.add(new listItem(s,
                            data.userVector.elementAt(0).name, data.userVector.elementAt(0).stateMsg));

                    myyAdapter.notifyDataSetChanged();

                    /***친구 목록***/

/*                    size = data.userVector.size();
                    for (int i = 1; i < size; i++) {
                        list.add(new listItem(MediaStore.Images.Media.insertImage(getContext() .getContentResolver(),
                                data.imgdatahash.get(data.userVector.elementAt(i).id),null,null),
                                data.userVector.elementAt(i).name, data.userVector.elementAt(i).stateMsg));

                        myAdapter.notifyDataSetChanged();
                    }*/

                } catch (Exception e) {

                }
            }
        });

        mylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Main_Chat_ProfileActivity.class);

                intent.putExtra("User_Name", mylist.get(i).name); //이름 전달
                intent.putExtra("User_Profile", mylist.get(i).url); // 프로필 사진 전달
                intent.putExtra("User_Chat", mylist.get(i).chat); // 상태메시지 전달
                intent.putExtra("Check", "myP"); //[체크]나의 프로필
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Main_Chat_ProfileActivity.class);

                intent.putExtra("User_Name", list.get(i).name); //이름 전달
                intent.putExtra("User_Profile", list.get(i).url); // 프로필 사진 전달
                intent.putExtra("User_Chat", list.get(i).chat); // 상태메시지 전달
                intent.putExtra("User_ID", data.userVector.elementAt(i + 1).id); //id 전달
                intent.putExtra("Check", "frP"); //[체크]친구 프로필

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
            }
            profile = (ImageView) v.findViewById(R.id.profile_image);
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView chat = (TextView) v.findViewById(R.id.chat);


            // profile.setImageResource(getItem(pos).profile);
            name.setText(getItem(pos).name);
            chat.setText(getItem(pos).chat);

            imageLoader.displayImage(list.get(position).url, profile, options); //이미지 처리
/*
            Uri.fromFile(file).toString();
            Uri.
            Uri.parse(list.get(position).url)*/
            //imageLoader.displayImage(   Uri.parse(list.get(position).url),profile,options);

            return v;
        }
    }

    public String getBase64String(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
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
