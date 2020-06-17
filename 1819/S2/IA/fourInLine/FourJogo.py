class Jogo:
    def __init__(self, tabuleiro = None, n = 7, m = 6):
        self.n = n
        self.m = m
        self.plays= [0]*n
        if tabuleiro is None:
            self.tabuleiro = [ [0]*n for i in range(m) ]
        else:
            self.tabuleiro = tabuleiro

    def __repr__(self):
        return str(self.tabuleiro)

    def printTab(self):
        for i in reversed( range( self.m ) ):
            for j in range( self.n ) :
                print '{:3}'.format(self.tabuleiro[i][j]),
            print

    def play(self, x, y):
        self.tabuleiro[ self.plays[x] ][ x ] = y
        self.plays[x] += 1
        print(self.plays)
        self.printTab()
