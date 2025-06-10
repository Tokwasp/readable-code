package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CellSignFinderTest {

    @DisplayName("지뢰셀의 출력할 모양을 찾는다.")
    @Test
    void findCellSignFrom() {
        //given
        CellSnapshot landMine = CellSnapshot.ofLandMine();
        CellSignFinder cellSignFinder = new CellSignFinder();

        //when
        String cellSign = cellSignFinder.findCellSignFrom(landMine);

        //then
        assertThat(cellSign).isEqualTo("☼");
    }
}