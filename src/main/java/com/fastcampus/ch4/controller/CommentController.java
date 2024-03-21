package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.CommentDto;
import com.fastcampus.ch4.service.CommentService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.servlet.http.HttpSession;
import java.util.List;

//@Controller
//@ResponseBody
@RestController
public class CommentController {
    @Autowired
    CommentService service;


    @PatchMapping("/comments/{cno}")
    public ResponseEntity<String> modify(@RequestBody CommentDto dto, @PathVariable Integer cno, HttpSession session){
        String commenter = "asdf"; //(String) session.getAttribute("id");
        dto.setCno(cno);
        dto.setCommenter(commenter);
        System.out.println(dto);

        try {
            int row_cnt = service.modify(dto);

            if(row_cnt != 1){
                throw new Exception("write failed");
            }
            return new ResponseEntity<String>("MOD_OK",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("MOD_ERR",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/comments")
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session){
        String commenter = "asdf"; //(String) session.getAttribute("id");
        dto.setBno(bno);
        dto.setCommenter(commenter);
        System.out.println(dto);

        try {
            int row_cnt = service.write(dto);

            if(row_cnt != 1){
                throw new Exception("write failed");
            }
            return new ResponseEntity<String>("WRT_OK",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("WRT_ERR",HttpStatus.BAD_REQUEST);
        }
    }

    // 지정된 댓글 삭제하는 메소드
    @DeleteMapping("/comments/{cno}") // /comments/1? 쿼리 내용 <- 1은 삭제할 댓글 번호.
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session){
        String commenter = "asdf"; //(String) session.getAttribute("id");
        try {
            int rowCnt = service.remove(cno,bno,commenter);
            System.out.println("rowCnt1 = " + rowCnt);
            if(rowCnt!=1)
                throw new Exception("Delete Failed");

            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> list(Integer bno){

        List<CommentDto> list = null;

        try {
            list = service.getList(bno);
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.BAD_REQUEST);

        }
    }
}
