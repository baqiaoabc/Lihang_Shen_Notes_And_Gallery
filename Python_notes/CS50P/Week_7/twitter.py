import re


url = input("URL: ").strip()

# 第一种提取名字的方法
# username = re.sub(r"^(https?://)?(www\.)?twitter\.com/", "", url)
# print(f"Username {username}")

# 第二种提取名字的方法

if matches := re.search(r"^https?://(www\.)?twitter\.com/([a-zA-Z0-9_ ]+)$", url, re.IGNORECASE):
    print(f"Username:", matches.group(2))


