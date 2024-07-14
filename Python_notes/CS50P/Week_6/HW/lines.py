import sys

if len(sys.argv) == 1:
    sys.exit("Too few command-line arguments")
elif len(sys.argv) > 2:
    sys.exit("Too many command-line arguments")
elif not sys.argv[1].endswith(".py"):
    sys.exit("Not a Python file")

try:
    line_count = 0
    with open(sys.argv[1], encoding="UTF-8") as file:
        for line in file:
            if not (line.lstrip().startswith("#") and line.lstrip() == ""):
                line_count+=1
    print(line_count)
except FileNotFoundError:
    sys.exit("File does not exist")