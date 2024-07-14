from operator import floordiv, mod
from doctest import testmod,run_docstring_examples

def divide_exact(n,d=10):
    """
    Return the quotient and remainder of diciding N by D

    >>> q, r = divide_exact(2013,10)
    >>> q
    201
    >>> r
    3
    """
    return floordiv(n,d), mod(n,d)

def search(f):
		x_diff = 0
		while True:
				if f(x_diff):
						return x_diff
				x_diff+=1
# 结合2个lambda function和自己定义的function search
# 这里返回的是一个function
def inverse(f):
		"""Return g(y) such that g(f(x)) -> x; 也就是说这里返回的g(y)
		是用来抵消掉输入的f函数的
		y对应的是 输入到g(y)中的值
		x对应的是 输入到f(x)中的值
		"""
		return lambda y: search(lambda x: f(x) == y)
def square(x):
    return x*x