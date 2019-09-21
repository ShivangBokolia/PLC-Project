package scanning;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JottScanner {
    private List<Token> tokenQueue;
    private String fileName;

    public JottScanner(String fileName) {
        this.fileName = fileName;
        this.tokenQueue = new ArrayList<>();
    }

    public List<Token> scanFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNo = 0;
            while ((line = bufferedReader.readLine()) != null) {
                lineNo += 1;
                for (int i = 0; i < line.length(); i++) {
                    //skipping the spaces
                    if (line.charAt(i) ==  ' '){
                        continue;
                    }
                    //adding the tokens to the queue
                    if (line.charAt(i) == '+' || line.charAt(i) == '-' ||
                            line.charAt(i) == '*' || line.charAt(i) == '^' || line.charAt(i) == ',' ||
                            line.charAt(i) == '(' || line.charAt(i) == ')' || line.charAt(i) == ';' ||
                            line.charAt(i) == '=') {
                        tokenQueue.add(new Token(Character.toString(line.charAt(i)), lineNo));
                    }
                    //detecting the comments
                    else if (line.charAt(i) == '/'){
                        if (i+1 < line.length() && line.charAt(i+1) == '/'){
                            break;
                        }
                        else{
                            tokenQueue.add(new Token(Character.toString(line.charAt(i)), lineNo));
                        }
                    }
                    else{
                        //checking for the strings that begin " and end with ".
                        if (line.charAt(i) == '\"'){
                            int index = i + 1;
                            String tokenChar = "\"";
                            while(index < line.length() && line.charAt(index) != '\"'){
                                tokenChar = tokenChar.concat(Character.toString(line.charAt(index)));
                                index = index + 1;
                            }
                            i = index;
                            tokenChar = tokenChar + "\"";
                            tokenQueue.add(new Token(tokenChar, lineNo));
                        }
                        else{
                            String tokenChar = "";
                            //checking for char and digit in a token
                            if (Character.isLetter(line.charAt(i))){
                                while(i + 1 < line.length()){
                                    if (line.charAt(i+1) == '+' || line.charAt(i+1) == '-' ||
                                            line.charAt(i+1) == '*' || line.charAt(i+1) == '^' || line.charAt(i+1) == ',' ||
                                            line.charAt(i+1) == '(' || line.charAt(i+1) == ')' || line.charAt(i+1) == ';' ||
                                            line.charAt(i+1) == '=' || line.charAt(i+1) == ' '){
                                        tokenChar = tokenChar.concat(Character.toString(line.charAt(i)));
                                        tokenQueue.add(new Token(tokenChar, lineNo));
                                        break;
                                    }
                                    else{
                                        tokenChar = tokenChar.concat(Character.toString(line.charAt(i)));
                                    }
                                    i = i + 1;
                                }
                            }
                            //checking for only digits and decimals in the token
                            else if (Character.isDigit(line.charAt(i))){
                                while(i + 1 < line.length()){
                                    if (line.charAt(i+1) == '+' || line.charAt(i+1) == '-' ||
                                            line.charAt(i+1) == '*' || line.charAt(i+1) == '^' || line.charAt(i+1) == ',' ||
                                            line.charAt(i+1) == '(' || line.charAt(i+1) == ')' || line.charAt(i+1) == ';' ||
                                            line.charAt(i+1) == '=' || line.charAt(i+1) == ' ' || Character.isLetter(line.charAt(i+1))){
                                        tokenChar = tokenChar.concat(Character.toString(line.charAt(i)));
                                        tokenQueue.add(new Token(tokenChar, lineNo));
                                        break;
                                    }
                                    else{
                                        tokenChar = tokenChar.concat(Character.toString(line.charAt(i)));
                                    }
                                    i = i + 1;
                                }
                            }
                            //checking for the token that begins with a '.'
                            else if (line.charAt(i) == '.'){
                                //combination for while and if possible
                                while(i + 1 < line.length()){
                                    if (line.charAt(i+1) == '+' || line.charAt(i+1) == '-' ||
                                            line.charAt(i+1) == '*' || line.charAt(i+1) == '^' || line.charAt(i+1) == ',' ||
                                            line.charAt(i+1) == '(' || line.charAt(i+1) == ')' || line.charAt(i+1) == ';' ||
                                            line.charAt(i+1) == '=' || line.charAt(i+1) == ' ' || Character.isLetter(line.charAt(i+1))){
                                        tokenChar = tokenChar.concat(Character.toString(line.charAt(i)));
                                        tokenQueue.add(new Token(tokenChar, lineNo));
                                        break;
                                    }
                                    else{
                                        tokenChar = tokenChar.concat(Character.toString(line.charAt(i)));
                                    }
                                    i = i + 1;
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokenQueue;
    }
}