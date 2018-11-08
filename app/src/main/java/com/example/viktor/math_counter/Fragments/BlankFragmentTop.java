package com.example.viktor.math_counter.Fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.viktor.math_counter.Activity.MainScreenActivity;
import com.example.viktor.math_counter.R;
import com.example.viktor.math_counter.Util.TopScore;
import com.example.viktor.math_counter.Util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.example.viktor.math_counter.Activity.MainScreenActivity.MainConstraintLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 * Use the {@link BlankFragmentTop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentTop extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public TextView textView1;
    public TextView textView2;
    public TextView textView3;
    public TextView textView4;
    public TextView textView5;
    public TextView textTitle;
    public FrameLayout frameLayout;

    private List<TopScore> topScoreList;


    Button btnClose;


    private static final String KEY_SET = "key_set";
    private static final String KEY_NANE = "key_name";////////

    TextView tv, tv2;
    ArrayList<String> list;

    String score;//////

    String[] a = new String[5];

    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public BlankFragmentTop() {
        // Required empty public constructor
    }

    public static BlankFragmentTop newInstance(String score) {/////add String name)
        BlankFragmentTop fragment = new BlankFragmentTop();
        Bundle args = new Bundle();
        args.putString(KEY_NANE, score);///////////
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            score = getArguments().getString(KEY_NANE);///////


        }
    }

   // @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        try {
//            readFromJson();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



//        topScoreList.sort(new Comparator<TopScore>() {
//            @Override
//            public int compare(TopScore o1, TopScore o2) {
//                return o2.getScore()- o1.getScore();
//            }
//        });


        final View v = inflater.inflate(R.layout.fragment_blank_fragment_top, container, false);
        textView1 = v.findViewById(R.id.tv_top1);
        textView2 = v.findViewById(R.id.tv_top2);
        textView3 = v.findViewById(R.id.tv_top3);
        textView4 = v.findViewById(R.id.tv_top4);
        textView5 = v.findViewById(R.id.tv_top5);
        btnClose = v.findViewById(R.id.btnClose);
        textTitle = v.findViewById(R.id.tvTitle);
        frameLayout = v.findViewById(R.id.fl_fragment);
        frameLayout.setBackgroundResource(R.drawable.fonscore);
        readMapGson(score);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.setVisibility(View.INVISIBLE);


                for (int i = 0; i < MainConstraintLayout.getChildCount(); i++) {
                    MainConstraintLayout.getChildAt(i).setEnabled(true);
                }


            }
        });

//        for (int i = 0; i < topScoreList.size(); i++) {
//
//            View view = frameLayout.getChildAt(i);
//
//            if(view instanceof TextView)   {
//
//                ((TextView)view).setText("Name: " + topScoreList.get(i).getName() + "; Score: " +
//                        topScoreList.get(i).getScore());
//
//            }
//
//        }



        return v;
    }


    private void readMapGson(String score) {



     List<TopScore> listGSON = new Gson().fromJson(score, new TypeToken<List<TopScore>>() {
        }.getType());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listGSON.sort(new Comparator<TopScore>() {
                @Override
                public int compare(TopScore o1, TopScore o2) {
                    return o2.getScore()-o1.getScore();
                }
            });
        }else {//Bubble method //Build.VERSION.SDK_INT < Build.VERSION_CODES.N
            for (int i = 0; i<listGSON.size(); i++){

                for (int j = 0; j<listGSON.size()-1; j++) {

                    if (listGSON.get(j).getScore()<listGSON.get(j+1).getScore()){

                        TopScore tmp = listGSON.get(j);
                        TopScore tmp1 = listGSON.get(j+1);
                        listGSON.set(j+1, tmp);
                        listGSON.set(j, tmp1);
                    }
                }
            }
        }
        for (int i = 0; i < listGSON.size(); i++) {

            View view = frameLayout.getChildAt(i);

            if (view instanceof TextView) {

                ((TextView) view).setText("Name: " + listGSON.get(i).getName() + "; Score: " +
                        listGSON.get(i).getScore());

            }


        }
    }

    private void readFromJson() throws IOException {

        topScoreList = new ArrayList<>();
        TopScore topScore = null;

        StringReader stringReader = new StringReader(score);
        JsonReader reader = new JsonReader(stringReader);

        reader.beginArray();
        while (reader.hasNext()){

            topScore = new TopScore();

            readObject(reader,topScore);

            topScoreList.add(topScore);
        }
        reader.endArray();


    }

    private void readObject(JsonReader reader, TopScore topScore) throws IOException {

        reader.beginObject();
        while(reader.hasNext()) {

            String keyName = reader.nextName();

            if (keyName.equals("name")) {


                String currentName = reader.nextString();
                topScore.setName(currentName);


            } else if (keyName.equals("score")) {

                int currentScore = reader.nextInt();

                topScore.setScore(currentScore);

            }

        }

        reader.endObject();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // if (context instanceof OnFragmentInteractionListener) {
        //mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}


