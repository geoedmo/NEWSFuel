// This is a terribly written application to calculate M/Y NEWS Fuel Levels
// By: George Finamore

package com.example.geoedmo.morelearning;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    double[] fwdTankArray = new double[]{
            31.7,
            41.2,
            51.5,
            62.5,
            74.2,
            86.6,
            99.8,
            113.8,
            128.5,
            143.9,
            160.0,
            176.9,
            194.5,
            212.9,
            231.9,
            251.7,
            272.3,
            293.6,
            315.6,
            338.3,
            361.8,
            385.7,
            410.0,
            435.0,
            460.9,
            487.2,
            516.2,
            546.1,
            576.1,
            603.0,
            629.4,
            658.0,
            691.4,
            732.3,
            783.4,
            846.7,
            918.4,
            977.7,
            1084.9,
            1180.1,
            1283.8,
            1396.1,
            1517.4,
            1650.7,
            1795.9,
            1950.5,
            2111.8,
            2277.2,
            2444.1,
            2609.9,
            2774.3,
            2940.8,
            3109.4,
            3208.2,
            3453.1,
            3628.2,
            3805.5,
            3983.5
    };
    // END FWD Tank
    // LAZ Tank
    double[] lazTankArray = new double[]{
            334.8,
            419.1,
            503.5,
            673.1,
            759.2,
            843.5,
            929.1,
            1014.8,
            1100.7,
            1186.8,
            1273.2,
            1348.5,
            1389.9,
            1431.3,
            1472.7,
            1514.1,
            1555.5,
            1596.9,
            1638.3,
            1679.7,
            1721.1,
            1762.5,
            1803.9,
            1845.3,
            1886.7,
            1928.1,
            1969.5,
            2010.9,
            2052.3,
            2093.7,
            2135.1,
            2176.5,
            2217.9,
            2259.3,
            2300.7,
            2342.1,
            2383.5,
            2424.9,
            2466.3,
            2507.7,
            2549.1

    };
    // END LAZ Tank

    // AFT Tank
    double[] aftTankArray = new double[]{
            0, // Position 0, This is the Value at 0 Inches, in Gallons
            5,
            6,
            7,
            8,
            20,
            30,
            56,
            65,
            75,
            89,
            99,
            120
    };
    // END AFT Tank

    // PORT Tank
    double[] portTankArray = new double[]{
            0, // Position 0, This is the Value at 0 Inches, in Gallons
            5,
            6,
            7,
            8,
            20,
            30,
            56,
            65,
            75,
            89,
            99,
            120
    };
    // END PORT Tank

    // STBD Tank
    double[] stbdTankArray = new double[]{
            0, // Position 0, This is the Value at 0 Inches, in Gallons
            5,
            6,
            7,
            8,
            20,
            30,
            56,
            65,
            75,
            89,
            99,
            120
    };

    String selectedFromTank;
    String selectedToTank;

    Button calculate;

    //Calculator
    EditText fwdTank, lazTank, aftTank, portTank, stbdTank;
    TextView totalGal;

    //Transfer
    EditText transferFrom, currentAmountIn1, currentAmountIn2;
    TextView transferTo;
    Button calculate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("calculator");
        tabSpec.setContent(R.id.fuelCalc);
        tabSpec.setIndicator("Calculator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("transfer");
        tabSpec.setContent(R.id.transfer);
        tabSpec.setIndicator("Transfer");
        tabHost.addTab(tabSpec);

        // Calculator Items
        fwdTank = (EditText) findViewById(R.id.fwdTank);
        lazTank = (EditText) findViewById(R.id.lazTank);
        aftTank = (EditText) findViewById(R.id.aftTank);
        portTank = (EditText) findViewById(R.id.portWing);
        stbdTank = (EditText) findViewById(R.id.stbdWing);


        calculate = (Button) findViewById(R.id.calculate);
        calculate.setOnClickListener(this);

        // Transfer Items
        transferFrom = (EditText) findViewById(R.id.transferFrom);
        transferTo = (TextView) findViewById(R.id.transferTo);
        currentAmountIn1 = (EditText) findViewById(R.id.currentAmountIn1);
        currentAmountIn2 = (EditText) findViewById(R.id.currentAmountIn2);

        // Spinner
        Spinner spinner = (Spinner) findViewById(R.id.transferOut);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tank_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Spinner
        Spinner spinner2 = (Spinner) findViewById(R.id.transferIn);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.tank_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        // End Spinner

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFromTank = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedToTank = parent.getItemAtPosition(position).toString();
                // find selected to array here?
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calculate2 = (Button) findViewById(R.id.calculate2);
        calculate2.setOnClickListener(this);

    }

    public void onClick(View v) {
        // Arrays with fuel Data

        // FWD Tank

        if (v == calculate) {

            // END STBD Tank

            // So we can display individual tank levels
            TextView gallonsFwd;
            TextView gallonsLaz;
            TextView gallonsAft;
            TextView gallonsPort;
            TextView gallonsStbd;
            float stbdTankInt;
            float portTankInt;
            float fwdTankInt;
            float aftTankInt;
            float lazTankInt;

            // Declare Integers
            double totalGals;

            //check if the values are not empty, if they are set them to 0 so they dont get calculated
            //and crash the program.
            if (!(TextUtils.isEmpty(fwdTank.getText().toString()))) {
                //fwdTank.setText("0");
                fwdTankInt = Float.parseFloat(fwdTank.getText().toString());
            } else {
                fwdTankInt = 0;
            }
            if (fwdTankInt > fwdTankArray.length) {

                fwdTank.setError("Tank too High!");
                fwdTankInt = 0;
            }

            if (!(TextUtils.isEmpty(lazTank.getText().toString()))) {
                //fwdTank.setText("0");
                lazTankInt = Float.parseFloat(lazTank.getText().toString());
            } else {
                lazTankInt = 0;
            }
            if (lazTankInt > (lazTankArray.length)) {
                lazTank.setError("Tank too High!");
                lazTankInt = 0;
            }

            if (!(TextUtils.isEmpty(aftTank.getText().toString()))) {
                //fwdTank.setText("0");
                aftTankInt = Float.parseFloat(aftTank.getText().toString());
            } else {
                aftTankInt = 0;
            }
            if (aftTankInt > (aftTankArray.length)) {
                aftTank.setError("Tank too High!");
                aftTankInt = 0;
            }

            if (!(TextUtils.isEmpty(portTank.getText().toString()))) {
                //fwdTank.setText("0");
                portTankInt = Float.parseFloat(portTank.getText().toString());
            } else {
                portTankInt = 0;
            }
            if (portTankInt > (portTankArray.length)) {
                portTank.setError("Tank too High!");
                portTankInt = 0;
            }

            if (!(TextUtils.isEmpty(stbdTank.getText().toString()))) {
                //fwdTank.setText("0");
                stbdTankInt = Float.parseFloat(stbdTank.getText().toString());
            } else {
                stbdTankInt = 0;
            }
            if (stbdTankInt > (stbdTankArray.length)) {
                stbdTank.setError("Tank too High!");
                stbdTankInt = 0;
            }
            // End Arrays with Fuel Data
            // make a function to figure out difference between two numbers, and return the number.

            // add the rest of the tanks...
            double additionalFwdFuel = findFuelDifference(fwdTankArray[(int) fwdTankInt], fwdTankArray[(int) fwdTankInt + 1], (fwdTankInt - (int) fwdTankInt));
            double fwdFuel = ((fwdTankArray[(int) fwdTankInt]) + additionalFwdFuel);

            double additionalLazFuel = findFuelDifference(lazTankArray[(int) lazTankInt], lazTankArray[(int) lazTankInt + 1], (lazTankInt - (int) lazTankInt));
            double lazFuel = ((lazTankArray[(int) lazTankInt]) + additionalLazFuel);

            double additionalAftFuel = findFuelDifference(aftTankArray[(int) aftTankInt], aftTankArray[(int) aftTankInt + 1], (aftTankInt - (int) aftTankInt));
            double aftFuel = ((aftTankArray[(int) aftTankInt]) + additionalAftFuel);

            double additionalPortFuel = findFuelDifference(portTankArray[(int) portTankInt], portTankArray[(int) portTankInt + 1], (portTankInt - (int) portTankInt));
            double portFuel = ((portTankArray[(int) portTankInt]) + additionalPortFuel);

            double additionalStbdFuel = findFuelDifference(stbdTankArray[(int) stbdTankInt], stbdTankArray[(int) stbdTankInt + 1], (stbdTankInt - (int) stbdTankInt));
            double stbdFuel = ((stbdTankArray[(int) stbdTankInt]) + additionalStbdFuel);

            // need to take values from array here, these are positions
            totalGals = fwdFuel + aftFuel + lazFuel + portFuel + stbdFuel;


            //Write Invidual Fuel Tank Gallons
            gallonsFwd = (TextView) findViewById(R.id.gallonsFwd);
            gallonsFwd.setText(String.valueOf(fwdFuel) + " Gallons");

            gallonsLaz = (TextView) findViewById(R.id.gallonsLaz);
            gallonsLaz.setText(String.valueOf(lazFuel) + " Gallons");

            gallonsAft = (TextView) findViewById(R.id.gallonsAft);
            gallonsAft.setText(String.valueOf(aftFuel) + " Gallons");

            gallonsPort = (TextView) findViewById(R.id.gallonsPort);
            gallonsPort.setText(String.valueOf(portFuel) + " Gallons");

            gallonsStbd = (TextView) findViewById(R.id.gallonsStbd);
            gallonsStbd.setText(String.valueOf(stbdFuel) + " Gallons");

            //Write the total Gallons
            totalGal = (TextView) findViewById(R.id.totalGals);
            totalGal.setText("Total Gallons = " + String.valueOf(totalGals));
        } else {

            // End Calculate Page

            double fuelTankFrom = 0;
            double fuelTankTo = 0;

            int transferAmount = 0;
            TextView tankAfterXer1, tankAfterXer2;

            transferAmount = Integer.parseInt(transferFrom.getText().toString());
            // Gallons to Transfer
            int selectedFromTankInt = 0;
            int selectedToTankInt = 0;

            if (selectedFromTank.contentEquals("FowardTank")) selectedFromTankInt = 0;
            if (selectedFromTank.contentEquals("AftTank")) selectedFromTankInt = 1;
            if (selectedFromTank.contentEquals("LazTank")) selectedFromTankInt = 2;
            if (selectedFromTank.contentEquals("PortWingTank")) selectedFromTankInt = 3;
            if (selectedFromTank.contentEquals("StbdWingTank")) selectedFromTankInt = 4;

            if (selectedToTank.contentEquals("FowardTank")) selectedToTankInt = 0;
            if (selectedToTank.contentEquals("AftTank")) selectedToTankInt = 1;
            if (selectedToTank.contentEquals("LazTank")) selectedToTankInt = 2;
            if (selectedToTank.contentEquals("PortWingTank")) selectedToTankInt = 3;
            if (selectedToTank.contentEquals("StbdWingTank")) selectedToTankInt = 4;
            double[] selectedFromArray = new double[100];
            double[] selectedToArray = new double[100];

            int i = 0;
            switch (selectedFromTankInt) {
                case 0:
                    selectedFromArray = new double[fwdTankArray.length];
                    while (i < fwdTankArray.length) {
                        selectedFromArray[i] = fwdTankArray[i];
                        i++;
                    }
                    break;
                case 1:
                    selectedFromArray = new double[aftTankArray.length];
                    while (i < aftTankArray.length) {
                        selectedFromArray[i] = aftTankArray[i];
                        i++;
                    }
                    break;
                case 2:
                    selectedFromArray = new double[lazTankArray.length];
                    while (i < lazTankArray.length) {
                        selectedFromArray[i] = lazTankArray[i];
                        i++;
                    }
                    break;
                case 3:
                    selectedFromArray = new double[portTankArray.length];
                    while (i < portTankArray.length) {
                        selectedFromArray[i] = portTankArray[i];
                        i++;
                    }
                    break;
                case 4:
                    selectedFromArray = new double[stbdTankArray.length];
                    while (i < stbdTankArray.length) {
                        selectedFromArray[i] = stbdTankArray[i];
                        i++;
                    }
                    break;
            }

            int j = 0;
            switch (selectedToTankInt) {
                case 0:
                    selectedToArray = new double[fwdTankArray.length];
                    while (j < fwdTankArray.length) {
                        selectedToArray[j] = fwdTankArray[j];
                        j++;
                    }
                    break;
                case 1:
                    selectedToArray = new double[aftTankArray.length];
                    while (j < aftTankArray.length) {
                        selectedToArray[j] = aftTankArray[j];
                        j++;
                    }
                    break;
                case 2:
                    selectedToArray = new double[lazTankArray.length];
                    while (j < lazTankArray.length) {
                        selectedToArray[j] = lazTankArray[j];
                        j++;
                    }
                    break;
                case 3:
                    selectedToArray = new double[portTankArray.length];
                    while (j < portTankArray.length) {
                        selectedToArray[j] = portTankArray[j];
                        j++;
                    }
                    break;
                case 4:
                    selectedToArray = new double[stbdTankArray.length];
                    while (j < stbdTankArray.length) {
                        selectedToArray[j] = stbdTankArray[j];
                        j++;
                    }
                    break;
            }

            double selectedFromFloat;
            double selectedToFloat;

            if (!(TextUtils.isEmpty(currentAmountIn1.getText().toString()))) {
                //fwdTank.setText("0");
                selectedFromFloat = Double.parseDouble(currentAmountIn1.getText().toString());
            } else {
                selectedFromFloat = 0;
            }
            if (selectedFromFloat >= selectedFromArray.length) {
                currentAmountIn1.setError("Tank too High!");
                selectedFromFloat = 0;
            }
            if (!(TextUtils.isEmpty(currentAmountIn2.getText().toString()))) {
                //fwdTank.setText("0");
                selectedToFloat = Double.parseDouble(currentAmountIn2.getText().toString());
            } else {
                selectedToFloat = 0;
            }
            if (selectedToFloat >= selectedToArray.length) {
                currentAmountIn2.setError("Tank too High!");
                selectedToFloat = 0;
            }


            fuelTankFrom = searchForInches(selectedFromArray, (selectedFromArray[(int)selectedFromFloat] - transferAmount), selectedFromArray.length);
            fuelTankTo = searchForInches(selectedToArray, ((selectedToArray[(int)selectedToFloat]) + transferAmount), selectedToArray.length);
            // end crazy math
            tankAfterXer1 = (TextView) findViewById(R.id.afterXfer1);
            tankAfterXer1.setText(selectedFromTank + " in Inches After Transfer = " + String.valueOf(round(fuelTankFrom, 2)) + "in");

            tankAfterXer2 = (TextView) findViewById(R.id.afterXfer2);
            tankAfterXer2.setText(selectedToTank + " in Inches After Transfer = " + String.valueOf(round(fuelTankTo, 2)) + "in");

            // Start Transfer Page

        }
    }
    public double searchForInches(double[] selectedInArray, double gallonSearch, int length) {
        int  i;
        int position = 0;
        double difference= 0;
        double differenceBetween = 0;

        for (i = 0; i < (length-1); i++) {

            if (selectedInArray[i] <= gallonSearch && gallonSearch <= selectedInArray[i + 1]) {

                if (gallonSearch == selectedInArray[i]) {
                    position = i;
                    return position;
                } else if (gallonSearch == selectedInArray[i+1]) {
                    position = i+1;
                    return position;
                } else if (gallonSearch > selectedInArray[length-1] || gallonSearch < selectedInArray [0]) {
                    currentAmountIn2.setError("Something went wrong");
                } else {
                    position = i;
                    double subtractNumber = selectedInArray[i + 1] - gallonSearch;

                    differenceBetween = selectedInArray[i + 1] - selectedInArray[i];
                    difference = differenceBetween - subtractNumber;
                    difference = difference / differenceBetween;
                }

            }

        }

        return position + difference;
    }

    public double findFuelDifference(double small, double big, double decimal) {

        double difference;
        double differenceValue;

        difference = big-small;
        differenceValue = difference*decimal;

        return differenceValue;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
