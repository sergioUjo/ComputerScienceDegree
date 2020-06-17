----- Aulas 1 e 2-----

-- 1
inc:: Num a => a -> a
inc x = x + 1

dobro:: Num a => a -> a
dobro x = x + x

quadrado:: Num a => a -> a
quadrado x = x * x

media:: Float -> Float -> Float
media x y = (x + y)/2

-- 2
triangulo:: Float -> Float -> Float -> Bool
triangulo a b c | a>b+c || b>a+c || c>a+b = False
                | otherwise = True

-- 3
area:: Float -> Float -> Float -> Float
area a b c = sqrt (s * (s-a) * (s-b) * (s-c))
           where s = (a+b+c)/2

-- 4
metades:: [a] -> ([a],[a])
metades x = (take s x, drop s x)
          where s = (length x) `div` 2

-- 5
-- a)
last2:: [a] -> a
last2 x = (reverse x) !! 0

last3:: [a] -> a
last3 x = x !! s
        where s = (length x) - 1

last4:: [a] -> [a]
last4 a =  take 1 b
        where b = reverse a

last5:: [a] -> [a]
last5 a = drop s a
        where s = (length a) - 1

-- b)
init2:: [a] -> [a]
init2 x = take s x
        where s = (length x) - 1

init3:: [a] -> [a]
init3 x = reverse (drop 1 (reverse x))

-- 6
-- a)
binom:: Int -> Int -> Int
binom n k = product [1..n] `div` (product [1..k] * product [1..(n-k)])

-- 7
-- a)
max2:: Float -> Float -> Float -> Float
max2 x y z | x>y && x>z = x
           | y>x && y>z = y
           | otherwise = z

min2:: Float -> Float -> Float -> Float
min2 x y z | x<y && x<z = x
           | y<x && y<z = y
           | otherwise = z

mid:: Float -> Float -> Float -> Float
mid x y z | (x < y && x > z) || (x < z && x > y) = x
           | (y < x && y > z) || (y < z && y > x) = y
           | otherwise = z

-- b)
max3:: Float -> Float -> Float -> Float
max3 x y z = max x (max y z)

min3:: Float -> Float -> Float -> Float
min3 x y z = min x (min y z)

-- 8
-- a)
maxOccurs:: Integer -> Integer -> (Integer, Integer)
maxOccurs x y = (max x y, if x==y then 2 else 1)

-- b)
orderTriple:: (Float, Float, Float) -> (Float, Float, Float)
orderTriple (x, y, z) = (min2 x y z, mid x y z, max2 x y z)

-- 9
classifica:: Int -> String
classifica x | x<=9 = "reprovado"
             | x<=12 = "suficiente"
             | x<=15 = "bom"
             | x<=18 = "muito bom"
             | x<=20 = "muito bom com distinção"

classifica2:: Int -> String
classifica2 x = if x<=9 then "reprovado" else
               if x<=12 then "suficiente" else
               if x<=15 then "bom" else
               if x<=18 then "muito bom" else
               "muito bom com distinção"

-- 10
xor:: Bool -> Bool -> Bool
xor False False = False
xor False True = True
xor True False = True
xor True True = False

-- 11
safetail:: [a] -> [a]
safetail x = if length x == 0 then [] else tail x

safetail2:: [a] -> [a]
safetail2 x | length x == 0 = []
            | otherwise = tail x

safetail3:: [a] -> [a]
safetail3 [] = []
safetail3 x = tail x

-- 12
-- a)
curta:: [a] -> Bool
curta x = if length x < 3 then True else False

-- b)
curta2:: [a] -> Bool
curta2 [] = True
curta2 [_] = True
curta2 [_,_] = True
curta2 _ = False
-- ou
curta3:: [a] -> Bool
curta3 [] = True
curta3 [x] = True
curta3 [x,y] = True  --só 2 elementos
curta3 (x:xs) = False  --lista indefenida. não é preciso ser assim (x:y:xs), porque lê por ordem


----- Aula 2 -----

-- 14
-- a) [Char]
-- b) (Char, Char, Char)
-- c) [(Bool, Char)]
-- d) ([Bool], [Char])
-- e) [[a] -> [a]]
-- f) [Bool -> Bool]

-- 15
-- 1) f:: (Int, Int) -> Int
-- 2) f:: a -> Int   g:: Int -> a
-- 3) f:: a -> Int -> Int   g:: a
-- 4) f:: a -> [Int] -> [Int]   g:: a
-- 5) f:: (Int, Int) -> [Int -> Int]

