package ca.blogspot.electrail.bindecihex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerFirst;
    Spinner spinnerFinal;
    EditText numInput;
    Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinnerFirst = (Spinner) findViewById(R.id.first_spinner);
        final Spinner spinnerFinal = (Spinner) findViewById(R.id.final_spinner);
        final EditText numInput = (EditText)findViewById(R.id.numInput);
        final Button goButton = (Button)findViewById(R.id.goButton);
        final TextView finalResult = (TextView)findViewById(R.id.result);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.first_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFinal.setAdapter(adapter);
        spinnerFirst.setAdapter(adapter);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String originalFrom = spinnerFirst.getSelectedItem().toString();
                String finalFrom = spinnerFinal.getSelectedItem().toString();

                String input = numInput.getText().toString();
                String binaryValue;
                String result;

                //take care of taking previous result to binary
                if (originalFrom.equals("Decimal"))
                    binaryValue = fromDec(input);
                else if (originalFrom.equals("Hexadecimal"))
                    binaryValue = fromHex(input);
                else
                    binaryValue = input;

                if (finalFrom.equals("decimal"))
                    result = toDec(binaryValue);
                else if (finalFrom.equals("Hexadecimal"))
                    result = toHex(binaryValue);
                else
                    result = binaryValue;

                finalResult.setText(result);
            }
        });
    }


    private static String fromDec(String pre) {
        int decNumber  = Integer.parseInt(pre);
        String finalString = "";
        while (true) {
            if (decNumber == 1){
                finalString = "1" + finalString;
                break;
            }

            else if (decNumber == 0){
                break;
            }
            else if (decNumber %2 == 0)
            {
                finalString =  "0" + finalString;
                decNumber /= 2;
                //if odd, prepare for a remainder
            } else if (decNumber %2 == 1) {
                finalString = "1" + finalString ;
                decNumber /= 2;
            }
        }
        return finalString;
    }

    private static String fromHex(String pre) {
        Integer convNum;
        String finalString = "";

        while(true) {
            char currentChar = pre.charAt(0);

            if (currentChar >= 'A' && currentChar <= 'F')
            {
                convNum = currentChar-55;
            } else if (currentChar >= 'a' && currentChar <= 'f')
            {
                convNum = currentChar-87;
            }
            else
            {
                convNum = currentChar-48;
            }

            String bin = fromDec(convNum.toString());

            while (bin.length() < 4) {
                bin = "0" + bin;
            }

            assert bin.length() == 4;

            finalString += bin;
            //try catch block to break out of loop when you can no longer shorter because I'm stupid
            try {
                pre = pre.substring(1);
            } catch (Exception e)
            {
                break;
            }
        }
        return finalString;
    }


    private static String toDec(String bin) {

        return bin;
    }

    private static String toHex(String bin) {

        return bin;
    }


}
