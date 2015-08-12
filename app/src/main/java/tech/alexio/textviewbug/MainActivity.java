package tech.alexio.textviewbug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String STARBUCKS_URL = "http://www.starbucks.com/";
    private static final String JAMBA_URL = "http://www.jambajuice.com/";
    private TextView wrongSpanClickView;
    private TextView doubleClickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wrongSpanClickView = (TextView) findViewById(R.id.wrongClick);
        doubleClickView = (TextView) findViewById(R.id.doubleClick);

        setWrongSpanClickView();
        setDoubleClickView();
    }

    private void setWrongSpanClickView() {
        String coffee = "Coffee";
        String smoothie = "Smoothie";
        String question = "Get a " + coffee + " or a " +smoothie + "?";
        SpannableStringBuilder builder =
                new SpannableStringBuilder(question);

        int coffeeStart = question.indexOf(coffee);
        int coffeeEnd = coffeeStart+coffee.length();
        builder.setSpan(new TestClickableSpan(STARBUCKS_URL),
                coffeeStart, coffeeEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int smoothieStart = question.indexOf(smoothie);
        int smoothieEnd = smoothieStart + smoothie.length();
        builder.setSpan(new TestClickableSpan(JAMBA_URL),
                smoothieStart, smoothieEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        wrongSpanClickView.setText(builder, TextView.BufferType.SPANNABLE);
    }

    private void setDoubleClickView() {
        String juice = "Smoothie!!";
        SpannableStringBuilder builder = new SpannableStringBuilder(juice);
        builder.setSpan(new TestClickableSpan(JAMBA_URL),
                0, juice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        doubleClickView.setMovementMethod(LinkMovementMethod.getInstance());
        doubleClickView.setText(builder, TextView.BufferType.SPANNABLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
