def main():
    shorten(input("Input: "))

def shorten(msg):
    res = "".join([ch for ch in msg if ch.lower() not in "aeiou"])
    return res

if __name__ == "__main__":
    main()