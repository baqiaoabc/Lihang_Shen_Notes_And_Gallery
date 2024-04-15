def make_generators_generator(g):
    """Generates all the "sub"-generators of the generator returned by
    the generator function g.

    >>> def every_m_ints_to(n, m):
    ...     i = 0
    ...     while (i <= n):
    ...         yield i
    ...         i += m
    ...
    >>> def every_3_ints_to_10():
    ...     for item in every_m_ints_to(10, 3):
    ...         yield item
    ...
    >>> for gen in make_generators_generator(every_3_ints_to_10):
    ...     print("Next Generator:")
    ...     for item in gen:
    ...         print(item)
    ...
    Next Generator:
    0
    Next Generator:
    0
    3
    Next Generator:
    0
    3
    6
    Next Generator:
    0
    3
    6
    9
    """
    "*** YOUR CODE HERE ***"
    last_ans = list(iter(g()))
    for idx in range(len(last_ans)):
        yield iter(last_ans[:idx+1])

# inheritance

class Wizard:
    identity = 'wizard'
    # 为避免继承报错，使用不定长参数，这点很重要
    def __init__(self,name, **kwags):
        # 这里为了方便不用setter
        if not name:
            raise ValueError("Missing name")
        self.name = name

    def say(self):
        print(f"This is {self.identity}")

# means Student is a subclass of Wizard
class Student(Wizard):
    identity = 'student'
    def __init__(self, name, house, **kwargs):
        # call Student's parent class Wizard
        super().__init__(name,**kwargs)
        self.house = house

    def test_student(self):
        print("inherit from student")

# means Student is a subclass of Wizard
class Professor(Wizard):
    identity = 'professor'
    def __init__(self, name, subject, **kwargs):
        super().__init__(name,**kwargs)
        self.subject = subject
    
    def test_professor(self):
        print("inherit from professor")

# Tutor is both a student and a professor
# in this case, it is a cooperative multiple inheritance
class Tutor (Student, Professor):
    identity = 'tutor'
    def __init__(self,name,house,subject,uniqueTutorId):
        super().__init__(name=name,house=house,subject=subject)
        self.uniqueTutorId = uniqueTutorId


def fib(n):
    """The nth Fibonacci number.

    >>> fib(20)
    6765
    """
    if n == 0 or n == 1:
        print("This is for original fib")
        return n
    else:
        print("This part will call new fib")
        return fib(n-2) + fib(n-1)

def memo(f):
    """Memoize f.

    >>> def fib(n):
    ...     if n == 0 or n == 1:
    ...         return n
    ...     else:
    ...         return fib(n-2) + fib(n-1)
    >>> fib = count(fib)
    >>> fib(20)
    6765
    >>> fib.call_count
    21891
    >>> counted_fib = count(fib)
    >>> fib  = memo(counted_fib)
    >>> fib(20)
    6765
    >>> counted_fib.call_count
    21
    >>> fib(35)
    9227465
    >>> counted_fib.call_count
    36
    """
    cache = {}
    def memoized(n):
        if n not in cache:
            cache[n] = f(n)
        print(f"cachen: {cache[n]} is returned")
        return cache[n]
    return memoized



# Time

def count(f):
    """Return a counted version of f with a call_count attribute.

    >>> def fib(n):
    ...     if n == 0 or n == 1:
    ...         return n
    ...     else:
    ...         return fib(n-2) + fib(n-1)
    >>> fib = count(fib)
    >>> fib(20)
    6765
    >>> fib.call_count
    21891
    """
    def counted(*args):
        counted.call_count += 1
        return f(*args)
    counted.call_count = 0
    return counted
