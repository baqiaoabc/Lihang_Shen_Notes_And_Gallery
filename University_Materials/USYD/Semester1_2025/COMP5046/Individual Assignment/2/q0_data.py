# This is the function you need to implement
import json

def read_data(filename):
    """Read the data from a json file.

    Keyword arguments:
    filename -- the name of a json file
    """
    # TODO
    # First dict
    first = {'train':[], 'dev':[], 'test':[]}

    # Second set
    second = set()
    # 只有jsonl可以按行读取，这里必须整体读取
    with open(filename) as f:
        dataset = json.load(f)

    # traverse all data in the dataset
    for dict_line in dataset:
        # whether dict_line belongs to train, dev, or test
        if dict_line['data'] == "train":
            first['train'].append((dict_line['question'],dict_line['sql']))
        elif dict_line['data'] == "test":
            first['test'].append((dict_line['question'],dict_line['sql']))
        elif dict_line['data'] == "dev":
            first['dev'].append((dict_line['question'],dict_line['sql']))
        second.add(dict_line['sql'])
    data = first, second
    
    return data
