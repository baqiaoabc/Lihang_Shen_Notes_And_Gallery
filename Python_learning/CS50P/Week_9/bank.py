# # global variable
# balance = 0


# def main():
#     # 不要让local var和global var重名
#     # 下面的声明是prevents creation of a local variable called balance
#     global balance
#     print("Balance:", balance)
#     deposit(100)
#     withdraw(50)
#     print("Balance:", balance)



# def deposit(n):
#     # 不加上下面语句时，该function会认为gloabl是一个local variable
#     global balance
#     balance += n

# def withdraw(n):
#     global balance
#     balance -= n


# if __name__ == "__main__":
#     main()

# global variable易错点
# def setcake(taste):
#     global cake
#     cake = taste
# def caketaste():
#     print cake #Output is whatever taste was

# caketaste()
# NameError: global name 'cake' is not defined
# This happens because when caketaste is called, no assignment to cake has occurred. 
# It will only occur after setcake has been called.

class Account:
    def __init__(self):
        self._balance = 0
        self.balance = 100
        self.balance_1 = 3

    @property
    def balance_1(self):
        return self._balance
    
    # @balance.setter
    # def balance(self,n):
    #     if n == 77 or n == 0:
    #         self._balance = n


def main():
    account = Account()
    print("Balance:", account._balance)
    print("Balance:", account.balance)
    account._balance = 77
    account.balance = 88
    print("Balance:", account.balance)
    print("Balance:", account._balance)


if __name__ == "__main__":
    main()
