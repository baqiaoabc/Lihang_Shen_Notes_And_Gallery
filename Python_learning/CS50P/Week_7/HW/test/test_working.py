import pytest
from working import convert


def test_error():
    with pytest.raises(ValueError):
        convert("9:60 AM to 5:60 PM")
        convert("9:99 PM to 5:20 AM")
        convert("cat")
        convert("9:22 am to 5:22 pm")
        convert("9 AM - 5 PM")
        convert("09:00 AM - 17:00 PM")

def test_normal():
    assert convert("9 AM to 5 PM") == "09:00 to 17:00"
    assert convert("9:30 AM to 5:00 PM") == "09:30 to 17:00"
    assert convert("9 AM to 5:30 PM") == "09:00 to 17:30"

    assert convert("10 PM to 8 AM") == "22:00 to 08:00"
    assert convert("10:30 PM to 8:50 AM") == "22:30 to 08:50"
    assert convert("10 PM to 8:50 AM") == "22:00 to 08:50"

    assert convert("9 AM to 5 AM") == "09:00 to 05:00"
    assert convert("9:30 AM to 5:00 AM") == "09:30 to 05:00"
    assert convert("9 AM to 5:30 AM") == "09:00 to 05:30"

    assert convert("9 PM to 5 PM") == "21:00 to 17:00"
    assert convert("9:30 PM to 5:00 PM") == "21:30 to 17:00"
    assert convert("9 PM to 5:30 PM") == "21:00 to 17:30"

def test_corner():
    assert convert("12:59 AM to 12:59 PM") == "00:59 to 12:59"
    assert convert("12:59 PM to 12:59 AM") == "12:59 to 00:59"

    assert convert("8:12 AM to 9:12 AM") == "08:12 to 09:12"

def test_convert():
    assert convert("9:00 AM to 5:00 PM") == "09:00 to 17:00"
    assert convert("9 AM to 5 PM") == "09:00 to 17:00"
    assert convert("10:30 PM to 8:50 AM") == "22:30 to 08:50"
    assert convert("10 PM to 8 AM") == "22:00 to 08:00"
    assert convert("12:00 PM to 12:00 AM") == "12:00 to 00:00"
    assert convert("12:00 AM to 12:00 PM") == "00:00 to 12:00"

    with pytest.raises(ValueError):
        convert("9:60 AM to 5:60 PM")
    with pytest.raises(ValueError):
        convert("9:60 AM to 5:00 PM")
    with pytest.raises(ValueError):
        convert("13:00 PM to 5:00 PM")
    with pytest.raises(ValueError):
        convert("9 AM - 5 PM")
    with pytest.raises(ValueError):
        convert("09:00 AM - 17:00 PM")