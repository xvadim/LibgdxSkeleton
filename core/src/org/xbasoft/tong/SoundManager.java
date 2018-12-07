package org.xbasoft.tong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by vadimkhohlov on 10/7/17.
 */

public class SoundManager {

    private static Music mMusic = null;

    public static int SOUND_CLICK = 0;

    private static String sSoundFiles[] = {"mfx/click.ogg"
    };

    private boolean mIsSoundEnabled = false;
    private Sound mSounds[] = new Sound[sSoundFiles.length];

    public static void enableMusic(boolean isMusicEnabled) {
        if (isMusicEnabled) {
            if (mMusic != null) {
                mMusic.dispose();
                mMusic = null;
            }
            if (mMusic == null) {
                mMusic = Gdx.audio.newMusic(Gdx.files.internal("mfx/music.mid"));
                mMusic.setLooping(true);
            }
            if (!mMusic.isPlaying()) {
                mMusic.play();
            }
        } else {
            if (mMusic != null) {
                mMusic.stop();
                mMusic.dispose();
                mMusic = null;
            }
        }
    }

    public static void resumeMusic(boolean isResume) {
        if (mMusic != null) {
            if (isResume) {
                mMusic.play();
            } else {
                mMusic.pause();
            }
        }
    }

    public void enableSound(boolean isEnabled) {
        if (isEnabled == mIsSoundEnabled) {
            return;
        }
        mIsSoundEnabled = isEnabled;
        if (mIsSoundEnabled) {
            for(int i = 0; i < sSoundFiles.length; i++) {
                mSounds[i] = Gdx.audio.newSound(Gdx.files.internal(sSoundFiles[i]));
            }
        } else {
            if (mSounds[0] == null) {
                return;
            }
            for(int i = 0; i < sSoundFiles.length; i++) {
                mSounds[i].dispose();
            }
        }
    }

    public void play(int pSoundId) {
        if (mIsSoundEnabled) {
            mSounds[pSoundId].play();
        }
    }
}
