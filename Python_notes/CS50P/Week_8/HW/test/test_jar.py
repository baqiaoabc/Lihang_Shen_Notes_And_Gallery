from jar import Jar
import pytest


def test_init():
    jar = Jar()
    assert jar.capacity == 12
    jar = Jar(3)
    assert jar.capacity == 3
    with pytest.raises(ValueError):
        jar = Jar(-1)

def test_str():
    jar = Jar()
    assert str(jar) == ""
    jar.deposit(1)
    assert str(jar) == "🍪"
    jar.deposit(11)
    assert str(jar) == "🍪🍪🍪🍪🍪🍪🍪🍪🍪🍪🍪🍪"


def test_deposite():
    jar = Jar(12)
    assert jar.size == 0
    jar.deposit(1)
    assert jar.size == 1
    with pytest.raises(ValueError):
        jar.deposit(12)
    with pytest.raises(ValueError):
        jar.deposit(-1)


def test_withdraw():
    jar = Jar(12)
    assert jar.size == 0
    with pytest.raises(ValueError):
        jar.withdraw(1)
    with pytest.raises(ValueError):
        jar.withdraw(-1)
    jar.deposit(3)
    jar.withdraw(1)
    assert jar.size == 2