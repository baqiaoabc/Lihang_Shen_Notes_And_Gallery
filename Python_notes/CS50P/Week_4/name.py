import sys

# argv[0]是文件名比如name.py, 可以用“David Malan”
try:
    print("hello, my name is", sys.argv[1])
except IndexError:
    print("Too few arguments")

# if len(sys.argv)<2:
#     print("Too few arguments")
# elif len(sys.argv)>2:
#     print("Too many arguments")
# else:
#     print("hello, my name is", sys.argv[1])


# if len(sys.argv)<2:
#     sys.exit("Too few arguments")
# elif len(sys.argv)>2:
#     sys.exit("Too many arguments")

# print("hello, my name is", sys.argv[1])