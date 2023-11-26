package chess;

import java.util.Objects;

public class ChessPositionImpl implements ChessPosition{
    //equals and hash code

    //////////////////////////////////////////////////////////////////////////////
    //This represents a location on the chessboard. This should be represented  //
    // a row number from 1-8, and a column number from 1-8. (1,1) corresponds to//
    // the bottom left corner (which in chess notation is denoted a1). (8,8)    //
    // corresponds to the top right corner (h8 in chess notation)               //
    //////////////////////////////////////////////////////////////////////////////
    private int row = 0;
    private int column = 0;

    public ChessPositionImpl(int newRow, int newColumn) {
        this.row = newRow;
        this.column = newColumn;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPositionImpl that)) return false;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
