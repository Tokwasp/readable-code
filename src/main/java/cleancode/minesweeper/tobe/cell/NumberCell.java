package cleancode.minesweeper.tobe.cell;

import static cleancode.minesweeper.tobe.cell.CellSnapshot.*;

public class NumberCell implements Cell {

    private final CellState cellState = CellState.initialize();
    private int nearbyLandMineCount;

    public NumberCell(int nearbyLandMineCount) {
        this.nearbyLandMineCount = nearbyLandMineCount;
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCount() {
        return true;
    }

    @Override
    public CellSnapshot getSnapshot() {
        if(cellState.isOpened()){
            return ofNumber(nearbyLandMineCount);
        }
        if(cellState.isFlagged()){
            return ofFlag();
        }
        return ofUnChecked();
    }

    @Override
    public void flag() {
        cellState.flag();
    }

    @Override
    public void open() {
        cellState.open();
    }

    @Override
    public boolean isChecked() {
        return cellState.isChecked();
    }

    @Override
    public boolean isOpened() {
        return cellState.isOpened();
    }
}
