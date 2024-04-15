import re
import sys

# 这个是regex用法一
def main():
    print(convert(input("Hours: ")))

def convert(s):
    # check data format
    matches = re.search(r"^([1-9]|1[0-2]):?(?:[0-5][0-9])? (AM|PM) to ([1-9]|1[0-2]):?(?:[0-5][0-9])? (AM|PM)$", s)
    check(matches)

    data = s.split(" ")
    for i in range(1,5,3):
        if data[i] == "AM":
            if "12:" in data[i-1] and ":" in data[i-1]:
                hour,minute = data[i-1].split(":")
                data[i-1] = f"00:{minute}"
            elif "12" in data[i-1] and ":" not in data[i-1]: 
                data[i-1] = f"00:00"
            elif "12:" not in data[i-1] and ":" in data[i-1]:
                hour,minute = data[i-1].split(":")
                data[i-1] = f"{int(hour):02}:{minute}"
            else:
                data[i-1] = f"{int(data[i-1]):02}:00"
        else:
            if "12:" in data[i-1] and ":" in data[i-1]:
                hour,minute = data[i-1].split(":")
                data[i-1] = f"12:{minute}"
            elif "12" in data[i-1] and ":" not in data[i-1]: 
                data[i-1] = f"12:00"
            elif "12:" not in data[i-1] and ":" in data[i-1]:
                hour,minute = data[i-1].split(":")
                data[i-1] = f"{int(hour)+12}:{minute}"
            else:
                data[i-1] = f"{int(data[i-1])+12}:00"
    return f"{data[0]} to {data[3]}"


def check(matches):
    if not matches:
        raise ValueError

if __name__ == "__main__":
    main()



# # regex用法二
# import re


# def main():
#     print(convert(input("Hours: ")))


# def convert(s):
#     if match := re.match(
#         r"(\d{1,2}):?(\d{2})? (AM|PM) to (\d{1,2}):?(\d{2})? (AM|PM)", s
#     ):
#         st_hour, st_min, st_ap, ed_hour, ed_min, ed_ap = match.groups()

#         if st_min is None:
#             st_min = 0

#         if ed_min is None:
#             ed_min = 0

#         st_hour, st_min, ed_hour, ed_min = map(int, [st_hour, st_min, ed_hour, ed_min])

#         if st_ap == "PM" and st_hour != 12:
#             st_hour += 12
#         elif st_ap == "AM" and st_hour == 12:
#             st_hour = 0

#         if ed_ap == "PM" and ed_hour != 12:
#             ed_hour += 12
#         elif ed_ap == "AM" and ed_hour == 12:
#             ed_hour = 0

#         if (
#             not 0 <= st_hour <= 23
#             or not 0 <= st_min <= 59
#             or not 0 <= ed_hour <= 23
#             or not 0 <= ed_min <= 59
#         ):
#             raise ValueError("Invalid arguments")

#         return f"{st_hour:02d}:{st_min:02d} to {ed_hour:02d}:{ed_min:02d}"
#     raise ValueError("Invalid arguments")


# if __name__ == "__main__":
#     main()