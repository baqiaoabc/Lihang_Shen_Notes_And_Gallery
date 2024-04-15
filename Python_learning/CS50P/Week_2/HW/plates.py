def main():
    plate = input("Plate: ")
    if is_valid(plate):
        print("Valid")
    else:
        print("Invalid")


def is_valid(s):
    if (max_six_cha_min_two_str(len(s)) and
        begin_with_two_letters(s[0:2]) and
        number_split_and_not_begin_with_zero(s) and
        no_punctuation(s)
        ):
        return True
    return False

def max_six_cha_min_two_str(length):
    return 2 <= length <= 6

def begin_with_two_letters(begin):
    return begin.isalpha()

def number_split_and_not_begin_with_zero(s):
    judge = True
    for cha in s:
        if cha.isalpha() and judge == False:
            return False
        elif cha.isnumeric() and judge == True and cha == "0":
            return False
        elif cha.isnumeric():
            judge = False
    return True
        

def no_punctuation(s):
    return s.isalnum()

main()

# def main():
#     plate = input("Plate: ")
#     if is_valid(plate):
#         print("Valid")
#     else:
#         print("Invalid")


# def is_valid(s):
#     if len(s) < 2 or len(s) > 6:
#         return False
#     if not s[0].isalpha() or not s[1].isalpha():
#         return False
#     if not all(ch.isalnum() for ch in s):
#         return False

#     flag = False
#     for ch in s:
#         if ch.isdigit():
#             flag = True
#         if ch.isalpha() and flag:
#             return False

#     # 只判断第一个数字是否为0，如果是就返回False
#     for ch in s:
#         if ch.isdigit():
#             print(ch)
#             return ch != "0"

#     # 这是针对只有字符的情况进行判断
#     return True


# if __name__ == "__main__":
#     main()