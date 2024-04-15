from validator_collection import checkers


def main():
    if checkers.is_email(input("What's your email address? ")):
        print("Valid")
    else:
        print("Invalid")


if __name__ == "__main__":
    main()