:- use_module(library(ic)).
:- use_module(library(ic_cumulative)).
:- use_module(library(branch_and_bound)).% for minimize/2



teste(F, Datas) :-
	compile(F),
	findall(I, tarefa(I,_,_,_), Tars),
	length(Tars, N),
	length(Datas, N),
	maxduracao(Tars,MaxConcl),
	[Concl|Datas] #:: 0..MaxConcl,
	prec_constrs(Tars,Datas,Concl),
	get_min(Concl, Concl),
	get_D(Tars, Du), get_R(Tars, Re),
	neededES(Datas, Du, Re, TES),
	neededCr(Datas, TCr),
	Limit #:: 0..TES,
	cumulative(Datas, Du, Re, Limit),
	minimize(labeling([Datas, Limit]), Limit),
	writeln('Duracao minima' : Concl),
	writeln('Trabalhadores ES' : TES),
	writeln('Trabalhadores Criticos' : TCr),
	writeln('Trabalhadores minimos' : Limit).

neededES(Datas, Du, Re, STES):-
	selectES(Datas, ES),
	cumulative(ES, Du, Re, TES), get_min(TES,STES).

	selectES([],[]).
	selectES([X|Datas], [MX|ES]):-
		get_min(X, MX),
		selectES(Datas, ES).

neededCr(Datas, STCr):-
	selectCr(Datas, Cr, DCr, 1),
	get_D(Cr, DuC), get_R(Cr, ReC),
	cumulative(DCr, DuC, ReC, TCr), get_min(TCr,STCr).



selectCr([],[],[], _).
selectCr([X|Datas], [K|Cr], [X|DCr], K):-
	get_domain_size(X, 1),
	K1 is K+1,
	selectCr(Datas, Cr, DCr, K1),!.

selectCr([_|Datas], Cr, DCr, K):-
	K1 is K+1,
	selectCr(Datas, Cr, DCr, K1).

get_D([], []).
get_D([I|IR], [D|DR]):- tarefa(I,_,D,_), get_D(IR,DR).

get_R([], []).
get_R([I|IR], [R|RR]):- tarefa(I,_,_,R), get_R(IR,RR).

% restricoes de precedencia
prec_constrs([],_,_).
prec_constrs([I|RTarefas],Datas,Concl) :-
	seleciona_var(I,Datas,DataI),
	tarefa(I, LTSegs, Di, _),
	prec_constrs_(LTSegs,Datas,DataI,Di),
	DataI+Di #=< Concl,
	prec_constrs(RTarefas,Datas,Concl).

prec_constrs_([],_,_,_).
prec_constrs_([J|RTSegs],Datas,DataI,Di) :-
	seleciona_var(J,Datas,DataJ),
	DataI+Di #=< DataJ,
	prec_constrs_(RTSegs,Datas,DataI,Di).

% soma duracao das tarefas
maxduracao([],0).
maxduracao([I|RTarefas],Somaf) :-
    maxduracao(RTarefas, Soma),
    tarefa(I, _, Di, _), Somaf is Soma+Di.


seleciona_var(1, [H|_], H):- !.
seleciona_var(N, [_|T], H):- N > 1,
    N1 is N-1,
    seleciona_var(N1,T,H).
