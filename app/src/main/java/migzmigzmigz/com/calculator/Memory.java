package migzmigzmigz.com.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public final class Memory {

    private static final String TAG = Memory.class.getSimpleName();
    private static final String PREFERENCE_NAME = "calculator_preferences";
    private static final String PREFERENCE_KEY = "calculator_key";

    private SharedPreferences mPreferences;
    private Set<String> mCache;
    private int mCurrentIndex = 0;

    private static Memory INSTANCE;

    public static Memory getInstance (Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Memory(context);
        }

        return INSTANCE;
    }

    private Memory (Context context) {
        mPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        mCache = mPreferences.getStringSet(PREFERENCE_KEY, null);

        if (mCache == null) {
            mCache = new HashSet<>();
        }
    }

    public void clear () {
        // no need to check if empty
        mCache.clear();
        mPreferences.edit().remove(PREFERENCE_KEY).apply();
        Log.i(TAG, "Cleared Memory");
    }

    public void store (String string) {
        if (string != null && !mCache.contains(string)) {
            mCache.add(string);
            mPreferences.edit().putStringSet(PREFERENCE_KEY, mCache).apply();
            Log.i(TAG, "Stored " + string + " to memory : " + mCache);
        }
    }

    public String recall () {
        if (mCache.isEmpty()) {
            Log.i(TAG, "There's nothing to recall. Returned null");
            return null;
        }

        String result = (String) mCache.toArray()[mCurrentIndex++];

        if (mCurrentIndex == mCache.size()) {
            mCurrentIndex = 0;
        }

        Log.i(TAG, "Recalled " + result + " : " + mCache);

        return result;
    }

}
