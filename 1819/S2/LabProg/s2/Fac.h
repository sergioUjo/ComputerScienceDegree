#define MAX 15
#define MAXALUNOS 50
#define MAXDISC 20

typedef struct aluno{
  char *nome, codigo[MAX];
  int disc[MAX],nd;
}ALUNO;

typedef struct disciplina{
  char *nome;
  int ano;
}DISCIPLINA;

ALUNO alunos[MAXALUNOS];
int NAlunos;
DISCIPLINA Disc[MAXDISC];
int NDisc;

ALUNO newAluno( char *n, char cod[], int d[], int ndisc);
DISCIPLINA newDisc(char *n, int a);
void input();
void printAlunos(ALUNO l[]);
void printDisc(DISCIPLINA d[]);
void printDiscAno(DISCIPLINA d[], int a);
