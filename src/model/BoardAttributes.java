package model;

public class BoardAttributes {
    private ChessComponent[][] chessComponents = new ChessComponent[8][8];
    private ChessColor currentColor;

    public BoardAttributes(ChessComponent[][] chessComponents, ChessColor currentColor) {
        this.chessComponents = chessComponents;
        this.currentColor = currentColor;
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }
}
