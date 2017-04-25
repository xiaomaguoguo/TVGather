package utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import com.bftv.knothing.firsttv.R;

/**
 * Created by MaZhihua on 2017/4/25.
 * 声音池工具类,目前只做了单个处理，后期有需要多个支持再扩展
 */
public class SoundPoolUtils {

    private SoundPool mSoundPool;

    boolean isAlreadyLoaded = false;

    boolean isAlreadyStop = false;

    private Context mContext ;

    int soundId = 0 ;

    private static SoundPoolUtils mInstance;

    private SoundPoolUtils(){}

    public SoundPoolUtils(Context mContext){
        this.mContext = mContext;
    }



    public static SoundPoolUtils getInstance(Context mContext) {
        if(mInstance == null){
            synchronized (SoundPoolUtils.class){
                if(mInstance == null){
                    mInstance = new SoundPoolUtils(mContext);
                }
            }
        }
        return mInstance;
    }

    public void initSoundPool() {
        if(mSoundPool == null){
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频数量
            builder.setMaxStreams(2);
            //AudioAttributes是一个封装音频各种属性的方法
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适的属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            //加载一个AudioAttributes
            builder.setAudioAttributes(attrBuilder.build());
            mSoundPool = builder.build();
        }

    }

    public void play(){

        if(mSoundPool == null){
            initSoundPool();
        }

        if(isAlreadyLoaded){
            if(isAlreadyStop){
                soundId = mSoundPool.load(mContext, R.raw.remind,1);
            }else{
                mSoundPool.stop(soundId);
                soundId = mSoundPool.load(mContext,R.raw.remind,1);
            }
        }else{
            soundId = mSoundPool.load(mContext,R.raw.remind,1);
        }
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                isAlreadyLoaded = true;
                isAlreadyStop = false;
                mSoundPool.play(soundId,1,1,0,10,1);
            }
        });
    }

    public void stop(){
        if(mSoundPool != null){
            mSoundPool.stop(soundId);
            isAlreadyStop = true;
            isAlreadyLoaded = false;
        }
    }

    public void release(){
        if(mSoundPool != null){
            mSoundPool.stop(soundId);
            mSoundPool.unload(soundId);
            mSoundPool.release();
            mSoundPool = null;
            isAlreadyLoaded = false;
            isAlreadyStop = true;
            mContext = null;
            mInstance = null;
        }
    }


}
