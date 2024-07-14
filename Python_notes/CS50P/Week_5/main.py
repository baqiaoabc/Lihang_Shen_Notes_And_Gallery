# from subpackage.submodul import ok
# import subpackage.submodul
import subMain
# y = ok()
y = subMain.subpackage.submodul.ok()
print(subMain.cowsay.cow("hello"))


# ================================================
# import sys
# sys.path.append('D:\\code test\\Python_learning\\CS50P\\Week_6')
# from module import ok
subMain.ok()

# ===============================================
# import sys
# sys.path.append("..\\\Week_6")
# from module import ok
# ok()