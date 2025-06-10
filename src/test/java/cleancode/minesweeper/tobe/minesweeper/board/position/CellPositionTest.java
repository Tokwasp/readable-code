package cleancode.minesweeper.tobe.minesweeper.board.position;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CellPositionTest {

    @DisplayName("셀의 좌표를 계산 하여 이동 한다.")
    @Test
    void calculatePositionBy() {
        //given
        CellPosition cellPosition = CellPosition.of(0, 0);
        RelativePosition relativePosition = RelativePosition.of(1, 1);

        //when
        CellPosition movedCellPosition = cellPosition.calculatePositionBy(relativePosition);

        //then
        assertThat(movedCellPosition.getRowIndex()).isEqualTo(1);
        assertThat(movedCellPosition.getColIndex()).isEqualTo(1);
    }

    @DisplayName("셀의 좌표를 계산 하여 이동 할때 보드 밖으로 이동할 경우 예외가 발생 한다.")
    @Test
    void calculatePositionBy_WhenMovedIndexOutBoard_ThenThrowException() {
        //given
        CellPosition cellPosition = CellPosition.of(0, 0);
        RelativePosition relativePosition = RelativePosition.of(-1, -1);

        //when //then
        Assertions.assertThatThrownBy(() -> cellPosition.calculatePositionBy(relativePosition))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("잘못된 좌표 입니다.");
    }

}