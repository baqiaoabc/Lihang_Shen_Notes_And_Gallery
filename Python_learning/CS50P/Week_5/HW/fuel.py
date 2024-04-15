# 我这里用的是eval()所以有些错误不会报
# def main():
#     frac = convert(input("Fraction: "))
#     res = gauge(frac)
#     print(res)


# def convert(fraction):
#     percentage = eval(fraction)*100
#     if percentage > 100:
#         raise ValueError
#     return round(percentage)


# def gauge(percentage):
#     if percentage <= 1:
#         return("E")
#     elif 100 >= percentage >= 99:
#         return("F")
#     elif 1< percentage < 99:
#         return(f"{percentage}%")


# if __name__ == "__main__":
#     main()



def main():
    while True:
        try:
            print(gauge(convert(input("Fraction: "))))
            break
        except (ValueError, ZeroDivisionError):
            pass


def convert(fraction):
        x, y = map(int, fraction.split("/"))
        if y == 0:
            raise ZeroDivisionError
        if x > y:
            raise ValueError
        return round(x / y * 100)



def gauge(percentage):
    if percentage <= 1:
        return "E"
    elif percentage >= 99:
        return "F"
    else:
        return f"{percentage}%"


if __name__ == "__main__":
    main()