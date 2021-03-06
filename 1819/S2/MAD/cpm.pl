
use_module(library(ic)).
use_module(library(ic_global)).

proj(FileDados, LS) :-
  compile(FileDados),
  findall(I,tarefa(I,_,_,_),Tars),
  length(Tars,N),
  length(LS,N),
  maxduracao(Tars,MaxConcl),
  [Concl|Datas] :: 0..MaxConcl,
  prec_constrs(Tars, LS,Concl)
  %minimize(labeling([],[Concl]),Concl). % SICStus Prolog
  % once(labeling([min(Concl)],[Concl])).  % SWI Prolog

  % restricoes de precedencia
  prec_constrs([],_,_).
  prec_constrs([Ti|RTarefas],LS,Concl) :-
    element(Ti,LS,LSI),
    tarefa(Ti, Tprec, Di, _),
    prec_constrs_(Tprec, LS, LSI),
    LS+Di =< Concl,
    prec_constrs(RTarefas, LS,Concl).

    prec_constrs_([],_,_,_).
    prec_constrs_([J|RTSegs], LS, LSI) :-
      element(J, LS, LSJ),
      tarefa(J, _, Dj, _),
      LSJ+Dj =< LSI,
      prec_constrs_( RTSegs, LS, LSI).

      % soma duracao das tarefas

      maxduracao([],0).
      maxduracao([Ti|RTarefas],Somaf) :-
        t(Ti, _, Di, _),
        maxduracao(RTarefas,Soma), Somaf is Soma+Di.
