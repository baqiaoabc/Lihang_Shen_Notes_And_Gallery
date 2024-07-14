# lowercase all input string
# Punctuation and whitespace should be outputted unchanged. 
# prompt the user explicitly, as by passing a str of your own as an argument to input.

def main():
    print("prompt: you can lowercase any string by type it below. For example, after you type \"Hello\", you will get \"hello\"")
    result = input("so, what is the string you want to lowercase? ").lower()
    print(result)


main()