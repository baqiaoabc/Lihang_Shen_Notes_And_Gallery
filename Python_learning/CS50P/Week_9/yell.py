def main():
    yell("this", "is", "cs50")

def yell(*words):
    # map: 把每一个words里的elements都运行str.upper method
    uppercased = map(str.upper, words)
    # using list comprehension to create a list
    uppercased = [word.upper() for word in words]
    print(*uppercased)


if __name__ == "__main__":
    main()