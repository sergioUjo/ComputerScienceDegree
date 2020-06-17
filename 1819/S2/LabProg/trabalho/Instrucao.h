typedef enum {ATRIB, ADD, SUB, MUL, DIV, IF_I, PRINT, READ, GOTO_I, LABEL} Opkind;

typedef struct instr {
  Opkind op;
  Elem first,second,third;
}*Instr;

Instr mkIAC(Opkind op, Elem x, Elem y, Elem z);
Instr mkIA(Opkind op, Elem x, Elem y);
Instr mkI(Opkind op, Elem x);
void printInstr(Instr i);
Elem InstrExe(Instr i);
