# email = input("what is your email? ").strip()

# username, domain = email.split("@")

# # 如果username是null则返回False；而且结果还是不准确
# if username and ".edu" in domain:
#     print("Valid")
# else:
#     print("Invalid")


# 用regex解决
import re

email = input("what is your email? ").strip()



# ".+@.+" == "..*@..*"

# 这里用\不行，因为123@123?.edu也是正确的；这里我们需要用到raw string
# When a string in Python is prefixed with the letter r or R, as in r'...' 
# and R'...', it becomes a raw string. In contrast to a conventional string, 
# a raw string considers backslashes () as literal characters. When working with 
# strings that include a lot of backslashes, such as regular expressions or directory 
# paths on Windows, raw strings are helpful.
# 为什么呢？参考这篇文章 https://blog.csdn.net/qq_39504519/article/details/107075923

# 如果 if re.search(r".+@.+\.edu", email):
# 则 123@456.edu. 也是有效的

# 此外，if re.search(r"^.+@.+\.edu$", email):
# 则 malan@@@harvard.edu 也是有效的
if re.search(r"^\w+@[a-zA-Z0-9_]+\.edu$", email):
    print("1st Valid")
else:
    print("1st Invalid")

# 和上面不同的是，这里的search加了第三个arguments，它表示不区分大小写，即azAZ09_@163.EDU是有效的
if re.search(r"^\w+@[a-zA-Z0-9_]+\.edu$", email, re.IGNORECASE):
    print("2nd Valid")
else:
    print("2nd Invalid")


# 下面这个是支持malan@harvard.com.edu 或者 lihang.shen@mail.utoronto.edu
if re.search(r"^(\w|\.)+@(\w+\.)?\w+\.edu$", email, re.IGNORECASE):
    print("3rd Valid")
else:
    print("3rd Invalid")