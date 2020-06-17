import math
import time

FACT = math.factorial
POW = math.pow
PI_SQUARED = POW(math.pi, 2)


def func_exercise_1(k):
    ''' Calculates a given k for the formula under the summatory on exercise 1 '''
    return POW(FACT(k), 2) / (POW(k, 2) * FACT((2*k)))


def func_exercise_2(k):
    ''' Calculates a given k for the formula under the summatory on exercise 2 '''
    return POW(-1, k-1) / POW(k, 2)


def solver(precision, func, baseMult):
    ''' Generic function to return value inferior of the given precision calcultating the absolute error '''
    epsilon = POW(10, -precision)
    actual = 0
    k = 1
    termo = 1
    while(abs(baseMult * termo) > epsilon):
        termo = func(k)
        actual += termo
        k += 1

    return [k, actual]


def calc_error(value):
    '''  '''
    return abs(PI_SQUARED - value)


def printer(func, baseMult):
    for precision in range(8, 16):
        start = time.time() * 1000
        exercise = solver(precision, func, baseMult)
        end = time.time() * 1000
        value = baseMult * exercise[1]
        print('value: %.15f, erro: %.15f, precision: %.15f, iterations: %d, time: %d' % (
            value, calc_error(value), POW(10, -precision), exercise[0], end-start))


def main():
    ''' Calculates exercise 1 float precision 10e-8 to 10e-15 '''
    printer(func_exercise_1, 18)
    ''' Calculates exercise 2 float precision 10e-8 to 10e-15 '''
    printer(func_exercise_2, 12)


def epsilon():
    print("Epsilon maquina:")
    eps = 1.0
    while eps + 1 > 1:
        eps = eps / 2
    print(eps)


epsilon()

main()
