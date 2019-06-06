package server.rest;

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
        return new ResponseEntity("MinFinParserController",HttpStatus.OK);
    }
}