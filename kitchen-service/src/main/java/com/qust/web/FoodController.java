package com.qust.web;

import com.qust.service.FoodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired
    FoodService foodService;

    @ApiOperation(value = "添加菜单类型", notes = "添加菜单类型")
    @PostMapping("/addfoodClass")
    public void addfoodClass(@RequestBody Map<String,Object> map){
        foodService.addfoodClass(map);
    }

    @ApiOperation(value = "获取该餐厅的菜单的所有类型", notes = "获取该餐厅的菜单的所有类型")
    @GetMapping("/getfoodClass")
    public List<Map<String,Object>> getfoodClass(@RequestParam  Long restaurant){
        return foodService.getFoodClass(restaurant);
    }

    @ApiOperation(value = "添加菜单", notes = "添加菜单")
    @PostMapping("/addFood")
    public void addfood(@RequestBody Map<String,Object> map){
        foodService.addfood(map);
    }

    @ApiOperation(value = "获取某种类型的全部菜品", notes = "获取某种类型的全部菜品")
    @GetMapping("/getfoodByclass")
    public List<Map<String,Object>> getfoodByclass(@RequestParam  Map<String,Object> map){
        return foodService.getfoodByclass(map);
    }

    @ApiOperation(value = "删除某菜单", notes = "删除某菜单")
    @GetMapping("/deleteFood")
    public void deleteFood(@RequestParam  Map<String,Object> map){
         foodService.deletefood(map);
    }

    @ApiOperation(value = "获取某个菜单的详细信息", notes = "获取某个菜单的详细信息")
    @GetMapping("/getFoodById")
    public Map<String,Object> getFoodById(@RequestParam  Map<String,Object> map){
        return foodService.getFoodById(map);
    }


    @ApiOperation(value = "修改菜单", notes = "修改菜单")
    @PostMapping("/updateFood")
    public void updateFood(@RequestBody Map<String,Object> map){
        foodService.updateFood(map);
    }


}
