import requests
import sys


try:
    number = float(sys.argv[1])
    response = requests.get("https://api.coindesk.com/v1/bpi/currentprice.json")
    price = response.json()["bpi"]["USD"]["rate_float"]
    print(f"${number*price:,.4f}")
except requests.RequestException:
    sys.exit()
except ValueError:
    sys.exit("Command-line argument is not a number")
except IndexError:
    sys.exit("Missing command-line argument")