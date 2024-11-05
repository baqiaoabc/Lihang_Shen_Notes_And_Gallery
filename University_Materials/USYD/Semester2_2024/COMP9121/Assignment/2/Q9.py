import hashlib
import time

start = time.time()

s_original = "My number is 490051481 and I love COMP9121."
originallen = len(s_original) # 43

s = bytes(s_original,'utf-8')
hashresult = hashlib.sha384(s).hexdigest()

# print(hashresult)
# a83a1dc90bbde609659c8b178d24d5c8eb7922d436e75a592b2bf423227d47ecf53a0599ca12ab505b94efc33cbfcf56

nounce = 0
while True:
    s_with_nounce = s_original + str(nounce)
    s = bytes(s_with_nounce,'utf-8')
    hashresult = hashlib.sha384(s).hexdigest()
    if hashresult[:6] == "000000":
        break
    nounce +=1
end = time.time()
execution_time = end - start
print(execution_time)
print(nounce) # 14258490
print(hashlib.sha384(bytes("My number is 490051481 and I love COMP9121.14258490",'utf-8')).hexdigest()[:6] == "000000")