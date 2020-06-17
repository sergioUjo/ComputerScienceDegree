:- use_module(library(ic)).
:- use_module(library(ic_global)).
:- use_module(library(ic_cumulative)).
:- use_module(library(branch_and_bound)).% for minimize/2

teste(F, Teams,Concl,Hours) :-
  compile(F),
  findall(I, tarefa(I,_,_,_,_), Tars),
  length(Tars, N),
  length(Hours, N),
  %maxduracao(Tars, MaxConcl),
  %funcao(Domain),
  [Concl,Hours] #:: 0..inf,
  %int_constrs(Hours),
  prec_constrs(Tars,Hours,Concl),

  findall(T, trabalhador(T,_), Trabs),
  length(Trabs, NT),
  length(Teams, N),
  select_team(Tars, NT, Teams),
  get_D( Tars, Du),
  checkOverLap(Hours, Du,Teams),
  get_min(Concl,Concl),
  labeling([Hours,Teams,Concl]).

checkOverLap([], [], []).
checkOverLap([HourI|Hours],[DuI|Du], [TeamI|Teams]):-
  checkOverLap_(HourI,DuI, TeamI, Hours, Du, Teams),
  checkOverLap(Hours, Du, Teams).

checkOverLap_(_,_,_,[],[],[]).
checkOverLap_(HourI, DuI, TI, [Hourj|Hours], [Duj|Du], [Tj|Teams]):-
  and(HourI #< Hourj+Duj,Hourj#< HourI+DuI,C),
  =>(C,D,1),
  length(Tj,N1), length(TI, N2),
  term_variables([Tj,TI], Vars),
  nvalue(N,Vars),
  #=(N1+N2, N, D),
  checkOverLap_(HourI, DuI, TI, Hours, Du, Teams).


select_team( [], _, _).
select_team( [I|RTars], NT, Teams):-
  tarefa(I,_,_,Esp,_),
  seleciona_var(I , Teams, Equipa),
  totalTrabs(Esp, N), length(Equipa,N),
  T #:: 1..NT,
  nvalue(N, Equipa),
  select_team_(Esp,T,Equipa),
  select_team(RTars,NT,Teams).

select_team_([],_,[]).
select_team_( [r(E,N)|RE], T , Equipa):-
  get_domain_as_list(T,Lt),
  possible_trabs(E, Lt,SLt),
  especial( r(E,N), SLt, Equipa , REquipa ),
  select_team_( RE,  T, REquipa).

especial( r(_,0), _ , Equipa, Equipa ).
especial( r(E,N), T ,[X|Equipa], REquipa):-
  X #:: T,
  K is N-1,
  especial( r(E,K), T,Equipa,REquipa),!.

totalTrabs([], 0).
totalTrabs( [r(_,N)|RE], Somaf):-
  totalTrabs(RE, Soma),
  Somaf is Soma+N.

possible_trabs(_,[],[]).
possible_trabs(E, [T|Rt], [T|Rs]):-
  trabalhador(T,Esp), member(E,Esp),
  possible_trabs( E, Rt, Rs),!.
possible_trabs(E, [_|Rt], S):-
  possible_trabs( E, Rt, S).

  % restricoes de precedencia
prec_constrs([],_,_).
prec_constrs([I|RTarefas],Datas,Concl) :-
  seleciona_var(I,Datas,DataI),
  tarefa(I, LTSegs, Di, _,_),
  prec_constrs_(LTSegs,Datas,DataI,Di),
  DataI+Di #=< Concl,
  prec_constrs(RTarefas,Datas,Concl).

prec_constrs_([],_,_,_).
prec_constrs_([J|RTSegs],Datas,DataI,Di) :-
  seleciona_var(J,Datas,DataJ),
  DataI+Di  #=< DataJ,
  prec_constrs_(RTSegs,Datas,DataI,Di).


seleciona_var(1, [H|_], H):- !.
seleciona_var(N, [_|T], H):- N > 1,
      N1 is N-1,
      seleciona_var(N1,T,H).


get_D([], []).
get_D([I|IR], [D|DR]):- tarefa(I,_,D,_,_), get_D(IR,DR).

  /*int_constrs( Hours):-
  findall( I, intervalo(I,_,_,_), Inter),
  int_constrs_( Inter, Hours).

  int_constrs_( [], _).
  int_constrs_( [I|RInter], Hours):-
  intervalo(I,Iprev,Min,Max),
  seleciona_var(I,Datas,DataI),
  seleciona_var(Iprev,Datas,DataIprev),
  tarefa(Iprev, _, Diprev, _,_),
  DataI #=> Min + Diprev, DataIprev,
  DataI #=< Max + Diprev, DataIprev,
  int_constrs( RInter, Hours).
  */
/*
% CODIGO DO calendario
month(1,31).
month(2,28).
month(3,31).
month(4,30).
month(5,31).
month(6,30).
month(7,31).
month(8,31).
month(9,30).
month(10,31).
month(11,30).
month(12,31).

calendario([d(21,5,3),d(22,5,4),d(23,5,5),d(24,5,6),d(27,5,2),d(28,5,3),d(29,5,4),d(30,5,5)]).

convert_time(Time, Hour, Day):-24*Day+Hour #= Time, 0 #=< Hour, Hour #< 24.

funcao(Horas):- calendario(L) ,aux(L,Horas).

aux([],[]).
aux([d(D,M,_)|RD],[A,B,C,R,E,F,G,H|Horas]):-
   total_day(M,D,DT), convert_time(A,8,DT),
   B is A +1, C is B+1, R is C+1, E is R+2, F is E+1, G is F+1, H is G+1,
   aux(RD,Horas).

total_day(M,D,DT):-calc_month(M,T),DT is D+T.

aux_month(1,[]).
aux_month(M,[D|RD]):-N is M-1,month(N,D),aux_month(N,RD).

calc_month(M,T):-aux_month(M,L),soma(L,T).

soma([],0).
soma([H|T],S):-soma(T,G),S is H+G.

soma_D_d(Data,Du,S):-
  convert_time(Data, Hora ,_),
  and(Hora#< 13, Data+Du #> 12, A),
  A => S #= Data+Du+1.
soma_D_d(Data,Du,S):-
  convert_time(Data,Hora,Day),
  and(Hora#>12, Data+Du #> 17, A),
  A=>(
  calendario(C),
  get_next_day(Day,C,DayS),
  TD is DayS-Day-1,
  S #= Data+Du+15+(24*TD)).

soma_D_d(Data,Du,S):-S #= Data+Du.
get_next_day(Day,[d(D,M,_),d(DC,MC,_)|_], CD):- total_day(M,D,Day), total_day(MC,DC,CD).
get_next_day( Day,[_|RC], D):- get_next_day(Day,RC,D).
*/
