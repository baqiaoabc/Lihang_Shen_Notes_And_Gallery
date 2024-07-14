import re
import sys


def main():
    print(parse(input("HTML: ")))


def parse(s):
    matches = re.search(r'^<iframe(?:.*)src="https?://www.youtube.com/embed/([a-z0-9]*)"(?:.*)></iframe>$', s, flags = re.IGNORECASE)
    if matches:
        return "https://youtu.be/" + matches.group(1)
    return None


if __name__ == "__main__":
    main()

