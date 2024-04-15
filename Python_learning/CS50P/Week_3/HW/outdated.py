month = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December"
]

while True:
    try:
        date = input("Date: ")
        if "/" in date:
            # 可以用map()优化一下，避免后面重复强转type
            m,d,y = date.split("/")
            if int(d) > 31 or int(m) > 12 or int(m) < 1 :
                pass
            else:
                print(f"{int(y):04d}-{int(m):02d}-{int(d):02d}")
                break
        else:
            m,d,y = date.split()
            d = d[0:-1]
            if m not in month or int(d) > 31:
                pass
            else:
                print(f"{int(y):04d}-{month.index(m) + 1:02d}-{int(d):02d}")
                break
    except ValueError:
        pass


# months = [
#     "January",
#     "February",
#     "March",
#     "April",
#     "May",
#     "June",
#     "July",
#     "August",
#     "September",
#     "October",
#     "November",
#     "December",
# ]


# while True:
#     date = input("Date: ")
#     if len(date.split("/")) == 3:
#         date = date.split("/")
#         try:
#             month, day, year = map(int, date)
#             if 1 <= month <= 12 and 1 <= day <= 31:
#                 print(f"{year:04d}-{month:02d}-{day:02d}")
#                 break
#         except ValueError:
#             pass
#     else:
#         date = date.split(",")
#         if len(date) == 2:
#             try:
#                 month, day = date[0].split()
#                 month = months.index(month) + 1
#                 day, year = int(day), int(date[1])
#                 if 1 <= month <= 12 and 1 <= day <= 31:
#                     print(f"{year:04d}-{month:02d}-{day:02d}")
#                     break
#             except ValueError:
#                 pass