-- 17
-- a) segundo:: [a] -> a
-- b) trocar:: (a, b) -> (b, a)
-- c) par:: a -> b -> (a, b)
-- d) dobro:: Num a => a -> a
-- e) metade:: Fractional a => a -> a
-- f) minuscula:: Char -> Bool
-- g) intervalo:: Ord a => a -> a -> a -> Bool
-- h) palindromo:: Eq a => [a] -> Bool
-- i) twice:: (a -> a) -> a -> a

-- 20
-- (2,[3]) = Verdadeiro
-- (2,[]) = Verdadeiro
-- (2,[True]) = Falso

-- 21
-- (2,[3]) = Verdadeiro
-- (2,[]) = Verdadeiro
-- (2,[True]) = Falso


----- Aula 3 -----

-- 22
sumSquare:: Int
sumSquare = sum ([x*x | x <- [1..100]])

-- 23
-- a)
aprox:: Int -> Double
aprox n = sum ([ ((-1)^(fromIntegral x)) / (2*(fromIntegral x) + 1) | x <- [0..n]])

-- b)
aprox':: Int -> Double
aprox' k = sum ([ ((-1)^(fromIntegral x)) / (((fromIntegral x) + 1)^2) | x <- [0..k]])

--24
divprop:: Int -> [Int]
divprop n = [y | y <- [1..n-1], mod n y == 0]

-- 25
perfeitos:: Int -> [Int]
perfeitos n = [y | y <- [1..n-1], sum(divprop y) == y]

-- 26
primo:: Int -> Bool
primo x = if sum (divprop x) == 1 then True else False

-- 27
pascal:: Int -> [[Int]]
pascal x = [[binom n k | k<-[0..n]] | n<-[0..x]]

-- 28
dotprod:: [Float] -> [Float] -> Float
dotprod x y = sum (zipWith (*) x y)

-- 29
pitagoricos:: Int -> [(Int, Int, Int)]
pitagoricos n = [(x,y,z) | x<-[1..n], y<-[1..n], z<-[1..n], x*x + y*y == z*z]


----- Aulas 4 e 5 -----

-- 30
-- a)
factorial:: Int -> Int
factorial 1 = 1
factorial n = n * factorial (n-1)

-- b)
rangeProduct:: Int -> Int -> Int
rangeProduct m n | m==n = n
                 | otherwise = n * rangeProduct m (n-1)

-- c)
factorial2:: Int -> Int
factorial2 n = rangeProduct 1 n

-- 31
mult:: Int -> Int -> Int
mult x 0 = 0
mult 0 x = 0
mult x y = x + mult x (y-1)

-- 32
intSqrt:: Int -> Int
intSqrt x = head (reverse [y | y<-[0..x-1], y*y<=x])

-- 33
maxReturn:: (Integer -> Integer) -> Integer -> Integer
maxReturn f 0 = f 0
maxReturn f n = max (maxReturn f (n-1)) (f n)

-- 34
zeroReturn:: (Integer -> Integer) -> Integer -> Bool
zeroReturn f 0 = if (f 0)==0 then True else False
zeroReturn f n = if (f n)==0 then True else zeroReturn f (n-1)

-- 35
sumReturn:: (Integer -> Integer) -> Integer -> Integer
sumReturn f 0 = f 0
sumReturn f n = (f n) + (sumReturn f (n-1))

-- 36
mdc:: Int -> Int -> Int
mdc x y = if y==0 then x else mdc y (mod x y)

mdc2:: Int -> Int -> Int
mdc2 x y = head ( reverse ([k | k<-[1..(max x y)], (mod x k)==0, (mod y k)==0]))

mdc3:: Int -> Int -> Int
mdc3 a 0 = a
mdc3 a b = mdc3 b (a`mod`b)

-- 37
power:: Int -> Int
power n = 2^n

-- 38
-- a)
and2:: [Bool] -> Bool
and2 [] = True
and2 (x:xs) = if x==False then False else and2 xs

-- b)
or2:: [Bool] -> Bool
or2 [] = False
or2 (x:xs) = if x==True then True else or2 xs

-- c)
concat2:: [[a]] -> [a]
concat2 [] = []
concat2 (x:xs) = x ++ concat2 xs

-- d)
replicate2:: Int -> a -> [a]
replicate2 0 a = []
replicate2 n a = [a] ++ replicate2 (n-1) a

