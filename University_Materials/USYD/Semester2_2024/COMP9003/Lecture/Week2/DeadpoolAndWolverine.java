import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;


public class DeadpoolAndWolverine {

    public static void main(String[] args) {
        /**
         args: The target score
         standard input: character-score pairs; Each pair will consist of a
         character's name and their associated revenue (in millions of dollars)

         character name is a single word
         the score is an integer or float
         **/
        // This is the total score in the command line
        // we assume that there is always a command line value
        int total = Integer.parseInt(args[0]);

        // Initialize a scanner
        Scanner scanner = new Scanner(System.in);

        // Initialized score for every character without specific value
        double score = 0.0;

        // it is a pair of character:score
        Map<String, Double> pairs = new HashMap<String, Double>();

        // now we begin to scan the standard input
        // I think we may need to read each line
        // the format should be "string number string number ..."
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.isEmpty()){
                continue;
            }
            // check the format
            String[] sepWords = line.split(" ");

            // index counter
            int idx = 0;
            for(String s : sepWords){
                if (idx%2 == 0){
                    try{
                        score = Double.parseDouble(sepWords[idx+1]);
                        if (score < 0)
                            throw new IllegalArgumentException("Number must be non-negative.");
                        pairs.put(s,score);
                    }catch(Exception e1){
                        // any mistake lead to the ending of this loop (i.e. skip current line)
                        if (! s.equals("EOF"))
                            System.out.println("Input '"+ line + "' is not valid. Make sure your input is valid.");
                        break;
                    }
                }
                idx ++;
            }
        }

        // after we receive EOF, we can now calculate the result
        ArrayList<String> characters = new ArrayList<>();
        double revenue = 0;

        while(total >= 0){
            double minimum_score = Double.POSITIVE_INFINITY;
            String character = "";
            for (String key: pairs.keySet()){
                if(pairs.get(key)<minimum_score){
                    minimum_score = pairs.get(key);
                    character = key;
                }else{
                    character = character;
                }
            }
            scanner.close();

            // after the for loop, we find the character who has the smallest score
            try{
                double value = pairs.remove(character);
                total -= value;
                if (total >= 0){
                    characters.add(character);
                    revenue += value;
                }
            } catch (NullPointerException e){

            }
        }

        if (characters.isEmpty()){
            System.out.println("No characters selected.");
        } else{
            Collections.sort(characters);
            System.out.println("Selected characters: " + characters);
            System.out.println("Total expected revenue: "+ (int) revenue +" million dollars");
        }
    }
}
