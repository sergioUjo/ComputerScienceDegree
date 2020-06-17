import math
import sys


POW = math.pow
SIN = math.sin
COS = math.cos
a = 0
b = 3


def calcN( h ):
    return (b-a)/h 

def f(x):
    return SIN(COS(SIN(POW(x,2))))

def somatorio( i, n, h):
    cur = a + h*i
    sum = 0
    while( i <= n ):
        sum += f(cur)
        cur += h
        i+=1
    return sum
    
def somatorio_simpson( i, n, h):
    cur = a + h*i
    sum = 0
    dois = False
    while( i <= n ):
        sum += ( 2 if dois else 4 ) * f(cur)
        dois = not(dois)
        cur += h
        i+=1
    return sum 

def rectangulos(h):
    n = calcN(h)
    return h * somatorio(0, n-1, h)

def trapezios(h):
    n = calcN(h)
    return h/2 * ( f(a) + f(b) ) + h * somatorio(1, n-1, h)

def simpson (h):
    n = calcN(h)
    return h/3 *( f(a) + somatorio_simpson(1,n-1, h) + f(b) )

#rectangulos 
# 10^-5
print("rectangulos: erro = 10^-5")
print(rectangulos(4.8*10**-6))

# 10^-7
print("rectangulos: erro = 10^-7")
#print(rectangulos(4.8*10**-8))

# 10^-9
print("rectangulos: erro = 10^-9")
#print(rectangulos(4.8*10**-10))

#trapezios 
# 10^-5
print("trapezios: erro = 10^-5")
print(trapezios(0.001))

# 10^-7
print("trapezios: erro = 10^-7")
print(trapezios(0.0001))

# 10^-9
print("trapezios: erro = 10^-9")
print(trapezios(0.00001))


#simpson 
# 10^-5
print("simpson: erro = 10^-5")
print(simpson(0.0001))

# 10^-7
print("simpson: erro = 10^-7")
print(simpson(0.003))

# 10^-9
print("simpson: erro = 10^-9")
print(simpson(0.0001))
