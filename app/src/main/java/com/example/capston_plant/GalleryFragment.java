package com.example.capston_plant;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.ArrayList;


public class GalleryFragment extends Fragment {

    private View view;
    private ImageButton btn_goBack;
    private GridView gridView;

    SingerAdapter singerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        gridView = view.findViewById(R.id.GridView);
        singerAdapter = new SingerAdapter();
        singerAdapter.addItem(new SingerItem(R.drawable.gallery_image_01));
        singerAdapter.addItem(new SingerItem(R.drawable.gallery_image_02));
        singerAdapter.addItem(new SingerItem(R.drawable.gallery_image_03));
        singerAdapter.addItem(new SingerItem(R.drawable.gallery_image_04));

        gridView.setAdapter(singerAdapter);


        btn_goBack = view.findViewById(R.id.btn_goBack);
        btn_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                transaction.replace(R.id.main_layout, homeFragment);
                transaction.commit();
            }
        });


        return view;

    }

    class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SingerItem singerItem){
            items.add(singerItem);
        }

        @Override
        public SingerItem getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            SingerViewer singerViewer = new SingerViewer(getActivity());
            singerViewer.setItem(items.get(i));
            return singerViewer;
        }
    }



}