-- e)
(!!!):: [a] -> Int -> a
(!!!) (x:xs) 0 = x
(!!!) (x:xs) n = (!!!) xs (n-1)

-- f)
elem2:: Eq a => a -> [a] -> Bool
elem2 y [] = False
elem2 y (x:xs) = if y==x then True else elem2 y xs

-- 39
concat3:: [[a]] -> [a]
concat3 xs = [xs !! y !! k | y<-[0..(length xs-1)], k<-[0..(length (xs !! y)-1)]]

-- 40 
forte:: String -> Bool
forte a = and [forteCharNum a 0, forteUppercase a, forteLowercase a, forteHasNum a]

forteCharNum:: String -> Int -> Bool
forteCharNum [] n = if n<8 then False else True
forteCharNum (x:xs) n | (x>='a' && x<='z') || (x>='A' && x<='Z') = forteCharNum xs (n+1)
                      | otherwise = forteCharNum xs (n+1)

forteUppercase:: String -> Bool
forteUppercase [] = False
forteUppercase (x:xs) = if x>='A' && x<='Z' then True else forteUppercase xs

forteLowercase:: String -> Bool
forteLowercase [] = False
forteLowercase (x:xs) = if x>='a' && x<='z' then True else forteLowercase xs 

forteHasNum:: String -> Bool
forteHasNum [] = False
forteHasNum (x:xs) = if x>='0' && x<='9' then True else forteHasNum xs

-- 42
intersperse:: a -> [a] -> [a]
intersperse a (x:[]) = [x]
intersperse a (x:xs) = [x] ++ [a] ++ intersperse a xs


----- Aulas 6 e 7 -----

-- 48
f48:: (a -> a) -> (a -> Bool) -> [a] -> [a]
f48 f p xs = map f (filter p xs)

-- 49
-- a)
(+++):: [a] -> [a] -> [a]
(+++) xs ys = foldr (:) ys xs

-- b)
concat4:: [[a]] -> [a]
concat4 xs = foldr (++) [] xs

-- c)
reverse2:: [a] -> [a]
reverse2 xs = foldr (\x y -> y ++ [x]) [] xs

-- d)
reverse3:: [a] -> [a]
reverse3 xs = foldl (\x y -> [y] ++ x) [] xs

-- e)
elem3:: Eq a => a -> [a] -> Bool
elem3 a xs = any (a==) xs

-- 50
dec2int:: [Int] -> Int
dec2int xs = foldl (\x y -> x*10 + y) 0 xs

-- 51
zipWith2:: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith2 f xs [] = []
zipWith2 f [] ys = []
zipWith2 f (x:xs) (y:ys) = [f x y] ++ (zipWith2 f xs ys)

-- 52
-- Auxiliar ao 52
insert :: (Ord a) => a -> [a] -> [a]
insert = insertBy compare

insertBy                :: (a -> a -> Ordering) -> a -> [a] -> [a]
insertBy cmp x []       =  [x]
insertBy cmp x ys@(y:ys')
                        =  case cmp x y of
                                GT -> y : insertBy cmp x ys'
                                _  -> x : ys

isort:: Ord a => [a] -> [a]
isort (x:xs) = foldr insert [x] xs

-- 53
shift:: [a] -> [a]
shift (x:xs) = xs++[x]

rotate:: [a] -> [[a]]
rotate xs = [foldl (\x y -> shift y) [] [snd (until (\(x,y) -> x==k) (\(x,y) -> (x+1,shift y)) (0,xs))] | k<-[0..((length xs)-1)]]

-- 54
-- a)
maximum2:: Ord a => [a] -> a
maximum2 xs = foldl1 max xs

maximum3:: Ord a => [a] -> a
maximum3 xs = foldr1 max xs

minimum2:: Ord a => [a] -> a
minimum2 xs = foldl1 min xs

minimum3:: Ord a => [a] -> a
minimum3 xs = foldr1 min xs

-- b)
foldr12:: (a -> a -> a) -> [a] -> a
foldr12 f (x:xs) = foldr f x xs

-- 55
add:: Int -> Int -> Int
add i 0 = i
add i j = succ (add i (pred j))

-- a)
mult2:: Int -> Int -> Int
mult2 x 0 = 0
mult2 x y = add x (mult2 x (pred y))

exp2:: Int -> Int -> Int
exp2 x 0 = 1
exp2 x y = mult2 x (exp2 x (pred y))

-- b)
foldi :: (a -> a) -> a -> Int -> a
foldi f q 0 = q
foldi f q i = f (foldi f q (pred i))

