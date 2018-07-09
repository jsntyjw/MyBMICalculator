package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvResult;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        tvDate = findViewById(R.id.tvDate);
        tvBMI = findViewById(R.id.tvBMI);
        tvResult = findViewById(R.id.tvResult);






        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                float BMI = weight/(height * height);

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                String strBMI = String.valueOf(BMI);

                if(BMI>=30){
                    tvResult.setText("You are obese");
                }
                else if(BMI>=25 && BMI<29.9){
                    tvResult.setText("You are overweight");
                }
                else if(BMI>=18.5 && BMI<24.9){
                    tvResult.setText("Your BMI is normal");
                }
                else if(BMI<18.5){
                    tvResult.setText("You are underweight");
                }

                tvBMI.setText(strBMI);
                tvDate.setText(datetime);
            }
        });

        final SharedPreferences aaa = PreferenceManager.getDefaultSharedPreferences(this);


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etWeight.setText(null);
                etHeight.setText(null);
                tvDate.setText(null);
                tvBMI.setText(null);
                tvResult.setText(null);


                aaa.edit().clear().commit();

            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        if(!etWeight.getText().toString().isEmpty() && !etHeight.getText().toString().isEmpty()){

        float weight = Float.parseFloat(etWeight.getText().toString());
        float height = Float.parseFloat(etHeight.getText().toString());
        String date = tvDate.getText().toString();
        String bmi = tvBMI.getText().toString();
        String result = tvResult.getText().toString();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putFloat("weight",weight);
        prefEdit.putFloat("height",height);
        prefEdit.putString("date",date);
        prefEdit.putString("bmi",bmi);
        prefEdit.putString("result",result);

        prefEdit.commit();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        float weight = prefs.getFloat("weight",0.0f);
        float height = prefs.getFloat("height",1f);
        String date = prefs.getString("date","");
        //String bmi = prefs.getString("bmi","");
        float bmiValue = weight/(height * height);
        String strbmiValue = String.valueOf(bmiValue);
        String result = prefs.getString("result","");


        tvDate.setText(date);
        if(bmiValue == 0){
            tvBMI.setText(null);
        }
        else {
            tvBMI.setText(strbmiValue);
        }
        tvResult.setText(result);
    }
}
