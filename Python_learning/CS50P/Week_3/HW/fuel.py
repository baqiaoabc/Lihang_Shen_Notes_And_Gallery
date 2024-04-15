while True:
    try:
        decimal = eval(input("Fraction: "))
        if decimal <= 0.01:
            print("E")
            break
        elif 1 >= decimal >= 0.99:
            print("F")
            break
        elif 0.1< decimal < 0.99:
            print(f"{decimal*100:.0f}%")
            break
    except (ValueError, NameError, ZeroDivisionError,SyntaxError):
        pass
