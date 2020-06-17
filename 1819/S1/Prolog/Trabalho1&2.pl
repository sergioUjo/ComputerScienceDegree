%Auxiliares
%Verica se x pertence a lista
member(X,[X|_]).
member(X,[_|Y]):- member(X,Y),!.

%Concatena duas listas
append([], L, L).
append([X|L1], L2, [X|L3]) :- append(L1, L2, L3).

%------------------------------------------------------
%Todas as variaveis aceites
pvars([a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z]).
pvar(X):-pvars(V),member(X,V).

%Verifica se X é variavel e/ou potencia.
power(X):-pvar(X),!.
power(X^Y):-pvar(X),integer(Y),Y>1,!.
coefficient(K):-number(K).

a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z]).
monomial(X):-pvar(X),!.
monomial(N):-number(N),!.
monomial(X):-power(X),!.
monomial(K*X):-coefficient(K),power(X),!.
monomial(X*K):-coefficient(K),power(X),!.
monomial(-X):- monomial(X).

%Retorna os constituintes do monomial
monparts(X^N,1,X^N):-power(X^N),!.
monparts(K*P,K,P):-number(K),!.
monparts(P*K,K,P):-number(K),!.
monparts(K,K,indep):-number(K),!.
monparts(-X,-1,X):-pvar(X),!.
monparts(X,1,X):-pvar(X),!.

%Simplificação de um monomial
simmon(M,M2):-monparts(M,K,P), aux_simmon(K,P,M2).
aux_simmon(K,indep,K):-!.
aux_simmon(1,P,P):-!.
aux_simmon(-1,P,-P):-!.
aux_simmon(0,_,0):-!.
aux_simmon(K,P,K*P):-!.


%Função usada para corrigir o sistema  64-bit IEEE, do qual resulta imperfeições em floats.
truncate(X,N,Result):- X >=0, Result is floor(10^N*X)/10^N, !.
truncate(X,N,Result):- X <0, Result is ceil(10^N*X)/10^N, !.

%Adiciona 2 monomias 
addmono(K1,K2,Result):-number(K1),number(K2),K3 is K1+K2, truncate(K3,2,Result).
addmono(M1,M2,M3):-monparts(M1,K1,XExp),monparts(M2,K2,XExp),K3 is K1+K2,truncate(K3,2,Result), aux_addmono(Result,XExp,M3).
aux_addmono(0.0,_, 0):- !.
aux_addmono(K,indep,K):-!.
aux_addmono(0,_,0):-!.
aux_addmono(1,XExp,XExp):-!.
aux_addmono(-1,XExp,-XExp):-!.
aux_addmono(K,XExp,K*XExp).

%Multiplica um monomial por um escalar E
multmono(0,_,0):-!.
multmono(E, M, K2):- coefficient(M), K2 is E*M,!.
multmono(E, M, K2*XExp):- monparts(M,K1,XExp), K2 is E*K1.

%Adiciona um monomial aos membros de uma lista que tem a mesma potencia e variavel, 
%returnando o resultado e a lista sem os mesmos. 
add_poly2list(X,[],X,[]).
add_poly2list(X,[Y|L1], R1, L2):- addmono(X,Y,R) , add_poly2list(R,L1,R1, L2),!.
add_poly2list(X,[Y|L1], R, [Y|L2]):- add_poly2list(X,L1,R,L2),!.

%Multiplica uma lista de monomiais por um escalar E
mult_poly(_,[],[]).
mult_poly(E, [X|L1], L2):-multmono(E,X,0), mult_poly( E, L1, L2),!.
mult_poly(E, [X|L1], [R|L2]):-multmono(E,X,R), mult_poly( E, L1, L2),!.

%Converte um polinomial numa lista e vice versa.
poly2list([],0):-!.
poly2list(L1, P):- reverse(L1,X), list2poly(X,P),!.
poly2list(P,L1):- reverse(L1,X), poly2list2(P,X),!.

