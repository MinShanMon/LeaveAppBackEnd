package ca.team3.laps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ca.team3.laps.exception.LeaveException;
import ca.team3.laps.model.ExtraHour;
import ca.team3.laps.model.Leave;
import ca.team3.laps.repository.ExtraHourRepository;
import ca.team3.laps.service.ExtraHourService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api")
public class ExtraHourController {
    @Autowired
    ExtraHourRepository extraHourRepository;

    @Autowired
    ExtraHourService extraHourService;

    @GetMapping("/extra/get/{id}")
    public ResponseEntity suboExtraHour(@PathVariable("id") Integer stfid){
        try{
            return new ResponseEntity<>(extraHourService.suboExtraHour(stfid), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //creating extrahour form
    @PostMapping("/extra/post")
    public ResponseEntity createExtra(@RequestBody ExtraHour extraHour){
        try{
            return new ResponseEntity<>(extraHourService.createExtraHour(extraHour), HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    //delete the extra
    @DeleteMapping("/extra/delete/{id}")
    public ResponseEntity deleteExtra(@PathVariable("id") Integer id){        
        try {
            var isRemoved = extraHourService.delExtraHour(id);
            if (!isRemoved) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update extra
    @PutMapping(value="/extra/put")
    public ResponseEntity updExtraHour(@RequestBody ExtraHour extraHour){
        try{
            ExtraHour ext = extraHourService.updExtraHour(extraHour.getId(), extraHour);
            return new ResponseEntity<>(ext, HttpStatus.CREATED);
        }
        catch(LeaveException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch(Exception e){            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //approve extra
    @PutMapping(value = "/extra/approve/put/{id}")
    public ResponseEntity approvExtraHour(@PathVariable("id") Integer id){
        try{
            return new ResponseEntity<>(extraHourService.approvExtraHour(id), HttpStatus.OK);
        }
        catch(LeaveException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    //reject extra
    @PutMapping(value = "/extra/reject/put/{id}")
    public ResponseEntity rejecExtraHour(@PathVariable("id") Integer id){
        try{
            return new ResponseEntity<>(extraHourService.rejecExtraHour(id), HttpStatus.OK);
        }
        catch(LeaveException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
