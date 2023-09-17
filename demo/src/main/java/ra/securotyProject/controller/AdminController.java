package ra.securotyProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.securotyProject.exception.NotfoundException;
import ra.securotyProject.exception.AlreadyExistException;
import ra.securotyProject.model.dto.request.CatagoryRequest;
import ra.securotyProject.model.dto.request.ColorRequest;
import ra.securotyProject.model.dto.request.TrademarkRequest;
import ra.securotyProject.model.dto.response.CatagoryResponse;
import ra.securotyProject.model.dto.response.ColorResponse;
import ra.securotyProject.model.dto.response.JwtResponse;
import ra.securotyProject.model.dto.response.TrademarkResponse;
import ra.securotyProject.service.ICatagoryService;
import ra.securotyProject.service.IColorService;
import ra.securotyProject.service.ITrandemarkService;
import ra.securotyProject.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private IUserService userService;
    @PatchMapping("/updateRole/{id}")
    public JwtResponse updateRole(@PathVariable Long id) throws NotfoundException, AlreadyExistException {
        return userService.updateRole(id);
    }
    @Autowired
    private ICatagoryService catagoryService;
    @PostMapping("/addCatagory")
    public ResponseEntity<CatagoryResponse> addCatagory(@RequestBody CatagoryRequest catagoryRequest) throws AlreadyExistException{
        return new ResponseEntity<>(catagoryService.save(catagoryRequest), HttpStatus.CREATED);
    }
    @GetMapping("/allCatagory")
    public ResponseEntity<List<CatagoryResponse>> getAllCatagory(){
        return new ResponseEntity<>(catagoryService.finAll(),HttpStatus.OK);
    }
    @PutMapping("/updateCatagory/{id}")
    public ResponseEntity<CatagoryResponse> updateCatagory(@PathVariable Long id, @RequestBody CatagoryRequest catagoryRequest) throws NotfoundException
    { return new ResponseEntity<CatagoryResponse>(catagoryService.update( catagoryRequest, id), HttpStatus.OK);
    }
    @DeleteMapping("/deleteCatagory/{id}")
    public ResponseEntity<String> deleteCatagory(@PathVariable Long id) throws NotfoundException
    {   catagoryService.delete(id);
        return new ResponseEntity<> ("Đã xóa thành cônng",HttpStatus.OK);
    }

    @PatchMapping("/blog/{id}")
    public ResponseEntity<String> blogUser(@PathVariable Long id) throws NotfoundException {
        return new ResponseEntity<>(userService.block_user(id),HttpStatus.OK);
    }
    @Autowired
    private IColorService colorService;
    @PostMapping("/addColor")
    public ResponseEntity<ColorResponse> addColor(@RequestBody ColorRequest colorRequest) throws AlreadyExistException {
        return new ResponseEntity<>(colorService.save(colorRequest), HttpStatus.CREATED);
    }
    @GetMapping("/allColor")
    public ResponseEntity<List<ColorResponse>> getAllColor(){
        return new ResponseEntity<>(colorService.finAll(),HttpStatus.OK);
    }
    @PutMapping("/updateColor/{id}")
    public ResponseEntity<ColorResponse> updateColor(@PathVariable Long id, @RequestBody ColorRequest trademarkRequest) throws NotfoundException
    { return new ResponseEntity<ColorResponse>(colorService.update( trademarkRequest, id), HttpStatus.OK);
    }
    @DeleteMapping("/deleteColor/{id}")
    public ResponseEntity<String> deleteColor(@PathVariable Long id) throws NotfoundException
    {   colorService.delete(id);
        return new ResponseEntity<> ("Đã xóa thành cônng",HttpStatus.OK);
    }
    @Autowired
    private ITrandemarkService trandemarkService;
    @PostMapping("/addTrademark")
    public ResponseEntity<TrademarkResponse> addTrademark(@RequestBody TrademarkRequest trademarkRequest) throws AlreadyExistException{
        return new ResponseEntity<>(trandemarkService.save(trademarkRequest),HttpStatus.CREATED);
    }
    @GetMapping("/allTrademark")
    public ResponseEntity<List<TrademarkResponse>> getAllTrademark(){
        return new ResponseEntity<>(trandemarkService.finAll(),HttpStatus.OK);
    }
    @PutMapping("/updateTrademark/{id}")
    public ResponseEntity<TrademarkResponse> updateTrademark(@PathVariable Long id, @RequestBody TrademarkRequest trademarkRequest) throws NotfoundException
    { return new ResponseEntity<TrademarkResponse>(trandemarkService.update( trademarkRequest, id), HttpStatus.OK);
    }
    @DeleteMapping("/deleteTrademark/{id}")
    public ResponseEntity<String> deleteTrademark(@PathVariable Long id) throws NotfoundException
    {   trandemarkService.delete(id);
        return new ResponseEntity<> ("Đã xóa thành cônng",HttpStatus.OK);
    }
}
