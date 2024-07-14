from bank import value

def test_beginWithHello():
    assert value("Hello") == 0
    assert value("hello") == 0
    assert value("Hello, world!") == 0

def test_beginWithH():
    assert value("hi, Jim") == 20
    assert value("Hi, Jim") == 20

def test_notBeginWithH():
    assert value("what's up!") == 100
    assert value("yHoo!") == 100
    assert value("grettings!!!!") == 100