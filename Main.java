import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[]args)
    {
         
        
    
        String filePath = ""; //file path to text you want to search.
        String Atext = null;
        try {
            Atext = Files.readString(Paths.get(filePath));
            
        } 
        catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }
                                    //the words whose locations are being compared. use lowercase. 
        ArrayList<List<Integer>> locationLists = findMedianDistance("", "", Atext);

        //prints out a list with [word 1 location, distance to nearest word 2] inside for each word 1 appearance.
        System.out.println(locationLists.toString());

       //next section looks through location lists and finds the median distance between word 1 and word 2.
       List<Integer> difList = new ArrayList<>();
       for(List<Integer> list : locationLists)
       {
            difList.add(list.get(1));
       }

       int size = difList.size();
       double median = 0;

       Collections.sort(difList);
       if (size % 2 == 1) {
            median = difList.get(size / 2);
        } else {
            double middle1 = difList.get(size / 2 - 1);
            double middle2 = difList.get(size / 2);
            median = (middle1 + middle2) / 2.0;
        }

        //prints the median distance out.
       System.out.println(" Median Dist: "+median);

 

    }
    
    //first word is the word that gets found the closest of the other. So result is [word 1 location, distance to nearest word 2]
    public static ArrayList<List<Integer>> findMedianDistance(String word1,String word2, String text)
    {
        //slits the text into a list of words, accounts for special characters and words with ' . 
        String[] words = Pattern.compile("[^\\w']+").split(text.trim().toLowerCase());
        
        int wordLocation = 0;
        List<Integer> word1Locations = new ArrayList<>();
        List<Integer> word2Locations = new ArrayList<>();
        for (String word : words) 
        {
            wordLocation ++;
            //the locations of word 1's and word 2's get split into 2 different lists.
            if( word.equals(word1) || word.equals(word1 + "'s") || word.equals(word1 + "s"))
            {
                word1Locations.add(wordLocation);
            }
            if( word.equals(word2) || word.equals(word2 + "'s") || word.equals(word2 + "s") /*|| word.equals("willoughby")*/)
            {
                word2Locations.add(wordLocation);
            }
        }
        //combines these lists into one big one.
        ArrayList<List<Integer>> result = new ArrayList<>();
        result.add(word1Locations);
        result.add(word2Locations);

        //this now combs the big list and compares the locations to find the closest distance to each word 1 occurance.
        ArrayList<List<Integer>> result2 = new ArrayList<>();
        for ( int word1Loc : result.get(0)) 
        {
            int closestDif = 999999;
            for(int word2Loc : result.get(1))
            {
                if(Math.abs(word1Loc - word2Loc)< closestDif)
                {
                    closestDif = Math.abs(word1Loc - word2Loc);
                }
            }
            List<Integer> miniResult = new ArrayList<>();
            miniResult.add(word1Loc);
            miniResult.add(closestDif);
            result2.add(miniResult);
            //result two becomes a list holding lists with the word 1 location, and distance to closest word 2.
        }
        


        return result2;
    }

}