add2:: Int -> Int -> Int
add2 x y = foldi succ x y

mult3:: Int -> Int -> Int
mult3 x y = foldi (+x) 0 y  -- só com foldi

exp3:: Int -> Int -> Int
exp3 x y = foldi (*x) 1 y

-- 56
mdc4:: Int -> Int -> Int
mdc4 x y = snd (until (\(a,b) -> a `mod` b == 0) (\(a,b) -> (b,a `mod` b) ) (x,y))
-- funçoes de ordem superior sao funções que recebem como argumento outras funçoes

-- 57
--scanl2:: (Int -> Int -> Int) -> Int -> [Int] -> [Int]
--scanl2 f a xs = scanl2_aux f a (reverse xs)

--scanl2_aux:: (Int -> Int -> Int) -> Int -> [Int] -> [Int]
--scanl2_aux f a [] = [a]
--scanl2_aux f a (x:xs) = [(foldl f a (x:xs))] ++ (scanl2 f a xs)


----- Aula 8 -----

-- 58
factorial3:: [Integer]
factorial3 = [product [1..x] | x<-[0..]]

--fibonacci:: [Integer]
--fibonacci = [sum [0..x] | x <- [0..]]

-- 59
merge:: [Integer] -> [Integer] -> [Integer]
merge xs [] = xs
merge [] ys = ys
merge (x:xs) ys = merge2 (xs++ys) (insert x [])

merge2:: [Integer] -> [Integer] -> [Integer]
merge2 [] a = a
merge2 (x:xs) a = merge2 xs (insert x a)

--hamming:: [Integer]
--hamming = merge [((2^x)*(3^y)*(5^z)) | x <- [0..], y <- [0..], z <- [0..]] []

-- 60
somas:: [Int] -> [Int]
somas xs = 0 : [(somas_aux xs y) | y <- [1..(length xs)]]

somas_aux:: [Int] -> Int -> Int
somas_aux xs 0 = 0
somas_aux (x:xs) y = x + somas_aux xs (y-1)

--ou

somas2 :: [Integer] -> [Integer]
somas2 n = 0 : zipWith (+) n (somas2 n)

-- 61
-- a)
pascal2:: [[Int]]
pascal2 = [[binom y x | x <- [0..y]] | y <- [0..]]

-- 62
cifraChave:: String -> String -> String
cifraChave xs ys = cifraChave_aux_recurrence xs (cifraChave_aux_copyKey xs ys)

cifraChave_aux_recurrence:: String -> String -> String
cifraChave_aux_recurrence [] ys = []
cifraChave_aux_recurrence (x:xs) (y:ys) = [(cifraChave_aux_transform (cifraChave_aux_select x 0) (cifraChave_aux_select y 0))] ++ cifraChave_aux_recurrence xs ys

cifraChave_aux_copyKey:: String -> String -> String
cifraChave_aux_copyKey xs ys = snd (until (\(a,b) -> length b >= length a) (\(a,b) -> (a,b++ys)) (xs,ys))

cifraChave_aux_select:: Char -> Int -> Int
cifraChave_aux_select x a = if (xs !! a == x) then a else cifraChave_aux_select x (a+1)
                          where xs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

cifraChave_aux_transform:: Int -> Int -> Char
cifraChave_aux_transform a b = xs !! (until (<=25) (\x -> x-26) (a+b))
                             where xs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"


----- Aula 9 -----

-- 64
elefantes:: Int -> IO ()
--nao pode pôr número menor que 3. O texto começa em 2 elefantes.
elefantes n | n < 3 =  putStrLn "Número inválido. Introduza outro"
            | otherwise = sequence_[putStrLn (frase i ) | i <- [2..n-1]]
                        where frase i = "Se " ++ show i ++ " elefantes incomodam muita gente, \n" ++ show (i + 1) ++ " elefantes incomodam muito mais!"

elefantes2 n | n < 3 =  putStrLn "Número inválido. Introduza outro"
             | otherwise = sequence_[putStrLn ("Se " ++ show i ++ " elefantes incomodam muita gente, \n" ++ show (i + 1) ++ " elefantes incomodam muito mais!") | i <- [2..n-1]]

-- 65
wc:: String -> IO ()
wc xs = putStrLn (show (length (lines xs)) ++ "   " ++ show (length (words xs)) ++ "   " ++ show (length xs + 1))

-- 66
--main:: IO ()
--main = do xs <- getLine
--          if (length xs == 0) then return () else putStrLn (reverse xs)
--          if (length xs == 0) then return () else main

