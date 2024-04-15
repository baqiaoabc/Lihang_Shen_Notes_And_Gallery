import argparse

# 可以用python argparseIllustration.py -h 查看arguments具体是干嘛的
parser = argparse.ArgumentParser(description="Meow like a cat")

# 设定command line 输入 arguments 的规则
parser.add_argument("-n",default=1,help="number of times to meow",type=int)
parser.add_argument("-nc",default=1,help="number of times to meow",type=int)
# 获取所有的command line arguments
args = parser.parse_args()

# 这里的arg.n是因为我们在上面添加了argument n；也可以添加别的，比如nc
for _ in range(args.n):
    print("meow")