list2poly([P],P):- monomial(P).
list2poly([P|L1], M-P1 ) :- monparts(P,K,_), K<0, multmono(-1,P,P1), list2poly(L1,M),!.
list2poly([P|L1], M+P ) :- list2poly(L1,M),!.

poly2list2(P, [P]):- monomial(P).
poly2list2(M-P1, [P|L1]) :- monomial(P1), multmono(-1,P1,P), poly2list2(M,L1),!.
poly2list2(M+P, [P|L1]) :-  monomial(P), poly2list2(M,L1),!.

%Simplifica uma lista de monomiais, somando os "familiares" e simplificando os mesmos.
simpoly_list([],[]).
simpoly_list([X|L],L2):-  add_poly2list(X,L,0,L3), simpoly_list(L3,L2),!.
simpoly_list([X|L],[R|L2]):- simmon(X,X1), add_poly2list(X1,L,R,L3), simpoly_list(L3,L2),!.

%Converte para uma lista, simplifica e volta a converter para polinomial
simpoly(P,PR):- poly2list(P,LP), simpoly_list(LP,LPR), poly2list(LPR,PR).

%Escala um polinomial por E
scalepoly(X,E,Y):- poly2list(X,L1), mult_poly(E, L1, L2), poly2list(L2,Y).

%Adiciona dois polinomiais com o  recurso ao poly2list para converter, e ao simpoly_list para fazer a soma.
addpoly(P1,P2,P3):-poly2list(P1,L1),poly2list(P2,L2),append(L1,L2,LT), simpoly_list(LT,L3), poly2list(L3,P3).
                                                                                                      
                                                                                                      
                        
pl(X)--> ( sin(Z), mn(Y), {atom_concat(Z,Y,X)}; mn(X)); pl(Z),sin(Y), mn(K), {atomic_list_concat([Z,Y,K],X)}.
mn(X)-->num(X).
mn(K)-->num(Y), [times], pot(Z), { atomic_list_concat([Y,'*',Z],K)}.
pot(X)--> var(X).
pot(X)-->var(Y), exp(Z), { atom_concat(Y,Z,X)}.
exp(X)-->[raised], [to], num(Z), {atom_concat('^',Z,X)}.
sin(X)-->[minus], {X = '-'};[plus], {X = '+'}.
num(X)--> coef(Y), [point], coef(K),{ atomic_list_concat([Y,'.',K],X)}; coef(K), {X = K}.
coef(X)--> cent(X); dec(X); uni(X).

var(X)-->[x], {X = x};[y], {X = y}.
cent(X)--> uni(Z), [hundred], {X is Z*100}.
cent(X)--> uni(Z), [hundred], [and], uni(K), {X is (Z*100)+K}.
cent(X)--> uni(Z), [hundred], [and], dec(K), {X is (Z*100)+K}.
dec(X)--> decteen(X); deces(X).
dec(X)-->deces(Z),uni(Y),{X is Z+Y}.  

deces(X)-->
    [twenty], {X is 20};
    [thirty], {X is 30};
   	[forty], {X is 40};
    [fifty], {X is 50};
    [sixty], {X is 60};
    [seventy], {X is 70};
    [eighty], {X is 80};
    [ninety], {X is 90}.

decteen(X)-->
    [ten], {X is 10};
    [eleven], {X is 11};
    [twelve], {X is 12};
   	[thirteen], {X is 13};
    [fourteen], {X is 14};
    [fifteen], {X is 15};
    [sixteen], {X is 16};
    [seventeen], {X is 17};
	[eighteen], {X is 18};	
    [nineteen], {X is 19}.

uni(X)-->
    [one], {X is 1};
    [two], {X is 2};
    [three], {X is 3};
	[four], {X is 4};
    [five], {X is 5};
    [six], {X is 6};
	[seven], {X is 7};
    [eight], {X is 8};
    [nine], {X is 9}.




                              