package app.wytalk;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SJE on 2017-12-08.
 */
public class CustomAdapter extends BaseAdapter {

    public class ListContents {
        public String msg;
        public int type;

        ListContents(String _msg, int _type) {
            this.msg = _msg;
            this.type = _type;
        }
    }

    private ArrayList<ListContents> m_List;
    Data data;
    int chatNum;

    public CustomAdapter() {
        m_List = new ArrayList();
        data = new Data();
    }
    // 외부에서 아이템 추가 요청 시 사용

    public void add(String _msg, int _type) {
        m_List.add(new ListContents(_msg, _type));
    }
    public void add2(String _msg, int _type, int _chatNum) {
        m_List.add(new ListContents(_msg, _type));
        chatNum = _chatNum;

    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        m_List.remove(_position);
    }

    @Override
    public int getCount() {
        return m_List.size();
    }

    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        TextView text = null;
        CustomHolder holder = null;
        LinearLayout layout = null;
        View viewRight = null;
        View viewLeft = null;

        FrameLayout frameLayout = null;
        ImageView imageView = null;


        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if (convertView == null) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatitem, parent, false);

            layout = (LinearLayout) convertView.findViewById(R.id.layout);
            text = (TextView) convertView.findViewById(R.id.text);
            viewRight = (View) convertView.findViewById(R.id.imageViewright);
            viewLeft = (View) convertView.findViewById(R.id.imageViewleft);

            frameLayout = (FrameLayout) convertView.findViewById(R.id.frameLayout);
            imageView = (ImageView) convertView.findViewById(R.id.imageView);


            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.m_TextView = text;
            holder.layout = layout;
            holder.viewRight = viewRight;
            holder.viewLeft = viewLeft;

            holder.frameLayout = frameLayout;
            holder.imageView = imageView;

            convertView.setTag(holder);
        } else {
            holder = (CustomHolder) convertView.getTag();
            text = holder.m_TextView;
            layout = holder.layout;
            viewRight = holder.viewRight;
            viewLeft = holder.viewLeft;

            frameLayout = holder.frameLayout;
            imageView = holder.imageView;

        }


/***********************이모티콘******************************/
        if (m_List.get(position).msg.equals("[PIMO1]")) {
            imageView.setImageResource(R.drawable.imo3);
            System.out.println("checktest이모티콘 출력---------------");
            imageView.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);
        }

/***********************이미지****************************/
        else if(m_List.get(position).msg.equals("++bitmap")){

            imageView.setImageBitmap(
                    data.chatDatahash.get(chatNum).bitmapVector.elementAt(0)
            );
            System.out.println("checktest이미지 출력---------------");
            imageView.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);

        }
/***********************일반 텍스트*****************************/
        else {
            text.setText(m_List.get(position).msg);
            System.out.println("checktest텍스트 출력---------------");
            imageView.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);
        }


        if (m_List.get(position).type == 0) {
            frameLayout.setBackgroundResource(R.drawable.textlogo);
            layout.setGravity(Gravity.LEFT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        } else if (m_List.get(position).type == 1) {
            frameLayout.setBackgroundResource(R.drawable.textlogo);
            layout.setGravity(Gravity.RIGHT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        } else if (m_List.get(position).type == 2) {
            frameLayout.setBackgroundResource(R.drawable.textlogo);
            layout.setGravity(Gravity.CENTER);
            viewRight.setVisibility(View.VISIBLE);
            viewLeft.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    private class CustomHolder {
        TextView m_TextView;
        LinearLayout layout;

        FrameLayout frameLayout;
        ImageView imageView;

        View viewRight;
        View viewLeft;
    }
}