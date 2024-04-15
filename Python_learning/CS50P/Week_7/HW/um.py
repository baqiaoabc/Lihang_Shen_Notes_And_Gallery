import re
import sys


def main():
    print(count(input("Text: ")))


def count(s):
    cnt = re.findall(r"\bum\b", s, flags = re.IGNORECASE)
    return len(cnt)

if __name__ == "__main__":
    main()