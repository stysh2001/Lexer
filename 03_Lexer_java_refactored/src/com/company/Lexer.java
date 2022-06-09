package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class Lexer {
    private final InputStream is;
    private int last_symbol;
    private int cursor;

    private static final Set<String> SEPARATORS;
    static {
        SEPARATORS = new HashSet<>();
        SEPARATORS.add("(");
        SEPARATORS.add(")");
        SEPARATORS.add(":=");
        SEPARATORS.add(";");

    }
    private static final Map<String, TokenType> KEYWORDS;
    static {
        KEYWORDS = new HashMap<>();
        KEYWORDS.put("or", TokenType.OR);
        KEYWORDS.put("and", TokenType.AND);
        KEYWORDS.put(":=", TokenType.ASSIGNMENT);
        KEYWORDS.put("if", TokenType.IF);
        KEYWORDS.put("then", TokenType.THEN);
        KEYWORDS.put("else", TokenType.ELSE);
        KEYWORDS.put("(", TokenType.OPEN);
        KEYWORDS.put(")", TokenType.CLOSE);
        KEYWORDS.put("xor", TokenType.XOR);
        KEYWORDS.put(";", TokenType.SEMI);
        KEYWORDS.put("__eof__", TokenType.EOF);
    }
    private static final Pattern int_pattern = compile("[1-9][0-9]*|0");
    public static final Pattern id_pattern = compile("[a-z_A-Z]([a-zA-Z_]|[0-9])*");

    public Lexer(InputStream is) throws ParseException {
        this.is = is;
        cursor = 0;
        readChar();
    }
    
    public Token readToken() throws ParseException {
        Token str_builder = new Token();
        str_builder.str = read();

        if (KEYWORDS.containsKey(str_builder.str))
            str_builder.type = KEYWORDS.get(str_builder.str);
        else if (id_pattern.matcher(str_builder.str).find())
            str_builder.type = TokenType.ID;
        else if (int_pattern.matcher(str_builder.str).find())
            str_builder.type = TokenType.INT;
        else {
            throw new IllegalArgumentException(String.format("Illegal character '%s' at positon %d", (char) last_symbol, cursor));
        }
        return str_builder;
    }

    private void readChar() throws ParseException {
        cursor++;
        try {
            last_symbol = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), cursor);
        }
    }

    String read() throws ParseException {
        StringBuilder str_builder = new StringBuilder();
        String tmp = "";

        while (Character.isWhitespace(last_symbol)) readChar();
        if (last_symbol == -1) return "__eof__";
        while (!(Character.isWhitespace(last_symbol) || last_symbol == -1)) {
            tmp += (char) last_symbol;

            if (SEPARATORS.contains(tmp)) { readChar(); return tmp; }
            if (!isValidStr(tmp)) return str_builder.toString();

            str_builder.append((char) last_symbol);
            readChar();
        }
        return str_builder.toString();
    }

    private boolean isValidStr(String str) {
        return id_pattern.matcher(str).matches() || int_pattern.matcher(str).matches() || str.equals(":");
    }
}
