package com.example.wellxiang.falldetecion;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class AlertDialogFragment extends DialogFragment {
    private TextView textView;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Timer timer;
    private Handler handler;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        textView = new TextView(getContext());
        builder = new AlertDialog.Builder(getActivity())
                .setTitle("Fall Alert")
                .setView(textView)
                .setMessage("Detect Fall, Send Alert?")
                .setIcon(R.drawable.ic_warning)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        alertDialog = builder.create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.setCanceledOnTouchOutside(false);
        countDown();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what > 0){
                    Log.v("time", msg.what + "");
                    //动态显示倒计时
                    textView.setText("                     " + msg.what + "seconds Alert will be send");
                }else{
                    //倒计时结束自动关闭
                    if(alertDialog != null){
                        alertDialog.dismiss();
                        Log.d("Shawn","alertDialog.dismiss()");
                    }
                    timer.cancel();
                    Log.d("Shawn", "timer.cancel()");
                }
            }
        };
        return alertDialog;
    }

    private void countDown(){

        Log.d("Shawn", "CountDown()");
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int countTime = 8;
            @Override
            public void run() {
                if (countTime > 0){
                    countTime --;
                }
                Message msg = handler.obtainMessage();
                msg.what = countTime;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(timerTask, 100, 1000);
    }
}
