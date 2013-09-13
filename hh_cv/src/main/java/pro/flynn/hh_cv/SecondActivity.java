package pro.flynn.hh_cv;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Set text recieved from main activity
        TextView clean = (TextView) findViewById(R.id.plaintext);
        clean.setText(getIntent().getStringExtra("cleandata"));

        TextView links = (TextView) findViewById(R.id.linkedtext);
        links.setText(getIntent().getStringExtra("linkdata"));

    }


    public void goActivity1(View v) {
        EditText et = (EditText) findViewById(R.id.et_answer);

        // Call main activity with OK result and text from answer field
        Intent output = new Intent(this, MainActivity.class);
        output.putExtra("text", et.getText().toString());
        setResult(RESULT_OK, output);
        finish();
    }

}