package com.android.aks4125.rxtrigger.ui.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.aks4125.rxtrigger.App;
import com.android.aks4125.rxtrigger.R;
import com.android.aks4125.rxtrigger.trigger.Trigger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {


    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private Unbinder unbinder;
    private App mApp;

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mApp = (App) getActivity().getApplicationContext();
        tvTitle.setText("You are in Fragment 1");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fabIncrement, R.id.fabDecrement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fabIncrement:
                mApp
                        .reactiveTrigger()
                        .send(new Trigger.Increment());
                break;
            case R.id.fabDecrement:
                mApp
                        .reactiveTrigger()
                        .send(new Trigger.Decrement());
                break;
        }
    }
}
