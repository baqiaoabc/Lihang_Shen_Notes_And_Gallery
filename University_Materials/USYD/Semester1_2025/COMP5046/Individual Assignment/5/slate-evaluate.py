import json

files = [name.strip() for name in open('data-for-slate.file-list.txt')]

def read_anno(filename):
    ans = set()
    for line in open(filename):
        parts = line.strip().split("-")
        labels = parts[1].split()
        span = eval(parts[0])
        start, end = span
        if isinstance(span[0], int):
            start = span
            end = span
        for label in labels:
            ans.add((start, end, label))
    return ans

scores = {}
for filename in files:
    anno = read_anno(filename + ".annotations")
    pred = read_anno(filename + ".spacy")

    tp = len(anno.intersection(pred))
    fp = len(pred.difference(anno))
    fn = len(anno.difference(pred))
    score = scores.setdefault('spacy', {})
    score['tp'] = score.get('tp', 0) + tp
    score['fp'] = score.get('fp', 0) + fp
    score['fn'] = score.get('fn', 0) + fn

for model, vals in scores.items():
    p = vals['tp'] / (vals['tp'] + vals['fp'])
    r = vals['tp'] / (vals['tp'] + vals['fn'])
    f = 2 * p * r / (p + r)
    print("Results for {}:\nPrecision {:.2f}\nRecall {:.2f}\nF-1 {:.2f}".format(model, 100 * p, 100 *r, 100 *f))
