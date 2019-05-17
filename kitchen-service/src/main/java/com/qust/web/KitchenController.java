package com.qust.web;

import com.qust.service.CodeService;
import com.qust.service.MaterialService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kitchen")
public class KitchenController {
    @Autowired
    CodeService codeService;
    @Autowired
    MaterialService materialService;

    @ApiOperation(value = "向后厨添加制作菜单", notes = "向后厨添加制作菜单")
    @PostMapping("/addOrder")
    public void addCodeOrder(@RequestBody Map<String,Object> map){
        codeService.addCodeOrder(map);
    }

    @ApiOperation(value = "根据类型获取该类型的所有物品列表", notes = "根据类型获取该类型的所有物品列表")
    @GetMapping("/getMaterial")
    public List<Map<String,Object>> getMaterial(@RequestParam  Map<String,Object> map){
        return materialService.getAllByTy(map);
    }

    @ApiOperation(value = "获取某个物品的详细信息", notes = "获取某个物品的详细信息")
    @GetMapping("/getSpecific")
    public Map<String,Object> getSpecific (@RequestParam  Map<String,Object> map){
        return materialService.getMateByI(map);
    }

    @ApiOperation(value = "修改某个物品的信息", notes = "修改某个物品的信息")
    @PostMapping("/alertMaterial")
    public void  alertMaterial (@RequestBody Map<String,Object> map){
        materialService.alertMaterial(map);
    }

    @ApiOperation(value = "检查添加的物品的名字是否重复占用", notes = "检查添加的物品的名字是否重复占用")
    @GetMapping("/checkName")
    public boolean checkName (@RequestParam  Map<String,Object> map){
         return materialService.checkName(map);
    }


    @ApiOperation(value = "添加一个物品", notes = "添加一个物品")
    @PostMapping("/saveMaterial")
    public void  saveMaterial (@RequestBody Map<String,Object> map){
        materialService.saveMaterial(map);
    }

    @ApiOperation(value = "检查添加的物品的名字是否重复占用", notes = "检查添加的物品的名字是否重复占用")
    @GetMapping("/deleteMaterial")
    public void deleteMaterial (@RequestParam  Map<String,Object> map){
        materialService.deleteMaterial(map);
    }


    @PostMapping("addoutflow")
    public void addoutflow (@RequestBody Map<String,Object> map) {
        materialService.addoutflow(map);
    }

    @PostMapping(value = "/getMateFlow")
    public Map<String,Object>   getMateFlow (@RequestBody Map<String,Object> map) {
        return materialService.getMateFlow(map);
    }

}
