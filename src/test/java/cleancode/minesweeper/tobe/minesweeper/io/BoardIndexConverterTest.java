package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.exception.GameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoardIndexConverterTest {

    @DisplayName("사용자의 입력으로부터 보드의 행 인덱스를 찾는다.")
    @Test
    void getSelectedRowIndex() {
        //given
        BoardIndexConverter indexConverter = new BoardIndexConverter();

        //when
        int rowIndex = indexConverter.getSelectedRowIndex("A3");

        //then
        assertThat(rowIndex).isEqualTo(2);
    }

    @DisplayName("사용자의 입력으로부터 보드의 행 인덱스를 찾을때 입력한 행이 0이라면 예외가 발생 한다.")
    @Test
    void getSelectedRowIndex_WhenRowIndexIsZero_ThenThrowException() {
        //given
        BoardIndexConverter indexConverter = new BoardIndexConverter();

        //when //then
        assertThatThrownBy(() -> indexConverter.getSelectedRowIndex("A0"))
                .isInstanceOf(GameException.class).hasMessage("잘못된 입력입니다.");
    }
}