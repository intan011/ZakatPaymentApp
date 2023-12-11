package com.example.zakatpayment;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar myToolbar;
    Button btnCalculate, btnReset;
    EditText etWeight, etValue;
    TextView totalGoldValueTextView;
    TextView zakatPayableTextView;

    TextView totalZakatTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(this);

        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);

        etWeight = findViewById(R.id.etWeight);
        etValue = findViewById(R.id.etValue);

        totalGoldValueTextView = findViewById(R.id.tvTotGold);
        zakatPayableTextView = findViewById(R.id.tvZkatPayable);
        totalZakatTextView = findViewById(R.id.tvTotZakat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_share){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Please use my application - https://github.com/intan011/ZakatPaymentApp.git");
            startActivity(Intent.createChooser(shareIntent, null));
            return true;
        }else if (item.getItemId() == R.id.item_about){
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
        }


        return false;
    }

    public void calculateZakat() {
        EditText weightInput = findViewById(R.id.etWeight);
        EditText valueInput = findViewById(R.id.etValue);
        Spinner typeSpinner = findViewById(R.id.spType);

        double goldWeight = Double.parseDouble(weightInput.getText().toString());
        double goldValue = Double.parseDouble(valueInput.getText().toString());
        String type = typeSpinner.getSelectedItem().toString();

        double totalGoldValue = ZakatCalculator.calculateTotalGoldValue(goldWeight, goldValue);
        double zakatPayable = ZakatCalculator.calculateZakatPayable(goldWeight, goldValue, type);

        // Calculate total zakat based on zakat payable
        double totalZakat = ZakatCalculator.calculateTotalZakat(goldWeight, goldValue, type);

        // Format the values to display with 2 decimal places
        String formattedTotalGoldValue = String.format("Total Gold Value: RM %.2f", totalGoldValue);
        String formattedZakatPayable = String.format("Zakat Payable: RM %.2f", zakatPayable);
        String formattedTotalZakat = String.format("Total Zakat: RM %.2f", totalZakat);

        // Update TextViews with the formatted values
        totalGoldValueTextView.setText(formattedTotalGoldValue);
        zakatPayableTextView.setText(formattedZakatPayable);
        totalZakatTextView.setText(formattedTotalZakat);

    }


    @Override
    public void onClick(View v) {
        if (v == btnCalculate){
            try{
                calculateZakat();
            }catch (NumberFormatException nfe){
                Toast.makeText(this,"Please enter valid numbers for gold weight and value", Toast.LENGTH_SHORT).show();
            }

        }else if (v == btnReset){
            etWeight.getText().clear();
            etValue.getText().clear();
            totalGoldValueTextView.setText("Total Gold Value: RM 0");
            zakatPayableTextView.setText("Zakat Payable: RM 0");
            totalZakatTextView.setText("Total Zakat: RM 0");
        }

    }
}