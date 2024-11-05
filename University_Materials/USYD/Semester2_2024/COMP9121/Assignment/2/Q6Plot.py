import matplotlib.pyplot as plt
import numpy as np
import math


# piecewise function for client-server
def client_server(n):
    return np.piecewise(n, [n > 7, n <= 7], [lambda n: n * 2285.714286, 16000])


# piecewise function for p2p
# def p2p(n):
#     return np.piecewise(n, [n], [lambda n: n * 2285.714286, lambda n: n*8*pow(10,3)/(3.5+n)])
def p2p(n):
    return np.piecewise(n, [n], [16000])

n = np.linspace(1, 100, 100)
y_1 = client_server(n)
y_2 = p2p(n)

plt.plot(n, y_1, label='Client-Server')

plt.plot(n, y_2, label='P2P')

plt.xlabel('N')
plt.ylabel('Minimum Distance Time')

plt.legend()
plt.show()
