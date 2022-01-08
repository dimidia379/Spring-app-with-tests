package guru.qa.restbackend.controller;

import guru.qa.restbackend.domain.HouseInfo;
import guru.qa.restbackend.domain.StudentInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class HogwartsController {


    private Map<String, StudentInfo> students = Map.of(
            "Harry", StudentInfo.builder().studentName("Harry Potter").build(),
            "Ron", StudentInfo.builder().studentName("Ron Weasley").studentHouse("Gryffindor").build(),
            "Hermione", StudentInfo.builder().studentName("Hermione Granger").studentHouse("Gryffindor").build(),
            "Draco", StudentInfo.builder().studentName("Draco Malfoy").studentHouse("Slytherin").build()
    );

    @PostMapping("student")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Добавление ученика")
    public StudentInfo addStudent(@RequestBody StudentInfo studentInfo) {
        StudentInfo student = StudentInfo.builder()
                .studentName(studentInfo.getStudentName())
                .studentHouse(studentInfo.getStudentHouse())
                .build();
        return student;


//        StudentInfo student = new StudentInfo();
//        student.setStudentName(studentInfo.getStudentName());
//        student.setStudentHouse(studentInfo.getStudentHouse());
//        students.put(studentInfo.getStudentName(), student);
//        return student;

    }

    @PatchMapping("students/{studentInfo}/house")
    @ApiOperation("Распределение ученика на факультет")
    public StudentInfo enrollToHouse(@PathVariable("studentInfo") String studentInfo, @RequestBody HouseInfo houseInfo) {
        StudentInfo student = students.get(studentInfo);
        student.setStudentHouse(houseInfo.getHouseName());
        return student;
    }

    @GetMapping("students/all")
    @ApiOperation("Получение всех учеников")
    public List<StudentInfo> getAllStudentsInfo() {
        List <StudentInfo> result = new ArrayList<>();
        for (Map.Entry<String, StudentInfo> entry : students.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }


}