-- 67
--main:: IO ()
--main = do xs<-getLine
--          putStrLn (cifraChave xs 13)
--          return ()


--cifraChave:: String -> Int -> String
--cifraChave [] a = []
--cifraChave (x:xs) a = (if (x>='a' && x<='z') || (x>='Z' && x<='Z') then [cifraChave_aux_transform (cifraChave_aux_select x 0) a] else [x]) ++ cifraChave xs a

--cifraChave_aux_select:: Char -> Int -> Int
--cifraChave_aux_select x a = if (xs !! a == x) then a else cifraChave_aux_select x (a+1)
--                          where xs = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"

--cifraChave_aux_transform:: Int -> Int -> Char
--cifraChave_aux_transform a b = xs !! (until (<=51) (\x -> x-52) (a+b))
--                             where xs = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"

-- 68
adivinha:: String -> IO ()
adivinha xs = adivinha_aux_main xs ['-' | x <- [1..(length xs)]] 0

adivinha_aux_main:: String -> String -> Int -> IO()
adivinha_aux_main xs ys a = do x <- getChar
                               let ys = adivinha_aux_giveString xs ys x
                               print "ola-"
                               if (adivinha_aux_maybeOver ys) == False then (adivinha_aux_main xs ys (a+1)) else print ys

adivinha_aux_maybeOver:: String -> Bool
adivinha_aux_maybeOver [] = True
adivinha_aux_maybeOver (x:xs) = if x=='-' then False else adivinha_aux_maybeOver xs

adivinha_aux_giveString:: String -> String -> Char -> String
adivinha_aux_giveString [] [] c = []
adivinha_aux_giveString (x:xs) (y:ys) c = (if x==c then [c] else [y]) ++ adivinha_aux_giveString xs ys c


----- Aula 10 -----
data Arv a = Vazia | No a (Arv a) (Arv a)

-- 70
sumArv:: Num a => Arv a -> a
sumArv Vazia = 0
sumArv (No a esq dir) = a + sumArv esq + sumArv dir

-- 71
listar_decrescente:: Arv a -> [a]
listar_decrescente Vazia = []
listar_decrescente (No x esq dir) = listar_decrescente dir ++ [x] ++ listar_decrescente esq

-- 72
nivel:: Int -> Arv a -> [a]
nivel x Vazia = []
nivel 0 (No a esq dir) = [a]
nivel x (No a esq dir) = nivel (x-1) esq ++ nivel (x-1) dir

-- 73
construir:: [a] -> Arv a
construir [] = Vazia
construir xs = No x (construir xs') (construir xs'')
              where n = (length xs) `div` 2
                    xs' = take n xs
                    x:xs'' = drop n xs

inserir:: Ord a => a -> Arv a -> Arv a
inserir x Vazia = No x Vazia Vazia
inserir x (No y esq dir) | x==y = No y esq dir
                         | x<y = No y (inserir x esq) dir
                         | x>y = No y esq (inserir x dir)

max_high:: Arv a -> Int -> Int
max_high Vazia n = n
max_high (No a esq dir) n = max (max_high esq (n+1)) (max_high dir (n+1))

-- 74
mapArv:: (a -> b) -> Arv a -> Arv b
mapArv f Vazia = Vazia
mapArv f (No a esq dir) = No (f a) (mapArv f esq) (mapArv f dir)

-- 75
mais_esq:: Arv a -> a
mais_esq (No a Vazia _) = a
mais_esq (No a esq dir) = mais_esq esq

-- a)
mais_dir:: Arv a -> a
mais_dir (No a _ Vazia) = a
mais_dir (No a esq dir) = mais_dir dir

-- b)
remover:: Ord a => a -> Arv a -> Arv a
remover x Vazia = Vazia
remover x (No a Vazia dir) | x==a = dir
remover x (No a esq Vazia) | x==a = esq
remover x (No a esq dir) | x<a = No a (remover x esq) dir
                         | x>a = No a esq (remover x dir)
                         | x==a = let z = mais_dir esq
                                  in No z (remover z esq) dir


----- Aula 11 -----

-- 77
-- a)
data Shape = Circle Float | Rectangle Float Float

-- b)
perimetro:: Shape -> Float
perimetro (Circle a) = pi*a
perimetro (Rectangle a b) = a+a+b+b

-- 78
data PointRectangle = Rectangle2 (Float,Float) (Float,Float)

