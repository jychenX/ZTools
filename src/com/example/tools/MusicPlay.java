package com.example.tools;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class MusicPlay {

	private SoundPool soundPool;  
    private HashMap<Integer, Integer> hashMap;  
    private int currStreamId;  
    private Context context;

    public MusicPlay(Context context, int id) {
    	this.context = context;
        initSoundPool(id);
    }  
  
    @SuppressLint("UseSparseArrays")
	@SuppressWarnings("deprecation")
	private void initSoundPool(int id) {  
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);  
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {

			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				play(1, 0);
			}
        });
        hashMap = new HashMap<Integer, Integer>();  
        hashMap.put(1, soundPool.load(context, id, 1));  
    }  
      
    private void play(int sound, int loop) {  
    	AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE); 
    	float volume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC); 
        currStreamId = soundPool.play(hashMap.get(sound), volume, volume, 1, loop, 1.0f);  
        System.out.println(currStreamId);  
    }  
}
