import numpy as np
import math
def predict_word_and_dist(vector_dictionary: list[list[float]], matrix: list[list[float]], words: list[int]) -> tuple[int, float, list[float]]:    # [[1, 1], [0, 0.2]]

    token = [vector_dictionary[idx] for idx in words]
    # token = [[1, 1], [0, 0.2]]

    token_len = len(token[0])
    avg = [0]*token_len
    # avg = [0.5,0.6]
    for t in token:
        for i in range(token_len):
            avg[i] += t[i]
    for i in range(len(avg)):
        avg[i]= avg[i]/len(token) # use the length of token = number of all added vector


    # [1.32  1.04  1.156]
    dot_product = []
    for v in matrix:
        dot_product.append(sum(avg[i]*v[i] for i in range(token_len)))

    # we need to consider about too big exponential and too small exponential value
    exponential=[]
    for v in dot_product:
        print(v)
        exponential.append(math.exp(v-max(dot_product)))
        # [e^(1.32-1.156), e^(1.04-1.156), e^(1.156-1.156)]
    print(exponential)

    # it is not normalization x_norm = (x-min(X))/(max(X)-min(X))；since the smallest is not 0 and larges is not 1 in "output"
    # it is softmax
    sum_exp = sum(exponential)
    p = exponential/sum_exp

    token_id = 0
    token_p= 0
    for i in range(len(p)):
        if p[i] > token_p:
            token_p = p[i]
            token_id = i
    return token_id, token_p, p


print(predict_word_and_dist([[1, 1], [0, 0.2], [1.1, 1.2]],
                            [[1.2, 1.2], [1, 0.9], [1.1, 1.01]],
                            [0, 1]))

