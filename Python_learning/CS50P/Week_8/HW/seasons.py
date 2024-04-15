from datetime import date
import inflect
import sys
import re


def main():
    final_answer()


def final_answer():
    try:
        birth_date = re.search(r"^\d{4}-\d{2}-\d{2}", input("Date of Birth: "))
        if not birth_date:
            sys.exit("Invalid date")
        birth_date = date.fromisoformat(birth_date.group())
    except ValueError:
        sys.exit("Invalid date")

    today = date.today()

    minutes_difference = minutes_between_dates(birth_date, today)

    p = inflect.engine()
    print(f"{p.number_to_words(minutes_difference, andword='').capitalize()} {p.plural('minute', minutes_difference)}") 



def minutes_between_dates(date_1, date_2):
    return (date_2 - date_1).days * 24 * 60


if __name__ == "__main__":
    main()