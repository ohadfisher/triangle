package triangle.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import triangle.model.State;
import triangle.model.Triangle;
import triangle.service.StorageService;

import java.util.List;

@RestController
public class TriangleController {
    private static final Logger log = LoggerFactory.getLogger(TriangleController.class);

    @Autowired
    StorageService storageService;

    @RequestMapping(value = "/addTriangle", method = RequestMethod.POST)
    public void add(@RequestBody Triangle triangleInput) {
        log.info("TriangleController.addTriangle: " + triangleInput + "\n");
        storageService.add(triangleInput);
    }



}



