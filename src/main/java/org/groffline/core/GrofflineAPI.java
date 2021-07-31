package org.groffline.core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import static org.springframework.http.MediaType.*;

@SpringBootApplication
@RestController
public class GrofflineAPI {


  public static void main(String[] args) {
  SpringApplication.run(GrofflineAPI.class, args);
  }

  @RequestMapping(value="/groffline", method=RequestMethod.GET)
  public ResponseEntity<byte[]>
  groffline(@RequestParam(value = "macro", defaultValue = "ms") String macro,
            @RequestParam(value = "input", defaultValue = ".TL\nNo input") String input) throws IOException {

    Groff.Macro useMacro;
    if(macro.equals(Groff.Macro.ME.toString())) {useMacro = Groff.Macro.ME;}
    else if(macro.equals(Groff.Macro.MM.toString())) {useMacro = Groff.Macro.MM;}
    else if(macro.equals(Groff.Macro.MOM.toString())) {useMacro = Groff.Macro.MOM;}
    else {useMacro = Groff.Macro.MS;}

    byte[] contents = Groff.render(input, useMacro);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(APPLICATION_PDF);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
    return response;
  }
}