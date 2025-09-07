package cleancode.studycafe.asis;

import cleancode.studycafe.asis.exception.AppException;
import cleancode.studycafe.asis.io.InputHandler;
import cleancode.studycafe.asis.io.OutputHandler;
import cleancode.studycafe.asis.io.StudyCafeFileHandler;
import cleancode.studycafe.asis.model.StudyCafeLockerPass;
import cleancode.studycafe.asis.model.StudyCafePass;
import cleancode.studycafe.asis.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            passTypeIntroduce();
            StudyCafePass cafePass = selectCafePass();

            StudyCafeLockerPass lockerPass = selectLockerPass(cafePass);
            outputHandler.showPassOrderSummary(cafePass, lockerPass);
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void passTypeIntroduce() {
        outputHandler.showWelcomeMessage();
        outputHandler.showAnnouncement();
        outputHandler.askPassTypeSelection();
    }

    private StudyCafePass selectCafePass() {
        StudyCafePassType cafePassType = inputHandler.getPassTypeSelectingUserAction();
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

        List<StudyCafePass> passCandidates = findPassCandidates(studyCafePasses, cafePassType);
        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    private StudyCafeLockerPass selectLockerPass(StudyCafePass studyCafePass) {
        List<StudyCafeLockerPass> lockerPassCandidates = studyCafeFileHandler.readLockerPasses();
        StudyCafeLockerPass lockerPass = findLockerPass(studyCafePass, lockerPassCandidates);

        if(lockerPass != null){
            outputHandler.askLockerPass(lockerPass);
            boolean lockerSelection = inputHandler.getLockerSelection();

            if(lockerSelection) {
                return lockerPass;
            }
        }

        return null;
    }

    private StudyCafeLockerPass findLockerPass(StudyCafePass selectedPass, List<StudyCafeLockerPass> lockerPasses) {
        return lockerPasses.stream()
                .filter(lockerPass ->
                        lockerPass.isSamePassType(selectedPass)
                                && lockerPass.isSameDuration(selectedPass)
                )
                .findFirst()
                .orElse(null);
    }

    private List<StudyCafePass> findPassCandidates(List<StudyCafePass> studyCafePasses, StudyCafePassType studyCafePassType) {
        return studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
                .toList();
    }

}
