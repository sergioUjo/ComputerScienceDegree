# Bisections: Slides
# Simple interactive: Slides
# Newtons: Slides + https://www.math.usm.edu/lambers/mat460/fall09/lecture10.pdf

import math
import sys

POW = math.pow
SIN = math.sin
COS = math.cos
EPSILON_EX1 = POW(10, -8)
EPSILON_EX2 = POW(10, -6)


def exercice_one_formula(x):
    return POW(x, 2) - x - SIN(x + 0.15)


def exercice_one_formula_derivative(x):
    return 2*x - COS(x + 0.15) - 1


def exercice_two_formula(x):
    return 0.5*POW(x, 3) - 4*POW(x, 2) + 6*x - 2


def exercice_two_formula_derivative(x):
    return 1.5*POW(x, 2) - 8*x + 6


def bisection_method(func, a, b, epsilon):
    m = a
    vfa = func(a)
    erro_iter = abs(b-a)
    nmr_iter = 0

    while (erro_iter > epsilon):
        nmr_iter += 1
        m = (a+b)/2
        if (func(m) == 0):
            erro_iter = 0
        elif(func(m) * vfa < 0):
            b = m
        else:
            a = m
        erro_iter /= 2

    return ["nmr_it: " + repr(nmr_iter), "value: " + repr(m), "error: " + repr(erro_iter)]


def simple_iteractive_method(func, x_0, n_max, epsilon):
    nmr_iter = 1
    while (nmr_iter <= n_max):
        x_1 = func(x_0)
        erro_iter = abs(x_1 - x_0)
        if (erro_iter < epsilon):
            return ["nmr_it: " + repr(nmr_iter), "value: " + repr(x_1), "error: " + repr(erro_iter)]
        nmr_iter += 1
        x_0 = x_1
    return ["it was not possible to find any solution with the pretend error after " + repr(n_max) + " iteractions"]


def newtons_method(func, func_dev, x_0, n_max, epsilon):
    nmr_iter = 1
    while(nmr_iter < n_max):
        x_1 = func(x_0)
        dev_x_1 = func_dev(x_0)

        if (abs(dev_x_1) < epsilon):
            return ["Denominator too small to compute"]

        x_1 = x_0 - (x_1 / dev_x_1)
        erro_iter = abs(x_1 - x_0)
        if (erro_iter < epsilon):
            return ["nmr_it: " + repr(nmr_iter), "value: " + repr(x_1), "error: " + repr(erro_iter)]

        nmr_iter += 1
        x_0 = x_1
    return ["Did not converge or it was not possible to find any solution with the pretend error after " + repr(n_max) + " iteractions"]


print("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++ BISECTION +++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")
print("     Using assignment exercise one formula:")
print("      => " + repr(bisection_method(exercice_one_formula, 1.0, 2.0, EPSILON_EX1)))

print("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++ SIMPLE ITERACTIVE +++++++++++++++++++++++++++++++++++++++++++++++++++++\n")
print("     Using assignment exercise one formula:")
print("      => " +
      repr(simple_iteractive_method(exercice_one_formula, 1, 11, EPSILON_EX1)))

print("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ NEWTONS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")
print("     Using assignment exercise one formula:")
print("      => " + repr(newtons_method(exercice_one_formula,
                                        exercice_one_formula_derivative, 1.6, 20, EPSILON_EX1)))
print("     Using assignment exercise two formula with x = 3.5:")
print("      => " + repr(newtons_method(exercice_two_formula,
                                        exercice_two_formula_derivative, 3.5, 20, EPSILON_EX2)))
print("     Using assignment exercise two formula with x = 6.5:")
print("      => " + repr(newtons_method(exercice_two_formula,
                                        exercice_two_formula_derivative, 6.5, 20, EPSILON_EX2)))
print("     Using assignment exercise two formula with x = 4.4:")
print("      => " + repr(newtons_method(exercice_two_formula,
                                        exercice_two_formula_derivative, 4.4, 20, EPSILON_EX2)))
print("  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
