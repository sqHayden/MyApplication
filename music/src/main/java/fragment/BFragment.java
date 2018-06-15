package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import shaoqi.myapplication.R;

/**
 * Created by hayden on 18-4-17.
 */

public class BFragment extends Fragment {

    private Button openC;
    private CallBack callBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof CallBack){
            callBack = (CallBack)getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bview,container,false);
        openC = view.findViewById(R.id.open_cFragment);
        openC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.callFromB();
            }
        });
        return view;
    }
}
