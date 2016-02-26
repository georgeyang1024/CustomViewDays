package com.wxp.customview;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import views.Day14View;
import views.Day14_TagAdapter;
import views.Day6View;


public class Day14Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Day1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Day14Fragment newInstance(String param1, String param2) {
        Day14Fragment fragment = new Day14Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Day14Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day14, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String[] data = new String[]{"111", "222", "3333", "222", "3333", "222", "3333", "222", "3333", "222", "3333", "222", "3333", "222", "3333", "222", "3333", "222", "3333", "222", "3333", "222", "3333"};

        final Day14View day14View = (Day14View) view.findViewById(R.id.id_14_view);
        day14View.setAdapter(new Day14_TagAdapter<String>(data) {
            @Override
            public View getView(Day6View parent, int pos, String o) {
                TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.tv, day14View, false);
                textView.setText(o);
                return textView;
            }
        });
        day14View.setOnTagClickListener(new Day14View.OnTagClickListener() {

            @Override
            public boolean clickTag(View parent, int pos, View tagView) {
                //Toast.makeText(getActivity(), "HEHE : " + data[pos], Toast.LENGTH_SHORT).show();
                showToast(data[pos]);
                return true;
            }
        });
    }

    private Toast mToast;
    public void showToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT);
        }
        mToast.setText(content);
        mToast.show();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
