// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package solubris.marketmon.web;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import solubris.marketmon.domain.Runner;
import solubris.marketmon.web.RunnerController;

privileged aspect RunnerController_Roo_Controller_Json {
    
    @RequestMapping(value = "/{selectionId}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> RunnerController.showJson(@PathVariable("selectionId") Long selectionId) {
        Runner runner = Runner.findRunner(selectionId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (runner == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(runner.toJson(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> RunnerController.listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Runner> result = Runner.findAllRunners();
        return new ResponseEntity<String>(Runner.toJsonArray(result), headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> RunnerController.createFromJson(@RequestBody String json) {
        Runner runner = Runner.fromJsonToRunner(json);
        runner.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> RunnerController.createFromJsonArray(@RequestBody String json) {
        for (Runner runner: Runner.fromJsonArrayToRunners(json)) {
            runner.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> RunnerController.updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Runner runner = Runner.fromJsonToRunner(json);
        if (runner.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> RunnerController.updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Runner runner: Runner.fromJsonArrayToRunners(json)) {
            if (runner.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{selectionId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> RunnerController.deleteFromJson(@PathVariable("selectionId") Long selectionId) {
        Runner runner = Runner.findRunner(selectionId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (runner == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        runner.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
}
