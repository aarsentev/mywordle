package assign4;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NerdleEngine {
    private static int guessNumber = 0;
    private String guessWord;
    private String filePath = "src/assign4/words.txt";

    public NerdleEngine() {
        this.guessWord = this.selectSecretWordFromFile();
    }

    private String selectSecretWordFromFile(){
        File file = new File(filePath);
        ArrayList<String> words = new ArrayList<String>();
        try(Scanner scan = new Scanner(file)){
            while (scan.hasNextLine()){
                words.add(scan.nextLine());
            }
        }
        catch(Exception err){
            System.out.println("Sorry! File not found!");
        }
        Random rand = new Random();
        int wordInd = rand.nextInt(words.size());
        return words.get(wordInd).trim();
    }

    private boolean checkWinningConditions(int[] wordArray){
        int checksum = 0;
        for (int i = 0; i < wordArray.length; i++){
            checksum += wordArray[i];
        }
        return (checksum == (2 * wordArray.length));
    }

    public GuessResponse makeGuess(String userGuess){
        int[] guesses = new int[5];
        userGuess = userGuess.trim();

        if (userGuess.length() < 5 && userGuess.length() > 0){
            //accept the shorter words and append * sign instead of missing words to make it 5 letters long
            for (int i = 0; i < (5 - userGuess.length()); i++)
                userGuess += "*";
        }
        else{
            userGuess = userGuess.toUpperCase().substring(0, 5);
            //accept the first 5 letters if the words is over 5 letters long
        }

        
        for (int i = 0; i < userGuess.length(); i++){

            String userCurLetter = "" + userGuess.charAt(i); //current letter of word typed by user 
            String secCurLetter = "" + this.guessWord.charAt(i); //current letter of word we are guessing
            
            if (userCurLetter.equals(secCurLetter)){
                guesses[i] = 2;
            }
            else if (this.guessWord.contains(userCurLetter)){
                guesses[i] = 1;
            }
            else if (!this.guessWord.contains(userCurLetter)){
                guesses[i] = 0;
            }
        }
        
        guessNumber++;
        boolean haveWon = checkWinningConditions(guesses);
        return new GuessResponse(guessNumber, haveWon, userGuess, guesses);
    }
}
