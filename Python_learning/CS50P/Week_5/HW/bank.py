def main():
    res = value(input("Greeting: "))
    print(res)

def value(ans):
    ans = ans.lower().strip()
    if ans.startswith("hello"):
        return 0
    elif ans.startswith("h"):
        return 20
    else:
        return 100


if __name__ == "__main__":
    main()
