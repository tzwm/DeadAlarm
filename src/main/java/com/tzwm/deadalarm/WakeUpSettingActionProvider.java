package com.tzwm.deadalarm;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by tzwm on 10/8/13.
 */
public class WakeUpSettingActionProvider extends ActionProvider implements View.OnClickListener {
    /**
     * Creates a new instance. ActionProvider classes should always implement a
     * constructor that takes a single Context parameter for inflating from menu XML.
     *
     * @param context Context for accessing resources.
     */

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private PopupWindow mPopWindow;

    public WakeUpSettingActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        mLayoutInflater = LayoutInflater.from(mContext);
        View rootView = mLayoutInflater.inflate(R.layout.blink_action_provider_setting, null);
        ImageView menuBlink = (ImageView) rootView
                .findViewById(R.id.menu_setting_blink);
        menuBlink.setBackgroundResource(R.drawable.spanner);
        menuBlink.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
                R.layout.popupwindow_list_setting, null, true);
        mPopWindow = new PopupWindow(menuView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        mPopWindow.setOutsideTouchable(true);// 设置触摸外面时消失
        mPopWindow.setAnimationStyle(android.R.style.Animation_Dialog);// 设置动画效果
        mPopWindow.showAsDropDown(view, -140, 20);// 显示位置在锚点view的左边底部

        TextView textView = (TextView)menuView.findViewById(R.id.nomal_mode);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmReceiver.wakeupWay = AlarmReceiver.WAKEUP_NORMAL;
            }
        });
        textView = (TextView)menuView.findViewById(R.id.sound_reproduce);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmReceiver.wakeupWay = AlarmReceiver.SOUND_REPRODUCE;
            }
        });
        textView = (TextView)menuView.findViewById(R.id.punch_the_ball);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmReceiver.wakeupWay = AlarmReceiver.PUNCH_THE_BALL;
            }
        });
    }
}
