package com.tzwm.deadalarm;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.RingtoneManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by tzwm on 10/6/13.
 */
public class MediaController {
    private Context mContext;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private String mFileName;

    public MediaController(Context _mContext) {
        mContext = _mContext;

        mRecorder = null;
        mPlayer =null;

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/DeadTimer_notification.3gp";
    }

    public void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("DeadAlarm Recording", "prepare() failed");
        }

        mRecorder.start();
    }

    public void stopRecording() {
        if (mRecorder == null)
            return;

        try {
            mRecorder.stop();
            mRecorder.release();
        } catch (Exception e) {
            Log.e("DeadAlarm Recording", "stop() failed");
        }

        mRecorder = null;
    }

    public void startPlaying() {
        mPlayer = new MediaPlayer();

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        try {
            File f = new File(mFileName);
            if(!f.exists()){
                mPlayer.release();
                playNotification();
                return;
            }else
                mPlayer.setDataSource(mFileName);
        } catch (Exception e) {
            mPlayer.release();
            playNotification();
            return;
        }

        try {
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("DeadAlarm Playing", "prepare() failed");
        }

    }

    public void playNotification() {
        mPlayer = new MediaPlayer();

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
            }
        });
        try {
            mPlayer.setDataSource(mContext,
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
            mPlayer.prepare();
            mPlayer.setLooping(false);
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if(mPlayer == null)
            return;

        mPlayer.stop();
    }

    public void repeatNotification() {
        mPlayer = new MediaPlayer();

        try {
            mPlayer.setDataSource(mContext,
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
            mPlayer.prepare();
            mPlayer.setLooping(true);
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
