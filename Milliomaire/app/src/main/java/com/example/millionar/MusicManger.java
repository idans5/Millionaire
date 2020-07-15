package com.example.millionar;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicManger {
    private static MusicManger refrence = null;
    public static MediaPlayer player;

    public static MusicManger getInstance(){
        if(refrence == null){
            refrence = new MusicManger();
        }
        return refrence;
    }

    public void initalizeMediaPlayer(Context context, int musicId){
        player = MediaPlayer.create(context, musicId);
        player.setLooping(false); // Set looping
        player.setVolume(100, 100);

    }

    public void startPlaying(){
        player.start();
    }

    public void stopPlaying(){
        player.stop();
    }
}