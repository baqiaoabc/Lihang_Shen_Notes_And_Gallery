from pyfiglet import Figlet
import sys
import random

# 创建Figlet对象
figlet = Figlet()

# 获得Figlet支持的font
font_list = figlet.getFonts()

if len(sys.argv) == 3:
    if sys.argv[1] not in ["-f", "--font"] or sys.argv[2] not in font_list:
        sys.exit("Invalid usage")
    font_setting = sys.argv[2]
elif len(sys.argv) == 1:
    font_setting = random.choice(font_list)
else:
    sys.exit("Invalid usage")

msg = input("Input: ")

# 设置Figlet的font
figlet.setFont(font=font_setting)

# 根据font输出msg
print("Output: ", figlet.renderText(msg), sep="\n", end="")
