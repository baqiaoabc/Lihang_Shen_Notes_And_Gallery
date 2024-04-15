from twttr import shorten

def test_letter_a():
    assert shorten("A") == ""
    assert shorten("ac!") == "c!"
    assert shorten("c!a") == "c!"
    assert shorten("12a34") == "1234"

def test_letter_e():
    assert shorten("E") == ""
    assert shorten("ec!") == "c!"
    assert shorten("c!e") == "c!"
    assert shorten("12e34") == "1234"

def test_letter_i():
    assert shorten("I") == ""
    assert shorten("ic!") == "c!"
    assert shorten("c!i") == "c!"
    assert shorten("12i34") == "1234"

def test_letter_o():
    assert shorten("O") == ""
    assert shorten("oc!") == "c!"
    assert shorten("c!o") == "c!"
    assert shorten("12o34") == "1234"

def test_letter_u():
    assert shorten("U") == ""
    assert shorten("uc!") == "c!"
    assert shorten("c!u") == "c!"
    assert shorten("12u34") == "1234"

def test_letter_mixed():
    assert shorten("aEiOu") == ""
    assert shorten("1a2e3E4O") == "1234"