area_rectangulo2:: PointRectangle -> Float
area_rectangulo2 (Rectangle2 (x1,y1) (x2,y2)) = (x2-x1) * (y2-y1)

--touch_rectangulo2:: PointRectangle -> PointRectangle -> Bool
--touch_rectangulo2 (Rectangle2 (x1,y1) (x2,y2)) (Rectangle2 (x3,y3) (x4,y4)) = if (x1>=x3 && )

-- 79
data Stack a = Stk [a]

push:: a -> Stack a -> Stack a
push x (Stk xs) = Stk (x:xs)

pop:: Stack a -> Stack a
pop (Stk (_:xs)) = Stk xs
pop _ = error "Stack.pop: empty stack"

top:: Stack a -> a
top (Stk (x:_)) = x
top _ = error "Stack.pop: empty stack"

empty:: Stack a
empty = Stk []

isEmpty:: Stack a -> Bool
isEmpty (Stk []) = True
isEmpty (Stk _) = False

makeStack:: [a] -> Stack a
makeStack xs = Stk xs

size:: Stack a -> Int
size (Stk xs) = length xs

list_stack:: Stack a -> [a]
list_stack (Stk []) = []
list_stack a = [(top a)] ++ list_stack (pop a)

parent:: String -> Bool
parent xs = if ((parent_aux xs empty)==False) then False else True

parent_aux:: String -> Stack Char -> Bool
parent_aux [] a = if (isEmpty a == False) then False else True
parent_aux (x:xs) a | (x=='(' || x=='[' || x=='{') = parent_aux xs (push x a)
                    | (x==']' && top a =='[') = parent_aux xs (pop a)
                    | (x==')' && top a =='(') = parent_aux xs (pop a)
                    | (x=='}' && top a =='{') = parent_aux xs (pop a)
                    | otherwise = False

-- 80
-- a)
calc:: Stack Float -> [String] -> Stack Float
calc a [] = a
calc a (x:xs) | x=="+" = calc (push ((top a) + (top (pop a))) a) xs
              | x=="-" = calc (push ((top a) - (top (pop a))) a) xs
              | x=="*" = calc (push ((top a) * (top (pop a))) a) xs
              | x=="/" = calc (push ((top a) / (top (pop a))) a) xs
              | otherwise = calc (push (read x) a) xs

-- b)
calcular:: String -> Float
calcular xs = top (calc empty (words xs))

-- c)
--module Main where
--import Stack

--main:: IO()
--main = do x<-getLine
--          print (calcular x)
--          return ()


--calcular:: String -> Float
--calcular xs = top (calc empty (words xs))

--calc:: Stack Float -> [String] -> Stack Float
--calc a [] = a
--calc a (x:xs) | x=="+" = calc (push ((top a) + (top (pop a))) a) xs
--              | x=="-" = calc (push ((top a) - (top (pop a))) a) xs
--              | x=="*" = calc (push ((top a) * (top (pop a))) a) xs
--              | x=="/" = calc (push ((top a) / (top (pop a))) a) xs
--              | otherwise = calc (push (read x) a) xs

-- 81
-- 1)
data Vertice = Vertice (Int, Int)
data Elemento = Elemento Int
data Grafo = Grafo [Vertice] [Elemento]

--maiores:: [Int] -> Bool
--maiores xs = and []

--scanl2:: (a -> a -> b) -> a -> [a] -> [b]
scanl2 f v [] = [0]
scanl2 f v (x:xs) = scanl22 f v (x:xs) : scanl2 f v xs

--scanl22:: (a -> a -> b) -> a -> [a] -> b
scanl22 f v [x] = f v x
scanl22 f v (x:xs) = f v (x + (scanl22 f v xs))

--scanl2:: (a -> a -> b) -> a -> [a] -> [b]
scanr2 f v [] = [0]
scanr2 f v (x:xs) = scanr22 f v (x:xs) : scanr2 f v xs

--scanl22:: (a -> a -> b) -> a -> [a] -> b
scanr22 f v [x] = f v x
scanr22 f v (x:xs) = f v (x + (scanr22 f v xs))

somasS:: (Int -> Int) -> Int -> [Int]
somasS f i = [somasS2 f i a | a<-[i..]]

somasS2:: (Int -> Int) -> Int -> Int -> Int
somasS2 f i a = if a==i then (f i) else (f i) + somasS2 f (i+1) a

dups:: [a] -> [a]
dups [] = []
dups (x:[]) = [x,x]
dups (x:y:xs) = x:x:y:dups xs