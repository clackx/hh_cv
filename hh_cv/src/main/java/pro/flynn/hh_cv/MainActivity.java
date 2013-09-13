package pro.flynn.hh_cv;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends FragmentActivity {

    private EditText name, birthdate, position, salary, phonenum, emailaddr;
    private Spinner sex;
    int RequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Change activity title
        setTitle("hh_cv Activity-1");

        // Define vars with components
        name = (EditText) findViewById(R.id.et_name);
        birthdate = (EditText) findViewById(R.id.et_birthdate);
        position = (EditText) findViewById(R.id.et_position);
        salary = (EditText) findViewById(R.id.et_salary);
        phonenum = (EditText) findViewById(R.id.et_phonenum);
        emailaddr = (EditText) findViewById(R.id.et_emailaddr);
        sex = (Spinner)findViewById(R.id.spinner);

        // Set click listener to birthdate edittext field to show dialog with datepicker
        birthdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(birthdate);
                newFragment.show(getSupportFragmentManager(), "set date");
            }
        });

    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public EditText et_date;
        public DatePickerFragment(EditText edit_text) {
            et_date = edit_text;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            int year, month, day;

            // Get text from date field and split it by dots
            String text = et_date.getText().toString();
            String[] str = text.split("\\.");

            // If split to 3 parts not success then set defaults
            if (str.length < 3) {
                year = 1985;
                month = 1;
                day = 1;
            }
            // else parse strings to integer vars
            else {
                year = Integer.parseInt(str[2]);
                month = Integer.parseInt(str[1]);
                day = Integer.parseInt(str[0]);
            }

            // Create dialog with set of vars
            return new DatePickerDialog(getActivity(), this, year, month - 1, day);
        }

        // Change edittext field when date is set
        public void onDateSet(DatePicker view, int year, int month, int day) {
            et_date.setText(String.format("%02d", day) + "." +
                    String.format("%02d", month + 1) + "." + String.valueOf(year));

        }
    }


    public void goActivity2(View v){

        // Link intent with second activity
        Intent intent = new Intent(this, SecondActivity.class);

        // Create plain text from values
        String cleantext = String.format("Соискатель: %s\nДата рождения: %s\nПол: %s\nДолжность: %s\nЗарплата: %s",
                name.getText().toString(), birthdate.getText().toString(),
                sex.getSelectedItem().toString(),
                position.getText().toString(), salary.getText().toString());

        // Create text for autolink
        String linktext = String.format("Телефон: %s\nEmail: %s",
                phonenum.getText().toString(), emailaddr.getText().toString());

        // Put values to intent
        intent.putExtra("cleandata", cleantext);
        intent.putExtra("linkdata", linktext);

        // Start second activity with the possibility of returning the result
        startActivityForResult(intent, RequestCode);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If second activity returned OK
        if (resultCode == Activity.RESULT_OK) {

            // Get text from second activity
            String text = data.getStringExtra("text");

            // Create alert dialog with title, text from second activity and OK button
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle("Получен ответ");
            ad.setMessage(text);
            ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            ad.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                // Create "about" alert dialog
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle("О Программе");
                ad.setMessage("Спасибо, headhunter!");
                ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                ad.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
