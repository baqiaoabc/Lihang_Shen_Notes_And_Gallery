import re
import sys


def main():
    print(validate(input("IPv4 Address: ")))


def validate(ip):
    if regex := re.search(
        r"^(1?[0-9]{0,2}|2[0-5]{0,2})\.(1?[0-9]{0,2}|2[0-5]{0,2})\.(1?[0-9]{0,2}|2[0-5]{0,2})\.(1?[0-9]{0,2}|2[0-5]{0,2})$",
        ip,
    ):
        return True
    return False


if __name__ == "__main__":
    main()
