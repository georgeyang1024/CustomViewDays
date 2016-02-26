package com.wxp.customview;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utils.DPU;


public class Day16Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Day16Fragment() {
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
    public static Day16Fragment newInstance(String param1, String param2) {
        Day16Fragment fragment = new Day16Fragment();
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

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final TextView textView;


        Rect outRect = new Rect();

        getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
        final int contentHeight = outRect.height();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_day16, container, false);
        textView = (TextView) view.findViewById(R.id.id_16_tv);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {


                Rect rect = new Rect();
                view.getWindowVisibleDisplayFrame(rect);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DPU.dp(60));
                layoutParams.setMargins(0, 0, 0, contentHeight - rect.height());
                textView.setLayoutParams(layoutParams);

            }
        });
        return view;
    }


}
