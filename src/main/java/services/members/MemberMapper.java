package services.members;

import domain.Belt;
import domain.Member;

/**
 * Created by helen on 30/08/2016.
 */
public class MemberMapper {
    static public Member toDomainModel(dto.Member dtoMember) {
        Member fullMember = new Member(
                dtoMember.getId(),
                dtoMember.getEmail(),
                mapBeltToEnum(dtoMember.getBelt()),
                dtoMember.getFees()
        );

        return fullMember;
    }

    static public dto.Member toDto(Member member) {
        dto.Member dtoMember = new dto.Member(
                member.getId(),
                member.getEmail(),
                mapBeltToString(member.getBelt()),
                member.getFees()
        );

        return dtoMember;
    }

    static Belt mapBeltToEnum(String beltString) {
        switch (beltString) {
            case "black_1_dan":
                return Belt.BLACK_FIRST_DAN;
            case "black_2_dan":
                return Belt.BLACK_SECOND_DAN;
            case "black_3_dan":
                return Belt.BLACK_THIRD_DAN;
            case "black_4_dan":
                return Belt.BLACK_FOURTH_DAN;
            case "brown_1_tab":
                return Belt.BROWN_ONE_TAB;
            case "brown_2_tab":
                return Belt.BROWN_TWO_TAB;
            case "brown":
                return Belt.BROWN;
            case "green_tab":
                return Belt.GREEN_TAB;
            case "green":
                return Belt.GREEN;
            case "yellow_tab":
                return Belt.YELLOW_TAB;
            case "yellow":
                return Belt.YELLOW;
            case "blue_tab":
                return Belt.BLUE_TAB;
            case "blue":
                return Belt.BLUE;
            case "white_tab":
                return Belt.WHITE_TAB;
            case "white":
                return Belt.WHITE;
        }

        return null;
    }

    static String mapBeltToString(Belt belt) {
        switch (belt) {
            case BLACK_FIRST_DAN:
                return "black_1_dan";
            case BLACK_SECOND_DAN:
                return "black_2_dan";
            case BLACK_THIRD_DAN:
                return "black_3_dan";
            case BLACK_FOURTH_DAN:
                return "black_4_dan";
            case BROWN_ONE_TAB:
                return "brown_1_tab";
            case BROWN_TWO_TAB:
                return "brown_2_tab";
            case BROWN:
                return "brown";
            case GREEN_TAB:
                return "green_tab";
            case GREEN:
                return "green";
            case YELLOW_TAB:
                return "yellow_tab";
            case YELLOW:
                return "yellow";
            case BLUE_TAB:
                return "blue_tab";
            case BLUE:
                return "blue";
            case WHITE_TAB:
                return "white_tab";
            case WHITE:
                return "white";
        }

        return null;
    }
}
