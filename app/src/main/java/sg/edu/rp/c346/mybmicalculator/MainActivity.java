package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvOutcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvOutcome = findViewById(R.id.textViewOutcome);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etHeight.getText().toString().trim().length() !=0 && etWeight.getText().toString().trim().length() !=0){
                    Double weight = Double.parseDouble(etWeight.getText().toString());
                    Double height = Double.parseDouble(etHeight.getText().toString());

                    Double bmi = weight / (height*height);

                    String outcome = "";
                    if (bmi < 18.5){
                        outcome += "underweight";
                    }
                    else if (bmi >= 18.5 && bmi <= 24.9){
                        outcome += "normal";
                    }
                    else if (bmi >= 25 && bmi <= 29.9){
                        outcome += "overweight";
                    }
                    else{
                        outcome += "obese";
                    }

                    Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                    String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                            (now.get(Calendar.MONTH)+1) + "/" +
                            now.get(Calendar.YEAR) + " " +
                            now.get(Calendar.HOUR_OF_DAY) + ":" +
                            now.get(Calendar.MINUTE);

                    tvDate.setText("Last Calculated Date: "+ datetime);
                    tvBMI.setText("Last Calculated BMI: "+ String.format("%.3f", bmi));
                    tvOutcome.setText("You are "+ outcome);
                    etWeight.setText("");
                    etHeight.setText("");
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_SHORT);
                    toast.show();

                }


            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvDate.setText("Last Calculated Date: ");
                tvBMI.setText("Last Calculated BMI: ");
                tvOutcome.setText("");

            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();

        // Get the value from the textView and store it in a variable
        String date = tvDate.getText().toString();
        String bmi = tvBMI.getText().toString();
        String outcome = tvOutcome.getText().toString();
        // Obtain an instance of the SharedPreference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // Obtain an instance of the SharedPrefences Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();
        // Add the Key-Value pair
        prefEdit.putString("date", date);
        prefEdit.putString("bmi", bmi);
        prefEdit.putString("outcome", outcome);
        // Call commit() method to save the changes into SharedPreferences
        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // Retrieve the saved data from the SharedPreferences object
        String date = prefs.getString("date", "");
        String bmi = prefs.getString("bmi", "");
        String outcome = prefs.getString("outcome", "");
        // Update the UI element with the value
        tvDate.setText(date);
        tvBMI.setText(bmi);
        tvOutcome.setText(outcome);
    }
}
