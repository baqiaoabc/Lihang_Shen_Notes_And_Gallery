"""
implement a function called convert that accepts a str as input and 
returns that same input with any :) converted to 🙂 (otherwise known as a slightly smiling face) 
and any :( converted to 🙁 (otherwise known as a slightly frowning face). 
All other text should be returned unchanged.
"""

def main():
    print(convert(input("your input: ")))

def convert(input):
    result = input.replace(":)", "🙂").replace(":(", "🙁")

    return result

main()