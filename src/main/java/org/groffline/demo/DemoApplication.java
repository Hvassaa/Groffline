package org.groffline.demo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.*;

@SpringBootApplication
@RestController
public class DemoApplication {


  public static void main(String[] args) {
  SpringApplication.run(DemoApplication.class, args);
  }

  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
  return String.format("Hello %s!", name);
  }

  @RequestMapping(value="/groffline", method=RequestMethod.GET)
  public ResponseEntity<byte[]>
  groffline(@RequestParam(value = "macro", defaultValue = "ms") String macro,
            @RequestParam(value = "input", defaultValue = ".TL\nNo input") String input) throws IOException {

    //String decodedInput = URLDecoder.decode(input, StandardCharsets.UTF_8);
    String filename = Groff.render(input, Groff.Macro.MS);
    Path path = Paths.get(filename);
    byte[] contents = Files.readAllBytes(path);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(APPLICATION_PDF);
    headers.setContentDispositionFormData(filename, filename);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
    return response;
  }
}