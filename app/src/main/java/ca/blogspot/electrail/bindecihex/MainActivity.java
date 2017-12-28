package ca.blogspot.electrail.bindecihex;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Spinner spinnerFirst;
    private Spinner spinnerFinal;
    private EditText numInput;
    private Button goButton;

    private View hidden;
    private boolean isHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //declare variables for changing elements in the xml
        final Spinner spinnerFirst = (Spinner) findViewById(R.id.first_spinner);
        final Spinner spinnerFinal = (Spinner) findViewById(R.id.final_spinner);
        final EditText numInput = (EditText)findViewById(R.id.numInput);
        final Button goButton = (Button)findViewById(R.id.goButton);
        final TextView finalResult = (TextView)findViewById(R.id.result);
        final TextView answIs = (TextView)findViewById(R.id.answIs);

        final View hidden = (View ) findViewById(R.id.hidden);
        hidden.setVisibility(View.INVISIBLE);
        isHistory = false;

        // FROM OFFICIAL ANDROID DEVELOPER WEBPAGE FOR SPINNERS:
        //set the spinners to have the contents of first_array
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.first_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFinal.setAdapter(adapter);
        spinnerFirst.setAdapter(adapter);

        //what to do when the "go!" button is pressed
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the input information and initialize result and intermediate values
                String originalFrom = spinnerFirst.getSelectedItem().toString();
                String finalFrom = spinnerFinal.getSelectedItem().toString();

                String input = numInput.getText().toString();
                String binaryValue = null;
                String result;

                boolean correct = true;

                //check for symbols that are not letters or numbers
                for (int i = 0; i < input.length(); i++) {
                    if (!Character.toString((input.charAt(i))).matches("[0-9A-Za-z]")) {
                        try {
                            throw new badInputException();
                        } catch (Exception e) {
                            Context context = getApplicationContext();
                            CharSequence text = "please format your value without special characters! :(";
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(context, text, duration).show();
                            correct = false;
                        }
                    }
                }

                //take care of taking previous result to binary
                //if input is incorrect and an exception is thrown,
                //make a toast with the issue and mark the error flag as true
                //when doing do, set binaryValue to the String 0 to ensure other methods can function
                if (originalFrom.equals("Decimal"))
                    try {
                        binaryValue = fromDec(input);
                    } catch (Exception e) {
                        Context context = getApplicationContext();
                        CharSequence text = "please only use whole numbers (and no letters)! :(";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(context, text, duration).show();
                        correct = false;
                        binaryValue = "0";
                    }
                else if (originalFrom.equals("Hexadecimal"))
                    try {
                        binaryValue = fromHex(input);
                    } catch (Exception e) {
                        Context context = getApplicationContext();
                        CharSequence text = "Please do not include letters G-Z in your input! :(";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(context, text, duration).show();
                        correct = false;
                        binaryValue = "0";
                    }
                else
                    try {
                        binaryValue = checkBinary(input);
                    } catch (Exception e) {
                        Context context = getApplicationContext();
                        CharSequence text = "please format your binary with only 1's and 0's! :(";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(context, text, duration).show();
                        correct = false;
                        binaryValue = "0";
                    }

                //take intermediate binary value to the resultant form
                if (finalFrom.equals("Decimal"))
                    result = toDec(binaryValue);
                else if (finalFrom.equals("Hexadecimal"))
                    result = toHex(binaryValue);
                else
                    result = binaryValue;

                //if no exceptions were thrown, print out result.
                //otherwise, print "error" instead of the given answer
                if (correct) {
                    //set the text in the bottom field
                    answIs.setText("your answer in " + finalFrom + " is");
                    finalResult.setText(result);
                } else {
                    answIs.setText("error");
                    finalResult.setText("");

                }
            }
        });
    }



    /**
     * Acts as a gate to ensure that the binary is formatted correctly
     * @param bin, a binary value
     * @return the inputted binary value if the input is valid
     * @throws badInputException if input is invalid
     */
    private String checkBinary(String bin) throws badInputException{
        //iterate through the string
        //if the character doesn't contain a 1 or 0, throw an exception
        for (int i = 0; i < bin.length(); i++)
        {
            int currentNum = bin.charAt(i);
            if (currentNum != '0' && currentNum !='1')
            {
                throw new badInputException();
            }
        }
        return bin;
    }

    /**
     * Converts a decimal value to binary
     * @param pre, a whole number in string format that does not cause overflow for an int
     * @return a string with the binary form of pre
     *              The string will return 0 if the text is not formatted correctly
     * @throws NumberFormatException if input is invalid
     */
    private String fromDec(String pre) throws NumberFormatException{
        //initialize decnumber, which is to hold pre as an integer
        //if parsing as an int fails, throw an exception, which will be
        // caught in the mainActivity class
        int decNumber = 0;
        decNumber  = Integer.parseInt(pre);

        //initialize the final string as the empty string, which gradually builds
        String finalString = "";
        //append a 0 to the front if even and 1 if odd
        //then perform integer division by 2 to the value to mutate it
        //iterate through until hitting 1 or 0
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
        //return built string
        return finalString;
    }

    /**
     * converts from hexidecimal to binary
     * @param pre, a string that has a hexidecimal value, cna use lower or uppercase
     * @return the hexidecimal in binary form and 0 if the string is invalid
     * @throws NumberFormatException or badInputException if input is invalid
     */
    private String fromHex(String pre) throws badInputException, NumberFormatException {
        //initialize variabbles for a hex digit in integer form and string to be built
        Integer convNum;
        String finalString = "";
        //there is a difference of 55 from the uppercase letters and 87 from the lowercase letters
        // in ASCII compared to its representation in hex
        final int UPPERCASE_OFFSET = 55;
        final int LOWERCASE_OFFSET = 87;

        //iterate through the string ot start building the string
        for(int i = 0; i < pre.length(); i++) {
            char currentChar = pre.charAt(i);
            //check if the string uses any invalid letters
            if ((currentChar >= 'G' && currentChar <= 'Z') || (currentChar >= 'g' && currentChar <= 'z')) {
                throw new badInputException();
            }

            ////convert the letters and numbers accordingly, from char to the proper int
            if (currentChar >= 'A' && currentChar <= 'F')
            {
                convNum = currentChar-UPPERCASE_OFFSET;
            } else if (currentChar >= 'a' && currentChar <= 'f')
            {
                convNum = currentChar-LOWERCASE_OFFSET;
            }
            else
            {
                convNum = currentChar -'0';
            }
            //convert the new int value to a string
            String bin = fromDec(convNum.toString());

            //ensure that the string is 4 digits long
            while (bin.length() < 4) {
                bin = "0" + bin;
            }

            //append the new value to the main string
            finalString += bin;
        }

        //return when finished
        return finalString;
    }

    /**
     * converts from binary to decimal
     * @param bin, a binary value
     * @return a string with a decimal representation of the binary value
     */
    private String toDec(String bin) {
        //initialize total as a double, in addition to the current exponent value,
        //that increases every iteration
        double total= 0;
        double currentExponent = 0;
        while (!bin.isEmpty()) {
            //take the last number in the set and convert it to an integer
            int currentNum = bin.charAt(bin.length()-1) - '0';

            //find the value of that space in the string
            double currentMultiplier = Math.pow(2, currentExponent);
            //multiply it with the integer at the place
            //this will ensure that the number persists if a 1 exists and
            //will not persist if a 0 exists, rather turning to a 0
            double newAmt = currentNum * currentMultiplier;
            //add the result to the cumulative total
            total += newAmt;
            //increment the exponent
            currentExponent +=1;
            //take off last character
            bin = bin.substring(0,bin.length()-1);
        }
        //convert the total to a string and return the string
        String finalTotal = Integer.toString((int)total);
        return finalTotal;
    }

    /**
     * converts from binary to hexidecimal
     * @param bin, a binary value
     * @return a string with a hexidecimal representation of the binary value
     */
    private String toHex(String bin) {
        //there is a difference of 55 from the uppercase letters
        // in ASCII compared to its representation in hex
        final int UPPERCASE_OFFSET = 55;

        //initialize empty string to build result off of and string for each new character
        String result = "";
        String newChar;
        //ensure to populate string until its length is a multiple of 4
        while (bin.length() %4 !=0) {
            bin = "0" + bin;
        }
        while (!bin.isEmpty()) {
            //get the current grouping, which is the last 4 digits of the string
            String currentGroup = bin.substring(bin.length()-4);

            //find the decimal value of the binary
            int decValue = Integer.parseInt(toDec(currentGroup));
            //if the value is 0 to 9, simply make the new character the string version of the decimal
            if (decValue >= 0 && decValue <=9)
            {
                newChar= Integer.toString(decValue);
            } else {
                //otherwise, add the offset to match the letter value in ASCII and then
                //cast it as a char.
                //then turn it to a string for the new character string
                decValue += UPPERCASE_OFFSET;
                newChar= Character.toString((char) decValue);
            }
            //add the new character to the result and take the used digits off the string
            result = newChar + result;
            bin = bin.substring(0,bin.length()-4);
        }
        //return built string
        return result;
    }
    public void slideUpDown(final View view) {
        if(!isHistory) {

            Animation bottomUp = AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.up);
            hidden.startAnimation(bottomUp);
            hidden.setVisibility(View.VISIBLE);
            isHistory = true;
        }
        else {
            Animation topDown = AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.down);
            hidden.startAnimation(topDown);
            hidden.setVisibility(View.INVISIBLE);
            isHistory = false;
        }
    }

}
