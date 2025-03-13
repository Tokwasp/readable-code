package cleancode.minesweeper.tobe.minesweeper.board;

import cleancode.minesweeper.tobe.minesweeper.board.cell.*;
import cleancode.minesweeper.tobe.minesweeper.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPositions;
import cleancode.minesweeper.tobe.minesweeper.board.position.RelativePosition;

import java.util.List;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;
    private GameStatus gameStatus;

    public GameBoard(GameLevel gameLevel){
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();
        board = new Cell[rowSize][colSize];
        initializeGameStatus();

        landMineCount = gameLevel.getLandMineCount();
    }

    public void initializeGame() {
        initializeGameStatus();
        CellPositions cellPositions = CellPositions.from(board);

        initializeEmptyCells(cellPositions);

        List<CellPosition> landMinePositions = cellPositions.extractRandomPositions(landMineCount);
        initializeLandMineCells(landMinePositions);

        List<CellPosition> numberPositionsCandidates = cellPositions.subtract(landMinePositions);
        initializeNumberCells(numberPositionsCandidates);
    }

    public void openAt(CellPosition cellPosition) {
        if (isLandMineCell(cellPosition)) {
            openOneSell(cellPosition);
            changeGameStatusToLose();
            return;
        }

        openSurroundedCells(cellPosition);
        checkIfGameIsOver();
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();

        checkIfGameIsOver();
    }

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        int colSize = getColSize();
        int rowSize = getRowSize();

        return cellPosition.isRowIndexMoreThanOrEqual(rowSize) ||
                cellPosition.isColIndexMoreThanOrEqual(colSize);
    }

    public boolean isLandMineCell(CellPosition cellPosition) {
        return findCell(cellPosition).isLandMine();
    }

    public boolean isInProgress() {
        return gameStatus == GameStatus.IN_PROGRESS;
    }

    public boolean isWinStatus() {
        return gameStatus == GameStatus.WIN;
    }

    public boolean isLoseStatus() {
        return gameStatus == GameStatus.LOSE;
    }

    public CellSnapshot getSnapshot(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSnapshot();
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    private void openOneSell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
    }

    private void openSurroundedCells(CellPosition cellPosition) {
        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMineCell(cellPosition)) {
            return;
        }

        openOneSell(cellPosition);

        if (doesCellHaveLandMineCount(cellPosition)) {
            return;
        }

        List<CellPosition> cellPositions = calculateSurroundedPositions(cellPosition, getRowSize(), getColSize());
        cellPositions.forEach(this::openSurroundedCells);
    }

    private boolean isAllChecked() {
        Cells cells = Cells.from(board);
        return cells.isAllChecked();
    }

    private void initializeGameStatus() {
        gameStatus = GameStatus.IN_PROGRESS;
    }

    private void initializeNumberCells(List<CellPosition> numberPositionsCandidates) {
        for (CellPosition candidatePosition : numberPositionsCandidates) {
            int count = countNearByLandMines(candidatePosition);
            if(count != 0){
                updateCellAt(candidatePosition, new NumberCell(count));
            }
        }
    }

    private void initializeLandMineCells(List<CellPosition> landMinePositions) {
        updateCellsAt(landMinePositions, new LandMineCell());
    }

    private void initializeEmptyCells(CellPositions cellPositions) {
        List<CellPosition> allPositions = cellPositions.getPositions();
        updateCellsAt(allPositions, new EmptyCell());
    }

    private void updateCellsAt(List<CellPosition> allPositions, Cell cell) {
        for (CellPosition position : allPositions) {
            updateCellAt(position, cell);
        }
    }

    private void updateCellAt(CellPosition position, Cell cell) {
        board[position.getRowIndex()][position.getColIndex()] = cell;
    }

    private int countNearByLandMines(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        long count = calculateSurroundedPositions(cellPosition, rowSize, colSize).stream()
                .filter(this::isLandMineCell)
                .count();

        return (int) count;
    }

    private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
        return RelativePosition.SURROUNDED_POSITIONS.stream()
                .filter(cellPosition::canCalculatePositionBy)
                .map(cellPosition::calculatePositionBy)
                .filter(position -> position.isRowIndexLessThan(rowSize))
                .filter(position -> position.isColIndexLessThan(colSize))
                .toList();
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        return findCell(cellPosition).hasLandMineCount();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        return findCell(cellPosition).isOpened();
    }

    private void checkIfGameIsOver() {
        if (this.isAllChecked()) {
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToLose() {
        gameStatus = GameStatus.LOSE;
    }

    private void changeGameStatusToWin() {
        gameStatus = GameStatus.WIN;
    }
}