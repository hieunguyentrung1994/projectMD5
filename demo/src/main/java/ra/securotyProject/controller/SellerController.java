package ra.securotyProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.securotyProject.exception.AlreadyExistException;
import ra.securotyProject.exception.NotfoundException;
import ra.securotyProject.model.dto.request.ColorRequest;
import ra.securotyProject.model.dto.request.TrademarkRequest;
import ra.securotyProject.model.dto.response.ColorResponse;
import ra.securotyProject.model.dto.response.TrademarkResponse;
import ra.securotyProject.service.IColorService;
import ra.securotyProject.service.ITrandemarkService;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
public class SellerController {


}
