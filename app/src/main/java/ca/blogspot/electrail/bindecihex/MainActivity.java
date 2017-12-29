package ca.blogspot.electrail.bindecihex;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {
    private Button goButton;
    private View hidden;
    private boolean isHistory;
    private ArrayList<String> historyTitles;
    private ArrayList<String> historyResults;

    private TextView hist1;
    private TextView result1;
    private TextView hist2;
    private TextView result2;
    private TextView hist3;
    private TextView result3;
    private TextView hist4;
    private TextView result4;
    private TextView hist5;
    private TextView result5;
    private TextView hist6;
    private TextView result6;
    private TextView hist7;
    private TextView result7;
    private TextView hist8;
    private TextView result8;

    private ArrayList<TextView> histList;
    private ArrayList<TextView> resultList;

    private TextView answIs;
    private TextView finalResult;
    private EditText numInput;
    private Spinner spinnerFirst;
    private Spinner spinnerFinal;
    private TextView letsConvert;
    private TextView in;
    private TextView to;
    private Button hideShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //declare variables for changing elements in the xml
        spinnerFirst = (Spinner) findViewById(R.id.first_spinner);
        spinnerFinal = (Spinner) findViewById(R.id.final_spinner);
        numInput = (EditText)findViewById(R.id.numInput);
        finalResult = (TextView)findViewById(R.id.result);
        answIs = (TextView)findViewById(R.id.answIs);
        letsConvert = (TextView)findViewById(R.id.letsconvert);
        in = (TextView)findViewById(R.id.in);
        to = (TextView)findViewById(R.id.to);
        hideShow = (Button)findViewById(R.id.slide);
        goButton = (Button)findViewById(R.id.goButton);
        hidden = findViewById(R.id.hidden);

        //initialize isHistory to signify that history is hidden
        isHistory = false;

        //initialize array for history
        historyTitles = new ArrayList<String>();
        historyResults = new ArrayList<String>();

        //initialize textviews for history
        hist1 = (TextView)findViewById(R.id.history1);
        result1 = (TextView)findViewById(R.id.result1);
        hist2 = (TextView)findViewById(R.id.history2);
        result2 = (TextView)findViewById(R.id.result2);
        hist3 = (TextView)findViewById(R.id.history3);
        result3 = (TextView)findViewById(R.id.result3);
        hist4 = (TextView)findViewById(R.id.history4);
        result4 = (TextView)findViewById(R.id.result4);
        hist5 = (TextView)findViewById(R.id.history5);
        result5 = (TextView)findViewById(R.id.result5);
        hist6 = (TextView)findViewById(R.id.history6);
        result6 = (TextView)findViewById(R.id.result6);
        hist7 = (TextView)findViewById(R.id.history7);
        result7 = (TextView)findViewById(R.id.result7);
        hist8 = (TextView)findViewById(R.id.history8);
        result8 = (TextView)findViewById(R.id.result8);

        //initialize arraylist for all the textviews (for first and second line separately
        //and add all necessary elements
        histList = new ArrayList<TextView>();
        resultList = new ArrayList<TextView>();
        histList.addAll(Arrays.asList(hist1, hist2, hist3, hist4, hist5, hist6, hist7,hist8));
        resultList.addAll(Arrays.asList(result1,result2,result3,result4,result5,result6,result7,result8));

        // FROM OFFICIAL ANDROID DEVELOPER WEBPAGE FOR SPINNERS:
        //set the spinners to have the contents of first_array
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.choices_array, android.R.layout.simple_spinner_item);
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
                String finalTo = spinnerFinal.getSelectedItem().toString();

                String input = numInput.getText().toString();
                String binaryValue;
                String result;

                boolean correct = true;

                //check for no input

                 if(input.isEmpty())
                 {
                     Context context = getApplicationContext();
                     CharSequence text = "The input box is empty! :(";
                     int duration = Toast.LENGTH_SHORT;
                     Toast.makeText(context, text, duration).show();
                     correct = false;
                     input = "0";
                 }
                //check for symbols that are not letters or numbers
                for (int i = 0; i < input.length(); i++) {
                    if (!Character.toString((input.charAt(i))).matches("[0-9A-Za-z]")) {
                        Context context = getApplicationContext();
                        CharSequence text = "please format your value without special characters! :(";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(context, text, duration).show();
                        correct = false;
                        input = "0";
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
                if (finalTo.equals("Decimal"))
                    result = toDec(binaryValue);
                else if (finalTo.equals("Hexadecimal"))
                    result = toHex(binaryValue);
                else
                    result = binaryValue;

                //if no exceptions were thrown, print out result and record history
                //otherwise, print "error" instead of the given answer
                if (correct) {
                    //set the text in the bottom field
                    answIs.setText("your answer in " + finalTo + " is");
                    finalResult.setText(result);

                    //format the new string stating the query to add to the history and add to
                    //the historyTitles array list
                    String addToHist = "From " + input + " (" + shorten(originalFrom) + ") to "+
                            shorten(finalTo) +":";
                    historyTitles.add(addToHist);
                    //add the result to the result history array
                    historyResults.add(result);
                    //if there is too much in the history, take out the oldest pair of history strings
                    if (historyTitles.size()>8)
                    {
                        historyTitles.remove(0);
                        historyResults.remove(0);
                    }
                } else {
                    answIs.setText("error");
                    finalResult.setText("");

                }


            }
        });


    }

    private String shorten(String original)
    {
        if (original.equals("Decimal"))
            return "dec";
        else if (original.equals("Hexadecimal"))
            return "hex";
        else
            return "bin";
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

    /**
     * Void method that regulates what occurs when someone wants to open/close history
     * @param view, which is the current view that it is using
     */
    public void slideHistory(View view) {
        if(!isHistory) {

            //if the history is empty, display "no history to show" in the second textview row
            //because that is the first row with no background color
            //make the first row's visibility GONE so that the text is at the top of the history

            //otherwise, assign the history to its respective textviews, with the newest at the top
            if (historyTitles.isEmpty())
            {
                histList.get(1).setText("no history to show!");
                resultList.get(1).setText("");

                histList.get(0).setVisibility(View.GONE);
                resultList.get(0).setVisibility(View.GONE);
                histList.get(1).animate().alpha(1).setDuration(200).setStartDelay(1000);
                resultList.get(1).animate().alpha(1).setDuration(200).setStartDelay(1000);
            } else {
                for (int j = 0; j < historyTitles.size(); j++) {
                    histList.get(j).setText(historyTitles.get(historyTitles.size()-(j+1)));
                    resultList.get(j).setText(historyResults.get(historyResults.size()-(j+1)));
                }
            }

            //animate the display of the history items
            //start 1 second after hide/show button is pushed and have each next item print after 150 miliseconds
            int offset = 1000;
            for (int i = 0; i < historyTitles.size(); i++)
            {
                histList.get(i).animate().alpha(1).setDuration(200).setStartDelay(offset);
                resultList.get(i).animate().alpha(1).setDuration(200).setStartDelay(offset);
                offset+=150;
            }

            //make an arraylist of alphaAnimations going from an opacity of 1 to 0, each with a
            //offset of 50 miliseconds more than the one before, starting at 0
            ArrayList<AlphaAnimation> alphAn = new ArrayList<AlphaAnimation>();

            int offsetBefore = 0;
            for (int i = 0; i < 7 ; i++)
            {
                alphAn.add(new AlphaAnimation(1.0f, 0.0f));
                alphAn.get(i).setDuration(150);
                alphAn.get(i).setFillAfter(true);
                alphAn.get(i).setStartOffset(offsetBefore);
                offsetBefore+= 50;
            }

            //apply animations so that the lowest appears first (has lowest offset)
            letsConvert.startAnimation(alphAn.get(6));
            numInput.startAnimation(alphAn.get(5));
            in.startAnimation(alphAn.get(4));
            spinnerFirst.startAnimation(alphAn.get(4));
            to.startAnimation(alphAn.get(3));
            spinnerFinal.startAnimation(alphAn.get(3));
            goButton.startAnimation(alphAn.get(2));
            answIs.startAnimation(alphAn.get(1));
            finalResult.startAnimation(alphAn.get(0));

            //animate the hideShow button to open the history bar
            //bottomUp animation is from Paul's answer on https://stackoverflow.com/questions/18232372/slide-a-layout-up-from-bottom-of-screen
            Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.up);
            hidden.startAnimation(bottomUp);
            hidden.setVisibility(View.VISIBLE);

            hideShow.setText("hide history");
            isHistory = true;
        }
        else {
            //if the list was empty, essentially undo what was done to show "no history to show!"
            //do this by taking away the visibility of GONE to the first set of textViews
            //and taking the second set to an opacity of 0

            //if not empty,
            if (historyTitles.isEmpty())
            {
                histList.get(0).setVisibility(View.VISIBLE);
                resultList.get(0).setVisibility(View.VISIBLE);
                histList.get(1).animate().alpha(0).setDuration(200);
                resultList.get(1).animate().alpha(0).setDuration(200);
            }

            //animate the list to disappear, with a difference of offset 50 miliseconds, starting at 0
            int offsetOut = 0;
            for (int i = 0; i < historyTitles.size(); i++) {
                histList.get(i).animate().alpha(0).setDuration(200).setStartDelay(offsetOut);
                resultList.get(i).animate().alpha(0).setDuration(200).setStartDelay(offsetOut);
                offsetOut += 50;
            }
            //make an arrayList of alpha animations again, but animate from an opacity
            //of 0 to 1 and set the initial offset of 400
            ArrayList<AlphaAnimation> alphAn = new ArrayList<AlphaAnimation>();
            int offsetBefore = 400;
            for (int i = 0; i < 7 ; i++)
            {
                alphAn.add(new AlphaAnimation(0.0f, 1.0f));
                alphAn.get(i).setDuration(150);
                alphAn.get(i).setFillAfter(true);
                alphAn.get(i).setStartOffset(offsetBefore);
                offsetBefore+= 150;
            }
            //assign to views to that the top view reappears first
            letsConvert.startAnimation(alphAn.get(0));
            numInput.startAnimation(alphAn.get(1));
            in.startAnimation(alphAn.get(2));
            spinnerFirst.startAnimation(alphAn.get(2));
            to.startAnimation(alphAn.get(3));
            spinnerFinal.startAnimation(alphAn.get(3));
            goButton.startAnimation(alphAn.get(4));
            answIs.startAnimation(alphAn.get(5));
            finalResult.startAnimation(alphAn.get(6));

            //animate the hideShow button to close the history bar
            //from Paul's answer on https://stackoverflow.com/questions/18232372/slide-a-layout-up-from-bottom-of-screen
            Animation topDown = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.down);
            hidden.startAnimation(topDown);
            hidden.setVisibility(View.GONE);

            hideShow.setText("see history again");
            isHistory = false;
        }
    }

}


