import json

data = json.load(open('tasks.json'))

file_list = open("data-for-slate.file-list.txt", 'w')
for i, task in enumerate(data):
    text_out = open(f'data-for-slate.dialogue.{i}.txt', 'w')
    anno_out = open(f'data-for-slate.dialogue.{i}.txt.annotations', 'w')
    spacy_out = open(f'data-for-slate.dialogue.{i}.txt.spacy', 'w')

    print(f'data-for-slate.dialogue.{i}.txt', file=file_list)

    text = task['data']['text']
    print(text, file=text_out)

    char_to_token = {}
    token = 0
    for pos, char in enumerate(text):
        char_to_token[pos] = token
        if char == ' ' and pos != 0:
            token += 1
    for pred in task['predictions']:
        if pred['model_version'] == 'en_core_web_lg':
            for item in pred['result']:
                info = item['value']
                start, end, labels = info['start'], info['end'], info['labels']
                start = char_to_token[start]
                end = char_to_token[end]
                labels = ' '.join(labels)
                print(f"((0, {start}), (0, {end})) - {labels}", file=spacy_out)
                print(f"((0, {start}), (0, {end})) - {labels}", file=anno_out)
    text_out.close()
    anno_out.close()
    spacy_out.close()
file_list.close()
