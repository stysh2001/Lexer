package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        try {
            File file = new File("./src/com/company/Code.txt");
            InputStream is = new FileInputStream(file);

            Lexer lexer = new Lexer(is);
            Token token;

            do {
                token = lexer.readToken();
                System.out.println(TokenType.to_string(token.type) + " " + token.str);
            } while (token.type != TokenType.EOF);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
