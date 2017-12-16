package app.wytalk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Main_PlusFragment extends Fragment {

    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main__plus, container, false);

        button = (Button)view.findViewById(R.id.button);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Service 끝",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),MyService.class);
                getContext().stopService(intent);

            }
        });

        return inflater.inflate(R.layout.fragment_main__plus, container, false);
    }

   }
