package com.tzwm.deadalarm;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Created by tzwm on 10/6/13.
 */
public class MediaController {
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private String mFileName;

    public MediaController() {
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
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("DeadAlarm Playing", "prepare() failed");
        }

    }

}
