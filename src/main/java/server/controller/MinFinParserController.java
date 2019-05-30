package server.controller;

import org.springframework.http.HttpStatus;
import server.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Controller
public class MinFinParserController extends BaseController {
    @RequestMapping(value = "/minfin/parser/**")
    public ResponseEntity baseRedbackController(
            HttpServletRequest request,
            HttpServletResponse response) {
        System.out.println(request);
        return new ResponseEntity("hello world",HttpStatus.OK);
    }

}