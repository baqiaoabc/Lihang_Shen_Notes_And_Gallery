import sys

def get_anno(filename):
    try:
        with open(filename) as src:
            lines = src.readlines()
            anno = set()
            for line in lines:
                line = line.strip()
                if len(line) == 0:
                    return "Contains blank lines"
                parts = line.split("-")
                if len(parts) != 2:
                    return "Line does not contain exactly one dash"
                span = eval(parts[0].strip())
                label = parts[1].strip().split()
                if isinstance(span[0], int):
                    span = (span, span)
                anno.add((span, tuple(label)))
            return anno
    except:
        return "Unknown error"

def check_all(data):
    for span, label in data:
        for item in label:
            if item not in [ "FAC", "GPE", "LOC", "ORG", "PER", "VEH"]:
                return f"invalid label {item}"
        if not isinstance(span, tuple):
            return f'data format for {span}'
        for pos in span:
            if not isinstance(pos, tuple):
                return f'data format for {pos} in {span}'
            for num in pos:
                if not isinstance(num, int):
                    return f'data format for {num} in {span}'
                if num < 0:
                    return f'data value for {num} in {span}'
        if span[0][0] > span[1][0]:
            return f'invalid span {span}'
        if span[0][0] == span[1][0] and span[0][1] > span[1][1]:
            return f'invalid span {span}'
    return ''


if __name__ == '__main__':
    data = get_anno(sys.argv[1])
    if isinstance(data, str):
        raise Exception(f"We were unable to load your data file. The error was: {data}")
    issue = check_all(data)
    if issue:
        raise Exception(f"There appears to be an invalid annotation in your data. The problem was: {issue}")
