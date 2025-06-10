package cleancode.minesweeper.tobe.minesweeper.board.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class LandMineCellTest {

    @DisplayName("지뢰셀을 열지 않고 깃발을 꽂지 않았다면 셀의 임시 상태는 UNCHECKED 상태 이다.")
    @Test
    void getSnapshot() {
        //given
        LandMineCell landMineCell = new LandMineCell();

        //when
        CellSnapshot snapshot = landMineCell.getSnapshot();

        //then
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.UNCHECKED);
    }

    @DisplayName("지뢰셀을 열었다면 셀의 임시 상태는 LANDMINE 이다.")
    @Test
    void getSnapshot_WhenOpenCell_ThenCellSnapshotStatusIsLandMine() {
        //given
        LandMineCell landMineCell = new LandMineCell();
        landMineCell.open();

        //when
        CellSnapshot snapshot = landMineCell.getSnapshot();

        //then
        assertThat(snapshot.getStatus()).isEqualTo(CellSnapshotStatus.LAND_MINE);
    }
}