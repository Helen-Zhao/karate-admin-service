package services.members;

import domain.AUStudent;

/**
 * Created by helen on 30/08/2016.
 * <p>
 * Name: Helen Zhao
 * UPI: hzha587
 * AUID: 6913580
 * <p>
 * SOFTENG 325 ASSIGNMENT 1 MAIN
 */

public class AUStudentMapper {
    static public AUStudent toDomainModel(dto.AUStudent dtoAUStudent) {
        AUStudent fullStudent = new AUStudent(
                dtoAUStudent.getId(),
                dtoAUStudent.getEmail(),
                MemberMapper.mapBeltToEnum(dtoAUStudent.getBelt()),
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
                MemberMapper.mapBeltToString(auStudent.getBelt()),
                auStudent.getFees(),
                auStudent.getAuid(),
                auStudent.getUpi(),
                auStudent.isPaidAnnualFee()
        );

        return dtoAUStudent;
    }
}
