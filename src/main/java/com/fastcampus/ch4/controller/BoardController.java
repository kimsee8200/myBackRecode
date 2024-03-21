package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.*;
import com.fastcampus.ch4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @PostMapping("/write")
    public String write(BoardDto boardDto, Model m, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.write(boardDto);

            if(rowCnt!=1){
                throw new Exception("Write Failed");
            }

            rattr.addFlashAttribute("msg", "WRT_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            rattr.addFlashAttribute("msg", "WRT_ERR");
            return "board";
        }

    }

    @GetMapping("/write")
    public String write(Model m){
        m.addAttribute("mode", "new");
        return "board";
    }

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.modify(boardDto);

            if(rowCnt!=1){
                throw new Exception("Modify Failed");
            }

            rattr.addFlashAttribute("msg", "MOD_OK");

            return "redirect:/board/list?page="+page+"&pageSize="+pageSize;
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute(boardDto);
            rattr.addFlashAttribute("msg", "MOD_ERR");
            return "board";
        }

    }

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model m, RedirectAttributes rattr){
        try {
            BoardDto dto =  boardService.read(bno);
            //m.addAttribute("boardDto",dto);
            m.addAttribute(dto); // 타입의 앞 글자를 소문자로 이름 저장.
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
        }
        catch (IllegalArgumentException e){
            rattr.addFlashAttribute("msg","NO_CTX");
            return "redirect:/board/list?page="+page+"&pageSize="+pageSize;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "board";
    }

    @PostMapping ("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");

        try {

            m.addAttribute("page",page);
            m.addAttribute("pageSize", pageSize);

            int rowCnt = boardService.remove(bno, writer );

            if(rowCnt!=1){
                throw new Exception("board remove error");
            }

            rattr.addFlashAttribute("msg", "Delete had success");
        }catch (Exception e){
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "DEL_ERR");
        }


        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(SearchCondition sc, Model m, HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();

        try {

            int totalCnt = boardService.getSerachResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            List<BoardDto> list = boardService.getSearchResultPage(sc);
            m.addAttribute("list",list);
            m.addAttribute("ph", pageHandler);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "boardList";
    }

    @GetMapping("comment/list")
    public ResponseEntity<List<BoardDto>> listC(HttpServletRequest request) {

        try {

            List<BoardDto> list = boardService.getList();

            return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK) ;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    private boolean loginCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute("id")!=null;
    }
}
