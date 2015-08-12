package tech.alexio.textviewbug;

import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

/**
 * Created by alexio on 8/12/15.
 */
public class TestClickableSpan extends ClickableSpan {

    private static final String TAG = TestClickableSpan.class.toString();

    private String url;

    public TestClickableSpan(String url) {
        this.url = url;
    }

    @Override
    public void onClick(View widget) {
        Log.e(TAG, "Url onClick: " + url);
    }
}
