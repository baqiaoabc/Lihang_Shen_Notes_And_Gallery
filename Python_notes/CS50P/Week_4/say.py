import cowsay
import sys

# import相当于读取对应的library
from saying import hello

if len(sys.argv) == 2:
    cowsay.trex("hello, " + sys.argv[1])
    cowsay.cow("hello, " + sys.argv[1])
    (sys.argv[1])
