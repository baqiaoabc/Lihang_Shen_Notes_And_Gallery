# 这里用import pytest是因为后面用了包中的方法
import pytest
from calculator import square

# import sys
# # sys.path.append("..")
# sys.path.append('D:\\code test\\Python_learning\\CS50P\\Week_5')
# from calculator import square

# def main():
#     test_positive()
#     test_negative()
#     test_zero()

def test_positive():
    # if not equal, an AssertionError will be raised
    assert square(2) == 4
    assert square(3) == 9
    print("positive correct")

def test_negative():
    assert square(-2) == 4
    assert square(-3) == 9

def test_zero():
    assert square(0) == 0

def test_str():
    with pytest.raises(TypeError):
        square("cat")

# if __name__ == "__main__":
#     main()