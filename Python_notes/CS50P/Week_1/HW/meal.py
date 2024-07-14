def main():
    convert_time = convert(input("What time is it? "))

    if 7 <= convert_time <= 8:
        print("breakfast time")
    elif 12 <= convert_time <= 13:
        print("lunch time")
    elif 18 <= convert_time <= 19:
        print("dinner time")


def convert(time):
    hours, minutes = map(int, time.replace("a.m.", "").replace("p.m.", "").split(":"))

    # 这里考虑到了12：30 p.m.是下午的因素
    if "p.m." in time and hours != 12:
        hours += 12
    if "a.m." in time and hours == 12:
        hours = 0
    return hours + minutes / 60


if __name__ == "__main__":
    main()