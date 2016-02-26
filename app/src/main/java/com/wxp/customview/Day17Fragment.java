package com.wxp.customview;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import views.Day17View;


public class Day17Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Day17Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Day1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Day17Fragment newInstance(String param1, String param2) {
        Day17Fragment fragment = new Day17Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_day17, container, false);
        Day17View day17View = (Day17View) view.findViewById(R.id.id_day17view);
        Day17View day17View_1 = (Day17View) view.findViewById(R.id.id_day17view_1);
        Day17View day17View_2 = (Day17View) view.findViewById(R.id.id_day17view_2);
        List<Day17View.InnerItem> mData = new ArrayList<>();
        mData.add(new Day17View.InnerItem(0, "清洁", "http://jd.com", "http://img11.360buyimg.com/n4/jfs/t1936/162/1274013976/110854/f2b30c64/5653db54N6f7bef81.jpg"));
        mData.add(new Day17View.InnerItem(1, "美妆", "http://jd.com", "http://img11.360buyimg.com/n4/jfs/t1936/162/1274013976/110854/f2b30c64/5653db54N6f7bef81.jpg"));
        mData.add(new Day17View.InnerItem(2, "母婴", "http://jd.com", "http://img11.360buyimg.com/n4/jfs/t1936/162/1274013976/110854/f2b30c64/5653db54N6f7bef81.jpg"));
        mData.add(new Day17View.InnerItem(3, "粮油", "http://jd.com", "http://img11.360buyimg.com/n4/jfs/t1936/162/1274013976/110854/f2b30c64/5653db54N6f7bef81.jpg"));
        mData.add(new Day17View.InnerItem(4, "饮料", "http://jd.com", "http://img11.360buyimg.com/n4/jfs/t1936/162/1274013976/110854/f2b30c64/5653db54N6f7bef81.jpg"));
        day17View.setAdapter(new Day17View.HotCategoryAdapter(getActivity(), mData));
        day17View.bindLayout();

        day17View_1.setAdapter(new Day17View.HotCategoryAdapter(getActivity(), mData));
        day17View_1.bindLayout();

        day17View_2.setAdapter(new Day17View.HotCategoryAdapter(getActivity(), mData.subList(0, 1)));
        day17View_2.bindLayout();

        return view;
    }


}
