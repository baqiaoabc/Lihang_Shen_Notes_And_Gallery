from plates import is_valid

# 注意每个UT function只能因为一种原因导致Fail
def test_strLengtH():
    is_valid("abcdef") == True
    is_valid("abcd") == True
    is_valid("ab") == True
    is_valid("a") == False
    is_valid("abcdefg") == False

def test_twoLettersBegin():
    is_valid("ab12") == True
    is_valid("a12") == False
    is_valid("12") == False

def test_numberNotBeginWithZero():
    is_valid("ab10") == True
    is_valid("ab01") == False
    is_valid("CS50P2") == False

def test_withoutPunctuation():
    is_valid("ab12") == True
    is_valid("ab12!") == False
