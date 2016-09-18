package services.members;

import domain.AUStudent;
import domain.Belt;

/**
 * Created by helen on 30/08/2016.
 */
public class AUStudentMapper {
    static public AUStudent toDomainModel(dto.AUStudent dtoAUStudent) {
        AUStudent fullStudent = new AUStudent (
                dtoAUStudent.getId(),
                dtoAUStudent.getEmail(),
                mapBeltToEnum(dtoAUStudent.getBelt()),
                dtoAUStudent.getFees(),
                dtoAUStudent.getAuid(),
                dtoAUStudent.getUpi(),
                dtoAUStudent.getPaidAnnualFee()
        );

        return fullStudent;
    }

    static public dto.AUStudent toDto(AUStudent auStudent) {
        dto.AUStudent dtoAUStudent = new dto.AUStudent(
                auStudent.getId(),
                auStudent.getEmail(),
                mapBeltToString(auStudent.getBelt()),
                auStudent.getFees(),
                auStudent.getAuid(),
                auStudent.getUpi(),
                auStudent.isPaidAnnualFee()
        );

        return dtoAUStudent;
    }

    static Belt mapBeltToEnum(String beltString) {
        switch (beltString) {
            case "Black First Dan":
                return Belt.BLACK_FIRST_DAN;
            case "Black Second Dan":
                return Belt.BLACK_SECOND_DAN;
            case "Black Third Dan":
                return Belt.BLACK_THIRD_DAN;
            case "Black Fourth Dan":
                return Belt.BLACK_FOURTH_DAN;
            case "Brown One Tab":
                return Belt.BROWN_ONE_TAB;
            case "Brown Two Tab":
                return Belt.BROWN_TWO_TAB;
            case "Brown":
                return Belt.BROWN;
            case "Green Tab":
                return Belt.GREEN_TAB;
            case "Green":
                return Belt.GREEN;
            case "Yellow Tab":
                return Belt.YELLOW_TAB;
            case "Yellow":
                return Belt.YELLOW;
            case "Blue Tab":
                return Belt.BLUE_TAB;
            case "Blue":
                return Belt.BLUE;
            case "White Tab":
                return Belt.WHITE_TAB;
            case "White":
                return Belt.WHITE;
        }

        return null;
    }

    static String mapBeltToString(Belt belt) {
        switch (belt) {
            case BLACK_FIRST_DAN:
                return "Black First Dan";
            case BLACK_SECOND_DAN:
                return "Black Second Dan";
            case BLACK_THIRD_DAN:
                return "Black Third Dan";
            case BLACK_FOURTH_DAN:
                return "Black Fourth Dan";
            case BROWN_ONE_TAB:
                return "Brown One Tab";
            case BROWN_TWO_TAB:
                return "Brown Two Tab";
            case BROWN:
                return "Brown";
            case GREEN_TAB:
                return "Green Tab";
            case GREEN:
                return "Green";
            case YELLOW_TAB:
                return "Yellow Tab";
            case YELLOW:
                return "Yellow";
            case BLUE_TAB:
                return "Blue Tab";
            case BLUE:
                return "Blue";
            case WHITE_TAB:
                return "White Tab";
            case WHITE:
                return "White";
        }

        return null;
    }
}
