package com.company;



public enum TokenType {
    ASSIGNMENT,   // :=
    IF,
    THEN,
    ELSE,
    ID,           // переменная
    SEMI,         // ;
    INT,          // число
    OPEN,         // (
    CLOSE,        // )
    XOR,
    OR,
    AND,
    EOF;

    static String to_string( TokenType type) {
        switch (type) {
            case AND -> { return "And"; }
            case ASSIGNMENT -> { return "Assignment"; }
            case IF -> { return "If"; }
            case THEN -> { return "Then"; }
            case ELSE -> { return "Else"; }
            case ID -> { return "Identifier"; }
            case SEMI -> { return "Semicolon"; }
            case INT -> { return "Integer"; }
            case OPEN -> { return "LeftParenthesis";}
            case CLOSE -> { return "RightParenthesis";}
            case XOR -> { return "Xor"; }
            case OR -> { return "Or"; }
            case EOF -> { return "EOF"; }
        }
        return null;
    }
}

