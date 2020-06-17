typedef struct{
  int num;
  int den;
}FRAC;

FRAC newFrac(int x, int y);

int num(FRAC x);

int den(FRAC x);

FRAC add(FRAC x, FRAC y);

FRAC mult(FRAC x, FRAC y);

FRAC div(FRAC x, FRAC y);

FRAC simplify(FRAC x);

FRAC sub(FRAC x, FRAC y);

void printFRAC(FRAC x);
