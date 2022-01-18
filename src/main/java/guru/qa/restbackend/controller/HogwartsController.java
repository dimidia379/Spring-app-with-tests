package guru.qa.restbackend.controller;

import guru.qa.restbackend.domain.HouseInfo;
import guru.qa.restbackend.domain.StudentInfo;
import guru.qa.restbackend.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class HogwartsController {

    StudentService studentService = new StudentService();

    @PostMapping("student")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Добавление ученика")
    public StudentInfo addStudent(@RequestBody StudentInfo studentInfo) {
        return studentService.addStudent(studentInfo);
    }

    @GetMapping("students/all")
    @ApiOperation("Список всех учеников")
    public List<StudentInfo> getAllStudentsInfo() {
        return studentService.getAll();
    }

    @GetMapping("students/house")
    @ApiOperation("Список учеников определенного факультета")
    public List<StudentInfo> getStudentsByHouse(@RequestParam("housename") HouseInfo houseInfo) {
        return studentService.getHouseStudentsList(houseInfo);
    }

    @GetMapping("students/search")
    @ApiOperation("Поиск ученика по имени")
    public List<StudentInfo> searchStudentByName(@RequestParam("name") String name) {
        return studentService.searchStudentByName(name);
    }

}
