typedef enum { QUADRADO, RECTANGULO, CIRCULO} FigKind;

typedef struct fig{
  FigKind kind;
  union {
    struct{ int lado; };
    struct{ int lar ,comp;};
    struct{ int raio; };
  };
} *FIG;

FIG newQuad( int x );
FIG newRect( int l, int c );
FIG newCirc( int r );

float area( FIG x);

//string token;
