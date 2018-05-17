package com.framgia.thanghn.scmusic.screen.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.thanghn.scmusic.R;
import com.framgia.thanghn.scmusic.screen.BaseFragment;

/**
 * Created by thang on 5/12/2018.
 */

public class MenuFragment extends BaseFragment implements View.OnClickListener {
    private static final String MY_EMAIL = "thanghai.tb123@gmail.com";
    private static final String SUBJECT = "feed back ScMusic";
    private static final String SEND = "Send email";
    private static final String EMAIL = "mailto:";
    private TextView mTextViewFeedBack;
    private TextView mTextViewSetting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mTextViewFeedBack = getView().findViewById(R.id.text_feedback);
        mTextViewSetting = getView().findViewById(R.id.text_setting);
        mTextViewSetting.setOnClickListener(this);
        mTextViewFeedBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_feedback:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(EMAIL + MY_EMAIL));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                startActivity(Intent.createChooser(emailIntent, SEND));
                break;
            case R.id.text_setting:
                break;
        }
    }
}
